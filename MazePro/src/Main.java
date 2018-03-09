import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.*;

public class Main extends Application{
	public static int delta = 2;
	public static ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
	Rectangle player;
	Rectangle finish;
	public long startTime;
	Group group;
	public boolean first = true;
	public boolean found = false;
	
	public static void main(String[] args) {
		launch(args);
	}
  
	@Override
	public void start(Stage primaryStage) throws Exception {
		MazeGenerator mg = new MazeGenerator(15,15);
		int counterx = 0;
		int countery = 0;
		int count = 0;
		group = new Group();
		int[][] maze = mg.getMaze();
		for(int[] x : maze){
			for(int z : x){
				if(z == 0){
					walls.add(new Rectangle(counterx, countery, 12,12));	
				}
				else{
					if(z==1 && first){
						player = new Rectangle(counterx, countery,13,13);
						player.setFill(Color.RED);
						group.getChildren().add(player);
						first = false;
					}
					counterx+= 15;
					continue;
				}
					counterx+=15;
					group.getChildren().add(walls.get(count));
					count++;
		    }
			counterx = 0;
			countery +=15;
		}
		for(int rows = maze.length-1; rows >= 0; -- rows){
			for(int cols = maze[rows].length-1; cols >= 0; -- cols){
			if(maze[rows][cols] == 1 && !found){
				finish = new Rectangle(rows*15, cols*15,13,13);
				finish.setFill(Color.GREEN);
				group.getChildren().add(finish);
				found = true;
				break;
			}
			}
		}
		Scene game = new Scene(group,462,462, Color.CORNSILK);
		moveRectangleOnKeyPress(game, player);
		primaryStage.setScene(game);
		primaryStage.setTitle("Maze");
		primaryStage.show();
		startTime = System.currentTimeMillis();
	}

	private void finish(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("No Winner");
		alert.setHeaderText("You touched the wall");
		alert.setContentText("You are not the winner!");
		alert.showAndWait();
		System.exit(0);
	}
	
	private void alertWinner(){
		long currentTime = System.currentTimeMillis();
		long hello = currentTime - startTime;
		double seconds = hello / 1000.0;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Winner");
		alert.setHeaderText("You are the Winner!!");
		alert.setContentText("You finishes the maze in " + seconds + " seconds");
		alert.showAndWait();
		System.exit(0);
	}

	private void moveRectangleOnKeyPress(Scene scene, final Rectangle rect) {
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	      @Override public void handle(KeyEvent event) {
	        switch (event.getCode()) {
	          case UP:    rect.setY(rect.getY() - delta); break;
	          case RIGHT: rect.setX(rect.getX() + delta); break;
	          case DOWN:  rect.setY(rect.getY() + delta); break;
	          case LEFT:  rect.setX(rect.getX() - delta); break;
	          default : break;
	        }
	        if (player.getBoundsInParent().intersects(finish.getBoundsInParent())) {
				alertWinner();
	        }
	        for(int i = 0; i < walls.size(); ++i){
				if (player.getBoundsInParent().intersects(walls.get(i).getBoundsInParent())) {
						finish();
				}
		    }
	      }
	    });
	  }
}