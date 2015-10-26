TableView = require 'views/tableView'
app = require 'application'

class MapTable extends TableView

	entity: 'api/maps'
	
	tableEntity: 'db/maps'
	
	fields:
		'#'						: 'id'
		'Name' 				: 'name'
		'Description'	: 'description'
		'Team Size'		: 'teamSize'
		
	entryView: 'views/maps/tableEntry'

	createEntry: (event) ->
		event?.preventDefault()
		app.navigate "admin/map/new", true 

return MapTable