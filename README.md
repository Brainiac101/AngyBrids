# Angy Brids

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

This project is an Angry Birds clone, with 7 types of birds, 3 types of pigs and 3 types of blocks.

There are 6 fixed levels in the game, after which randomisation of levels starts, leading to infinite gameplay.

On top of that, there are 4 power-ups, whose details can be read further into the file.

## How to run
- Double-click on the Angy Brids-1.0.0.jar file
- Alternatively, to run the JAR file through the terminal, go to the root directory of the project and run the following command:
```
java -jar Angy\ Brids-1.0.0.jar
```

If you prefer to run through an IDE, follow the steps given below to run the project.

### IntelliJ Idea
- Open the build.gradle file and select "Open as Project"
- Open the Gradle Window by following these steps: `View -> Tool Windows -> Gradle`
- To run the program, double-click the run task, accessible through `lwjgl3 -> Tasks -> application`.

### Eclipse
- Choose `File -> Import -> Gradle -> Existing Gradle Project`
- Right-click the lwjgl3 project and select `Run as -> Run Configurations`
- Select Java Application (on the right side)
- Click the icon at top left to create a new run configuration
- Select the `Lwjgl3Launcher` class as the Main class
- Click on the `Arguments` Tab
- Under Working Directory (at the bottom), select `Other -> Workspace`
> For macOS, add `-XstartOnFirstThread` argument to VM options
- Select the assets folder

## How to test
- Go to the root directory of the project and run the following command:
`./gradlew test`

## Information
The game is not an exact copy of the Classical Angry Birds game but heavily draws inspiration from it.

### Damage dealing rules
- A pig is dealt one hit if it collides with the ground with a vertical velocity.
- A pig is dealt one hit if it collides with the block when either of the 2 objects have vertical velocity.
- A pig is dealt damage equal to the bird's health upon direct impact with the bird.
- Similarly, a bird is dealt damage equal to the pig's health upon direct impact with the pig.
- A bird dies when it directly hits the ground.
- A bird gets damaged equal to the block's body upon direct impact with the block.
- A block is dealt one hit when it collides with another block with a vertical velocity.
- A block is dealt one hit when it falls on the ground with a vertical velocity.

### Birds
There are 7 types of birds in the games, with varying density and health.

### Pigs
There are 3 types of pigs, Basic, Crazy, and King, each with varying surface area and health.

### Blocks
There are 3 types of blocks - Wood, Stone and Glass, each with varying density and health.

### Power-Ups
The game currently has 4 power-ups
- **Bird Quake** - deals a single hit to all pigs and blocks in the level
- **Sling Shot** - Multiplies the velocity of launch of the bird, thus allowing to reach farther than it could before.
- **Super Charge** - A one time use power up that deals one hit to every entity within a radius of `100`, with the center being the point of death of the bird.
- **Power Potion** - A one time use power up that increases the health of the current bird by 4.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## References
- [Stack Overflow](https://stackoverflow.com/)
- [LibGDX official Documentation](https://libgdx.com/wiki/)
- [Game From Scratch tutorials](https://gamefromscratch.com/libgdx-tutorial-series/)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)
- [Google Images](https://images.google.com/) (For textures)

## UML
![UML](https://github.com/user-attachments/assets/da92f0f3-2007-4c65-8e62-18e5304f4c2c)
