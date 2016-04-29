package me.hibo98.uuid_request_tool;

import java.awt.Color;
import java.util.UUID;
import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Converter extends JFrame {

    private static Converter instance;
    private static final String version = "1.6";
    private final JLabel name_label = new JLabel("Username: ");
    private final JLabel uuid_label = new JLabel("UUID: ");
    private final JPanel c_panel = new JPanel();
    private final JPanel button_panel = new JPanel();
    private final JPanel uuid_panel = new JPanel();
    private final JPanel name_panel = new JPanel();
    private final JPanel status_panel = new JPanel();
    public final JButton uuid_name = new JButton("UUID > Username");
    public final JButton name_uuid = new JButton("Username > UUID");
    public final JTextField uuid_a = new JTextField(23);
    public final JButton uuid_copy = new JButton("Copy");
    public final JTextField name_a = new JTextField(20);
    public final JButton name_copy = new JButton("Copy");
    private final JLabel status_ta = new JLabel("STATUS: " + "Converter ready!");
    private final ConverterListener AL = new ConverterListener(this);

    public Converter() {
        super("Minecraft UUID Request Tool by hibo98 v" + version);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex1) {
            }
        }
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
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(450, 200);
        super.setLocation(200, 200);
        super.setResizable(false);
        super.setVisible(true);
    }

    public static String getUsername() {
        return getInstance().name_a.getText();
    }

    public static void setStatus(Level level, String status) {
        if (level.equals(Level.SEVERE)) {
            getInstance().status_ta.setForeground(new Color(139, 0, 0));
        } else if (level.equals(Level.WARNING)) {
            getInstance().status_ta.setForeground(new Color(128, 128, 0));
        } else if (level.equals(Level.FINE)) {
            getInstance().status_ta.setForeground(new Color(0, 100, 0));
        } else {
            getInstance().status_ta.setForeground(Color.black);
        }
        getInstance().status_ta.setText("STATUS: " + status);
    }

    public static void setUUID(UUID uuid) {
        getInstance().uuid_a.setText(uuid.toString());
    }

    public static void resetUUID() {
        getInstance().uuid_a.setText("");
    }

    public static UUID getUUID() throws IllegalArgumentException {
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
