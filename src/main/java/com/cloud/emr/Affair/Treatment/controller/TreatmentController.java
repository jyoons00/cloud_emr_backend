package com.cloud.emr.Affair.Treatment.controller;


import com.cloud.emr.Affair.CheckIn.entity.CheckInEntity;
import com.cloud.emr.Affair.CheckIn.service.CheckInService;
import com.cloud.emr.Affair.MedicalFee.service.MedicalFeeService;
import com.cloud.emr.Affair.Treatment.dto.TreatmentResponse;
import com.cloud.emr.Affair.Treatment.dto.TreatmentRequest;
import com.cloud.emr.Affair.Treatment.entity.TreatmentEntity;
import com.cloud.emr.Affair.Treatment.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/treatment")
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;
    @Autowired
    private CheckInService checkInService;
    @Autowired
    private MedicalFeeService medicalFeeService;


    //진료 조회 (진료비 조회까지)
    // 진료비 조회는 되는데, 추후에 진료 유형까지 나오도록하겠습니다.
    @GetMapping("/search")
    public ResponseEntity<Object> searchTreatment(@RequestParam Long treatmentId) {
        TreatmentResponse treatmentResponse = medicalFeeService.updateTotalFeeForTreatment(treatmentId);

        if(treatmentResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "진료를 찾을 수 없습니다."
            ));
        }

        return ResponseEntity.ok(Map.of(
                "message", "조회 성공",
                "data", treatmentResponse
        ));

    }


    //진료 등록 (진료비 테이블 조인 전)
    @PostMapping("/register")
    public ResponseEntity<Object> registerTreatment(@RequestBody TreatmentRequest treatmentRequest, @RequestParam Long checkInId) {
        try {
            //접수 번호에 맞는 접수 찾기
            CheckInEntity checkInEntity = checkInService.findCheckInById(checkInId);

            if (checkInEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "message", "접수번호가 존재하지 않습니다."
                ));
            }


            TreatmentEntity newTreatment = treatmentService.createTreatment(treatmentRequest, checkInEntity);

            Map<String, Object> responseData = Map.of(
                    "treatmentId", newTreatment.getTreatmentId(),
                    "checkInId", newTreatment.getCheckInEntity().getCheckInId(),
                    "treatmentDate", newTreatment.getTreatmentDate(),
                    "treatmentStatus", newTreatment.getTreatmentStatus(),
                    "treatmentNextDate", newTreatment.getTreatmentNextDate(),
                    "treatmentComment", newTreatment.getTreatmentComment(),
                    "treatmentDept", newTreatment.getTreatmentDept(),
                    "treatmentDoc", newTreatment.getTreatmentDoc()
            );


            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message","진료 등록 성공",
                        "data", responseData
            ));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "진료 등록 실패",
                    "data", e.getMessage()
            ));
        }
    }

}
