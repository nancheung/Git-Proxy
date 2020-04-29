package com.nancheung.gitproxy.task.runner;

import com.nancheung.gitproxy.task.CleanStrategyProperties;

import java.nio.file.Path;

/**
 * 清除zip包任务
 *
 * @author NanCheung
 */
public class CleanZipFileRunnable extends AbstractCleanRunnable {
    
    public CleanZipFileRunnable(Path cleanPath, CleanStrategyProperties.Strategy cleanStrategy) {
        super(cleanPath, cleanStrategy);
    }
    
    @Override
    protected String runnableName() {
        return "清除zip包";
    }
}
