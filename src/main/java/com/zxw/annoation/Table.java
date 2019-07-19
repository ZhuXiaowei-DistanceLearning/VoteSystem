package com.zxw.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 注释在类上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface Table {
	String value();
}
