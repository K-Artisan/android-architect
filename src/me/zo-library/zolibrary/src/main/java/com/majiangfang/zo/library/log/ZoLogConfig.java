package com.majiangfang.zo.library.log;

public abstract class ZoLogConfig {

    static int MAX_LINE_LEN = 512; //每行最多字符数
    static ZoStackTraceFormatter ZO_STACK_TRACE_FORMATTER = new ZoStackTraceFormatter();
    static ZoThreadFormatter ZO_THREAD_FORMATTER = new ZoThreadFormatter();

    public String getGlobalTag() {
        return "ZoLog";
    }

    /**
     * log是否可用
     * @return
     */
    public boolean enable() {
        return true;
    }

    /**
     * 日志里是否要包含线程信息
     *
     * @return
     */
    public boolean includeThread() {
        return false;
    }

    /**
     * 堆栈信息的深度
     *
     * @return
     */
    public int stackTraceDepth() {
        return 5;
    }

    public ZoLogPrinter[] printers() {
        return null;
    }

    /**
     * 让调用者实现该方法，避免Log框架依赖某个序列化框架
     *
     * @return
     */
    public JsonParser injectJsonParser() {
        return null;
    }

    /**
     * 对象序列化接口
     */
    public interface JsonParser {
        String toJson(Object src);
    }
}
