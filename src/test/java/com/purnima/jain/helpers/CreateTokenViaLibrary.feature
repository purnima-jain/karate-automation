Feature: Create Token via Library

	Scenario: Create Token via Library
		* def tokenGenerator = Java.type('com.purnima.jain.helpers.TokenGenerator')
		# Fetch Token
		* print 'Username:' + username
		* print 'Password:' + password
		* def authToken = tokenGenerator.generateToken(username, password);
		* print 'Token:' + authToken