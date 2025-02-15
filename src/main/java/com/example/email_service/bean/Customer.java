package com.example.email_service.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String companyName;
    private String contactName;
    private String address;
    private String email;
    private String phone;
}
