# SauceDemo Automation Project

## Description

A web automation framework designed to test various functionalities on the [SauceDemo](https://www.saucedemo.com/) e-commerce platform. The goal is to evaluate how the application behaves under different conditions for multiple users and identify any issues or limitations in the user experience. All interactions are designed to mimic real user scenarios with robust error handling and efficient execution.


## Disclaimers

1. *Explicit Waits*
    - The framework uses explicit waits to ensure elements are either visible or clickable before interacting with them.
    - If an element is not found within *8 seconds*, a TimeoutException is thrown, which is then handled in the same method by throwing a **custom exception** specific to the page where the error occurred.
    - When these custom exceptions are thrown, the framework initiates a specific flow that redirects to the *Login Page*, allowing for recovery and continuity in testing.

2. *JDK Version*
    - Built and tested using *OpenJDK 23*.

3. *Driver Management*
    - This framework utilizes *WebDriverManager*, eliminating the need to manually download browser drivers.
    - To specify the browser, simply configure the desired browser name in the suite.xml file.

4. *Execution Flow*
    - Tests are executed in the following sequence to ensure comprehensive coverage:
        - *Purchase Tests*: Validating the ability to complete purchases successfully.
        - *Cart Management Tests*: Testing remove item functionality.
        - *Logout Tests*: Ensuring proper logout behavior across different accounts.

5. *Data-Driven Testing*
    - Implements *Data Providers* to test all available user credentials on the SauceDemo platform.
    - Each test execution involves different user data to ensure complete validation.

6. *Error Reporting*
    - *Assertions* and *custom exception messages* are used to provide detailed information about failures.


## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/AnthonyyHL/SauceDemoAutomationProject.git

2. Install the required dependencies using Maven or any other build tool.
3. Configure your `suite.xml` file with the desired browser and test onfigurations.
4. Run the tests.
