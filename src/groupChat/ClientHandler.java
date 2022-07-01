package groupChat;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;

public class ClientHandler implements Runnable   {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

        @Override
        public void run() {
            String messageFromClient;

            while (socket.isConnected()) {
                try {
                    messageFromClient = bufferedReader.readLine();
                    broadcastMessage(messageFromClient);
                } catch (IOException | ConcurrentModificationException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }
            }
        }

    public void broadcastMessage(String messageToSend) {
            for (ClientHandler clientHandler : clientHandlers) {
                String commands = "/commands";
                String time = "/time";
                String help = "/help";
                String url1 = "http://www.google.com";
                try {
                    if (!clientHandler.clientUsername.equals(clientUsername) && !messageToSend.contains(commands) && !messageToSend.contains(help) && !messageToSend.contains(time)) {
                        clientHandler.bufferedWriter.write(messageToSend);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();

                    }
                    if(clientHandler.clientUsername.equals(clientUsername)) {
                        if(messageToSend.contains(commands)) {
                            clientHandler.bufferedWriter.write("Available commands: "+time+", "+help);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();

                        } else if(messageToSend.contains(time)) {
                            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());

                            clientHandler.bufferedWriter.write(timeStamp);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();

                        } else if(messageToSend.contains(help)) {
                            Desktop desktop = java.awt.Desktop.getDesktop();
                            URI url = new URI(url1);
                            clientHandler.bufferedWriter.write("opening " + url);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                            desktop.browse(url);
                        }
                    }
                } catch (IOException | URISyntaxException | ConcurrentModificationException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
