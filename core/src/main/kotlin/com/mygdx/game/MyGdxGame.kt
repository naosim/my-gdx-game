package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile

class MyGdxGame : ApplicationAdapter() {
    internal lateinit var batch: SpriteBatch
//    internal lateinit var font: BitmapFont
    lateinit var group: DrawWrapperGroup
    lateinit var sprite: DrawWrapperSprite
    lateinit var s: Sprite

    lateinit var renderer: OrthogonalTiledMapRenderer

    lateinit private var  camera: OrthographicCamera

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



        val layer = setup(TiledMapTileLayer(160, 160, 16, 16), {
            var texture = Texture("map2.png")
            var textureRegionList = arrayOf(
                    TextureRegion(texture, 16 * (0), 0, 16, 16),
                    TextureRegion(texture, 16 * (1), 0, 16, 16),
                    TextureRegion(texture, 16 * (2), 0, 16, 16),
                    TextureRegion(texture, 16 * (3), 0, 16, 16)
            )
            for(x in 0 until 160) {
                val cell = TiledMapTileLayer.Cell()
                cell.tile = StaticTiledMapTile(textureRegionList[x % 4])
                for(y in 0 until 160) {
                    it.setCell(x, y, cell)
                }
            }
        })
        renderer = OrthogonalTiledMapRenderer(setup(TiledMap(), { it.layers.add(layer) }));



        sprite = DrawWrapperSprite(Sprite(Texture("map2.png"), 16, 16))

        group = DrawWrapperGroup()
        group.add(sprite)



    }

    override fun render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        sprite.sprite.x++

        camera.update();
        renderer.setView(camera);
        renderer.render();



//        batch.begin()

        //group.draw(batch)
//        batch.end()
    }

    override fun dispose() {
        super.dispose()
    }
}

class DrawWrapperGroup: DrawWrapper {
    val list: MutableList<DrawWrapper> = mutableListOf()
    fun add(wrapper: DrawWrapper) {
        list.add(wrapper)
    }

    override fun draw(batch: Batch) {
        list.forEach { it.draw(batch) }
    }
}

class DrawWrapperSprite(val sprite: Sprite): DrawWrapper {
    override fun draw(batch: Batch) {
        sprite.draw(batch)
    }

}

interface DrawWrapper {
    fun draw(batch: Batch)
}


fun <T> setup (t:T, c:(T)-> Unit): T {
    c.invoke(t)
    return t
}