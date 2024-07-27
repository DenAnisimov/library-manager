package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class JacksonConfigTest {

    @Test
    void testCreateObjectMapper() throws Exception {
        ObjectMapper objectMapper = JacksonConfig.createObjectMapper();

        assertNotNull(objectMapper, "ObjectMapper должен быть создан");

        assertFalse(objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS),
                "WRITE_DATES_AS_TIMESTAMPS должен быть выключен");

        LocalDate date = LocalDate.of(2024, 7, 27);
        String dateStr = objectMapper.writeValueAsString(date);
        String expectedDateStr = "\"" + date.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\"";
        assertEquals(expectedDateStr, dateStr, "Дата должна быть сериализована в формате ISO_LOCAL_DATE");

        LocalDate deserializedDate = objectMapper.readValue(dateStr, LocalDate.class);
        assertEquals(date, deserializedDate, "Дата должна быть десериализована корректно");
    }
}
