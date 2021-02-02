package com.ayoholdings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.UnaryOperator;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConversionFactor {
    private Measurement from;
    private Measurement to;
    private UnaryOperator<Double> converter;
}