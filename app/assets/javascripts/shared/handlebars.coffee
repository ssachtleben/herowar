Handlebars.registerHelper 'prettifyDate', (timestamp, pattern) ->
  return new Date(timestamp).toUTCString()

return {}
