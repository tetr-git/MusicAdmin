package ui.cli;

import routing.handler.EventHandler;
import routing.parser.*;

import java.util.Scanner;

public class ConsoleManagement {
    private CliModeEnum mode = CliModeEnum.c;

    ParseCreate parseCreate;
    ParseDelete parseDelete;
    ParsePersistence parsePersistence;
    ParseRead parseRead;
    ParseStorage parseStorage;
    ParseUpdate parseUpdate;
    Scanner scanner;
    boolean exitMarker = false;

    public ConsoleManagement(EventHandler inputHandler) {
        parseCreate = new ParseCreate(inputHandler);
        parseDelete = new ParseDelete(inputHandler);
        parsePersistence = new ParsePersistence(inputHandler);
        parseRead = new ParseRead(inputHandler);
        parseStorage = new ParseStorage(inputHandler);
        parseUpdate = new ParseUpdate(inputHandler);
        scanner = new Scanner(System.in);
    }

    public void run() {
        String input;
        do {
            System.out.print(mode.toString() + "# ");
            input = scanner.nextLine();
            if (input.charAt(0) == ':') {
                switchMode(input);
            } else {
                parseToEvent(input);
            }
        } while (!exitMarker);
    }

    private void switchMode(String input) {
        switch (input) {
            case ":c":
                mode = CliModeEnum.c;
                break;
            case ":d":
                mode = CliModeEnum.d;
                break;
            case ":u":
                mode = CliModeEnum.u;
                break;
            case ":r":
                mode = CliModeEnum.r;
                break;
            case ":p":
                mode = CliModeEnum.p;
                break;
            case ":s":
                mode = CliModeEnum.s;
                break;
            case ":exit":
                exitMarker = true;
                break;
            default:
                System.out.println("Error: invalid mode specified");
                break;
        }
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
        System.out.println(string);
    }

}
