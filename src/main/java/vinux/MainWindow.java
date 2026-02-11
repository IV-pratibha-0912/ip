package vinux;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI window.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Vinux vinux;

    private Image userImage = new Image(
            this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image vinuxImage = new Image(
            this.getClass().getResourceAsStream("/images/DaVinux.png"));
    private Image logoImage = new Image(
            this.getClass().getResourceAsStream("/images/VinuxLogo.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Vinux instance and shows welcome message. */
    public void setVinux(Vinux v) {
        vinux = v;

        if (logoImage != null) {
            ImageView logo = new ImageView(logoImage);
            logo.setFitWidth(350);
            logo.setPreserveRatio(true);

            // Centre the logo
            HBox logoContainer = new HBox(logo);
            logoContainer.setAlignment(Pos.CENTER);

            dialogContainer.getChildren().add(logoContainer);
        }

        dialogContainer.getChildren().add(
                DialogBox.getVinuxDialog(vinux.getWelcomeMessage(), vinuxImage)
        );
    }

    /**
     * Handles user input from the text field or send button.
     * Creates dialog boxes for both user input and Vinux response.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return;
        }
        String response = vinux.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getVinuxDialog(response, vinuxImage)
        );
        userInput.clear();
    }
}
