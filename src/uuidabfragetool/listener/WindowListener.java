package uuidabfragetool.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowListener extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
