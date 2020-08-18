package com.tcl.tcloud.base.appschedule.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName StatusResult
 * @Description TODO
 * @Author xiaosheng1.li
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusResult<T> {
    private String status;

    private String errorCode;

    private T data;

    private String msg;
}
