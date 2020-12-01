package com.hub.eventgenerator.utility;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hub.eventgenerator.model.AudioEvent;
import com.hub.eventgenerator.model.Source;

import java.io.IOException;

/**
 * Converts AudioEvent object into JSON string.
 */
public class AudioEventSerializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public AudioEventSerializer() {
        SimpleModule module = new SimpleModule("CustomAudioEventSerializer",
                new Version(1, 0, 0, null, null, null));

        module.addSerializer(Source.class, new StdSerializer<>(Source.class) {
            @Override
            public void serialize(Source source, JsonGenerator jsonGenerator, SerializerProvider serializer)
                    throws IOException {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeObjectField("coordinates", source.getCoordinates());
                jsonGenerator.writeArrayFieldStart("types");
                for (Source.PotentialType type : source.getTypes()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField(type.getType(), type.getProbability());
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndArray();
                jsonGenerator.writeEndObject();
            }
        });
        module.addSerializer(AudioEvent.class, new StdSerializer<>(AudioEvent.class) {
            @Override
            public void serialize(AudioEvent audioEvent, JsonGenerator jsonGenerator, SerializerProvider serializer)
                    throws IOException {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("sensor_id", audioEvent.getSensorId());
                jsonGenerator.writeObjectField("date_real", audioEvent.getDateReal());
                jsonGenerator.writeObjectField("sensor_coordinates", audioEvent.getSensorCoordinates());
                jsonGenerator.writeRaw(",\"source\":" + objectMapper.writeValueAsString(audioEvent.getSource()));
                jsonGenerator.writeEndObject();
            }
        });
        objectMapper.registerModule(module);
    }

    public static void enablePrettyJson() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public String serialize(AudioEvent audioEvent) throws JsonProcessingException {
        return objectMapper.writeValueAsString(audioEvent);
    }
}
