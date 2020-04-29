package com.nancheung.gitproxy.task;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.temporal.ChronoUnit;

/**
 * 清理策略配置
 *
 * @author NanCheung
 */
@Data
@ConfigurationProperties(prefix = "git-proxy.auto-clean-strategy")
public class CleanStrategyProperties {
    
    /**
     * 压缩包清理策略
     */
    private Strategy zipFile;
    
    /**
     * 临时文件夹清理策略
     */
    private Strategy tempDir;
    
    {
        zipFile = Strategy.builder().time(1).unit(ChronoUnit.HOURS).build();
        tempDir = Strategy.builder().time(10).unit(ChronoUnit.SECONDS).build();
    }
    
    @Data
    @Builder
    public static class Strategy {
        
        /**
         * 自动清理文件间隔时长
         */
        private Integer time;
        
        /**
         * 自动清理文件间隔时长单位
         */
        private ChronoUnit unit;
    }
    
}
