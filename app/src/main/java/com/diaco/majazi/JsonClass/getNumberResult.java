package com.diaco.majazi.JsonClass;

public class getNumberResult {
    String chat_id;
    String text;
    String reply_to_message_id;
    reply_markup reply_markup;


    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReply_to_message_id() {
        return reply_to_message_id;
    }

    public void setReply_to_message_id(String reply_to_message_id) {
        this.reply_to_message_id = reply_to_message_id;
    }

    public com.diaco.majazi.JsonClass.reply_markup getReply_markup() {
        return reply_markup;
    }

    public void setReply_markup(com.diaco.majazi.JsonClass.reply_markup reply_markup) {
        this.reply_markup = reply_markup;
    }
}
