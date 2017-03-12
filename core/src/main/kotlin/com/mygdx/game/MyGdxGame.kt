package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
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
    lateinit private var playerModel:Player

    lateinit private var  camera: OrthographicCamera

    lateinit private var  world: World
//    lateinit private var  body: Body



//    lateinit var fieldModel: DisplayModel<Batch>
//    lateinit var player: Player

    lateinit private var staticBox: Body

    override fun create() {
        println(Gdx.graphics.getWidth())
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()


        camera = OrthographicCamera()
        camera.zoom = 0.5f
        camera.setToOrtho(false, w * camera.zoom, h * camera.zoom)
        camera.update()

        val myAssets = MyAssets()
        world = World(Vector2(0f, -98f), true)

        stage1 = Stage1(camera, world)

        sprite = setup(Sprite(myAssets.texture, 16, 16), {
            it.x = 16f
            it.y = 64f
        })
        playerModel = Player(sprite, camera, world)
        stage1.children.add(playerModel)



//        body = createPlayerBody(createRect(sprite), world)
//        staticBox = createStaticBody(Rectangle(32f, 0f, 16f, 16f), world)

    }








    override fun render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // upate
//        world.step(Gdx.graphics.getDeltaTime(), 6, 2)
        world.step(1/60f, 6, 2)


        stage1.update()


        stage1.renderer.batch.setProjectionMatrix(camera.combined)
        stage1.renderer.batch.begin()
        stage1.notifyAfterUpdate(stage1.renderer.batch)
        stage1.renderer.batch.end()

        camera.update()

    }

    override fun dispose() {
        super.dispose()
    }
}

fun <T> setup (t:T, c:(T)-> Unit): T {
    c.invoke(t)
    return t
}

fun createPlayerBody(rect:Rectangle, world: World): Body{
    val bodyDef = BodyDef()
    bodyDef.type = BodyDef.BodyType.DynamicBody
    bodyDef.position.set(rect.x, rect.y)
    bodyDef.fixedRotation = true

    // Create a body in the world using our definition
    val result = world.createBody(bodyDef)

    // Now define the dimensions of the physics shape
    val shape = PolygonShape()
    shape.setAsBox(rect.width/2, rect.height/2)

    val fixtureDef = FixtureDef()
    fixtureDef.shape = shape
//    fixtureDef.density = 1f
    fixtureDef.friction = 0f
    fixtureDef.restitution = 0f

    result.createFixture(fixtureDef)

    // Shape is the only disposable of the lot, so get rid of it
    shape.dispose()

    return result
}

fun createStaticBody(rect:Rectangle, world: World): Body{
    val bodyDef = BodyDef()
    bodyDef.type = BodyDef.BodyType.StaticBody
    bodyDef.position.set(rect.x, rect.y)
    bodyDef.fixedRotation = true


    // Create a body in the world using our definition
    val result = world.createBody(bodyDef)

    // Now define the dimensions of the physics shape
    val shape = PolygonShape()
    shape.setAsBox(rect.width/2, rect.height/2)

    val fixtureDef = FixtureDef()
    fixtureDef.shape = shape
//    fixtureDef.density = 1f
    fixtureDef.friction = 0f
    fixtureDef.restitution = 0f

    result.createFixture(fixtureDef)

    // Shape is the only disposable of the lot, so get rid of it
    shape.dispose()

    return result
}

fun createRect(sprite:Sprite) = Rectangle(sprite.x, sprite.y, sprite.width, sprite.height)