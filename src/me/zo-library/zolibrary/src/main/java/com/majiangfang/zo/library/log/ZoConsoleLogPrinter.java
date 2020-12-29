package com.majiangfang.zo.library.log;

import android.util.Log;

import androidx.annotation.NonNull;


/**
 * 控制台日志打印器
 */
public class ZoConsoleLogPrinter implements ZoLogPrinter {
    @Override
    public void print(@NonNull ZoLogConfig config, int level, String tag, @NonNull String printString) {
        int len = printString.length();
        int countOfSub = len / ZoLogConfig.MAX_LINE_LEN;

        if (countOfSub > 0) {
            StringBuilder log = new StringBuilder();
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                log.append(printString.substring(index, index + ZoLogConfig.MAX_LINE_LEN));
                index += ZoLogConfig.MAX_LINE_LEN;
            }

            //不整除，把剩余的打印出来
            if (index != len) {
                log.append((printString.substring(index, len)));
            }

            Log.println(level, tag, log.toString());
        } else {
            Log.println(level, tag, printString);
        }
    }
}
