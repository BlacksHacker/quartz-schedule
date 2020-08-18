package com.tcl.tcloud.base.appschedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName Demand
 * @Description TODO
 * @Author xiaosheng1.li
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "需求参数模型")
public class DemandDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    @Schema(description = "业务方标识列", example = "")
    private String business;
    @Schema(description = "需求类型，0：运营类， 1：产品功能类", required = true, example = "")
    private int demandGroup;
    @Schema(description = "消息主题", required = true, example = "")
    private String title;
    @Schema(description = "消息内容", required = true, example = "")
    private String content;
    @Schema(description = "跳转地址", required = true, example = "")
    private String redirectUrl;
    @Schema(description = "用户标签列表，接收字符串集合", required = true, example = "{\"new\",\"old\"}")
    private List<String> customTagList;
    @Schema(description = "推送起始时间", required = true, example = "")
    private String startTime;
    @Schema(description = "推送结束时间", required = true, example = "")
    private String endTime;
    @Schema(description = "消息推送类型列表", required = true, example = "{\"\",\"\"}")
    private List<String> pushTypeList;
    @Schema(description = "图片地址", required = true, example = "")
    private String imageUrl;
    @Schema(description = "定时表达式列表，接受字符串集合", required = true, example = "")
    private List<String> cronList;
    private String fcd;
    private String fcu;
}
