package com.nancheung.gitproxy.git;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

/**
 * Git信息
 *
 * @author NanCheung
 */
@Getter
@ToString
@Builder(access = AccessLevel.PRIVATE)
public class GitInfo {
    /**
     * git服务器
     */
    private String gitServer;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 仓库名
     */
    private String repositoryName;
    /**
     * url
     */
    private String url;
    
    public static GitInfo build(String url) {
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
