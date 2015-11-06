package org.slf4j.ext.events.example2;

import org.slf4j.ext.events.EventDetails;
import org.slf4j.ext.events.PropertyGroup;

/**
 * Created by himavija on 10/9/15.
 */
@EventDetails
public class Event {
    @PropertyGroup
    private Who who;
}
