import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataUtil {

    private final static ObjectMapper jsonMapper = new ObjectMapper();

    /*
    /**********************************************************
    /* Getters
    /**********************************************************
     */

    public static InputStream getStream(String resourcePath) {
        return DataUtil.class.getClassLoader().getResourceAsStream(resourcePath);
    }

    public static String getString(String resourcePath) {
        return optionalString(resourcePath).orElseThrow(() -> new RuntimeException("Unable to parse file: " + resourcePath));
    }

    public static List<String> getLines(String resourcePath) {
        return optionalLines(resourcePath).orElseThrow(() -> new RuntimeException("Unable to parse file: " + resourcePath));
    }

    public static JsonNode getJson(String resourcePath) {
        return optionalJson(resourcePath).orElseThrow(() -> new RuntimeException("Unable to parse json file: " + resourcePath));
    }

    public static JsonElement getGson(String resourcePath) {
        return optionalGson(resourcePath).orElseThrow(() -> new RuntimeException("Unable to parse json file: " + resourcePath));
    }


    /*
    /**********************************************************
    /* Optionals
    /**********************************************************
     */

    public static Optional<InputStream> optionalStream(String resourcePath) {
        return Optional.ofNullable(DataUtil.class.getClassLoader().getResourceAsStream(resourcePath));
    }

    public static Optional<String> optionalString(String resourcePath) {
        try {
            InputStream stream = getStream(resourcePath);
            String content = IOUtils.toString(stream, Charset.defaultCharset());
            closeQuietly(stream);
            return Optional.of(content);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public static Optional<List<String>> optionalLines(String resourcePath) {
        try {
            InputStream stream = getStream(resourcePath);
            List<String> lines = IOUtils.readLines(stream, Charset.defaultCharset()).stream()
                    .filter(line -> !StringUtils.trimToEmpty(line).isEmpty() && !line.startsWith("#"))
                    .collect(Collectors.toList());
            closeQuietly(stream);
            return Optional.of(lines);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public static Optional<JsonNode> optionalJson(String resourcePath) {
        try {
            return Optional.of(jsonMapper.readTree(getStream(resourcePath)));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public static Optional<JsonElement> optionalGson(String resourcePath) {
        try {
            return Optional.of(JsonParser.parseString(getString(resourcePath)));
        } catch (JsonSyntaxException ex) {
            return Optional.empty();
        }
    }

    /*
    /**********************************************************
    /* Converters
    /**********************************************************
     */

    public static JsonNode asJson(String json) {
        return asOptionalJson(json).orElseThrow(() -> new RuntimeException("Unable to convert string to json. Content: " + json));
    }

    public static Optional<JsonNode> asOptionalJson(String json) {
        try {
            return Optional.of(jsonMapper.readTree(json));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public static JsonElement asGson(String json) {
        return asOptionalGson(json).orElseThrow(() -> new RuntimeException("Unable to convert string to gson. Content: " + json));
    }

    public static Optional<JsonElement> asOptionalGson(String json) {
        try {
            return Optional.of(JsonParser.parseString(json));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    /*
    /**********************************************************
    /* Private Helpers
    /**********************************************************
     */

    private static void closeQuietly(final Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (final IOException ignored) { }
        }
    }

}
