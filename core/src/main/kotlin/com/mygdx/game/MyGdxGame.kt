package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.math.Rectangle
import com.mygdx.game.model.DisplayModel

class MyAssets {
    val texture = Texture("map2.png")
    val textureRegionList = (0 until 4).map{TextureRegion(texture, 16 * (it), 0, 16, 16)}.toTypedArray()
}


class MyGdxGame : ApplicationAdapter() {
    internal lateinit var batch: SpriteBatch
    lateinit var sprite: Sprite
    lateinit var s: Sprite

    lateinit var renderer: OrthogonalTiledMapRenderer

    lateinit private var  camera: OrthographicCamera

    lateinit var fieldModel: DisplayModel
//    lateinit var player: Player

    override fun create() {
        batch = SpriteBatch()
        val hoge = Hoge(3)
//        font = BitmapFont()
        println(Gdx.graphics.getWidth())
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera = OrthographicCamera()
        camera.zoom = 0.5f
        camera.setToOrtho(false, w * camera.zoom, h * camera.zoom)
        camera.update()

        val myAssets = MyAssets()

        val layer = setup(TiledMapTileLayer(160, 160, 16, 16), {
            for(x in 0 until 160) {
                val cell = TiledMapTileLayer.Cell()
                cell.tile = StaticTiledMapTile(myAssets.textureRegionList[x % 4])
                for(y in 0 until 160) {
                    it.setCell(x, y, cell)
                }
            }
        })

        var tiledMap = setup(TiledMap(), { it.layers.add(layer) })

        renderer = OrthogonalTiledMapRenderer(tiledMap)

        sprite = Sprite(myAssets.texture, 16, 16)

        fieldModel = DisplayModel(Rectangle(0f, 0f, 16*160f, 16*160f), {}, {
            renderer.setView(camera)
            renderer.render()
        })

        var playerModel = DisplayModel(
                Rectangle(0f, 0f, 16f, 16f),
                { it.rect.x++ },
                {
                    sprite.x = it.positionOfWorld.x
                    sprite.y = it.positionOfWorld.y

                    camera.position.x = sprite.x
                    camera.position.y = sprite.y


                    sprite.draw(batch)
                }
        )

        fieldModel.children.add(playerModel)
    }

    override fun render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // upate
        fieldModel.update()

//        player.update()

        camera.update()

        batch.setProjectionMatrix(camera.combined)
        batch.begin()

        fieldModel.notifyAfterUpdate()

        batch.end()
    }

    override fun dispose() {
        super.dispose()
    }
}

fun <T> setup (t:T, c:(T)-> Unit): T {
    c.invoke(t)
    return t
}
