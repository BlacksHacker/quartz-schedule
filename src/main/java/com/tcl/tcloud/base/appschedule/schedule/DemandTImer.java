package com.tcl.tcloud.base.appschedule.schedule;

import com.tcl.tcloud.base.appschedule.utils.DateUtil;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @ClassName AddTImer
 * @Description TODO
 * @Author xiaosheng1.li
 **/
public class DemandTImer extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //判断需求优先级逻辑
        System.out.println(DateUtil.now() + "---------------------------------------开始定时执行。。。。");
    }
}
