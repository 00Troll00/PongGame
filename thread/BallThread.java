package thread;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BallThread extends Thread{
  private double x;
  private double y;
  private Rectangle ball;
  private Rectangle leftBar;
  private Rectangle rightBar;
  private int contLeftScore;
  private int contRightScore;
  private Text score;
  private boolean up = false;
  private boolean on = true;
  private final double BOARD_GAME_MIN_X = 15;
  private final double BOARD_GAME_MAX_X = 495;
  private final double BOARD_GAME_MIN_Y = 5;
  private final double BOARD_GAME_MAX_Y = 405;


  public BallThread(Rectangle ball, Rectangle leftBar, Rectangle rightBar, Text score){
    this.score = score;
    this.rightBar = rightBar;
    this.leftBar = leftBar;
    this.ball = ball;
    this.x = ball.getLayoutX();
    this.y = ball.getLayoutY();
  }

  @Override
  public void run(){
    try{
      while(on){
        movingToRight();
        if(y > rightBar.getLayoutY() - 10 && y < rightBar.getLayoutY() + rightBar.getHeight() + 10)
          System.out.println("acertou");
        else{
          contLeftScore++;
          score.setText(contLeftScore + " | " + contRightScore);
          Platform.runLater( () -> ball.setLayoutX(450));
          x=450;
          Thread.sleep(2);
        }   
        movingToLeft();
        if(y > leftBar.getLayoutY() - 10 && y < leftBar.getLayoutY() + leftBar.getHeight() + 10)
          System.out.println("acertou");
        else{
          contRightScore++;
          score.setText(contLeftScore + " | " + contRightScore);
          Platform.runLater( () -> ball.setLayoutX(50));
          x=50;
          Thread.sleep(2);
        }          
      }
      score.setText("0 | 0");
    }
    catch(InterruptedException e){

    }
  }//end run

  public void movingToRight() throws InterruptedException{
    System.out.println(x);
    //the ball is going down
    while(x<BOARD_GAME_MAX_X-10 && on){
      if(!up){
      while(y<BOARD_GAME_MAX_Y && x<BOARD_GAME_MAX_X-10 && on){
        Thread.sleep(10);
        x++;
        Platform.runLater( () -> ball.setLayoutX(x));
        y++;
        Platform.runLater( () -> ball.setLayoutY(y));
      }
      if(y>=BOARD_GAME_MAX_Y)
        up = true;
      }
      System.out.println(x);
      //the ball is going up
      if(up){
      while(y>BOARD_GAME_MIN_Y && x<BOARD_GAME_MAX_X-10 && on){
        Thread.sleep(10);
        x++;
        Platform.runLater( () -> ball.setLayoutX(x));
        y--;
        Platform.runLater( () -> ball.setLayoutY(y));
      }//end while
      if(y<=BOARD_GAME_MIN_Y)
        up = false;
      }

      System.out.println(x);
      //System.out.println("1");
    }
  }//end movingToRight

  public void movingToLeft() throws InterruptedException{
    while (x > BOARD_GAME_MIN_X && on){
      //the ball is going up
      if(up){
      while(y>BOARD_GAME_MIN_Y && x > BOARD_GAME_MIN_X && on){
        Thread.sleep(10);
        x--;
        Platform.runLater( () -> ball.setLayoutX(x));
        y--;
        Platform.runLater( () -> ball.setLayoutY(y));
      }//end while
      if(y<=BOARD_GAME_MIN_Y)
        up = false;
      }

      //the ball is going down
      if(!up){
      while(y<BOARD_GAME_MAX_Y && x > BOARD_GAME_MIN_X && on){
        Thread.sleep(10);
        x--;
        Platform.runLater( () -> ball.setLayoutX(x));
        y++;
        Platform.runLater( () -> ball.setLayoutY(y));
      }
      if(y>=BOARD_GAME_MAX_Y)
        up = true;
      }
    }
  }//end movingToLeft

  public void setOn(boolean value){
    this.on = value;
  }
}

/**
 * TODO
 *    (DONE) planning how the ball will move
 *    (DONE) make a function to each state of moviment
 */