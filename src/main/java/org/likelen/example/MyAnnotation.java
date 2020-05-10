package org.likelen.example;

import java.lang.annotation.*;

/**
 * 클래스영역까지는 데이터가 남지만, 바이트 코드를 로딩했을 때 애노테이션 정보는 빼고 가져온다.
 * 그러므로. 런타임까지 같이 가져오고 싶다면.
 */
//@Retention(RetentionPolicy.CLASS)
@Retention(RetentionPolicy.RUNTIME) // -- 1
@Target({ElementType.FIELD, ElementType.TYPE}) // -- 2
@Inherited // -- 3
public @interface MyAnnotation {

    //값을 가질 수 있는데, 제약적으로 가질 수 있음.
    String name() default "len"; // -- 4
    int number() default 100;
    int value() default 100;
}
