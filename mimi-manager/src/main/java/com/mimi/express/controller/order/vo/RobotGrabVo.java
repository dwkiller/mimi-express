package com.mimi.express.controller.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class RobotGrabVo {
    private String school;
    private List<GrabOrderInVo> data;
}
