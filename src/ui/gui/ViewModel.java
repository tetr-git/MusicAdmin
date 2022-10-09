package ui.gui;

import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import routing.events.ChangeEvent;
import routing.events.CreateMediaEvent;
import routing.events.CreateUploaderEvent;
import routing.events.DeleteEvent;
import routing.handler.EventHandler;
import util.MediaStringGenerator;
import util.ParseMedia;

import java.math.BigDecimal;
import java.util.ArrayList;


public class ViewModel {
    private MediaFileRepoList mediaFileRepoList;
    private ArrayList<MediaWrapper> mW;
    private ParseMedia parseMedia;
    private MediaFileRepository zeroRepo;

    public ViewModel(){
        this.mediaFileRepoList = new MediaFileRepoList(new BigDecimal(10000000));
        this.addHandler();
        this.addRandomExamples();
        this.genMediaWrapperArrayList();
        this.zeroRepo = mediaFileRepoList.getCopyOfRepoByNumber(0);
    }

    private void addRandomExamples() {
        inputHandler.handle(new CreateUploaderEvent("Max", "Max"));
        inputHandler.handle(new CreateUploaderEvent("Moritz", "Moritz"));
        String[] arg = new String[]{"Audio","Max", "News", "10", "10", "320"};
        String[] arg2 = new String[]{"Audio","Moritz", "Tutorial", "10", "10", "320"};
        //todo mM rausnehmen, nach Anpassung Erstellung Adresse
        parseMedia = new ParseMedia();
        createMediaHandler.handle(new CreateMediaEvent(arg, parseMedia.parseToCollection(arg)));
        createMediaHandler.handle(new CreateMediaEvent(arg2,parseMedia.parseToCollection(arg2)));
    }

    private void genMediaWrapperArrayList() {
        mW = new ArrayList<>();
        for (MediaFile m : zeroRepo.readMediaList()) {
            MediaWrapper mediaWrapper = new MediaWrapper(m);
            mW.add(mediaWrapper);
        }
    }

    public void buttonClickAdd(ActionEvent actionEvent) {
        String[] arg = this.inputProperty.get().trim().split("\\s+");
        if (arg.length == 1) {
            inputHandler.handle(new CreateUploaderEvent(arg[0],arg[0]));
        } else {
            createMediaHandler.handle(new CreateMediaEvent(arg,parseMedia.parseToCollection(arg)));
        }
        this.updateProperties();
    }

    public void buttonClickDelete(ActionEvent actionEvent) {
        String arg = this.inputProperty.get();
        inputHandler.handle(new DeleteEvent(arg,arg));
        this.updateProperties();
    }

    public void buttonClickUpdate(ActionEvent actionEvent) {
        String arg = this.inputProperty.get();
        inputHandler.handle(new ChangeEvent(arg,arg));
        this.updateProperties();

    }

    public void buttonClickAddRandom(ActionEvent actionEvent) {
        String[] arg = MediaStringGenerator.generateMediaString();
        createMediaHandler.handle(new CreateMediaEvent(arg,parseMedia.parseToCollection(arg)));
        this.updateProperties();
    }

    private void updateProperties(){
        this.genMediaWrapperArrayList();
        mediaTable.setItems(FXCollections.observableArrayList(mW));
        uploaderTable.setItems(FXCollections.observableArrayList(zeroRepo.readUploaderList()));
        tagsTable.setItems(FXCollections.observableArrayList(zeroRepo.listEnumTags()));

        input.clear();



    }
    @FXML private TextField input;
    private StringProperty inputProperty=new SimpleStringProperty();

    /*  @author:
        https://stackoverflow.com/questions/70175587/how-do-you-use-a-javafx-tableview-with-java-records
        https://stackoverflow.com/questions/38049734/java-setcellvaluefactory-lambda-vs-propertyvaluefactory-advantages-disadvant
     */

    @FXML private void initialize() {
        this.input.textProperty().bindBidirectional(this.inputProperty);
        mediaTableMediaType.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getType()));
        mediaTableAddress.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getAddress()));
        mediaTableMediaUploader.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getUploader()));
        //mediaTableAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress())
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
        uploaderColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        //tagTable
        tagColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().toString()));

        this.updateProperties();
    }

    private EventHandler inputHandler;
    private EventHandler createMediaHandler;
    private EventHandler outputHandler;
    //https://gist.github.com/james-d/8602638
    private void addHandler() {
        inputHandler = new EventHandler();
        outputHandler = new EventHandler();
        createMediaHandler = new EventHandler();
        //inputHandler.add(new GLInputListener(mediaFileRepository, outputHandler));
        //createMediaHandler.add(new CreateMediaListener(mediaFileRepository,outputHandler));
        /*todo addGuiOutputHandler
        CliOutputListener cliOutputListener = new CliOutputListener(cM);
        outputHandler.add(cliOutputListener);
        */
    }

    // MediaElement Table
    @FXML private TableView<MediaWrapper> mediaTable = new TableView<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableMediaType = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableAddress = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableMediaUploader = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableTags = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableAccessCount = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableBitrate = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableMediaLength = new TableColumn<>();

    @FXML private TableColumn<MediaWrapper, String> mediaTableSamplingRate = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableResolution = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableHolder = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableInteractiveType = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableMediaUploadDate = new TableColumn<>();
    @FXML private TableColumn<MediaWrapper, String> mediaTableSize = new TableColumn<>();



    @FXML private TableView<Uploader> uploaderTable = new TableView<>();
    @FXML private TableColumn<Uploader, String> uploaderColumn = new TableColumn<>();
    @FXML private TableColumn<Uploader, String> uploaderAmountColumn = new TableColumn<>();

    @FXML private TableView<Tag> tagsTable = new TableView<>();
    @FXML private TableColumn<Tag, String> tagColumn = new TableColumn<>();

}
/*
        //this.mediaTableAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        //ReadOnlyObjectWrapper(data.getValue().getUser().getFirstName());
        //this.mediaTableAddress.setCellValueFactory(new ReadOnlyObjectWrapper(.get().getUser().getFirstName()););
        /*mediaTable.getColumns().addAll(mediaTableAddress,mediaTableTags);
        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html
        //mediaTableAddress = new TableColumn<MediaElementImpl, String>("address");
        //mediaTableAddress.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        mediaTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        //mediaTableAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        // https://stackoverflow.com/questions/70175587/how-do-you-use-a-javafx-tableview-with-java-records
        //https://stackoverflow.com/questions/38049734/java-setcellvaluefactory-lambda-vs-propertyvaluefactory-advantages-disadvant
        //mediaTable.getColumns().add(mediaTableAddress);
        //mediaTable.getColumns().addAll(mediaTableAddress);
        //mediaTable.getItems().addAll(mM.readMediaList());
        //mediaElements = FXCollections.observableArrayList(mM.readMediaList());
        mediaTable.setItems(FXCollections.observableArrayList(mM.readMediaList()));
 */