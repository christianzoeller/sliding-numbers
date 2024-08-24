# Sliding Numbers

An implementation of the famous [2048 game](https://en.wikipedia.org/wiki/2048_(video_game)).

The app is written with Jetpack Compose and other Jetpack libraries such as Hilt, Room and
DataStore.

The implementation of the actual game mechanics follows the Java implementation on
[Rosetta Code](https://rosettacode.org/wiki/2048#Java).

## Building

This project is configured to automatically create signed release builds. The files containing
the secrets necessary for signing the builds are added to the repository as
[secure files](https://docs.gitlab.com/ee/ci/secure_files/). Therefore, simply checking out the
repository and attempting to build it will result in a failed Gradle sync.

Essentially, there are three options to remedy this:
1. Remove the [parts relevant to signing builds](https://developer.android.com/studio/publish/app-signing#secure-shared-keystore)
from the *build.gradle.kts* file of the ```app``` module and only work with debug builds.
2. Create your own keystore and *keystore.properties* file.
3. Contact me to request Developer role for this repository, allowing you to download secure
files.

## Open Source Libraries

This project makes use of [AboutLibraries](https://github.com/mikepenz/AboutLibraries) to display a
list of used open source dependencies and their licenses within the app.

In order to keep this information up-to-date, the following gradle task has to be run after
changing a dependency:

```
app:exportLibraryDefinitions
```

## Background

The project was created in order to have a small app to test things out. To be more precise, the first
testable versions of [type-safe Navigation Compose](https://developer.android.com/guide/navigation/design/type-safety)
were the trigger to start the project.

This is also why the app has no especially game-like UX or fancy animations. It is just a means to an
end.

So far, it also allowed me to test out [Konsist](https://github.com/LemonAppDev/konsist) as a means
to impose architecture guidelines, naming conventions and the like.

### Why 2048?

The idea to implement the 2048 game for this came to me when I saw some of the passengers on a
flight playing the game on the screens in their seats. I used to play the game in school and was a bit
surprised to see that it is still around.

As for the name, I originally planned to code some nice animations for the movement of the numbers, but
in the end, I never did that to keep the work put into this project at a reasonable level (I also did
not want to just call it "2048").