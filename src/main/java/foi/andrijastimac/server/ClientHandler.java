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

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(client.getInputStream())
                );

                PrintWriter out = new PrintWriter(
                        client.getOutputStream()
                )
        ) {

            String requestLine = in.readLine();

            if (requestLine == null) {
                client.close();
                return;
            }

            System.out.println("REQUEST LINE:");
            System.out.println(requestLine);

            String[] parts = requestLine.split(" ");

            String path = parts[1];
            String method = parts[0];

            String header;
            int contentLength = 0;

            while ((header = in.readLine()) != null && !header.isEmpty()) {

                if (header.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(
                            header.substring(15).trim()
                    );
                }
            }

            String body = "";

            if (contentLength > 0) {

                char[] buffer = new char[contentLength];

                in.read(buffer);

                body = new String(buffer);
            }


            Router router = new Router();

            String content = router.route(
                    method,
                    path,
                    body
            );

            String contentType = "text/html";

            if (path.endsWith(".css")) {
                contentType = "text/css";
            }

            if (content.equals("404")) {

                out.println("HTTP/1.1 404 Not Found");
                out.println("Content-Type: text/html");
                out.println();
                out.println("<h1>404 Not Found</h1>");

            } else {

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: " + contentType + "; charset=utf-8");
                out.println();
                out.println(content);
            }

            out.flush();

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
