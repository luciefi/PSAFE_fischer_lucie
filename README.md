## Safety Net

Safety Net is a Spring Boot application made with Maven.

It loads personal data from the data.json file and make it available to emergency services.

To run the application, execute commands

`mvn install`

and 

`mvn spring-boot:run`

### Testing and coverage

The app has unit tests and integration tests written.

To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

`mvn test`

To get the coverage report from Jacoco, run command

`mvn verify`

The HTML report index.html can be found in the folder jacoco in target/site. Models, DTOs, DataSource and 
SafetyNetApplication are excluded from the coverage.

The Surefire reports can be found in the surefire-reports folder in target/.