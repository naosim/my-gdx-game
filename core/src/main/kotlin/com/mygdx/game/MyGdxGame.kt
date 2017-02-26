package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.model.Player
import com.mygdx.game.model.Stage1

class MyAssets {
    val texture = Texture("map2.png")
    val textureRegionList = (0 until 4).map{TextureRegion(texture, 16 * (it), 0, 16, 16)}.toTypedArray()
}


class MyGdxGame : ApplicationAdapter() {
//    internal lateinit var batch: SpriteBatch
    lateinit var sprite: Sprite
    lateinit var s: Sprite

    lateinit var stage1: Stage1

    lateinit private var  camera: OrthographicCamera

//    lateinit var fieldModel: DisplayModel<Batch>
//    lateinit var player: Player

    override fun create() {
        println(Gdx.graphics.getWidth())
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera = OrthographicCamera()
        camera.zoom = 0.5f
        camera.setToOrtho(false, w * camera.zoom, h * camera.zoom)
        camera.update()

        val myAssets = MyAssets()

        stage1 = Stage1(camera)

        sprite = Sprite(myAssets.texture, 16, 16)
        var playerModel = Player(sprite, camera)
        stage1.children.add(playerModel)
    }


    override fun render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // upate
        stage1.update()
        camera.update()

        stage1.renderer.batch.setProjectionMatrix(camera.combined)
        stage1.renderer.batch.begin()
        stage1.notifyAfterUpdate(stage1.renderer.batch)
        stage1.renderer.batch.end()
    }

    override fun dispose() {
        super.dispose()
    }
}

fun <T> setup (t:T, c:(T)-> Unit): T {
    c.invoke(t)
    return t
}
