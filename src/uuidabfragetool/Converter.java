package uuidabfragetool;

import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import uuidabfragetool.listener.WindowListener;
import uuidabfragetool.listener.ConverterListener;

public class Converter extends JFrame {

    private static Converter instance;
    private static final String version = "1.4";
    private final JLabel name_label = new JLabel("Username: ");
    private final JLabel uuid_label = new JLabel("UUID: ");
    private final JPanel c_panel = new JPanel();
    private final JPanel button_panel = new JPanel();
    private final JPanel uuid_panel = new JPanel();
    private final JPanel name_panel = new JPanel();
    private final JPanel status_panel = new JPanel();
    public final JButton uuid_name = new JButton("UUID in Username umwandeln");
    public final JButton name_uuid = new JButton("Username in UUID umwandeln");
    public final JTextField uuid_a = new JTextField(23);
    public final JButton uuid_copy = new JButton("Copy");
    public final JTextField name_a = new JTextField(20);
    public final JButton name_copy = new JButton("Copy");
    private final JLabel status_ta = new JLabel("STATUS: " + "Converter Bereit!");
    private final ConverterListener AL = new ConverterListener(this);

    public Converter() {
        super("UUID Abfrage Tool by kaenganxt und hibo98 v" + version);
        button_panel.add(uuid_name);
        uuid_name.addActionListener(AL);
        button_panel.add(name_uuid);
        name_uuid.addActionListener(AL);
        uuid_panel.add(uuid_label);
        uuid_panel.add(uuid_a);
        uuid_a.addActionListener(AL);
        uuid_panel.add(uuid_copy);
        uuid_copy.addActionListener(AL);
        name_panel.add(name_label);
        name_panel.add(name_a);
        name_a.addActionListener(AL);
        name_panel.add(name_copy);
        name_copy.addActionListener(AL);
        status_panel.add(status_ta);
        c_panel.add(button_panel);
        c_panel.add(uuid_panel);
        c_panel.add(name_panel);
        c_panel.add(status_panel);
        super.add(c_panel);
        super.addWindowListener(new WindowListener());
        super.setSize(500, 200);
        super.setLocation(200, 200);
        super.setResizable(false);
        super.setVisible(true);
    }
    
    
    public static String getUsername() {
        return getInstance().name_a.getText();
    }

    public static void setStatus(String status) {
        getInstance().status_ta.setText("STATUS: " + status);
    }

    public static void setUUID(UUID uuid) {
        getInstance().uuid_a.setText(uuid.toString());
    }

    public static void resetUUID() {
        getInstance().uuid_a.setText("");
    }

    public static UUID getUUID() {
        return UUID.fromString(getInstance().uuid_a.getText());
    }

    public static void resetUsername() {
        getInstance().name_a.setText("");
    }

    public static String getUUIDString() {
        return getInstance().uuid_a.getText();
    }

    public static void setUsername(String username) {
        getInstance().name_a.setText(username);
    }
    
    public static Converter getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new Converter();
    }
}
