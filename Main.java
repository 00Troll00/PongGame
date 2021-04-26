import javax.accessibility.AccessibleValue;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import thread.BallThread;
import thread.PcBarThread;

public class Main extends Application{
  //menu objects
  private VBox vBoxMenu;
  private Rectangle menuBorder;
  private Text score;
  private Region regionMenu;
  private Button startButton;

  private Rectangle playerBar;
  private Rectangle rigthBar;

  //threads
  private PcBarThread pcThread;
  private BallThread ballThread;

  private Scene scene;
  private boolean movingUp = false;
  private boolean movingDown = false;
  private double playerOneY;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
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
    AnchorPane root = new AnchorPane(ball, playerBar, rigthBar, menuBorder, vBoxMenu);
    root.setStyle("-fx-background-color: black;");
    scene = new Scene(root, 655, 400);
    addHandlePlayerBar();

    //creating the Threads
    ballThread = new BallThread(ball, playerBar, rigthBar, score);

    pcThread = new PcBarThread(rigthBar);

    startButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent e){
        if(pcThread.isAlive() || ballThread.isAlive()){
          pcThread.setOn(false);
          ballThread.setOn(false);
          startButton.setText("START");
          startButton.setDisable(true);
          Thread thread = new Thread( () -> {
            try{
              while(pcThread.isAlive() || ballThread.isAlive()){
                Thread.sleep(1);
              }
              Platform.runLater( () ->{ 
                ball.setX(50);
                ball.setY(25);
                startButton.setDisable(false);
              });
            }
            catch(InterruptedException ex){ }
          });
          thread.start();
        }
        else{//add thread to wate the time that to have a real reset
          pcThread = new PcBarThread(rigthBar);
          pcThread.start();
          ballThread = new BallThread(ball, playerBar, rigthBar, score);
          ballThread.start();
          startButton.setText("RESET");
        }          
      }
    });
    
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

    //configs to the score text
    score = new Text("0 | 0");
    score.setFill(Color.WHITE);
    score.setStyle("-fx-font-size: 16px;");

    //configs to the VBox
    vBoxMenu = new VBox();
    vBoxMenu.setLayoutX(515);
    vBoxMenu.setLayoutY(5);
    vBoxMenu.setPrefSize(145, 400);
    vBoxMenu.setAlignment(Pos.CENTER);
    vBoxMenu.setPadding(new Insets(0, 5, 5, 5));;

    //configs to the Region
    regionMenu = new Region();
    regionMenu.setPrefSize(2, 2);
    VBox.setVgrow(regionMenu, Priority.ALWAYS);

    //configs to the start button
    startButton = new Button("START");
    startButton.setMaxWidth(400);
    startButton.setStyle("-fx-background-color: black; -fx-border-color: white;");
    startButton.setTextFill(Color.WHITE);
    startButton.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
        startButton.setStyle("-fx-background-color: white; -fx-border-color: white;");
        startButton.setTextFill(Color.BLACK);
      }
    });
    startButton.setOnMouseExited(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
        startButton.setStyle("-fx-background-color: black; -fx-border-color: white;");
        startButton.setTextFill(Color.WHITE);
      }
    });

    Text text = new Text("SCORE");
    text.setFill(Color.WHITE);
    text.setStyle("-fx-font-size: 18px;");

    vBoxMenu.getChildren().addAll(text, score, regionMenu, startButton);
  }//end menu
  
  //add the player bar animation
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

  //add the key functions
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
  }//end addHandlePlayerBar
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