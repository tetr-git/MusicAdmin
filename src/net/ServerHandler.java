package net;

import routing.handler.EventHandler;
import routing.parser.*;
import util.InputModeEnum;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler {

    private EventHandler inputHandler = null;
    private final int port;
    private DataInputStream in;
    private DataOutputStream out;
    private InputModeEnum mode = InputModeEnum.c;
    private final ParseCreate parseCreate;
    private final ParseDelete parseDelete;
    private final ParsePersistence parsePersistence;
    private final ParseRead parseRead;
    private final ParseStorage parseStorage;
    private final ParseUpdate parseUpdate;
    boolean exitMarker = false;
    ArrayList<Integer> ports;

    public ServerHandler(EventHandler inputHandler, int port) {
        this.inputHandler = inputHandler;
        this.port = port;
        parseCreate = new ParseCreate(inputHandler);
        parseDelete = new ParseDelete(inputHandler);
        parsePersistence = new ParsePersistence(inputHandler);
        parseRead = new ParseRead(inputHandler);
        parseStorage = new ParseStorage(inputHandler);
        parseUpdate = new ParseUpdate(inputHandler);
        ports = new ArrayList<>();
        ports.add(port);
        ports.add(port+1);
    }

    public void run(){
        try(ServerSocket serverSocket=new ServerSocket(port)) {
            try {
                Socket socket=serverSocket.accept();
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                System.out.println("client: "+socket.getInetAddress()+":"+socket.getPort());
                while (true){
                    //out.writeInt(-in.readInt());
                    //out.writeUTF("1:"+ in.readUTF());
                    this.handleInput(in.readChar(),in.readUTF());
                }
            } catch (EOFException e) {
                System.out.println("client disconnect");
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runMulti(){
        boolean add = false;
        while (true) {
            int newport = 0;
            if (add) {
                ports.add(newport);
                add= false;
            }
            for (int portNumber : ports) {
                if (portNumber==1001) {
                    try(ServerSocket serverSocket=new ServerSocket(portNumber)) {
                        try {
                            Socket socket=serverSocket.accept();
                            in = new DataInputStream(socket.getInputStream());
                            out = new DataOutputStream(socket.getOutputStream());
                            newport = ports.size()+1001;
                            System.out.println("client: "+socket.getInetAddress()+":"+newport);
                            out.writeInt(newport);
                            add = true;
                            socket.close();
                        } catch (EOFException e) {
                            System.out.println("added client/ client disconnected on standardport 1001");
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try(ServerSocket serverSocket=new ServerSocket(portNumber)) {
                        try {
                            Socket socket=serverSocket.accept();
                            in = new DataInputStream(socket.getInputStream());
                            out = new DataOutputStream(socket.getOutputStream());
                            this.handleInput(in.readChar(),in.readUTF());
                        } catch (EOFException e) {
                            System.out.println("client disconnect");
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void handleInput(char modeChar, String input) {
        for(InputModeEnum m: InputModeEnum.values()){
            char c = m.toString().charAt(0);
            int i = Character.compare(c, modeChar);
            if (i==0) {
                mode = m;
            }
        }
        this.parseToEvent(input);
    }

    private void parseToEvent(String input) {
        switch (mode) {
            case c:
                parseCreate.execute(input);
                break;
            case d:
                parseDelete.execute(input);
                break;
            case u:
                parseUpdate.execute(input);
                break;
            case r:
                parseRead.execute(input);
                break;
            case p:
                parsePersistence.execute(input);
                break;
            case s:
                parseStorage.execute(input);
                break;
        }
    }

    public void writeToConsole(String string) {
        try {
            out.writeUTF(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(string);
    }
}
