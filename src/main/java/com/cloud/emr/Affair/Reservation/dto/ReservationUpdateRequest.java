package com.cloud.emr.Affair.Reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
// 환자 예약 변경
public class ReservationUpdateRequest {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime newReservationDate;  // 새 예약 날짜

    private String reservationYn;              // 예약 여부 (Y, N)
    private String reservationChangeCause; // 예약 변경 사유

}

