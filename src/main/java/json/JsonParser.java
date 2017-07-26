package json;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.get;

public class JsonParser {

    /**Get Json as string passing url as parameter*/
    public static String getJsonString(String url) {
        return get(url).asString();
    }

    /**Get value passing (Json) response & path to Json key
     * returned is initialized empty string or actual value with stripped square brackets*/
    public static String getJsonValue(String jsonResponse, String pathToKey) {

        String value = JsonPath.from(jsonResponse).getString(pathToKey).replaceAll("[\\p{Ps}\\p{Pe}]", "");

        return nullValue(value);
    }

    /**if value is "null" return ""
     * otherwise return actual value*/
    private static String nullValue(String value) {

        if (value.equalsIgnoreCase("Другое")) return "";
        if (value.equalsIgnoreCase("null")) return "";
        else return value;
    }

}
