package uuidabfragetool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class API {

    private static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final String PAGE_URL = "https://api.mojang.com/users/profiles/minecraft/";
    private static final JSONParser jsonParser = new JSONParser();

    public static String getNameFromMojang(UUID uuid) throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) new URL(PROFILE_URL + uuid.toString().replace("-", "")).openConnection();
        switch (connection.getResponseCode()) {
            case 200:
                JSONObject response = (JSONObject) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
                String name = (String) response.get("name");
                if (name == null) {
                    String cause = (String) response.get("cause");
                    String errorMessage = (String) response.get("errorMessage");
                    if (cause != null && cause.length() > 0) {
                        throw new IllegalStateException(errorMessage);
                    }
                }
                connection.disconnect();
                return name;
            case 204:
                Converter.setStatus("Error: Die eingegebene UUID existiert nicht!");
                connection.disconnect();
                return null;
            case 429:
                Converter.setStatus("Error: Zu viele Anfragen innerhalb kurzer Zeit!");
                connection.disconnect();
                return null;
            default:
                connection.disconnect();
                return null;
        }
    }

    public static UUID getUUIDFromMojang(String username) throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) new URL(PAGE_URL + username).openConnection();
        switch (connection.getResponseCode()) {
            case 200:
                JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
                connection.disconnect();
                String id = (String) jsonObject.get("id");
                UUID uuid = UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
                return uuid;
            case 204:
                Converter.setStatus("Error: Der eingegebene Username existiert nicht!");
                connection.disconnect();
                return null;
            case 429:
                Converter.setStatus("Error: Zu viele Anfragen innerhalb kurzer Zeit!");
                connection.disconnect();
                return null;
            default:
                Converter.setStatus("Error: Es ist ein unbekannter Fehler aufgetreten!");
                connection.disconnect();
                return null;
        }
    }
}
