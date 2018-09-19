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
    Example: --startDate=2017-01-01.00:00:00  
 
    start date of the database search. Must be provided in yyyy.MM.dd.HH:mm:ss format
  
 - #### Duration  
    Argument: duration  
    Type: Enum  
    Format: Hourly | Daily  
    Required: true  
    Example: --duration=daily  
 
    Duration determines range that will be searched in database. Duration can have two values HOURLY or DAILY.
    Hourly will define time range of one hour from startDate argument while Daily will define range of 
    one day from startDate argument. 
  
 - #### Threshold  
    Argument: threshold  
    Type: int  
    format: number  
    Required: true  
    Example: --threshold=500  
 
    number of the requests in given range that will trigger restriction.
  
 - #### Access Log  
    Argument: accesslog  
    Type: file  
    Format: String  
    Required: false  
    Example: --accesslog=data/access.log  
 
    Location of the log file that will be parsed and inserted into the database. Log File must be a csv file following format:  
    date (yyyy-MM-dd HH:mm:ss.SSS) | ip address | http method | http response | http client
 
 ### Examples
 
Example without accesslog:

```
java -jar target/parser.jar --startDate=2017-01-01.00:00:00 --duration=daily --threshold=500
```

Example with accesslog:

```
java -jar target/parser.jar --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200 --accesslog=data/access.log
```

*note: accesslog will be processed first in order to populate the database for search*

Example with -cp parameter:

```
java -cp target/parser.jar com.ef.Parser --startDate=2017-01-01.00:00:00 --duration=daily --threshold=200 --accesslog=data/access.log
```

 ### Running Tests

#### Unit Tests
Unit tests are located inside _src/test_ directory. Tests are automatically run during package and install phase. 
If you need to manually run just unit tests you can do so by executing:
```
mvn test
``` 

#### Integration Tests
Integration tests are found in _src/it_ directory suffixed with _IT.java_. These tests will not be run during package 
but will be run during install phase of maven lifecycle, tests can also be run manually with the following command:
```
mvn verify
```

_note: This will also trigger Unit Tests_

## Util
Data folder contains utility files that can help with app development:

 - **access.log** - example log file that has proper format required by the app
 - **docker-database.sh** - executable shell script that will bootstrap mysql:8.0 server with proper database and database access
 - **test-instructions.txt** - original instructions for the project
 - **solution.sql** - Solution of the sql questions. These scripts can also be found in src/main/resources/com/ef/repository/AccessLogRepository.xml

## Technologies
 
 - Spring Boot 2 - dependency injection and bootstrapping framework
 - MyBatis - Database access layer
 - Project Reactor - Reactive streams
 - Liquibase - Database versioning
 - JCommander - for parsing cli arguments
 - Maven - for packaging and testing application