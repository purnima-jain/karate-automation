Feature: Create Token via API

	Scenario: Create Token Via API
		Given url apiTokenBaseUrl + '/createTokenViaApi'
		* print 'apiTokenUrl::' + apiTokenBaseUrl + '/createTokenViaApi'
		And request {"username": "#(username)", "password": "#(password)"}
		When method Post
		Then status 200
		* def authToken = response.token
		* print 'Token :: ' + authToken
	