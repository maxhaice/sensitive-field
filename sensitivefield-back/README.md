`Sensitive Field` backend
===============
__About the project__

Backend of `Sensitive Field` project has been developed using JAVA Spring boot and Spring security technologies.

- __[UI](../sensitivefield-front)__
- __[Full Project](https://gitlab.com/m.nawrocki/sensitive-field)__
---


## Build

### Prerequisites

Make sure that you have installed all of the following prerequisites on your development machine:

* Git - [Download & Install Git](https://git-scm.com/downloads). OSX and Linux machines typically have this already installed.
* Maven - [Download & Install Maven](http://maven.apache.org/download.cgi) linux/solaris/macos and Windows OS.
* MySQL Server - [Download & Install MySQL Installer](https://dev.mysql.com/downloads/installer/) on windows. [Other link](https://dev.mysql.com/downloads/mysql/) for any OS.

### Instructions 

#### 1. Execute SQL scripts in MySQL
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
Open a project file [application.properties](src/main/resources/application.properties) and change values of following required variables:
1.  db port
2.  db username
2.  db password
3.  jwt secret
4.  jwt token expiration in seconds

Table of variables below.
##### application.properties params

|------------------------------------------------------------------------------------------------------------------------------|
| Param                | Description                                               | Variable                                  |
| -------------------- | --------------------------------------------------------- | ----------------------------------------- |
| port                 | MySQL connection port                                     | `port` param within spring.datasource.url |
| username             | MySQL username                                            | spring.datasource.username                |
| password             | MYSQL password                                            | spring.datasource.password                |
| jwt Secret           | Secret key for encryption                                 | sensitivefield.app.jwtSecret              |
| jwt Token Expiration | The number of seconds that the access token will be valid | sensitivefield.app.jwtExpiration          |
|------------------------------------------------------------------------------------------------------------------------------|

#### 3. Configure with Lombok
In the project we are using `Lombok` to create getters, setters and "default" constructors.
+ To start the project we need:
    - Withing `IntelliJ IDEA` IDE:
        * Go to File -> Settings -> Plugins, enter in search line Lombok, press enter, install the lombok plugin.
    - Within any other IDE (or without `Lombok` itself):
        * Add getters and setters to all fields of entities where getters/setters/default constructors are in use. These entities are located in **[folder](src/main/java/com.hub.sensitivefield/model)** folder.
        * Remove annotations for all models
             * **@Getter**
             * **@Setter**
             * **@NoArgsConstructor**
### 4. Run

To run JAR file you can use `maven`/`gradle`

#### Maven
 
```sh
mvn package && java -jar target/sensitivefield-0.0.1-beta.jar
```
 
#### Gradle  

```sh
./gradlew build && java -jar build/libs/sensitivefield-0.0.1.jar 
```

## Back-end API

### Swagger UI
In our project we use SWAGGER-UI.
To open it you should:
    * Run application
    * Navigate to `localhost:8080/swagger-ui/` in browser

## Info about the project

### Users rights

|---------------------------------------------------------------------------------------|
| Type          | Rights                                                                |
|---------------|-----------------------------------------------------------------------|
| User          | Can monitor the current situation and view history up to two days ago |
| Extended user | Can view statistics and history of hits                               |
| Admin         | Can do everything everything except adding a superuser and an admin   |
| Super user    | Can do absolutely everything, including creating admins               |
|---------------------------------------------------------------------------------------|

You can check java spring security configuration [here](src/main/java/com.hub.sensitivefield/config/oAuth2Configuration/WebSecurityConfig).

## License
Copyright (c) 2020-2020 SmartHub, Inc.
