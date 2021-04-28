package view_generators;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import thread.BallThread;
import thread.PcBarThread;

public class GameBoard extends AnchorPane{
  //the threads
  public BallThread ballThread;
  public PcBarThread pcBarThread;

  //the size of the board
  public final double BOARD_SIZE_X = 500;
  public final double BOARD_SIZE_Y = 400;

  //the Y position of the bars
  private double leftBarY;
  private double rightBarY;

  //the game components
  private Rectangle leftBar;
  private Rectangle rightBar;
  private Rectangle ball;

  //boolean variables that activate the bar animation
  private boolean leftBarUp = false;
  private boolean leftBarDown = false;
  private boolean rightBarUp = false;
  private boolean rightBarDown = false;

  //scene reference
  private Scene scene;

  public GameBoard(){
    super();
    //game components config
    configGameComponents();

    leftBarY = leftBar.getLayoutY();

    ballThread = new BallThread(ball, leftBar, rightBar, null);
    pcBarThread = new PcBarThread(rightBar);

    super.setPrefSize(BOARD_SIZE_X + 10, BOARD_SIZE_Y + 10);
    super.setMinSize(BOARD_SIZE_X + 10, BOARD_SIZE_Y + 10);
    super.setMaxSize(BOARD_SIZE_X + 10, BOARD_SIZE_Y + 10);
    super.getChildren().addAll(leftBar, ball, rightBar);

    setLeftAnimation();
    setRightAnimation();
  }//end constuctor

  public void onClose(){
    if(pcBarThread.isAlive())
      pcBarThread.setOn(false);
    if(ballThread.isAlive())
      ballThread.setOn(false);
  }

  public void setLeftAnimation(){
    AnimationTimer timer = new AnimationTimer(){
      @Override
      public void handle(long now){
        if(leftBarUp && leftBarY > 0){
          leftBarY -= 5;
          leftBar.setLayoutY(leftBarY);
        }
        if(leftBarDown && leftBarY < 320){
          leftBarY += 5;
          leftBar.setLayoutY(leftBarY);
        }
      }
    };
    timer.start();
  }

  public void setRightAnimation(){
    AnimationTimer timer = new AnimationTimer(){
      @Override
      public void handle(long now){
        if(rightBarUp && rightBarY > 0){
          rightBarY -= 5;
          rightBar.setLayoutY(rightBarY);
        }
        if(rightBarDown && rightBarY < 320){
          rightBarY += 5;
          rightBar.setLayoutY(rightBarY);
        }
      }
    };
    timer.start();
  }//end setAnimations

  public void setOnePlayerKeys(){
    scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent e){
        switch (e.getCode()){
          case W: leftBarUp = true; break;
          case S: leftBarDown = true; break;
          default:
        }
      }
    });

    scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent e){
        switch (e.getCode()){
          case W: leftBarUp = false; break;
          case S: leftBarDown = false; break;
          default:
        }
      }
    });
  }

  public void setTwoPlayerKeys(){
    scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent e){
        switch (e.getCode()){
          case W: leftBarUp = true; break;
          case S: leftBarDown = true; break;
          case UP: rightBarUp = true; break;
          case DOWN: rightBarDown = true; break;
          default:
        }
      }
    });

    scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent e){
        switch (e.getCode()){
          case W: leftBarUp = false; break;
          case S: leftBarDown = false; break;
          case UP: rightBarUp = false; break;
          case DOWN: rightBarDown = false; break;
          default:
        }
      }
    });
  }

  public void startResetGame(Button button, boolean isTwoPlayersSelected, Label score){
    if(pcBarThread.isAlive() || ballThread.isAlive()){
      pcBarThread.setOn(false);
      ballThread.setOn(false);
      button.setText("START");
      button.setDisable(true);
      Thread thread = new Thread( () -> {
        try{
          while(pcBarThread.isAlive() || ballThread.isAlive()){
            Thread.sleep(1);
          }
          Platform.runLater( () ->{ 
            ball.setLayoutX(50);
            ball.setLayoutY(25);
            button.setDisable(false);
            score.setText("0 | 0");
          });
        }
        catch(InterruptedException ex){ }
      });
      thread.start();
    }
    else{
      if(isTwoPlayersSelected){
        setTwoPlayerKeys();
        rightBarY = rightBar.getLayoutY();
      }
      else{
        setOnePlayerKeys();
        pcBarThread = new PcBarThread(rightBar);
        pcBarThread.start();
      }
      ballThread = new BallThread(ball, leftBar, rightBar, score);
      ballThread.start();
      button.setText("RESET");
    }
  }

  public void setScene(Scene scene){
    this.scene = scene;
    setOnePlayerKeys();
  }

  private void configGameComponents(){
    //left bar
    leftBar = new Rectangle(10, 90);
    leftBar.setLayoutX(5);
    leftBar.setLayoutY(BOARD_SIZE_Y/2);
    leftBar.setFill(Color.WHITE);

    //right bar
    rightBar = new Rectangle(10, 90);
    rightBar.setLayoutX(BOARD_SIZE_X + 5 - rightBar.getWidth());
    rightBar.setLayoutY(BOARD_SIZE_Y/2);
    rightBar.setFill(Color.WHITE);

    //ball
    ball = new Rectangle(10, 10);
    ball.setLayoutX(BOARD_SIZE_X/2);
    ball.setLayoutY(BOARD_SIZE_Y/2);
    ball.setFill(Color.WHITE);
  }//end configGameComponents
}
