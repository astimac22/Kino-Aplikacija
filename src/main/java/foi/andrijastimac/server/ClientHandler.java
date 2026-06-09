package foi.andrijastimac.server;

import foi.andrijastimac.routes.Router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
    private final Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    public void handle() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream())){
            String requestLine = in.readLine();

            if (requestLine == null) {
                return;
            }

            String[] parts = requestLine.split(" ");
            String path = parts[1];

            Router router = new Router();

            String content = router.route(path);
            String contentType = path.endsWith(".css")
                    ? "text/css"
                    : "text/html";

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: " + contentType + "; charset=utf-8");
            out.println();
            out.println(content);
            out.flush();

            client.close();
        }
        catch (IOException e) {
                e.printStackTrace();
            }
        }
}
