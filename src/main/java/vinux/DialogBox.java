package vinux;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm a");

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Wraps the dialog label and timestamp into a VBox.
     * Must be called AFTER all style classes are applied.
     */
    private void wrapWithTimestamp(boolean isUser) {
        String time = LocalTime.now().format(TIME_FORMAT);
        Label timestamp = new Label(time);
        timestamp.getStyleClass().add("timestamp-label");

        VBox textBox = new VBox(2, dialog, timestamp);
        textBox.setAlignment(isUser ? Pos.TOP_RIGHT : Pos.TOP_LEFT);

        getChildren().remove(dialog);
        if (isUser) {
            getChildren().add(0, textBox);
        } else {
            getChildren().add(textBox);
        }
    }

    /**
     * Applies a CSS style class based on the response content.
     * Must be called BEFORE wrapWithTimestamp.
     */
    private void applyTaskStyle(String text) {
        String lower = text.toLowerCase();
        if (lower.startsWith("gotcha") && lower.contains("[t]")) {
            dialog.getStyleClass().add("todo-label");
        } else if (lower.startsWith("gotcha") && lower.contains("[d]")) {
            dialog.getStyleClass().add("deadline-label");
        } else if (lower.startsWith("gotcha") && lower.contains("[e]")) {
            dialog.getStyleClass().add("event-label");
        } else if (lower.startsWith("solid!")) {
            dialog.getStyleClass().add("mark-label");
        } else if (lower.startsWith("cheer:")) {
            dialog.getStyleClass().add("cheer-label");
        } else if (lower.startsWith("error:")) {
            applyErrorStyle(text.substring(6).trim());
        } else if (lower.startsWith("consider it done!")) {
            dialog.getStyleClass().add("clear-label");
        } else if (lower.contains("vinux command guide") || lower.startsWith("====")) {
            dialog.getStyleClass().add("help-label");
        } else if (lower.startsWith("got it! added expense")) {
            dialog.getStyleClass().add("expense-label");
        } else if (lower.startsWith("here are your expenses")
                || lower.startsWith("total spent on")
                || lower.startsWith("alright, i've removed this expense")) {
            dialog.getStyleClass().add("expense-label");
        }  else if (lower.startsWith("expense summary")) {
            dialog.getStyleClass().add("summary-label");
        }
    }

    private void applyErrorStyle(String message) {
        dialog.setText("[!] " + message);
        dialog.getStyleClass().clear();
        dialog.getStyleClass().add("error-label");
        dialog.setStyle(
                "-fx-background-color: #fc6a03;"
                        + "-fx-border-color: #e65100;"
                        + "-fx-border-width: 2px;"
                        + "-fx-text-fill: #000435;"
                        + "-fx-font-weight: normal;"
                        + "-fx-font-style: normal;"
                        + "-fx-font-family: 'Georgia';"
                        + "-fx-font-size: 13px;"
                        + "-fx-background-radius: 1em 1em 0 1em;"
                        + "-fx-border-radius: 1em 1em 0 1em;"
                        + "-fx-padding: 8px;"
        );
    }

    /**
     * Flips the dialog box so the image is on the left and text on the right.
     * Must be called BEFORE applyTaskStyle and wrapWithTimestamp.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /** Returns a dialog box for user messages. */
    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.wrapWithTimestamp(true);   // timestamp only, no style
        return db;
    }

    /** Returns a dialog box for Vinux messages, flipped to the left. */
    public static DialogBox getVinuxDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();                    // adds reply-label
        db.applyTaskStyle(text);      // clears ALL styles and adds error-label if error
        db.wrapWithTimestamp(false);
        return db;
    }
}
