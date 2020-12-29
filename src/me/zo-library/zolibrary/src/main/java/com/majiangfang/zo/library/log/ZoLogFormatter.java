package com.majiangfang.zo.library.log;

public interface ZoLogFormatter<T> {
    String format(T data);
}
