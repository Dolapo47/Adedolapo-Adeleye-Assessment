package com.zenpay.paymentgateway.util;

import com.zenpay.paymentgateway.response.GatewayResponse;
import com.zenpay.paymentgateway.enums.TransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Helper {

    public ResponseEntity<GatewayResponse> getGatewayResponseResponseEntity(GatewayResponse response) {
        if (response.getCode().equals("00")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(response.getCode().equals("90")){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public boolean validateTransactionStatus(String status) {
        if(status.equalsIgnoreCase(TransactionStatus.PENDING.toString())){
            return true;
        }else if(status.equalsIgnoreCase(TransactionStatus.COMPLETED.toString())){
            return true;
        }else if(status.equalsIgnoreCase(TransactionStatus.FAILED.toString())){
            return true;
        }else if(status.equalsIgnoreCase(TransactionStatus.CANCELLED.toString())){
            return true;
        }
        else{
            return false;
        }
    }
}
