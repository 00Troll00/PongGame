package view_generators;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import util.Util;

public class Menu extends VBox{
  public final double MENU_SIZE_X = 150;
  public final double MENU_SIZE_Y = 400;
  //buttons
  private Button btnStart;
  private Button btnTwoPlayers;
  
  //the label that shows the score
  private Label score;
  
  //boolean variable that is true if two players option is selected (default = false)
  private boolean isTwoPlayersSelected = false;

  //region to divide the menu
  private Region region;
  
  //gameboard
  private GameBoard gameBoard;
  
  //hbox to the two players option
  private HBox hBoxTwoPlayers;

  public Menu(GameBoard gameBoard){
    super();

    this.gameBoard = gameBoard;

    //configs to the btnStart
    btnStart = new Button("START");
    btnStart.setMinSize(140, 35);
    startResetGame();
    //setting the buttons on mouse enter and exited
    Util.setButtonStyleConfiguration(btnStart);

    //configs to the btnTwoPlayers
    btnTwoPlayers = new Button(" ");
    btnTwoPlayers.setMinSize(31, 31);
    onOffTwoPlayers();
    btnTwoPlayersStyleConfiguration();

    //configs to the hbox
    hBoxTwoPlayers = new HBox();
    Label text = new Label("Two Players");
    HBox.setMargin(text, new Insets(4, 0, 0, 0));
    hBoxTwoPlayers.getChildren().addAll(btnTwoPlayers, text);
    hBoxTwoPlayers.setSpacing(5);

    //configs to the region
    region = new Region();
    region.setPrefSize(2, 2);
    VBox.setVgrow(region, Priority.ALWAYS);

    //Text that has "SCORE" in it
    Label scoreLabel= new Label("SCORE");

    //config to the score
    score = new Label("0 | 0");

    super.setPadding(new Insets(5, 5, 5, 5));
    super.setWidth(MENU_SIZE_X);
    super.setMaxWidth(MENU_SIZE_X);
    super.setHeight(MENU_SIZE_Y);
    super.setSpacing(5);
    super.setAlignment(Pos.CENTER);
    super.getChildren().addAll(scoreLabel, score, hBoxTwoPlayers, region, btnStart);
  }

  public void startResetGame(){
    btnStart.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e){
        gameBoard.startResetGame(btnStart, isTwoPlayersSelected, score);
      }
    });
  }//end str

  public void onOffTwoPlayers(){
    btnTwoPlayers.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e){
        if(isTwoPlayersSelected){
          isTwoPlayersSelected = false;
          btnTwoPlayers.setText(" ");
        }
        else{
          isTwoPlayersSelected = true;
          btnTwoPlayers.setText("X");
        }
      }
    });
  }

  public void btnTwoPlayersStyleConfiguration(){
    btnTwoPlayers.setOnMouseEntered(m -> {
      btnTwoPlayers.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-radius: 30px;");
    });
    btnTwoPlayers.setOnMouseExited(m-> {
      btnTwoPlayers.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-radius: 0px;");
    });
  }
  
}
