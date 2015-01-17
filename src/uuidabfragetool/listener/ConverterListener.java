package uuidabfragetool.listener;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import uuidabfragetool.API;
import uuidabfragetool.Converter;

public class ConverterListener implements ActionListener {

    public Converter con;

    public ConverterListener(Converter converter) {
        this.con = converter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == con.name_uuid) {
            API.resetUUID();
            try {
                API.setUUID(API.getUUIDFromMojang(API.getName()));
                API.setStatus("UUID von " + API.getName() + " wurde bei Mojang abgefragt!");
            } catch (IOException | ParseException ex) {
                Logger.getLogger(ConverterListener.class.getName()).log(Level.SEVERE, null, ex);
                API.setStatus("UUID konnte nicht bei Mojang abgefragt werden!");
            }
        } else if (e.getSource() == con.uuid_name) {
            API.resetName();
            try {
                String name = API.getNameFromMojang(API.getUUID());
                if (name.equals("429")) {
                    API.resetName();
                    API.setStatus("Error: 429 - Zu viele Anfragen innerhalb kurzer Zeit!");
                } else {
                    API.setName(name);
                    API.setStatus("Username zur UUID wurde bei Mojang abgefragt!");
                }
            } catch (IOException | ParseException ex) {
                Logger.getLogger(ActionListener.class.getName()).log(Level.SEVERE, null, ex);
                API.setStatus("Username konnte nicht bei Mojang abgefragt werden!");
            }
        } else if (e.getSource() == con.name_copy) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(API.getName()), null);
        } else if (e.getSource() == con.uuid_copy) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(API.getUUIDString()), null);
        } else if (e.getSource() == con.name_a) {
            API.resetUUID();
            try {
                API.setUUID(API.getUUIDFromMojang(API.getName()));
                API.setStatus("UUID von " + API.getName() + " wurde bei Mojang abgefragt!");
            } catch (IOException | ParseException ex) {
                Logger.getLogger(ConverterListener.class.getName()).log(Level.SEVERE, null, ex);
                API.setStatus("UUID konnte nicht bei Mojang abgefragt werden!");
            }
        } else if (e.getSource() == con.uuid_a) {
            API.resetName();
            try {
                String name = API.getNameFromMojang(API.getUUID());
                if (name.equals("429")) {
                    API.resetName();
                    API.setStatus("Error: 429 - Zu viele Anfragen innerhalb kurzer Zeit!");
                } else {
                    API.setName(name);
                    API.setStatus("Username zur UUID wurde bei Mojang abgefragt!");
                }
            } catch (IOException | ParseException ex) {
                Logger.getLogger(ActionListener.class.getName()).log(Level.SEVERE, null, ex);
                API.setStatus("Username konnte nicht bei Mojang abgefragt werden!");
            }
        }
    }
}
