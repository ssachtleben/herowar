BaseView = require 'views/baseView'
templates = require 'templates'

class Settings extends BaseView

	entity: 'ui/me'
	
	template: templates.get 'header/settings.tmpl'
	
	bindEvents: ->
		@listenTo @model, 'change:isFetched change:isGuest change:isUser', @render
	
	events:
		'click .logout-link'	    : 'logout'
		'click .login-link'		    : 'toggleTooltip'
		
	logout: (event) ->
		if event
			event.preventDefault()
			$CurrentTarget = $ event.currentTarget
			$CurrentTarget.addClass 'disabled'
		$.ajax
			type: 'GET'
			url: '/logout'
			success: (data, textStatus, jqXHR) =>
				console.log 'Reset model'
				@model.reset()

	toggleTooltip: (event) ->
		$Tooltip = $ '.login-tooltip'
		$Tooltip.toggleClass 'visible'
		$Input = $ '.login-form input[name="email"]'
		$Input.focus() if $Input?.length > 0

return Settings