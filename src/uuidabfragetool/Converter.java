package uuidabfragetool;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Converter {

    private final JLabel name_label = new JLabel("Username: ");
    private final JLabel uuid_label = new JLabel("UUID: ");

    public JFrame converter = new JFrame(" UUID Abfrage Tool by kaenganxt und hibo98 v1.0");
    public JPanel c_panel = new JPanel();
    public JPanel button_panel = new JPanel();
    public JButton uuid_name = new JButton("UUID in Username umwandeln");
    public JButton name_uuid = new JButton("Username in UUID umwandeln");
    public JPanel uuid_panel = new JPanel();
    public JTextField uuid_a = new JTextField(23);
    public JButton uuid_copy = new JButton("Copy");
    public String uuid;
    public JPanel name_panel = new JPanel();
    public JTextField name_a = new JTextField(20);
    public JButton name_copy = new JButton("Copy");
    public String name;
    public JPanel status_panel = new JPanel();
    public JLabel status_ta = new JLabel();
    public String status;
    private final MyActionListener AL = new MyActionListener(this);

    public Converter() {
        button_panel.add(uuid_name);
        uuid_name.addActionListener(AL);
        button_panel.add(name_uuid);
        name_uuid.addActionListener(AL);
        uuid_panel.add(uuid_label);
        uuid_panel.add(uuid_a);
        uuid_panel.add(uuid_copy);
        uuid_copy.addActionListener(AL);
        name_panel.add(name_label);
        name_panel.add(name_a);
        name_panel.add(name_copy);
        name_copy.addActionListener(AL);
        status_panel.add(status_ta);
        status = "Converter Bereit!";
        status_ta.setText("STATUS: " + status);
        c_panel.add(button_panel);
        c_panel.add(uuid_panel);
        c_panel.add(name_panel);
        c_panel.add(status_panel);
        converter.add(c_panel);
        converter.addWindowListener(new MyWindowListener());
        converter.setSize(450, 200);
        converter.setLocation(200, 200);
        converter.setResizable(false);
        converter.setVisible(true);
    }
}
