package me.hibo98.uuid_request_tool;

import java.util.UUID;
import java.util.logging.Level;

public class Response {

    private final String username;
    private final UUID uuid;
    private final Level status;
    private final String msg;

    public Response(String username, UUID uuid, Level status, String msg) {
        this.username = username;
        this.uuid = uuid;
        this.status = status;
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Level getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
