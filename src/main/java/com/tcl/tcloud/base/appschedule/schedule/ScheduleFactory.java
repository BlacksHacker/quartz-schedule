package com.tcl.tcloud.base.appschedule.schedule;

import com.tcl.tcloud.base.appschedule.dto.JobObject;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Schedule
 * @Description TODO
 * @Author xiaosheng1.li
 **/
public class ScheduleFactory {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleFactory.class);
    public static void addJob(JobObject jobObject, Scheduler scheduler){
        TriggerBuilder triggerBuilder= null;
        boolean isValidCron = StringUtils.isNotBlank(jobObject.getCron()) && CronExpression.isValidExpression(jobObject.getCron());
        boolean isValidInterval = jobObject.getInterval() != null && jobObject.getInterval() > 0;
        boolean isValidRepeat = jobObject.getRepeat() != null && jobObject.getRepeat() > 1;
        boolean isValidAppointTime = jobObject.getAppointTime() != null && jobObject.getAppointTime().after(new Date());

        try {
            if (isValidCron || isValidInterval || isValidRepeat || isValidAppointTime){
                triggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobObject.getJobId(), jobObject.getJobGroup());
                if (isValidCron){
                    // cron表达式触发器,设置服务重启后，什么都不错，等待Cron定义下次任务执行的时间点 withMisfireHandlingInstructionDoNothing，misFire默认时间60s,60s内不算misFire
                    triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(jobObject.getCron()).withMisfireHandlingInstructionDoNothing());
                }else if (isValidAppointTime){
                    //指定大于当前时间的点
                    triggerBuilder.startAt(jobObject.getAppointTime());
                }else {
                    //指定间隔时间和重复次数
                    triggerBuilder.withSchedule(simpleBuilder(jobObject));
                }
            }
            //设置触发和结束时间
            if (triggerBuilder != null){
                triggerBuilder = setTriggerBuilderTime(triggerBuilder, jobObject.getStartTime(), jobObject.getEndTime());
                Trigger trigger = triggerBuilder.build();
                Class clazz = Class.forName(jobObject.getJobClassName());
                JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobObject.getJobId(), jobObject.getJobGroup()).usingJobData(jobObject.getJobDataMap()).build();
                boolean isValidJob = jobObject.getEndTime() == null || jobObject.getEndTime().after(new Date());
                if (!scheduler.isShutdown() && isValidJob){
                    scheduler.scheduleJob(jobDetail, trigger);
                    resumeJob(jobObject.getJobId(), jobObject.getJobGroup(), scheduler);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void pauseAll(Scheduler scheduler) throws SchedulerException{
        scheduler.pauseAll();
    }

    public static void resumeAll(Scheduler scheduler) throws SchedulerException{
        scheduler.resumeAll();
    }

    public static void pauseJob(String jobId, String jobGroup, Scheduler scheduler) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobId, jobGroup);
        TriggerKey triggerKey = TriggerKey.triggerKey(jobId, jobGroup);
        if (scheduler.checkExists(triggerKey)){
            scheduler.pauseTrigger(triggerKey);
        }
        if (scheduler.checkExists(jobKey)){
            scheduler.pauseJob(jobKey);
        }
    }

    public static void resumeJob(String jobId, String jobGroup, Scheduler scheduler) throws SchedulerException{
        JobKey jobKey = JobKey.jobKey(jobId, jobGroup);
        TriggerKey triggerKey = TriggerKey.triggerKey(jobId, jobGroup);
        if (scheduler.checkExists(triggerKey)){
            scheduler.resumeTrigger(triggerKey);
        }
        if (scheduler.checkExists(jobKey)){
            scheduler.resumeJob(jobKey);
        }
    }

    public static void pauseJobByGroup(String jobGroup, Scheduler scheduler) throws SchedulerException{
        scheduler.pauseTriggers(GroupMatcher.triggerGroupEquals(jobGroup));
        scheduler.pauseJobs(GroupMatcher.jobGroupEquals(jobGroup));
    }

    public static void resumeJobByGroup(String jobGroup, Scheduler scheduler) throws SchedulerException{
        scheduler.resumeTriggers(GroupMatcher.triggerGroupEquals(jobGroup));
        scheduler.resumeJobs(GroupMatcher.jobGroupEquals(jobGroup));
    }

    public static void removeJob(String jobId, String jobGroup, Scheduler scheduler) throws SchedulerException{
        TriggerKey triggerKey = TriggerKey.triggerKey(jobId, jobGroup);
        JobKey jobKey = JobKey.jobKey(jobId, jobGroup);
        if (scheduler.checkExists(triggerKey) && scheduler.checkExists(jobKey)){
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        }
    }

    public static void removeJobByGroup(String jobGroup, Scheduler scheduler) throws SchedulerException{
        GroupMatcher<TriggerKey> tkMatcher = GroupMatcher.triggerGroupEquals(jobGroup);
        List<TriggerKey> triggerKeyList = new ArrayList<>(scheduler.getTriggerKeys(tkMatcher));
        List<JobKey> jobKeyList = new ArrayList<>(scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroup)));
        scheduler.pauseTriggers(tkMatcher);
        scheduler.unscheduleJobs(triggerKeyList);
        scheduler.deleteJobs(jobKeyList);
    }

    private static SimpleScheduleBuilder simpleBuilder(JobObject jobObject){
        SimpleScheduleBuilder ssBuilder = SimpleScheduleBuilder.simpleSchedule();
        if (jobObject.getInterval() != null && jobObject.getInterval() > 0){
            ssBuilder.withIntervalInSeconds(jobObject.getInterval());
        }
        if (jobObject.getRepeat() != null){
            if (jobObject.getRepeat() > 1){
                ssBuilder = ssBuilder.withRepeatCount(jobObject.getRepeat() - 1);
            }
        }else {
            ssBuilder = ssBuilder.repeatForever();
        }
        return ssBuilder;
    }

    private static TriggerBuilder setTriggerBuilderTime(TriggerBuilder triggerBuilder, Date startTime, Date endTime){
        Date nowTime = new Date();
        boolean startNowEnd = startTime.before(nowTime) && nowTime.before(endTime);
        boolean nowStartEnd = nowTime.before(startTime) && startTime.before(endTime);
        if (startTime != null && endTime != null){
            // 时间线：开始-》现在-》结束
            if (startNowEnd){
                triggerBuilder.startNow();
            }
            //时间线：现在-》开始-》结束
            if (nowStartEnd){
                triggerBuilder.startAt(startTime);
            }
            triggerBuilder.endAt(endTime);
        }
        if (startTime != null && endTime == null){
            if (nowTime.before(startTime)){
                triggerBuilder.startAt(startTime);
            }
            if (startTime.before(nowTime)){
                triggerBuilder.startNow();
            }
        }
        if (startTime == null && endTime != null){
            if (nowTime.before(endTime)){
                triggerBuilder.startNow();
                triggerBuilder.endAt(endTime);
            }
        }
        if (startTime == null && endTime == null){
            triggerBuilder.startNow();
        }
        return triggerBuilder;
    }
}
