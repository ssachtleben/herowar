BaseTool = require 'tools/baseTool'
scenegraph = require 'scenegraph'
db = require 'database'

class SelectOnTerrain extends BaseTool

	constructor: (@intersectHelper) ->
		@initialize()

	initialize: ->
		@radius = 0.5
		@bindEvents()
		return

	bindEvents: ->
		return

	onIntersect: ->
		return

	onNonIntersect: ->
		return

	update: (position, intersect) ->
		return

	onMouseMove: (event) =>
		radius = @radius
		if radius > 1
			radius /=2
			radius += 0.33
		intersectList = @intersectHelper.mouseIntersects [ scenegraph.getMap().getMainObject() ], radius
		if intersectList.length > 0
			normalMatrix = new THREE.Matrix3()
			@lastIntersect = @intersectHelper.getIntersectObject intersectList
			if @lastIntersect.face?
				normalMatrix.getInverse @lastIntersect.object.matrixWorld
				normal = @lastIntersect.face.normal.clone()
				normal.applyMatrix3(normalMatrix).normalize()
				@lastPosition = new THREE.Vector3().addVectors @lastIntersect.point, normal
			@onIntersect()
			@update @lastPosition, @lastIntersect
		else
			@onNonIntersect()
		return

return SelectOnTerrain