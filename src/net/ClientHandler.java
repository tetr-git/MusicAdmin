package net;

import util.InputModeEnum;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler {

    private InputModeEnum mode = InputModeEnum.c;
    private final int port;
    private int clientInstancePort;
    Scanner scanner;
    boolean exitMarker = false;

    public ClientHandler(int port) {
        this.port = port;
        scanner = new Scanner(System.in);
    }

    public void run() {
        try (Socket socket = new Socket("localhost", port);
             DataInputStream in=new DataInputStream(socket.getInputStream());
             DataOutputStream out=new DataOutputStream(socket.getOutputStream())) {
            Scanner scanner = new Scanner(System.in);
            String input;
            do {
                System.out.print(mode.toString() + "# ");
                input = scanner.nextLine();
                if (input.charAt(0) == ':') {
                    switchMode(input);
                } else {
                    out.writeChar(mode.toChar());
                    out.writeUTF(input);
                    System.out.println(in.readUTF());
                }
            } while (!exitMarker);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*

             DataInputStream in=new DataInputStream(socket.getInputStream());
             DataOutputStream out=new DataOutputStream(socket.getOutputStream());) {
            clientInstancePort = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Socket socket = new Socket("localhost", clientInstancePort);
     */

    private void switchMode(String input) {
        switch (input) {
            case ":c":
                mode = InputModeEnum.c;
                break;
            case ":d":
                mode = InputModeEnum.d;
                break;
            case ":u":
                mode = InputModeEnum.u;
                break;
            case ":r":
                mode = InputModeEnum.r;
                break;
            case ":p":
                mode = InputModeEnum.p;
                break;
            case ":s":
                mode = InputModeEnum.s;
                break;
            case ":exit":
                exitMarker = true;
                break;
            default:
                System.out.println("Error: invalid mode specified");
                break;
        }
    }
}
