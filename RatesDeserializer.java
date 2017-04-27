import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class RatesDeserializer implements JsonDeserializer<RateObject> {
    @Nullable
    public RateObject deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        RateObject rate = null;
        if (json.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> entries =
                    json.getAsJsonObject().entrySet();
            if (entries.size() > 0) {
                Map.Entry<String, JsonElement> entry = entries.iterator().next();
                rate = new RateObject(entry.getKey(), entry.getValue().getAsDouble());
            }
        }
        return rate;
    }
}