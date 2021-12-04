Feature: Customer GET API Tests

  # Background: Define URL & Token
  # Given url apiCustomerBaseUrl
  # * print 'apiCustomerBaseUrl::', apiCustomerBaseUrl
  # Retrieve Token
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
  
  
  Scenario: Get All Customers
    Given url apiCustomerBaseUrl + '/list'
    When method Get
    Then status 200


  Scenario: Get N Customers where limit & offset are query-parameters in url
    # Limitation: path does not work with query-parameters
    # Given path '/listByCount?count=5'
    Given url apiCustomerBaseUrl + '/listByCount?limit=5&offset=0'
    When method Get
    Then status 200
    * print response
    # Schema Validation
    And match each response ==
      """
      	{
      		"customerId": "#string",
      		"firstName": "#string",
      		"middleName": "##string",
      		"lastName": "#string"
      	}
      """


  Scenario: Get N Customers where limit & offset are query-parameters NOT in url
    Given param limit = 5
    Given param offset = 0
    Given url apiCustomerBaseUrl + '/listByCount'
    When method Get
    Then status 200
    # Verify that the returned object is array
    And match response == "#array"
    # Match the size of the array
    And match response == "#[5]"
    # Verify the firstName of the first element in the array is a String
    And match response[0].firstName == "#string"
    # Verify at least one of the firstName in not null
    And match response[*].firstName !contains null
    # Verify that the "firstName" anywhere in the json hierarchy is not null
    And match response..firstName !contains null
    # Verify that each lastName anywhere in json hierarchy is string
    And match each response..lastName == "#string"
    # Verify that each lastName anywhere in json hierarchy is either string or null or it's optional (meaning missing from output)
    And match each response..lastName == "##string"


  Scenario: Get Non-Existent Customer By Id
    Given url apiCustomerBaseUrl + '/12345'
    When method Get
    Then status 404


  Scenario: Create a Customer and retrieve that Customer By Id
    # Create a new Customer
    Given header Authorization = 'Token ' + token
    Given url apiCustomerBaseUrl
    * print apiCustomerBaseUrl
    And request {"firstName": "John", "lastName": "Doe"}
    When method Post
    Then status 201
    * def customerId = response.customerId
    # Retrieve Customer By customerId
    Given url apiCustomerBaseUrl + '/' + customerId
    When method Get
    Then status 200
    And match response.customerId == customerId
    And match response == {"customerId": "#string", "firstName": "#string", "lastName": "#string"}
    And match response == {"customerId": "#string", "firstName": "John", "lastName": "Doe"}
