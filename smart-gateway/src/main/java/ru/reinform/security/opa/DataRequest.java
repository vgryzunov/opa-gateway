package ru.reinform.security.opa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class DataRequest {
    private final Map<String, Object> input = new HashMap<>();
    private final static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public String toString() {
        try {
            return mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            log.error("Cannot serialize OPA input", e);
            return "";
        }
    }
}
