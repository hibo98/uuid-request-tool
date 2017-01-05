package me.hibo98.uuid_request_tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import org.json.simple.DeserializationException;

public class ConverterListener implements ActionListener {

    private final Converter con;

    public ConverterListener(Converter converter) {
        con = converter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == con.name_uuid || e.getSource() == con.name) {
            if (con.getUsername() == null || con.getUsername().isEmpty()) {
                con.setStatus(Level.SEVERE, "You must enter a Username!");
                return;
            }
            con.resetUUID();
            try {
                new MojangTask().run(con.getUsername(), false, (response) -> {
                    con.setUUID(response.getUuid());
                    con.setStatus(response.getStatus(), response.getMsg());
                });
            } catch (IOException | DeserializationException ex) {
                con.setStatus(Level.SEVERE, "An unknown error occurred!");
            }
        } else if (e.getSource() == con.uuid_name || e.getSource() == con.uuid) {
            if (con.getUUIDString() == null || con.getUUIDString().isEmpty()) {
                con.setStatus(Level.SEVERE, "You must enter a UUID!");
                return;
            }
            con.resetUsername();
            try {
                new MojangTask().run(con.getUUID().toString(), true, (response) -> {
                    con.setUsername(response.getUsername());
                    con.setStatus(response.getStatus(), response.getMsg());
                });
            } catch (IOException | DeserializationException ex) {
                con.setStatus(Level.SEVERE, "An unknown error occurred!");
            } catch (IllegalArgumentException ex) {
                con.setStatus(Level.SEVERE, "You entred no valid UUID!");
            }
        }
    }
}
