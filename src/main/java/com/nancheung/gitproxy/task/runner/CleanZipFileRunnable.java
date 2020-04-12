package com.nancheung.gitproxy.task.runner;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import com.nancheung.gitproxy.GitProxyProperties;
import lombok.AllArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 清除zip包任务
 *
 * @author NanCheung
 */
@AllArgsConstructor
public class CleanZipFileRunnable implements Runnable {
    
    private final GitProxyProperties gitProxyProperties;
    
    @Override
    public void run() {
        File[] files = gitProxyProperties.getZipFileDir().toFile().listFiles();
        
        if (ArrayUtil.isEmpty(files)) {
            return;
        }
        
        GitProxyProperties.Clean.Time clean = gitProxyProperties.getClean().getZipFile();
        
        Arrays.stream(files)
                .filter(file -> DateUtil.toLocalDateTime(DateUtil.date(file.lastModified()))
                        .plus(clean.getTime(), clean.getUnit()).isBefore(LocalDateTime.now()))
                .forEach(FileUtil::del);
    }
}
