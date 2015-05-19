package uuidabfragetool;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class API {

    private static final Converter con = Converter.getInstance();
    private static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final String PAGE_URL = "https://api.mojang.com/profiles/page/1";
    private static final String AGENT = "minecraft";
    private static final JSONParser jsonParser = new JSONParser();

    public static String getNameFromMojang(UUID uuid) throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) new URL(PROFILE_URL + uuid.toString().replace("-", "")).openConnection();
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

    public static UUID getUUIDFromMojang(String username) throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) new URL(PAGE_URL).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        JSONObject obj = new JSONObject();
        obj.put("name", username);
        obj.put("agent", AGENT);
        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
            writer.write(obj.toJSONString().getBytes());
            writer.flush();
        }
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
        connection.disconnect();
        JSONArray array = (JSONArray) jsonObject.get("profiles");
        Number count = (Number) jsonObject.get("size");
        if (count.intValue() == 0) {
            API.setStatus("UUID konnte nicht bei Mojang abgefragt werden!");
            return null;
        }
        JSONObject jsonProfile = (JSONObject) array.get(0);
        String id = (String) jsonProfile.get("id");
        UUID uuid = UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
        return uuid;
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
