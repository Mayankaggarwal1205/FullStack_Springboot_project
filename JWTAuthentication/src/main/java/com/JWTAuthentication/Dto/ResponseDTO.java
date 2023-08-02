package com.JWTAuthentication.Dto;

import com.JWTAuthentication.entity.Customer;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDTO {

    private Integer responseCode;
    private String responseMessage;



}
