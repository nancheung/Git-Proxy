package com.nancheung.gitproxy;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.nio.file.Path;
import java.time.temporal.ChronoUnit;

/**
 * 参数配置
 *
 * @author : NanCheung
 */
@Data
@ConfigurationProperties(prefix = "git-proxy")
public class GitProxyProperties {
    /**
     * 程序文件存放路径
     */
    private Path programFileDir = new File(System.getProperty("user.home") + "\\.git_proxy\\").toPath();
    /**
     * 临时文件夹路径
     */
    private Path tempDir = new File(programFileDir.toString() + "\\temp\\").toPath();
    /**
     * 压缩包文件存放路径
     */
    private Path zipFileDir = new File(programFileDir.toString() + "\\zip\\").toPath();
    /**
     * 自动清理文件策略
     */
    private Clean clean;
    
    /**
     * 自动清理文件策略
     */
    @Data
    public static class Clean {
        
        /**
         * 压缩包清理策略
         */
        private Time zipFile;
        /**
         * 临时文件夹清理策略
         */
        private Time tempDir;
        
        @Data
        public static class Time {
            /**
             * 自动清理文件间隔时长
             */
            private int time;
            /**
             * 自动清理文件间隔时长单位
             */
            private ChronoUnit unit;
        }
        
    }
}
