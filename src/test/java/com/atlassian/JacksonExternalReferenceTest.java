package com.atlassian;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class JacksonExternalReferenceTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deserializeWithTypeFieldAtTheEndFails() throws IOException {
        objectMapper.readValue(loadJson("sample-fail.json"), Pet.class);
    }

    @Test
    void deserializeWithTypeFieldAtTheTopSucceeds() throws IOException {
        objectMapper.readValue(loadJson("sample-success.json"), Pet.class);
    }

    private InputStream loadJson(String jsonFile) {
        return this.getClass().getClassLoader().getResourceAsStream(jsonFile);
    }
}

