package org.slf4j;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by slim on 4/22/14.
 */
public interface IMarkerFactory extends Function<String, Marker> , Predicate<String> {
    /**
     * Manufacture a {@link Marker} instance by name. If the instance has been
     * created earlier, return the previously created instance.
     *
     * <p>Null name values are not allowed.
     *
     * @param name the name of the marker to be created, null value is
     * not allowed.
     *
     * @return a Marker instance
     */
    Marker getMarker(String name);
    /**
     * Manufacture a {@link Marker} instance by name. If the instance has been
     * created earlier, return the previously created instance.
     *
     * <p>Null name values are not allowed.
     *
     * @param name the name of the marker to be created, null value is
     * not allowed.
     *
     * @return a Marker instance
     */
    @Override
    default Marker apply(String name) {
        return getMarker(name);
    }

    /**
     * Checks if the marker with the name already exists. If name is null, then false
     * is returned.
     *
     * @param name logger name to check for
     * @return true id the marker exists, false otherwise.
     */
    boolean exists(String name);

    /**
     * Checks if the marker with the name already exists. If name is null, then false
     * is returned.
     *
     * @param name logger name to check for
     * @return true id the marker exists, false otherwise.
     */
    @Override
    default boolean test(String name) {
     return exists(name);
    }

    /**
     * Detach an existing marker.
     * <p>
     * Note that after a marker is detached, there might still be "dangling" references
     * to the detached marker.
     *
     *
     * @param name The name of the marker to detach
     * @return whether the marker  could be detached or not
     */
    boolean detachMarker(String name);


    /**
     * Create a marker which is detached (even at birth) from this IMarkerFactory.
     *
     * @param name marker name
     * @return a dangling marker
     * @since 1.5.1
     */
    Marker getDetachedMarker(String name);
}
