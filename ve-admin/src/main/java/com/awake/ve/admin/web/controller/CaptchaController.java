package com.awake.ve.admin.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.awake.ve.admin.web.domain.vo.CaptchaVo;
import com.awake.ve.common.core.constant.Constants;
import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.core.utils.reflect.ReflectUtils;
import com.awake.ve.common.mail.config.properties.MailProperties;
import com.awake.ve.common.mail.utils.MailUtils;
import com.awake.ve.common.rateLimiter.annotation.RateLimiter;
import com.awake.ve.common.rateLimiter.enums.LimitType;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.common.web.config.properties.CaptchaProperties;
import com.awake.ve.common.web.enums.CaptchaType;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.LinkedHashMap;

/**
 * 验证码控制器
 *
 * @author wangjiaxing
 * @date 2025/2/12 9:24
 */
@SaIgnore
@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
public class CaptchaController {

    private final CaptchaProperties captchaProperties;
    private final MailProperties mailProperties;

    /**
     * 短信验证码
     *
     * @param phonenumber 手机号
     * @author wangjiaxing
     * @date 2025/2/12 9:26
     */
    @RateLimiter(key = "#phonenumber", time = 60, count = 1)
    @GetMapping("/resource/sms/code")
    public R<Void> smsCode(@NotBlank(message = "{user.phonenumber.not.blank}") String phonenumber) {
        String key = GlobalConstants.CAPTCHA_CODE_KEY + phonenumber;
        String code = RandomUtil.randomNumbers(4);
        RedisUtils.setCacheObject(key, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
        // 验证码模板id 自行处理(查数据库或写死)
        String templateId = "";
        LinkedHashMap<String, String> map = new LinkedHashMap<>(1);
        map.put("code", code);
        SmsBlend smsBlend = SmsFactory.getSmsBlend("config1");
        SmsResponse smsResponse = smsBlend.sendMessage(phonenumber, templateId, map);
        if (!smsResponse.isSuccess()) {
            log.error("[CaptchaController][smsCode] 发送短信验证码失败:{}", smsResponse.getData());
            return R.fail(smsResponse.getData().toString());
        }
        return R.ok();
    }

    /**
     * 邮箱验证码
     *
     * @param email 邮箱
     * @author wangjiaxing
     * @date 2025/2/12 9:35
     */
    @RateLimiter(key = "#email", time = 60, count = 1)
    public R<Void> emailCode(@NotBlank(message = "{user.email.not.blank}") String email) {
        if (!mailProperties.getEnabled()) {
            return R.fail("邮箱服务未开启");
        }
        String key = GlobalConstants.CAPTCHA_CODE_KEY + email;
        String code = RandomUtil.randomNumbers(4);
        RedisUtils.setCacheObject(key, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
        try {
            MailUtils.sendText(email, "登录验证码", "本次登录验证码为:" + code + ",有效期为" + Constants.CAPTCHA_EXPIRATION + "分钟,请尽快填写");
        } catch (Exception e) {
            log.error("[CaptchaController][emailCode] 发送邮箱验证码失败", e);
            return R.fail(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 生成验证码
     *
     * @author wangjiaxing
     * @date 2025/2/12 9:40
     */
    @RateLimiter(time = 60, count = 10, limitType = LimitType.IP)
    @GetMapping("/auth/code")
    public R<CaptchaVo> generateCode() {
        CaptchaVo captchaVo = new CaptchaVo();
        if (!captchaProperties.getEnable()) {
            captchaVo.setCaptchaEnabled(false);
            return R.ok(captchaVo);
        }

        // 保存验证码信息
        String uuid = IdUtil.randomUUID();
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + uuid;

        // 生成验证码
        CaptchaType captchaType = captchaProperties.getType();
        boolean isMath = CaptchaType.MATH == captchaType;
        Integer length = isMath ? captchaProperties.getNumberLength() : captchaProperties.getCharLength();
        CodeGenerator codeGenerator = ReflectUtils.newInstance(captchaType.getClazz(), length);
        AbstractCaptcha captcha = SpringUtils.getBean(captchaProperties.getCategory().getClazz());

        captcha.setGenerator(codeGenerator);
        captcha.createCode();

        // 如果是数学验证码,使用SpEL表达式处理验证码结果
        String code = captcha.getCode();
        if(isMath){
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(StringUtils.remove(code, "="));
            code = expression.getValue(String.class);
        }
        RedisUtils.setCacheObject(verifyKey , code , Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
        captchaVo.setUuid(uuid);
        captchaVo.setImg(captcha.getImageBase64());
        return R.ok(captchaVo);
    }
}
