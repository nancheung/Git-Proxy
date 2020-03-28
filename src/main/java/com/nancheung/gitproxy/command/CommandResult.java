package com.nancheung.gitproxy.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 命令行结果
 *
 * @author : NanCheung
 */
@Getter
@ToString
@Builder
public class CommandResult {
    /**
     * 执行状态，0：成功；其他失败
     */
    private int status;
    /**
     * 指令返回的结果
     */
    private String result;
}
