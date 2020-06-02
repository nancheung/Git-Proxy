package com.nancheung.gitproxy.controller;

import com.nancheung.gitproxy.common.ApiResult;
import com.nancheung.gitproxy.git.GitInfo;
import com.nancheung.gitproxy.git.GitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResult<Void> clone(String url) {
        gitService.clone(GitInfo.build(url));
        return ApiResult.success();
    }
}
