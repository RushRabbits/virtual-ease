package com.awake.ve.common.tenant.handle;

import cn.hutool.core.collection.ListUtil;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.tenant.helper.TenantHelper;
import com.awake.ve.common.tenant.properties.TenantProperties;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;

import java.util.List;

/**
 * 自定义租户处理器
 *
 * @author wangjiaxing
 * @date 2025/1/7 10:04
 */
@Slf4j
@AllArgsConstructor
public class PlusTenantLineHandler implements TenantLineHandler {

    private final TenantProperties tenantProperties;

    /**
     * 获取租户id
     *
     * @author wangjiaxing
     * @date 2025/1/7 11:08
     */
    @Override
    public Expression getTenantId() {
        String tenantId = TenantHelper.getTenantId();
        if (StringUtils.isBlank(tenantId)) {
            log.error("无法获取有效的租户id -> Null");
            return new NullValue();
        }
        return new StringValue(tenantId);
    }

    /**
     * 忽略的表
     *
     * @param tableName 表名
     * @author wangjiaxing
     * @date 2025/1/7 11:09
     */
    @Override
    public boolean ignoreTable(String tableName) {
        String tenantId = TenantHelper.getTenantId();
        // 判断是否有租户
        if (StringUtils.isNotBlank(tenantId)) {
            // 不需要过滤租户的表
            List<String> excludes = tenantProperties.getExcludes();
            // 非业务表
            List<String> tables = ListUtil.toList("gen_table", "gen_table_column");
            tables.addAll(excludes);
            return tables.contains(tableName);
        }
        return true;
    }
}
