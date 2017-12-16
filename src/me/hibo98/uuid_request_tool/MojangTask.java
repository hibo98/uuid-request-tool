package me.hibo98.uuid_request_tool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Level;
import org.json.simple.DeserializationException;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

public class MojangTask extends Thread {

    private static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final String PAGE_URL = "https://api.mojang.com/users/profiles/minecraft/";

    public void run(String request, boolean isUUID, Callback<Response> c) {
        UUID uuid = null;
        String username = null;
        Level level = Level.SEVERE;
        String status = "An unknown error occurred!";
        if (isUUID) {
            uuid = UUID.fromString(request);
        } else {
            username = request;
        }
        try {
            String requestString = isUUID ? request.replace("-", "") : request;
            HttpURLConnection connection = (HttpURLConnection) new URL((isUUID ? PROFILE_URL : PAGE_URL) + requestString).openConnection();
            switch (connection.getResponseCode()) {
                case 200:
                    JsonObject response = (JsonObject) Jsoner.deserialize(new InputStreamReader(connection.getInputStream()));
                    if (isUUID) {
                        username = (String) response.get("name");
                    } else {
                        String id = (String) response.get("id");
                        uuid = UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
                    }
                    level = Level.FINE;
                    status = "Success!";
                    break;
                case 204:
                    status = "The entered " + (isUUID ? "UUID" : "Username") + " doesn't exists!";
                    break;
                case 429:
                    status = "You've send too many requests!";
                    break;
            }
            connection.disconnect();
        } catch (IOException | DeserializationException ex) {
        }
        c.run(new Response(username, uuid, level, status));
    }
}
