package com.cloud.emr.Main.User.service;

import com.cloud.emr.Main.User.dto.UserRegisterRequest;
import com.cloud.emr.Main.User.entity.UserEntity;
import com.cloud.emr.Main.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 이 어노테이션이 누락되면 빈으로 등록되지 않음
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 사용자 조회 메서드
    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null); // 유저가 없으면 null 반환
    }

    /**   중복검증 추가 및 Javadoc 추가해드렸습니다. +최경태
     * 회원가입
     * @author : 전재윤
     * @param userRegisterRequest : 등록 dto
     * @exception IllegalArgumentException : 중복검증
     * @return : UserEntity
     */
    public UserEntity registerUser(UserRegisterRequest userRegisterRequest) {

        // 중복 검증: 이메일이나 사용자 계정이 이미 존재하는지 확인
        if (userRepository.existsByUserLoginId(userRegisterRequest.getUserLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 계정입니다.");
        }

        // DTO에서 Entity로 변환
        UserEntity userEntity = userRegisterRequest.toUserEntity();

        // 데이터베이스에 저장
        return userRepository.save(userEntity);
    }
}
