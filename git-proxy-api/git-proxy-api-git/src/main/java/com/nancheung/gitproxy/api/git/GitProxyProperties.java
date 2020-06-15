package com.nancheung.gitproxy.api.git;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.nio.file.Path;

/**
 * 参数配置
 *
 * @author NanCheung
 */
@Data
@ConfigurationProperties(prefix = "git-proxy")
public class GitProxyProperties {
    
    /**
     * 默认程序文件路径
     */
    public static final Path DEFAULT_PROGRAM_FILE_PATH = new File(System.getProperty("user.home") + "\\.git_proxy\\").toPath();
    
    /**
     * 程序文件存放路径
     */
    private Path programFilePath;
    
    /**
     * 临时文件夹路径
     */
    private Path tempDirPath;
    
    /**
     * 压缩包文件存放路径
     */
    private Path zipFilePath;
    
    {
        programFilePath = DEFAULT_PROGRAM_FILE_PATH;
        tempDirPath = new File(DEFAULT_PROGRAM_FILE_PATH.toString() + "\\temp\\").toPath();
        zipFilePath = new File(DEFAULT_PROGRAM_FILE_PATH.toString() + "\\zip\\").toPath();
    }
}
