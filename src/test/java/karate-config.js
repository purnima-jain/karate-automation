function fn() {

	var env = karate.env; // get system property 'karate.env'
	karate.log('System Property karate.env is ', env);	
	if(!env) {
		env = 'dev';
	}
	karate.log('Environment is ', env);	

	var config = {
		apiCustomerBaseUrl: 'http://localhost:8080/api/v1/customers',
		apiBookBaseUrl: 'http://localhost:8080/api/v1/books',
		apiTokenBaseUrl: 'http://localhost:8080/api/v1/token'
	}
	
	if(env == 'dev') {
		config.username = 'username_1'
		config.password = 'password_1'
	} else if(env == 'test') {
		config.username = 'test_username_1'
		config.password = 'test_password_1'
	} else if(env == 'syst') {
		config.username = 'syst_username_1'
		config.password = 'syst_password_1'
	}
	
	config.token = karate.callSingle('classpath:com/purnima/jain/helpers/CreateToken.feature', config).authToken
	
	return config;
}