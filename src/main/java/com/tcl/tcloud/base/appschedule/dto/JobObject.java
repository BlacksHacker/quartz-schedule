package com.tcl.tcloud.base.appschedule.dto;

import lombok.Data;
import org.quartz.JobDataMap;

import java.util.Date;

/**
 * @ClassName JobDetail
 * @Description TODO
 * @Author xiaosheng1.li
 **/
@Data
public class JobObject {
    private String jobId;
    private String jobGroup;
    private String cron;
    private Date endTime;
    private Date startTime;
    private Date appointTime;
    private Integer interval;
    private Integer repeat;
    private String jobClassName;
    private JobDataMap jobDataMap = new JobDataMap();

}
