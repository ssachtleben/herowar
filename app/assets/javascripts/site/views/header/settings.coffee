BaseView = require 'views/baseView'
templates = require 'templates'

class Settings extends BaseView

	entity: 'ui/me'
	
	template: templates.get 'header/settings.tmpl'

	bindEvents: ->
		@listenTo @model, 'change:isFetched change:isGuest change:isUser', @render
		$('body').on 'show.bs.dropdown', '.dropdown', -> $(this).find('.dropdown-menu').first().stop(true, true).fadeIn()
		$('body').on 'hide.bs.dropdown', '.dropdown', -> $(this).find('.dropdown-menu').first().stop(true, true).fadeOut()
		return

	unbindEvents: ->
		$('body').off 'show.bs.dropdown', '.dropdown'
		$('body').off 'hide.bs.dropdown', '.dropdown'
		return

	events:
		'click .login-link'	: 'toggleTooltip'

	afterRender: ($html) =>
		super $html
		return unless @model.get('isUser')
		return if @done
		@$('[data-hover="dropdown"]').dropdownHover()
		@done = true
		return

	toggleTooltip: (event) ->
		$Tooltip = $ '.login-tooltip'
		$Tooltip.toggleClass 'visible'
		$Input = $ '.login-form input[name="email"]'
		$Input.focus() if $Input?.length > 0

return Settings