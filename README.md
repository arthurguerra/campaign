# Campaign
Campaign App

## Architecture

The campaign applications is divided into the following packages:

### app

The app package has the three controllers of the application:

- CampaignController

This controllers receives all requests for campaigns. It also interacts with the CampaignService
to CRUD campaigns.

- FanController

This controllers receives register fan request and it interacts with the FanService
to register a new fan into the system.

- GlobalControllerExceptionHander

This is a special controller responsible for handling all expcetions thrown at
runtime in the application. It prevents the application from displaying
standard error messages from the servlets and allow the app to give better
feedback messages.

### core

This package contains the two core classes of this app: Fan and Campaign.

### exceptions

It has all expcetions thrown by the services of this app.

### service

This package has all services interfaces and their corresponding implementations.

#### impl

This package stays inside the service package because it can only contain implementations
for interfaces defined to be services.

### utils

Package created for keeping helper classes.

## Project Dependencies

- Maven 3.5.0
- Java 8
- Spring 4

*For more information about the dependencies required by this project, please,
check the **pom.xml** file.*

## Build

This project uses Maven to manage its dependencies. So, if you want to build this project
from scratch, run the following command:

    mvn clean package install

This command might take a little bit of time because Maven will make sure all dependencies exist
locally and if they do not, it will download all of them from its central repository.

Now, you can see that a new directory name *target* was created. This directory
contains the results of the build process. The file we are most interested to is
**campaign.jar**. This *jar* is the campaign application. To execute it, run:

    java -jar campaign.jar

Then, the application will boot and the campaign app will respond to requests on port 8080.