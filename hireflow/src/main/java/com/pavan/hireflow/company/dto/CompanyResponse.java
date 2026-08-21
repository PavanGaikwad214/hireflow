package com.pavan.hireflow.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyResponse {

    private Long id;

    private String name;

    private String description;

    private String website;

    private String logoUrl;

    private String location;
}
