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
- [ ] date should be saved in ISO 8601 format (no open text entry)
- [ ] improving UI

admin
- [ ] add login screen for admins
- [ ] edit global_ranking screen
- [ ] edit race screen
- [x] edit runner screen
- [ ] edit runner_race screen
- [ ] edit segment screen
- [ ] edit segment_times screen
- [x] edit volunteer screen
- [ ] edit volunteer_race screen

user
- [ ] add login screen for users
- [x] add screen for registration of runners (no login required)
- [ ] add screen for registration of volunteers (no login required)

## analyse code with PMD

edit the build.gradle file in the pmd section to the correct file location (and uncomment line)

if you want your build to fail, set ignoreFailures to false

```groovy
pmd {
    toolVersion = "6.52.0"
    //ruleSetFiles("path:\\to\\project\\top\\directory\\databases-project\\src\\main\\resources\\pmd\\pmdRules.xml")
    ignoreFailures = true
}
```

to test the program, execute the gradlew build command (generates html file)

## diagram

![diagram](/diagram/diagram.svg)
