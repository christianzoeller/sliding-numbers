# Sliding Numbers

An implementation of the famous [2048 game](https://en.wikipedia.org/wiki/2048_(video_game)).

The app is written with Jetpack Compose and other Jetpack libraries such as Hilt, Room and
DataStore.

The implementation of the actual game mechanics follows the Java implementation on
[Rosetta Code](https://rosettacode.org/wiki/2048#Java).

## Open Source Libraries

This project makes use of [AboutLibraries](https://github.com/mikepenz/AboutLibraries) to display a
list of used open source dependencies and their licenses within the app.

In order to keep this information up-to-date, the following gradle task has to be run after
changing a dependency:

```
app:exportLibraryDefinitions
```