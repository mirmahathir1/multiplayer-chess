package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class WatchChoice {

    public boolean spectator;

    public WatchChoice(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Watch Game");
        alert.setHeaderText("Watch online game or previous game?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("Online Game");
        ButtonType buttonTypeTwo = new ButtonType("Previous Game");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            spectator=true;
        }

        else {
            spectator=false;
        }
    }
}
