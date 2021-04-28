package view_generators;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TopBar extends HBox{
  private Button close;
  private Button minimize;
  private ImageView icon;
  private Label title;
  private Region region;

  private int stageX;
  private int stageY;

  public final double BAR_SIZE_Y = 25;

  public TopBar(){
    super();
    close = new Button("X");
    minimize = new Button("_");
    icon = new ImageView();
    title = new Label("Title");
    region = new Region();

    region.setMinHeight(BAR_SIZE_Y);

    HBox.setMargin(icon, new Insets(3, 3, 0, 0));
    HBox.setMargin(title, new Insets(3, 3, 0, 0));

    HBox.setHgrow(region, Priority.ALWAYS);

    configButtonsSize(close);
    configButtonsSize(minimize);
    
    //configing the hbox that contains the elements to the bar
    super.setMaxHeight(BAR_SIZE_Y);
    super.setMinHeight(BAR_SIZE_Y);
    super.setPrefHeight(BAR_SIZE_Y);
    super.setPadding(new Insets(0, 0, 3, 3));
    super.setSpacing(2);
    super.getChildren().addAll(icon, title, region, minimize, close);
  }

  public void configButtonsSize(Button b){
    Platform.runLater( () -> {
      b.setMinSize(50, 28);
      b.setPadding(new Insets(0, 0, 1, 0));
    });
  }

  public void configButtonsAction(Stage stage, GameBoard gameBoard){
    close.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent e){
        gameBoard.onClose();
        stage.close();
      }
    });
    minimize.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent e){
        stage.setIconified(true);
      }
    });
  }

  public void setMoviment(Stage stage){
    region.setOnMousePressed(m -> {
      if(m.getButton() == MouseButton.PRIMARY){
        region.setCursor(Cursor.MOVE);
        stageX = (int) (stage.getX() - m.getScreenX());
        stageY = (int) (stage.getY() - m.getScreenY());
      }
    });
    region.setOnMouseDragged( m -> {
      if(m.getButton() == MouseButton.PRIMARY){
        stage.setX(m.getScreenX() + stageX);
        stage.setY(m.getScreenY() + stageY);
      }
    });
    region.setOnMouseReleased(m -> {
      region.setCursor(Cursor.DEFAULT);
    });
  }

  private static void setButtonStyleConfiguration(Button button){
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
