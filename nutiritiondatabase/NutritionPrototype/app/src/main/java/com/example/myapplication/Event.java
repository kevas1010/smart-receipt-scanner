package com.example.myapplication;

public class Event {
    private int eventID;
    private String eventName;
    private String eventDate;
    private String exertionLevel;
    private int lengthOfEvent;
    private String description;

    // Constructor
    public Event(int eventID, String eventName, String eventDate, String exertionLevel, int lengthOfEvent, String description) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.exertionLevel = exertionLevel;
        this.lengthOfEvent = lengthOfEvent;
        this.description = description;
    }

    // Getters and setters
    public int getEventID() { return eventID; }
    public void setEventID(int eventID) { this.eventID = eventID; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public String getExertionLevel() { return exertionLevel; }
    public void setExertionLevel(String exertionLevel) { this.exertionLevel = exertionLevel; }

    public int getLengthOfEvent() { return lengthOfEvent; }
    public void setLengthOfEvent(int lengthOfEvent) { this.lengthOfEvent = lengthOfEvent; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
