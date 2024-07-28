package com.zenpay.paymentgateway.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayResponse {
    private String code;
    private String message;
    private Object data;
}
