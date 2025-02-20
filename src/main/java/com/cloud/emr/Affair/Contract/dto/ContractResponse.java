package com.cloud.emr.Affair.Contract.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponse {
    private Long contractCode;
    private String contractName;
    private String contractRelationship;
    private String contractTelephone;
    private Long contractDiscount;
}
