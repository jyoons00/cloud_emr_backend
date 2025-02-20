package com.cloud.emr.Affair.Qualification.controller;

import com.cloud.emr.Affair.Patient.service.PatientService;
import com.cloud.emr.Affair.Qualification.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/qualification")
public class QualificationController {

    private QualificationService qualificationService;
    private PatientService patientService;

    @Autowired
    public QualificationController(PatientService patientService, QualificationService qualificationService) {
        this.patientService = patientService;
        this.qualificationService = qualificationService;
    }

    //환자의 주민번호를 바탕으로 MockAPI - 건강보험 자격 조회
    @PostMapping("/health-insurance")
    public Mono<ResponseEntity<Map<String, Object>>> getPatientHealthInsurance(@RequestParam("patientNo") Long patientNo) {
        try {
            String targetPatientRrn = patientService.findPatientRrnByPatientNo(patientNo);
            return qualificationService.getHealthInsuranceInfo(targetPatientRrn)
                    .map(insuranceInfo -> ResponseEntity.ok(Map.of(
                            "message", "조회 성공",
                            "patientNo", patientNo,
                            "insuranceInfo", insuranceInfo
                    )))
                    .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            Map.of(
                                    "error", "보험정보가 없습니다."
                            )
                    ));
        } catch (IllegalArgumentException e) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of(
                            "error", e.getMessage()
                    )
            ));
        }
    }

    //환자의 주민번호를 바탕으로 MockAPI - 의료 급여 자격 조회
    @PostMapping("/medical-assistance")
    public Mono<ResponseEntity<Map<String, Object>>> getPatientMedicalAssistance(@RequestParam("patientNo") Long patientNo) {
        try {
            String targetPatientRrn = patientService.findPatientRrnByPatientNo(patientNo);
            return qualificationService.getMedicalAssistanceInfo(targetPatientRrn)
                    .map(insuranceInfo -> ResponseEntity.ok(Map.of(
                            "message", "조회 성공",
                            "patientNo", patientNo,
                            "insuranceInfo", insuranceInfo
                    )))
                    .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            Map.of(
                                    "error", "보험정보가 없습니다."
                            )
                    ));
        } catch (IllegalArgumentException e) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of(
                            "error", e.getMessage()
                    )
            ));
        }
    }

    //기초 생활 수급 자격 확인
    @PostMapping("/basic-livelihood")
    public Mono<ResponseEntity<Map<String, Object>>> getBasicLivelihood(@RequestParam("patientNo") Long patientNo) {
        try {
            String targetPatientRrn = patientService.findPatientRrnByPatientNo(patientNo);
            return qualificationService.getBasicLivelihoodInfo(targetPatientRrn)
                    .map(insuranceInfo -> ResponseEntity.ok(Map.of(
                            "message", "조회 성공",
                            "patientNo", patientNo,
                            "insuranceInfo", insuranceInfo
                    )))
                    .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            Map.of(
                                    "error", "보험정보가 없습니다."
                            )
                    ));
        } catch (IllegalArgumentException e) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of(
                            "error", e.getMessage()
                    )
            ));
        }
    }
}
