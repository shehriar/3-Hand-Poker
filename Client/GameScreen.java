import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameScreen {
    BorderPane bp = new BorderPane();
    MenuBar mb;
    Menu menu;
    VBox vb;
    HBox hb1, hb2, hb3, hb4;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9;
    Button b1, b2;
    Label l1, l2, l3, l4, l5, winnings, ppInfo, announcement;
    Client c;

    public GameScreen(Client c, int pp, int a, Stage primaryStage) {
        bp.getStyleClass().add("bp");
        this.c = c;
        menu();
        vb = new VBox(25);
        vb.setAlignment(Pos.CENTER);
        header();
        dealerCards();
        announcement = new Label("Three Card Poker");
        announcement.getStyleClass().add("title");
        winnings.setAlignment(Pos.CENTER);
        playerWagerOptions(pp, a, primaryStage);
        playerCards();
        vb.getChildren().addAll(hb4, hb1, l4, announcement, hb2, l5, hb3);
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
        });
        MenuItem style2 = new MenuItem("Neon");
        style2.setOnAction(event -> {
            Scene scene = bp.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add("style-neon.css");
        });
        MenuItem style3 = new MenuItem("Vintage");
        style3.setOnAction(event -> {
            Scene scene = bp.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add("style-vintage.css");
        });
        MenuItem style4 = new MenuItem("Minimalist");
        style4.setOnAction(event -> {
            Scene scene = bp.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add("style-minimalist.css");
        });
        mi3.getItems().addAll(style1, style2, style3, style4);
        menu.getItems().addAll(mi1, mi2, mi3);
        mi1.setOnAction(event -> {
            Platform.exit();
        });
        mi2.setOnAction(event -> {
        });
    }

    public void header() {
        hb4 = new HBox(50);
        hb4.setAlignment(Pos.CENTER);
        winnings = new Label("");
        winnings.getStyleClass().add("l");
        winnings.setAlignment(Pos.BASELINE_LEFT);
        ppInfo = new Label("");
        ppInfo.getStyleClass().add("l");
        ppInfo.setAlignment(Pos.BASELINE_RIGHT);
        hb4.getChildren().addAll(winnings, ppInfo);
    }

    public void dealerCards() {
        l4 = new Label("Dealer's Cards");
        l4.getStyleClass().add("l");
        hb1 = new HBox(10);
        hb1.setAlignment(Pos.CENTER);
        iv1 = showCard("cardback.png");
        iv2 = showCard("cardback.png");
        iv3 = showCard("cardback.png");
        hb1.getChildren().addAll(iv1, iv2, iv3);
    }

    public void playerCards() {
        l5 = new Label("Player's Cards");
        l5.getStyleClass().add("l");
        hb3 = new HBox(10);
        hb3.setAlignment(Pos.CENTER);
        iv7 = showCard(c.message.playersCardsImg.get(0));
        iv8 = showCard(c.message.playersCardsImg.get(1));
        iv9 = showCard(c.message.playersCardsImg.get(2));
        hb3.getChildren().addAll(iv7, iv8, iv9);
    }

    public ImageView showCard(String image) {
        ImageView card = new ImageView(new Image(image));
        card.setFitHeight(130);
        card.setFitWidth(100);
        return card;
    }

    public void playAgain(Stage primaryStage){
        PokerInfo pi = new PokerInfo();
        WagerScreen ws = new WagerScreen(pi, c, primaryStage);
        Scene scene = new Scene(ws.bp, 800,800);
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("Wager");
        primaryStage.setScene(scene);
    }

    public void playerWagerOptions(int pp, int a, Stage primaryStage) {
        Button playAgain = new Button("Replay");
        playAgain.getStyleClass().add("b");
        playAgain.setOnAction(e -> playAgain(primaryStage));
        Button exit = new Button("Exit");
        exit.getStyleClass().add("b");
        exit.setOnAction(e -> {
            Platform.exit();
        });
        hb2 = new HBox(20);
        hb2.setAlignment(Pos.CENTER);
        b1 = new Button("Fold");
        b1.getStyleClass().add("b");
        b1.setOnAction(e -> {
            updateGameScreen();
            winnings.setText("How much you won/lost : " + c.message.winningsFold);
            hb2.getChildren().removeAll(b1, iv4, l1, iv5, l2, iv6, l3, b2);
            announcement.setText("You Fold!");
            hb2.getChildren().addAll(playAgain, iv4, l1, iv5, l2, iv6, l3, exit);
        });
        iv4 = new ImageView(new Image("pairplus.png"));
        iv4.setFitHeight(80);
        iv4.setFitWidth(80);
        l1 = new Label("" + pp);
        l1.getStyleClass().add("l");
        iv5 = new ImageView(new Image("ante.png"));
        iv5.setFitHeight(80);
        iv5.setFitWidth(80);
        l2 = new Label("" + a);
        l2.getStyleClass().add("l");
        iv6 = new ImageView(new Image("play.png"));
        iv6.setFitHeight(80);
        iv6.setFitWidth(80);
        l3 = new Label("" + 0);
        l3.getStyleClass().add("l");
        b2 = new Button("Play");
        b2.getStyleClass().add("b");
        b2.setOnAction(e -> {
            updateGameScreen();
            l3.setText("" + a);
            if (!c.message.dealerHasQueenOrHigher) {
                winnings.setText("How much you won/lost : 0");
                announcement.setText("Dealer's Hand Invalid!");
            } else {
                winnings.setText("How much you won/lost : " + c.message.winningsPlay);
                if (c.message.playerWins) {
                    announcement.setText("You Win!");
                } else {
                    announcement.setText("You Lose!");
                }
            }
            hb2.getChildren().removeAll(b1, iv4, l1, iv5, l2, iv6, l3, b2);
            hb2.getChildren().addAll(playAgain, iv4, l1, iv5, l2, iv6, l3, exit);
        });
        hb2.getChildren().addAll(b1, iv4, l1, iv5, l2, iv6, l3, b2);
    }

    public void updateGameScreen() {
        iv1.setImage(new Image(c.message.dealersCardsImg.get(0)));
        iv2.setImage(new Image(c.message.dealersCardsImg.get(1)));
        iv3.setImage(new Image(c.message.dealersCardsImg.get(2)));
        ppInfo.setText("Pair Plus : " + c.message.ppCombo);
    }
}