package ng.com.jcedar.jambprep.helper;

/**
 * Created by Oluwafemi on 11/26/2015.
 */
import java.util.HashMap;

/**
 * Provides static methods for creating mutable {@code Maps} instances easily.
 */
public class Maps {
    /**
     * Creates a {@code HashMap} instance.
     *
     * @return a newly-created, initially-empty {@code HashMap}
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }
}
