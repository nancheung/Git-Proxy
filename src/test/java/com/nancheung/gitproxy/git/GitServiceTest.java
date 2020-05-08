package com.nancheung.gitproxy.git;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GitServiceTest {
    
    public static final String GIT_URL = "https://github.com/nancheung97/Git-Proxy";
    
    @Autowired
    private GitService gitService;
    
    @Test
    void testClone() throws InterruptedException, ExecutionException, IOException {
        gitService.clone(GIT_URL);
    }
    
    @Test
    void testGetGitInfo() {
        GitInfo gitInfo = gitService.getGitInfo(GIT_URL);
        System.out.println(gitInfo);
    }
}