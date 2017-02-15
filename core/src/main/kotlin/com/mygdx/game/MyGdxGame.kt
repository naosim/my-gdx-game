package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MyGdxGame : ApplicationAdapter() {
    internal lateinit var batch: SpriteBatch
    internal lateinit var img: Texture
    internal lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
        val hoge = Hoge(3)
        font = BitmapFont()
        println(Gdx.graphics.getWidth())

    }

    override fun render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(img, 0f, 0f)
        font.draw(batch, "Hello", 100f, 100f)
        batch.end()
    }

    override fun dispose() {
        super.dispose()
    }
}
