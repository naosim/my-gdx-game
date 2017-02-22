package com.mygdx.game.model

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/**
 * rect: position from parent
 */
class DisplayModel(
        var rect:Rectangle,
        var updateAction:((v: DisplayModel)->Unit)? = null,
        var onAfterUpdateEvent:((v: DisplayModel)->Unit)? = null
) {
    var parent: DisplayModel? = null
    val position: Vector2
        get() = Vector2(rect.x, rect.y)
    val positionOfWorld: Vector2
        get() {
            val p = parent?.let { it.positionOfWorld } ?: Vector2(0f, 0f)
            return p.add(position.x, position.y)
        }
    val children:MutableList<DisplayModel> = mutableListOf()



    fun remove() {
        parent?.let { it.removeChild(this) }
    }

    fun removeChild(c:DisplayModel) {
        children.remove(c)
    }

    fun update(){
        updateAction?.let{ it.invoke(this) }
        children.forEach { it.update() }
    }

    fun notifyAfterUpdate(){
        onAfterUpdateEvent?.let { it.invoke(this) }
        children.forEach { it.notifyAfterUpdate() }
    }
}


interface DisplayModelListener {
    fun update()
}
