package com.majiangfang.zo.library.log;

/**
 * 线程信息格式化
 */
public class ZoThreadFormatter implements ZoLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "Thread:" + data.getName();
    }
}
