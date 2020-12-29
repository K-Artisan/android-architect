package com.majiangfang.zo.library.log;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Tips:
 * 1.打印堆栈信息
 * 2.File输出
 * 3.模拟控制台
 */
public class ZoLog {
    private static final String ZO_LOG_PACKAGE;

    static {
        String className = ZoLog.class.getName();
        ZO_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

    static void v(Object... contents) {
        log(ZoLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(ZoLogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(ZoLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(ZoLogType.D, tag, contents);
    }

    public static void i(Object... contents) {
        log(ZoLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(ZoLogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(ZoLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(ZoLogType.W, tag, contents);
    }

    public static void e(Object... contents) {
        log(ZoLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(ZoLogType.E, tag, contents);
    }

    public static void a(Object... contents) {
        log(ZoLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(ZoLogType.A, tag, contents);
    }


    public static void log(@ZoLogType.Type int type, Object... contents) {
        String logTag = ZoLogManager.getInstance().getConfig().getGlobalTag();
        log(type, logTag, contents);
    }

    public static void log(@ZoLogType.Type int type, String tag, Object... contents) {
        ZoLogConfig logConfig = ZoLogManager.getInstance().getConfig();
        log(logConfig, type, tag, contents);
    }

    public static void log(@NonNull ZoLogConfig config, @ZoLogType.Type int type, String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }

        List<ZoLogPrinter> printers = config.printers() != null
                ? Arrays.asList(config.printers())
                : ZoLogManager.getInstance().getPrinters();
        if (printers == null) {
            return; //无Log打印器返回
        }

        StringBuilder sb = new StringBuilder();
        //线程信息
        if (config.includeThread()) {
            String threadInfo = ZoLogConfig.ZO_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        //堆栈信息
        if (config.stackTraceDepth() > 0) {
            String stackTraceInfo = ZoLogConfig.ZO_STACK_TRACE_FORMATTER.format(
                    ZoStackTraceUtil.getCroppedRealStackTrack(new Throwable().getStackTrace(), ZO_LOG_PACKAGE, config.stackTraceDepth())
            );
            sb.append(stackTraceInfo).append("\n");
        }

        //解析获取记录对象的信息
        String body = parseBody(contents, config);
        sb.append(body);

        //打印Log
        for (ZoLogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());
        }
    }


    /**
     * 解析并获取记录对象的描述信息
     *
     * @param contents 记录对象
     * @param config
     * @return
     */
    private static String parseBody(@NonNull Object[] contents, @NonNull ZoLogConfig config) {
        if (config.injectJsonParser() != null) {
            //只有一个数据且为String的情况下不再进行序列化
            if (contents.length == 1 && contents[0] instanceof String) {
                return (String) contents[0];
            }
            return config.injectJsonParser().toJson(contents);
        };

        return parseBodyDefault(contents);
    }

    private static String parseBodyDefault(@NonNull Object[] contents) {

        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1); //删除最后一个分号
        }

        return sb.toString();
    }
}
