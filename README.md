[![Java CI with Gradle](https://github.com/lowie2727/databases-project/actions/workflows/gradle.yml/badge.svg)](https://github.com/lowie2727/databases-project/actions/workflows/gradle.yml)

# databases-project

running race

## run program

JDK 11 or higher required

to build the program, execute the following command
```bash
./gradlew build --build-cache --parallel --no-watch-fs
```

to run the program, execute the following command
```bash
./gradlew run --build-cache --parallel --no-watch-fs
```

## TODO

- [ ] use SQLite CHECK constraints on columns (e.g. check names for special characters)
- [ ] update diagram to latest version

## diagram

![diagram](/diagram/diagram.svg)
