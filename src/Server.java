import domain_logic.MediaFileRepoList;
import net.ServerHandler;
import routing.handler.EventHandler;
import routing.listener.*;

import java.math.BigDecimal;

import static util.TypeConverter.addInteger;
import static util.TypeConverter.isNumericInteger;

public class Server {

    public static void main(String[] args) {

        boolean startserver = true;
        boolean tcp = false;
        int mediaFileRepositorySize = 10000000;

        if (args.length > 0) {
            for (String string : args) {
                if (isNumericInteger(string)) {
                    mediaFileRepositorySize = addInteger(string);
                    break;
                }
            }
            for (String string : args) {
                if (string.equalsIgnoreCase("tcp")) {
                    tcp = true;
                    break;
                }
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("udp")) {
                System.out.println("Not implemented");
                startserver = false;
            }
        }
        if (startserver && tcp) {
            MediaFileRepoList mediaFileRepoList = new MediaFileRepoList(new BigDecimal(mediaFileRepositorySize));
            EventHandler inputHandler = new EventHandler();
            ServerHandler serverHandler = new ServerHandler(inputHandler, 1001);
            EventHandler outputHandler = new EventHandler();
            inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new CreateUploaderListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ChangeListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new DeleteListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new LoadListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ReadMediaListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ReadUploaderListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new ReadTagListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new InstanceListener(mediaFileRepoList, outputHandler));
            inputHandler.add(new SaveListener(mediaFileRepoList, outputHandler));
            OutputNetClientListener clientOutputListener = new OutputNetClientListener(serverHandler);
            outputHandler.add(clientOutputListener);
            serverHandler.run();
        }
    }
}