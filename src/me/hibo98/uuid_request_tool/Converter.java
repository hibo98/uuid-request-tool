package me.hibo98.uuid_request_tool;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
    private static final String version = "1.7";
    private final JPanel c_panel = new JPanel();
    private final JPanel button_panel = new JPanel();
    private final JPanel uuid_panel = new JPanel();
    private final JPanel name_panel = new JPanel();
    public final JButton uuid_name = new JButton("UUID > Username");
    public final JButton name_uuid = new JButton("Username > UUID");
    public final JTextField uuid = new JTextField(23);
    private final JButton uuid_copy = new JButton("Copy");
    public final JTextField name = new JTextField(20);
    private final JButton name_copy = new JButton("Copy");
    private final JLabel status = new JLabel("STATUS: " + "Converter ready!");
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
        uuid_panel.add(new JLabel("UUID: "));
        uuid_panel.add(uuid);
        uuid.addActionListener(AL);
        uuid_panel.add(uuid_copy);
        uuid_copy.addActionListener((ae) -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(getUUIDString()), null);
        });
        name_panel.add(new JLabel("Username: "));
        name_panel.add(name);
        name.addActionListener(AL);
        name_panel.add(name_copy);
        name_copy.addActionListener((ae) -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(getUsername()), null);
        });
        c_panel.add(button_panel);
        c_panel.add(uuid_panel);
        c_panel.add(name_panel);
        c_panel.add(status);
        super.add(c_panel);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(450, 200);
        super.setLocation(200, 200);
        super.setResizable(false);
        super.setVisible(true);
    }

    public String getUsername() {
        return name.getText();
    }

    public void setStatus(Level level, String msg) {
        if (level.equals(Level.SEVERE)) {
            status.setForeground(new Color(139, 0, 0));
        } else if (level.equals(Level.WARNING)) {
            status.setForeground(new Color(128, 128, 0));
        } else if (level.equals(Level.FINE)) {
            status.setForeground(new Color(0, 100, 0));
        } else {
            status.setForeground(Color.black);
        }
        status.setText("STATUS: " + msg);
    }

    public void setUUID(UUID uuid) {
        this.uuid.setText(uuid.toString());
    }

    public void resetUUID() {
        uuid.setText("");
    }

    public UUID getUUID() throws IllegalArgumentException {
        return UUID.fromString(uuid.getText());
    }

    public void resetUsername() {
        name.setText("");
    }

    public String getUUIDString() {
        return uuid.getText();
    }

    public void setUsername(String username) {
        name.setText(username);
    }

    public static Converter getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new Converter();
    }
}
