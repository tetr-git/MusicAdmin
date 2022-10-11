import domain_logic.MediaFileRepoList;
import net.ServerHandler;
import routing.handler.EventHandler;
import routing.listener.*;

import java.math.BigDecimal;

public class Server {

    public static void main(String[] args) {
        MediaFileRepoList mediaFileRepoList = new MediaFileRepoList(new BigDecimal(10000000));
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