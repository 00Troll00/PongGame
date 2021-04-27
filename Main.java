/************************************
 * "MAKE THE GAME MODULAR, IDIOT!!!" - me to myself
 ************************************/

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
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
import view_generators.GameBoard;
import view_generators.Menu;

public class Main extends Application{
  //menu objects
  /*private VBox vBoxMenu;
  private Rectangle menuBorder;
  private Text score;
  private Region regionMenu;
  private Button startButton;
  private Button playerTwo;
  private boolean isPlayerTwoActiated = false;
  private HBox playerTwoHbox;

  private Rectangle playerBar;
  private Rectangle rightBar;
  AnimationTimer playerTwoAnimationTimer;

  //threads
  private PcBarThread pcThread;
  private BallThread ballThread;

  private Scene scene;
  private boolean movingUpPlayerOne = false;
  private boolean movingDownPlayerOne = false;
  private boolean movingUpPlayerTwo = false;
  private boolean movingDownPlayerTwo = false;
  private double playerOneY;
  private double rightBarY;*/

  private GameBoard gameBoard;
  private Menu menu;

  private HBox gameHBox;
  private AnchorPane root;
  private Scene scene;

  public static void main(String[] args) {
    launch(args);
  }
  @Override
  public void start(Stage stage) throws Exception {
    gameBoard = new GameBoard();
    menu = new Menu(gameBoard);


    gameHBox = new HBox();
    gameHBox.getChildren().addAll(gameBoard, menu);
    
    root = new AnchorPane();
    root.getChildren().add(gameHBox);

    scene = new Scene(root, gameBoard.BOARD_SIZE_X + menu.MENU_SIZE_X, gameBoard.BOARD_SIZE_Y);

    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }
  
  /*
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
    rightBar = new Rectangle(500, 200, 10, 90);
    rightBar.setStroke(Color.WHITE);
    rightBar.setFill(Color.WHITE);

    //creating the menu
    menu();

    //creating the pane and scene
    AnchorPane root = new AnchorPane(ball, playerBar, rightBar, menuBorder, vBoxMenu);
    root.setStyle("-fx-background-color: black;");
    scene = new Scene(root, 655, 400);
    addHandlePlayerBar();

    pcThread = new PcBarThread(rightBar);
    ballThread = new BallThread(ball, playerBar, rightBar, score);

    //the start and reset fuctions
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
                score.setText("0 | 0");
              });
            }
            catch(InterruptedException ex){ }
          });
          thread.start();
        }
        else{
          if(isPlayerTwoActiated){
            rightBarY = rightBar.getY();
            playerTwoAnimation();
          }
          else{          
            pcThread = new PcBarThread(rightBar);
            pcThread.start();
            if(playerTwoAnimationTimer != null)
              playerTwoAnimationTimer.stop();
          }
          ballThread = new BallThread(ball, playerBar, rightBar, score);
          ballThread.start();
          startButton.setText("RESET");
        }          
      }
    });
    
    //configurations for the stage
    primaryStage.setOnCloseRequest(e -> {
      if(pcThread.isAlive() || ballThread.isAlive()){
        ballThread.interrupt();
        pcThread.interrupt();
      }
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

    //configs to the player two button
    playerTwo = new Button(" ");
    playerTwo.setStyle("-fx-background-color: black; -fx-border-color: white;");
    playerTwo.setTextFill(Color.WHITE);
    playerTwo.setMinWidth(31);
    playerTwoButonActions();

    //function to config the HBox
    playerTwoHBoxConfig();

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

    vBoxMenu.getChildren().addAll(text, score, playerTwoHbox, regionMenu, startButton);
  }//end menu
  
  //add the player bar animation
  public void playerBarAnimation(){
    AnimationTimer timer = new AnimationTimer(){
      @Override
      public void handle(long now){
        if(movingUpPlayerOne && playerOneY > 0){
          playerOneY -= 5;
          playerBar.setY(playerOneY);
        }
        if(movingDownPlayerOne && playerOneY < 320){
          playerOneY += 5;
          playerBar.setY(playerOneY);
        }
      }
    };
    timer.start();
  }

  //adding the playerTwo animation
  public void playerTwoAnimation(){
    AnimationTimer playerTwoAnimationTimer = new AnimationTimer(){
      @Override
      public void handle(long now){
        if(movingUpPlayerTwo && rightBarY > 0){
          rightBarY -= 5;
          rightBar.setY(rightBarY);
        }
        if(movingDownPlayerTwo && rightBarY < 320){
          rightBarY += 5;
          rightBar.setY(rightBarY);
        }
      }
    };
    playerTwoAnimationTimer.start();
  }
  
  //add the key functions
  public void addHandlePlayerBar(){
    scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent e){
        switch(e.getCode()){
          case W:
            movingUpPlayerOne = true;
            break;
          case S:
            movingDownPlayerOne = true;
            break;
          case UP:
            movingUpPlayerTwo = true;
            break;
          case DOWN:
            movingDownPlayerTwo = true;
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
            movingUpPlayerOne = false;
            break;
          case S:
            movingDownPlayerOne = false;
            break;
          case UP:
            movingUpPlayerTwo = false;
            break;
          case DOWN:
            movingDownPlayerTwo = false;
            break;
          default:
        }
      }
    });
  }//end addHandlePlayerBar

  public void playerTwoButonActions(){
    playerTwo.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent e){
        if(isPlayerTwoActiated){
          playerTwo.setText(" ");
          isPlayerTwoActiated = false;
        }
        else{
          playerTwo.setText("X");
          isPlayerTwoActiated = true;
        }
      }
    });

    playerTwo.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
        playerTwo.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-radius: 10px;");
      }
    });

    playerTwo.setOnMouseExited(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent e){
        playerTwo.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-radius: 0px;");
      }
    });
  }

  public void playerTwoHBoxConfig(){
    playerTwoHbox = new HBox();

    //text to display biside the button
    Text text = new Text("Two Players");
    text.setFill(Color.WHITE);
    text.setStyle("-fx-font-size: 16px;");
    HBox.setMargin(text, new Insets(5, 0, 0, 5));
    
    //adding nodes to the HBox
    playerTwoHbox.getChildren().addAll(playerTwo, text);
  }*/
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