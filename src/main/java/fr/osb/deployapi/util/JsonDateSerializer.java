package fr.osb.deployapi.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JSON date/time serializer.
 * Created on 15/02/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

    /**
     * Date format.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(final Date value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(value != null ? DATE_FORMAT.format(value) : null);
    }

}
