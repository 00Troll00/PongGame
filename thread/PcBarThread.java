package thread;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

public class PcBarThread extends Thread{
  private Rectangle bar;
  private double y;

  private final double MIN_Y = 0;
  private final double MAX_Y = 320;

  public PcBarThread(Rectangle bar){
    this.bar = bar;
  }

  @Override
  public void run(){
    try{
      while (true){
        moveDown();
        moveUp();
      }
    }
    catch (InterruptedException e){

    }
  }

  public void moveDown() throws InterruptedException{
    while(y < MAX_Y){
      y++;
      Platform.runLater( () -> bar.setY(y));
      Thread.sleep(2);
    }
  }

  public void moveUp() throws InterruptedException{
    while(y > MIN_Y){
      y--;
      Platform.runLater( () -> bar.setY(y));
      Thread.sleep(2);
    }
  }
}
