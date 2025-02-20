package com.cloud.emr.Affair.Contract.service;

import com.cloud.emr.Affair.Contract.dto.ContractRequest;
import com.cloud.emr.Affair.Contract.dto.ContractResponse;
import com.cloud.emr.Affair.Contract.entity.ContractEntity;
import com.cloud.emr.Affair.Contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;


    public List<ContractResponse> getAllContracts() {
        List<ContractEntity> contractEntities = contractRepository.findAll();

        return contractEntities.stream().map(contractEntity -> {
            ContractResponse contractResponse = new ContractResponse(
                    contractEntity.getContractCode(),
                    contractEntity.getContractName(),
                    contractEntity.getContractRelationship(),
                    contractEntity.getContractTelephone(),
                    contractEntity.getContractDiscount()
            );

            return contractResponse;
        }).collect(Collectors.toList());
    }

    public ContractEntity findById(Long contractCode) {
        return contractRepository.findById(contractCode).orElse(null);
    }

    public ContractResponse createContract(ContractRequest contractRequest) {
        ContractEntity contractEntity = ContractEntity.builder()
                .contractCode(contractRequest.getContractCode())
                .contractName(contractRequest.getContractName())
                .contractRelationship(contractRequest.getContractRelationship())
                .contractTelephone(contractRequest.getContractTelephone())
                .contractDiscount(contractRequest.getContractDiscount())
                .build();

        contractEntity = contractRepository.save(contractEntity);

        return new ContractResponse(
                contractEntity.getContractCode(),
                contractEntity.getContractName(),
                contractEntity.getContractRelationship(),
                contractEntity.getContractTelephone(),
                contractEntity.getContractDiscount()
        );
    }

    public ContractResponse updateContract(Long contractId, ContractRequest contractRequest) {
        ContractEntity contractEntity = contractRepository.findById(contractId).orElseThrow(() ->
                new IllegalArgumentException("Contract not found"));

        //업데이트 할 항목이 공백인 경우 원본 그대로 넘기기
        ContractEntity updateContractEntity = ContractEntity.builder()
                .contractCode(
                        contractRequest.getContractCode() != null ? contractRequest.getContractCode() : contractEntity.getContractCode()
                )
                .contractName(
                        contractRequest.getContractName() != null ? contractRequest.getContractName() : contractEntity.getContractName()
                )
                .contractRelationship(
                        contractRequest.getContractRelationship() != null ? contractRequest.getContractRelationship() : contractEntity.getContractRelationship()
                )
                .contractTelephone(
                        contractRequest.getContractTelephone() != null ? contractRequest.getContractTelephone() : contractEntity.getContractRelationship()
                )
                .contractDiscount(
                        contractRequest.getContractDiscount() != null ? contractRequest.getContractDiscount() : contractEntity.getContractDiscount()
                )
                .build();

        contractEntity = contractRepository.save(updateContractEntity);
        //수정 후 기존 정보는 폐기
        contractRepository.deleteById(contractId);

        return new ContractResponse(
                contractEntity.getContractCode(),
                contractEntity.getContractName(),
                contractEntity.getContractRelationship(),
                contractEntity.getContractTelephone(),
                contractEntity.getContractDiscount()
        );
    }

    public ContractResponse deleteById(Long contractId) {
        ContractEntity contractEntity = contractRepository.findById(contractId).orElseThrow(() -> new IllegalArgumentException("Contract not found"));

        contractRepository.deleteById(contractId);

        return new ContractResponse(
                contractEntity.getContractCode(),
                contractEntity.getContractName(),
                contractEntity.getContractRelationship(),
                contractEntity.getContractTelephone(),
                contractEntity.getContractDiscount()
        );


    }
}
