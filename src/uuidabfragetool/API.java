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

    private static final Converter con = Converter.instance;
    private static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final JSONParser jsonParser = new JSONParser();

    public static String getNameFromMojang(UUID uuid) throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) new URL(PROFILE_URL + uuid.toString().replace("-", "")).openConnection();
        System.out.println(connection.getResponseCode());
        if (connection.getResponseCode() == 200) {
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
        } else if (connection.getResponseCode() == 429) {
            connection.disconnect();
            return "429";
        } else {
            connection.disconnect();
            return null;
        }
    }

    public static UUID getUUIDFromMojang(String username) {
        return null;
    }

    public static String getName() {
        return con.name_a.getText();
    }

    public static UUID getUUID() {
        return UUID.fromString(con.uuid_a.getText());
    }

    public static String getUUIDString() {
        return con.uuid_a.getText();
    }

    public static void setUUID(UUID uuid) {
        con.uuid_a.setText(uuid.toString());
    }

    public static void setName(String username) {
        con.name_a.setText(username);
    }

    public static void resetName() {
        con.name_a.setText("");
    }

    public static void resetUUID() {
        con.uuid_a.setText("");
    }

    public static void setStatus(String status) {
        con.status_ta.setText("STATUS: " + status);
    }

}
