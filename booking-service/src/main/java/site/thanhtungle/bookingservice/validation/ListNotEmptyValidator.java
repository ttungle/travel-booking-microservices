package site.thanhtungle.bookingservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class ListNotEmptyValidator implements ConstraintValidator<ListNotEmpty, Set<Long>> {

    @Override
    public void initialize(ListNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(Set<Long> field, ConstraintValidatorContext constraintValidatorContext) {
        return field != null && !field.isEmpty();
    }
}
