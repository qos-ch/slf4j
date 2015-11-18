package org.slf4j.ext.mdc.example2;

import org.slf4j.ext.mdc.Property;
import org.slf4j.ext.mdc.Pojo;

/**
 * Created by himavija on 10/9/15.
 */
@Pojo(name = "who")
public class Who {
    @Property(name = "id") private String id;
}
