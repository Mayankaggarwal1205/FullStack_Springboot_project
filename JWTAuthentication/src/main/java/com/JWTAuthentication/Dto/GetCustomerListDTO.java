package com.JWTAuthentication.Dto;

import com.JWTAuthentication.entity.Customer;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class GetCustomerListDTO {

    private Integer responseCode;
    private List<Customer> responseMessage;
}
