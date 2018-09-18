# Log Parser Application
This is a simple java application that parses access logs, 
saves them into a database and searches the log for ip addresses that
meet specified criteria.

Features:

 - Parse Log files and save them into database
 - Search saved log file for ip addresses that meet given criteria
 
## Technologies
 
 - Spring Boot 2 - dependency injection and bootstrapping framework
 - MyBatis - Database access layer
 - Project Reactor - Reactive streams
 - Liquibase - Database versioning
 - JCommander - for parsing cli arguments

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

Options are key value pairs separated by space:

 - startDate - start date of the ip address restriction. startDate must be in yyyy-MM-dd.HH:mm:ss **(required)**
 - duration - duration of the search from startDate. Can only have two values daily or hourly **(required)**
 - threshold - number of the requests in given range that will trigger restriction **(required)**
 - accesslog - access log that will be loaded into the database **(optional)**
 
example without accesslog:

```
java -jar target/parser.jar -startDate=2017-01-01.00:00:00 --duration=daily --threshold=500
```

example with accesslog:

```
java -jar target/parser.jar -startDate=2017-01-01.00:00:00 --duration=daily --threshold=500 -accesslog=data/access.log
```

*note: accesslog will be processed first in order to populate the database for search*