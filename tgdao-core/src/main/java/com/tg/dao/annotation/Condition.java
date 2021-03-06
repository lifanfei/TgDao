package com.tg.dao.annotation;

import com.tg.dao.constant.Attach;
import com.tg.dao.constant.Criterions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by twogoods on 2017/7/28.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
public @interface Condition {
    Criterions criterion() default Criterions.EQUAL;

    String column() default "";

    Attach attach() default Attach.AND;

    //selective 下参数值得判断条件 即  <if test="username != null">username = #{username}</if>
    String test() default "";
}
