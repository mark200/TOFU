package nl.tudelft.oopp.group54.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.TreeMap;

public class RequestBuilder {

    private Map<String, Object> toBeReturned = new TreeMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    public RequestBuilder add(String key, Object value) {
        this.toBeReturned.put(key, value);
        return this;
    }

    /**
     * Transforms JSON into String.
     * @return A String based representation
     */
    public String build() {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(this.toBeReturned);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.toBeReturned.clear();

        return json;
    }
}
