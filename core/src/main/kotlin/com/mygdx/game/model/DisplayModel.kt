package com.mygdx.game.model

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/**
 * rect: position from parent
 */
class DisplayModel<T>(
        var rect:Rectangle,
        var updateAction:((v: DisplayModel<T>)->Unit)? = null,
        var onAfterUpdateEvent:((t:T, v: DisplayModel<T>)->Unit)? = null
) {
    var parent: DisplayModel<T>? = null
    val position: Vector2
        get() = Vector2(rect.x, rect.y)
    val positionOfWorld: Vector2
        get() {
            val p = parent?.let { it.positionOfWorld } ?: Vector2(0f, 0f)
            return p.add(position.x, position.y)
        }
    val children:MutableList<DisplayModel<T>> = mutableListOf()



    fun remove() {
        parent?.let { it.removeChild(this) }
    }

    fun removeChild(c:DisplayModel<T>) {
        children.remove(c)
    }

    fun update(){
        updateAction?.let{ it.invoke(this) }
        children.forEach { it.update() }
    }

    fun notifyAfterUpdate(t: T){
        onAfterUpdateEvent?.let { it.invoke(t, this) }
        children.forEach { it.notifyAfterUpdate(t) }
    }
}


interface DisplayModelListener {
    fun update()
}
