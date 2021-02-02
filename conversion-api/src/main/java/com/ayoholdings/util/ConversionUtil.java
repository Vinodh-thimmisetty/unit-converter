package com.ayoholdings.util;

import com.ayoholdings.model.ConversionCategory;
import com.ayoholdings.model.ConversionDTO;
import com.ayoholdings.model.ConversionFactor;
import com.ayoholdings.model.Measurement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConversionUtil {

    private ConversionUtil() {
    }

    public static ConversionDTO availableCategories() {

        Map<String, ConversionCategory> categories = new HashMap<>();
        categories.put("Length", lengthCategory());
        categories.put("Mass", massCategory());
        categories.put("Temperature", temperatureCategory());

        return ConversionDTO.builder()
                .categories(categories)
                .build();
    }

    private static ConversionCategory massCategory() {

        final Measurement kg = Measurement.builder()
                .name("Kilogram")
                .label("kg").build();

        final Measurement tonne = Measurement.builder()
                .name("tonne")
                .label("tonne").build();

        final Measurement pound = Measurement.builder()
                .name("Pound")
                .label("pound").build();

        final Measurement ounce = Measurement.builder()
                .name("Ounce")
                .label("ounce").build();

        UnaryOperator<Double> kgToPound = (k) -> (k * 2.20_462);
        UnaryOperator<Double> kgToOunce = (k) -> (k * 35.274);

        UnaryOperator<Double> tonneToPound = (k) -> (k * 2_204.62);
        UnaryOperator<Double> tonneToOunce = (k) -> (k * 35274);

        UnaryOperator<Double> poundToKg = (p) -> (p * 0.453_592);
        UnaryOperator<Double> ounceToKg = (o) -> (o * 0.0_283_495);

        UnaryOperator<Double> poundToTonne = (p) -> (p * 0.000_453_592);
        UnaryOperator<Double> ounceToTonne = (o) -> (o * 0.00_002_835);

        List<ConversionFactor> conversions = new ArrayList<>();
        conversions.add(ConversionFactor.builder().from(kg).to(pound).converter(kgToPound).build());
        conversions.add(ConversionFactor.builder().from(kg).to(ounce).converter(kgToOunce).build());
        conversions.add(ConversionFactor.builder().from(tonne).to(pound).converter(tonneToPound).build());
        conversions.add(ConversionFactor.builder().from(tonne).to(ounce).converter(tonneToOunce).build());
        conversions.add(ConversionFactor.builder().from(pound).to(kg).converter(poundToKg).build());
        conversions.add(ConversionFactor.builder().from(pound).to(tonne).converter(poundToTonne).build());
        conversions.add(ConversionFactor.builder().from(ounce).to(kg).converter(ounceToKg).build());
        conversions.add(ConversionFactor.builder().from(ounce).to(tonne).converter(ounceToTonne).build());

        return ConversionCategory.builder()
                .metric(Stream.of(kg, tonne).collect(Collectors.toList()))
                .imperial(Stream.of(pound, ounce).collect(Collectors.toList()))
                .conversions(conversions)
                .build();
    }

    private static ConversionCategory temperatureCategory() {
        final Measurement celsius = Measurement.builder()
                .name("Celsius")
                .label("c").build();
        final Measurement fahrenheit = Measurement.builder()
                .name("Fahrenheit")
                .label("f").build();
        final Measurement kelvin = Measurement.builder()
                .name("Kelvin")
                .label("k").build();

        UnaryOperator<Double> celToFah = (cel) -> (cel * 1.8) + 32;
        UnaryOperator<Double> celToKel = (cel) -> cel + 273.15;

        UnaryOperator<Double> fahToCel = (fah) -> (fah - 32) * 0.5555;
        UnaryOperator<Double> fahToKel = (fah) -> (fah - 32) * 0.5555 + 273.15;

        UnaryOperator<Double> kelToCel = (kel) -> kel - 273.15;
        UnaryOperator<Double> kelToFah = (kel) -> (kel - 273.15) * 1.8 + 32;

        List<ConversionFactor> conversions = new ArrayList<>();
        conversions.add(ConversionFactor.builder().from(celsius).to(fahrenheit).converter(celToFah).build());
        conversions.add(ConversionFactor.builder().from(celsius).to(kelvin).converter(celToKel).build());
        conversions.add(ConversionFactor.builder().from(fahrenheit).to(kelvin).converter(fahToKel).build());
        conversions.add(ConversionFactor.builder().from(fahrenheit).to(celsius).converter(fahToCel).build());
        conversions.add(ConversionFactor.builder().from(kelvin).to(celsius).converter(kelToCel).build());
        conversions.add(ConversionFactor.builder().from(kelvin).to(fahrenheit).converter(kelToFah).build());

        return ConversionCategory.builder()
                .metric(Stream.of(celsius, fahrenheit, kelvin).collect(Collectors.toList()))
                .imperial(Stream.of(celsius, fahrenheit, kelvin).collect(Collectors.toList()))
                .conversions(conversions)
                .build();
    }

    private static ConversionCategory lengthCategory() {
        final Measurement kilometer = Measurement.builder()
                .name("KiloMeter")
                .label("km").build();
        final Measurement meter = Measurement.builder()
                .name("Meter")
                .label("m").build();
        final Measurement inch = Measurement.builder()
                .name("Inch")
                .label("inch").build();
        final Measurement yard = Measurement.builder()
                .name("Yard")
                .label("yard").build();
        final Measurement foot = Measurement.builder()
                .name("Foot")
                .label("foot").build();

        UnaryOperator<Double> kmToInch = (km) -> km * 39_370.1;
        UnaryOperator<Double> kmToYard = (km) -> km * 1_093.61;
        UnaryOperator<Double> kmToFoot = (km) -> km * 3280.84;

        UnaryOperator<Double> mToInch = (m) -> m * 39.3_701;
        UnaryOperator<Double> mToYard = (m) -> m * 1.09_361;
        UnaryOperator<Double> mToFoot = (m) -> m * 3.28_084;

        UnaryOperator<Double> inchToKm = (i) -> i * 0.0_000_254;
        UnaryOperator<Double> inchToM = (i) -> i * 0.0_254;

        UnaryOperator<Double> yardToKm = (y) -> y * 0.0_009_144;
        UnaryOperator<Double> yardToM = (y) -> y * 0.9_144;

        UnaryOperator<Double> footToKm = (f) -> f * 0.0_003_048;
        UnaryOperator<Double> footToM = (f) -> f * 0.3048;

        List<ConversionFactor> conversions = new ArrayList<>();
        conversions.add(ConversionFactor.builder().from(kilometer).to(inch).converter(kmToInch).build());
        conversions.add(ConversionFactor.builder().from(kilometer).to(yard).converter(kmToYard).build());
        conversions.add(ConversionFactor.builder().from(kilometer).to(foot).converter(kmToFoot).build());

        conversions.add(ConversionFactor.builder().from(meter).to(inch).converter(mToInch).build());
        conversions.add(ConversionFactor.builder().from(meter).to(yard).converter(mToYard).build());
        conversions.add(ConversionFactor.builder().from(meter).to(foot).converter(mToFoot).build());

        conversions.add(ConversionFactor.builder().from(inch).to(kilometer).converter(inchToKm).build());
        conversions.add(ConversionFactor.builder().from(inch).to(meter).converter(inchToM).build());

        conversions.add(ConversionFactor.builder().from(yard).to(kilometer).converter(yardToKm).build());
        conversions.add(ConversionFactor.builder().from(yard).to(meter).converter(yardToM).build());

        conversions.add(ConversionFactor.builder().from(foot).to(kilometer).converter(footToKm).build());
        conversions.add(ConversionFactor.builder().from(foot).to(meter).converter(footToM).build());

        return ConversionCategory.builder()
                .metric(Stream.of(kilometer, meter).collect(Collectors.toList()))
                .imperial(Stream.of(inch, yard, foot).collect(Collectors.toList()))
                .conversions(conversions)
                .build();
    }
}
