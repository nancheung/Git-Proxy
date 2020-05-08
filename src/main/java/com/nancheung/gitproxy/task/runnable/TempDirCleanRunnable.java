package com.nancheung.gitproxy.task.runnable;

import com.nancheung.gitproxy.task.CleanStrategyProperties;

import java.nio.file.Path;

/**
 * 清除临时文件夹任务
 *
 * @author NanCheung
 */
public class TempDirCleanRunnable extends AbstractCleanRunnable {
    
    public TempDirCleanRunnable(Path cleanPath, CleanStrategyProperties.Strategy cleanStrategy) {
        super(cleanPath, cleanStrategy);
    }
    
    @Override
    protected String runnableName() {
        return "清除临时文件夹";
    }
    
}
