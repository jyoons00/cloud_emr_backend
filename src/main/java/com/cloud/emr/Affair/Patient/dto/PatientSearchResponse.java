package com.cloud.emr.Affair.Patient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatientSearchResponse {
    private String patientId;
    private String patientName;
}
