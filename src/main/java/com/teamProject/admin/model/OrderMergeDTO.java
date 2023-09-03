package com.teamProject.admin.model;

import java.util.List;

public class OrderMergeDTO {
    private OrderInfoDTO orderInfoDTO;
    private List<OrderDataDTO> list;

    public OrderInfoDTO getOrderInfoDTO() {
        return orderInfoDTO;
    }

    public void setOrderInfoDTO(OrderInfoDTO orderInfoDTO) {
        this.orderInfoDTO = orderInfoDTO;
    }

    public List<OrderDataDTO> getList() {
        return list;
    }

    public void setList(List<OrderDataDTO> list) {
        this.list = list;
    }
}
