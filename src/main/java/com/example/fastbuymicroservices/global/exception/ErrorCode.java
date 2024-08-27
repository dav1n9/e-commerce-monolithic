package com.example.fastbuymicroservices.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    ITEM_NOT_FOUND(404, "상품을 찾을 수 없습니다."),
    ORDER_NOT_FOUND(404, "주문을 찾을 수 없습니다."),
    WISHLIST_NOT_FOUND(404, "위시리스트를 찾을 수 없습니다."),
    WISHLIST_ITEM_NOT_FOUND(404, "위시리스트 상품을 찾을 수 없습니다."),
    PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다."),
    ORDER_NOT_DELIVERED(400, "배송 완료된 주문이 아닙니다."),
    RETURN_PERIOD_EXPIRED(400, "반품 신청 기한이 아닙니다."),
    ORDER_CANCELLATION_NOT_ALLOWED(400, "주문 취소가 불가능 합니다."),
    DUPLICATE_EMAIL(400, "이미 가입된 이메일 입니다."),
    NOT_USER_ORDER(403, "해당 유저의 주문이 아닙니다."),
    MAIL_SEND_FAILURE (500, "메일 발송에 실패했습니다."),
    INVALID_VERIFICATION_CODE (400, "인증번호가 틀렸습니다.");

    private final int status;
    private final String message;
}