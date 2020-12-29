package com.majiangfang.zo.library.log;

/**
 * 堆栈信息格式化
 */
public class ZoStackTraceFormatter implements ZoLogFormatter<StackTraceElement[]> {
    @Override
    public String format(StackTraceElement[] stackTraces) {
        StringBuilder sb = new StringBuilder(128);
        if (stackTraces == null || stackTraces.length == 0) {
            return null;
        } else if (stackTraces.length == 1) {
            return "\t-" + stackTraces[0].toString();
        } else {
            for (int i = 0, len = stackTraces.length; i < len; i++) {
                if (i == 0) {
                    sb.append("stackTrace: \n");
                }

                if (i != len - 1){
                    sb.append("\t├ ");
                    sb.append(stackTraces[i].toString());
                    sb.append("\n");
                }else {
                    sb.append("\t└ ");
                    sb.append(stackTraces[i].toString());
                }
            }
        }

        return sb.toString();
    }
}
