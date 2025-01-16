package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import game.board.StandardBoard.StandardBoard;
import utils.message.Message;
import utils.message.MessageUtils;
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

    public void sendMessage(Message message) {
        String json = MessageUtils.serializeMessage(message);
        out.println(json);
    }

    public Message receiveMessage() throws IOException {
        String json = in.readLine();
        return MessageUtils.deserializeMessage(json);
    }

    public StandardBoard deserializeBoard(String serializedBoard) {
        return SerializationUtils.deserializeBoard(serializedBoard);
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}