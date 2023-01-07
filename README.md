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
- [x] password hashing
- [ ] password salting
- [ ] date should be saved in ISO 8601 format (no open text entry)
- [ ] improving UI (low priority)

admin
- [ ] add login screen for admins
- [ ] add segments to race from edit races menu
- [ ] add entities to junction tables (select from choiceBox)

user
- [x] add registration screen for runners
- [x] add login screen for runners
- [ ] runners should be able to select races after login
- [ ] add login screen for volunteers
- [ ] add registration for volunteers
- [ ] volunteers should be able to select races after login

## diagram

![diagram](/diagram/diagram.svg)
