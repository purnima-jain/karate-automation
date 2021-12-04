Feature: Customer POST API Tests

  Background: Define URL, Token & Data-Generator
    # Given url apiTokenBaseUrl + '/createTokenViaApi'
    # * print 'apiTokenUrl::' + apiTokenBaseUrl + '/createTokenViaApi'
    # And request {"username": "username_1", "password": "password_1"}
    # When method Post
    # Then status 200
    # * def token = response.token
    # * print 'Token :: ' + token
    # * def tokenResponse = callonce read('classpath:com/purnima/jain/helpers/CreateToken.feature')
    # * def token = tokenResponse.authToken
    # * print 'The value of the token is ' + token
    * def dataGenerator = Java.type('com.purnima.jain.helpers.DataGenerator')


  Scenario: Create a new Customer
    Given header Authorization = 'Token ' + token
    Given url apiCustomerBaseUrl
    * print apiCustomerBaseUrl
    And request {"firstName": "John", "lastName": "Doe"}
    When method Post
    Then status 201
    * print response
    And match response.firstName == 'John'
    And match response.lastName == 'Doe'


  Scenario: Create a new Customer using Embedded & Multi-Line Expression
    # Embedded Data
    Given def userData = {"firstName": "John", "lastName": "Doe"}
    Given header Authorization = 'Token ' + token
    Given url apiCustomerBaseUrl
    * print apiCustomerBaseUrl
    # Multi-Line Expression
    And request
      """
      	{
         	"firstName": #(userData.firstName),
         	"lastName": #(userData.lastName)
      	}
      """
    When method Post
    Then status 201
    * print response


  Scenario: Create a new Customer using Java Data-Generator	(static method)
    * def randomFirstName = dataGenerator.getRandomFirstName()
    * def randomLastName = dataGenerator.getRandomLastName()
    Given header Authorization = 'Token ' + token
    Given url apiCustomerBaseUrl
    * print apiCustomerBaseUrl
    And request
      """
      	{
         	"firstName": #(randomFirstName),
         	"lastName": #(randomLastName)
      	}
      """
    When method Post
    Then status 201
    * print response
    And match response ==
      """
      	{
      		"customerId": "#string",
      		"firstName": #(randomFirstName),
      		"lastName": #(randomLastName)
      	}
      """


  Scenario: Create a new Customer using Java Data-Generator	(non-static method)
    # Get Random First Name
    * def jsGetRandomFirstNameFunction =
      """
      	function () {
      		var DataGenerator = Java.type('com.purnima.jain.helpers.DataGenerator')
      		var generator = new DataGenerator()
      		return generator.getRandomFirstNameViaNonStaticMethod()
      	}
      """
    * def randomFirstName = call jsGetRandomFirstNameFunction
    # Get Random Last Name
    * def jsGetRandomLastNameFunction =
      """
      	function () {
      		var DataGenerator = Java.type('com.purnima.jain.helpers.DataGenerator')
      		var generator = new DataGenerator()
      		return generator.getRandomLastNameViaNonStaticMethod()
      	}
      """
    * def randomLastName = call jsGetRandomLastNameFunction
    # Call API
    Given header Authorization = 'Token ' + token
    Given url apiCustomerBaseUrl
    * print apiCustomerBaseUrl
    And request
      """
      	{
         	"firstName": #(randomFirstName),
         	"lastName": #(randomLastName)
      	}
      """
    When method Post
    Then status 201
    * print response
    And match response ==
      """
      	{
      		"customerId": "#string",
      		"firstName": #(randomFirstName),
      		"lastName": #(randomLastName)
      	}
      """


  Scenario Outline: Validate Customer
    * def randomFirstName = dataGenerator.getRandomFirstName()
    * def randomLastName = dataGenerator.getRandomLastName()
    Given url apiCustomerBaseUrl + '/validate'
    * print apiCustomerBaseUrl
    And request
      """
      	{
         	"firstName": "<firstName>",
         	"lastName": "<lastName>"
      	}
      """
    When method Post
    Then status <status>
    * print response
    And match response == <errorResponse>

    Examples: 
      | firstName          | lastName          | status | errorResponse                                             |
      | #(randomFirstName) | KKK               |    422 | {"errors":["Last Name should be at least 4 characters"]}  |
      | KKK                | #(randomLastName) |    422 | {"errors":["First Name should be at least 4 characters"]} |
      | KKKK               | KKKK              |    200 | {"errors":[]}                                             |
      |                    | KKKK              |    422 | {"errors":["First Name cannot be empty"]}                 |


  Scenario: Create a new Customer by reading Json Payload from file
    Given header Authorization = 'Token ' + token
    Given url apiCustomerBaseUrl
    * print apiCustomerBaseUrl
    * def createCustomerRequestBodyFromFile = read('classpath:com/purnima/jain/karate/customer/json/CreateCustomer.json')
    And request createCustomerRequestBodyFromFile
    When method Post
    Then status 201
    * print response
