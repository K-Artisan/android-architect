package com.majiangfang.zo.library.log;

import androidx.annotation.NonNull;

/**
 * 日志打印器接口
 */
public interface ZoLogPrinter {
    void print(@NonNull ZoLogConfig config, int level, String tag, @NonNull String printString);
}
