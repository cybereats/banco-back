package cybereats.fpmislata.com.banco_back.domain.validation;

import java.util.Set;

import cybereats.fpmislata.com.banco_back.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class DtoValidator {

    private static Validator validator;

    private static Validator getValidator() {
        if (validator == null) {
            ValidatorFactory factory = Validation.byDefaultProvider()
                    .configure()
                    .buildValidatorFactory();
            validator = factory.getValidator();
        }
        return validator;
    }

    public static <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = getValidator().validate(dto);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
    }
}