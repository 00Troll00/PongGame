package thread;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

public class BallThread extends Thread{
  private double x;
  private double y;
  private Rectangle ball;
  private Rectangle leftBar;
  private Rectangle rightBar;
  private boolean up = false;
  private final double BOARD_GAME_MIN_X = 10;
  private final double BOARD_GAME_MAX_X = 505;
  private final double BOARD_GAME_MIN_Y = 0;
  private final double BOARD_GAME_MAX_Y = 400;


  public BallThread(Rectangle ball, Rectangle leftBar, Rectangle rightBar){
    this.rightBar = rightBar;
    this.leftBar = leftBar;
    this.ball = ball;
    this.x = ball.getX();
    this.y = ball.getY();
  }

  @Override
  public void run(){
    try{
      while(true){
        movingToRight();
        if(y > rightBar.getY() - 10 && y < rightBar.getY() + rightBar.getHeight() + 10)
          System.out.println("acertou");
        else{
          Platform.runLater( () -> ball.setX(450));
          x=450;
          Thread.sleep(2);
        }   
        movingToLeft();
        if(y > leftBar.getY() - 10 && y < leftBar.getY() + leftBar.getHeight() + 10)
          System.out.println("acertou");
        else{
          Platform.runLater( () -> ball.setX(50));
          x=50;
          Thread.sleep(2);
        }          
      }
    }
    catch(InterruptedException e){

    }
  }//end run

  public void movingToRight() throws InterruptedException{
    System.out.println(x);
    //the ball is going down
    while(x<BOARD_GAME_MAX_X-10){
      if(!up){
      while(y<BOARD_GAME_MAX_Y && x<BOARD_GAME_MAX_X-10){
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
      while(y>BOARD_GAME_MIN_Y && x<BOARD_GAME_MAX_X-10){
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
    while (x > BOARD_GAME_MIN_X){
      //the ball is going up
      if(up){
      while(y>BOARD_GAME_MIN_Y && x > BOARD_GAME_MIN_X){
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
      while(y<BOARD_GAME_MAX_Y && x > BOARD_GAME_MIN_X){
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
}

/**
 * TODO
 *    (DONE) planning how the ball will move
 *    (DONE) make a function to each state of moviment
 */