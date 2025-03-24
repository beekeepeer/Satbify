# Satbify

Satbify is a rule-based system that generates SATB (Soprano, Alto, Tenor, Bass) harmonic structures based on user prompts created in the Reaper MIDI editor. It adheres to essential voice-leading principles, ensuring smooth and musically correct harmonizations.

## Features

- **Automated SATB Harmonization**: Transforms simple MIDI prompts into complex harmonies using key switches in Reaper.
- **Optimized Voicing**: Evaluates all possible voice arrangements and selects the smoothest option while avoiding forbidden parallels.
- **Register Consistency**: Maintains the overall height of the music, adjusting smoothly to register changes in the prompt.
- **Detailed Control**: Allows precise specification of chord bass, melody degree, chord width, type, scale, and key to ensure accuracy.
- **Custom Note Integration**: Users can insert specific notes into any voice, and Satbify will adjust harmonization accordingly.
- **Continuous Improvement**: Regular updates introduce new musical rules and optimizations.

## Planned Features

If there is sufficient interest, future updates may include:
- **Deploy** on server (as it was before).
- **Utilize LLM** service for improving some complex decisions and for theoretical explanations.
- **User Authentication**: Secure login for personalized experiences.
- **Web Interface**: Store and retrieve chord progressions in a personal library.
- **Collaboration Tools**: Share progressions among users, with options for rating and tagging.

## Technical Details

- **Core Application**: Written in Java 17, utilizing dynamic programming for decision-making.
- **Reaper Script**: Implemented in Lua.
- **Communication with the server**: Uses `cURL` and a REST API with json load to facilitate interaction.

## Installation & Usage

1. **Install the Reaper Lua Script**: Download and install the Lua script from this repository.
2. **Run the Spring Boot Web Application**: Start the Java-based backend service. It is intended to run on the server.
3. **Create a Prompt in Reaper**: Use the MIDI editor to define some key switches.
4. **Execute the Script**: Run the Lua script in Reaper to generate harmonized SATB output.

https://www.youtube.com/embed/IDbd3HZmEW0

## License
All rights reserved.
```
src
 └───main
     ├───java
     │   └───com
     │       └───DAWIntegration
     │           └───Satbify
     │               │   SatbifyApplication.java
     │               │
     │               ├───controllers
     │               │       PageController.java
     │               │       SatbifyController.java
     │               │
     │               ├───keySwitchAppliers
     │               │       AlterationKeySwitchApplier.java
     │               │       ChordTypeKeySwitchApplier.java
     │               │       FinalNotesKeySwitchApplier.java
     │               │       InversionKeySwitchApplier.java
     │               │       KeySwitchApplier.java
     │               │       MelodicPositionKeySwitchApplier.java
     │               │       PhrasePeriodKeySwitchApplier.java
     │               │       RegisterKeySwitchApplier.java
     │               │       RootKeySwitchApplier.java
     │               │       ScaleKeySwitchApplier.java
     │               │       SpacingKeySwitchApplier.java
     │               │
     │               ├───module
     │               │       Alteration.java
     │               │       ChordType.java
     │               │       Degree.java
     │               │       FatChord.java
     │               │       Inversion.java
     │               │       Key.java
     │               │       MelodicPosition.java
     │               │       Note.java
     │               │       Occurrence.java
     │               │       REAPER_stuff.java
     │               │       RequestDeserialized.java
     │               │       Scale.java
     │               │       Spacing.java
     │               │
     │               ├───repository
     │               │       FatChordRepository.java
     │               │
     │               └───service
     │                       ChordFinder.java
     │                       ChordsToReaperSerializer.java
     │                       ChordVersionCreator.java
     │                       ChordVersionsConnector.java
     │                       KeySwitchSorter.java
     │                       NotesToChordsConverter.java
     │                       ReaperDeserializer.java
     │                       SatbifyFacade.java
     │                       SatbifyMethods.java
     │
     └───resources
         │   application.properties
         │
         ├───data
         │       incomingDataExample.json
         │       incomingDataExampleSmall.json
         │       incomingDataHumanized.json
         │
         ├───DAW_scripts
         │   └───REAPER
         │           satbify 0.03.lua
         │           satbify_note_names.txt
         │           SaveTokenExample.lua
         │
         ├───META-INF
         │       MANIFEST.MF
         │
         └───templates
                 download_page.html
                 main_page.html
                ```