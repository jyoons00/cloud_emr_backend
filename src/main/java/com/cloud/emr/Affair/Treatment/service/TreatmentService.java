package com.cloud.emr.Affair.Treatment.service;


import com.cloud.emr.Affair.CheckIn.entity.CheckInEntity;
import com.cloud.emr.Affair.Treatment.dto.TreatmentRequest;
import com.cloud.emr.Affair.Treatment.entity.TreatmentEntity;
import com.cloud.emr.Affair.Treatment.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

//    public TreatmentService( TreatmentRepository treatmentRepository) {
//        this.treatmentRepository = treatmentRepository;
//    }

    public TreatmentEntity createTreatment(TreatmentRequest treatmentRequest, CheckInEntity checkInEntity) {

        // TreatmentEntity 객체 생성
        TreatmentEntity treatmentEntity = treatmentRequest.toTreatmentEntity(checkInEntity);

        // TreatmentEntity 저장
        return treatmentRepository.save(treatmentEntity);
    }

    public TreatmentEntity findTreatmentById(Long treatmentId) {
        return treatmentRepository.findById(treatmentId).orElse(null);
    }


}