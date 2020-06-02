package com.nancheung.gitproxy.git;

import cn.hutool.core.util.ZipUtil;
import com.nancheung.gitproxy.GitProxyProperties;
import com.nancheung.gitproxy.command.CommandResult;
import com.nancheung.gitproxy.command.CommandUtils;
import com.nancheung.gitproxy.common.exception.GitProxyException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

/**
 * Git操作
 *
 * @author NanCheung
 */
@Slf4j
@Service
@AllArgsConstructor
public class GitService {
    
    private final GitProxyProperties gitProxyProperties;
    
    @Async("gitCloneExecutor")
    public void clone(GitInfo gitInfo) {
        log.debug("开始clone，git信息[{}]", gitInfo);
        
        //创建临时文件夹
        Path tempDirectory;
        try {
            tempDirectory = Files.createTempDirectory(gitProxyProperties.getTempDirPath(), gitInfo.getRepositoryName());
        } catch (IOException e) {
            throw new GitProxyException("创建临时文件夹失败！", e);
        }
        String tempDirectoryPath = tempDirectory.toString();
        
        //clone项目到临时文件夹
        CommandResult result;
        try {
            result = CommandUtils.getResult("git clone " + gitInfo.getUrl(), tempDirectoryPath);
        } catch (ExecutionException e) {
            throw new GitProxyException("clone命令执行失败！", e);
        } catch (InterruptedException e) {
            throw new GitProxyException("clone操作中断！", e);
        }
        Assert.isTrue(CommandUtils.verify(result), result.getResult());
        
        //将项目打压缩包
        ZipUtil.zip(tempDirectoryPath, gitProxyProperties.getZipFilePath() + "\\" + tempDirectory.getFileName() + ".zip");
    }
}
