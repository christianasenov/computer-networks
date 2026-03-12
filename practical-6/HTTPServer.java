import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

	public HTTPServer() {}

	public static void main(String[] args) throws IOException {

		int port = 18080;

		System.out.println("Opening the server socket on port " + port);
		ServerSocket serverSocket = new ServerSocket(port);

		while (true) {
			Socket clientSocket = serverSocket.accept();

			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			Writer writer = new OutputStreamWriter(clientSocket.getOutputStream());

			String requestLine = reader.readLine();
			System.out.println("Request: " + requestLine);

			String line = reader.readLine();
			while (!line.isEmpty()) {
				System.out.println(line);
			}

			String[] parts = requestLine.split(" ");
			String method = parts[0];
			String path = parts[1];

			System.out.println(method);
			System.out.println(path);

			if (method.equals("GET")) {
				String body = "<html><body><h1>Hello from HTTPServer</h1></body></html>";

				writer.write("HTTP/1.1 200 OK\r\n");
				writer.write("Content-Type: text/html\r\n");
				writer.write("Content-Length: " + body.length() + "\r\n");
				writer.write("Connection: close\r\n");
				writer.write("\r\n");
				writer.write(body);
			}
			else {
				String body = "<html><body><h1>400 Bad Request</h1></body></html>";

				writer.write("HTTP/1.1 400 Bad Request\r\n");
				writer.write("Content-Type: text/html\r\n");
				writer.write("Content-Length: " + body.length() + "\r\n");
				writer.write("Connection: close\r\n");
				writer.write("\r\n");
				writer.write(body);
			}

			writer.flush();
			clientSocket.close();
		}
	}
}