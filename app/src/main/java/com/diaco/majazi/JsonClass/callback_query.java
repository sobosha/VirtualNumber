package com.diaco.majazi.JsonClass;

import java.util.List;

public class callback_query {
    String id;
    String data;
    List<id> from;
    messagechild message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<com.diaco.majazi.JsonClass.id> getFrom() {
        return from;
    }

    public void setFrom(List<com.diaco.majazi.JsonClass.id> from) {
        this.from = from;
    }

    public messagechild getMessage() {
        return message;
    }

    public void setMessage(messagechild message) {
        this.message = message;
    }
}
