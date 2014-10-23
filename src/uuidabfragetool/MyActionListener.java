package uuidabfragetool;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyActionListener implements ActionListener {
    public Converter con;
    public MyActionListener(Converter converter) {
        this.con=converter;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==con.name_uuid) {
            con.name = con.name_a.getText();
            try {
                ArrayList<String> conv = new ArrayList<>();
                conv.add(con.name);
                UUIDFetcher uf = new UUIDFetcher(conv);
                Map<String, UUID> uuids = uf.call();
                String uuid = ((UUID)uuids.values().toArray()[0]).toString();
                con.uuid = uuid;
                con.status = "UUID von "+con.name+" wurde bei Mojang abgefragt!";
            } 
            catch (Exception ex) {
                Logger.getLogger(MyActionListener.class.getName()).log(Level.SEVERE, null, ex);
                con.status = "UUID konnte nicht bei Mojang abgefragt werden!";
                con.uuid = "";
            } 
            con.status_ta.setText("STATUS: "+con.status);
            con.uuid_a.setText(con.uuid);
        }
        else if (e.getSource()==con.uuid_copy) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(con.uuid),null);
        }
        else if (e.getSource()==con.uuid_name) {
            con.uuid = con.uuid_a.getText();
            try {
                ArrayList<UUID> conv = new ArrayList<>();
                conv.add(UUID.fromString(con.uuid));
                NameFetcher nf = new NameFetcher(conv);
                Map<UUID, String> names = nf.call();
                String name = (String)names.values().toArray()[0];
                con.name = name;
                con.status = "Username zur UUID wurde bei Mojang abgefragt!";
            } 
            catch (Exception ex) {
                Logger.getLogger(MyActionListener.class.getName()).log(Level.SEVERE, null, ex);
                con.status = "Username konnte nicht bei Mojang abgefragt werden!";
                con.name = "";
            }
            con.status_ta.setText("STATUS: "+con.status);
            con.name_a.setText(con.name);
        }
        else if (e.getSource()==con.name_copy) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(con.name),null);
        }
    }
}