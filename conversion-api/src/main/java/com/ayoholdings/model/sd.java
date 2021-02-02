/*
package com.ayoholdings.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

*/
/**
 *  {
 *      "categories": {
 *          "length" : {
 *              "metric":[
 *                  { "name": "Kilometer", "label": "km"}
 *                  { "name": "meter", "label": "m"}
 *              ],
 *              "imperial":[
 *                  { "name": "Yard", "label": "yard"},
 *                  { "name": "Foot", "label": "foot"}
 *              ]
 *          },
 *          "area": {
 *              "metric":[
 *                  { "name": "Kilometer", "label": "km"}
 *                  { "name": "meter", "label": "m"}
 *              ],
 *              "imperial":[
 *                  { "name": "Yard", "label": "yard"},
 *                  { "name": "Foot", "label": "foot"}
 *              ]
 *          }
 *      }
 *
 *  }
 *
 *//*

@Data
public class ConversionDTO {
    private ConversionCategory conversionCategory;
    private Map<String , ConversionCategory> categories;
}

class ConversionCategory{
    private Map<String , Measurement> measurements;
}

class Measurement{
    private Map<String , List<Measurement>> measurements;

}

class MeasurementMeasurement{
    private String label;
    private String name;
}
*/
