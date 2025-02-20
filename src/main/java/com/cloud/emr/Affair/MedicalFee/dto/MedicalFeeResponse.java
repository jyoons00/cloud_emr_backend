package com.cloud.emr.Affair.MedicalFee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalFeeResponse {
    private Long medicalFeeId;
    private Long medicalTypeId;
    private Long treatmentId;

}
