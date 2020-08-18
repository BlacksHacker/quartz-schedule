package com.tcl.tcloud.base.appschedule.utils;

/**
 * @ClassName Constant
 * @Description TODO
 * @Author xiaosheng1.li
 **/
public class Constant {
    /**
     * @Author xiaosheng1.li
     * @Description 状态常量
     * @Param
     * @return
     **/
    public static class Status {
        public static final String SUCCESS = "success";
        public static final String FAILURE = "failure";
    }

    public static class IsOpenType{
        public static final int CLOSE = 0;
        public static final int OPEN = 1;
        public static final int TIME_OUT = 2;
    }

    public static class DemandStatus{
        public static final int PENDING = 0;
        public static final int AGREE = 1;
        public static final int REFUSE = 2;
    }

    public static class Code{
        private static final String EC = "EC1003";
    }

    public static class Message{

    }
}
