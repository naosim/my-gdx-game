package com.mygdx.game.lib

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture

data class ContactContainer(val other: Fixture, val contact: Contact)

class ContactContainerList {
    var list = emptyArray<ContactContainer>()
    private var mTouchStatus: TouchStatus? = null
    var touchStatus: TouchStatus = TouchStatus(false, false, false, false)
    get() {
        if(mTouchStatus == null) {
            mTouchStatus = TouchStatus(
                    list.filter { it.contact.worldManifold.normal.y < -0.9 }.isNotEmpty(),
                    list.filter { it.contact.worldManifold.normal.x < -0.9 }.isNotEmpty(),
                    list.filter { it.contact.worldManifold.normal.y >  0.9 }.isNotEmpty(),
                    list.filter { it.contact.worldManifold.normal.x >  0.9 }.isNotEmpty()
            )
        }
        return mTouchStatus!!
    }
    fun add(c:ContactContainer) {
        list += c
    }
    fun clear() {
        list = emptyArray()
        mTouchStatus = null
    }


    fun isBottomTouched(): Boolean {
        return list.filter { it.contact.worldManifold.normal.y > 0.7 }.size >= 1
    }

}

class TouchStatus(val top:Boolean, val right:Boolean, val bottom:Boolean, val left:Boolean)
