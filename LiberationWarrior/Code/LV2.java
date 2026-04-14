package com.example.demo20;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class LV2 extends Application {
    double characterHP=150;
    double enemyHP = 100,bossHP=100;
    Text roundMessage,levelComplete, scoreText;

    //Scene
    Scene scene;
    MediaPlayer ost;

    //Images
    Image attackSheet, attackLeftSheet, walkSheet, idleSheet, idleReversed, walkLeftSheet, awakeSheet, enemyIdleSheet,enemyDeadSheet, enemyHurtSheet, enemyRunSheet, enemyAttackSheet;

    //Main character Sprites
    Sprite attack, attackLeft, walk, walkLeft, idle, idleLeft,block,death,deathLeft;
    //Enemy Sprites
    Sprite enemyIdle, enemyDead, enemyHurt, enemyRun, enemyAttack,enemyFire,enemyCharge;
    //Boss Sprites
    Sprite bossIdle, bossBladeMagic, bossLightningMagic, bossFireMagic, bossAnger, bossDeath, lightning, fire, blade;
    //Animations
    Timeline attackRightAnimation,attackLeftAnimation,walkAnimation,walkLeftAnimation,blockAnimation,deathCharacterAnimation,deathLeftCharacterAnimation;

    Timeline enemyAttackAnimation = new Timeline(new KeyFrame(Duration.millis(10))) ,enemyMagicAnimation, enemyDeadAnimation,enemyChargeAnimation, enemyRunAnimation,move,moveBack,enemyDeath;

    Timeline bossIdleAnimation, bossBladeMagicAnimation, bossLightningMagicAnimation, bossFireMagicAnimation, bossAngerAnimation, bossDeathAnimation, bossAnimation, BladeMagicAnimation, LightningMagicAnimation, FireMagicAnimation,magicAnimation;

    boolean dead;
    Pane root;
    Group mainCharacter, enemy,boss,magic;
    Text counterText, round1Message, enemyHealthText,characterHealthText,clickHere;
    boolean phase2, phase3,phase4,phase5;
    Rectangle enemyHealthBar,characterHealthBar, magicBarrier, hiddenBarrier;
    Timeline timer;
    int score;

    private boolean animationStarted = false;
    String orientation = "Right";
    String enemyOrientation = "Left";
    boolean awakeFinished = false;
    static boolean timerStopped;
    int round = 1;
    int attackCounter;
    int magicX;
    boolean bossDead,deadCh;
    boolean blocking;
    private boolean isBack;
    private boolean hit;
    private boolean canMove;
    private Sprite gate;
    private Timeline gateAnimation;
    boolean isGateOpened;
    private FadeTransition showGate = new FadeTransition();
    private FadeTransition showTextAnimation;

    private int loggedInPlayerIndex;
    private Object[] loggedInPlayer;
    Scene loggedInPlayerScene;

    public LV2(Stage primaryStage, Object [] loggedInPlayer, int index, Scene scene) throws InterruptedException {
        this.loggedInPlayer = loggedInPlayer;
        this.loggedInPlayerIndex = index;
        this.loggedInPlayerScene = scene;
        start(primaryStage);
    }

    public LV2() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println(Arrays.toString(loggedInPlayer));
        ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/LV2OST.mp4/"));
        ost.setCycleCount(MediaPlayer.INDEFINITE);
        ost.play();
        clickHere = new Text("The BLACK BLADE is calling for you\n\t   (Click ENTER)");
        clickHere.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 24px;");
        clickHere.setFill(Color.WHITE);
        clickHere.setX(475);
        clickHere.setY(170);
        FadeTransition quickFade = new FadeTransition(Duration.millis(1) , clickHere);
        quickFade.setFromValue(1);
        quickFade.setToValue(0);
        quickFade.play();
        root = new Pane();
        magicBarrier = new Rectangle(400,500);
        magicBarrier.setArcHeight(250);
        magicBarrier.setArcWidth(125);
        magicBarrier.setFill(Color.GAINSBORO);
        magicBarrier.setTranslateX(750);
        magicBarrier.setOpacity(0.3);
        hiddenBarrier = new Rectangle(25,500);
        hiddenBarrier.setFill(Color.GAINSBORO);
        hiddenBarrier.setTranslateX(0);
        hiddenBarrier.setOpacity(0.3);
        hiddenBarrier.setVisible(false);
        scene = new Scene(root, 1000, 500);
        // character = new Character(50, 350);
        ImageView background = new ImageView("LV2 Background.png");
        background.setFitHeight(500);
        background.setFitWidth(1000);

        // Sprite Sheets
        attackSheet = new Image("Attack_1.png");
        attackLeftSheet = new Image("Attack_1Left.png");
        walkSheet = new Image("Walk.png");
        idleSheet = new Image("Idle.png");
        idleReversed = new Image("IdleLeft.png");
        walkLeftSheet = new Image("WalkL.png");
        awakeSheet = new Image("Awake.png");


        //Sprite objects
        attack = new Sprite(attackSheet, 6);
        attackLeft = new Sprite(attackLeftSheet, 6);
        walk = new Sprite(walkSheet, 8);
        idle = new Sprite(idleSheet, 6);
        idleLeft = new Sprite(idleReversed, 6);
        walkLeft = new Sprite(walkLeftSheet, 8);
        block = new Sprite(new Image("Block.png") , 1);
        death = new Sprite(new Image("death.png") , 3);
        deathLeft = new Sprite(new Image("deathLeft.png") , 3);
        mainCharacter = new Group(attack,attackLeft,walk,idle,idleLeft,walkLeft,block , death , deathLeft);
        mainCharacter.setTranslateX(50);
        mainCharacter.setTranslateY(200);


        //Enemy Sprite
        enemyIdle = new Sprite(new Image("LV2Idle.png"),1);
        enemyAttack = new Sprite(new Image("LV2Attack.png"), 4);
        enemyFire = new Sprite(new Image("LV2Fireball.png"), 8);
        enemyCharge = new Sprite(new Image("Charge.png") ,12);
        enemyDead = new Sprite(new Image("LV2Dead.png"), 6);
        enemyRun = new Sprite(new Image("LV2Run.png"), 8);
        enemy = new Group(enemyIdle ,enemyAttack , enemyFire,enemyDead , enemyRun);
        enemyCharge.setTranslateX(830);
        enemyCharge.setTranslateY(250);
        enemy.setTranslateX(830);
        enemy.setTranslateY(200);

        //Other sprites
        gate = new Sprite(new Image("gateSprite.png"),12);
        scene.setOnMousePressed(e->{
            System.out.println("X: "+e.getX() +"\nY: "+e.getY());
        });
        gate.setTranslateX(550);
        gate.setTranslateY(101.5);
        gate.setViewport(new Rectangle2D(0,0,160,165));
        gate.setVisible(false);

        //ViewPort
        attack.setViewport(new Rectangle2D(0, 0, 128, 128));
        death.setViewport(new Rectangle2D(0, 0, 128, 128));
        deathLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        attackLeft.setViewport(new Rectangle2D(5 * 128, 0, 128, 128));
        walk.setViewport(new Rectangle2D(0, 0, 128, 128));
        walkLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        idle.setViewport(new Rectangle2D(0, 0, 128, 128));
        idleLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        block.setViewport(new Rectangle2D(0, 0, 128, 128));
        enemyIdle.setViewport(new Rectangle2D(0, 0, 128, 128));
        enemyAttack.setViewport(new Rectangle2D(3*512, 0, 128, 128));
        //Visibilities
        visibilityOff(attackLeft, attack, walk, walkLeft, idleLeft,block,death,deathLeft);
        visibilityOff(enemyAttack,enemyDead,enemyRun,enemyCharge,enemyFire);

        //Walk Animations
        walkAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (animationStarted && !mainCharacter.getBoundsInParent().intersects(magicBarrier.getBoundsInParent())) {
                orientation = "Right";
                walk.setTranslateX(walk.getTranslateX() + 10);
                mainCharacter.setTranslateX(walk.getTranslateX());
                walk.nextFrame();
            }
        }));
        walkLeftAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (animationStarted) {
                if (!mainCharacter.getBoundsInParent().intersects(hiddenBarrier.getBoundsInParent())) {
                    orientation = "Left";
                    walkLeft.setTranslateX(walkLeft.getTranslateX() - 10);
                    mainCharacter.setTranslateX(walkLeft.getTranslateX());
                    walkLeft.reverseFrame();
                }
            }
        }));
        walkAnimation.setCycleCount(Timeline.INDEFINITE);
        walkLeftAnimation.setCycleCount(Timeline.INDEFINITE);
        root.getChildren().addAll(background,gate,mainCharacter,enemy,enemyCharge, magicBarrier,hiddenBarrier, clickHere);
        attackCharaceter(primaryStage);
        walk(primaryStage);
        creatingHealthBar();
        enemyMove();
        showFinish(primaryStage);
        deathCharacter(primaryStage);

        primaryStage.setTitle("LIBERATION WARRIOR");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
    public static class Sprite extends ImageView {
        private final int frameCount;
        int currentFrame;
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
            if (lastFrame == 0) lastFrame = frameCount - 1;
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
    public void attackCharaceter(Stage primaryStage) {

        attackRightAnimation = new Timeline(
                new KeyFrame(Duration.millis(80), event -> {

                    if (animationStarted) {
                        attack.setTranslateX(mainCharacter.getTranslateX());
                        attack.nextFrame();

                        // Animate the attack by advancing to the next frame
                        if (enemy.isVisible()){
                            if (mainCharacter.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                                double damage = (double) 5;
                                enemyHP -= damage;
//                                enemy.setEffect(new Glow(30));
                                enemy.setEffect(new Glow(30));
                                updateEnemyHealthBar();
//                                enemyHurt();
                                System.out.println(enemyHP);
                                if (enemyHP < 0) {
                                    enemyCharge.setVisible(false);
                                }
                                if (enemyHP == 0){
                                    dead = true;
                                    death();
                                }
                            }
                        }
                    }
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
                                double damage = 1;
                                enemyHP -= damage;
                                enemy.setEffect(new Glow(30));
                                updateEnemyHealthBar();
                                System.out.println(enemyHP);

                                if (enemyHP <= 0 && !dead) {
                                    enemyCharge.setVisible(false);
                                }
                                if (enemyHP == 0){
                                    dead = true;
                                    death();
                                }
                            }
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
            enemy.setEffect(null);
            attackLeft.setViewport(new Rectangle2D(5 * 128, 0, 128, 128));
        });
        attackRightAnimation.setOnFinished(e->{
            enemy.setEffect(null);
        });
        onPressed(primaryStage);

    }
    public void walk(Stage primaryStage) {
        onPressed(primaryStage);
        scene.setOnKeyReleased(e -> {
            setX(mainCharacter.getTranslateX(), idle, idleLeft);
            if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                walkAnimation.stop();
                orientation = "Right";
                visibilityOff(idleLeft, walk, attackLeft,block);
                if (attack.isVisible() && attackRightAnimation.getStatus() == Animation.Status.RUNNING || walkLeftAnimation.getStatus() == Animation.Status.RUNNING) {
                    idle.setVisible(false);
                } else {
                    visibilityOff(walkLeft, attack);
                    idle.setVisible(true);
                }
                setX(mainCharacter.getTranslateX(), walkLeft, walk, attack,block);
            }
            else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                walkLeftAnimation.stop();
                orientation = "Left";

                visibilityOff(idle, walkLeft, attack,block);
                if (attackLeft.isVisible() && attackLeftAnimation.getStatus() == Animation.Status.RUNNING || walkAnimation.getStatus() == Animation.Status.RUNNING) {
                    idleLeft.setVisible(false);
                } else {
                    walkLeftAnimation.stop();
                    visibilityOff(walk, attackLeft);
                    idleLeft.setVisible(true);
                }
                setX(mainCharacter.getTranslateX(), walk, walkLeft, attackLeft,block);
            }
            else if (e.getCode() == KeyCode.SPACE){
                walkAnimation.stop();
                walkLeftAnimation.stop();
            } else if (e.getCode() == KeyCode.L) {
                blockAnimation.stop();
                visibilityOff(walkLeft, attack,attackLeft,block);
                blocking = false;
                System.out.println("relsssss"+blocking);
                if (orientation.equals("Right")) idle.setVisible(true);
                else idleLeft.setVisible(true);
            }
        });



    }
    public void onPressed(Stage primaryStage){
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                animationStarted = true;
                visibilityOff(idle, idleLeft, walk, walkLeft,block);
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
                if (walkLeftAnimation.getStatus() != Animation.Status.RUNNING ) {
                    if (blocking){
                        blockAnimation.stop();
                    }
                    orientation = "Right";
                    visibilityOff(idle, idleLeft, attack, attackLeft, walkLeft, block);
                    walk.setVisible(true);
                    setX(mainCharacter.getTranslateX(), walk, walkLeft,block);
                    animationStarted = true;
                    walkAnimation.play();
                    System.out.println();
                }
            } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                if (walkAnimation.getStatus() != Animation.Status.RUNNING) {
                    if (blocking){
                        blockAnimation.stop();
                    }
                    orientation = "Left";
                    visibilityOff(idle, idleLeft, attack, attackLeft, walk,block);
                    walkLeft.setVisible(true);
                    walk.setTranslateX(mainCharacter.getTranslateX());
                    walkLeft.setTranslateX(walk.getTranslateX());
                    animationStarted = true;
                    walkLeftAnimation.play();
                }
            } else if (event.getCode() == KeyCode.L) {
                if (walkAnimation.getStatus() != Animation.Status.RUNNING || walkLeftAnimation.getStatus() != Animation.Status.RUNNING ) {
                    visibilityOff(idle, idleLeft, attack, attackLeft, walk,walkLeft);
                    walkAnimation.stop();
                    walkLeftAnimation.stop();
                    block.setVisible(true);
                    animationStarted = true;
                    blocking=true;
                    block();
                }}
            else if (event.getCode() == KeyCode.ENTER && isGateOpened&&mainCharacter.getBoundsInParent().intersects(gate.getBoundsInParent())){
                System.out.println("Entered");
                levelOver(primaryStage);
            }

        });
    }
    public void enemyMove(){
        move = new Timeline(new KeyFrame(Duration.millis(1500), e -> {
            if (enemy.getTranslateX() == 830&&enemy.isVisible()&&!dead){
                magicAttack();
                charge();

            }
            else {
                if (!hit && enemy.getBoundsInParent().intersects(mainCharacter.getBoundsInParent()) &&enemy.isVisible()&&!dead) {
                    System.out.println("Entered");
                    visibilityOff(enemyDead,enemyRun,enemyIdle);
                    enemyAttack.setVisible(true);
                    enemyAttack();
                    System.out.println("Hit");
                    hit = true;
                    isBack = true;
                }
            }
        }));

        moveBack = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            if (hit && enemy.getTranslateX() != 830 &&enemy.isVisible()&&!dead) {
                FadeTransition ft = new FadeTransition(Duration.millis(1500) , enemy);
                ft.setFromValue(1);
                ft.setToValue(0);
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(400) , enemy);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                enemyAttackAnimation.setOnFinished(event->{
                    mainCharacter.setEffect(new Glow(0));
                    enemyAttack.setVisible(false);
                    enemyIdle.setVisible(true);
                    ft.play();
                    enemyAttack.currentFrame = 0;
                    enemyAttack.lastFrame = enemyAttack.frameCount;

                });
                ft.setOnFinished(event->{
                    enemy.setTranslateX(830);
                    fadeTransition.play();
                });
                fadeTransition.setOnFinished(event->{
                    move.play();
                    moveBack.play();
                });
            } else {
                hit = false;
                isBack = false;
            }
        }));
        move.setCycleCount(Animation.INDEFINITE);
        move.play();

        moveBack.setCycleCount(Animation.INDEFINITE);
        moveBack.play();
    }
    public void magicAttack(){
        enemyMagicAnimation = new Timeline(new KeyFrame(Duration.millis(75) , e->{
            visibilityOff(enemyAttack,enemyDead,enemyIdle,enemyRun);
            enemyFire.setVisible(true);
            enemyFire.reverseFrame();
        }));
        enemyMagicAnimation.setCycleCount(8);
        enemyMagicAnimation.play();
    }
    public void charge(){
        enemyChargeAnimation = new Timeline(new KeyFrame(Duration.millis(150) , e->{
            visibilityOff(enemyAttack,enemyDead,enemyIdle,enemyRun);
            enemyCharge.setVisible(true);
            enemyCharge.setTranslateX(enemyCharge.getTranslateX()-60);
            if (enemyCharge.getBoundsInParent().intersects(mainCharacter.getBoundsInParent())&&!blocking&&enemy.isVisible()){
                characterHP -= 12;
                if (characterHP <= 0){
                    deadCh = true;
                }
                updateCharacterHealthBar();
                mainCharacter.setEffect(new Glow(1));
                System.out.println(characterHP);
            }
            enemyCharge.reverseFrame();
        }));
        enemyChargeAnimation.setCycleCount(12);
        enemyChargeAnimation.setOnFinished(e-> enemyCharge.setVisible(false));
        enemyMagicAnimation.setOnFinished(e->{
            enemy.setTranslateX(enemy.getTranslateX() - 10);
            enemyFire.currentFrame = 0;
            enemyFire.lastFrame = enemyFire.frameCount;
            enemyCharge.setTranslateX(enemy.getTranslateX());
            enemyChargeAnimation.play();
        });
        enemyChargeAnimation.setOnFinished(e->{
            mainCharacter.setEffect(new Glow(0));
            enemyCharge.setTranslateX(enemy.getTranslateX());
            enemyCharge.setVisible(false);
            enemyCharge.currentFrame = 0;
            enemyCharge.lastFrame = enemyCharge.frameCount;
            enemyRun();
        });
    }
    public void enemyAttack(){
        enemyAttackAnimation = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            visibilityOff(enemyFire,enemyDead,enemyIdle,enemyRun);
            enemyAttack.setVisible(true);
            if (enemy.getBoundsInParent().intersects(mainCharacter.getBoundsInParent()) &&!blocking&&enemy.isVisible() &&!enemy.getBoundsInParent().intersects(magicBarrier.getBoundsInParent())){
                characterHP -= 3*3;
                if (characterHP <= 0){
                    deadCh = true;
                }
                updateCharacterHealthBar();
                mainCharacter.setEffect(new Glow(1));
                System.out.println(characterHP);
            }
            else {
                if (!blocking){
                    characterHP -= 18;
                    if (characterHP <= 0){
                        deadCh = true;
                    }
                    updateCharacterHealthBar();
                    mainCharacter.setEffect(new Glow(1));
                    System.out.println(characterHP);
                }
            }
            enemyAttack.reverseFrame();

        }));
        enemyAttackAnimation.setCycleCount(4);
        enemyAttackAnimation.play();

    }
    public void enemyRun(){
        enemyRunAnimation = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            visibilityOff(enemyAttack,enemyDead,enemyIdle,enemyFire);
            enemyRun.setVisible(true);
            if (!enemy.getBoundsInParent().intersects(mainCharacter.getBoundsInParent()) && !isBack) enemy.setTranslateX(enemy.getTranslateX() - 15);
            else{
                enemyRunAnimation.stop();
                enemyRun.setVisible(false);
                enemyIdle.setVisible(true);
            }

            enemyRun.reverseFrame();
        }));

        enemyRunAnimation.setCycleCount(Animation.INDEFINITE);
        enemyRunAnimation.play();
    }
    public void creatingHealthBar(){
        enemyHealthText = new Text(203, 440, " THE GUARDIAN OF THE DIMENSION ");
        enemyHealthText.setStyle("-fx-font:  17px 'Stencil'");
        enemyHealthText.setFill(Color.WHITE);
        root.getChildren().add(enemyHealthText);

        // Create text element for character health display


        // Create rectangle element for enemy health bar
        ImageView userFac = new ImageView("facer.png");
        userFac.setTranslateX(5);userFac.setTranslateY(6);
        userFac.setFitWidth(45);userFac.setFitHeight(45);
        enemyHealthBar = new Rectangle(205, 450, 600, 18);
        Rectangle cover = new Rectangle(205,450,600,20);
        cover.setFill(Color.BLACK);
        cover.setOpacity(0.6);
        cover.setStroke(Color.GOLD);
        cover.setStrokeWidth(2);
        enemyHealthBar.setFill(Color.DARKRED);
        root.getChildren().addAll(cover,enemyHealthBar,userFac);

        // Create rectangle element for character health bar
        characterHealthBar = new Rectangle(55, 29, 220, 18);
        characterHealthBar.setFill(Color.DARKGREEN);
        Rectangle coverCharacter = new Rectangle(55,28,220,20);
        coverCharacter.setFill(Color.BLACK);
        coverCharacter.setOpacity(0.6);
        coverCharacter.setStroke(Color.WHITE);
        coverCharacter.setStrokeWidth(2);

        root.getChildren().addAll(coverCharacter,characterHealthBar);
    }


    private void updateEnemyHealthBar() {
        enemyHealthBar.setWidth(150 * enemyHP / 25);
    }

    private void updateCharacterHealthBar() {
        characterHealthBar.setWidth(150 * characterHP / 100.0);
    }
    public void block(){
        blockAnimation = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            // blocking = true;
            block.nextFrame();
        }));
        blockAnimation.setCycleCount(Animation.INDEFINITE);
        blockAnimation.play();
    }
    public void death(){
        enemyDeath = new Timeline(new KeyFrame(Duration.millis(300) , e->{
//            enemyAttackAnimation.stop();
//            enemyChargeAnimation.stop();
//            enemyMagicAnimation.stop();
//            enemyRunAnimation.stop();
            if (dead) {
                ostFinish();
                visibilityOff(enemyAttack, enemyDead, enemyRun, enemyCharge, enemyIdle);
                enemyAttackAnimation.stop();
                enemyDead.setVisible(true);
                enemyDead.reverseFrame();
            }
        }));
        enemyDeath.setCycleCount(6);
        enemyDeath.play();
        enemyDeath.setOnFinished(e->{
            enemyDead.setViewport(new Rectangle2D(0,0,128,128));
            enemyDead.currentFrame = 0;
            enemyDead.lastFrame = enemyDead.frameCount;
        });
    }
    public void levelOver(Stage primaryStage){
        clickHere.setVisible(false);
        Text levelComplete = new Text("Level Complete");
        Rectangle cover = new Rectangle(750, 175, Color.BLACK);
        ImageView mainMenu = new ImageView(new Image("Main Menu.png"));
        ImageView character = new ImageView(new Image("Idle.png"));
        ImageView retry = new ImageView(new Image("Retry.png"));
        score+= 30;
        scoreText = new Text("Score: +"+score);
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
        mainMenu.setOnMouseEntered(e-> {
            Glow glow = new Glow(100);
            mainMenu.setEffect(glow);
        });
        mainMenu.setOnMouseExited(e-> {
            mainMenu.setEffect(null);
        });

        retry.setOnMouseEntered(e-> {
            Glow glow = new Glow(100);
            retry.setEffect(glow);
        });
        retry.setOnMouseExited(e-> {
            retry.setEffect(null);
        });

        //on clicking retry and main menu
        retry.setOnMouseClicked(e->{
            LV2 level2 = new LV2();
            level2.start(primaryStage);
        });

        mainMenu.setOnMouseClicked(event -> {
            int score = Integer.parseInt(loggedInPlayer[5].toString())+30;
            loggedInPlayer [5] = String.valueOf(score);
            GameIntro gameIntro = new GameIntro();
            gameIntro.ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/MainmenuOST.mp4/"));
            gameIntro.openLoggedInPlayerScreen(loggedInPlayer,loggedInPlayerIndex,primaryStage,true,true,loggedInPlayerScene);
        });
        scoreText.setFill(Color.WHITE);
        scoreText.setX(380);
        scoreText.setY(235);
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
        FadeTransition ft = new FadeTransition(Duration.millis(1500) , group);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
    public void showFinish(Stage primaryStage){
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            if (dead){
                enemyDeath.setOnFinished(event->{
                    enemyDead.setViewport(new Rectangle2D(0,0,128,128));
                    showGate();
                });
                showGate.setOnFinished(event->{
                    openGate();
                });
            }
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
    }
    public void deathCharacter(Stage primaryStage) {
        deathCharacterAnimation = new Timeline(
                new KeyFrame(Duration.millis(600), event -> {
                    death.nextFrame();

                })
        );
        deathCharacterAnimation.setCycleCount(death.frameCount);
        deathLeftCharacterAnimation = new Timeline(
                new KeyFrame(Duration.millis(600), event -> {
                    deathLeft.nextFrame();

                })
        );
        deathLeftCharacterAnimation.setCycleCount(death.frameCount);
        Timeline lose = new Timeline(new KeyFrame(Duration.millis(100),e->{
            if(deadCh){
                if( orientation.equals("Right")){
                    visibilityOff(idle,idleLeft,attack,attackLeft,walk,walkLeft);
                    death.setTranslateX(mainCharacter.getTranslateX());
                    death.setVisible(true);
                    deathCharacterAnimation.play();
                    attackRightAnimation.stop();
                    attackLeftAnimation.stop();
                    walkAnimation.stop();
                    walkLeftAnimation.stop();
                    deathCharacterAnimation.setOnFinished(event->{
                        mainCharacter.setVisible(false);
                    });
                }
                if( orientation.equals("Left")){
                    visibilityOff(idle,idleLeft,attack,attackLeft,walk,walkLeft);
                    deathLeft.setTranslateX(mainCharacter.getTranslateX());
                    deathLeft.setVisible(true);
                    deathLeftCharacterAnimation.play();
                    attackRightAnimation.stop();
                    attackLeftAnimation.stop();
                    walkAnimation.stop();
                    walkLeftAnimation.stop();
                    deathLeftCharacterAnimation.setOnFinished(event->{
                        mainCharacter.setVisible(false);
                    });

                }
                GameOver(deathCharacterAnimation,primaryStage);
                GameOver(deathLeftCharacterAnimation,primaryStage);
            }

        }));
        lose.setCycleCount(Animation.INDEFINITE);
        lose.play();
    }

    public void GameOver(Timeline Tl,Stage primaryStage) {
        Tl.setOnFinished(ev->{
            ostFinish();
            Text levelComplete = new Text("Level Failed");
            Rectangle cover = new Rectangle(750, 175, Color.BLACK);
            ImageView mainMenu = new ImageView(new Image("Main Menu.png"));
            ImageView character = new ImageView(new Image("Idle.png"));
            ImageView retry = new ImageView(new Image("Retry.png"));
            scoreText = new Text("Score: "+0);
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
            mainMenu.setOnMouseEntered(event-> {
                Glow glow = new Glow(100);
                mainMenu.setEffect(glow);
            });
            mainMenu.setOnMouseExited(event-> {
                mainMenu.setEffect(null);
            });
            //on clicking main menu
            mainMenu.setOnMouseClicked(event->{
                GameIntro gameIntro = new GameIntro();
                gameIntro.ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/MainmenuOST.mp4/"));
                gameIntro.openLoggedInPlayerScreen(loggedInPlayer,loggedInPlayerIndex,primaryStage,true,true,loggedInPlayerScene);
            });
            //on clicking retry
            retry.setOnMouseClicked(e->{
                LV2 level2 = new LV2();
                level2.start(primaryStage);
            });
            retry.setOnMouseEntered(event-> {
                Glow glow = new Glow(100);
                retry.setEffect(glow);
            });
            retry.setOnMouseExited(event-> {
                retry.setEffect(null);
            });
            scoreText.setFill(Color.WHITE);
            scoreText.setX(380);
            scoreText.setY(250);
            scoreText.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 40px;");
            levelComplete.setStyle("-fx-font-family: rainyhearts; -fx-font-size: 48px;");
            levelComplete.setFill(Color.RED);

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
            FadeTransition ft = new FadeTransition(Duration.millis(1500) , group);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        });
    }
    public void openGate(){
        gateAnimation = new Timeline(new KeyFrame(Duration.millis(250) , e->{
            gate.nextFrame();
            isGateOpened = true;
        }));
        gateAnimation.setOnFinished(e->{
            showText();
        });
        gateAnimation.setCycleCount(11);
        gateAnimation.play();
    }
    public void showGate(){
        gate.setVisible(true);
        showGate = new FadeTransition(Duration.millis(1000) , gate);
        showGate.setFromValue(0);
        showGate.setToValue(1);
        showGate.play();
    }
    public void showText(){
        showTextAnimation = new FadeTransition(Duration.millis(1500) , clickHere);
        showTextAnimation.setFromValue(0);
        showTextAnimation.setToValue(1);
        showTextAnimation.setCycleCount(Animation.INDEFINITE);
        showTextAnimation.setAutoReverse(true);
        showTextAnimation.play();
    }
    public void ostFinish(){
        ost.setVolume(1);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000) , e->{
            ost.setVolume(ost.getVolume()-0.07);
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
    }

}

