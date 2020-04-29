package com.nancheung.gitproxy.task.runner;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import com.nancheung.gitproxy.task.CleanStrategyProperties;
import lombok.AllArgsConstructor;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 清除zip包任务
 *
 * @author NanCheung
 */
@AllArgsConstructor
public class CleanZipFileRunnable implements Runnable {
    
    /**
     * 清理文件夹路径
     */
    private final Path cleanPath;
    
    /**
     * 清理时间策略
     */
    private final CleanStrategyProperties.Strategy cleanStrategy;
    
    @Override
    public void run() {
        File[] files = cleanPath.toFile().listFiles();
        
        if (ArrayUtil.isEmpty(files)) {
            return;
        }
        
        Arrays.stream(files)
                .filter(file -> DateUtil.toLocalDateTime(DateUtil.date(file.lastModified()))
                        .plus(cleanStrategy.getTime(), cleanStrategy.getUnit()).isBefore(LocalDateTime.now()))
                .forEach(FileUtil::del);
    }
}
