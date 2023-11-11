import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ServerScreen {
    BorderPane bp = new BorderPane();
    VBox vb;
    Button b;

    public ServerScreen(ListView<String> lv) {
        lv.getStyleClass().add("lv");
        bp.setStyle("-fx-background-color: #8c8c8c;");
        vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(10));
        b = new Button("Off");
        b.setOnAction(e -> {
        });
        b.getStyleClass().add("b");
        vb.getChildren().addAll(lv, b);
        bp.setCenter(vb);
    }
}