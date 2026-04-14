package com.example.demo20;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class OneVsOne extends Application {
    ImageView background = new ImageView("RuinedCityBackground.png");
    Character character;
    double characterHP=50;
    double enemyHP = 10,bossHP=100;

    //Scene
    Scene scene;

    //Images
    Image attackSheet, attackLeftSheet, walkSheet, idleSheet, idleReversed, walkLeftSheet, awakeSheet, enemyIdleSheet,enemyDeadSheet, enemyHurtSheet, enemyRunSheet, enemyAttackSheet;

    //Main character Sprites
    Sprite attack, attackLeft, walk, walkLeft, idle, idleLeft,block;

    //Enemy Sprites
    Sprite enemyIdle, enemyDead, enemyHurt, enemyRun, enemyAttack;

    //Boss Sprites
    Sprite bossIdle, bossBladeMagic, bossLightningMagic, bossFireMagic, bossAnger, bossDeath, lightning, fire, blade;

    //Animations
    Timeline attackRightAnimation,attackLeftAnimation,walkAnimation,walkLeftAnimation,blockAnimation;
    Timeline enemyAttackAnimation , enemyDeadAnimation, enemyHurtAnimation, enemyRunAnimation,enemyAnimations;
    Timeline bossIdleAnimation, bossBladeMagicAnimation, bossLightningMagicAnimation, bossFireMagicAnimation, bossAngerAnimation, bossDeathAnimation, bossAnimation, BladeMagicAnimation, LightningMagicAnimation, FireMagicAnimation,magicAnimation;
    boolean dead;
    Pane root;
    Group mainCharacter, enemy,boss,magic;
    Text counterText, round1Message,bossHealthText,characterHealthText;
    boolean phase2, phase3,phase4,phase5;
    Rectangle bossHealthBar,characterHealthBar;
    Timeline timer;
    String orientation = "Right";
    String enemyOrientation = "Left";
    boolean awakeFinished = false;
    static boolean timerStopped;
    int round = 1;
    int attackCounter;
    int magicX;
    boolean bossDead;
    boolean blocking;

    public OneVsOne(ImageView background, Stage primaryStage) {
        this.background = background;
        start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }



    private boolean animationStarted = false;

    double character2HP =50;
//Attribute for character2

    //Scene


    //Images
    Image attack2Sheet, attackLeft2Sheet, walk2Sheet, idle2Sheet, idle2Reversed, walkLeft2Sheet, awake2Sheet;

    //Main character Sprites
    Sprite attack2, attackLeft2, walk2, walkLeft2, idle2, idleLeft2, block2;
    //Enemy Sprites
    //Animations
    Timeline attackRight2Animation, attackLeft2Animation, walk2Animation, walkLeft2Animation, block2Animation;

    Group mainCharacter2;
    Text  character2HealthText;

    Rectangle  character2HealthBar;

    private boolean animation2Started = false;
    String orientation2 = "Right";



    boolean blocking2;
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        scene = new Scene(root, 1000, 500);
        // character = new Character(50, 350);
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
        mainCharacter = new Group(attack,attackLeft,walk,idle,idleLeft,walkLeft,block);

        mainCharacter.setTranslateX(50);
        mainCharacter.setTranslateY(250);
        attack.setViewport(new Rectangle2D(0, 0, 128, 128));
        attackLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        walk.setViewport(new Rectangle2D(0, 0, 128, 128));
        walkLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        idle.setViewport(new Rectangle2D(0, 0, 128, 128));
        idleLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        block.setViewport(new Rectangle2D(0, 0, 128, 128));

        visibilityOff(attackLeft, attack, walk, walkLeft, idleLeft,block);

        root.getChildren().addAll(background,mainCharacter);

        attackCharaceter();
        creatingHealthBar();
        walk();
        block();
        //Character2
        // Sprite Sheets
        attack2Sheet = new Image("Attack2_2.png");
        attackLeft2Sheet = new Image("AttackLeft2_2.png");
        walk2Sheet = new Image("Walk2.png");
        idle2Sheet = new Image("Idle2.png");
        idle2Reversed = new Image("IdleLeft2.png");
        walkLeft2Sheet = new Image("WalkLeft2.png");
        awake2Sheet = new Image("Awake.png");
        //Sprite objects
        attack2 = new Sprite(attack2Sheet, 5);
        attackLeft2 = new Sprite(attackLeft2Sheet, 5);
        walk2 = new Sprite(walk2Sheet, 9);
        idle2 = new Sprite(idle2Sheet, 6);
        idleLeft2 = new Sprite(idle2Reversed, 6);
        walkLeft2 = new Sprite(walkLeft2Sheet, 9);
        block2 = new Sprite(new Image("ProtectionLeft2.png") , 1);
        mainCharacter2 = new Group(attack2, attackLeft2, walk2, idle2, idleLeft2, walkLeft2, block2);

        mainCharacter2.setTranslateX(350);
        mainCharacter2.setTranslateY(250);
        attack2.setViewport(new Rectangle2D(0, 0, 128, 128));
        attackLeft2.setViewport(new Rectangle2D(4 * 128, 0, 128, 128));
        walk2.setViewport(new Rectangle2D(0, 0, 128, 128));
        walkLeft2.setViewport(new Rectangle2D(0, 0, 128, 128));
        idle2.setViewport(new Rectangle2D(0, 0, 128, 128));
        idleLeft2.setViewport(new Rectangle2D(0, 0, 128, 128));
        block2.setViewport(new Rectangle2D(0, 0, 128, 128));

        visibilityOff(attackLeft2, attack2, walk2, walkLeft2, idleLeft2, block2);

        //back button
        ImageView backButton = new ImageView("back.png");
        backButton.setFitWidth(50);
        backButton.setFitHeight(50);

        backButton.setOnMouseEntered( event ->{
            Glow glow = new Glow(100);
            backButton.setEffect(glow);
        });
        backButton.setOnMouseExited( event ->{
            backButton.setEffect(null);
        });

        backButton.setOnMouseClicked(e->{
            GameIntro gameIntro = new GameIntro();
            try {
                gameIntro.open1Versus1Screen(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        root.getChildren().addAll(mainCharacter2,backButton);

        attackCharaceter2();
        creatingHealthBar2();
        walk2();
        block2();
        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
        primaryStage.setTitle("Sprite Animation with KeyEvent Example");
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
        public void nextFrameDeath(){
            currentFrame = (currentFrame + 1) % frameCount;
            int frameWidth = (int) image.getWidth() / frameCount;
            if (currentFrame == 0){
                currentFrame = frameCount-1;
            }
//            System.out.println("Curr frame "+ currentFrame);
            setViewport(new Rectangle2D(currentFrame * frameWidth, 0, frameWidth, getImage().getHeight()));
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

    public void attackCharaceter() {

        attackRightAnimation = new Timeline(
                new KeyFrame(Duration.millis(80), event -> {

                    if (animationStarted) {
                        attack.setTranslateX(mainCharacter.getTranslateX());
                        attack.nextFrame();

                        // Animate the attack by advancing to the next frame
                        if (mainCharacter2.isVisible()){
                            if (mainCharacter.getBoundsInParent().intersects(mainCharacter2.getBoundsInParent()) && !blocking2 ) {
                                double damage = (double) 6/6;
                                character2HP -= damage;
                                updateCharacter2HealthText();
                                updateCharacter2HealthBar();
//                                enemy.setEffect(new Glow(30));
                                mainCharacter2.setEffect(new Glow(30));

//                                enemyHurt();
                                System.out.println(enemyHP);

//                                if (enemyHP <= 0) {
//                                    enemy.setVisible(false);
//                                    dead = true;
//                                    if(characterHP<=50&&characterHP>=0) {
//                                        characterHP += 1;
//                                        updateCharacterHealthText();
//                                        updateCharacterHealthBar();
//                                    }
//
//
//                                }
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

                        if (mainCharacter2.isVisible()){
                            if (mainCharacter.getBoundsInParent().intersects(mainCharacter2.getBoundsInParent()) && !blocking2 ) {
                                double damage = (double) 6/6;
                                character2HP -= damage;
                                updateCharacter2HealthText();
                                updateCharacter2HealthBar();
//                                enemy.setEffect(new Glow(30));
                                mainCharacter2.setEffect(new Glow(30));

//                                enemyHurt();
                                System.out.println(enemyHP);

//                                if (enemyHP <= 0) {
//                                    enemy.setVisible(false);
//                                    dead = true;
//                                    if(characterHP<=50&&characterHP>=0) {
//                                        characterHP += 1;
//                                        updateCharacterHealthText();
//                                        updateCharacterHealthBar();
//                                    }
//
//
//                                }
                            }
                        }
                    }
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

            mainCharacter2.setEffect(new Glow(0));
            attackLeft.setViewport(new Rectangle2D(5 * 128, 0, 128, 128));
        });
        attackRightAnimation.setOnFinished(e->{

            mainCharacter2.setEffect(new Glow(0));

        });

        onPressed();





    }
    public void walk() {
        walkAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (animationStarted) {
                orientation = "Right";
                walk.setTranslateX(walk.getTranslateX() + 10);
                mainCharacter.setTranslateX(walk.getTranslateX());
                walk.nextFrame();
            }
        }));
        walkLeftAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (animationStarted) {
                orientation = "Left";
                walkLeft.setTranslateX(walkLeft.getTranslateX() - 10);
                mainCharacter.setTranslateX(walkLeft.getTranslateX());
                walkLeft.reverseFrame();
            }
        }));
        walkAnimation.setCycleCount(Timeline.INDEFINITE);
        walkLeftAnimation.setCycleCount(Timeline.INDEFINITE);
        onPressed();
        onReleased();


    }
    public void onReleased(){
        scene.setOnKeyReleased(e -> {
            setX(mainCharacter.getTranslateX(), idle, idleLeft);
            setX(mainCharacter2.getTranslateX(), idle2, idleLeft2);
            if (e.getCode() == KeyCode.RIGHT ) {
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
            else if (e.getCode() == KeyCode.LEFT ) {
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
            else if (e.getCode() == KeyCode.ENTER){
                walkAnimation.stop();
                walkLeftAnimation.stop();
            } else if (e.getCode() == KeyCode.L) {
                blockAnimation.stop();
                block.setVisible(false);

                blocking = false;
                System.out.println("relsssss"+blocking);
                if (orientation.equals("Right")) idle.setVisible(true);
                else idleLeft.setVisible(true);
            }

            else if (e.getCode() == KeyCode.D ) {
                walk2Animation.stop();
                orientation2 = "Right";
                visibilityOff(idleLeft2, walk2, attackLeft2, block2);
                if (attack2.isVisible() && attackRight2Animation.getStatus() == Animation.Status.RUNNING || walkLeft2Animation.getStatus() == Animation.Status.RUNNING) {
                    idle2.setVisible(false);
                } else {
                    visibilityOff(walkLeft2, attack2);
                    idle2.setVisible(true);
                }
                setX(mainCharacter2.getTranslateX(), walkLeft2, walk2, attack2, block2);
            }
            else if (e.getCode() == KeyCode.A) {
                walkLeft2Animation.stop();
                orientation2 = "Left";

                visibilityOff(idle2, walkLeft2, attack2, block2);
                if (attackLeft2.isVisible() && attackLeft2Animation.getStatus() == Animation.Status.RUNNING || walk2Animation.getStatus() == Animation.Status.RUNNING) {
                    idleLeft2.setVisible(false);
                } else {
                    walkLeft2Animation.stop();
                    visibilityOff(walk2, attackLeft2);
                    idleLeft2.setVisible(true);
                }
                setX(mainCharacter2.getTranslateX(), walk2, walkLeft2, attackLeft2, block2);
            }
            else if (e.getCode() == KeyCode.SPACE){
                walk2Animation.stop();
                walkLeft2Animation.stop();
            } else if (e.getCode() == KeyCode.V) {
                block2Animation.stop();
                block2.setVisible(false);

                blocking2 = false;
                // System.out.println("relsssss"+ blocking2);
                if (orientation2.equals("Right")) idle2.setVisible(true);
                else idleLeft2.setVisible(true);
            }
        });

    }
    public void onPressed(){
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
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
            else if (event.getCode() == KeyCode.RIGHT ) {
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
            }
            else if (event.getCode() == KeyCode.LEFT ) {
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
            }
            else if (event.getCode() == KeyCode.L) {
                if (walkAnimation.getStatus() != Animation.Status.RUNNING || walkLeftAnimation.getStatus() != Animation.Status.RUNNING ) {
                    visibilityOff(idle, idleLeft, attack, attackLeft, walk,walkLeft);
                    walkAnimation.stop();
                    walkLeftAnimation.stop();
                    block.setVisible(true);
                    animationStarted = true;
                    blocking=true;
                    block();
                }}
            else if (event.getCode() == KeyCode.SPACE) {
                animation2Started = true;
                visibilityOff(idle2, idleLeft2, walk2, walkLeft2, block2);
                if (orientation2.equals("Right")) {
                    attackLeft2.setVisible(false);
                    attack2.setVisible(true);
                    attack2.setTranslateX(mainCharacter2.getTranslateX());
                    attackRight2Animation.play();
                } else if (orientation2.equals("Left")) {
                    attack2.setVisible(false);
                    attackLeft2.setVisible(true);
                    attackLeft2.setTranslateX(mainCharacter2.getTranslateX());
                    attackLeft2Animation.play();
                }
            }
            else if (event.getCode() == KeyCode.D ) {
                if (walkLeft2Animation.getStatus() != Animation.Status.RUNNING ) {
                    if (blocking2){
                        block2Animation.stop();
                    }
                    orientation2 = "Right";
                    visibilityOff(idle2, idleLeft2, attack2, attackLeft2, walkLeft2, block2);
                    walk2.setVisible(true);
                    setX(mainCharacter2.getTranslateX(), walk2, walkLeft2, block2);
                    animation2Started = true;
                    walk2Animation.play();
                    System.out.println();
                }
            } else if (event.getCode() == KeyCode.A) {
                if (walk2Animation.getStatus() != Animation.Status.RUNNING) {
                    if (blocking2){
                        block2Animation.stop();
                    }
                    orientation2 = "Left";
                    visibilityOff(idle2, idleLeft2, attack2, attackLeft2, walk2, block2);
                    walkLeft2.setVisible(true);
                    walk2.setTranslateX(mainCharacter2.getTranslateX());
                    walkLeft2.setTranslateX(walk2.getTranslateX());
                    animation2Started = true;
                    walkLeft2Animation.play();
                }
            }
            else if (event.getCode() == KeyCode.V) {
                if (walk2Animation.getStatus() != Animation.Status.RUNNING || walkLeft2Animation.getStatus() != Animation.Status.RUNNING ) {
                    visibilityOff(idle2, idleLeft2, attack2, attackLeft2, walk2, walkLeft2);
                    walk2Animation.stop();
                    walkLeft2Animation.stop();
                    block2.setVisible(true);
                    animation2Started = true;
                    blocking2 =true;
                    block2();
                }
            }
        });
    }


    public void block(){
        blockAnimation = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            // blocking = true;
            block.nextFrame();
        }));
        blockAnimation.setCycleCount(Animation.INDEFINITE);
        blockAnimation.play();
    }
    public void creatingHealthBar(){


        // Create text element for character health display
        characterHealthText = new Text(50, 20, "Character HP: " + characterHP);
        root.getChildren().add(characterHealthText);

        // Create rectangle element for enemy health bar


        // Create rectangle element for character health bar
        characterHealthBar = new Rectangle(50, 30, 150, 20);
        characterHealthBar.setFill(Color.RED);
        root.getChildren().add(characterHealthBar);
    }
    private void updateCharacterHealthText() {
        characterHealthText.setText("Character HP: " + characterHP);
    }


    private void updateCharacterHealthBar() {
        characterHealthBar.setWidth(150 * characterHP / 100.0);
    }
//Method for character2

    public void attackCharaceter2() {

        attackRight2Animation = new Timeline(
                new KeyFrame(Duration.millis(80), event -> {

                    attack2.setTranslateX(mainCharacter2.getTranslateX());
                    attack2.nextFrame();

                    // Animate the attack by advancing to the next frame
                    if (mainCharacter.isVisible()){
                        if (mainCharacter2.getBoundsInParent().intersects(mainCharacter.getBoundsInParent()) && !blocking ) {
                            double damage = (double) 6/6;
                            characterHP -= damage;
                            updateCharacterHealthText();
                            updateCharacterHealthBar();
//                                enemy.setEffect(new Glow(30));
                            mainCharacter.setEffect(new Glow(30));

//                                enemyHurt();
                            //System.out.println(enemyHP);

//                                if (enemyHP <= 0) {
//                                    enemy.setVisible(false);
//                                    dead = true;
//                                    if(characterHP<=50&&characterHP>=0) {
//                                        characterHP += 1;
//                                        updateCharacterHealthText();
//                                        updateCharacterHealthBar();
//                                    }
//
//
//                                }
                        }
                    }
                })
        );

        attackLeft2Animation = new Timeline(
                new KeyFrame(Duration.millis(75), event -> {
                    attackLeft2.setTranslateX(mainCharacter2.getTranslateX());
                    attackLeft2.reverseFrame();
                    if (mainCharacter.isVisible()){
                        if (mainCharacter2.getBoundsInParent().intersects(mainCharacter.getBoundsInParent()) && !blocking ) {
                            double damage = (double) 6 / 6;
                            characterHP -= damage;
                            updateCharacterHealthText();
                            updateCharacterHealthBar();
//                                enemy.setEffect(new Glow(30));
                            mainCharacter.setEffect(new Glow(30));

//                                enemyHurt();
                            //System.out.println(enemyHP);

//                                if (enemyHP <= 0) {
//                                    enemy.setVisible(false);
//                                    dead = true;
//                                    if(characterHP<=50&&characterHP>=0) {
//                                        characterHP += 1;
//                                        updateCharacterHealthText();
//                                        updateCharacterHealthBar();
//                                    }
//
//
//                                }
                        }
                    }
                })
        );

        attackRight2Animation.setCycleCount(attack2.frameCount);
        attackLeft2Animation.setCycleCount(attackLeft2.frameCount);


        attackLeft2Animation.setOnFinished(e -> {
            try {
                Thread.sleep(45);
            } catch (InterruptedException exs) {
                throw new RuntimeException(exs);
            }
            mainCharacter.setEffect(new Glow(0));
            attackLeft2.setViewport(new Rectangle2D(0, 0, 128, 128));
        });
        attackRight2Animation.setOnFinished(e->{
            mainCharacter.setEffect(new Glow(0));

        });
        onPressed();





    }
    public void walk2() {
        walk2Animation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            orientation2 = "Right";
            walk2.setTranslateX(walk2.getTranslateX() + 10);
            mainCharacter2.setTranslateX(walk2.getTranslateX());
            walk2.nextFrame();
        }));
        walkLeft2Animation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (animation2Started) {
                orientation2 = "Left";
                walkLeft2.setTranslateX(walkLeft2.getTranslateX() - 10);
                mainCharacter2.setTranslateX(walkLeft2.getTranslateX());
                walkLeft2.reverseFrame();
            }
        }));
        walk2Animation.setCycleCount(Timeline.INDEFINITE);
        walkLeft2Animation.setCycleCount(Timeline.INDEFINITE);
        onPressed();
        onReleased();




    }


    public void block2(){
        block2Animation = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            // blocking = true;
            block2.nextFrame();
        }));
        block2Animation.setCycleCount(Animation.INDEFINITE);
        block2Animation.play();
    }
    public void creatingHealthBar2(){


        // Create text element for character health display
        character2HealthText = new Text(650, 20, "Character HP: " + character2HP);
        root.getChildren().add(character2HealthText);

        // Create rectangle element for enemy health bar


        // Create rectangle element for character health bar
        character2HealthBar = new Rectangle(650, 30, 150, 20);
        character2HealthBar.setFill(Color.RED);
        root.getChildren().add(character2HealthBar);
    }
    private void updateCharacter2HealthText() {
        character2HealthText.setText("Character HP: " + character2HP);
    }


    private void updateCharacter2HealthBar() {
        character2HealthBar.setWidth(150 * character2HP / 100.0);
    }
}
