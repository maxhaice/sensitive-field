# EventGenerator

**EventGenerator** - console tool, that was developed for testing SensitiveField [backend](https://gitlab.com/m.nawrocki/sensitive-field/-/tree/develop/sensitivefield-back). This helper app written in "pure" Java using [Maven](https://maven.apache.org/) build automation tool.

![EventGenerator screenshot](https://img.techpowerup.org/201119/eg-screenshot.png)

## Build

### Prerequisites
 1. OpenJDK 11 - [Download](https://adoptopenjdk.net/releases.html).
 2. IntelliJ IDEA - [Download](https://www.jetbrains.com/idea/).

### Instruction

 1. Run  `mvn package` for build executable JAR.
 2. Open command line (or terminal). 
 3. Navigate to */target* in project directory, where you can find ***EventGenerator-1.0.jar*** file. 
 4. Execute command `java -jar EventGenerator-1.0.jar`.

## Usage

 - By default, this tool generates events with random delay. But you can run this jar with different options for customize tool`s behavior. 
 - List of all flags show below:
 ![Help message](https://i.ibb.co/R4GgNgR/eg-help.png)

 - For example, command `java -jar EventGenerator-1.0.jar -d 10` sets delay between audio events to **10 seconds**. 
 - By default, sensor will be created with random id and coordinates. But command `java -jar EventGenerator-1.0.jar -e` says tool to use **only existing** sensors. 
 - You may need to use randomized delays, too. By default, random delay generates in range **from 5 to 20 seconds**. But you can specify another range using flag `-r` or `--range`.
  Example: `java -jar EventGenerator-1.0.jar -r 10,20`.
- Option `-p` "*prettify*" console output of *AudioEvent* JSON objects.
 - Use flag `-v` for more detailed output and `-h` to show help.
