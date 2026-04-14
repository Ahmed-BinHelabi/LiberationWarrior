package com.example.demo20;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Random;

public class LV1 extends Application {
    MediaPlayer ost;
    int score = 0;
    Text scoreText;
    int characterHP;
    double enemyHP = 10;
    boolean block;
    Rectangle hiddenBarrier1,hiddenBarrier2;

    //Scene
    Scene scene;

    //Images
    Image attackSheet, attackLeftSheet, walkSheet, idleSheet, idleReversed, walkLeftSheet, awakeSheet, face, enemyIdleSheet,enemyDeadSheet, enemyHurtSheet, enemyRunSheet, enemyAttackSheet;

    //Sprites
    Sprite attack, attackLeft, walk, walkLeft, idle, idleLeft, awake;

    Sprite enemyIdle, enemyDead, enemyDeath, enemyRun, enemyAttack;

    //Animations
    Timeline attackRightAnimation,attackLeftAnimation,walkAnimation,walkLeftAnimation,awakeAnimation,timeFinish,loop,timeChecker, finishLevelOne,endMessage;

    Timeline enemyAttackAnimation , enemyDeadAnimation, enemyDeathAnimation, enemyRunAnimation;
    boolean dead;
    Pane root;
    Group mainCharacter, enemy;
    Rectangle rectangle;

    Text counterText, roundMessage,levelComplete;
    FadeTransition fadeTransition;
    Timeline timer;
    private boolean animationStarted = false;
    String orientation = "Right";
    String enemyOrientation = "Left";
    boolean awakeFinished = false;
    static boolean timerStopped;
    int round = 1;
    boolean levelFinished;
    ImageView blood;
    private int loggedInPlayerIndex;
    private Object[] loggedInPlayer;
    Scene loggedInPlayerScene;
    public LV1(Stage primaryStage, Object [] loggedInPlayer, int index, Scene scene) throws InterruptedException {
        this.loggedInPlayer = loggedInPlayer;
        this.loggedInPlayerIndex = index;
        this.loggedInPlayerScene = scene;
        start(primaryStage);
    }

    public LV1() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/LV1OST.mp4/"));
        ost.setCycleCount(MediaPlayer.INDEFINITE);
        ost.play();
        System.out.println(Arrays.toString(loggedInPlayer));
        blood = new ImageView(new Image("bloodEnemy.gif"));
        blood.setVisible(false);
        timerStopped = false;
        root = new Pane();
        scene = new Scene(root, 1000, 500);
        // character = new Character(50, 350);
        ImageView background = new ImageView("RuinedCityBackground.png");
        background.setFitHeight(500);
        background.setFitWidth(1000);
        //swordAttack = new MediaPlayer(new Media("file:///C:/Users/mbawa/OneDrive/Desktop/SwordAttack.mp3/"));
//        swordAttack.setCycleCount(MediaPlayer.INDEFINITE);
       // swordAttack.setVolume(0.2);


        //Intro
        Text text = new Text("\t\t*DEMO*\nCollect more than 10 points to win \n      (Tap the screen to start)");
        text.setFill(Color.rgb(196, 183, 177));
        text.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 32px; -fx-text-fill: WHITE;");
        text.setX(340);
        text.setY(260);
        Glow glow = new Glow(100);
        FadeTransition fadeIn = fadeIn(text, 1500, -1);
        fadeIn.play();
        scoreText = new Text("Score: "+ score);
        scoreText.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 28px; -fx-text-fill: BLACK; -fx-font-weight: bold;");
        scoreText.setTranslateX(5);
        scoreText.setTranslateY(30);


        // Sprite Sheets
        attackSheet = new Image("Attack_1.png");
        attackLeftSheet = new Image("Attack_1Left.png");
        walkSheet = new Image("Walk.png");
        idleSheet = new Image("Idle.png");
        idleReversed = new Image("IdleLeft.png");
        walkLeftSheet = new Image("WalkL.png");
        awakeSheet = new Image("Awake.png");
        //slash = new ImageView("Slash.gif");
        //slash.setVisible(false);

        //Enemy Sheets
        enemyIdleSheet = new Image("Enemy Idle.png");
        enemyDeadSheet = new Image("Enemy Dead.png");
        enemyHurtSheet = new Image("Enemy Hurt.png");
        enemyRunSheet = new Image("Enemy Run.png");
        enemyAttackSheet = new Image("Enemy Attack.png");


        //Sprite objects
        attack = new Sprite(attackSheet, 6);
        attackLeft = new Sprite(attackLeftSheet, 6);
        walk = new Sprite(walkSheet, 8);
        idle = new Sprite(idleSheet, 6);
        idleLeft = new Sprite(idleReversed, 6);
        walkLeft = new Sprite(walkLeftSheet, 8);
        awake = new Sprite(awakeSheet, 3);
        mainCharacter = new Group(attack,attackLeft,walk,idle,idleLeft,walkLeft,awake);


        //Enemy Sprite
        enemyIdle = new Sprite(enemyIdleSheet,1);
        enemyAttack = new Sprite(enemyAttackSheet, 7);
        enemyDeath = new Sprite(enemyDeadSheet, 2);
        enemyRun = new Sprite(enemyRunSheet, 9);
        enemy = new Group(enemyIdle ,enemyAttack  , enemyRun,blood);


        //Configuring the character
        mainCharacter.setTranslateX(50);
        mainCharacter.setTranslateY(350);

        enemy.setTranslateY(350);
        enemy.setTranslateX(350);
        attack.setViewport(new Rectangle2D(0, 0, 128, 128));
        attackLeft.setViewport(new Rectangle2D(5 * 128, 0, 128, 128));
        walk.setViewport(new Rectangle2D(0, 0, 128, 128));
        walkLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        idle.setViewport(new Rectangle2D(0, 0, 128, 128));
        idleLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        awake.setViewport(new Rectangle2D(0, 0, 128, 128));
        enemyIdle.setViewport(new Rectangle2D(0, 0, 128, 128));

        visibilityOff(attackLeft, attack, walk, walkLeft, idleLeft, idle);
        visibilityOff(enemyAttack,enemyDeath, enemyDeath,enemyRun);

        //Timelines for animations
        walkAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (animationStarted &&!mainCharacter.getBoundsInParent().intersects(hiddenBarrier2.getBoundsInParent())) {
                orientation = "Right";
                walk.setTranslateX(walk.getTranslateX() + 10);
                mainCharacter.setTranslateX(walk.getTranslateX());
                walk.nextFrame();
            }
        }));
        walkLeftAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (animationStarted&&!mainCharacter.getBoundsInParent().intersects(hiddenBarrier1.getBoundsInParent())) {
                orientation = "Left";
                walkLeft.setTranslateX(walkLeft.getTranslateX() - 10);
                mainCharacter.setTranslateX(walkLeft.getTranslateX());
                walkLeft.reverseFrame();
            }
        }));
        awakeAnimation = new Timeline(
                new KeyFrame(Duration.millis(500), event -> {
                    awake.nextFrame(); // Animate the attack by advancing to the next frame
                })
        );
        awakeAnimation.setCycleCount(awake.frameCount);
        root.setOnMousePressed(e -> {
            if (!awakeFinished) {
                text.setEffect(glow);
                awakeAnimation.play();
                fadeIn.stop();
                fadeOut(text, 1500);
            }
        });
        walkAnimation.setCycleCount(Timeline.INDEFINITE);
        walkLeftAnimation.setCycleCount(Timeline.INDEFINITE);

        // Add this block in your start() method



        counterText = new Text("1:00");
        counterText.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 28px; -fx-fill: red; -fx-font-weight: BOLD;");
        counterText.setTranslateX(scene.getWidth() - 70);
        counterText.setTranslateY(30);
        roundMessage = new Text("Round Two\n 1.5x Speed");
        roundMessage.setFont(Font.font("rainyhearts" , FontWeight.BOLD , FontPosture.REGULAR,40));
        roundMessage.setFill(Color.GOLD);
        roundMessage.setX(400);
        roundMessage.setY(250);
        roundMessage.setVisible(false);
        levelComplete = new Text("Level Complete");
        levelComplete.setX(400);
        levelComplete.setY(250);
        levelComplete.setFont(Font.font("rainyhearts" , FontWeight.BOLD , FontPosture.REGULAR,32));
        levelComplete.setVisible(false);
        // Add this block in your start() method
        endMessage = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            if (round == 2) roundMessage.setVisible(true);
        }));
        endMessage.setCycleCount(2);
        endMessage.setOnFinished(e->{
            FadeTransition fadeOut = fadeOut(roundMessage,2500);
            fadeOut.play();
        });
        timer = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    // enemy.setTranslateX();
                    updateCounter(counterText);
                    //enemy.setVisible(true);
                })
        );

        hiddenBarrier1 = new Rectangle(25,500);
        hiddenBarrier1.setFill(Color.GAINSBORO);
        hiddenBarrier1.setTranslateX(-10);
        hiddenBarrier1.setOpacity(0.3);
        hiddenBarrier1.setVisible(false);
        hiddenBarrier2 = new Rectangle(90,500);
        hiddenBarrier2.setFill(Color.GAINSBORO);
        hiddenBarrier2.setTranslateX(990);
        hiddenBarrier2.setOpacity(0.3);
        hiddenBarrier2.setVisible(false);
        timer.setCycleCount(Timeline.INDEFINITE);
        root.getChildren().addAll(background, new ImageView(face), text,mainCharacter , enemy,counterText,scoreText,hiddenBarrier1,hiddenBarrier2);

        timeFinish = new Timeline(new KeyFrame(Duration.millis(100), e->{
            enemy.setVisible(false);
        }));
        loop = new Timeline(new KeyFrame(Duration.millis(100) , e->{

            if(counterText.getText().equals("0:30")) {
//                enemy.setVisible(false);
//                enemy.setVisible(true);
                round++;
                enemy.setTranslateX(3000);
                //timerStopped = false;
//                roundMessage.setVisible(true);
                endMessage.play();

                timer.stop();
              //  counterText.setText("0:05");
                updateCounter(counterText);
                timer.play();

            }
        }));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
        counterText.setFill(Color.RED);

        awakeAnimation.setOnFinished(e -> {
            awake.setVisible(false);

            enemyRunAnimation.setCycleCount(Animation.INDEFINITE);
            enemyRunAnimation.play();
            enemyAttackAnimation.setCycleCount(Animation.INDEFINITE);
            enemyAttackAnimation.play();
            counterText.setFill(Color.WHITE);
            timer.play();
            idle.setVisible(true);
            awakeFinished = true;
        });

        finishLevelOne = new Timeline(new KeyFrame(Duration.millis(100), e -> {
//            System.out.println("Round "+ round);

            if (counterText.getText().equals("0:00")) {
                enemyRunAnimation.stop();
                enemyAttackAnimation.stop();
                timer.stop();
               // counterText.setText("0:00");
                //counterText.setVisible(false);
                mainCharacter.setVisible(false);
                enemy.setVisible(false);
                roundMessage.setVisible(false);
                    if (score >= 10) {
                        ost.stop();
                        Text levelComplete = new Text("Level Complete");
                        Rectangle cover = new Rectangle(750, 175, Color.BLACK);
                        ImageView mainMenu = new ImageView(new Image("Main Menu.png"));
                        ImageView character = new ImageView(new Image("Idle.png"));
                        ImageView retry = new ImageView(new Image("Retry.png"));
                        Group group = new Group();
                        group.getChildren().addAll(cover, levelComplete, character, scoreText, retry, mainMenu);
                        root.getChildren().add(group);
                        mainCharacter.setVisible(false);
                        enemy.setVisible(false);
                        mainMenu.setFitWidth(195);
                        mainMenu.setFitHeight(56);
                        mainMenu.setTranslateY(273);
                        mainMenu.setTranslateX(300);
                        retry.setFitWidth(195);
                        retry.setFitHeight(56);
                        retry.setTranslateY(273);
                        retry.setTranslateX(500);
                        mainMenu.setOnMouseEntered(event -> {
                            mainMenu.setEffect(glow);
                        });
                        mainMenu.setOnMouseExited(event -> {
                            mainMenu.setEffect(null);
                        });
                        retry.setOnMouseEntered(event -> {
                            retry.setEffect(glow);
                        });
                        retry.setOnMouseExited(event -> {
                            retry.setEffect(null);
                        });

                        //on clicking main menu
                        mainMenu.setOnMouseClicked(event->{
                            loggedInPlayer [5] = String.valueOf(score);
                            GameIntro gameIntro = new GameIntro();
                            gameIntro.ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/MainmenuOST.mp4/"));
                            gameIntro.openLoggedInPlayerScreen(loggedInPlayer,loggedInPlayerIndex,primaryStage,true,false,loggedInPlayerScene);
                        });


                        //on clicking retry
                        retry.setOnMouseClicked(event->{
                            LV1 lv1 = new LV1();
                            try {
                                lv1.start(primaryStage);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        scoreText.setFill(Color.WHITE);
                        scoreText.setX(380);
                        scoreText.setY(215);
                        scoreText.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 40px;");
                        levelComplete.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 48px;");
                        levelComplete.setFill(Color.GOLD);

                        character.setFitHeight(200);
                        character.setFitWidth(200);
                        character.setViewport(new Rectangle2D(0, 0, 128, 128));
                        character.setTranslateX(100);
                        character.setTranslateY(100);
                        cover.setOpacity(0.6);
                        cover.setTranslateY(150);
                        cover.setTranslateX(125);
                        levelComplete.setX(350);
                        levelComplete.setY(200);
                        finishLevelOne.stop();
                        FadeTransition ft = fadeIn(group, 500, 1);
                        ft.setAutoReverse(true);
                        ft.play();

                    }
                    else {
                        ost.stop();
                        mainCharacter.setVisible(false);
                        enemy.setVisible(false);
                        Text levelComplete = new Text("Level Failed");
                        Rectangle cover = new Rectangle(750, 175, Color.BLACK);
                        ImageView mainMenu = new ImageView(new Image("Main Menu.png"));
                        ImageView retry = new ImageView(new Image("Retry.png"));
                        mainMenu.setFitWidth(195);
                        mainMenu.setFitHeight(56);
                        mainMenu.setTranslateY(273);
                        mainMenu.setTranslateX(300);
                        retry.setFitWidth(195);
                        retry.setFitHeight(56);
                        retry.setTranslateY(273);
                        retry.setTranslateX(500);
                        mainMenu.setOnMouseEntered(event -> {
                            mainMenu.setEffect(glow);
                        });
                        mainMenu.setOnMouseExited(event -> {
                            mainMenu.setEffect(null);
                        });
                        retry.setOnMouseEntered(event -> {
                            retry.setEffect(glow);
                        });
                        retry.setOnMouseExited(event -> {
                            retry.setEffect(null);
                        });
                        //on clicking main menu
                        mainMenu.setOnMouseClicked(event->{
                            GameIntro gameIntro = new GameIntro();
                            gameIntro.ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/MainmenuOST.mp4/"));
                            gameIntro.openLoggedInPlayerScreen(loggedInPlayer,loggedInPlayerIndex,primaryStage,false,false,loggedInPlayerScene);
                        });
                        //on clicking retry
                        retry.setOnMouseClicked(event->{
                            LV1 lv1 = new LV1();
                            try {
                                lv1.start(primaryStage);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        scoreText.setFill(Color.WHITE);
                        scoreText.setX(380);
                        scoreText.setY(215);
                        scoreText.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 40px;");
                        levelComplete.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 48px;");
                        levelComplete.setFill(Color.RED);
                        ImageView character = new ImageView(new Image("Idle.png"));
                        character.setFitHeight(200);
                        character.setFitWidth(200);
                        character.setViewport(new Rectangle2D(0, 0, 128, 128));
                        character.setTranslateX(100);
                        character.setTranslateY(100);
                        cover.setOpacity(0.6);
                        cover.setTranslateY(150);
                        cover.setTranslateX(125);
                        levelComplete.setX(350);
                        levelComplete.setY(200);
                        finishLevelOne.stop();
                        Group group = new Group();
                        group.getChildren().addAll(cover, levelComplete, character, scoreText, retry, mainMenu);
                        FadeTransition ft = fadeIn(group, 1000, 1);
                        ft.play();
                        root.getChildren().add(group);
                        ft.setOnFinished(event->{
                            enemy.setVisible(false);
                            enemyAttack.setVisible(false);
                        });
                    }

            }
        }));

        finishLevelOne.setCycleCount(Animation.INDEFINITE);
        finishLevelOne.play();

        root.getChildren().addAll(roundMessage);



        enemyRespawn();
        enemyRun();
        enemyAttack();
        attackCharaceter();
        walk();
        attackCharaceter();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("LIBERATION WARRIOR");
        primaryStage.show();
    }

    public static class Sprite extends ImageView {
        private final int frameCount;
        private int currentFrame;
        Image image;
        int lastFrame;

        public Sprite(Image image, int frameCount) {
            super(image);
            this.image = image;
            this.frameCount = frameCount;
            this.currentFrame = 0;
            this.lastFrame = frameCount;
        }

        public void nextFrame() {
            currentFrame = (currentFrame + 1) % frameCount;
            int frameWidth = (int) image.getWidth() / frameCount;
//            System.out.println("Curr frame "+ currentFrame);
            setViewport(new Rectangle2D(currentFrame * frameWidth, 0, frameWidth, getImage().getHeight()));
        }

        public void reverseFrame() {
            lastFrame = lastFrame - 1;
            if (lastFrame == -1) lastFrame = frameCount - 1;
            int frameWidth = (int) image.getWidth() / frameCount;
            setViewport(new Rectangle2D(lastFrame * frameWidth, 0, frameWidth, getImage().getHeight()));
        }

    }

    public static void visibilityOff(Sprite... sprites) {
        for (Sprite i : sprites) {
            i.setVisible(false);
        }
    }

    public static void setX(double x, Sprite... sprites) {
        for (Sprite i : sprites) {
            i.setTranslateX(x);
        }
    }

    public static void setY(double y, Sprite... sprites) {
        for (Sprite i : sprites) {
            i.setTranslateY(y);
        }
    }

    public static FadeTransition fadeIn(Node node, double time, int cycleCount) {
        FadeTransition ft = new FadeTransition(Duration.millis(time), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(cycleCount);
        ft.setAutoReverse(true);

        return ft;
    }

    public static FadeTransition fadeOut(Node node, double time) {
        FadeTransition ft = new FadeTransition(Duration.millis(time), node);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        return ft;
    }

    public void attackCharaceter() {
        attackRightAnimation = new Timeline(
                new KeyFrame(Duration.millis(80), event -> {

                    if (animationStarted) {
                        attack.setTranslateX(mainCharacter.getTranslateX());
                        attack.nextFrame();


                        // Animate the attack by advancing to the next frame
                        if (enemy.isVisible()){
                            if (mainCharacter.getBoundsInParent().intersects(enemy.getBoundsInParent()) ) {
                                double damage = (double) 6/6;
                                enemyHP -= damage;
                                blood.setVisible(true);

//                                enemy.setEffect(new Glow(30));
                                enemy.setEffect(new Glow(30));

//                                enemyHurt();
//                                System.out.println(enemyHP);


                                if (enemyHP <= 0) {
                                    score += 3;
                                    scoreText.setText("Score: "+score);
                                    enemy.setVisible(false);
                                    dead = true;


                                }}
                        }}
                })
        );

        attackLeftAnimation = new Timeline(
                new KeyFrame(Duration.millis(75), event -> {
                    if (animationStarted) {
                        attackLeft.setTranslateX(mainCharacter.getTranslateX());
                        attackLeft.reverseFrame();

                        // Animate the attack by advancing to the next frame
                        if (enemy.isVisible()){
                            if (mainCharacter.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                                double damage = (double) 6/6;
                                blood.setVisible(true);
                                enemyHP -= damage;
//                                slash.setTranslateX(enemy.getTranslateX()-10);
                                enemy.setEffect(new Glow(30));
//                                System.out.println(enemyHP);

                                if (enemyHP <= 0) {
                                    enemy.setVisible(false);
                                    score += 3;
                                    scoreText.setText("Score: "+score);
                                    dead=true;

                                }}
                        }}
                })
        );

        attackRightAnimation.setCycleCount(attack.frameCount);
        attackLeftAnimation.setCycleCount(attackLeft.frameCount);


        attackLeftAnimation.setOnFinished(e -> {
            try {
                Thread.sleep(45);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            blood.setVisible(false);
            enemy.setEffect(null);
            attackLeft.setViewport(new Rectangle2D(5 * 128, 0, 128, 128));
        });
        attackRightAnimation.setOnFinished(e->{
            blood.setVisible(false);
            enemy.setEffect(null);

        });
        onPressed();





    }

    public void walk() {
        onPressed();
        scene.setOnKeyReleased(e -> {
            if (awakeFinished) {
                setX(mainCharacter.getTranslateX(), idle, idleLeft);
                if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                    walkAnimation.stop();
                    orientation = "Right";
                    visibilityOff(idleLeft, walk, attackLeft);
                    if (attack.isVisible() && attackRightAnimation.getStatus() == Animation.Status.RUNNING || walkLeftAnimation.getStatus() == Animation.Status.RUNNING) {
                        idle.setVisible(false);
                    } else {
                        visibilityOff(walkLeft, attack);
                        idle.setVisible(true);
                    }
                    setX(mainCharacter.getTranslateX(), walkLeft, walk, attack);
                }
                else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                    walkLeftAnimation.stop();
                    orientation = "Left";

                    visibilityOff(idle, walkLeft, attack);
                    if (attackLeft.isVisible() && attackLeftAnimation.getStatus() == Animation.Status.RUNNING || walkAnimation.getStatus() == Animation.Status.RUNNING) {
                        idleLeft.setVisible(false);
                    } else {
                        walkLeftAnimation.stop();
                        visibilityOff(walk, attackLeft);
                        idleLeft.setVisible(true);
                    }
                    setX(mainCharacter.getTranslateX(), walk, walkLeft, attackLeft);
                }
                else if (e.getCode() == KeyCode.SPACE){
                    walkAnimation.stop();
                    walkLeftAnimation.stop();
                }
            }
        });



    }
    public void onPressed(){
        scene.setOnKeyPressed(event -> {
            if (awakeFinished) {
                if (event.getCode() == KeyCode.SPACE) {
                    animationStarted = true;
                    visibilityOff(idle, idleLeft, walk, walkLeft);
                    if (orientation.equals("Right")) {
                        attackLeft.setVisible(false);
                        attack.setVisible(true);
                        attack.setTranslateX(mainCharacter.getTranslateX());
                        attackRightAnimation.play();
                    } else if (orientation.equals("Left")) {
                        attack.setVisible(false);
                        attackLeft.setVisible(true);
                        attackLeft.setTranslateX(mainCharacter.getTranslateX());
                        attackLeftAnimation.play();
                    }
                }
                else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                    if (walkLeftAnimation.getStatus() != Animation.Status.RUNNING) {
                        visibilityOff(idle, idleLeft, attack, attackLeft, walkLeft);
                        walk.setVisible(true);
                        setX(mainCharacter.getTranslateX(), walk, walkLeft);
                        animationStarted = true;
                        walkAnimation.play();
                    }
                } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                    if (walkAnimation.getStatus() != Animation.Status.RUNNING) {
                        visibilityOff(idle, idleLeft, attack, attackLeft, walk);
                        walkLeft.setVisible(true);
                        walk.setTranslateX(mainCharacter.getTranslateX());
                        walkLeft.setTranslateX(walk.getTranslateX());
                        animationStarted = true;
                        walkLeftAnimation.play();
                    }
                }
//                else if(timerStopped&& event.getCode()==KeyCode.ENTER){
//                    enemy.setVisible(true);
//                    timerStopped=false;
//                    counterText.setText("0:07");
//                    updateCounter(counterText);
//
//
//                }
            }
        });
    }



    public void enemyRun(){
        int cycle = 0;
        enemyRunAnimation = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            visibilityOff(enemyIdle, enemyDeath,enemyAttack,enemyDeath);
            enemyRun.setVisible(true);
            if (round == 1) {
                if (enemyOrientation.equals("Right")) {
                    enemy.setTranslateX(enemy.getTranslateX() + 20);
                    enemyRun.nextFrame();
                }
                if (enemy.getTranslateX() > 700 || enemyOrientation.equals("Left")) {
                    enemyRun.setImage(new Image("Enemy Run_Left.png"));
                    enemyOrientation = "Left";
                    enemy.setTranslateX(enemy.getTranslateX() - 20);
                    enemyRun.reverseFrame();
                }
                if (enemy.getTranslateX() < 100) {
                    enemyOrientation = "Right";
                    enemyRun.setImage(enemyRunSheet);
                }
            }
            else if (round == 2) {
                if (enemyOrientation.equals("Right")) {
                    enemy.setTranslateX(enemy.getTranslateX() + 20*2);
                    enemyRun.nextFrame();
                }
                if (enemy.getTranslateX() > 700 || enemyOrientation.equals("Left")) {
                    enemyRun.setImage(new Image("Enemy Run_Left.png"));
                    enemyOrientation = "Left";
                    enemy.setTranslateX(enemy.getTranslateX() - 20*2);
                    enemyRun.reverseFrame();
                }
                if (enemy.getTranslateX() < 100) {
                    enemyOrientation = "Right";
                    enemyRun.setImage(enemyRunSheet);
                }
            }
        }));


//        enemyRunAnimation.setCycleCount(Animation.INDEFINITE);
//        enemyRunAnimation.play();
    }

    public void enemyAttack(){
        enemyAttackAnimation = new Timeline(new KeyFrame(Duration.millis(100), e->{
            if (enemy.getBoundsInParent().intersects(mainCharacter.getBoundsInParent())){
                visibilityOff(enemyIdle, enemyDeath, enemyRun, enemyDeath);
                enemyAttack.setVisible(true);
                if (enemyOrientation.equals("Right") || enemy.getTranslateX() < mainCharacter.getTranslateX()){
                    enemyAttack.setImage(enemyAttackSheet);
                    enemyAttack.nextFrame();
                } else if (enemyOrientation.equals("Left") || enemy.getTranslateX() < mainCharacter.getTranslateX()) {
                    enemyAttack.setImage(new Image("Enemy Attack_Left.png"));
                    enemyAttack.reverseFrame();
                }
            }
        }));
//        enemyAttackAnimation.setCycleCount(Animation.INDEFINITE);
//        enemyAttackAnimation.play();
    }


    public void enemyRespawn(){

        Timeline eventFiring = new Timeline(new
                KeyFrame(Duration.millis(3000) , event -> {
            if (dead){
                enemyHP = 10;
                enemy.setVisible(true);
                dead = false;
                Random r = new Random();
                int result = r.nextInt(-25,1000) ;
                enemy.setTranslateX(result);
            }
        }));
        eventFiring.setCycleCount(Animation.INDEFINITE);
        eventFiring.play();
    }
    // Add this method in your Testing class
    private void updateCounter(Text counterText) {
        int minutes = Integer.parseInt(counterText.getText().split(":")[0]);
        int seconds = Integer.parseInt(counterText.getText().split(":")[1]);
        if (seconds > 0) {
            seconds--;
        }
        else {
            if (minutes > 0) {
                minutes--;
                seconds = 59;
            }
            else if(seconds<=0){

                    // Stop the timer or perform any action when the countdown reaches 0
                    timerStopped = true;
                    timer.stop();


            }
        }

        // Update the counter text
        counterText.setText(String.format("%d:%02d", minutes, seconds));
    }


}