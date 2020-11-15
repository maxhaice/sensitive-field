package com.hub.sensitivefield.model;

/*it's not never used, because its only to save this value to db. This kinds of events contains this values in entities
    and by name we can find it, intellij mistake ^^*/
public enum PriorityEvent {
    SuperDangerous,
    Dangerous,
    Warn,
    Default
}