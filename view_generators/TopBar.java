package view_generators;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TopBar extends HBox{
  private Button close = new Button();
  private Button minimize = new Button();
  private ImageView icon = new ImageView();
  private Text title = new Text();
  private Region region = new Region();

  public TopBar(Stage stage){
    super();
    close = new Button();
    minimize = new Button();
    icon = new ImageView();
    title = new Text();
    region = new Region();

    HBox.setVgrow(region, Priority.ALWAYS);
    
    //configing the hbox that contains the elements to the bar
    super.setMaxHeight(25);
    super.setMinHeight(25);
    super.setPrefHeight(25);
    super.getChildren().addAll(icon, title, region, minimize, close);
  }

  private static void setMoviment(Stage stage, Region region){
    //
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
