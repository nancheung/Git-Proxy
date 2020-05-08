package com.nancheung.gitproxy.controller;

import com.nancheung.gitproxy.git.GitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Git操作接口
 *
 * @author NanCheung
 */
@AllArgsConstructor
@RestController
@RequestMapping
public class GitController {
    
    private final GitService gitService;
    
    @GetMapping("clone")
    public void clone(String url) throws InterruptedException, ExecutionException, IOException {
        gitService.clone(url);
    }
}
