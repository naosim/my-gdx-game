package com.mygdx.game.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World
import com.mygdx.game.createPlayerBody
import com.mygdx.game.createRect

class Player(
        val sprite: Sprite,
        camera: Camera,
        world: World): DisplayModel<Batch>(Rectangle(0f, 0f, 16f, 16f)) {
    val body = createPlayerBody(createRect(sprite), world)

    override var updateAction: ((DisplayModel<Batch>) -> Unit)? = {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            body.position.x--
//            body.linearVelocity.x = -40f
            body.setLinearVelocity(-40f, body.linearVelocity.y)
//            body.setTransform(body.position.x - 1, body.position.y, body.angle)
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.setLinearVelocity(40f, body.linearVelocity.y)
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            body.position.y--
        } else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            body.setLinearVelocity(body.linearVelocity.x, 100f)
        }

        sprite.x = body.position.x
        sprite.y = body.position.y
    }

    override var onAfterUpdateEvent: ((Batch, DisplayModel<Batch>) -> Unit)? = { batch, model ->
//        sprite.x = model.positionOfWorld.x
//        sprite.y = model.positionOfWorld.y


        sprite.draw(batch)
        camera.position.x = sprite.x
        camera.position.y = sprite.y
    }
}