package org.kurron.exercise;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of the Solution interface that uses parallel streams to do its job.
 */
public class StreamSolution implements Solution {

    @Override
    public Map<String, Integer> solve( List<String> dataFiles ) {

        Map<String, Integer> bob = dataFiles.stream().map( StreamSolution::fileNameToFileContents )
                          .flatMap( StreamSolution::stringPairToMap )
                          .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue, Integer::sum ) );
        Map<String,Integer> alpha = new HashMap<>( 8 );
        alpha.put( "A", 1 );
        alpha.put( "C", 2 );
        alpha.put( "CD", 4 );
        alpha.put( "A", 3 );
        alpha.put( "Z", 1 );
        Map<String,Integer> bravo = new HashMap<>( 8 );
        bravo.put( "B", 2 );
        bravo.put( "A", 3 );
        bravo.put( "CD", 4 );
        bravo.put( "A", 3 );
        bravo.put( "Z", 1 );
        Map<String,Integer> totals = Stream.concat( alpha.entrySet().stream(), bravo.entrySet().stream() )
                                           .collect( Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum ) );
        return totals;
    }

    // transform from a string to lines contained within a text file
    static List<String> fileNameToFileContents( String filename ) {
        try(Stream<String> lines = Files.lines( Paths.get( filename ), StandardCharsets.UTF_8 ) ) {
            return lines.collect( Collectors.toList() );
        }
        catch ( IOException e ) {
            //TODO: at least log something
            throw new RuntimeException( e );
        }
    }

    static Set<Map.Entry<String,Integer>> toEntrySet(Map<String,Integer> map ) {
        return map.entrySet();
    }

    // transform list of key-value strings into a map of key-value pairs
    static Stream<Map.Entry<String,Integer>> stringPairToMap( List<String> pairs ) {
        Map<String,Integer> bob = pairs.stream().map( StreamSolution::mapLineToPair  )
                             .collect( Collectors.toMap( Pair::getKey, Pair::getValue, Integer::sum ) );
        return bob.entrySet().stream();
    }

    static Pair mapLineToPair( String line ) {
        String[] parsed = line.split( "=" );
        return new Pair( parsed[0], Integer.parseInt( parsed[1] ) );
    }

    static class Pair {
        String key;
        Integer value;

        Pair(String key, Integer value) {
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
