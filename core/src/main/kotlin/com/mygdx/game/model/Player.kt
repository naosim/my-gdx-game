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
import com.mygdx.game.lib.ContactContainer
import com.mygdx.game.lib.ContactContainerList
import com.mygdx.game.setup

class Player(
        val sprite: Sprite,
        camera: Camera,
        world: World): DisplayModel<Batch>(Rectangle(0f, 0f, 16f, 16f)) {
    val body = setup(createPlayerBody(createRect(sprite), world), {
        it.userData = this
    })
    var contactContainerList = ContactContainerList()

    var isAfterAction = false

    override var updateAction: ((DisplayModel<Batch>) -> Unit)? = {
        if(body.world.contactCount > 0) {
            body.world.contactList
                .filter { it.fixtureA.body == body || it.fixtureB.body == body }
                .forEach {
                    contactContainerList.add(ContactContainer(if(it.fixtureA.body == this.body) it.fixtureB else it.fixtureA, it))
                }
        }

        if(isAfterAction) {
            isAfterAction = false
        } else {
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                body.setLinearVelocity(-40f, body.linearVelocity.y)
            } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                body.setLinearVelocity(40f, body.linearVelocity.y)
            }

//            println("${contactContainerList.touchStatus.left}, ${contactContainerList.touchStatus.right}")
            println("${contactContainerList.touchStatus.bottom}")

            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if(contactContainerList.touchStatus.bottom) {
                    isAfterAction = true
                    body.setLinearVelocity(body.linearVelocity.x, 100f)
                } else {
                    if(contactContainerList.touchStatus.right) {
                        isAfterAction = true
                        body.setLinearVelocity(-40f, 100f)
                    }else if(contactContainerList.touchStatus.left) {
                        isAfterAction = true
                        body.setLinearVelocity(40f, 100f)
                    }
                }
            } else {
                if(contactContainerList.touchStatus.bottom) {
                    if(contactContainerList.touchStatus.right) {
                        println("hit")
                        isAfterAction = true
                        body.setLinearVelocity(-40f, body.linearVelocity.y)
                    }else if(contactContainerList.touchStatus.left) {
                        println("hit")
                        isAfterAction = true
                        body.setLinearVelocity(40f, body.linearVelocity.y)
                    }
                }
            }
        }





        sprite.x = body.position.x
        sprite.y = body.position.y

        contactContainerList.clear()
    }

    var contactSet = emptySet<ContactContainer>()

    fun onBeginContact(c:ContactContainer) {
        contactSet = contactSet.plus(c)
    }

    fun onEndContact(c:ContactContainer) {
        contactSet = contactSet.minus(c)
    }

    override var onAfterUpdateEvent: ((Batch, DisplayModel<Batch>) -> Unit)? = { batch, model ->
//        sprite.x = model.positionOfWorld.x
//        sprite.y = model.positionOfWorld.y


        sprite.draw(batch)
        camera.position.x = sprite.x
        camera.position.y = sprite.y
    }

}