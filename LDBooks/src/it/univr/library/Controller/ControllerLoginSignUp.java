package it.univr.library.Controller;

import it.univr.library.Data.*;
import it.univr.library.Model.*;
import it.univr.library.Utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

public class ControllerLoginSignUp {

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField pswField;

    @FXML
    private HBox headerHBox;

    private User user;

    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        // handle to press enter for login
        pswField.setOnKeyReleased(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
                loginButton.fire();
        });

        loginButton.setOnAction(this::handleLoginButton);
        signUpButton.setOnAction(this::handleSignUpButton);
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox,cart);
    }

    private void handleLoginButton(ActionEvent actionEvent)
    {
        ControllerAlert alerts = new ControllerAlert();

        ModelRegisteredUser DBLoginRegisteredUser = new ModelDatabaseRegisteredUser();
        ModelManager DBLoginManager = new ModelDatabaseManagers();

        User user = fetchUser();
        RegisteredClient realUser = DBLoginRegisteredUser.getRegisteredClient(user);
        Manager manager = DBLoginManager.getManager(user);

        if(realUser == null && manager == null || realUser != null && manager != null)
            alerts.displayAlert("Invalid mail or password!");
        else
        {
            if(realUser != null) //user page
            {
                StageManager loginStage = new StageManager();
                loginStage.setStageUserPage((Stage) loginButton.getScene().getWindow(), clientToRegisteredClient(realUser), cart);
            }
            else //manager page
            {
                StageManager loginStage = new StageManager();
                loginStage.setStageManagerPage((Stage) loginButton.getScene().getWindow(), manager, cart);
            }

        }
    }

    private void handleSignUpButton(ActionEvent actionEvent)
    {
        StageManager signUpStage = new StageManager();
        signUpStage.setStageSignUp((Stage) signUpButton.getScene().getWindow(), user, cart);
    }

    private User fetchUser()
    {
        Client u = new Client();
        u.setEmail(mailTextField.getText().toUpperCase());
        u.setPassword(pswField.getText());

        return u;
    }

    private RegisteredClient clientToRegisteredClient(Client user) {
        ModelUserAddress DBInformation = new ModelDatabaseUserAddress();
        RegisteredClient regUser =
                new RegisteredClient(user.getName(), user.getSurname(), user.getEmail(),
                        user.getPassword(), user.getPhoneNumber(), DBInformation.getAddressesRegisteredUser(user));

        return regUser;
    }
}
