import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientWelcomeScreen extends Application {
	BorderPane bp = new BorderPane();
	VBox vb;
	HBox hb1, hb2;
	Label l1, l2, l3, title;
	TextField tf1, tf2;
	Button b;
	PokerInfo pi = new PokerInfo();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		bp.getStyleClass().add("bp");
		vb = new VBox(50);
		vb.setAlignment(Pos.CENTER);
		title = new Label("Three Card Poker");
		title.getStyleClass().add("title");
		l3 = new Label("Please enter server port number and IP address");
		l3.getStyleClass().add("l");
		hb1 = new HBox(20);
		hb1.setAlignment(Pos.CENTER);
		l1 = new Label("Port number :");
		l1.getStyleClass().add("l");
		tf1 = new TextField();
		tf1.setStyle("-fx-background-color: #ffffff; fx-border-color: #A8BD60; -fx-font-family: Monaco; -fx-font-size: 15; fx-text-fill: black;");
		hb1.getChildren().addAll(l1, tf1);
		hb2 = new HBox(20);
		hb2.setAlignment(Pos.CENTER);
		l2 = new Label("IP Address :");
		l2.getStyleClass().add("l");
		tf2 = new TextField();
		tf2.setStyle("-fx-background-color: #ffffff; fx-border-color: #A8BD60; -fx-font-family: Monaco; -fx-font-size: 15; fx-text-fill: black;");
		hb2.getChildren().addAll(l2, tf2);
		b = new Button("Next");
		b.getStyleClass().add("b");
		b.setOnAction(e->{
			try {
				int portNumber = Integer.parseInt(tf1.getText());
				String ipAddress = tf2.getText();
				Client c = new Client(data -> {
					Platform.runLater(()->{
					});
				}, portNumber, ipAddress);
				c.start();
				WagerScreen ws = new WagerScreen(pi, c, primaryStage);
				Scene scene = new Scene(ws.bp, 800,800);
				scene.getStylesheets().add("style.css");
				primaryStage.setTitle("Wager");
				primaryStage.setScene(scene);
			} catch (Exception ignored) {
				tf1.clear();
				tf1.clear();
			}
		});
		vb.getChildren().addAll(title, l3, hb1, hb2, b);
		bp.setCenter(vb);
		Scene scene = new Scene(bp, 800,800);
		scene.getStylesheets().add("style.css");
		primaryStage.setTitle("Client Portal");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}