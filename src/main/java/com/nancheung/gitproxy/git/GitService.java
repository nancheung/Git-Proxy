package com.nancheung.gitproxy.git;

import cn.hutool.core.util.ZipUtil;
import com.nancheung.gitproxy.GitProxyProperties;
import com.nancheung.gitproxy.command.CommandResult;
import com.nancheung.gitproxy.command.CommandUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    
    public File clone(String url) throws ExecutionException, InterruptedException, IOException {
        //解析git信息
        GitInfo gitInfo = getGitInfo(url);
        
        //创建临时文件夹
        String tempDirectoryPath = Files.createTempDirectory(gitProxyProperties.getTempDirPath(), ".git_proxy").toString();
        
        //clone项目到临时文件夹
        CommandResult result = CommandUtils.getResult("git clone " + gitInfo.getUrl(), tempDirectoryPath);
        
        Assert.isTrue(CommandUtils.verify(result), result.getResult());
        
        //将项目打压缩包
        return ZipUtil.zip(tempDirectoryPath, gitProxyProperties.getZipFilePath() + "\\" + gitInfo.getRepositoryName() + ".zip");
    }
    
    public GitInfo getGitInfo(String url) {
        Assert.hasText(url, "git链接不能为空");
        String[] gitInfos = url.split("/");
        Assert.isTrue(gitInfos.length == 5, "git链接格式错误！");
        
        return GitInfo.builder()
                .gitServer(gitInfos[2])
                .userName(gitInfos[3])
                .repositoryName(gitInfos[4])
                .url(url)
                .build();
    }
    
}
