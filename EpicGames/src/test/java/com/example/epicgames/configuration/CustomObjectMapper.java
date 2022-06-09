package com.example.epicgames.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapper {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
