package com.kaveloper.portfolio.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 해당 어노테이션은 파라미터에
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 남아있는다
public @interface LoginMember {
}

