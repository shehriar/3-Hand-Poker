import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server {
    int portNumber;
    ArrayList<ClientThread> clients = new ArrayList<>();
    ArrayList<PokerInfo> clientsInfo = new ArrayList<>();
    ArrayList<ThreeCardLogic> clientsLogic = new ArrayList<>();
    TheServer server;
    private Consumer<Serializable> callback;

    Server(Consumer<Serializable> call, int portNumber) {
        this.callback = call;
        this.server = new TheServer();
        this.server.start();
        this.portNumber = portNumber;
    }

    public class TheServer extends Thread {
        public void run() {
            try (ServerSocket servSock = new ServerSocket(portNumber);) {
                while (clients.size() < 4) {
                    PokerInfo pi = new PokerInfo();
                    clientsInfo.add(pi);
                    ThreeCardLogic tcl = new ThreeCardLogic();
                    clientsLogic.add(tcl);
                    ClientThread ct = new ClientThread(servSock.accept(), clients.size(), pi, tcl);
                    clients.add(ct);
                    callback.accept("A client has joined the server.");
                    callback.accept("Number of clients connected to the server: " + clients.size());
                    ct.start();
                }
            } catch(Exception e) {
                callback.accept("Server socket did not launch.");
            }
        }
    }

    class ClientThread extends Thread {
        PokerInfo pi;
        ThreeCardLogic tcl;
        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count, PokerInfo pi, ThreeCardLogic tcl) {
            this.connection = s;
            this.count = count;
            this.pi = pi;
            this.tcl = tcl;
        }

        public void updateClients(PokerInfo message) {
            ClientThread t = clients.get(count);
            try {
                t.out.writeObject(message);
            } catch(Exception ignored) {
            }
        }

        public void run() {
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            } catch(Exception ignored) {
            }
            while (true) {
                try {
                    PokerInfo data = (PokerInfo) in.readObject();
                    data.dealersCardsImg = tcl.dealers;
                    data.playersCardsImg = tcl.players;
                    data.dealerHasQueenOrHigher = tcl.dealerHasQueenOrHigher;
                    data.playerWins = tcl.playerWins;
                    data.winningsFold = tcl.calculateWinningsFold(data.pairPlus, data.ante);
                    data.winningsPlay = tcl.calculateWinningsPlay(data.pairPlus, data.ante);
                    if (tcl.playerPairPlusMultiplier == 40) {
                        data.ppCombo = "Straight flush";
                    } else if (tcl.playerPairPlusMultiplier == 30) {
                        data.ppCombo = "Three of a kind";
                    } else if (tcl.playerPairPlusMultiplier == 6) {
                        data.ppCombo = "Straight";
                    } else if (tcl.playerPairPlusMultiplier == 3) {
                        data.ppCombo = "Flush";
                    } else if (tcl.playerPairPlusMultiplier == 1) {
                        data.ppCombo = "Pair";
                    } else {
                        data.ppCombo = "None";
                    }
                    updateClients(data);
                    callback.accept("Client " + (count + 1) + " state:");
                    callback.accept("Wager[pair plus: " + data.pairPlus + " | Ante: " + data.ante + "]");
                    callback.accept("Winnings[fold: " + data.winningsFold + " | play: " + data.winningsPlay + "]");
                } catch (Exception e) {
                    callback.accept("A client has left the server.");
                    clients.remove(this);
                    break;
                }
            }
        }
    }
}
