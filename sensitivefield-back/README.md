Sensitive field back
===============
__About project__

Back of this project has been coded by JAVA Spring boot, Spring security.

- __[UI](https://google.com.ua)__
- __[Full Project](https://google.com.ua)__
---


## Build

###Prerequisites

Make sure you have installed all of the following prerequisites on your development machine:

* Git - [Download & Install Git](https://git-scm.com/downloads). OSX and Linux machines typically have this already installed.
* Maven - [Download & Install Maven](http://maven.apache.org/download.cgi) linux/solaris/macos and Windows OS.
* MySQL Server - [Download & Install MySQL Installer](https://dev.mysql.com/downloads/installer/) on windows. [Other link](https://dev.mysql.com/downloads/mysql/) for any OS.

###Instructions 

#### 1. Execute sql scripts in MySQL
 In folder [sql-scripts](sql-scripts) execute sql scripts:

+ [create.sql](sql-scripts/create.sql) - create db tables, dependencies **FIRST ONLY**
+ [seed.sql](sql-scripts/seed.sql) - create default params
  - List of this params:
    * roles(links in the end of readme):
      * [User]()
      * [Extended user]()
      * [Admin]()
      * [Super User]()
    * types of events
    * kinds of events
    * new user with role [Super User]() with DEFAULT username = root, password = root

#### 2. Configure application properties
Open project file [application.properties](src/main/resources/application.properties)
and change (REQUIRED, check table:
1.  port connection
2.  db username
3.  jwt secret
4.  jwt token expiration in seconds

Table of variables below.
##### application.properties params

| Param | Description | Variable |
| ------- | --------- | ------- |
| port   | MySQL DB port connection | port param in url spring.datasource.url |
| username | MySQL user username | spring.datasource.username |
| password    | MYSQL user password | spring.datasource.password |
| jwt Secret | Secret key for encryption | sensitivefield.app.jwtSecret |
| jwt Token Expiration | The number of seconds that the access token will be valid | sensitivefield.app.jwtExpiration |

#### 3. Configure with Lombok
In project we are using Lombok to create getters, setters and no arguments constructor.
+ To start the project we need:
    - With IntelliJ IDEA:
        * Go to File -> Settings -> Plugins, enter in search line Lombok, press enter, install the lombok plugin.
    - Without Intellij IDEA (or without lombok)
        * Add getters and setters to the all fields of entities where getters/setters/noArgsConstructor annotations in use, these entities stay in the files of **[folder](src/main/java/com.hub.sensitivefield/model)**
        * Remove annotations in all models
             * **@Getter**
             * **@Setter**
             * **@NoArgsConstructor**
### 4. Run

To run JAR file you can use maven/gradle

#### Maven
 
mvn package && java -jar target/sensitivefield-0.0.1-beta.jar
 
#### Gradle  

./gradlew build && java -jar build/libs/sensitivefield-0.0.1.jar 

## Back-end API

### Swagger UI
In our project we use SWAGGER-UI.
You can open it using url like "localhost:8080/swagger-ui/"

## Info about project

### Type of Users

| Type | Rights |
| ------- | --------- |
| User   | Can monitor the current situation and view history up to two days ago |
| Extended user | Can view statistics and history of hits |
| Admin    | Can do everything everything except adding a superuser and an admin |
| Super user | Can do absolutely everything, including creating admins |

You can check java spring security configuration in this **[file](src/main/java/com.hub.sensitivefield/config/oAuth2Configuration/WebSecurityConfig)**

## License
Copyright (c) 2020-2020 SmartHub, Inc.
