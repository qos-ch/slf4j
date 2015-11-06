package org.slf4j.ext.events.example2;

import org.slf4j.ext.events.Property;
import org.slf4j.ext.events.PropertyGroup;

/**
 * Created by himavija on 10/9/15.
 */
@PropertyGroup(name = "who")
public class Who {
    @Property(name = "id") private String id;
}
