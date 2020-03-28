package com.nancheung.gitproxy.git;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Git信息
 *
 * @author : NanCheung
 */
@Getter
@ToString
@Builder
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
}
