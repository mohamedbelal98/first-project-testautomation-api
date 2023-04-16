# restful-booker_api_testautomation

* Implement a test automation framework using Shaft Engine and RestAssured for testing Restful Booker API

* Implement modular design by Applying the Object Model design pattern (POM).

# Technologies

* JDK-17.

* Maven as a build tool.

* TestNG as a unit test framework.

* Shaft Engine for API testing.

* Rest Assured for API testing.

#  Run the Test

## 1. Locally

Prerequisites: jdk-17 and maven should be installed

### 1.1 Run the Test locally using IntelliJ IDEA

1. Import this project to your intellij.

2. Add listeners:
   - com.shaft.tools.listeners.AlterSuiteListener.
   - com.shaft.tools.listeners.SuiteListener.
   - com.shaft.tools.listeners.InvokedMethodListener.

3. Run the test cases/classes

### 1.2 Run the Test locally using the Terminal or CMD

1. Clone or download the project.
2. run the following command `mvn test` to run all test classes.
3. run the following command `mvn test -Dtest=TestClassName` to run single test class.
4. run the following command `mvn test -Dtest=TestClassName#TestMethodName` to run single test method.
