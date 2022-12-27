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
- [x] add runners
- [x] edit runners
- [x] delete runners across the database
- [ ] improving UI

implementing Jdbi and model
- [ ] global_ranking
- [x] race
- [x] runner
- [ ] runner_race
- [x] segment
- [ ] segment_times
- [x] volunteer
- [ ] volunteer_race

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
