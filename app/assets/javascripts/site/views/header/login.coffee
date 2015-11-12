FormView = require 'views/formView'
templates = require 'templates'
app = require 'application'

###
    The Login shows our login part in home view.

    @author Sebastian Sachtleben
###
class Login extends FormView

	entity: 'ui/me'
	
	template: templates.get 'header/login.tmpl'

	url: '/login/email/auth'
	
	validateForm: ($Form) ->
		isValid = true
		inputEmail = $Form.find 'input[name="email"]'
		if inputEmail.val().length <= 4
			@setInputState inputEmail, 'error'
			$.gritter.add title: 'Login failed', text: 'The email doesn\'t exists.'
			isValid = false
		else
			@setInputState inputEmail, ''
		passwordUsername = $Form.find 'input[name="password"]'
		if passwordUsername.val().length <= 4
			@setInputState passwordUsername, 'error'
			if isValid
				$.gritter.add title: 'Login failed', text: 'The password doesn\'t match.'
			isValid = false
		else
			@setInputState passwordUsername, ''
		isValid
	
	onSuccess: (data, textStatus, jqXHR) ->
		# TODO: this method is never called ?!?
		@model.set data
		@model.validateResponse(data)
		app.navigate 'play', true
			
	onError: (jqXHR, textStatus, errorThrown) ->
		# TODO: why is successful login goes to error?!?!
		return window.location.reload() if jqXHR.status is 200
		$.gritter.add title: 'Login failed', text: 'Email or password is incorrect'
	
return Login