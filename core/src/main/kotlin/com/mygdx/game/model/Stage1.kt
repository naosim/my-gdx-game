package com.mygdx.game.model

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World
import com.mygdx.game.MyAssets
import com.mygdx.game.createStaticBody
import com.mygdx.game.setup

class Stage1(
        val camera: OrthographicCamera,
        val world: World
):DisplayModel<Batch>(Rectangle(0f, 0f, 16*160f, 16*160f)) {
    val renderer: OrthogonalTiledMapRenderer
    init {
        val myAssets = MyAssets()
        val layer = setup(TiledMapTileLayer(160, 160, 16, 16), {
            var y = 0
            val cells = arrayOf(
                    setup(TiledMapTileLayer.Cell(), { it.tile = StaticTiledMapTile(myAssets.textureRegionList[0]) }),
                    setup(TiledMapTileLayer.Cell(), { it.tile = StaticTiledMapTile(myAssets.textureRegionList[1]) })
            )
            arrayOf(
                    arrayOf(0, 0, 0, 0, 0),
                    arrayOf(1, 0, 0, 0, 1),
                    arrayOf(1, 0, 0, 0, 1),
                    arrayOf(1, 0, 0, 0, 1),
                    arrayOf(1, 1, 1, 1, 1)
            ).reversedArray().forEach { row ->
                var x = 0
                row.forEach { num ->
                    if(num != 0) {
                        it.setCell(x, y, cells[num])
                        createStaticBody(Rectangle(x * 16f, y * 16f, 16f, 16f), world)
                    }
                    x++
                }
                y++
            }
        })
        var a = layer.getCell(1, 1)

        var objs = layer.objects.count
        println("a " + a)
//        println("hoge " + objs[20])

        var tiledMap = setup(TiledMap(), { it.layers.add(layer) })

        renderer = OrthogonalTiledMapRenderer(tiledMap)
    }
    override var updateAction: ((DisplayModel<Batch>) -> Unit)? = {

    }

    override var onAfterUpdateEvent: ((Batch, DisplayModel<Batch>) -> Unit)? = {batch, model ->
        var f = false
        if(batch.isDrawing) {
            batch.end()
            f = true
        }
        renderer.setView(camera)
        renderer.render()

        if(f) {
            batch.begin()
        }
    }

}