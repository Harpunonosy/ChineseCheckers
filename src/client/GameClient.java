import java.io.IOException;

public class GameClient {
    private ClientConnection connection;
    private ClientInputHandler inputHandler;
    private ClientOutputHandler outputHandler;

    public GameClient(String serverAddress) throws IOException {
        connection = new ClientConnection(serverAddress);
        inputHandler = new ClientInputHandler(connection);
        outputHandler = new ClientOutputHandler(connection);
    }

    public void start() {
        new Thread(outputHandler).start();
        inputHandler.handleInput();
    }

    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient("localhost");
        client.start();
    }
}