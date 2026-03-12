import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

	public HTTPServer() {}

	public static void main(String[] args) throws IOException {

		int port = 18080;

		// The server side is slightly more complex
		// First we have to create a ServerSocket
		System.out.println("Opening the server socket on port " + port);
		ServerSocket serverSocket = new ServerSocket(port);

		// The ServerSocket listens and then creates as Socket object
		// for each incoming connection.
		System.out.println("Server waiting for client...");
		Socket clientSocket = serverSocket.accept();
		System.out.println("Client connected!");

		// Like files, we use readers and writers for convenience
		BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		Writer writer = new OutputStreamWriter(clientSocket.getOutputStream());

		// Read the first line of the HTTP request
		String requestLine = reader.readLine();
		System.out.println("Request: " + requestLine);

		// Read remaining headers until blank line
		String line;
		while (!(line = reader.readLine()).isEmpty()) {
    		System.out.println("Header: " + line);
		}

		// Split request line
		String[] parts = requestLine.split(" ");
		String method = parts[0];
		String path = parts[1];

		System.out.println("Method: " + method);
		System.out.println("Path: " + path);

		// If GET request for index
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
		// To make better use of bandwidth, messages are not sent
		// until the flush method is used

		// Close down the connection
		clientSocket.close();
	}
}