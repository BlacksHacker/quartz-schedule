package com.tcl.tcloud.base.appschedule.utils;

import java.util.UUID;

/**
 * @ClassName DemandUtil
 * @Description TODO
 * @Author xiaosheng1.li
 **/
public class DemandUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
