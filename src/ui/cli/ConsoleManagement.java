package ui.cli;

import routing.handler.EventHandler;

import java.util.Scanner;

public class ConsoleManagement {
    EventHandler inputHandler;
    private CliModeEnum mode= CliModeEnum.c;

    ParseCreate parseCreate;
    ParseDelete parseDelete;
    ParsePersistence parsePersistence;
    ParseRead parseRead;
    ParseUpdate parseUpdate;

    public ConsoleManagement(EventHandler inputHandler) {
        this.inputHandler = inputHandler;
        parseCreate = new ParseCreate(inputHandler);
        parseDelete = new ParseDelete(inputHandler);
        parsePersistence = new ParsePersistence(inputHandler);
        parseRead = new ParseRead(inputHandler);
        parseUpdate = new ParseUpdate(inputHandler);
    }

    public void run() {
        String input;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("# ");
            input = scanner.nextLine();
            if (input.charAt(0) == ':') {
                System.out.println(this.switchMode(input));
            } else {
                this.parseToEvent(input);
            }
        } while (!input.equals("0"));
        System.out.println("Bye");
    }

    private String switchMode(String input) {
        switch (input) {
            case ":c":
                this.mode = CliModeEnum.c;
                return "Insert mode";
            case ":d":
                this.mode = CliModeEnum.d;
                return "Deletion mode";
            case ":u":
                this.mode = CliModeEnum.u;
                return "Update mode";
            case ":r":
                this.mode = CliModeEnum.r;
                return "Read mode";
            case ":p":
                this.mode = CliModeEnum.p;
                return "Persistent mode";
            case ":config":
                this.mode = CliModeEnum.c;
                return "Config mode";
            default:
                return "Error: invalid mode specified";
        }
    }

    private void parseToEvent(String input) {
        switch (mode) {
            case c:
                parseCreate.execute(input);
            case d:
                parseDelete.execute(input);
            case u:
                parseUpdate.execute(input);
            case r:
                parseRead.execute(input);
            case p:
                parsePersistence.execute(input);
        }
    }

    public void writeToConsole(String string) {
        System.out.println(string);
    }

}
