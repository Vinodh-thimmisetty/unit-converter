package com.ayoholdings.service;

import com.ayoholdings.exception.InvalidCategoryException;
import com.ayoholdings.exception.InvalidConversionException;
import com.ayoholdings.model.ConversionCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
class ConversionServiceImplTest {

    @InjectMocks
    private ConversionServiceImpl conversionService;


    @Test
    void availableCategories() {
        final Map<String, ConversionCategory> categories = this.conversionService.availableCategories().getCategories();
        assertEquals(3, categories.size()); // Temperature + Mass + Length
        assertTrue(categories.keySet().containsAll(Arrays.asList("Temperature", "Mass", "Length")));
    }

    @Test
    void conversions() {

        assertEquals(10936.1, this.conversionService.convert("Length", "KiloMeter", "yard", 10.0), 0.1);
        assertEquals(393701, this.conversionService.convert("Length", "KiloMeter", "Inch", 10.0), 0.1);

        assertEquals(50, this.conversionService.convert("Temperature", "Celsius", "Fahrenheit", 10.0), 0.1);
        assertEquals(283.15, this.conversionService.convert("Temperature", "Celsius", "Kelvin", 10.0), 0.1);

        assertEquals(22.0462, this.conversionService.convert("Mass", "Kilogram", "Pound", 10.0), 0.1);
        assertEquals(352.74, this.conversionService.convert("Mass", "Kilogram", "Ounce", 10.0), 0.1);

    }

    @Test
    void exceptional_cases() {
        Assertions.assertThrows(InvalidCategoryException.class, () -> this.conversionService.convert("DUmmy", "Kilogram", "tonne", 10.0));
        Assertions.assertThrows(InvalidConversionException.class, () -> this.conversionService.convert("Mass", "DUmmy", "Pound", 10.0));
    }

}