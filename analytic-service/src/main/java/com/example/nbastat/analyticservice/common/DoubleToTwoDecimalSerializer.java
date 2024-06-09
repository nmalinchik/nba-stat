package com.example.nbastat.analyticservice.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class DoubleToTwoDecimalSerializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        BigDecimal bigDecimal = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
        gen.writeNumber(bigDecimal);
    }
}