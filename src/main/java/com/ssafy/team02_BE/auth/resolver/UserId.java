package com.ssafy.team02_BE.auth.resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //어노테이션이 사용될 위치를 parameter로 지정
@Retention(RetentionPolicy.RUNTIME) //컴파일 이후 런타임 동안 참조 가능 -> 어노테이션이 유효
public @interface UserId { //@userId 생성
}
