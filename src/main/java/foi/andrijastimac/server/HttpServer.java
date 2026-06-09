package foi.andrijastimac.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)){

            System.out.println("Server started on port " + port);
            while (true) {
                Socket client = serverSocket.accept();

                ClientHandler handler = new ClientHandler(client);
                handler.handle();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
