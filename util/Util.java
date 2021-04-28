package util;

import javafx.scene.control.Button;

public abstract class Util {
  public static void setButtonStyleConfiguration(Button button){
    button.setOnMouseEntered(m -> {
        button.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-text-fill: black;");
    });

    button.setOnMouseExited(m -> {
        button.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-text-fill: white;");
    });
  }
}
