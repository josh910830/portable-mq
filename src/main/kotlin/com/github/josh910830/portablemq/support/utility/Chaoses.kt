package com.github.josh910830.portablemq.support.utility

class Chaoses {

    companion object {
        fun toggle(initError: Boolean) = Toggle(initError)
        fun random(errorPercent: Int) = Random(errorPercent)
    }


    interface Switch {
        fun push()
    }

    class Toggle(initError: Boolean) : Switch {
        private var toggle = initError
        override fun push() {
            val current = toggle
            toggle = !toggle
            if (current) throw RuntimeException("ERROR")
        }
    }

    class Random(private val errorPercent: Int) : Switch {
        override fun push() {
            val value = Math.random() * 100
            val success = value > errorPercent
            if (!success) throw RuntimeException("ERROR")
        }
    }

}
