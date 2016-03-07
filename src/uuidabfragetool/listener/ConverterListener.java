package uuidabfragetool.listener;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import org.json.simple.parser.ParseException;
import uuidabfragetool.API;
import uuidabfragetool.Converter;

public class ConverterListener implements ActionListener {

    private final Converter con;

    public ConverterListener(Converter converter) {
        con = converter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == con.name_uuid || e.getSource() == con.name_a) {
            if (Converter.getUsername() == null || Converter.getUsername().equals("")) {
                Converter.setStatus(Level.SEVERE, "You must enter a Username!");
                return;
            }
            Converter.resetUUID();
            try {
                UUID uuid = API.getUUIDFromMojang(Converter.getUsername());
                if (uuid != null) {
                    Converter.setUUID(uuid);
                    Converter.setStatus(Level.FINE, "UUID was successfully requested from Mojang!");
                }
            } catch (IOException | ParseException ex) {
                Converter.setStatus(Level.SEVERE, "An unknown error occurred!");
            }
        } else if (e.getSource() == con.uuid_name || e.getSource() == con.uuid_a) {
            if (Converter.getUUIDString() == null || Converter.getUUIDString().equals("")) {
                Converter.setStatus(Level.SEVERE, "You must enter a UUID!");
                return;
            }
            Converter.resetUsername();
            try {
                String name = API.getNameFromMojang(Converter.getUUID());
                if (name != null) {
                    Converter.setUsername(name);
                    Converter.setStatus(Level.FINE, "Username was successfully requested from Mojang!");
                }
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
