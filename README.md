# Liberation Warrior

### A 2D Action Combat Game built with JavaFX

![Java](https://img.shields.io/badge/Java-11%2B-orange?style=flat-square&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-17%2B-blue?style=flat-square)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen?style=flat-square)
![Exhibition](https://img.shields.io/badge/🏆%201st%20Place-Alfaisal%20Minigame%20Exhibition-gold?style=flat-square)

---

## About the Game

Liberation Warrior is a sprite-based 2D fighting game where a samurai warrior is banished to another dimension by a powerful wizard. Stripped of his world and his honor, the warrior must battle through unknown lands — fighting progressively harder enemies and environments — until he finally returns to face the wizard in an epic multi-phase showdown.

Built entirely in Java using JavaFX, the game features hand-animated sprite sheets, original soundtracks per level, a full player profile system, and both a story campaign and local multiplayer mode.

---

## Game Modes

### Story Mode — 3 Levels

| Level   | Arena        | Enemies                    | Highlight                                             |
| ------- | ------------ | -------------------------- | ----------------------------------------------------- |
| **LV1** | Ruined City  | Standard fighters          | Combat introduction, rounds & scoring system          |
| **LV2** | Fire Arena   | Faster enemies + Mini-Boss | Fireball projectiles, aggressive AI, mini-boss finale |
| **LV3** | Castle Arena | The Wizard (Main Boss)     | Multi-phase boss — Blade, Lightning & Fire magic      |

Each level has its own unique background, enemy behavior, and original soundtrack.

### 1v1 Versus Mode

Two players, one keyboard. Full combat system with health bars, blocking, and attacks. Great for settling scores with friends.

---

## Features

- 🎨 **Sprite-sheet animation engine** — idle, walk, run, attack, block, hurt, and death states for every character
- 🧙 **Multi-phase boss fight** — the wizard switches between Blade Magic, Lightning Magic, and Fire Magic as his HP drops
- 👤 **Player profile system** — register, log in, and track your score history across sessions
- 🎵 **Original soundtracks** — unique OST for the main menu and each of the 3 levels
- 📖 **Animated story intro** — typewriter-effect narrative screen before the game begins
- ❤️ **Health & score system** — real-time HP bars, round tracking, and live score display
- 🌆 **Animated environments** — GIF and parallax backgrounds per level

---

## Project Structure

```
LiberationWarrior/
├── Code/
│   ├── GameIntro.java           # Main menu, player login & registration, mode select
│   ├── intro_Description.java   # Typewriter-effect story intro screen
│   ├── LV1.java                 # Level 1 – Ruined City
│   ├── LV2.java                 # Level 2 – Fire Arena + Mini-Boss
│   ├── LV3.java                 # Level 3 – Castle + Final Boss Fight
│   ├── OneVsOne.java            # 1v1 Versus mode entry point
│   └── VS11.java                # Full 1v1 local multiplayer implementation
└── Resources/
    ├── Player sprites           # Idle, Walk, Run, Attack, Block, Jump, Dead
    ├── Enemy sprites            # Idle, Run, Attack, Hurt, Dead variants
    ├── Boss sprites             # Idle, Blade/Lightning/Fire Magic, Anger, Death
    ├── Backgrounds              # Per-level arenas + main menu environments
    ├── UI assets                # Buttons, HUD, icons, level maps, keyboard layouts
    └── Audio                   # Per-level OSTs + main menu soundtrack
```

---

## Controls

### Story Mode (Single Player)

| Action     | Key |
| ---------- | --- |
| Move Right | `→` |
| Move Left  | `←` |
| Jump       | `↑` |
| Attack     | `A` |
| Block      | `S` |

### Versus Mode — Player 1 & Player 2

| Action     | Player 1 | Player 2 |
| ---------- | -------- | -------- |
| Move Right | `→`      | `D`      |
| Move Left  | `←`      | `A`      |
| Jump       | `↑`      | `W`      |
| Attack     | `.`      | `F`      |
| Block      | `/`      | `G`      |

---

## Getting Started

### Prerequisites

- Java JDK 11 or higher
- JavaFX SDK 17+ — download from [gluonhq.com](https://gluonhq.com/products/javafx/)
- IntelliJ IDEA or any Java IDE with JavaFX support

### Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Ahmed-BinHelabi/LiberationWarrior.git
   ```

2. **Add JavaFX to your project:**
   - In IntelliJ: `File → Project Structure → Libraries → Add JavaFX SDK lib folder`

3. **Set VM options:**

   ```
   --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.media
   ```

4. **Update resource paths** in each `.java` file to point to your local `Resources/` folder

5. **Run `GameIntro.java`** as the main entry point

---

## Tech Stack

| Technology                | Usage                                               |
| ------------------------- | --------------------------------------------------- |
| **Java**                  | Core game logic, OOP structure                      |
| **JavaFX**                | Rendering, UI, input handling, media                |
| **Timeline / KeyFrame**   | Frame-by-frame sprite animation engine              |
| **MediaPlayer**           | In-game audio and per-level OST playback            |
| **Rectangle2D viewports** | Sprite sheet cycling for smooth character animation |
| **Pane / Group**          | Scene graph management for layered game elements    |

---

## Team

This game was built as a team project at Alfaisal University, College of Engineering.

| Name               |
| ------------------ |
| Ahmed Bin Halabi   |
| Abdulrahman Rashed |
| Mohammed Bawazir   |
| Mohammed Haytham   |
| Saad Alkeridis     |
| Zakariya BaAlawi   |
| Abdulrahman Hisham |

**Supervisor:** Eng. Hoda Elsayed

🏆 **1st Place** — Alfaisal University Annual Minigame Exhibition 2023

---

## Author

**Ahmed Bin Halabi**
Software Engineering Student — Alfaisal University
[LinkedIn](www.linkedin.com/in/ahmed-bin-halabi-a78127253)
[GitHub](https://github.com/Ahmed-BinHelabi)
