package de.dhbw.boggle.scenes;

import de.dhbw.boggle.Repository_Bridge_Ranking;
import de.dhbw.boggle.async_services.Async_Service_Ranking_File_Loader;
import de.dhbw.boggle.entities.Entity_Ranking_Entry;
import de.dhbw.boggle.ranking_entry.Mapper_Ranking_Entry_List;
import de.dhbw.boggle.ranking_entry.Ranking_Entry;
import de.dhbw.boggle.repositories.Repository_Ranking;
import de.dhbw.boggle.scene_factory.Scene_Creator;
import de.dhbw.boggle.ui_elements.Ranking_List_View_Cell;
import de.dhbw.boggle.value_objects.VO_Field_Size;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Ranking_Scene extends Advanced_Boggle_Scene{

    private final Repository_Ranking rankingList;
    private Entity_Ranking_Entry newRankingEntry;

    private Service<Boolean> fileLoader;

    @FXML private ListView<Ranking_Entry> rankingList4;
    @FXML private ListView<Ranking_Entry> rankingList5;
    @FXML private Label loadLabel4;
    @FXML private Label loadLabel5;

    public Ranking_Scene(List<Object> argList) {
        this.rankingList = new Repository_Bridge_Ranking();
        rankingList.loadAllRankingEntries();
    }

    @Override
    public void init() {
        fileLoader = new Async_Service_Ranking_File_Loader(this.rankingList);
        fileLoader.setOnSucceeded(workerStateEvent -> {
            if(fileLoader.getValue()) {

                if(newRankingEntry != null) {
                    rankingList.addRankingEntry(newRankingEntry);
                }

                updateRankingListView();

                loadLabel4.setVisible(false);
                loadLabel5.setVisible(false);

                rankingList4.setVisible(true);
                rankingList5.setVisible(true);
            }
        });
    }

    @Override
    public void build() {
        System.out.println("Building Ranking Scene...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ranking_Scene.fxml"));
            loader.setController(this);

            AnchorPane root = loader.load();
            super.scene = new Scene(root);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void validateArgList(List<Object> argList) {
        if(argList != null) {
            if(argList.size() >= 1) {
                if(!argList.get(0).getClass().equals(Entity_Ranking_Entry.class))
                    throw new RuntimeException("Argument 0 must be an instance of Entity_Ranking_Entry");

                newRankingEntry = (Entity_Ranking_Entry) argList.get(0);
            }
        }
    }

    @FXML
    public void initialize() {
        rankingList4.setCellFactory(rankingListViewCell -> new Ranking_List_View_Cell());
        rankingList5.setCellFactory(rankingListViewCell -> new Ranking_List_View_Cell());

        fileLoader.start();
    }

    @FXML
    private void backToMainMenu() {
        sceneManager.changeScene(Scene_Creator.SCENE.MAIN_MENU);
    }

    private void updateRankingListView() {
        Mapper_Ranking_Entry_List rankingEntryMapper = new Mapper_Ranking_Entry_List();

        List<Ranking_Entry> entryList4 = rankingEntryMapper.apply(
                rankingList.getRankingByFieldSize(new VO_Field_Size((short)4))
        );

        List<Ranking_Entry> entryList5 = rankingEntryMapper.apply(
                rankingList.getRankingByFieldSize(new VO_Field_Size((short)5))
        );

        rankingList4.getItems().clear();
        rankingList5.getItems().clear();

        rankingList4.getItems().addAll(entryList4);
        rankingList5.getItems().addAll(entryList5);
    }
}
