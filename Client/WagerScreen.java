import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WagerScreen {
    BorderPane bp = new BorderPane();
    MenuBar mb;
    Menu menu;
    VBox vb;
    HBox hb1, hb2;
    ImageView iv1, iv2;
    Slider s1, s2;
    Label l1, l2;
    Button b;
    CheckBox cb;
    int ante = 5, pairPlus = 5;
    String style = "style.css";

    public WagerScreen(PokerInfo pi, Client c, Stage primaryStage) {
        bp.getStyleClass().add("bp");
        menu();
        vb = new VBox(50);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(50));
        pickAnte();
        pickPairPlus();
        b = new Button("Next");
        b.getStyleClass().add("b");
        b.setOnAction(e -> {
            pi.ante = this.ante;
            if (cb.isSelected()) {
                pi.pairPlus = this.pairPlus;
            } else {
                pi.pairPlus = 0;
            }
            c.send(pi);
            GameScreen gs = new GameScreen(c, pi.pairPlus, pi.ante, primaryStage);
            Scene scene = new Scene(gs.bp, 800,800);
            scene.getStylesheets().add(style);
            primaryStage.setTitle("Three Card Poker");
            primaryStage.setScene(scene);
        });
        vb.getChildren().addAll(hb1, hb2, b);
        bp.setCenter(vb);
    }

    public void menu() {
        mb = new MenuBar();
        bp.setTop(mb);
        menu = new Menu("Options");
        mb.getMenus().add(menu);
        MenuItem mi1 = new MenuItem("Exit");
        MenuItem mi2 = new MenuItem("Fresh Start");
        Menu mi3 = new Menu("New Look");
        MenuItem style1 = new MenuItem("Default");
        style1.setOnAction(event -> {
            Scene scene = bp.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add("style.css");
            style = "style.css";
        });
        MenuItem style2 = new MenuItem("Neon");
        style2.setOnAction(event -> {
            Scene scene = bp.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add("style-neon.css");
            style = "style-neon.css";
        });
        MenuItem style3 = new MenuItem("Vintage");
        style3.setOnAction(event -> {
            Scene scene = bp.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add("style-vintage.css");
            style = "style-vintage.css";
        });
        MenuItem style4 = new MenuItem("Minimalist");
        style4.setOnAction(event -> {
            Scene scene = bp.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add("style-minimalist.css");
            style = "style-minimalist.css";
        });
        mi3.getItems().addAll(style1, style2, style3, style4);
        menu.getItems().addAll(mi1, mi2, mi3);
        mi1.setOnAction(event -> {
            Platform.exit();
        });
        mi2.setOnAction(event -> {
        });
    }

    public void pickAnte() {
        hb1 = new HBox(20);
        hb1.setAlignment(Pos.CENTER_LEFT);
        iv1 = new ImageView(new Image("ante.png"));
        iv1.setFitHeight(130);
        iv1.setFitWidth(130);
        s1 = new Slider(5, 25, 5);
        s1.getStyleClass().add("s");
        l1 = new Label("5");
        l1.getStyleClass().add("l");
        s1.valueProperty().addListener((observable, oldValue, newValue) -> {
            l1.setText(String.valueOf(newValue.intValue()));
            this.ante = newValue.intValue();
        });
        hb1.getChildren().addAll(iv1, s1, l1);
    }

    public void pickPairPlus() {
        hb2 = new HBox(20);
        hb2.setAlignment(Pos.CENTER_LEFT);
        iv2 = new ImageView(new Image("pairplus.png"));
        iv2.setFitHeight(130);
        iv2.setFitWidth(130);
        s2 = new Slider(5, 25, 5);
        s2.getStyleClass().add("s");
        l2 = new Label("5");
        l2.getStyleClass().add("l");
        s2.valueProperty().addListener((observable, oldValue, newValue) -> {
            l2.setText(String.valueOf(newValue.intValue()));
            this.pairPlus = newValue.intValue();
        });
        cb = new CheckBox();
        cb.getStyleClass().add("cb");
        hb2.getChildren().addAll(iv2, s2, l2, cb);
    }
}