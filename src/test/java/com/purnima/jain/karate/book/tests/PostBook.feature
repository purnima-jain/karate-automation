Feature: Book POST API Tests

  Scenario: Create a new Book
    Given header Authorization = 'Token ' + bookToken
    Given url apiBookBaseUrl
    * print apiBookBaseUrl
    And request {"title": "War and Peace", "author": "Leo Tolstoy"}
    When method Post
    Then status 201
    * print response
    And match response.title == 'War and Peace'
    And match response.author == 'Leo Tolstoy'
    
    