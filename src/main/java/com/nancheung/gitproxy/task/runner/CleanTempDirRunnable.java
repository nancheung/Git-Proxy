package com.nancheung.gitproxy.task.runner;

import com.nancheung.gitproxy.task.CleanStrategyProperties;

import java.nio.file.Path;

/**
 * 清除临时文件夹任务
 *
 * @author NanCheung
 */
public class CleanTempDirRunnable extends AbstractCleanRunnable {
    
    public CleanTempDirRunnable(Path cleanPath, CleanStrategyProperties.Strategy cleanStrategy) {
        super(cleanPath, cleanStrategy);
    }
    
    @Override
    protected String runnableName() {
        return "清除临时文件夹";
    }
    
}
