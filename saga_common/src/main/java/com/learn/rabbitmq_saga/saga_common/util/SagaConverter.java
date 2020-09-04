package com.learn.rabbitmq_saga.saga_common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SagaConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static <T> String encode(T obj) throws IOException {
        return MAPPER.writeValueAsString(obj);
    }

    public static <T> T decode(String content, Class<?> className) throws IOException {
        return (T) MAPPER.readValue(content, className);
    }
}
