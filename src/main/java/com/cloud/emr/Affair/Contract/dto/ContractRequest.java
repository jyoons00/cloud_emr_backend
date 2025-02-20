package com.cloud.emr.Affair.Contract.dto;

import lombok.Getter;

@Getter
public class ContractRequest {
    private Long contractCode;
    private String contractName;
    private String contractRelationship;
    private String contractTelephone;
    private Long contractDiscount; // 50%이면 50, 20%이면 20
}
