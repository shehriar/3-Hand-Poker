import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {
    PokerInfo message;
    int portNumber;
    String ipAddress;
    Socket connection;
    ObjectOutputStream out;
    ObjectInputStream in;
    private Consumer<Serializable> callback;

    Client(Consumer<Serializable> call, int portNumber, String ipAddress) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        this.callback = call;
    }

    public void run() {
        try {
            connection = new Socket(ipAddress, portNumber);
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
            connection.setTcpNoDelay(true);
        } catch (Exception ignored) {
        }
        while (true) {
            try {
                message = (PokerInfo) in.readObject();
            } catch (Exception ignored) {
            }
        }
    }

    public void send(PokerInfo data) {
        try {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
