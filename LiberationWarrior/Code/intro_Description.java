package com.example.demo20;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class intro_Description extends Application {

    // Declare the label and the text variables
    private Label label;
    private String text = "In a realm where swords clash with sorcery, a skilled samurai engages\n in a fierce battle with a powerful wizard. However, the tide turns when\n the wizard, in a desperate move,casts a unique spell that banishes\n the samurai to another dimension. Now, the displaced warrior \n embarks on aperilous journey through unknown lands, overcoming\n challengesn and seeking a way to return to confront the elusive\n wizard once more, determined to settle the score in an epic showdown \nof skill and magic.";
    // Declare the index and the displayText variables
    private int index = 0;
    private String displayText = "";
    MediaPlayer sound;

    // Declare the number of characters to print before a new line
    //private int maxChars = 40;

    // Declare the number of characters printed on the current line
    private int currentChars = 0;

    // Declare the total number of characters appended to the displayText
    private int totalChars = 0;

    // Create a constructor for the class
    public intro_Description() {
        // Initialize the label
        label = new Label();
        ImageView Background = new ImageView();

        // Create a timeline and a keyframe
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(82), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Check if the animation is not finished
                if (!isFinished()) {
                    // Append the next character to the displayText
                    displayText += text.charAt(totalChars);

                    // Increment the index, the currentChars, and the totalChars
                    index++;
                    currentChars++;
                    totalChars++;
                    label.setFont(Font.font("Jokerman", FontWeight.BOLD, FontPosture.ITALIC,25));
                    label.setTextFill(Color.WHITE);

                    // Update the text of the label
                    label.setText(displayText);
                } else {
                    // Stop the timeline
                    timeline.stop();
                }
            }
        });
        // Add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);

        // Set the cycle count of the timeline to indefinite
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Play the timeline
        timeline.play();
    }
    // Override the start method to set up the stage and the scene
    @Override
    public void start(Stage stage) {
        // Set the title and the size of the stage
        stage.setTitle("Lib warrior Tracing Example");
        Media sound = new Media("file:///C:/Users/mbawa/Downloads/So.mpeg/");
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaPlayer.play();
        // Create a stack pane to hold the label
        Pane root = new Pane();
        ImageView bg = new ImageView("TestBackg.gif");
        bg.setFitHeight(500);
        bg.setFitWidth(1000);
        //text bg
        ImageView character = new ImageView("char_new.gif");
        character.setFitWidth(360);
        character.setFitHeight(480);
        character.setX(5);
        character.setY(80);
        label.setLayoutX(100);
        label.setLayoutY(100);
        // Add the label to the stack pane
        root.getChildren().addAll(bg,character,label,mediaView);

        // Create a scene with the stack pane as the root node
        Scene scene = new Scene(root,1000,500);

        // Set the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }
    // Check if the animation is finished
    private boolean isFinished() {
        // Return true if the totalChars is equal to the text length
        return totalChars == text.length();
    }
    // Create a main method to run the application
    public static void main(String[] args) {
        launch(args);
    }
}

