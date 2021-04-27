package others;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public static class UpBar {
  private static VBox bar;

  public static VBox generateUpBar(Stage stage){
    Button close = new Button();
    Button minimize = new Button();
    ImageView icon = new ImageView();
    Text title = new Text();
    Region region = new Region();

    VBox.setVgrow(region, Priority.ALWAYS);
    
    //configing the bar
    bar.setStyle("-fx-background-color: black;");
    bar.setMaxHeight(25);
    bar.setMinHeight(25);
    bar.setPrefHeight(25);
    bar.getChildren().addAll(icon, title, region, minimize, close);
  }

  private static void setMoviment(Stage stage, Region region){

  }

  private static void setButtonInitialConfiguration(Button button){
    button.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
        button.setStyle("-fx-background-color: white; -fx-border-color: white;");
        button.setTextFill(Color.BLACK);
      }
    });

    button.setOnMouseExited(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
        button.setStyle("-fx-background-color: black; -fx-border-color: white;");
        button.setTextFill(Color.WHITE);
      }
    });
  }
}
