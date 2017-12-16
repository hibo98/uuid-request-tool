package me.hibo98.uuid_request_tool;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Converter extends Application {

    private static final String version = "2.0";
    private final Button uuid_name = new Button("UUID > Username");
    private final Button name_uuid = new Button("Username > UUID");
    private final TextField uuid = new TextField();
    private final Button uuid_copy = new Button("Copy");
    private final TextField name = new TextField();
    private final Button name_copy = new Button("Copy");
    private final Label status = new Label("STATUS: Converter ready!");

    @Override
    public void start(Stage stage) {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(15));
        gp.setHgap(15);
        gp.setVgap(15);
        gp.add(new HBox(25, uuid_name, name_uuid), 1, 0);
        gp.add(new Label("UUID:"), 0, 1);
        gp.add(uuid, 1, 1);
        uuid.setMinWidth(250);
        uuid.setOnAction(new UUIDEvent());
        uuid_name.setOnAction(new UUIDEvent());
        gp.add(uuid_copy, 2, 1);
        gp.add(new Label("Username:"), 0, 2);
        gp.add(name, 1, 2);
        name.setMinWidth(250);
        name.setOnAction(new UsernameEvent());
        name_uuid.setOnAction(new UsernameEvent());
        gp.add(name_copy, 2, 2);
        gp.add(new StackPane(status), 0, 3, 3, 1);
        uuid_copy.setOnAction((ae) -> {
            HashMap<DataFormat, Object> map = new HashMap<>();
            map.put(DataFormat.PLAIN_TEXT, uuid.getText());
            Clipboard.getSystemClipboard().setContent(map);
        });
        name_copy.setOnAction((ae) -> {
            HashMap<DataFormat, Object> map = new HashMap<>();
            map.put(DataFormat.PLAIN_TEXT, name.getText());
            Clipboard.getSystemClipboard().setContent(map);
        });
        stage.setScene(new Scene(gp));
        stage.setTitle("Minecraft UUID Request Tool " + version);
        stage.setResizable(false);
        stage.show();
    }

    public void setStatus(Level level, String msg) {
        if (level.equals(Level.SEVERE)) {
            status.setTextFill(Color.web("#8B0000"));
        } else if (level.equals(Level.WARNING)) {
            status.setTextFill(Color.web("#808000"));
        } else if (level.equals(Level.FINE)) {
            status.setTextFill(Color.web("#006400"));
        } else {
            status.setTextFill(Color.BLACK);
        }
        status.setText("STATUS: " + msg);
    }

    public void resetUUID() {
        uuid.setText("");
    }

    public UUID getUUID() {
        try {
            return UUID.fromString(uuid.getText());
        } catch (IllegalArgumentException ex) {
            setStatus(Level.SEVERE, "You entred no valid UUID!");
        }
        return null;
    }

    public void resetUsername() {
        name.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class UUIDEvent implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            if (uuid.getText().isEmpty()) {
                setStatus(Level.SEVERE, "You must enter a UUID!");
                return;
            }
            resetUsername();
            UUID u = getUUID();
            if (u == null) {
                return;
            }
            new MojangTask().run(u.toString(), true, (response) -> {
                name.setText(response.getUsername());
                setStatus(response.getStatus(), response.getMsg());
            });
        }
    }
    
    public class UsernameEvent implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            if (name.getText().isEmpty()) {
                setStatus(Level.SEVERE, "You must enter a Username!");
                return;
            }
            resetUUID();
            new MojangTask().run(name.getText(), false, (response) -> {
                uuid.setText(response.getUuid().toString());
                setStatus(response.getStatus(), response.getMsg());
            });
        }
    }
}
