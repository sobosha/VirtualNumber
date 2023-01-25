package com.diaco.majazi.JsonClass;

public class message {
    String message_id;
    String text;
    chat chat;
    from from;
    callback_query callback_query;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public com.diaco.majazi.JsonClass.chat getChat() {
        return chat;
    }

    public void setChat(com.diaco.majazi.JsonClass.chat chat) {
        this.chat = chat;
    }

    public com.diaco.majazi.JsonClass.from getFrom() {
        return from;
    }

    public void setFrom(com.diaco.majazi.JsonClass.from from) {
        this.from = from;
    }

    public com.diaco.majazi.JsonClass.callback_query getCallback_query() {
        return callback_query;
    }

    public void setCallback_query(com.diaco.majazi.JsonClass.callback_query callback_query) {
        this.callback_query = callback_query;
    }


}
