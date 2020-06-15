package com.nancheung.gitproxy.api.git.controller;

import com.nancheung.gitproxy.api.git.common.ApiResult;
import com.nancheung.gitproxy.api.git.common.GitInfo;
import com.nancheung.gitproxy.api.git.service.GitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Git操作接口
 *
 * @author NanCheung
 */
@RestController
@RequestMapping
@AllArgsConstructor
@Api(value = "git仓库操作", tags = "Git操作模块")
public class GitController {
    
    private final GitService gitService;
    
    @ApiOperation(value = "克隆git仓库")
    @ApiImplicitParam(name = "url", value = "git仓库url", required = true, dataType = "String")
    @GetMapping("clone")
    public ApiResult<Void> clone(String url) {
        gitService.clone(GitInfo.build(url));
        return ApiResult.success();
    }
}
