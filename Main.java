import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import thread.BallThread;
import thread.PcBarThread;

public class Main extends Application{
  //menu objects
  private Rectangle menuBorder;

  private Rectangle playerBar;
  private Rectangle rigthBar;

  private PcBarThread pcThread;

  private Scene scene;
  private boolean movingUp = false;
  private boolean movingDown = false;
  private double playerOneY;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Button button = new Button("Start");
    button.setStyle("-fx-background-color: black; -fx-border-color: white;");
    button.setTextFill(Color.WHITE);
    button.setLayoutX(525);
    button.setLayoutY(350);
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
    //creating the ball
    Rectangle ball = new Rectangle(50, 25, 10, 10);
    ball.setStroke(Color.WHITE);
    ball.setFill(Color.WHITE);

    //creating the player bar
    playerBar = new Rectangle(5, 200, 10, 90);
    playerBar.setStroke(Color.WHITE);
    playerBar.setFill(Color.WHITE);
    playerOneY = playerBar.getY();
    playerBarAnimation();

    //creating the pc bar or second player bar
    rigthBar = new Rectangle(500, 200, 10, 90);
    rigthBar.setStroke(Color.WHITE);
    rigthBar.setFill(Color.WHITE);

    //creating the menu
    menu();

    //creating the pane and scene
    AnchorPane root = new AnchorPane(ball, playerBar, rigthBar, menuBorder, button);
    root.setStyle("-fx-background-color: black;");
    scene = new Scene(root, 655, 400);
    addHandlePlayerBar();

    //creating the Threads
    BallThread ballThread = new BallThread(ball, playerBar, rigthBar);
    ballThread.start();

    //configuring and starting the right bar Thread
    pcThread = new PcBarThread(rigthBar);
    pcThread.start();
    
    //configurations for the stage
    primaryStage.setOnCloseRequest(e -> {
      ballThread.interrupt();
      pcThread.interrupt();
      System.exit(0);
    });
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }//end start

  public void menu(){
    //configs to the menu border
    menuBorder = new Rectangle(515, 5, 145, 400);
    menuBorder.setStroke(Color.WHITE);
  }//end menu

  public void playerBarAnimation(){
    AnimationTimer timer = new AnimationTimer(){
      @Override
      public void handle(long now){
        if(movingUp && playerOneY > 0){
          playerOneY -= 5;
          playerBar.setY(playerOneY);
        }
        if(movingDown && playerOneY < 320){
          playerOneY += 5;
          playerBar.setY(playerOneY);
        }
      }
    };
    timer.start();
  }

  public void addHandlePlayerBar(){
    scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent e){
        switch(e.getCode()){
          case W:
            movingUp = true;
            break;
          case S:
            movingDown = true;
            break;
          default:
        }
      }
    });

    scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent e){
        switch(e.getCode()){
          case W:
            movingUp = false;
            break;
          case S:
            movingDown = false;
            break;
          default:
        }
      }
    });
  }
}//end class Main

/**
 * TODO
 *    put all the objects and other stuffs as atributes to the Main class
 *    make the thread to the "AI" player
 *    and create the AI bar
 *    assign the keys to the player bar moviment, up arrow and down arrow
 *    create the score text, in the middle top of the scene
 *    create the score count, that a player win when it reachs 10
 */