package com.pagos.wallet.models;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class BaseModel {
    @SuppressWarnings("unchecked")
    public Map<String, Object> toMap() {
        return new ObjectMapper().convertValue(this, Map.class);
    }

    public String toJSON() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException exception) {
            return this.toString();
        }
    }

    public String toXML() {
        try {
            return new XmlMapper().writeValueAsString(this);
        } catch (JsonProcessingException exception) {
            return this.toString();
        }
    }
}
