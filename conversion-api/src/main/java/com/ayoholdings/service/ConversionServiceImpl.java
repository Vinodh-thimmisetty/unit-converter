package com.ayoholdings.service;

import com.ayoholdings.exception.InvalidCategoryException;
import com.ayoholdings.exception.InvalidConversionException;
import com.ayoholdings.model.ConversionCategory;
import com.ayoholdings.model.ConversionDTO;
import com.ayoholdings.util.ConversionUtil;
import org.springframework.stereotype.Service;

@Service
public class ConversionServiceImpl implements ConversionService {


    @Override
    public ConversionDTO availableCategories() {
        return ConversionUtil.availableCategories();
    }

    @Override
    public Double convert(String category, String from, String to, Double value) {
        final ConversionCategory conversionCategory = ConversionUtil.availableCategories()
                .getCategories()
                .get(category);
        if (conversionCategory == null) {
            throw new InvalidCategoryException("Invalid category - " + category);
        }
        return conversionCategory
                .getConversions().stream()
                .filter(conv -> conv.getFrom().getName().equalsIgnoreCase(from) &&
                        conv.getTo().getName().equalsIgnoreCase(to))
                .findFirst().orElseThrow(() -> new InvalidConversionException("Invalid Conversion Types: FROM - " + from + " TO - " + to))
                .getConverter().apply(value);
    }
}
