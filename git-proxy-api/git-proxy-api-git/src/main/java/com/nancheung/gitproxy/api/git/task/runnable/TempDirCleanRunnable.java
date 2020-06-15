package com.nancheung.gitproxy.api.git.task.runnable;

import com.nancheung.gitproxy.api.git.task.CleanStrategyProperties;

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
