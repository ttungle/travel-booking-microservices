package site.thanhtungle.commons.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@AllArgsConstructor
public class DateValidatorImpl implements DateValidator {

    @Override
    public boolean isValid(String dateString) {
        try {
            Instant.parse(dateString);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
