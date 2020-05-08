package com.nancheung.gitproxy.task.runnable;

import com.nancheung.gitproxy.task.CleanStrategyProperties;

import java.nio.file.Path;

/**
 * 清除zip包任务
 *
 * @author NanCheung
 */
public class ZipFileCleanRunnable extends AbstractCleanRunnable {
    
    public ZipFileCleanRunnable(Path cleanPath, CleanStrategyProperties.Strategy cleanStrategy) {
        super(cleanPath, cleanStrategy);
    }
    
    @Override
    protected String runnableName() {
        return "清除zip包";
    }
}
