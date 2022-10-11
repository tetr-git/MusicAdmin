import domain_logic.MediaFileRepoList;
import observer.OberserverTyp;
import routing.handler.EventHandler;
import routing.listener.*;
import ui.cli.ConsoleManagement;

import java.math.BigDecimal;

import static util.TypeConverter.addInteger;
import static util.TypeConverter.isNumericInteger;

public class CliAlternativ {

    public static void main(String[] args) {
        boolean start = true;
        boolean tcp = false;
        int mediaFileRepositorySize = 10000000;

        if (args.length>0) {
            for (String string : args) {
                if(isNumericInteger(string)) {
                    mediaFileRepositorySize = addInteger(string);
                    break;
                }
            }
            if (args.length==1&&args[0].equalsIgnoreCase("tcp")) {
                start = false;
            }
            if (args.length==1&&args[0].equalsIgnoreCase("udp")) {
                System.out.println("Not implemented");
                start = false;
            }
        }

        if (start && !tcp) {
            MediaFileRepoList mediaFileRepoList = new MediaFileRepoList(new BigDecimal(mediaFileRepositorySize));
            mediaFileRepoList.attachObserverToList(OberserverTyp.tag);
            EventHandler inputHandler = new EventHandler();
            ConsoleManagement consoleManagement = new ConsoleManagement(inputHandler);
            EventHandler outputHandler = new EventHandler();
            inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new CreateUploaderListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ChangeListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new LoadListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ReadMediaListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ReadUploaderListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new InstanceListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new SaveListener(mediaFileRepoList, outputHandler));
            OutputCliListener outputCliListener = new OutputCliListener(consoleManagement);
            outputHandler.add(outputCliListener);
            consoleManagement.run();
        }
    }
}

