Feature: Book GET API Tests

	Background: Define URL
		Given url apiBookBaseUrl
		* print 'apiBookBaseUrl::', apiBookBaseUrl
	
	Scenario: Get All Books
		* print 'Test: Get All Books'
		Given path '/list'
		When method Get
		Then status 200
	
	@myTag
	Scenario: Get All Books 2
		* print 'Test: Get All Books 2'
		Given path '/list'
		When method Get
		Then status 200
		
	@ignore
	Scenario: Get All Books 3
		* print 'Test: Get All Books 3'
		Given path '/list'
		When method Get
		Then status 200				