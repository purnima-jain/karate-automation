Feature: Customer DELETE API Tests

  # Background: Define URL & Token
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
  
  
  Scenario: Create & then Delete a Customer
    # Create a Customer
    Given header Authorization = 'Token ' + customerToken
    Given url apiCustomerBaseUrl
    * print apiCustomerBaseUrl
    And request {"firstName": "Jane", "lastName": "Doe"}
    When method Post
    Then status 201
    * print response
    And match response.firstName == 'Jane'
    And match response.lastName == 'Doe'
    * def customerId = response.customerId
    * print 'customerId:: ' + customerId
    # Search for the newly created Customer
    Given url apiCustomerBaseUrl + '/' + customerId
    When method Get
    Then status 200
    And match response.customerId == customerId
    # Delete the newly created Customer
    Given header Authorization = 'Token ' + customerToken
    Given url apiCustomerBaseUrl + '/' + customerId
    When method Delete
    Then status 200
    # Search for the deleted Customer
    Given url apiCustomerBaseUrl + '/' + customerId
    When method Get
    Then status 404
