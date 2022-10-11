package cli;

import routing.InputModeEnum;
import routing.handler.EventHandler;
import routing.parser.*;

import java.util.Scanner;

public class ConsoleManagement {
    private final ParseCreate parseCreate;
    private final ParseDelete parseDelete;
    private final ParsePersistence parsePersistence;
    private final ParseRead parseRead;
    private final ParseStorage parseStorage;
    private final ParseUpdate parseUpdate;
    private final Scanner scanner;
    boolean exitMarker = false;
    private InputModeEnum mode = InputModeEnum.c;

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
