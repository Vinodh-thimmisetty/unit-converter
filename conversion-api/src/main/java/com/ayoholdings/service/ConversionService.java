package com.ayoholdings.service;

import com.ayoholdings.model.ConversionDTO;

public interface ConversionService {
    ConversionDTO availableCategories();

    Double convert(String category, String from, String to, Double value);
}
