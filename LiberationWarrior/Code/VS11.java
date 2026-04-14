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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class VS11 extends Application {
    Character character;
    double characterHP=150;
    double character2HP=150;
    double enemyHP = 10,bossHP=100;

    //Scene
    Scene scene;
    Rectangle hiddenBarrier1,hiddenBarrier2;
    //Images
    Image attackSheet, attackLeftSheet, walkSheet, idleSheet, idleReversed, walkLeftSheet, awakeSheet, enemyIdleSheet,enemyDeadSheet, enemyHurtSheet, enemyRunSheet, enemyAttackSheet;

    //Main character Sprites
    Sprite attack, attackLeft, walk, walkLeft, idle, idleLeft,block,blockLeft;
    //Enemy Sprites
    Sprite enemyIdle, enemyDead, enemyHurt, enemyRun, enemyAttack;
    //Boss Sprites
    Sprite bossIdle, bossBladeMagic, bossLightningMagic, bossFireMagic, bossAnger, bossDeath, lightning, fire, blade;
    //Animations
    Timeline attackRightAnimation,attackLeftAnimation,walkAnimation,walkLeftAnimation,blockAnimation;

    Timeline enemyAttackAnimation , enemyDeadAnimation, enemyHurtAnimation, enemyRunAnimation,enemyAnimations;

    Timeline bossIdleAnimation, bossBladeMagicAnimation, bossLightningMagicAnimation, bossFireMagicAnimation, bossAngerAnimation, bossDeathAnimation, bossAnimation, BladeMagicAnimation, LightningMagicAnimation, FireMagicAnimation,magicAnimation;

    boolean dead;
    Text scoreText;
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



    private boolean animationStarted = false;

//Attribute for character2

    //Scene


    //Images
    Image attack2Sheet, attackLeft2Sheet, walk2Sheet, idle2Sheet, idle2Reversed, walkLeft2Sheet, awake2Sheet;

    //Main character Sprites
    Sprite attack2, attackLeft2, walk2, walkLeft2, idle2, idleLeft2, block2Left,block2;
    //Enemy Sprites
    //Animations
    Timeline attackRight2Animation, attackLeft2Animation, walk2Animation, walkLeft2Animation, block2Animation;




    Group mainCharacter2;
    Text  character2HealthText;

    Rectangle  character2HealthBar;


    private boolean animation2Started = false;
    String orientation2 = "Right";

    boolean blocking2;
    ImageView background = new ImageView("RuinedCityBackground.png");

    public VS11(ImageView background, Stage primaryStage) {
        this.background = background;
        start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        scene = new Scene(root, 1000, 500);
        // character = new Character(50, 350);
        //background = new ImageView("RuinedCityBackground.png");
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
        blockLeft = new Sprite(new Image("Block Left.png") , 1);
        mainCharacter = new Group(attack,attackLeft,walk,idle,idleLeft,walkLeft,block,blockLeft);

        mainCharacter.setTranslateX(50);
        mainCharacter.setTranslateY(200);
        attack.setViewport(new Rectangle2D(0, 0, 128, 128));
        attackLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        walk.setViewport(new Rectangle2D(0, 0, 128, 128));
        walkLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        idle.setViewport(new Rectangle2D(0, 0, 128, 128));
        idleLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        block.setViewport(new Rectangle2D(0, 0, 128, 128));
        blockLeft.setViewport(new Rectangle2D(0, 0, 128, 128));

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

        visibilityOff(attackLeft, attack, walk, walkLeft, idleLeft,block,blockLeft);

        root.getChildren().addAll(background,mainCharacter,hiddenBarrier1,hiddenBarrier2);

        attackCharaceter();
        creatingHealthBar();
        creatingHealthBar2();
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
        block2Left = new Sprite(new Image("ProtectionLeft2.png") , 1);
        block2 = new Sprite(new Image("Protection2.png") , 1);


        mainCharacter2 = new Group(attack2, attackLeft2, walk2, idle2, idleLeft2, walkLeft2,block2, block2Left);

        mainCharacter2.setTranslateX(350);
        mainCharacter2.setTranslateY(200);
        attack2.setViewport(new Rectangle2D(0, 0, 128, 128));
        attackLeft2.setViewport(new Rectangle2D(4 * 128, 0, 128, 128));
        walk2.setViewport(new Rectangle2D(0, 0, 128, 128));
        walkLeft2.setViewport(new Rectangle2D(0, 0, 128, 128));
        idle2.setViewport(new Rectangle2D(0, 0, 128, 128));
        idleLeft2.setViewport(new Rectangle2D(0, 0, 128, 128));
        block2Left.setViewport(new Rectangle2D(0, 0, 128, 128));
        block2.setViewport(new Rectangle2D(0, 0, 128, 128));

        visibilityOff(attackLeft2, attack2, walk2, walkLeft2, idleLeft2, block2Left,block2);
        setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);
        setX(mainCharacter.getTranslateX(), idle, idleLeft,attack,attackLeft,walk,walkLeft,block,blockLeft);

        root.getChildren().addAll(mainCharacter2);

      attackCharaceter2();
        creatingHealthBar2();
       walk2();
        block2();
        checkWinner(primaryStage);
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

                                updateCharacter2HealthBar();
//                                enemy.setEffect(new Glow(30));
                                mainCharacter2.setEffect(new Glow(30));





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

                                updateCharacter2HealthBar();
//                                enemy.setEffect(new Glow(30));
                                mainCharacter2.setEffect(new Glow(30));

//                                enemyHurt();
                                System.out.println(enemyHP);


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
            if (!mainCharacter.getBoundsInParent().intersects(hiddenBarrier2.getBoundsInParent()))  {
                orientation = "Right";
                walk.setTranslateX(walk.getTranslateX() + 10);
                mainCharacter.setTranslateX(walk.getTranslateX());
                walk.nextFrame();
            }
        }));
        walkLeftAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (!mainCharacter.getBoundsInParent().intersects(hiddenBarrier1.getBoundsInParent())) {
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
            setX(mainCharacter.getTranslateX(), idle, idleLeft,attack,attackLeft,walk,walkLeft,block,blockLeft);
            setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);
            if (e.getCode() == KeyCode.RIGHT ) {
                walkAnimation.stop();
                orientation = "Right";
                visibilityOff(idleLeft, walk, attackLeft,block,blockLeft);
                if (attack.isVisible() && attackRightAnimation.getStatus() == Animation.Status.RUNNING || walkLeftAnimation.getStatus() == Animation.Status.RUNNING) {
                    idle.setVisible(false);
                }
                else {
                    visibilityOff(walkLeft, attack,attackLeft,block,blockLeft);
                    idle.setVisible(true);
                }
                setX(mainCharacter.getTranslateX(), idle, idleLeft,attack,attackLeft,walk,walkLeft,block,blockLeft);

            }
            else if (e.getCode() == KeyCode.LEFT ) {
                walkLeftAnimation.stop();
                orientation = "Left";

                visibilityOff(idle, walkLeft, attack,block,blockLeft);
                if (attackLeft.isVisible() && attackLeftAnimation.getStatus() == Animation.Status.RUNNING || walkAnimation.getStatus() == Animation.Status.RUNNING) {
                    idleLeft.setVisible(false);
                }
                else {
                    walkLeftAnimation.stop();
                    visibilityOff(walk, attackLeft,block,blockLeft);
                    idleLeft.setVisible(true);
                }
                setX(mainCharacter.getTranslateX(), idle, idleLeft,attack,attackLeft,walk,walkLeft,block,blockLeft);
            }
            else if (e.getCode() == KeyCode.ENTER){
                walkAnimation.stop();
                walkLeftAnimation.stop();
            }
            else if (e.getCode() == KeyCode.L) {
                blockAnimation.stop();
                block.setVisible(false);
                blockLeft.setVisible(false);
                attack.setVisible(false);
                attackLeft.setVisible(false);

                blocking = false;

                if (orientation.equals("Right")) idle.setVisible(true);
                else idleLeft.setVisible(true);
            }

            if (e.getCode() == KeyCode.D ) {
                walk2Animation.stop();
                orientation2 = "Right";
                visibilityOff(idleLeft2, walk2, attackLeft2,block2,block2Left);
                setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);
                if (attack2.isVisible() && attackRight2Animation.getStatus() == Animation.Status.RUNNING || walkLeft2Animation.getStatus() == Animation.Status.RUNNING) {
                    idle2.setVisible(false);
                }
                else {
                    visibilityOff(walkLeft2, attack2,attackLeft2,block2,block2Left);
                    idle2.setVisible(true);
                }

            }
            else if (e.getCode() == KeyCode.A ) {
                walkLeft2Animation.stop();
                orientation2 = "Left";
                setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);

                visibilityOff(idle2, walkLeft2, attack2,block2,block2Left);
                if (attackLeft2.isVisible() && attackLeft2Animation.getStatus() == Animation.Status.RUNNING || walk2Animation.getStatus() == Animation.Status.RUNNING) {
                    idleLeft2.setVisible(false);
                }
                else {
                    walkLeft2Animation.stop();
                    visibilityOff(walk2, attackLeft2,block2,block2Left);
                    idleLeft2.setVisible(true);
                }
            }
            else if (e.getCode() == KeyCode.C){
                walk2Animation.stop();
                walkLeft2Animation.stop();
                setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);

            }
            else if (e.getCode() == KeyCode.V) {
                block2Animation.stop();
                block2.setVisible(false);
                block2Left.setVisible(false);
                attack2.setVisible(false);
                attackLeft2.setVisible(false);
                setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);


                blocking2 = false;
                if (orientation2.equals("Right")) idle2.setVisible(true);
                else idleLeft2.setVisible(true);
            }
        });

    }
    public void onPressed(){
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                animationStarted = true;
                visibilityOff(idle, idleLeft, walk, walkLeft,block,blockLeft);
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
                    visibilityOff(idle, idleLeft, attack, attackLeft, walkLeft, block,blockLeft);
                    walk.setVisible(true);
                    setX(mainCharacter.getTranslateX(), idle, idleLeft,attack,attackLeft,walk,walkLeft,block,blockLeft);
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
                    visibilityOff(idle, idleLeft, attack, attackLeft, walk,block,blockLeft);
                    walkLeft.setVisible(true);
                    setX(mainCharacter.getTranslateX(), idle, idleLeft,attack,attackLeft,walk,walkLeft,block,blockLeft);
                    animationStarted = true;
                    walkLeftAnimation.play();
                }
            }
            else if (event.getCode() == KeyCode.L) {
                if (walkAnimation.getStatus() != Animation.Status.RUNNING || walkLeftAnimation.getStatus() != Animation.Status.RUNNING  ) {
                    visibilityOff(idle, idleLeft, attack, attackLeft, walk,walkLeft);
                    walkAnimation.stop();
                    walkLeftAnimation.stop();
                    setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);

                    if(orientation.equals("Right")){ block.setVisible(true);blockLeft.setVisible(false);}
                    if(orientation.equals("Left")) {blockLeft.setVisible(true);block.setVisible(false);}
                    animationStarted = true;
                    blocking=true;
                    block();
                }
            }
            else if (event.getCode() == KeyCode.C) {
                animation2Started = true;
                visibilityOff(idle2, idleLeft2, walk2, walkLeft2,block2,block2Left);
                if (orientation2.equals("Right")) {
                    attackLeft2.setVisible(false);
                    attack2.setVisible(true);
                    setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);
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
                    visibilityOff(idle2, idleLeft2, attack2, attackLeft2, walkLeft2, block2,block2Left);
                    walk2.setVisible(true);
                    setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);
                    animation2Started = true;
                    walk2Animation.play();
                    System.out.println(

                    );

                }
            }
            else if (event.getCode() == KeyCode.A ) {
                if (walk2Animation.getStatus() != Animation.Status.RUNNING) {
                    if (blocking2){
                        block2Animation.stop();
                    }
                    orientation2 = "Left";
                    visibilityOff(idle2, idleLeft2, attack2, attackLeft2, walk2,block2,block2Left);
                    walkLeft2.setVisible(true);
                    setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);
                    animation2Started = true;
                    walkLeft2Animation.play();
                }
            }
            else if (event.getCode() == KeyCode.V) {
                if (walk2Animation.getStatus() != Animation.Status.RUNNING || walkLeft2Animation.getStatus() != Animation.Status.RUNNING  ) {
                    visibilityOff(idle2, idleLeft2, attack2, attackLeft2, walk2,walkLeft2);
                    setX(mainCharacter2.getTranslateX(), idle2, idleLeft2,attack2,attackLeft2,walk2,walkLeft2,block2,block2Left);

                    walk2Animation.stop();
                    walkLeft2Animation.stop();
                    if(orientation2.equals("Right")){ block2.setVisible(true);block2Left.setVisible(false);}
                    if(orientation2.equals("Left")) {block2Left.setVisible(true);block2.setVisible(false);}
                    animationStarted = true;
                    blocking2=true;
                    block2();
                }
            }
        });
    }

    public void block2(){
        block2Animation = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            // blocking = true;
            if(orientation2.equals("Left")) block2Left.nextFrame();
            if(orientation2.equals("Right"))block2.nextFrame();
        }));
        block2Animation.setCycleCount(Animation.INDEFINITE);
        block2Animation.play();
    }
    public void block(){
        blockAnimation = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            if(orientation.equals("Left")) block.nextFrame();
            if(orientation.equals("Right"))blockLeft.nextFrame();
        }));
        blockAnimation.setCycleCount(Animation.INDEFINITE);
        blockAnimation.play();

    }
    public void creatingHealthBar(){
        characterHealthText = new Text(55, 65, " Player One ");
        characterHealthText.setStroke(Color.WHITE);
        characterHealthText.setStrokeWidth(1.5);
        characterHealthText.setStyle("-fx-font:  17px 'Stencil'");
        root.getChildren().add(characterHealthText);

        // Create text element for character health display


        // Create rectangle element for enemy health bar
        ImageView userFac = new ImageView("Facepx.png");
        userFac.setTranslateX(5);userFac.setTranslateY(6);
        userFac.setFitWidth(45);userFac.setFitHeight(45);


        // Create rectangle element for character health bar
        characterHealthBar = new Rectangle(55, 29, 220, 18);
        characterHealthBar.setFill(Color.DARKRED);
        Rectangle coverCharacter = new Rectangle(55,28,220,20);
        coverCharacter.setFill(Color.BLACK);
        coverCharacter.setOpacity(0.6);
        coverCharacter.setStroke(Color.WHITE);
        coverCharacter.setStrokeWidth(2);

        root.getChildren().addAll(coverCharacter,characterHealthBar,userFac);
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

                            updateCharacterHealthBar();
//                                enemy.setEffect(new Glow(30));
                            mainCharacter.setEffect(new Glow(30));


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

                            updateCharacterHealthBar();
//                                enemy.setEffect(new Glow(30));
                            mainCharacter.setEffect(new Glow(30));

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
            if(!mainCharacter2.getBoundsInParent().intersects(hiddenBarrier2.getBoundsInParent())) {
                orientation2 = "Right";
                walk2.setTranslateX(walk2.getTranslateX() + 10);
                mainCharacter2.setTranslateX(walk2.getTranslateX());
                walk2.nextFrame();
            }
        }));
        walkLeft2Animation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (!mainCharacter2.getBoundsInParent().intersects(hiddenBarrier1.getBoundsInParent())) {
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



    public void creatingHealthBar2(){
        character2HealthText = new Text(750, 65, " Player Two ");
        character2HealthText.setStroke(Color.WHITE);
        character2HealthText.setStrokeWidth(1.5);
        character2HealthText.setStyle("-fx-font:  17px 'Stencil'");
        root.getChildren().add(character2HealthText);

        // Create text element for character health display


        // Create rectangle element for enemy health bar
        ImageView user2Fac = new ImageView("user2pix.png");
        user2Fac.setTranslateX(700);user2Fac.setTranslateY(6);
        user2Fac.setFitWidth(45);user2Fac.setFitHeight(45);


        // Create rectangle element for character health bar
        character2HealthBar = new Rectangle(750, 29, 220, 18);
        character2HealthBar.setFill(Color.STEELBLUE);
        Rectangle cover2Character = new Rectangle(750,28,220,20);
        cover2Character.setFill(Color.BLACK);
        cover2Character.setOpacity(0.6);
        cover2Character.setStroke(Color.WHITE);
        cover2Character.setStrokeWidth(2);

        root.getChildren().addAll(cover2Character,character2HealthBar,user2Fac);
    }

    private void updateCharacter2HealthBar() {
        character2HealthBar.setWidth(150 * character2HP / 100.0);
    }
public void checkWinner(Stage primaryStage){
        Timeline tt= new Timeline(new KeyFrame(Duration.millis(100),e->{
            if(characterHP<=0){
                Text levelComplete = new Text("Player Two Win");
                Rectangle cover = new Rectangle(750, 175, Color.BLACK);
                ImageView mainMenu = new ImageView(new Image("Main Menu.png"));
                ImageView character = new ImageView(new Image("IdleLeft2aa.png"));

                Group group = new Group();
                group.getChildren().addAll(cover, levelComplete, character, mainMenu);
                root.getChildren().add(group);
                mainCharacter.setVisible(false);

                mainMenu.setFitWidth(195);
                mainMenu.setFitHeight(56);
                mainMenu.setTranslateY(250);
                mainMenu.setTranslateX(400);

                mainMenu.setOnMouseEntered(event-> {
                    Glow glow = new Glow(100);
                    mainMenu.setEffect(glow);
                });
                mainMenu.setOnMouseExited(event-> {
                    mainMenu.setEffect(null);
                });

                //ON Clicking main menu
                mainMenu.setOnMouseClicked(event -> {
                    GameIntro gameIntro = new GameIntro();
                    try {
                        gameIntro.open1Versus1Screen(primaryStage);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });

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
            else if(character2HP<=0){
                Text levelComplete = new Text("Player One Win");
                Rectangle cover = new Rectangle(750, 175, Color.BLACK);
                ImageView mainMenu = new ImageView(new Image("Main Menu.png"));
                ImageView character = new ImageView(new Image("Idleaa.png"));

                Group group = new Group();
                group.getChildren().addAll(cover, levelComplete, character, mainMenu);
                root.getChildren().add(group);
                mainCharacter2.setVisible(false);

                mainMenu.setFitWidth(195);
                mainMenu.setFitHeight(56);
                mainMenu.setTranslateY(250);
                mainMenu.setTranslateX(400);
                mainMenu.setOnMouseEntered(event-> {
                    Glow glow = new Glow(100);
                    mainMenu.setEffect(glow);
                });
                mainMenu.setOnMouseExited(event-> {
                    mainMenu.setEffect(null);
                });

                //ON Clicking main menu
                mainMenu.setOnMouseClicked(event -> {
                    GameIntro gameIntro = new GameIntro();
                    try {
                        gameIntro.open1Versus1Screen(primaryStage);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
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
        }));
        tt.setCycleCount(Animation.INDEFINITE);
        tt.play();
}
}

