package com.cloud.emr.Affair.Contract.controller;

import com.cloud.emr.Affair.Contract.dto.ContractRequest;
import com.cloud.emr.Affair.Contract.dto.ContractResponse;
import com.cloud.emr.Affair.Contract.entity.ContractEntity;
import com.cloud.emr.Affair.Contract.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contract")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }
    //계약처 관계 CRUD

    //모든 계약처 조회
    @GetMapping("/list")
    public ResponseEntity<Object> getAllContracts() {
        List<ContractResponse> contractResponseList = contractService.getAllContracts();

        if(contractResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
                    "message", "계약처가 없습니다."
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message","모든 계약처 조회 성공",
                "data", contractResponseList
        ));
    }

    //계약처 생성
    @PostMapping("/create")
    public ResponseEntity<Object> createContract(@RequestBody ContractRequest contractRequest) {
        try {
            ContractEntity existContract = contractService.findById(contractRequest.getContractCode());

            if(existContract != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "message","이미 존재하는 계약처 코드입니다.",
                        "data", existContract
                ));
            }

            ContractResponse contractResponse = contractService.createContract(contractRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message","계약처 생성 성공",
                    "data", contractResponse
            ));



        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "계약처 생성 실패",
                    "error", e.getMessage()
            ));
        }
    }

    //계약처 정보 수정
    @PostMapping("/update")
    public ResponseEntity<Object> updateContract(@RequestBody ContractRequest contractRequest, @RequestParam Long contractId) {
        try {
            ContractEntity contractEntity = contractService.findById(contractId);
            if (contractEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "message", "해당 코드를 찾을 수 없습니다",
                        "data", contractId
                ));
            }

            ContractResponse response = contractService.updateContract(contractId, contractRequest);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message" , "계약처 정보 수정 성공",
                    "data", response
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message","수정 작업 중 오류 발생",
                    "error", e.getMessage()
            ));
        }
    }

    //계약처 선택 삭제
    @PostMapping("delete")
    public ResponseEntity<Object> deleteContract(@RequestParam Long contractId) {
        try {
            ContractEntity contractEntity = contractService.findById(contractId);
            if (contractEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "message","존재하지 않는 계약처 코드입니다.",
                        "data", contractId
                ));
            }

            ContractResponse response = contractService.deleteById(contractId);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
               "message","삭제 성공",
               "data", response
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message","삭제 작업 중 오류 발생",
                    "error", e.getMessage()
            ));

        }
    }
}
