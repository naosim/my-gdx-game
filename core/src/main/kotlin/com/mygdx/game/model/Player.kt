package com.mygdx.game.model

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite

class Player(val displayModel: DisplayModel<Batch>, val sprite: Sprite, val camera:OrthographicCamera) {
    fun update() {
        sprite.x = displayModel.positionOfWorld.x
        sprite.y = displayModel.positionOfWorld.y

        camera.position.x = sprite.x
        camera.position.y = sprite.y
    }
}