package com.ayoholdings.api;


import com.ayoholdings.exception.InvalidCategoryException;
import com.ayoholdings.exception.InvalidConversionException;
import com.ayoholdings.model.ConversionDTO;
import com.ayoholdings.service.ConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConversionApi.class)
class ConversionApiTest {
    public static final String CATEGORY_API = "/api/categories";
    public static final String CONVERSION_API = "/api/convert/Mass/Kilogram/Pound/10";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversionService conversionService;

    @Test
    void test_whether_all_apis_are_interactive() throws Exception {

        when(conversionService.availableCategories())
                .thenReturn(ConversionDTO.builder().build());

        when(conversionService.convert(anyString(), anyString(), anyString(), anyDouble()))
                .thenReturn(22.0462);

        this.mockMvc.perform(get(CATEGORY_API))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get(CONVERSION_API))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    void validate_exception_scenarios() throws Exception {
        when(conversionService.availableCategories())
                .thenThrow(new InvalidCategoryException("Invalid Category Exception"));
        when(conversionService.convert(anyString(), anyString(), anyString(), anyDouble()))
                .thenThrow(new InvalidConversionException("Invalid Conversion Exception"));

        this.mockMvc.perform(get("/api/convert/RANDOM_CATEGORY/Kilogram/Pound1/11"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(get("/api/convert/Mass/RANDOM_MEASUREMENT/Pound1/11"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));

    }

}