package com.cloud.emr.Affair.Treatment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentResponse {
    private Long treatmentId;
    private Long checkInId;
    private Date treatmentDate;
    private String treatmentStatus;
    private Date treatmentNextDate;
    private String treatmentComment;
    private String treatmentDept;
    private String treatmentDoc;
    private Long treatmentTotalFee;


}
