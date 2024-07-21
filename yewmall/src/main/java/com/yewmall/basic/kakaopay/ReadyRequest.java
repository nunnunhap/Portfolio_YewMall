package com.yewmall.basic.kakaopay;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // Builder Pattern 매개변수 생성자를 알아서 만들어줌.
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReadyRequest {
    private String cid;
    private String partnerOrderId;
    private String partnerUserId;
    private String itemName;
    private Integer quantity;
    private Integer totalAmount;
    private Integer taxFreeAmount;
    private String approvalUrl;
    private String cancelUrl;
    private String failUrl;
}
