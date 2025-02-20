package com.cloud.emr.Affair.MedicalFee.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "medical_type")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_type_id", nullable = false, columnDefinition = "INT")
    private Long medicalTypeId;

    @Column(name = "medical_type_name", nullable = false)
    private String medicalTypeName;

    @Column(name = "medical_type_fee", nullable = false, columnDefinition = "INT")
    private Long medicalTypeFee;
}
