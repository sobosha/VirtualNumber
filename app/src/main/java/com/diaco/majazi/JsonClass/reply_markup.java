package com.diaco.majazi.JsonClass;

import java.util.List;

public class reply_markup {
    List<keyboard> keyboard;
    boolean resize_keyboard;

    public boolean isResize_keyboard() {
        return resize_keyboard;
    }

    public void setResize_keyboard(boolean resize_keyboard) {
        this.resize_keyboard = resize_keyboard;
    }

    public List<com.diaco.majazi.JsonClass.keyboard> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(List<com.diaco.majazi.JsonClass.keyboard> keyboard) {
        this.keyboard = keyboard;
    }
}
