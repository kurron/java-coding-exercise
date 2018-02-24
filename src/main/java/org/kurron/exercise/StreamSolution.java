package org.kurron.exercise;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of the {@link Solution} interface that uses parallel streams to accomplish the task.
 */
public class StreamSolution implements Solution {

    @Override
    public Map<String, Integer> solve( final List<String> dataFiles ) {
        return dataFiles.stream()
                        .parallel()
                        .map( StreamSolution::fileNameToFileContents )
                        .flatMap( StreamSolution::stringToMapEntryStream )
                        .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue, Integer::sum ) );
    }

    /**
     * This function will read the lines of the specified file, returning a collection of each line read.
     * @param filename name of the file to read.
     * @return collection of the file's contents.
     */
    private static List<String> fileNameToFileContents( final String filename ) {
        final URL location = Optional.ofNullable( StreamSolution.class.getClassLoader().getResource( filename ) )
                                     .orElseThrow( IllegalStateException::new );
        try( Stream<String> lines = Files.lines( Paths.get( location.toURI() ), StandardCharsets.UTF_8 ) ) {
            return lines.collect( Collectors.toList() );
        }
        catch ( IOException | URISyntaxException e ) {
            //TODO: at least log something
            throw new RuntimeException( e );
        }
    }

    /**
     * Given a collection of strings with the format of "key = number", this function will create a stream
     * of map entries.  Any duplicates in the strings are accounted for, with the integer values summed together
     * and stored under a single key.
     * @param pairs collection of key-number pairs to transform.
     * @return stream of map entries.
     */
    private static Stream<Map.Entry<String,Integer>> stringToMapEntryStream( final List<String> pairs ) {
       return pairs.stream()
                   .parallel()
                   .map( StreamSolution::mapLineToPair  )
                   .collect( Collectors.toMap( Pair::getKey, Pair::getValue, Integer::sum ) )
                   .entrySet()
                   .stream();
    }

    /**
     * Given a string with the format of "key = number", this function will transform into a more manageable object.
     * @param line text to convert.
     * @return newly created object.
     */
    private static Pair mapLineToPair( final String line ) {
        final String[] parsed = line.split( "=" );
        return new Pair( parsed[0], Integer.parseInt( parsed[1] ) );
    }

    /**
     * Wanted an object to hold the key-value pairs parsed out from the string in the data files.
     */
    static class Pair {
        String key;
        Integer value;

        Pair( String key, Integer value ) {
            this.key = key;
            this.value = value;
        }

        String getKey() {
            return key;
        }

        Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + " = " + value;
        }
    }
}
