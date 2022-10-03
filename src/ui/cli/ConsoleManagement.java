package ui.cli;

import routing.handler.EventHandler;

import java.util.Scanner;

public class ConsoleManagement {
    EventHandler inputHandler;
    private CliModeEnum mode= CliModeEnum.c;

    ParseCreate parseCreate;

    public ConsoleManagement(EventHandler inputHandler) {
        this.inputHandler = inputHandler;
        parseCreate = new ParseCreate(inputHandler);
    }

    public void run() {
        String input;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("# ");
            input = scanner.nextLine();
            if (input.charAt(0) == ':') {
                this.switchMode(input);
            } else {
                this.parseToEvent(input);
            }
        } while (!input.equals("0"));
        System.out.println("Bye");
    }

    private String switchMode(String input) {
        //todo print is missing
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
                /*
                case ":config": this.mode = 'g';
                return "Config mode";
             */
            default:
                return "Error: invalid mode specified";
        }
    }

    private void parseToEvent(String input) {
        switch (mode) {
            case c:
                parseCreate.execute(input);
            /*case d:
                this.mode = CliModeEnum.d;
                printer = "Deletion mode";
            case u:
                this.mode = CliModeEnum.u;
                printer = "Update mode";
            case r:
                this.mode = CliModeEnum.r;
                printer = "Read mode";
            case p:
                this.mode = CliModeEnum.p;
                printer = "Persistent mode";
            case c:
                this.mode = 'g';
                printer = "Config mode";

            default:
                printer = "Error: invalid mode specified";

             */
        }
    }

    public void writeToConsole(String string) {
        System.out.println(string);
    }

}
