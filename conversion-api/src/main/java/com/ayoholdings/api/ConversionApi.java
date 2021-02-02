package com.ayoholdings.api;

import com.ayoholdings.model.ConversionDTO;
import com.ayoholdings.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConversionApi {

    @Autowired
    public ConversionService conversionService;


    @GetMapping("/categories")
    public ResponseEntity<ConversionDTO> loadConversionCategories() {
        return ResponseEntity.ok(conversionService.availableCategories());
    }

    @GetMapping("/convert/{category}/{from}/{to}/{value}")
    public ResponseEntity<Double> conversion(@PathVariable String category,
                                             @PathVariable String from,
                                             @PathVariable String to,
                                             @PathVariable Double value) {
        return ResponseEntity.ok(conversionService.convert(category, from, to, value));
    }


}
