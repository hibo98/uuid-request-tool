package uuidabfragetool.listener;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.UUID;
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
            Converter.resetUUID();
            try {
                UUID uuid = API.getUUIDFromMojang(Converter.getUsername());
                if (uuid != null) {
                    Converter.setUUID(uuid);
                    Converter.setStatus("UUID von " + Converter.getUsername() + " wurde bei Mojang abgefragt!");
                }
            } catch (IOException | ParseException ex) {
                Converter.setStatus("UUID konnte nicht bei Mojang abgefragt werden!");
            }
        } else if (e.getSource() == con.uuid_name || e.getSource() == con.uuid_a) {
            Converter.resetUsername();
            try {
                String name = API.getNameFromMojang(Converter.getUUID());
                if (name != null) {
                    Converter.setUsername(name);
                    Converter.setStatus("Username wurde erfolgreich bei Mojang abgefragt!");
                }
            } catch (IOException | ParseException ex) {
                Converter.setStatus("Username konnte nicht bei Mojang abgefragt werden!");
            }
        } else if (e.getSource() == con.name_copy) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(Converter.getUsername()), null);
        } else if (e.getSource() == con.uuid_copy) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(Converter.getUUIDString()), null);
        }
    }
}
