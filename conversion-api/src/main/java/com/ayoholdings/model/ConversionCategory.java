package com.ayoholdings.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConversionCategory {
    private List<Measurement> metric;
    private List<Measurement> imperial;
    @JsonIgnore
    private List<ConversionFactor> conversions;

}