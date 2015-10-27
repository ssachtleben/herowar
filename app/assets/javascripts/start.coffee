(->
  ###
      Initialize our application on document ready.
  ###
  $ ->
    app = require 'application'
    app.start()
    console.log 'Application started'

).call this