package com.example.demo20;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;

public class GameIntro extends Application {
    ImageView newButton, stroyModeButton, versusButton,helpButton,quitButton;
    MediaPlayer ost;
    Scene scene;
    // Define a 2D array to store player data (adjust the size as needed)
    private Object[][] playerData = new String[100][6];
    // Index to keep track of the current player data
    private int currentPlayerIndex = 0;
    private int loggedInPlayerIndex;
    private Object[] loggedInPlayer;



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/MainmenuOST.mp4/"));
        ost.setCycleCount(MediaPlayer.INDEFINITE);
        ost.play();


        //interface background
        ImageView background = new ImageView("background.gif");
        background.setFitHeight(540);
        background.setFitWidth(960);

        //background scene after start button
        ImageView backGroundEf = new ImageView("BackGroundEffected.gif");
        backGroundEf.setFitHeight(540);
        backGroundEf.setFitWidth(960);

        //main character img
        ImageView character = new ImageView("char_new.gif");
        character.setFitWidth(360);
        character.setFitHeight(480);
        character.setX(5);
        character.setY(80);

        //game icon
        ImageView icon = new ImageView("Icon_new.png");
        icon.setFitWidth(500);
        icon.setFitHeight(500);
        icon.setX(350);
        icon.setY(-30);

        //Help option things

        //background scene for help
        ImageView sceneHelp = new ImageView("Scenehelp.PNG");
        sceneHelp.setFitHeight(540);
        sceneHelp.setFitWidth(960);
        //move help icon
        ImageView moveHelpL = new ImageView("KeyboardL .png");
        moveHelpL.setFitWidth(299);
        moveHelpL.setFitHeight(356);
        moveHelpL.setX(95);
        moveHelpL.setY(60);

        ImageView moveHelpR = new ImageView("KeyboardR .png");
        moveHelpR.setFitWidth(299);
        moveHelpR.setFitHeight(356);
        moveHelpR.setX(600);
        moveHelpR.setY(78);

        //back icon
        ImageView backButtonHelp = new ImageView("back.png");
        backButtonHelp.setFitHeight(60);
        backButtonHelp.setFitWidth(60);


        //ending of help button

        //Buttons
        newButton = new ImageView("newButton.png");
        stroyModeButton = new ImageView("stroyModeButton.png");
        versusButton = new ImageView("versusButton.png");
        helpButton = new ImageView("helpButton.png");
        quitButton = new ImageView("quitButton.png");
        ImageView startButton = new ImageView("startButton.png");

        //start screen
        startButton.setFitWidth(195);
        startButton.setFitHeight(53.83);
        startButton.setX(500);
        startButton.setY(350);
        startButton.setOnMouseEntered(e->{
            Glow glow = new Glow(100);
            startButton.setEffect(glow);
        });
        startButton.setOnMouseExited(e->{
            startButton.setEffect(null);
        });

        //changing scene on clicking start button
        startButton.setOnMouseClicked(e->{
            root.getChildren().remove(startButton);
            root.getChildren().remove(background);
            root.getChildren().remove(character);
            root.getChildren().remove(icon);
            root.getChildren().addAll(backGroundEf,character,icon);

            icon.setFitHeight(300);
            icon.setFitWidth(300);
            icon.setX(500);
            icon.setY(30);

            //Buttons
            //new Game button
            newButton.setFitWidth(195);
            newButton.setFitHeight(53.83);
            newButton.setX(550);
            newButton.setY(250);
            newButton.setOnMouseEntered( event ->{
                Glow glow = new Glow(100);
                newButton.setEffect(glow);
            });
            newButton.setOnMouseExited( event ->{
                newButton.setEffect(null);
            });
            //continue Game button
            stroyModeButton.setFitWidth(195);
            stroyModeButton.setFitHeight(53.83);
            stroyModeButton.setX(550);
            stroyModeButton.setY(305);
            stroyModeButton.setOnMouseEntered(event ->{
                Glow glow = new Glow(100);
                stroyModeButton.setEffect(glow);
            });
            stroyModeButton.setOnMouseExited(event ->{
                stroyModeButton.setEffect(null);
            });

            //settings button
            versusButton.setFitWidth(195);
            versusButton.setFitHeight(53.83);
            versusButton.setX(550);
            versusButton.setY(360);
            versusButton.setOnMouseEntered(event ->{
                Glow glow = new Glow(100);
                versusButton.setEffect(glow);
            });
            versusButton.setOnMouseExited(event ->{
                versusButton.setEffect(null);
            });
            //help button
            helpButton.setFitWidth(195);
            helpButton.setFitHeight(53.83);
            helpButton.setX(550);
            helpButton.setY(415);
            helpButton.setOnMouseEntered( event ->{
                Glow glow = new Glow(100);
                helpButton.setEffect(glow);
            });
            helpButton.setOnMouseExited( event ->{
                helpButton.setEffect(null);
            });
            //quitButton
            quitButton.setFitWidth(195);
            quitButton.setFitHeight(53.83);
            quitButton.setX(550);
            quitButton.setY(470);
            quitButton.setOnMouseEntered( event ->{
                Glow glow = new Glow(100);
                quitButton.setEffect(glow);
            });
            quitButton.setOnMouseExited( event ->{
                quitButton.setEffect(null);
            });

            root.getChildren().addAll(newButton, stroyModeButton, versusButton,helpButton,quitButton);
        });

        //on clicking new button

        newButton.setOnMouseClicked(e->
        {
            TextInputDialog newDialog = new TextInputDialog();

            newDialog.setTitle("Register");
            newDialog.setHeaderText(null);

            ButtonType registerButton = new ButtonType("Sign Up");

            // sign up fields
            GridPane grid = new GridPane();

            Label nameLabel = new Label("Player Name ");
            TextField nameTf = new TextField();
            nameTf.setPromptText("Enter you name");

            Label usernameLabel = new Label("Username ");
            TextField usernameTf = new TextField();
            usernameTf.setPromptText("Enter username");

            Label passwordLabel = new Label("Password ");
            PasswordField passwordTf = new PasswordField();
            passwordTf.setPromptText("Enter password");

            Label numberLabel = new Label("Phone Number ");
            TextField numberTf = new TextField();
            numberTf.setPromptText("Enter number");

            Label genderLabel = new Label("Gender ");
            ComboBox<String> genderList = new ComboBox<>();
            genderList.setValue("Male");
            genderList.getItems().addAll("Male","Female");

            Label singUp = new Label("Register");

            grid.addRow(0,singUp);
            GridPane.setColumnSpan(singUp,2);
            grid.addRow(1,nameLabel,nameTf);
            grid.addRow(2,usernameLabel,usernameTf);
            grid.addRow(3,passwordLabel,passwordTf);
            grid.addRow(4,numberLabel,numberTf);
            grid.addRow(5,genderLabel ,genderList);
            grid.setHgap(5);
            grid.setVgap(10);
            grid.setAlignment(Pos.CENTER);

            newDialog.getDialogPane().setContent(grid);
            newDialog.getDialogPane().getButtonTypes().setAll(registerButton,ButtonType.CANCEL);

            newDialog.getDialogPane().setStyle("-fx-background-image: url(dialogBg.jpg);");
            singUp.setStyle("-fx-text-fill: white;-fx-font: bold 40px 'serif';");
            nameLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
            usernameLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
            passwordLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
            numberLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
            genderLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");

            ImageView img = new ImageView("pxChar.png");
            img.setFitWidth(108);
            img.setFitHeight(166);
            newDialog.setGraphic(img);
            newDialog.show();

            Button signUpButtonNode = (Button) newDialog.getDialogPane().lookupButton(registerButton);


            signUpButtonNode.setOnAction(event -> {
                if (nameTf.getText().isEmpty() || usernameTf.getText().isEmpty() ||
                        passwordTf.getText().isEmpty() || numberTf.getText().isEmpty() ||
                        genderList.getValue().isEmpty()) {
                    newDialog.show();
                    // Optionally, you can display a message to indicate empty fields
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Oops! To complete your registration, please fill out all the required fields.");
                    alert.setHeaderText(null);
                    alert.setTitle("Invalid Input");
                    alert.getDialogPane().setStyle("-fx-background-color: pink;");
                    alert.showAndWait();
                } else {
                    // Save player data using the new method
                    savePlayerData(nameTf.getText(), usernameTf.getText(),
                            passwordTf.getText(), numberTf.getText(), genderList.getValue());

                    // Close the dialog
                    newDialog.close();
                }

            });

        });

        //on clicking story mode button
        stroyModeButton.setOnMouseClicked(e->
        {
            //create dialog for login
            TextInputDialog continueDialog = new TextInputDialog();

            continueDialog.setTitle("Login");
            continueDialog.setHeaderText(null);

            ButtonType loginButton = new ButtonType("Login");
            // sign up fields
            GridPane grid = new GridPane();

            Label usernameLabel = new Label("Username ");
            TextField usernameTf = new TextField();
            usernameTf.setPromptText("Enter username");

            Label passwordLabel = new Label("Password ");
            PasswordField passwordTf = new PasswordField();
            passwordTf.setPromptText("Enter password");

            Label login = new Label("LOGIN");

            grid.addRow(0,login);
            GridPane.setColumnSpan(login,2);
            grid.addRow(1,usernameLabel,usernameTf);
            grid.addRow(2,passwordLabel,passwordTf);

            grid.setHgap(5);
            grid.setVgap(10);
            grid.setAlignment(Pos.CENTER);

            continueDialog.getDialogPane().setContent(grid);
            continueDialog.getDialogPane().getButtonTypes().setAll(loginButton,ButtonType.CANCEL);

            login.setStyle("-fx-text-fill: white;-fx-font: bold 40px 'serif';");
            continueDialog.getDialogPane().setStyle("-fx-background-image: url(dialogBg.jpg);");
            usernameLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
            passwordLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");

            ImageView img = new ImageView("pxChar.png");
            img.setFitWidth(108);
            img.setFitHeight(166);
            continueDialog.setGraphic(img);
            continueDialog.setGraphic(img);

            continueDialog.setGraphic(img);
            continueDialog.show();

            // when clicking login button
            Button loginButtonNode = (Button) continueDialog.getDialogPane().lookupButton(loginButton);

            loginButtonNode.setOnAction(event -> {
                String enteredUsername = usernameTf.getText();
                String enteredPassword = passwordTf.getText();

                // Validate login and get player data
                Object[] player = validateLoginAndGetPlayerData(enteredUsername, enteredPassword);

                if (player != null) {
                    // Successful login, create a new 1D array to store player data
                    loggedInPlayer = new Object[6];
                    System.arraycopy(player, 0, loggedInPlayer, 0, player.length);
                    openLoggedInPlayerScreen(loggedInPlayer,loggedInPlayerIndex,primaryStage,false, false,scene);
                } else {
                    // Invalid login, display an error message
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
                    alert.showAndWait();
                }
            });
        });
        //on clicking versus button
        versusButton.setOnMouseClicked(e -> {
            open1Versus1Screen(primaryStage);

            printPlayerData();
            System.out.println(currentPlayerIndex);
        });

        //on clicking help button
        helpButton.setOnMouseClicked(mouseEvent-> {
            Pane pane = new Pane();
            pane.getChildren().addAll(sceneHelp,moveHelpR,moveHelpL,backButtonHelp);
            Scene helpScene = new Scene(pane,960,540);
            primaryStage.setScene(helpScene);
        });
        backButtonHelp.setOnMouseEntered( event ->{
            Glow glow = new Glow(100);
            backButtonHelp.setEffect(glow);
        });
        backButtonHelp.setOnMouseExited( event ->{
            backButtonHelp.setEffect(null);
        });
        backButtonHelp.setOnMouseClicked(mouseEvent -> {
            primaryStage.setScene(scene);
        });

        //on clicking quit button
        quitButton.setOnMouseClicked(e->{
            System.exit(0);
        });


        root.getChildren().addAll(background,icon,startButton,character);
        scene = new Scene(root,960,540);
        primaryStage.setScene(scene);
        Image stageIcon = new Image("stageIcon.png");
        primaryStage.setTitle("LIBERATION WARRIOR");
        primaryStage.getIcons().add(stageIcon);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    // Method to save player data in the 2D array
    private void savePlayerData(String name, String username, String password, String number, String gender) {
        // Check if there is space in the array
        if (currentPlayerIndex < playerData.length) {
            // Save player data in the array
            playerData[currentPlayerIndex][0] = name;
            playerData[currentPlayerIndex][1] = username;
            playerData[currentPlayerIndex][2] = password;
            playerData[currentPlayerIndex][3] = number;
            playerData[currentPlayerIndex][4] = gender;



            // Increment the index for the next player
            currentPlayerIndex++;

            // Optionally, you can display a message to indicate successful registration
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Now go with 'Continue' Option");
            alert.setHeaderText("Registration successful!");
            alert.showAndWait();
        } else {
            // Optionally, you can display an error message if the array is full
            Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum number of players reached.");
            alert.showAndWait();
        }
    }

    // Method to validate login credentials and get player data
    private Object[] validateLoginAndGetPlayerData(String username, String password) {
        for (int i = 0; i < currentPlayerIndex; i++) {
            if (username.equals(playerData[i][1]) && password.equals(playerData[i][2])) {
                // Valid login, return player data
                loggedInPlayerIndex = i;
                return playerData[i];
            }
        }
        // Invalid login, return null
        return null;
    }

    // loggedInPlayerScene
    public Object [] openLoggedInPlayerScreen(Object [] LoggedInPlayer,int index,Stage primaryStage,boolean openLock2, boolean openLock3,Scene scene){
        ost.play();
        boolean openLevel2 = openLock2;
        boolean openLevel3 = openLock3;

        //initiate array to store curr player data
        Object[] currPlayer = LoggedInPlayer;

        //construct player screen
        Pane root = new Pane();

        //interface background
        ImageView background = new ImageView("bg.jpg");
        background.setFitHeight(540);
        background.setFitWidth(960);

        //game icon
        ImageView playerProfileIcon = new ImageView("icon_playerprofile.png");
        playerProfileIcon.setFitHeight(180);
        playerProfileIcon.setFitWidth(180);
        playerProfileIcon.setX(390);
        playerProfileIcon.setY(5);

        //player profile icon
        ImageView profileIcon = new ImageView("profileIcon.png");
        profileIcon.setFitHeight(80);
        profileIcon.setFitWidth(80);
        profileIcon.setX(5);
        profileIcon.setY(5);
        profileIcon.setOnMouseEntered( event ->{
            Glow glow = new Glow(100);
            profileIcon.setEffect(glow);
        });
        profileIcon.setOnMouseExited( event ->{
            profileIcon.setEffect(null);
        });

        //player name
        Label playerName = new Label();
        playerName.setText(currPlayer[0].toString());
        playerName.setLayoutX(87);
        playerName.setLayoutY(22);
        playerName.setStyle("-fx-text-fill: black;-fx-font:  bold 28px 'rounded sans';");

        //player username
        Label playerUsername = new Label();
        playerUsername.setText(currPlayer[1].toString());
        playerUsername.setLayoutX(90);
        playerUsername.setLayoutY(55);
        playerUsername.setStyle("-fx-text-fill: grey;-fx-font: 15px 'serif';");

        //player score
        Label playerScore = new Label();
        if (currPlayer[5]!=null) {
            playerScore.setText("SCORE: " + currPlayer[5].toString());
        }
        else {
            playerScore.setText("SCORE: " + String.valueOf(0));
        }
        playerScore.setLayoutX(800);
        playerScore.setLayoutY(25);
        playerScore.setStyle("-fx-text-fill: white;-fx-font: bold 20px 'serif';");

        //maps
        Image level1 = new Image("level1Map.png");
        Image level1Play = new Image("playLevel1.png");

        Image level2 = new Image("level2Map.jpg");
        Image level2Locked = new Image("lockedLevel2.jpg");
        Image level2Play = new Image("playLevel2.jpg");

        Image level3 = new Image("level3Map.png");
        Image level3Locked = new Image("lockedLevel3.png");
        Image level3Play = new Image("playLevel3.png");


        ImageView map1 = new ImageView(level1);
        map1.setFitWidth(274.28);
        map1.setFitHeight(165.28);
        map1.setX(30);
        map1.setY(187);
        map1.setOnMouseEntered( event ->{
            map1.setImage(level1Play);
            Glow glow = new Glow(0.5);
            map1.setEffect(glow);
            map1.setFitWidth(300);
            map1.setFitHeight(181);
            map1.setX(17.5);
            map1.setY(179.5);
        });
        map1.setOnMouseExited( event ->{
            map1.setImage(level1);
            map1.setEffect(null);
            map1.setFitWidth(274.28);
            map1.setFitHeight(165.28);
            map1.setX(30);
            map1.setY(187);

        });


        ImageView map2 = new ImageView(level2);
        map2.setFitWidth(274.28);
        map2.setFitHeight(165.28);
        map2.setX(344.28);
        map2.setY(187);


        map2.setOnMouseEntered( event ->{
            if (openLevel2){
                map2.setImage(level2Play);
            }
            else {
                map2.setImage(level2Locked);
            }
            Glow glow = new Glow(0.5);
            map2.setEffect(glow);
            map2.setFitWidth(300);
            map2.setFitHeight(181);
            map2.setX(332.5);
            map2.setY(179.5);
        });
        map2.setOnMouseExited( event ->{
            map2.setImage(level2);
            map2.setEffect(null);
            map2.setFitWidth(274.28);
            map2.setFitHeight(165.28);
            map2.setX(344.28);
            map2.setY(187);
        });

        ImageView map3 = new ImageView(level3);
        map3.setFitWidth(274.28);
        map3.setFitHeight(165.28);
        map3.setX(658.56);
        map3.setY(187);


        map3.setOnMouseEntered( event ->{
            if (openLevel3){
                map3.setImage(level3Play);
            }
            else {
                map3.setImage(level3Locked);
            }
            Glow glow = new Glow(0.5);
            map3.setEffect(glow);
            map3.setFitWidth(300);
            map3.setFitHeight(181);
            map3.setX(646.5);
            map3.setY(179.5);

        });
        map3.setOnMouseExited( event ->{
            map3.setImage(level3);
            map3.setEffect(null);
            map3.setFitWidth(274.28);
            map3.setFitHeight(165.28);
            map3.setX(658.56);
            map3.setY(187);
        });

        //logOut button
        ImageView logOutButton = new ImageView("logoutButton.png");
        logOutButton.setFitWidth(195);
        logOutButton.setFitHeight(53.83);
        logOutButton.setX(382.5);
        logOutButton.setY(400);
        logOutButton.setOnMouseEntered( event ->{
            Glow glow = new Glow(100);
            logOutButton.setEffect(glow);
        });
        logOutButton.setOnMouseExited( event ->{
            logOutButton.setEffect(null);
        });

        //on clicking the profile icon
        profileIcon.setOnMouseClicked(e->{
            Alert viewProfile = new Alert(Alert.AlertType.INFORMATION);
            viewProfile.setTitle("Profile");
            viewProfile.setHeaderText(null);
            ButtonType editButton = new ButtonType("Edit");


            //profile information
            GridPane gridPane = new GridPane();

            Label playerNameLabel = new Label("Name: ");
            Text playerNameData = new Text(currPlayer[0].toString());

            Label playerUsernameLabel = new Label("Username: ");
            Text playerUsernameData = new Text(currPlayer[1].toString());

            Label playerPasswordLabel = new Label("Password: ");
            Text playerPasswordData = new Text(currPlayer[2].toString());

            Label playerNumberLabel = new Label("Phone: ");
            Text playerNumberData = new Text(currPlayer[3].toString());

            Label playerGenderLabel = new Label("Gender: ");
            Text playerGenderData = new Text(currPlayer[4].toString());

            Label playerScoreLabel = new Label("Score: ");
            Text playerScoreData;
            System.out.println(currPlayer[5]);
            if (currPlayer[5]!=null) {
                playerScoreData = new Text(currPlayer[5].toString());
            }
            else {
                playerScoreData = new Text(String.valueOf(0));
            }

            Label title = new Label("Profile");

            gridPane.addRow(0,title);
            GridPane.setColumnSpan(title,2);
            gridPane.addRow(1,playerNameLabel,playerNameData);
            gridPane.addRow(2,playerUsernameLabel,playerUsernameData);
            gridPane.addRow(3,playerPasswordLabel,playerPasswordData);
            gridPane.addRow(4,playerNumberLabel,playerNumberData);
            gridPane.addRow(5,playerGenderLabel ,playerGenderData);
            gridPane.addRow(6,playerScoreLabel ,playerScoreData);

            gridPane.setHgap(5);
            gridPane.setVgap(10);
            gridPane.setAlignment(Pos.CENTER);

            viewProfile.getDialogPane().setContent(gridPane);

            viewProfile.getDialogPane().setStyle("-fx-background-image: url(dialogBg.jpg);");
            title.setStyle("-fx-text-fill: white;-fx-font: bold 40px 'serif';");
            playerNameLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'rounded sans';");
            playerUsernameLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'rounded sans';");
            playerPasswordLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'rounded sans';");
            playerNumberLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'rounded sans';");
            playerGenderLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'rounded sans';");
            playerScoreLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'rounded sans';");

            playerNameData.setStyle("-fx-fill: black;-fx-font: bold 12px 'serif';");
            playerUsernameData.setStyle("-fx-fill: black;-fx-font: bold  12px 'serif';");
            playerPasswordData.setStyle("-fx-fill: black;-fx-font: bold 12px 'serif';");
            playerNumberData.setStyle("-fx-fill: black;-fx-font: bold 12px 'serif';");
            playerGenderData.setStyle("-fx-fill: black;-fx-font: bold 12px 'serif';");
            playerScoreData.setStyle("-fx-fill: black;-fx-font: bold 12px 'serif';");

            ImageView img = new ImageView("profileIcon.png");
            img.setFitWidth(100);
            img.setFitHeight(100);
            viewProfile.setGraphic(img);
            //viewProfile.setGraphic(img);

            //viewProfile.setGraphic(img);

            viewProfile.getDialogPane().getButtonTypes().add(editButton);
            viewProfile.show();

            //when clicking edit button
            Button editButtonNode = (Button) viewProfile.getDialogPane().lookupButton(editButton);
            editButtonNode.setOnAction(event->{
                TextInputDialog editProfileDialog = new TextInputDialog();

                editProfileDialog.setTitle("Edit Profile");
                editProfileDialog.setHeaderText(null);

                ButtonType doneButton = new ButtonType("Done");

                //user profile fields to be changed
                GridPane grid = new GridPane();

                Label nameLabel = new Label("Player Name ");
                TextField nameTf = new TextField();
                nameTf.setText(currPlayer[0].toString());

                Label usernameLabel = new Label("Username ");
                TextField usernameTf = new TextField();
                usernameTf.setText(currPlayer[1].toString());

                Label passwordLabel = new Label("Password ");
                PasswordField passwordTf = new PasswordField();
                passwordTf.setText(currPlayer[2].toString());

                Label numberLabel = new Label("Phone ");
                TextField numberTf = new TextField();
                numberTf.setText(currPlayer[3].toString());

                Label genderLabel = new Label("Gender ");
                ComboBox<String> genderList = new ComboBox<>();
                genderList.setValue(currPlayer[4].toString());

                Label scoreLabel = new Label("Score ");
                TextField scoreTf;
                if (currPlayer[5]!=null) {
                    scoreTf = new TextField(currPlayer[5].toString());
                }
                else {
                    scoreTf = new TextField(String.valueOf(0));
                }
                scoreTf.setEditable(false);

                Label editTitle = new Label("Edit profile");

                grid.addRow(0,editTitle);
                GridPane.setColumnSpan(editTitle,2);
                grid.addRow(1,nameLabel,nameTf);
                grid.addRow(2,usernameLabel,usernameTf);
                grid.addRow(3,passwordLabel,passwordTf);
                grid.addRow(4,numberLabel,numberTf);
                grid.addRow(5,genderLabel ,genderList);
                grid.addRow(6,scoreLabel ,scoreTf);

                grid.setHgap(5);
                grid.setVgap(10);
                grid.setAlignment(Pos.CENTER);

                editProfileDialog.getDialogPane().setContent(grid);
                editProfileDialog.getDialogPane().getButtonTypes().setAll(doneButton,ButtonType.CANCEL);

                editProfileDialog.getDialogPane().setStyle("-fx-background-image: url(dialogBg.jpg);");
                editTitle.setStyle("-fx-text-fill: white;-fx-font: bold 40px 'serif';");
                nameLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
                usernameLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
                passwordLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
                numberLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
                genderLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");
                scoreLabel.setStyle("-fx-text-fill: white;-fx-font: italic bold 16px 'serif';");


                ImageView editIcon = new ImageView("profileIcon.png");
                editIcon.setFitWidth(100);
                editIcon.setFitHeight(100);
                editProfileDialog.setGraphic(editIcon);

                editProfileDialog.show();

                // when clicking done button
                Button doneButtonNode = (Button) editProfileDialog.getDialogPane().lookupButton(doneButton);
                doneButtonNode.setOnAction(doneEvent -> {
                    // Get the updated values from the text fields and combo box
                    String newName = nameTf.getText();
                    String newUsername = usernameTf.getText();
                    String newPassword = passwordTf.getText();
                    String newNumber = numberTf.getText();

                    if (nameTf.getText().isEmpty() || usernameTf.getText().isEmpty() ||
                            passwordTf.getText().isEmpty() || numberTf.getText().isEmpty()) {
                        editProfileDialog.show();

                        // Optionally, you can display a message to indicate empty fields
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Oops! Make sure to fill out all the fields to update your profile.");
                        alert.setHeaderText(null);
                        alert.setTitle("Invalid Input");
                        alert.getDialogPane().setStyle("-fx-background-color: pink;");
                        alert.showAndWait();
                    } else {
                        // Update the data in the playerData array

                        currPlayer[0] = newName;
                        playerData [index][0] = currPlayer[0];
                        currPlayer[1] = newUsername;
                        playerData [index][1] = currPlayer[1];
                        currPlayer[2] = newPassword;
                        playerData [index][2] = currPlayer[2];
                        currPlayer[3] = newNumber;
                        playerData [index][3] = currPlayer[3];
                        playerData [index][5] = currPlayer[5];


                        //changing player name and his username in the player start screen
                        playerName.setText(currPlayer[0].toString());
                        playerUsername.setText(currPlayer[1].toString());

                        // Optionally, you can display a message to indicate successful update
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully!");
                        alert.setHeaderText("Update Successful");
                        alert.showAndWait();

                        // Close the edit profile dialog
                        editProfileDialog.close();
                    }

                });

            });
        });
        //on clicking map 1 option:
        map1.setOnMouseClicked(e->{
            try {
                ost.stop();
                LV1 lv1 = new LV1(primaryStage,currPlayer,loggedInPlayerIndex, scene);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        //on clicking map 2 option:logOuy

        map2.setOnMouseClicked(e->{
            if (openLock2) {
                try {
                    ost.stop();
                    LV2 lv2 = new LV2 (primaryStage,currPlayer,loggedInPlayerIndex, scene);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //on clicking map 3 option:logOuy

        map3.setOnMouseClicked(e->{
            if (openLock3) {
                ost.stop();
                try {
                    LV3 lv3 = new LV3 (primaryStage,currPlayer,loggedInPlayerIndex, scene);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //on clicking logOut button
        logOutButton.setOnMouseClicked(e->{
            primaryStage.setScene(scene);
            playerData [index][0] = currPlayer[0];
            playerData [index][1] = currPlayer[1];
            playerData [index][2] = currPlayer[2];
            playerData [index][3] = currPlayer[3];
            playerData [index][4] = currPlayer[4];
            playerData [index][5] = currPlayer[5];
        });

        //binding things together and create a new scene
        root.getChildren().addAll(background,profileIcon,playerProfileIcon,playerName,playerUsername,playerScore,map1,map2,map3,logOutButton);
        Scene playerProfileScene = new Scene(root,960,540);
        primaryStage.setScene(playerProfileScene);
        primaryStage.setResizable(false);
        Image stageIcon = new Image("stageIcon.png");
        primaryStage.setTitle("LIBERATION WARRIOR");
        primaryStage.getIcons().add(stageIcon);
        primaryStage.show();

        return currPlayer;
    }


    public void open1Versus1Screen(Stage primaryStage) {

        //construct player screen
        Pane root = new Pane();

        //interface background
        ImageView background = new ImageView("bg.jpg");
        background.setFitHeight(540);
        background.setFitWidth(960);

        //game icon
        ImageView playerProfileIcon = new ImageView("icon_playerprofile.png");
        playerProfileIcon.setFitHeight(180);
        playerProfileIcon.setFitWidth(180);
        playerProfileIcon.setX(390);
        playerProfileIcon.setY(5);

        //players
        ImageView player1 = new ImageView("player1.png");
        player1.setFitWidth(153.1);
        player1.setFitHeight(312);
        player1.setX(50);
        player1.setY(100);


        ImageView player2 = new ImageView("player2.png");
        player2.setFitWidth(174.4);
        player2.setFitHeight(293.8);
        player2.setX(735.6);
        player2.setY(100);


        //maps
        ImageView castleArena = new ImageView("castleArena.jpg");
        castleArena.setFitWidth(165.375);
        castleArena.setFitHeight(216.5);
        castleArena.setX(210);
        castleArena.setY(150);


        castleArena.setOnMouseEntered( event ->{
            Glow glow = new Glow(0.5);
            castleArena.setEffect(glow);
            background.setImage(new Image("castleArenaBackground.png"));

        });
        castleArena.setOnMouseExited( event ->{
            castleArena.setEffect(null);
            background.setImage(new Image("bg.jpg"));

        });

        ImageView fireArena = new ImageView("fireArena.jpg");
        fireArena.setFitWidth(165.375);
        fireArena.setFitHeight(216.5);
        fireArena.setX(385.375);
        fireArena.setY(150);


        fireArena.setOnMouseEntered( event ->{
            Glow glow = new Glow(0.5);
            fireArena.setEffect(glow);
            background.setImage(new Image("fireArenaBackground.png"));

        });
        fireArena.setOnMouseExited( event ->{
            fireArena.setEffect(null);
            background.setImage(new Image("bg.jpg"));

        });

        ImageView terraceLand = new ImageView("terraceLand.jpg");
        terraceLand.setFitWidth(165.375);
        terraceLand.setFitHeight(216.5);
        terraceLand.setX(560.75);
        terraceLand.setY(150);


        terraceLand.setOnMouseEntered( event ->{
            Glow glow = new Glow(0.5);
            terraceLand.setEffect(glow);
            background.setImage(new Image("terraceLandBackground.png"));


        });
        terraceLand.setOnMouseExited( event ->{
            terraceLand.setEffect(null);
            background.setImage(new Image("bg.jpg"));

        });


        //exit button
        ImageView exitButton = new ImageView("exitButton.png");
        exitButton.setFitWidth(195);
        exitButton.setFitHeight(53.83);
        exitButton.setX(382.5);
        exitButton.setY(400);
        exitButton.setOnMouseEntered( event ->{
            Glow glow = new Glow(100);
            exitButton.setEffect(glow);
        });
        exitButton.setOnMouseExited( event ->{
            exitButton.setEffect(null);
        });

        //on clicking map 1 option:
        castleArena.setOnMouseClicked(e->{
            try {
                ImageView gameBackground = new ImageView("castleArenaBackground.png");
                openOneVsOne(gameBackground,primaryStage);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        //on clicking map 2 option:
        terraceLand.setOnMouseClicked(e->{
            try {
                ImageView gameBackground = new ImageView("terraceLandBackground.png");
                openOneVsOne(gameBackground,primaryStage);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        //on clicking map 3 option:
        fireArena.setOnMouseClicked(e->{
            try {
                ImageView gameBackground = new ImageView("fireArenaBackground.png");
                openOneVsOne(gameBackground,primaryStage);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        //on clicking exit button
        exitButton.setOnMouseClicked(e->{
            try {
                start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

        //binding things together and creat a new scene
        root.getChildren().addAll(background,playerProfileIcon,player1,castleArena,terraceLand,fireArena,player2,exitButton);
        Scene versusScene = new Scene(root,960,540);
        primaryStage.setScene(versusScene);
        primaryStage.setResizable(false);
        Image stageIcon = new Image("stageIcon.png");
        primaryStage.setTitle("LIBERATION WARRIOR");
        primaryStage.getIcons().add(stageIcon);
        primaryStage.show();
    }


    // Method to update the current player data in the playerData array
//    private void updatePlayerDataInArray(Object[] updatedPlayerData) {
//        // Find the index of the current player in the playerData array
//        int currentPlayerArrayIndex = -1;
//
//        for (int i = 0; i < currentPlayerIndex; i++) {
//            if (updatedPlayerData == playerData[i]) {
//                currentPlayerArrayIndex = i;
//                break;
//            }
//        }
//        // If the current player is found, update the data
//        if (currentPlayerArrayIndex != -1) {
//            System.arraycopy(updatedPlayerData, 0, playerData[currentPlayerArrayIndex], 0, updatedPlayerData.length);
//        } else {
//            System.out.println("Error: Current player not found in playerData array.");
//        }
//    }

    //method to open level one

    //method to open one Vs one screen
    private void openOneVsOne(ImageView img,Stage primaryStage) throws InterruptedException {
        // initiate the level 1 class and
        VS11 oneVsOne = new VS11(img,primaryStage);
    }


    private void printPlayerData() {
        System.out.println("Player Data:");
        for (int i = 0; i < currentPlayerIndex; i++) {
            System.out.println("Player " + (i + 1) + ":");
            System.out.println("Name: " + playerData[i][0]);
            System.out.println("Username: " + playerData[i][1]);
            System.out.println("Password: " + playerData[i][2]);
            System.out.println("Number: " + playerData[i][3]);
            System.out.println("Gender: " + playerData[i][4]);
            System.out.println("Score: " + playerData[i][5]);

            System.out.println("-------------------------");
        }
    }
    private void printCurrPlayerData(Object [] arr){
        System.out.println("The current player data");
        System.out.println("Name: " + arr[0]);
        System.out.println("Username: " + arr[1]);
        System.out.println("Password: " + arr[2]);
        System.out.println("Number: " + arr[3]);
        System.out.println("Gender: " + arr[4]);
        System.out.println("Score: " + arr[5]);

        System.out.println("-------------------------");
    }
}


