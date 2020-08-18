package com.tcl.tcloud.base.appschedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName DemandOperationDto
 * @Description TODO
 * @Author xiaosheng1.li
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "运营类消息类")
public class DemandOperationDto extends DemandDto{
    @Schema(description = "消息类型", example = "")
    private String messageType;
    @Schema(description = "开启状态：0：关闭， 1：开启，2：暂停, 数据初始化时均为：0 关闭状态")
    private Integer isOpen;
    @Schema(description = "优先级， 1~10, 1级最高，数据初始化时，默认为 5")
    private Integer priorityLevel;
}
