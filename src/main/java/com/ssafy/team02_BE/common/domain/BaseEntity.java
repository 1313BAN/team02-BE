package com.ssafy.team02_BE.common.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
//JPA 어노테이션 - 엔티티 클래스들의 공통 부모 클래스로 사용 (DB 테이블 생성되지 않음)
@MappedSuperclass
//JPA Entity에서 이벤트가 발생할 때마다 특정 로직을 실행시켜주는 역할
//엔티티가 저장되거나 업데이트될 때 자동으로 @CreatedDate, @LastModifiedDate가 붙은 필드에 값을 채워줌
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}