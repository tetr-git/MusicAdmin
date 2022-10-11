package ui.gui;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import routing.ParseMediaCollector;
import routing.events.*;
import routing.handler.EventHandler;
import routing.listener.*;
import util.RepoCollection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ViewModel {
    private final MediaFileRepoList mediaFileRepoList;
    private LinkedList<MediaTableWrapper> mediaTableWrapperList;
    private LinkedList<UploaderWithAmountTableWrapper> uploaderWrapperList;
    private LinkedList<TagTableWrapper> tagTableWrapperList;
    private ParseMediaCollector parseMediaCollector;


    public ViewModel() {
        this.mediaFileRepoList = new MediaFileRepoList(new BigDecimal(10000000));
        this.addHandler();
        this.addRandomExamples();
        this.genMediaWrapperArrayList();
        this.genUploaderWithCountedNumberWrapper();
        this.genTagTableWrapperList();
    }

    private void addRandomExamples() {
        inputHandler.handle(new CreateUploaderEvent("Max", "Max"));
        inputHandler.handle(new CreateUploaderEvent("Moritz", "Moritz"));
        String[] arg = new String[]{"Audio", "Max", "News", "10", "10", "320"};
        String[] arg2 = new String[]{"Audio", "Moritz", "Tutorial", "10", "10", "320"};
        parseMediaCollector = new ParseMediaCollector();
        inputHandler.handle(new CreateMediaEvent(arg, parseMediaCollector.parseToCollection(arg)));
        inputHandler.handle(new CreateMediaEvent(arg2, parseMediaCollector.parseToCollection(arg2)));
    }

    private void genMediaWrapperArrayList() {
        mediaTableWrapperList = new LinkedList<>();
        if (!mediaFileRepoList.getRepoList().isEmpty()) {
            for (MediaFileRepository mediaFileRepository : mediaFileRepoList.getRepoList()) {
                if ((!mediaFileRepository.readMediaList().isEmpty()) && mediaFileRepository.isActiveRepository()) {
                    for (MediaFile mediaFile : mediaFileRepository.readMediaList()) {
                        MediaTableWrapper mediaTableWrapper = new MediaTableWrapper(mediaFile, mediaFileRepository.getNumberOfRepository());
                        mediaTableWrapperList.add(mediaTableWrapper);
                    }
                }
            }
        }
    }

    private void genUploaderWithCountedNumberWrapper() {
        uploaderWrapperList = new LinkedList<>();
        if (!mediaFileRepoList.getRepoList().isEmpty()) {
            for (MediaFileRepository mediaFileRepository : mediaFileRepoList.getRepoList()) {
                if ((!mediaFileRepository.readUploaderWithCountedMediaElements().isEmpty()) && mediaFileRepository.isActiveRepository()) {
                    LinkedHashMap<Uploader, Integer> map = mediaFileRepository.readUploaderWithCountedMediaElements();
                    for (Map.Entry<Uploader, Integer> entry : map.entrySet()) {
                        Uploader uploader = entry.getKey();
                        Integer number = entry.getValue();
                        UploaderWithAmountTableWrapper uWrapper = new UploaderWithAmountTableWrapper(uploader, number, mediaFileRepository.getNumberOfRepository());
                        uploaderWrapperList.add(uWrapper);
                    }
                }
            }
        }
    }

    private void genTagTableWrapperList() {
        tagTableWrapperList = new LinkedList<>();
        if (!mediaFileRepoList.getRepoList().isEmpty()) {
            for (MediaFileRepository mediaFileRepository : mediaFileRepoList.getRepoList()) {
                if ((!mediaFileRepository.listEnumTags().isEmpty()) && mediaFileRepository.isActiveRepository()) {
                    for (Tag tag : mediaFileRepository.listEnumTags()) {
                        tagTableWrapperList.add(new TagTableWrapper(tag, mediaFileRepository.getNumberOfRepository()));
                    }
                }
            }
        }
    }

    private void triggerStatusOfInstance() {
        ArrayList<String> list = new ArrayList<>();
        list.add("storage");
        if (instanceZero.isSelected()) {
            list.add("0");
        }
        if (instanceOne.isSelected()) {
            list.add("1");
        }
        if (instanceTwo.isSelected()) {
            list.add("2");
        }
        String[] arg = list.toArray(new String[0]);
        inputHandler.handle(new SetRepositoryStatusEvent(list, new RepoCollection(arg)));
    }

    public void buttonClickUpdateInstanceActivity(ActionEvent actionEvent) {
        this.triggerStatusOfInstance();
        this.updateProperties();
    }


    public void buttonClickAdd(ActionEvent actionEvent) {
        String[] arg = this.inputProperty.get().trim().split("\\s+");
        if (arg.length == 1) {
            inputHandler.handle(new CreateUploaderEvent(arg[0], arg[0]));
        } else {
            inputHandler.handle(new CreateMediaEvent(arg, parseMediaCollector.parseToCollection(arg)));
        }
        this.updateProperties();
    }

    public void buttonClickDelete(ActionEvent actionEvent) {
        String arg = this.inputProperty.get();
        inputHandler.handle(new DeleteEvent(arg, arg));
        this.updateProperties();
    }

    public void buttonClickUpdate(ActionEvent actionEvent) {
        String arg = this.inputProperty.get();
        inputHandler.handle(new ChangeEvent(arg, arg));
        this.updateProperties();

    }

    public void buttonClickSaveJos(ActionEvent actionEvent) {
        String arg = "saveJos";
        inputHandler.handle(new SaveEvent(arg, arg));
        this.updateProperties();
    }

    public void buttonClickLoadJos(ActionEvent actionEvent) {
        String arg = "loadJos";
        inputHandler.handle(new LoadEvent(arg, arg));
        this.updateProperties();
    }

    private void updateProperties() {
        this.genMediaWrapperArrayList();
        this.genUploaderWithCountedNumberWrapper();
        this.genTagTableWrapperList();
        mediaTable.setItems(FXCollections.observableArrayList(mediaTableWrapperList));
        uploaderTable.setItems(FXCollections.observableArrayList(uploaderWrapperList));
        tagsTable.setItems(FXCollections.observableArrayList(tagTableWrapperList));
        input.clear();
    }

    @FXML
    private TextField input;
    public CheckBox instanceZero;
    public CheckBox instanceOne;
    public CheckBox instanceTwo;
    private final StringProperty inputProperty = new SimpleStringProperty();

    /*
    @author:
       https://stackoverflow.com/questions/70175587/how-do-you-use-a-javafx-tableview-with-java-records
       https://stackoverflow.com/questions/38049734/java-setcellvaluefactory-lambda-vs-propertyvaluefactory-advantages-disadvant
     */

    @FXML
    private void initialize() {
        this.input.textProperty().bindBidirectional(this.inputProperty);
        //mediaTable
        mediaTableMediaType.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getType()));
        mediaTableAddress.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getAddress()));
        mediaTableInstance.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getInstance()));
        mediaTableMediaUploader.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getUploader()));
        mediaTableTags.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTags()));
        mediaTableAccessCount.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getAccessCount()));
        mediaTableBitrate.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getBitrate()));
        mediaTableMediaLength.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getLength()));
        mediaTableSamplingRate.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getSamplingRate()));
        mediaTableResolution.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getResolution()));
        mediaTableHolder.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getHolder()));
        mediaTableInteractiveType.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTypeInteractive()));
        mediaTableMediaUploadDate.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getUploadDate()));
        mediaTableSize.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getSize()));
        //uploaderTable
        uploaderColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getUploader()));
        uploaderAmountColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNumberOfMediaFiles()));
        uploaderInstance.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getInstance()));
        //tagTable
        tagColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTag()));
        tagInstance.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getInstance()));

        this.updateProperties();
    }

    private EventHandler inputHandler;
    private EventHandler outputHandler;

    //https://gist.github.com/james-d/8602638
    private void addHandler() {
        inputHandler = new EventHandler();
        outputHandler = new EventHandler();
        //ConsoleManagement consoleManagement = new ConsoleManagement(inputHandler);
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
        //CliOutputListener cliOutputListener = new CliOutputListener(consoleManagement);
        //outputHandler.add(cliOutputListener);
    }

    @FXML
    private TableView<MediaTableWrapper> mediaTable = new TableView<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableAddress = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableInstance = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableMediaType = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableMediaUploader = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableTags = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableAccessCount = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableBitrate = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableMediaLength = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableSamplingRate = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableResolution = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableHolder = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableInteractiveType = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableMediaUploadDate = new TableColumn<>();
    @FXML
    private TableColumn<MediaTableWrapper, String> mediaTableSize = new TableColumn<>();

    @FXML
    private TableView<UploaderWithAmountTableWrapper> uploaderTable = new TableView<>();
    @FXML
    private TableColumn<UploaderWithAmountTableWrapper, String> uploaderColumn = new TableColumn<>();
    @FXML
    private TableColumn<UploaderWithAmountTableWrapper, String> uploaderAmountColumn = new TableColumn<>();
    @FXML
    private TableColumn<UploaderWithAmountTableWrapper, String> uploaderInstance = new TableColumn<>();

    @FXML
    private TableView<TagTableWrapper> tagsTable = new TableView<>();
    @FXML
    private TableColumn<TagTableWrapper, String> tagColumn = new TableColumn<>();
    @FXML
    private TableColumn<TagTableWrapper, String> tagInstance = new TableColumn<>();

}