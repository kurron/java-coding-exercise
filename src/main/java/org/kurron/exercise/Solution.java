package org.kurron.exercise;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Service interface that allows me to try different implementations without changing too much code.
 */
public interface Solution {

    /**
     * Reads through a collection of key-value data files of integers, tallying up totals for the individual keys.
     * @param dataFiles names of the data files to process.
     * @return mapping of data variables to their sums.
     */
    Map<String,Integer> solve( List<String> dataFiles );
}
