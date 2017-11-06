package com.xcz1899.logger;

import android.util.Log;

/**
 * 文件说明:Log 工具类
 *
 * @author xc.zhang
 */

public class Logger {
    /**
     * 是否输出日志
     */
    private static boolean enable = true;
    /**
     * 是否输出线程
     */
    private static boolean enableThread = true;
    /**
     * 日志等级
     */
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    /**
     * 日志标签
     */
    private static String tag = "Logger";
    /**
     * 格式化日志的标线
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER;

    private Logger() {
        //防止实例化
    }

    /**
     * 设置默认的TAG
     * @param tag 默认标签
     */
    public static void setTag(String tag) {
        Logger.tag = tag;
    }

    /**
     * 设置是否输出日志
     * @param enable 是否输出日志
     */
    public static void setEnable(boolean enable) {
        Logger.enable = enable;
    }

    /**
     * 设置是否输出线程信息
     * @param enableThread 是否输出线程信息
     */
    public static void setEnableThread(boolean enableThread) {
        Logger.enableThread = enableThread;
    }

    public static void v(String message) {
        Logger.v(tag, message);
    }

    public static void v(String tag, String message) {
        Logger.logFormat(VERBOSE, tag, message);
    }

    public static void d(String message) {
        Logger.d(tag, message);
    }

    public static void d(String tag, String message) {
        Logger.logFormat(DEBUG, tag, message);
    }

    public static void i(String message) {
        Logger.i(tag, message);
    }

    public static void i(String tag, String message) {
        Logger.logFormat(INFO, tag, message);
    }

    public static void w(String message) {
        Logger.w(tag, message);
    }

    public static void w(String tag, String message) {
        Logger.logFormat(WARN, tag, message);
    }

    public static void e(String message) {
        Logger.e(tag, message);
    }

    public static void e(String tag, String message) {
        Logger.logFormat(ERROR, tag, message);
    }


    /**
     * 格式化日志
     *
     * @param msg 日志信息
     *
     * @return 返回新的日志信息
     */
    private static void logFormat(int level, String tag, String msg) {
        logTopBorder(level, tag);
        logHeaderContent(level, tag);
        logContent(level, tag, msg);
        logBottomBorder(level, tag);
    }

    /**
     * 日志头部
     * @param level
     * @param tag
     */
    private static void logTopBorder(int level, String tag) {
        logOutput(level, tag, TOP_BORDER);
    }

    /**
     * 日志上部内容，包括线程信息和调用位置
     * @param level
     * @param tag
     */
    private static void logHeaderContent(int level, String tag) {
        if (enableThread) {
            logOutput(level, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().getName());
            logDivider(level, tag);
        }
        // StackTrace存储信息的规则：函数调用的先后顺序
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : trace) {
            if (stackTraceElement.isNativeMethod() || stackTraceElement.getClassName().equals(Thread.class.getName())
                    || stackTraceElement.getClassName().equals(Logger.class.getName())) {
                //剔除本地方法，剔除线程类，剔除当前类
                continue;
            } else {
                //记录下调用当前类的函数位置
                StringBuilder builder = new StringBuilder();
                builder.append("║ ")
                        .append(getSimpleClassName(stackTraceElement.getClassName()))
                        .append(".")
                        .append(stackTraceElement.getMethodName())
                        .append(" ")
                        .append(" (")
                        .append(stackTraceElement.getFileName())
                        .append(":")
                        .append(stackTraceElement.getLineNumber())
                        .append(")");
                logOutput(level, tag, builder.toString());
                logDivider(level, tag);
                break;
            }
        }
    }

    /**
     * 去除类名中的 .class 后缀
     * @param name
     * @return
     */
    private static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }
    /**
     * 日志的分隔符
     * @param level
     * @param tag
     */
    private static void logDivider(int level, String tag) {
        logOutput(level, tag, MIDDLE_BORDER);
    }

    /**
     * 日志的内容
     * @param level
     * @param tag
     * @param message
     */
    private static void logContent(int level, String tag, String message) {
        logOutput(level, tag, HORIZONTAL_DOUBLE_LINE + " " + message);
    }

    /**
     * 日志内容的底部
     * @param level
     * @param tag
     */
    private static void logBottomBorder(int level, String tag) {
        logOutput(level, tag, BOTTOM_BORDER);
    }


    /**
     * 真实调用Log类输出日志
     *
     * @param level   log等级
     * @param tag     log的标签
     * @param message log的内容
     */
    private static void logOutput(int level, String tag, String message) {
        if (enable) {
            switch (level) {
                case VERBOSE:
                    Log.v(tag, message);
                    break;
                case INFO:
                    Log.i(tag, message);
                    break;
                case WARN:
                    Log.w(tag, message);
                    break;
                case ERROR:
                    Log.e(tag, message);
                    break;
                case DEBUG:
                default:
                    Log.v(tag, message);
                    break;
            }
        }
    }
}
