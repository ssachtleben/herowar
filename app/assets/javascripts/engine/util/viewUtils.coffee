_projector = new THREE.Projector()

viewUtils = 

	positionToScreen: (position, viewportWidthHalf, viewportHeightHalf, camera) ->
		position.project camera
		return x: Math.round((position.x * viewportWidthHalf) + viewportWidthHalf), y: Math.round(- (position.y * viewportHeightHalf) + viewportHeightHalf)

return viewUtils
