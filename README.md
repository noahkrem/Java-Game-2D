# Bird's Eye — 2D Java Adventure Game

A compact 2D tile-based action-adventure game written in Java using the standard Java 2D API and Swing. It demonstrates classic game-dev systems: a fixed-timestep game loop, tile map rendering, sprite animation, collision detection, entities (NPCs/monsters/objects), HUD (health), simple audio, and input handling.

This project is an excellent resume piece to show fundamentals of game architecture, OOP design, and graphics programming in Java.

## Highlights

- Tile-based world with sprite animation and layered drawing order.
- Player controller with sprinting, interaction (keys, doors, torches), and dialog with NPCs.
- Entity/OBJ/NPC/Monster system with collision detection and simple AI hooks.
- Audio support for background music and sound effects (Sound class).
- Clean separation of responsibilities: `GamePanel` (game loop & render), `TileManager`, `CollisionChecker`, `AssetSetter`, `UI`, and entity classes.

## Project structure (important files)

- `src/main/Main.java` — entry point that creates the game window and starts the loop.
- `src/main/GamePanel.java` — main game loop, render/update orchestration, entity drawing order.
- `src/main/KeyHandler.java` — keyboard input mapping and game state controls.
- `src/main/UI.java` — HUD, title screen, dialogue rendering.
- `src/main/CollisionChecker.java` — tile, object and entity collision logic.
- `src/main/AssetSetter.java` — places objects, NPCs, and monsters in the world.
- `src/entity/Player.java` — player-specific logic, sprite handling, interactions.
- `res/` — images, tiles, sprites, audio and maps.

## Controls

- Movement: W (up), A (left), S (down), D (right)
- Sprint: Hold SHIFT
- Interact / Confirm: ENTER
- Pause / Unpause: P
- Title menu navigation: W / S and ENTER
- Debug draw-time toggle: T

## How to build & run

Prerequisites

- Java Development Kit (JDK) 11 or newer installed and available on PATH. On Windows, you can install OpenJDK or Oracle JDK. On WSL/Ubuntu use `sudo apt install openjdk-17-jdk`.
- Gradle is not required locally because the project includes Gradle build files; the CI uses the Gradle wrapper. If you prefer to run Gradle locally, install Gradle or use the wrapper.

Build & run (Gradle)

1. From the repository root, build the project using the Gradle wrapper (recommended):

```powershell
# Windows PowerShell (uses Gradle wrapper)
./gradlew build
```

Or on WSL/Bash:

```bash
./gradlew build
```

2. Run the produced JAR (from repo root):

```powershell
# Windows PowerShell
java -jar build\libs\BirdsEye-0.1.0.jar
```

Or on WSL/Bash:

```bash
java -jar build/libs/BirdsEye-0.1.0.jar
```

CI / GitHub Actions

This repository includes a GitHub Actions workflow that runs on push and pull requests to `main`/`master`. The workflow builds with Gradle and uploads the produced JAR as an artifact named `BirdsEye-jar`.

Tips

- If resource files (images, maps, fonts) fail to load when running from the JAR, confirm your code reads them via classpath (e.g., `getResourceAsStream`) and that the `res/` folder is included inside the JAR under `res/` (the Gradle jar task copies `res/` into the JAR).
- If you prefer an IDE workflow, import the project as a Gradle project in IntelliJ IDEA or Eclipse and run `main.Main` or the produced JAR.

## Running from an IDE (IntelliJ / Eclipse)

- Open the project folder.
- Mark `src` as the sources root (if the IDE doesn't detect it automatically).
- Configure project SDK to a matching JDK (8+).
- Run `main.Main` as a Java application.

## Where to add screenshots & GIFs

To make your README visually impressive, add screenshots and short gameplay GIFs. Recommended structure:

- `res/screenshots/` — PNG or JPG images for static screenshots.
- `res/gifs/` — short looping GIFs showing gameplay or mechanics.

Add images and reference them in this README with relative paths:

![Title screen](res/screenshots/title-screen.png)

Or embed a GIF:

![Gameplay demo](res/gifs/gameplay-demo.gif)

Include captions and short notes (what's being shown, controls used).

## Suggested README screenshots to capture

- Title screen with menu
- A combat/monster encounter
- Picking up a key and opening a door
- HUD showing health and inventory
- A short 3–6 second GIF of walking animation + interaction

## Design notes / talking points for interviews

- Fixed timestep game loop using delta time for smooth rendering at 60 FPS.
- Y-sorting of entities for correct draw order based on world Y coordinate.
- Collision detection uses entity "solidArea" rectangles and predictive checks for next-frame collisions.
- Resource pipeline uses simple relative paths and ImageIO for sprites and fonts.

## Possible improvements / extension ideas

- Use a build system (Maven/Gradle) to manage builds and create an executable JAR.
- Implement save/load and multiple levels.
- Add a sprite sheet loader and animation data format for easier character additions.
- Expand AI behaviors and add pathfinding (A*).
- Add unit tests for non-UI systems (collision, map loading).

## Resume bullets (Google X Y Z format)

- Implemented a 2D Java game engine and shipped a reproducible build pipeline using Gradle and GitHub Actions, enabling automated CI builds and artifact uploads for consistent demo delivery.
- Built player interaction systems (movement, sprinting, item pickup, door unlocking) and collision detection across ~15 classes, enabling modular entity behaviors and reusable game components.
- Implemented resource management (maps, sprites, fonts, audio) and UI systems (title screen, HUD, dialogue), delivering a complete playable prototype suitable for demos and portfolio showcases.
- Improved rendering stability by optimizing draw order with Y-sorting and using double-buffered Swing rendering, maintaining smooth animations at 60 FPS on desktop JVMs.

## License

Add a license file here (e.g., MIT) if you want to allow public use. To add: create `LICENSE` and paste your chosen license text.

---

If you'd like, I can:
- Add a simple Gradle build and a runnable JAR target.
- Generate a short demo GIF by running the game and capturing frames (requires additional permissions/tools).
- Draft a one-page portfolio blurb or deploy instructions for itch.io.

Would you like me to add a Gradle build (recommended) and a runnable JAR next?"}