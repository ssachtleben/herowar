EditorEventbus = require 'editorEventbus'

class ObjectHelper
	
	constructor: ->
		EditorEventbus.resetWireframe.add @refreshWireframe

	hasWireframe: (obj) ->
		found = false
		for mesh in obj.children
			found = true if mesh.name is 'wireframe'
		found

	addWireframe: (obj, color) ->
		#TODO hard coded children access ...
		if obj
			wireframe = new THREE.WireframeHelper obj.children[0], color
			wireframe.userData.parentId = obj.children[0].id
			wireframe.name = "wireframe"
			obj.add wireframe

	removeWireframe: (obj) ->
		mesh = @getWireframe(obj)
		if mesh
			mesh.geometry.dispose()
			obj.remove mesh
	
	changeWireframeColor: (obj, color) ->
		if obj
			for mesh in obj.children
				if mesh.name is 'wireframe'
					mesh.material.color.set color
					mesh.material.needsUpdate = true

	getBaseObject: (obj) ->	
		unless obj then return
		obj = obj.parent while not _.isUndefined(obj.parent) and not (obj.parent instanceof THREE.Scene)
		obj
	
	getWireframe: (obj) ->
		if obj
			if obj.name is 'wireframe'
				mesh = obj
			else
				for child in obj.children
					if child.name is 'wireframe'
						mesh = child
						break
		mesh
	
	getModel: (obj) ->
		if obj
			for child in obj.children
					if child.name isnt 'wireframe'
						mesh = child
						break
			unless mesh
				mesh = obj
		mesh
	
	isTerrain: (obj) ->
		scenegraph = require 'scenegraph'
		if _.isUndefined obj or _.isUndefined scenegraph.getMap().getMainObject() then return false
		obj = @getBaseObject obj unless obj.parent instanceof THREE.Scene
		scenegraph.getMap().getMainObject().id is obj.id

#For updating all geometry data ...
	refreshWireframe: (obj) =>
		mesh = @getWireframe(obj)
		if mesh
			source = obj.getObjectById mesh.userData.parentId
			mesh.geometry.fromGeometry source.geometry

return ObjectHelper