package view_generators;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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

  public GameBoard(){
    super();
    //game components config
    configGameComponents();

    leftBarY = leftBar.getLayoutY();
    rightBarY = rightBar.getLayoutY();

    ballThread = new BallThread(ball, leftBar, rightBar, null);
    pcBarThread = new PcBarThread(rightBar);

    super.setPrefSize(BOARD_SIZE_X + 10, BOARD_SIZE_Y + 10);
    super.setMinSize(BOARD_SIZE_X + 10, BOARD_SIZE_Y + 10);
    super.setMaxSize(BOARD_SIZE_X + 10, BOARD_SIZE_Y + 10);
    super.getChildren().addAll(leftBar, ball, rightBar);
  }

  public void startResetGame(Button button, boolean isTwoPlayersSelected, Text score){
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
        rightBarY = rightBar.getLayoutY();
        //playerTwoAnimation();
      }
      else{          
        pcBarThread = new PcBarThread(rightBar);
        pcBarThread.start();
        //if(playerTwoAnimationTimer != null)
          //playerTwoAnimationTimer.stop();
      }
      ballThread = new BallThread(ball, leftBar, rightBar, score);
      ballThread.start();
      button.setText("RESET");
    }
  }

  private void configGameComponents(){
    //left bar
    leftBar = new Rectangle(10, 90);
    leftBar.setLayoutX(5);
    leftBar.setLayoutY(BOARD_SIZE_Y/2);
    //leftBar.setFill(Color.BLACK);

    //right bar
    rightBar = new Rectangle(10, 90);
    rightBar.setLayoutX(BOARD_SIZE_X + 5 - rightBar.getWidth());
    rightBar.setLayoutY(BOARD_SIZE_Y/2);
    //rightBar.setFill(Color.BLACK);

    //ball
    ball = new Rectangle(10, 10);
    ball.setLayoutX(BOARD_SIZE_X/2);
    ball.setLayoutY(BOARD_SIZE_Y/2);
    //ball.setFill(Color.BLACK);
  }//end configGameComponents
}
