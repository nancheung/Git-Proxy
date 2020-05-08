package com.nancheung.gitproxy.task.runnable;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import com.nancheung.gitproxy.task.CleanStrategyProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 清理任务抽象类
 * <p>所有清理任务必须由该类派生</p>
 *
 * @author NanCheung
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractCleanRunnable implements Runnable {
    
    /**
     * 清理文件夹路径
     */
    private final Path cleanPath;
    
    /**
     * 清理时间策略
     */
    private final CleanStrategyProperties.Strategy cleanStrategy;
    
    /**
     * 声明任务名称
     *
     * @return 任务名称
     */
    protected abstract String runnableName();
    
    
    @Override
    public void run() {
        File[] allFiles = cleanPath.toFile().listFiles();
    
        Optional.ofNullable(allFiles)
                .filter(ArrayUtil::isNotEmpty)
                .map(files -> {
                    LocalDateTime cleanFlagTime = LocalDateTime.now().plus(-cleanStrategy.getTime(), cleanStrategy.getUnit());
                    return Arrays.stream(files)
                            .filter(file -> DateUtil.toLocalDateTime(DateUtil.date(file.lastModified())).isBefore(cleanFlagTime))
                            .collect(Collectors.toList());
                })
                .filter(CollectionUtil::isNotEmpty)
                .ifPresent(files -> {
                    files.forEach(FileUtil::del);
    
                    if (log.isDebugEnabled()) {
                        log.debug("[{}]完成，清理数量[{}]", this.runnableName(), files.size());
                    }
                });
    
    }
}
