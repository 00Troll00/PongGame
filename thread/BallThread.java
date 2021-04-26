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
  private final double BOARD_GAME_MIN_X = 10;
  private final double BOARD_GAME_MAX_X = 505;
  private final double BOARD_GAME_MIN_Y = 0;
  private final double BOARD_GAME_MAX_Y = 400;


  public BallThread(Rectangle ball, Rectangle leftBar, Rectangle rightBar, Text score){
    this.score = score;
    this.rightBar = rightBar;
    this.leftBar = leftBar;
    this.ball = ball;
    this.x = ball.getX();
    this.y = ball.getY();
  }

  @Override
  public void run(){
    try{
      while(on){
        movingToRight();
        if(y > rightBar.getY() - 10 && y < rightBar.getY() + rightBar.getHeight() + 10)
          System.out.println("acertou");
        else{
          contLeftScore++;
          score.setText(contLeftScore + " | " + contRightScore);
          Platform.runLater( () -> ball.setX(450));
          x=450;
          Thread.sleep(2);
        }   
        movingToLeft();
        if(y > leftBar.getY() - 10 && y < leftBar.getY() + leftBar.getHeight() + 10)
          System.out.println("acertou");
        else{
          contRightScore++;
          score.setText(contLeftScore + " | " + contRightScore);
          Platform.runLater( () -> ball.setX(50));
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
        Platform.runLater( () -> ball.setX(x));
        y++;
        Platform.runLater( () -> ball.setY(y));
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
        Platform.runLater( () -> ball.setX(x));
        y--;
        Platform.runLater( () -> ball.setY(y));
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
        Platform.runLater( () -> ball.setX(x));
        y--;
        Platform.runLater( () -> ball.setY(y));
      }//end while
      if(y<=BOARD_GAME_MIN_Y)
        up = false;
      }

      //the ball is going down
      if(!up){
      while(y<BOARD_GAME_MAX_Y && x > BOARD_GAME_MIN_X && on){
        Thread.sleep(10);
        x--;
        Platform.runLater( () -> ball.setX(x));
        y++;
        Platform.runLater( () -> ball.setY(y));
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