package com.example.demo20;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Random;

public class LV3 extends Application {
    MediaPlayer ost;
    double characterHP=150;
    double enemyHP = 10,bossHP=200;

    //Scene
    Scene scene;

    //Images
    Image attackSheet, attackLeftSheet, walkSheet, idleSheet, idleReversed, walkLeftSheet, awakeSheet, enemyIdleSheet,enemyDeadSheet, enemyHurtSheet, enemyRunSheet, enemyAttackSheet;

    //Main character Sprites
    Sprite attack, attackLeft, walk, walkLeft, idle, idleLeft,block,death,deathLeft;
    //Enemy Sprites
    Sprite enemyIdle, enemyDead, enemyHurt, enemyRun, enemyAttack;
    //Boss Sprites
    Sprite bossIdle, bossBladeMagic, bossLightningMagic, bossFireMagic, bossAnger, bossDeath, lightning, fire, blade;
    //Animations
    Timeline attackRightAnimation,attackLeftAnimation,walkAnimation,walkLeftAnimation,blockAnimation,deathCharacterAnimation,deathLeftCharacterAnimation;

    Timeline enemyAttackAnimation , enemyDeadAnimation, enemyHurtAnimation, enemyRunAnimation,enemyAnimations;

    Timeline bossIdleAnimation, bossBladeMagicAnimation, bossLightningMagicAnimation, bossFireMagicAnimation, bossAngerAnimation, bossDeathAnimation, bossAnimation, BladeMagicAnimation, LightningMagicAnimation, FireMagicAnimation,magicAnimation;

    boolean dead,deadCh;
    Pane root;
    Group mainCharacter, enemy,boss,magic;
    Text counterText, round1Message,bossHealthText,characterHealthText;
    boolean phase2, phase3,phase4,phase5,phas6;
    Rectangle bossHealthBar,characterHealthBar;
    Timeline timer;

    private boolean animationStarted = false;
    String orientation = "Right";
    String enemyOrientation = "Left";
    boolean awakeFinished = false;
    static boolean timerStopped;
    int round = 1;
    int attackCounter;
    int magicX;
    boolean bossDead;
    boolean blocking;
    Text scoreText;
    private int loggedInPlayerIndex;
    private Object[] loggedInPlayer;
    Scene loggedInPlayerScene;

    public LV3(Stage primaryStage, Object [] loggedInPlayer, int index, Scene scene) throws InterruptedException {
        this.loggedInPlayer = loggedInPlayer;
        this.loggedInPlayerIndex = index;
        this.loggedInPlayerScene = scene;
        start(primaryStage);
    }

    public LV3() {
    }

    Rectangle hiddenBarrier1,hiddenBarrier2;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        root = new Pane();
        scene = new Scene(root, 1000, 500);
        ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/LV3OST.mp4/"));
        ost.setCycleCount(MediaPlayer.INDEFINITE);
        ost.play();
        // character = new Character(50, 350);
        ImageView background = new ImageView("Castle-2D-Battle-Backgrounds5.PNG");
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
        death =new Sprite(new Image("death.png"),3);
        deathLeft =new Sprite(new Image("deathLeft.png"),3);
        block = new Sprite(new Image("Block.png") , 1);
        mainCharacter = new Group(attack,attackLeft,walk,idle,idleLeft,walkLeft,block,death,deathLeft);
        death.setTranslateX(mainCharacter.getTranslateX()
        );

        //Enemy Sprite
        enemyIdle = new Sprite(enemyIdleSheet,1);
        enemyAttack = new Sprite(enemyAttackSheet, 7);
        enemyHurt = new Sprite(enemyHurtSheet, 2);
        enemyDead = new Sprite(enemyDeadSheet, 2);
        enemyRun = new Sprite(enemyRunSheet, 9);
        enemy = new Group(enemyIdle ,enemyAttack , enemyHurt , enemyDead , enemyRun);

        //Boss Sprites
        bossAnger = new Sprite(new Image("Boss Anger.png") , 5);
        bossDeath = new Sprite(new Image("Boss Death.png") , 6);
        bossIdle = new Sprite(new Image("Boss Idle.png") , 3);
        bossBladeMagic = new Sprite(new Image("Blade Attack.png") , 5);
        bossLightningMagic = new Sprite(new Image("Lightning Attack.png") , 5);
        bossFireMagic = new Sprite(new Image("Fire Attack.png") , 5);
        blade = new Sprite(new Image("Blue magic Attack.png") , 7);
        lightning = new Sprite(new Image("Purple Magic Attack.png") , 9);
        fire = new Sprite(new Image("Orange Magic Attack.png") , 10);

        //Boss Group
        boss = new Group(bossAnger,bossDeath,bossIdle,bossBladeMagic,bossLightningMagic,bossFireMagic);
        //Configuring the character
        mainCharacter.setTranslateX(50);
        mainCharacter.setTranslateY(150);
        //Configuring the boss
        boss.setTranslateX(700);
        boss.setTranslateY(0);
        magic = new Group(blade,lightning,fire);
        magicX = 500;
        magic.setTranslateX(magicX);
        magic.setTranslateY(0);
        enemy.setTranslateY(150);
        enemy.setTranslateX(350);
        //View Port
        attack.setViewport(new Rectangle2D(0, 0, 128, 128));
        attackLeft.setViewport(new Rectangle2D(5 * 128, 0, 128, 128));
        walk.setViewport(new Rectangle2D(0, 0, 128, 128));
        walkLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        idle.setViewport(new Rectangle2D(0, 0, 128, 128));
        idleLeft.setViewport(new Rectangle2D(0, 0, 128, 128));
        block.setViewport(new Rectangle2D(0, 0, 128, 128));
        enemyIdle.setViewport(new Rectangle2D(0, 0, 128, 128));
        bossIdle.setViewport(new Rectangle2D(0, 0, 406, 406));
        bossBladeMagic.setViewport(new Rectangle2D(0, 0, 406, 406));
        bossLightningMagic.setViewport(new Rectangle2D(0, 0, 406, 406));
        bossFireMagic.setViewport(new Rectangle2D(0, 0, 406, 406));
        blade.setViewport(new Rectangle2D(0, 0, 406, 406));
        lightning.setViewport(new Rectangle2D(0, 0, 406, 406));
        fire.setViewport(new Rectangle2D(0, 0, 406, 406));
        bossAnger.setViewport(new Rectangle2D(0, 0, 406, 406));
        bossDeath.setViewport(new Rectangle2D(0, 0, 406, 406));
        death.setViewport(new Rectangle2D(0, 0, 128, 128));
        deathLeft.setViewport(new Rectangle2D(0, 0, 128, 128));

        //Visibilities
        visibilityOff(attackLeft, attack, walk, walkLeft, idleLeft,block,death,deathLeft);
        visibilityOff(enemyAttack,enemyDead,enemyHurt,enemyRun,enemyIdle);
        visibilityOff(bossDeath,bossAnger,bossBladeMagic,bossLightningMagic,bossFireMagic);
        visibilityOff(fire,lightning,blade);
        //Timelines for animations


        hiddenBarrier1 = new Rectangle(25,500);
        hiddenBarrier1.setFill(Color.GAINSBORO);
        hiddenBarrier1.setTranslateX(-40);
        hiddenBarrier1.setOpacity(0.3);
        hiddenBarrier1.setVisible(false);
        hiddenBarrier2 = new Rectangle(90,500);
        hiddenBarrier2.setFill(Color.GAINSBORO);
        hiddenBarrier2.setTranslateX(910);
        hiddenBarrier2.setOpacity(0.3);
        hiddenBarrier2.setVisible(false);

        // Add this block in your start() method



        // Add this block in your start() method
        root.getChildren().addAll(background,mainCharacter , boss,magic,enemy,hiddenBarrier1,hiddenBarrier2);

        // Initialize boss animations
        enemyRun();
        enemyAttack();
        enemyRespawn();
        initializeBossAnimations();

        Death(primaryStage);
        attackCharaceter();
        creatingHealthBar();
        walk();
        bossAttack();
        deathCharacter(primaryStage);
        primaryStage.setScene(scene);

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
            if (lastFrame == 0) lastFrame = frameCount - 1;
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
                        if (enemy.isVisible()){
                            if (mainCharacter.getBoundsInParent().intersects(enemy.getBoundsInParent()) ) {
                                double damage = (double) 6/6;
                                enemyHP -= damage;
//                                enemy.setEffect(new Glow(30));
                                enemy.setEffect(new Glow(30));

//                                enemyHurt();
                                System.out.println(enemyHP);

                                if (enemyHP <= 0) {
                                    enemy.setVisible(false);
                                    dead = true;
                                    if(characterHP<=150) {
                                        characterHP += 5;

                                        updateCharacterHealthBar();
                                    }


                                }
                            }
                        }
                        if (boss.isVisible()){
                            if (mainCharacter.getBoundsInParent().intersects(boss.getBoundsInParent()) &&bossAngerAnimation.getStatus() != Animation.Status.RUNNING ) {
                                double damage = (double) 1;
                                bossHP -= damage;
                                if (bossHP == 160) phase2 = true;
                                else if (bossHP == 110 ) {phase3 = true;}
                                else if (bossHP == 75){phase4 = true;}
                                else if (bossHP == 40){phase5 = true;}
                                else if(bossHP==0) {phas6=true;bossDead=true;}
                                //if()
                                updateBossHealthBar();

//                                enemy.setEffect(new Glow(30));
                                boss.setEffect(new Glow(30));

//                                enemyHurt();
                                System.out.println(bossHP);

                                if (enemyHP <= 0) {
                                    enemy.setVisible(false);
                                    dead = true;


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
                                double damage = (double) 6/6;
                                enemyHP -= damage;
                                enemy.setEffect(new Glow(30));
                                System.out.println(enemyHP);

                                if (enemyHP <= 0) {
                                    enemy.setVisible(false);
                                    dead=true;

                                    if(characterHP<=150) {
                                        characterHP += 6;


                                        updateCharacterHealthBar();
                                    }
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
            boss.setEffect(null);
            attackLeft.setViewport(new Rectangle2D(5 * 128, 0, 128, 128));
        });
        attackRightAnimation.setOnFinished(e->{
            enemy.setEffect(null);
            boss.setEffect(null);

        });
        onPressed();





    }

    public void walk() {
        walkAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (animationStarted) {
                if (!mainCharacter.getBoundsInParent().intersects(hiddenBarrier2.getBoundsInParent())) {
                    orientation = "Right";
                    walk.setTranslateX(walk.getTranslateX() + 10);
                    mainCharacter.setTranslateX(walk.getTranslateX());
                    walk.nextFrame();
                }
            }
        }));
        walkLeftAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (animationStarted) {
                if (!mainCharacter.getBoundsInParent().intersects(hiddenBarrier1.getBoundsInParent())) {
                    orientation = "Left";
                    walkLeft.setTranslateX(walkLeft.getTranslateX() - 10);
                    mainCharacter.setTranslateX(walkLeft.getTranslateX());
                    walkLeft.reverseFrame();
                }
            }
        }));
        walkAnimation.setCycleCount(Timeline.INDEFINITE);
        walkLeftAnimation.setCycleCount(Timeline.INDEFINITE);
        onPressed();
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
                block.setVisible(false);
                visibilityOff(walkLeft,walk,attackLeft,attack);

                blocking = false;
                if (orientation.equals("Right")) idle.setVisible(true);
                else idleLeft.setVisible(true);
            }
        });



    }
    public void onPressed(){
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
        });
    }



    public void enemyRun(){
        enemyRunAnimation = new Timeline(new KeyFrame(Duration.millis(100) , e->{
            visibilityOff(enemyIdle,enemyHurt,enemyAttack,enemyDead);
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
                visibilityOff(enemyIdle, enemyHurt, enemyRun, enemyDead);
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
                int result = r.nextInt(-25,900) ;
                enemy.setTranslateX(result);
            }
        }));
        eventFiring.setCycleCount(Animation.INDEFINITE);
        eventFiring.play();
    }

    public void initializeBossAnimations() {
        bossIdleAnimation = createBossAnimation(bossIdle, 3, 100);
        bossBladeMagicAnimation = createBossAnimation(bossBladeMagic, 5, 100);
        bossLightningMagicAnimation = createBossAnimation(bossLightningMagic, 5, 100);
        bossFireMagicAnimation = createBossAnimation(bossFireMagic, 5, 100);
        bossAngerAnimation = createBossAnimation(bossAnger, 5, 100);
        bossDeathAnimation = createBossAnimation(bossDeath, 6, 500);
        BladeMagicAnimation = createMagicAnimation(blade, 7, 100);
        LightningMagicAnimation = createMagicAnimation(lightning, 9, 100);
        FireMagicAnimation = createMagicAnimation(fire, 10, 100);

//        bossAnimation.play();

    }

    public Timeline createBossAnimation(Sprite sprite, int frameCount, int durationMillis) {
        bossAnimation = new Timeline(
                new KeyFrame(Duration.millis(durationMillis), event -> {
                    if (frameCount == 6){
                        sprite.nextFrameDeath();
                    }
                    else sprite.nextFrame();

                })
        );
        bossAnimation.setCycleCount(frameCount);
        return bossAnimation;
    }

    public Timeline createMagicAnimation(Sprite sprite, int frameCount, int durationMillis) {
        magicAnimation = new Timeline(
                new KeyFrame(Duration.millis(durationMillis), event -> {

                    // Customize the animation logic based on the specific sprite
                    // For example, you can translate the sprite or change its frame
                    magic.setTranslateX(magic.getTranslateX() -30);
                    sprite.nextFrame();

                })
        );
        magicAnimation.setCycleCount(frameCount);
        return magicAnimation;
    }
    public void bossAttack(){
        Timeline attackRandomizer = new Timeline(new KeyFrame(Duration.seconds(2),event->{

            Random random = new Random();
            attackCounter= random.nextInt(3);
            if (!bossDead) {

                if (attackCounter == 0) {
                    bossFireMagic.setVisible(true);
                    fire.setVisible(true);
                    visibilityOff(bossLightningMagic, bossBladeMagic, bossIdle, bossAnger, bossDeath, lightning, blade);
                    bossFireMagicAnimation.play();
                    bossLightningMagicAnimation.stop();
                    bossBladeMagicAnimation.stop();
                    BladeMagicAnimation.stop();
                    FireMagicAnimation.play();
                    LightningMagicAnimation.stop();
                    System.out.println("HP: " + bossHP);
                    bossFireMagicAnimation.setOnFinished(e -> {
                        magicAnimation.play();
                        visibilityOff(bossLightningMagic, bossBladeMagic, bossFireMagic, bossAnger, bossDeath, lightning, blade);
                        bossIdle.setVisible(true);
                    });
                    FireMagicAnimation.setOnFinished(e -> {
                        magic.setTranslateX(magicX);
                        visibilityOff(lightning, blade, fire);
                        //bossIdle.setVisible(true);
                    });

                }
                if (attackCounter == 1) {
                    bossLightningMagic.setVisible(true);
                    lightning.setVisible(true);
                    visibilityOff(bossFireMagic, bossBladeMagic, bossIdle, bossAnger, bossDeath, fire, blade);
                    bossFireMagicAnimation.stop();
                    bossLightningMagicAnimation.play();
                    bossBladeMagicAnimation.stop();
                    BladeMagicAnimation.stop();
                    FireMagicAnimation.stop();
                    LightningMagicAnimation.play();
                    bossLightningMagicAnimation.setOnFinished(e -> {
                        visibilityOff(bossLightningMagic, bossBladeMagic, bossFireMagic, bossAnger, bossDeath, fire, blade);
                        bossIdle.setVisible(true);
                    });
                    LightningMagicAnimation.setOnFinished(e -> {
                        magic.setTranslateX(magicX);
                        visibilityOff(fire, blade, lightning);
                        //bossIdle.setVisible(true);
                    });
                }
                if (attackCounter == 2) {
                    bossBladeMagic.setVisible(true);
                    blade.setVisible(true);
                    visibilityOff(bossLightningMagic, bossFireMagic, bossIdle, bossAnger, bossDeath, fire, lightning);
                    bossFireMagicAnimation.stop();
                    bossLightningMagicAnimation.stop();
                    bossBladeMagicAnimation.play();
                    BladeMagicAnimation.play();
                    FireMagicAnimation.stop();
                    LightningMagicAnimation.stop();
                    bossBladeMagicAnimation.setOnFinished(e -> {
                        visibilityOff(bossLightningMagic, bossBladeMagic, bossFireMagic, bossAnger, bossDeath, lightning, fire);
                        bossIdle.setVisible(true);
                    });
                    BladeMagicAnimation.setOnFinished(e -> {
                        magic.setTranslateX(magicX);
                        visibilityOff(fire, blade, blade);
                        //bossIdle.setVisible(true);
                    });
                }


                if (phase2) {
                    System.out.println("Switch Phase");
                    visibilityOff(bossBladeMagic, bossLightningMagic, bossFireMagic, bossIdle, bossAnger, bossDeath, fire, lightning, blade);
                    bossAnger.setVisible(true);
                    bossAngerAnimation.play();


                }
                if (phase3) {
                    System.out.println("Switch Phase");
                    visibilityOff(bossBladeMagic, bossLightningMagic, bossFireMagic, bossIdle, bossAnger, bossDeath, fire, lightning, blade);
                    bossAnger.setVisible(true);
                    bossAngerAnimation.play();


                }
                if (phase4) {
                    System.out.println("Switch Phase");
                    visibilityOff(bossBladeMagic, bossLightningMagic, bossFireMagic, bossIdle, bossAnger, bossDeath, fire, lightning, blade);
                    bossAnger.setVisible(true);
                    bossAngerAnimation.play();


                }
                if (phase5) {
                    System.out.println("Switch Phase");
                    visibilityOff(bossBladeMagic, bossLightningMagic, bossFireMagic, bossIdle, bossAnger, bossDeath, fire, lightning, blade);
                    bossAnger.setVisible(true);
                    bossAngerAnimation.play();


                }

            }
            if (magic.getBoundsInParent().intersects(mainCharacter.getBoundsInParent())) {

                System.out.println("the block "+blocking);
                if (!blocking) {


                    characterHP = characterHP - ((double) 30);
                    updateCharacterHealthBar();
                    if(characterHP<=0){deadCh=true;}
                }
            }
            bossAngerAnimation.setOnFinished(e->{
                phase2 = false;phase3=false;phase4=false;phase5=false;
                Timeline pushTL = new Timeline(new KeyFrame(Duration.millis(30), ev->{
                    if (mainCharacter.getTranslateX() >= 200 ) {
                        mainCharacter.setTranslateX(mainCharacter.getTranslateX() - 30);
                    }
                    enemyRunAnimation.setCycleCount(Animation.INDEFINITE);
                    enemyRunAnimation.play();
                    enemyAttackAnimation.setCycleCount(Animation.INDEFINITE);
                    enemyAttackAnimation.play();

                }));

                pushTL.setCycleCount(20);
                pushTL.play();
            });

        }));


        attackRandomizer.setCycleCount(Animation.INDEFINITE);
        attackRandomizer.play();

    }
    public void creatingHealthBar(){
        bossHealthText = new Text(230, 395, " THE BLACK BLADE ");
        bossHealthText.setStyle("-fx-font:  17px 'Stencil'");
        root.getChildren().add(bossHealthText);

        // Create text element for character health display


        // Create rectangle element for enemy health bar
        ImageView userFac = new ImageView("facer.png");
        userFac.setTranslateX(5);userFac.setTranslateY(6);
        userFac.setFitWidth(45);userFac.setFitHeight(45);
        bossHealthBar = new Rectangle(230, 401, 600, 18);
        Rectangle cover = new Rectangle(230,400,600,20);
        cover.setFill(Color.BLACK);
        cover.setOpacity(0.6);
        cover.setStroke(Color.GOLD);
        cover.setStrokeWidth(2);
        bossHealthBar.setFill(Color.DARKRED);
        root.getChildren().addAll(cover,bossHealthBar,userFac);

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




    private void updateBossHealthBar() {
        bossHealthBar.setWidth(150 * bossHP / 50.0);
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
    public void Death(Stage primaryStage){
        Timeline win= new Timeline(new KeyFrame(Duration.millis(100),e->{
            if(bossDead) {
                ost.stop();
                visibilityOff(bossBladeMagic, bossLightningMagic, bossFireMagic, bossIdle, bossAnger, fire, lightning, blade);
                bossDeath.setVisible(true);
                bossDeathAnimation.play();
                enemyAttackAnimation.stop();
                enemyRunAnimation.stop();

                bossDeathAnimation.setOnFinished(eve->{
                    boss.setVisible(false);
                    enemy.setVisible(false);
                    Text levelComplete = new Text("Level Complete");
                    Rectangle cover = new Rectangle(750, 175, Color.BLACK);
                    ImageView mainMenu = new ImageView(new Image("Main Menu.png"));
                    ImageView character = new ImageView(new Image("Idle.png"));
                    ImageView retry = new ImageView(new Image("Retry.png"));
                    scoreText = new Text("Score: +"+100);
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

                    retry.setOnMouseEntered(event-> {
                        Glow glow = new Glow(100);
                        retry.setEffect(glow);
                    });
                    retry.setOnMouseExited(event-> {
                        retry.setEffect(null);
                    });

                    //on clicking main menu
                    mainMenu.setOnMouseClicked(event->{
                        int score = Integer.parseInt(loggedInPlayer[5].toString())+100;
                        loggedInPlayer [5] = String.valueOf(score);
                        GameIntro gameIntro = new GameIntro();
                        gameIntro.ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/MainmenuOST.mp4/"));
                        gameIntro.openLoggedInPlayerScreen(loggedInPlayer,loggedInPlayerIndex,primaryStage,true,true,loggedInPlayerScene);
                    });


                    //on clicking retry
                    retry.setOnMouseClicked(event -> {
                        LV3 lv3 = new LV3();
                        try {
                            lv3.start(primaryStage);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    scoreText.setFill(Color.WHITE);
                    scoreText.setX(380);
                    scoreText.setY(250);
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
                });
            }

        }));
        win.setCycleCount(Animation.INDEFINITE);
        win.play();

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
        Timeline lose= new Timeline(new KeyFrame(Duration.millis(100),e->{


            if(deadCh){
                ost.stop();
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
                GameOver(deathCharacterAnimation, primaryStage);
                GameOver(deathLeftCharacterAnimation, primaryStage);
            }

        }));
        lose.setCycleCount(Animation.INDEFINITE);
        lose.play();


    }

    public void GameOver(Timeline Tl , Stage primaryStage) {
        Tl.setOnFinished(ev->{
            Text levelComplete = new Text("You Died");
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
            retry.setOnMouseEntered(event-> {
                Glow glow = new Glow(100);
                retry.setEffect(glow);
            });
            retry.setOnMouseClicked(event -> {
                LV3 lv3 = new LV3();
                try {
                    lv3.start(primaryStage);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            retry.setOnMouseExited(event-> {
                retry.setEffect(null);
            });

            //on clicking main menu
            mainMenu.setOnMouseClicked(event->{
                int score = Integer.parseInt(loggedInPlayer[5].toString());
                loggedInPlayer [5] = String.valueOf(score);
                GameIntro gameIntro = new GameIntro();
                gameIntro.ost = new MediaPlayer(new Media("file:///C:/Users/mbawa/Downloads/MainmenuOST.mp4/"));
                gameIntro.openLoggedInPlayerScreen(loggedInPlayer,loggedInPlayerIndex,primaryStage,true,false,loggedInPlayerScene);
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
}