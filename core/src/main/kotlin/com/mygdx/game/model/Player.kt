package com.mygdx.game.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle

class Player(sprite: Sprite, camera: Camera): DisplayModel<Batch>(Rectangle(0f, 0f, 16f, 16f)) {
    override var updateAction: ((DisplayModel<Batch>) -> Unit)? = {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            it.rect.x--
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            it.rect.x++
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            it.rect.y--
        } else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            it.rect.y++
        }
    }

    override var onAfterUpdateEvent: ((Batch, DisplayModel<Batch>) -> Unit)? = { batch, model ->
        sprite.x = model.positionOfWorld.x
        sprite.y = model.positionOfWorld.y

        camera.position.x = sprite.x
        camera.position.y = sprite.y
        sprite.draw(batch)
    }
}