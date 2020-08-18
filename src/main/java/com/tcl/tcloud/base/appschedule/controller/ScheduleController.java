package com.tcl.tcloud.base.appschedule.controller;

import com.alibaba.fastjson.JSON;
import com.tcl.tcloud.base.appschedule.dto.DemandDto;
import com.tcl.tcloud.base.appschedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ScheduleController
 * @Description TODO
 * @Author xiaosheng1.li
 **/
@RestController
@Tag(name = "schedule", description = "定时任务交互接口")
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Operation(summary = "定时作业任务添加接口",
    description = "根据需求信息，添加对应的定时任务")
    @PostMapping("/demandJob/add")
    public String addDemandJob(@RequestBody DemandDto demandDto){
        log.info("add demand job:{}", JSON.toJSON(demandDto));
        return JSON.toJSONString(scheduleService.addDemandSchedule(demandDto));
    }

    @Operation(summary = "定时作业任务暂停接口",
    description = "根据需求Uuid,暂停需求对应的定时任务",
    parameters = {
            @Parameter(name = "demandUuid", description = "需求主键uuid", required = true)
    })
    @GetMapping("/demandJob/pause")
    public String pauseDemandJob(@RequestParam String demandUuid){
        log.info("pause demand job, demandUuid:{}", demandUuid);
        return JSON.toJSONString(scheduleService.pauseDemandSchedule(demandUuid));
    }

    @Operation(summary = "定时作业任务恢复接口",
    description = "根据需求Uuid,恢复之前暂停的需求对应的定时任务",
    parameters = {
            @Parameter(name = "demandUuid", description = "需求主键uuid", required = true)
    })
    @GetMapping("/demandJob/resume")
    public String resumeDemandJob(@RequestParam String demandUuid){
        log.info("resume demand job, demandUuid:{}", demandUuid);
        return JSON.toJSONString(scheduleService.resumeDemandSchedule(demandUuid));
    }

    @Operation( summary = "定时作业任务移除接口",
    description = "根据需求uuid,暂停并删除需求对应的定时任务",
    parameters = {
            @Parameter(name = "demandUuid", description = "需求主键uuid", required = true)
    })
    @GetMapping("/demandJob/remove")
    public String removeDemandJob(@RequestParam String demandUuid){
        log.info("remove demand job, demandUuid:{}", demandUuid);
        return JSON.toJSONString(scheduleService.removeDemandSchedule(demandUuid));
    }

    @GetMapping("/test")
    public void test(){
        scheduleService.test();
    }
}
