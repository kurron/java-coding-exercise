package org.kurron.exercise

import spock.lang.Specification
import spock.lang.Unroll

/***
 * Unit-level test of {@link Solution}
 */
class SolutionUnitTest extends Specification {

    @Unroll
    void 'foo'(  List<URL> data, Map<String,Integer> expected ) {
        given: 'subject under test'
        def sut = new StreamSolution()

        when: 'solve is called'
        def results = sut.solve( data )

        then: 'expected calculations are returned'
        results == expected

        where:
        data                          || expected
        [new URL( 'file:///' )] || [:]
    }
}
