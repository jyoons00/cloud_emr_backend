package com.cloud.emr.Affair.Treatment.dto;

import com.cloud.emr.Affair.CheckIn.entity.CheckInEntity;
import com.cloud.emr.Affair.Treatment.entity.TreatmentEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Calendar;
import java.util.Date;

@Getter
public class TreatmentRequest {
    @NotBlank(message = "접수ID는 필수입니다.")
    @Column(nullable = false)
    private Long checkInId;

    //아직 접수만 받고, 진료 전인 경우 진료비는 등록되지 않았을 경우가 있음
    //그래서 nullable 빼두었습니다.
//    private Long treatmentFeeId;
    private Date treatmentNextDate;
    private String treatmentComment;

    private String treatmentStatus;
    private String treatmentDept;
    private String treatmentDoc;

    private Date getMaxDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(9999, Calendar.DECEMBER, 31);
        return cal.getTime();
    }



    //TreatmentEntity로 변환시킴
    public TreatmentEntity toTreatmentEntity(CheckInEntity checkInEntity) {
        return TreatmentEntity.builder()
                .checkInEntity(checkInEntity)
                .treatmentNextDate((this.treatmentNextDate == null) ? getMaxDate() : this.treatmentNextDate)
                .treatmentComment((this.treatmentComment == null) ? "" : this.treatmentComment)
                .treatmentStatus((this.treatmentStatus == null || this.treatmentStatus.isBlank()) ? "진료전" : this.treatmentStatus)
                .treatmentDept((this.treatmentDept == null || this.treatmentDept.isBlank()) ? "배정되지 않음" : this.treatmentDept)
                .treatmentDoc((this.treatmentDoc == null || this.treatmentDoc.isBlank()) ? "배정되지 않음" : this.treatmentDoc)
                .build();
    }
}


// treatmentStatus는 접수된 직후 진료로 가져온 경우, 무조건 진료전이기 때문에 이렇게 처리했습니다.
// treatmentDept, treatmentDoc는 접수된 직후, 바로 배정이 되지 않기 때문에 이렇게 처리했습니다.