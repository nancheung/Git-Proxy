package com.nancheung.gitproxy.command;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.RuntimeUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.concurrent.*;

/**
 * 命令行执行工具
 *
 * @author NanCheung
 */
@Slf4j
@UtilityClass
public class CommandUtils {
    
    /**
     * 执行指令
     *
     * @param command  指令
     * @param execPath 执行指令的路径
     * @return 指令处理器
     */
    public Process exec(String command, String execPath) {
        return RuntimeUtil.exec(null, new File(execPath), command);
    }
    
    /**
     * 获取指令执行后的结果
     *
     * @param command  指令
     * @param execPath 执行指令的路径
     * @return 指令执行结果
     * @throws ExecutionException   执行过程中可能抛出该异常
     * @throws InterruptedException 当线程阻塞时，可能抛出该异常
     */
    public CommandResult getResult(String command, String execPath) throws ExecutionException, InterruptedException {
        return getResult(exec(command, execPath));
    }
    
    /**
     * 获取指令执行后的结果
     *
     * @param process 指令处理器
     * @return 指令执行结果
     * @throws ExecutionException   执行过程中可能抛出该异常
     * @throws InterruptedException 当线程阻塞时，可能抛出该异常
     */
    public CommandResult getResult(Process process) throws InterruptedException, ExecutionException {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNamePrefix("clone-result-pool-%d").build();
        ExecutorService threadPool = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    
        String result;
        try {
            Future<String> resultFuture = threadPool.submit(() -> RuntimeUtil.getResult(process));
            Future<String> errorResultFuture = threadPool.submit(() -> RuntimeUtil.getErrorResult(process));
        
            while (true) {
                if (resultFuture.isDone() && errorResultFuture.isDone()) {
                    if (StringUtils.isEmpty(result = resultFuture.get())) {
                        result = errorResultFuture.get();
                    }
                    break;
                }
            }
        } finally {
            threadPool.shutdown();
        }
    
        return CommandResult.builder()
                .status(process.waitFor())
                .result(result)
                .build();
    }
    
    /**
     * 校验指令是否执行成功
     *
     * @param process 指令处理器
     * @return 指令是否执行成功
     */
    public boolean verify(Process process) {
        try {
            return verify(getResult(process));
        } catch (ExecutionException | InterruptedException e) {
            log.error("指令执行错误", e);
        }
        return false;
    }
    
    /**
     * 校验指令是否执行成功
     *
     * @param result 指令执行结果
     * @return 指令是否执行成功
     */
    public boolean verify(CommandResult result) {
        return result.getStatus() == 0;
    }
}
