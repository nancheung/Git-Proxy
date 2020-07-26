/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.nancheung.gitproxy.common.rbac.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nancheung.gitproxy.common.rbac.enums.RbacClientExceptionEnum;
import com.nancheung.gitproxy.common.restful.ApiResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证异常方案
 *
 * @author NanCheung
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    
    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        ApiResult<Void> result = ApiResult.failed(RbacClientExceptionEnum.AUTHENTICATION_FAILED, authException.getMessage());
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
