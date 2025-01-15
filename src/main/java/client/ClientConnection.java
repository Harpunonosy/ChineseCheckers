package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import game.board.StandardBoard.StandardBoard;
import utils.SerializationUtils;

public class ClientConnection {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientConnection(String serverAddress) throws IOException {
        socket = new Socket(serverAddress, 12345);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public StandardBoard deserializeBoard(String serializedBoard) { //Rozpakowywanie planszy
        return SerializationUtils.deserializeBoard(serializedBoard);
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}