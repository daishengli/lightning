package top.daishengli.lightning.common;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 公用缓存组件
 *
 * @author daishengli
 */
@Component
public class CommonCache {
    /**
     * 定时缓存组件，默认到期时间5分钟
     *
     * @return 定时缓存组件
     */
    @Bean
    public TimedCache<String, Object> timedCache() {
        TimedCache<String, Object> timedCache = CacheUtil.newTimedCache(300000);
        timedCache.schedulePrune(1000);
        return timedCache;
    }
}
