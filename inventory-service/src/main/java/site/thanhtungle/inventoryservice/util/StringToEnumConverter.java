package site.thanhtungle.inventoryservice.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import site.thanhtungle.commons.constant.enums.EFilterOperators;

@Component
public class StringToEnumConverter implements Converter<String, EFilterOperators> {

    /**
     * Custom converter to use lowercase Enums on request parameters
     *
     * @param source {String}
     * @return EFilterOperators
    * */
    @Override
    public EFilterOperators convert(String source) {
        return source.isEmpty() ? null : EFilterOperators.valueOf(source.trim().toUpperCase());
    }
}
