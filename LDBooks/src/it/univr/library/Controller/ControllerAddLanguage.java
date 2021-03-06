package it.univr.library.Controller;

import it.univr.library.Data.Book;
import it.univr.library.Data.Language;
import it.univr.library.Model.ModelDatabaseLanguage;
import it.univr.library.Model.ModelLanguage;
import it.univr.library.Utils.StageManager;
import it.univr.library.Data.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

public class ControllerAddLanguage {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button addNewLanguageButton;

    @FXML
    private TextField newLanguageTextField;

    @FXML
    private ListView<Language> languagesListView;
    private ObservableList<Language> languages = FXCollections.observableArrayList();

    private User manager;
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        populateLanguages();
        languagesListView.setItems(languages);

        addNewLanguageButton.setOnAction(this::handleAddNewLanguageButton);
    }



    public void setManager(User manager)
    {
        this.manager = manager;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox, cart);
    }


    private void populateLanguages()
    {
        ModelLanguage DBlanguages = new ModelDatabaseLanguage();
        languages.addAll((DBlanguages.getLanguages()));
    }

    private void handleAddNewLanguageButton(ActionEvent actionEvent)
    {
        ControllerAlert alerts = new ControllerAlert();

        Language newLanguage = new Language(newLanguageTextField.getText().trim());

        if(!newLanguage.getName().isEmpty())
        {
            boolean exist = false;
            for (Language language: languages)
            {
                if (language.equals(newLanguage)) {
                    exist = true;
                    break;
                }
            }

            if(!exist)
            {
                //if the authors doesn't already exists so insert into db
                ModelLanguage DBinsertNewLanguage = new ModelDatabaseLanguage();
                DBinsertNewLanguage.addNewLanguage(newLanguage);

                //change scene
                StageManager addEditBooks = new StageManager();
                addEditBooks.setStageAddEditBooks((Stage) addNewLanguageButton.getScene().getWindow(), manager, cart);
            }
            else
            {
                alerts.displayAlert("Language already exists!");
            }
        }
        else
        {
            alerts.displayAlert("Language name must be filled!");
        }
    }
}
