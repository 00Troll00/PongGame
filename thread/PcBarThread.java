package thread;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

public class PcBarThread extends Thread{
  private Rectangle bar;
  private double y;

  private boolean on = true;

  private final double MIN_Y = 0;
  private final double MAX_Y = 320;

  public PcBarThread(Rectangle bar){
    this.bar = bar;
    y = bar.getLayoutY();
  }

  @Override
  public void run(){
    try{
      while (on){
        moveDown();
        moveUp();
      }
    }
    catch (InterruptedException e){

    }
  }

  public void moveDown() throws InterruptedException{
    while(y < MAX_Y && on){
      y++;
      Platform.runLater( () -> bar.setLayoutY(y));
      Thread.sleep(2);
    }
  }

  public void moveUp() throws InterruptedException{
    while(y > MIN_Y && on){
      y--;
      Platform.runLater( () -> bar.setLayoutY(y));
      Thread.sleep(2);
    }
  }

  public void setOn(boolean value){
    this.on = value;
  }
}
