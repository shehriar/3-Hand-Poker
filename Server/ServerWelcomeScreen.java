import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerWelcomeScreen extends Application {
	BorderPane bp = new BorderPane();
	VBox vb;
	HBox hb;
	Label l;
	TextField tf;
	Button b;
	ListView<String> lv = new ListView<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		bp.setStyle("-fx-background-color: #8c8c8c;");
		vb = new VBox(50);
		vb.setAlignment(Pos.CENTER);
		hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		l = new Label("Please enter a port number :");
		l.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 15; -fx-text-fill: rgba(234,234,124,0.65);");
		tf = new TextField();
		tf.getStyleClass().add("tf");
		hb.getChildren().addAll(l, tf);
		b = new Button("On");
		b.getStyleClass().add("b");
		b.setOnAction(e->{
			try {
				int portNumber = Integer.parseInt(tf.getText());
				Server s = new Server(data -> {
					Platform.runLater(()->{
						lv.getItems().add( data.toString());
					});
				}, portNumber);
				ServerScreen ss = new ServerScreen(lv);
				Scene scene = new Scene(ss.bp, 800,800);
				scene.getStylesheets().add("style.css");
				primaryStage.setTitle("Server");
				primaryStage.setScene(scene);
			} catch (Exception t) {
				tf.clear();
				tf.setPromptText("Invalid port number!");
			}
		});
		vb.getChildren().addAll(hb, b);
		bp.setCenter(vb);
		Scene scene = new Scene(bp, 800,800);
		scene.getStylesheets().add("style.css");
		primaryStage.setTitle("Server Portal");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}