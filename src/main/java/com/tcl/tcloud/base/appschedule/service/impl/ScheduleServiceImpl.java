package com.tcl.tcloud.base.appschedule.service.impl;

import com.tcl.tcloud.base.appschedule.dto.DemandDto;
import com.tcl.tcloud.base.appschedule.dto.JobObject;
import com.tcl.tcloud.base.appschedule.schedule.DemandTImer;
import com.tcl.tcloud.base.appschedule.schedule.ScheduleFactory;
import com.tcl.tcloud.base.appschedule.service.ScheduleService;
import com.tcl.tcloud.base.appschedule.utils.Constant;
import com.tcl.tcloud.base.appschedule.utils.DateUtil;
import com.tcl.tcloud.base.appschedule.utils.StatusResult;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName ScheduleServiceImpl
 * @Description TODO
 * @Author xiaosheng1.li
 **/
@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private static final String JOB_PREFIX = "JOB-UUID-";
    private static final String JOB_GROUP_PREFIX = "JOBGroup-UUID-";

    private Scheduler scheduler;

    @Autowired
    public void setScheduler(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    @Override
    public StatusResult addDemandSchedule(DemandDto demandDto) {
        try {
            //防止重复添加，先删除需求对应所有定时任务
            ScheduleFactory.removeJobByGroup(JOB_GROUP_PREFIX + demandDto.getUuid(), scheduler);
            List<String> cronList = demandDto.getCronList();
            if (!CollectionUtils.isEmpty(cronList)){
                for (String cron : cronList){
                    JobObject jobObject = new JobObject();
                    jobObject.setJobId(JOB_PREFIX + UUID.randomUUID());
                    jobObject.setJobGroup(JOB_GROUP_PREFIX + demandDto.getUuid());
                    jobObject.setStartTime(DateUtil.StringToDate(demandDto.getStartTime()));
                    jobObject.setEndTime(DateUtil.StringToDate(demandDto.getEndTime()));
                    jobObject.setCron(cron);
                    jobObject.setJobClassName(DemandTImer.class.getName());
                    jobObject.getJobDataMap().put("demand", demandDto);
                    ScheduleFactory.addJob(jobObject, scheduler);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return StatusResult.builder()
                    .status(Constant.Status.FAILURE)
                    .data(e)
                    .build();
        }
        return StatusResult.builder()
                .status(Constant.Status.SUCCESS)
                .data(demandDto.getUuid())
                .build();
    }

    @Override
    public StatusResult pauseDemandSchedule(String demandUuid) {
        String jobGroup = JOB_GROUP_PREFIX + demandUuid;
        try {
            ScheduleFactory.pauseJobByGroup(jobGroup, scheduler);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return StatusResult.builder()
                    .status(Constant.Status.FAILURE)
                    .data(e)
                    .build();
        }
        return StatusResult.builder()
                .status(Constant.Status.SUCCESS)
                .data(demandUuid)
                .build();
    }

    @Override
    public StatusResult resumeDemandSchedule(String demandUuid) {
        String jobGroup = JOB_GROUP_PREFIX + demandUuid;
        try {
            ScheduleFactory.resumeJobByGroup(jobGroup, scheduler);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return StatusResult.builder()
                    .status(Constant.Status.FAILURE)
                    .data(e)
                    .build();
        }
        return StatusResult.builder()
                .status(Constant.Status.SUCCESS)
                .data(demandUuid)
                .build();
    }

    @Override
    public StatusResult removeDemandSchedule(String demandUuid) {
        String jobGroup = JOB_GROUP_PREFIX + demandUuid;
        try {
            ScheduleFactory.removeJobByGroup(jobGroup, scheduler);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return StatusResult.builder()
                    .status(Constant.Status.FAILURE)
                    .data(e)
                    .build();
        }
        return StatusResult.builder()
                .status(Constant.Status.SUCCESS)
                .data(demandUuid)
                .build();
    }

    @Override
    public void test() {
//        try {
//            JobObject jobObject = new JobObject();
//            jobObject.setJobId(JOB_PREFIX + UUID.randomUUID());
//            jobObject.setJobGroup(JOB_GROUP_PREFIX + 123);
////        jobObject.setStartTime(DateUtil.StringToDate(demandDto.getStartTime()));
////        jobObject.setEndTime(DateUtil.StringToDate(demandDto.getEndTime()));
////        jobObject.setCron(cron);
//            jobObject.setStartTime(DateUtil.StringToDate("2020-08-01 23:59:59"));
//            jobObject.setEndTime(DateUtil.StringToDate("2020-10-01 23:59:59"));
//            jobObject.setAppointTime(DateUtil.StringToDate("2020-08-17 14:40:00"));
//            jobObject.setJobClassName(DemandTImer.class.getName());
//            jobObject.getJobDataMap().put("demand", null);
//            ScheduleFactory.addJob(jobObject, scheduler);
//            System.out.println("add");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        try {
            long startAtTime = System.currentTimeMillis() + 1000 * 600;
            //任务名称
            String name = UUID.randomUUID().toString();
            //任务所属分组
            String group = DemandTImer.class.getName();
            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(DemandTImer.class).withIdentity(name, group).build();
            //创建任务触发器
            //Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).startAt(new Date(startAtTime)).build();
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("20 * * * * ? *")).startNow().endAt(new Date(startAtTime)).build();
            //将触发器与任务绑定到调度器内
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println(jobDetail.getKey());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
