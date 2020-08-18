package com.tcl.tcloud.base.appschedule.service;

import com.tcl.tcloud.base.appschedule.dto.DemandDto;
import com.tcl.tcloud.base.appschedule.utils.StatusResult;

/**
 * @ClassName ScheduleService
 * @Description TODO
 * @Author xiaosheng1.li
 **/
public interface ScheduleService {
    /**
     * 功能描述: 添加定时任务，支持单需求多定时表达式任务
     *
     * @param demandDto
     * @return : com.tcl.tcloud.base.appschedule.utils.StatusResult
     * @author : xiaosheng1.li
     * @date : 2020/8/17 16:24
     **/
    StatusResult addDemandSchedule(DemandDto demandDto);

    /**
     * 功能描述: 暂停定时任务，按组暂停,同一需求对应同一组
     *
     * @param demandUuid
     * @return : com.tcl.tcloud.base.appschedule.utils.StatusResult
     * @author : xiaosheng1.li
     * @date : 2020/8/17 16:25
     **/
    StatusResult pauseDemandSchedule(String demandUuid);

    /**
     * 功能描述: 针对于暂停状态进行重启任务
     *
     * @param demandUuid
     * @return : com.tcl.tcloud.base.appschedule.utils.StatusResult
     * @author : xiaosheng1.li
     * @date : 2020/8/17 16:26
     **/
    StatusResult resumeDemandSchedule(String demandUuid);

    /**
     * 功能描述: 移除定时任务
     *
     * @param demandUuid
     * @return : com.tcl.tcloud.base.appschedule.utils.StatusResult
     * @author : xiaosheng1.li
     * @date : 2020/8/17 16:26
     **/
    StatusResult removeDemandSchedule(String demandUuid);

    public void test();
}
