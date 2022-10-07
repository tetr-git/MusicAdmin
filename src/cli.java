import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import routing.handler.EventHandler;
import routing.listener.CliOutputListener;
import routing.listener.CreateMediaListener;
import routing.listener.CreateUploaderListener;
import ui.cli.ConsoleManagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;

import static util.TypeConverter.addInteger;
import static util.TypeConverter.isNumericInteger;

public class cli {



    public static void main(String[] args) {

        int mediaFileRepositorySize = 10000000;

        if (args[0].equalsIgnoreCase("udp")) {
            System.out.println("not implemented yet");
        } else if (args[0].equalsIgnoreCase("tcp")) {
            System.out.println("not implemted yet");
        } else
        if (isNumericInteger(args[0])) {
            mediaFileRepositorySize = addInteger(args[0]);
            MediaFileRepoList mediaFileRepoList = new MediaFileRepoList(new BigDecimal(mediaFileRepositorySize));
            EventHandler inputHandler = new EventHandler();
            ConsoleManagement consoleManagement = new ConsoleManagement(inputHandler);
            EventHandler outputHandler = new EventHandler();
            inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new CreateUploaderListener(mediaFileRepoList,outputHandler));
            CliOutputListener cliOutputListener = new CliOutputListener(consoleManagement);
            outputHandler.add(cliOutputListener);
            consoleManagement.run();
            }
    }
}
