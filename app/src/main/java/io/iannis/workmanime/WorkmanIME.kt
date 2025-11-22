package io.iannis.workmanime

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent

class WorkmanIME : InputMethodService() {
    private val remap = mapOf(
        KeyEvent.KEYCODE_Q to KeyEvent.KEYCODE_Q,
        KeyEvent.KEYCODE_W to KeyEvent.KEYCODE_D,
        KeyEvent.KEYCODE_E to KeyEvent.KEYCODE_R,
        KeyEvent.KEYCODE_R to KeyEvent.KEYCODE_W,
        KeyEvent.KEYCODE_T to KeyEvent.KEYCODE_B,
        KeyEvent.KEYCODE_Y to KeyEvent.KEYCODE_J,
        KeyEvent.KEYCODE_U to KeyEvent.KEYCODE_F,
        KeyEvent.KEYCODE_I to KeyEvent.KEYCODE_U,
        KeyEvent.KEYCODE_O to KeyEvent.KEYCODE_P,
        KeyEvent.KEYCODE_P to KeyEvent.KEYCODE_SEMICOLON,
        KeyEvent.KEYCODE_A to KeyEvent.KEYCODE_A,
        KeyEvent.KEYCODE_S to KeyEvent.KEYCODE_S,
        KeyEvent.KEYCODE_D to KeyEvent.KEYCODE_H,
        KeyEvent.KEYCODE_F to KeyEvent.KEYCODE_T,
        KeyEvent.KEYCODE_G to KeyEvent.KEYCODE_G,
        KeyEvent.KEYCODE_H to KeyEvent.KEYCODE_Y,
        KeyEvent.KEYCODE_J to KeyEvent.KEYCODE_N,
        KeyEvent.KEYCODE_K to KeyEvent.KEYCODE_E,
        KeyEvent.KEYCODE_L to KeyEvent.KEYCODE_O,
        KeyEvent.KEYCODE_SEMICOLON to KeyEvent.KEYCODE_I,
        KeyEvent.KEYCODE_Z to KeyEvent.KEYCODE_Z,
        KeyEvent.KEYCODE_X to KeyEvent.KEYCODE_X,
        KeyEvent.KEYCODE_C to KeyEvent.KEYCODE_M,
        KeyEvent.KEYCODE_V to KeyEvent.KEYCODE_C,
        KeyEvent.KEYCODE_B to KeyEvent.KEYCODE_V,
        KeyEvent.KEYCODE_N to KeyEvent.KEYCODE_K,
        KeyEvent.KEYCODE_M to KeyEvent.KEYCODE_L,
        KeyEvent.KEYCODE_CAPS_LOCK to KeyEvent.KEYCODE_DEL,
        KeyEvent.KEYCODE_1 to KeyEvent.KEYCODE_1,
        KeyEvent.KEYCODE_2 to KeyEvent.KEYCODE_2,
        KeyEvent.KEYCODE_3 to KeyEvent.KEYCODE_3,
        KeyEvent.KEYCODE_4 to KeyEvent.KEYCODE_4,
        KeyEvent.KEYCODE_5 to KeyEvent.KEYCODE_5,
        KeyEvent.KEYCODE_6 to KeyEvent.KEYCODE_6,
        KeyEvent.KEYCODE_7 to KeyEvent.KEYCODE_7,
        KeyEvent.KEYCODE_8 to KeyEvent.KEYCODE_8,
        KeyEvent.KEYCODE_9 to KeyEvent.KEYCODE_9,
        KeyEvent.KEYCODE_0 to KeyEvent.KEYCODE_0,
    )

    fun handleKeyEvent(keyCode: Int, event: KeyEvent?): Boolean {
        val ic = currentInputConnection
        val remappedKeyCode = remap[keyCode]
        if (event == null || remappedKeyCode == null) {
            return false
        }

        // Caps lock -> backspace, and we need to ignore caps lock state.
        // Unfortunately, keyboards with a caps lock LED will still toggle the LED
        // when caps lock is pressed. I don't think there is a workaround for this.
        var meta = event.metaState and KeyEvent.META_CAPS_LOCK_ON.inv()

        // Number row is inverted.
        if (remappedKeyCode in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9) {
            meta = if (meta and KeyEvent.META_SHIFT_ON != 0) {
                // Shift is pressed. We should send numbers.
                // Turns off META_SHIFT_ON, META_SHIFT_LEFT_ON, and META_SHIFT_RIGHT_ON.
                meta and KeyEvent.META_SHIFT_MASK.inv()
            } else {
                // Shift is not pressed. We should send symbols.
                // Turns on META_SHIFT_ON, META_SHIFT_LEFT_ON, and META_SHIFT_RIGHT_ON.
                // Essentially simulates both shift keys being pressed.
                meta or KeyEvent.META_SHIFT_MASK
            }
        }

        ic.sendKeyEvent(
            KeyEvent(
                event.downTime,
                event.eventTime,
                event.action,
                remappedKeyCode,
                event.repeatCount,
                meta,
                event.deviceId,
                event.scanCode,
                event.flags,
                event.source,
            )
        )
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return handleKeyEvent(keyCode, event) || super.onKeyUp(keyCode, event)
    }

    override fun onCreateInputView() = null
}