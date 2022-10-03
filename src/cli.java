import domain_logic.MediaFileRepository;
import routing.handler.EventHandler;
import routing.listener.CliOutputListener;
import routing.listener.CreateMediaListener;
import ui.cli.ConsoleManagement;

import java.math.BigDecimal;

public class cli {
    public static void main(String[] args) {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000));
        EventHandler inputHandler = new EventHandler();
        ConsoleManagement consoleManagement = new ConsoleManagement(inputHandler);
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepository, outputHandler));
        CliOutputListener cliOutputListener = new CliOutputListener(consoleManagement);
        outputHandler.add(cliOutputListener);
        consoleManagement.run();
    }
}
