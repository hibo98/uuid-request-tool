package me.hibo98.uuid_request_tool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Level;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class API {

    private static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final String PAGE_URL = "https://api.mojang.com/users/profiles/minecraft/";
    private static final JSONParser jsonParser = new JSONParser();

    public static void getNameFromMojang(UUID uuid, Callback<String> c) throws IOException, ParseException {
        (new NameTask()).run(uuid, c);
    }

    public static void getUUIDFromMojang(String username, Callback<UUID> c) throws IOException, ParseException {
        (new UUIDTask()).run(username, c);
    }

    private static class NameTask extends Thread {

        public void run(UUID uuid, Callback<String> c) throws IOException, ParseException {
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
                    Converter.setStatus(Level.FINE, "Username was successfully requested from Mojang!");
                    c.run(name);
                    break;
                case 204:
                    Converter.setStatus(Level.SEVERE, "The entered UUID doesn't exists!");
                    connection.disconnect();
                    break;
                case 429:
                    Converter.setStatus(Level.SEVERE, "Too many requests!");
                    connection.disconnect();
                    break;
                default:
                    Converter.setStatus(Level.SEVERE, "An unknown error occurred!");
                    connection.disconnect();
            }
        }
    }

    private static class UUIDTask extends Thread {

        public void run(String username, Callback<UUID> c) throws IOException, ParseException {
            HttpURLConnection connection = (HttpURLConnection) new URL(PAGE_URL + username).openConnection();
            switch (connection.getResponseCode()) {
                case 200:
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
                    connection.disconnect();
                    String id = (String) jsonObject.get("id");
                    UUID uuid = UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
                    Converter.setStatus(Level.FINE, "UUID was successfully requested from Mojang!");
                    c.run(uuid);
                    break;
                case 204:
                    Converter.setStatus(Level.SEVERE, "The entered Username doesn't exists!");
                    connection.disconnect();
                    break;
                case 429:
                    Converter.setStatus(Level.SEVERE, "Too many requests!");
                    connection.disconnect();
                    break;
                default:
                    Converter.setStatus(Level.SEVERE, "An unknown error occurred!");
                    connection.disconnect();
            }
        }
    }
}
