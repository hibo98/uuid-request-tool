package me.hibo98.uuid_request_tool;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import org.json.simple.parser.ParseException;

public class ConverterListener implements ActionListener {

    private final Converter con;

    public ConverterListener(Converter converter) {
        con = converter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == con.name_uuid || e.getSource() == con.name_a) {
            if (Converter.getUsername() == null || Converter.getUsername().isEmpty()) {
                Converter.setStatus(Level.SEVERE, "You must enter a Username!");
                return;
            }
            Converter.resetUUID();
            try {
                API.getUUIDFromMojang(Converter.getUsername(), (UUID uuid) -> {
                    Converter.setUUID(uuid);
                });
            } catch (IOException | ParseException ex) {
                Converter.setStatus(Level.SEVERE, "An unknown error occurred!");
            }
        } else if (e.getSource() == con.uuid_name || e.getSource() == con.uuid_a) {
            if (Converter.getUUIDString() == null || Converter.getUUIDString().isEmpty()) {
                Converter.setStatus(Level.SEVERE, "You must enter a UUID!");
                return;
            }
            Converter.resetUsername();
            try {
                API.getNameFromMojang(Converter.getUUID(), (String name) -> {
                    Converter.setUsername(name);
                });
            } catch (IOException | ParseException ex) {
                Converter.setStatus(Level.SEVERE, "An unknown error occurred!");
            } catch (IllegalArgumentException ex) {
                Converter.setStatus(Level.SEVERE, "You entred no valid UUID!");
            }
        } else if (e.getSource() == con.name_copy) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(Converter.getUsername()), null);
        } else if (e.getSource() == con.uuid_copy) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(Converter.getUUIDString()), null);
        }
    }
}
