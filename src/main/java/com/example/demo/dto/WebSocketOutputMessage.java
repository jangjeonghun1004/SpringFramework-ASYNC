package com.example.demo.dto;

public class WebSocketOutputMessage {
    private String from;
    private String text;
    private long time;

    public WebSocketOutputMessage(String from, String text, long time) {
        this.from = from;
        this.text = text;
        this.time = time;
    }

    // Getters and Setters
    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }
}
