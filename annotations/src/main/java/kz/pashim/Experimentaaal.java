package kz.pashim;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Experimentaaal {
    String arg1();
    int arg2() default 3;
    boolean arg3() default true;
    Class<?> arg4();
}
