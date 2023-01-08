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
- [x] date should be saved in ISO 8601 format (no open text entry)
- [ ] improving UI (low priority)

admin
- [ ] add login screen for admins
- [x] add segments to race from edit races menu
- [x] add entities to junction tables (select from choiceBox)
- [ ] do not show all data in the choiceBox
- [ ] the distance of the segments together must not exceed the distance of the race (editRace class)
- [ ] the time should be obtained in the UI by entering hours minutes and seconds (runnerRace, segmentTime)
- [ ] the global ranking should add up the times of runners from different races (runner with the fastest pace over the various races wins (exploitable solution))

user
- [x] add registration screen for runners
- [x] add login screen for runners
- [ ] runners should be able to select races after login
- [ ] there should be a logout button (should not be visible in the admin screen)
- [ ] runners should be able to view the global ranking
- [ ] add login screen for volunteers
- [ ] add registration for volunteers
- [ ] volunteers should be able to select races after login

## diagram

![diagram](/diagram/diagram.svg)
