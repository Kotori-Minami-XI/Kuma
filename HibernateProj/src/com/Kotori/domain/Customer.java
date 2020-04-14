package com.Kotori.domain;

import lombok.Getter;
        import lombok.Setter;

@Getter @Setter
public class Customer {
    private Long cust_id;
    private String cust_name;
    private String cust_source;
    private String cust_industry;
    private String cust_level;
    private String cust_phone;
    private String cust_mobile;
}
