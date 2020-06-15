package com.nancheung.gitproxy.api.git.service;

import com.nancheung.gitproxy.api.git.common.GitInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GitServiceTest {
    
    public static final String GIT_URL = "https://github.com/nancheung97/Git-Proxy";
    
    @Autowired
    private GitService gitService;
    
    @Test
    void testClone() {
        gitService.clone(GitInfo.build(GIT_URL));
    }
    
    @Test
    void testGetGitInfo() {
        GitInfo gitInfo = GitInfo.build(GIT_URL);
        System.out.println(gitInfo);
    }
}