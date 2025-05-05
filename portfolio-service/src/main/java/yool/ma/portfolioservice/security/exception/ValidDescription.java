package yool.ma.portfolioservice.security.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MeaningfulTextValidator.class)
public @interface ValidDescription {
    String message() default "Description must contain meaningful text";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
