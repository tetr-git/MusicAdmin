import domain_logic.MediaFileRepoList;
import observer.OberserverTyp;
import routing.handler.EventHandler;
import routing.listener.*;
import ui.cli.ConsoleManagement;

import java.math.BigDecimal;

import static util.TypeConverter.addInteger;
import static util.TypeConverter.isNumericInteger;

public class Cli {

    public static void main(String[] args) {
        boolean startCli = false;
        int mediaFileRepositorySize = 10000000;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("udp")) {
                System.out.println("not implemented yet");
            } else if (args[0].equalsIgnoreCase("tcp")) {
                System.out.println("not implemted yet");
            } else if (isNumericInteger(args[0])) {
                mediaFileRepositorySize = addInteger(args[0]);
                startCli = true;
            }
        } else {
            startCli = true;
        }
        if (startCli) {
            MediaFileRepoList mediaFileRepoList = new MediaFileRepoList(new BigDecimal(mediaFileRepositorySize));
            mediaFileRepoList.attachObserverToList(OberserverTyp.capacity);
            mediaFileRepoList.attachObserverToList(OberserverTyp.tag);
            EventHandler inputHandler = new EventHandler();
            ConsoleManagement consoleManagement = new ConsoleManagement(inputHandler);
            EventHandler outputHandler = new EventHandler();
            inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new CreateUploaderListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ChangeListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new DeleteListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new LoadListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ReadMediaListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ReadUploaderListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ReadTagListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new RepositoryListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new SaveListener(mediaFileRepoList, outputHandler));
            CliOutputListener cliOutputListener = new CliOutputListener(consoleManagement);
            outputHandler.add(cliOutputListener);
            consoleManagement.run();
        }
    }
}

