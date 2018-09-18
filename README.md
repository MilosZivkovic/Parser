# Log Parser Application
This is a simple java application that parses access logs, 
saves them into a database and searches the log for ip addresses that
meet specified criteria.

Features:

 - Parse Log files and save them into database
 - Search saved log file for ip addresses that meet given criteria
 
## Getting Started
Project uses maven as build system. 
You can easily build the project by executing following maven command:

```
mvn clean pakcage
```

Then after the project has been built you can execute it with simple java jar command:

```
java -jar target/parser.jar [options]
```

### Options  
Options are key value pairs separated by space:

  - ##### Start Date  
  
    Argument: startDate  
    Type: DateTime  
    Format: yyyy-MM-dd.HH:mm:ss  
    Required: true  
 
    start date of the database search. Must be provided in yyyy.MM.dd.HH:mm:ss format
  
 - #### Duration  
    Argument: duration  
    Type: Enum  
    Format: Hourly | Daily  
    Required: true  
 
    Duration determines range that will be searched in database. Duration can have two values HOURLY or DAILY.
    HOURLY will add one hour to the startDate argument while DAILY will add one day to startDate.
  
 - #### Threshold  
    Argument: threshold  
    Type: int  
    format: number  
    Required: true  
 
    number of the requests in given range that will trigger restriction.
  
 - #### Access Log  
    Argument: accesslog  
    Type: file  
    Format: String  
    Required: false  
 
    Location of the log file that will be parsed and inserted into the database. Log File must be a csv file following format:  
    date (yyyy-MM-dd HH:mm:ss.SSS) | ip address | http method | http response | http client
 
 ### Examples
 
example without accesslog:

```
java -jar target/parser.jar -startDate=2017-01-01.00:00:00 --duration=daily --threshold=500
```

example with accesslog:

```
java -jar target/parser.jar -startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200 -accesslog=data/access.log
```

*note: accesslog will be processed first in order to populate the database for search*

## Util
Data folder contains utility files that can help with app development:

 - **access.log** - example log file that has proper format required by the app
 - **docker-database.sh** - executable shell script that will bootstrap mysql:8.0 server with proper database and database access
 - **test-instructions.txt** - original instructions for the project

## Technologies
 
 - Spring Boot 2 - dependency injection and bootstrapping framework
 - MyBatis - Database access layer
 - Project Reactor - Reactive streams
 - Liquibase - Database versioning
 - JCommander - for parsing cli arguments

## About 

My name is Milos Zivkovic. I have been working as a java software developer for 2.5 years. This is a sample project with 
the intention of demonstrating my java proficiency. You can find me on following links:

 - Linkedin: https://www.linkedin.com/in/zivkovicmilos/
 - Github: https://github.com/MilosZivkovic
 - Email: developer.milos.zivkovic@gmail.com