package org.kurron.exercise

import spock.lang.Specification

/***
 * Unit-level test of {@link StreamSolution}
 */
class StreamSolutionUnitTest extends Specification {

    void 'Test processing test files'() {
        given: 'subject under test'
        def sut = new StreamSolution()

        and: 'the two data files'
        def data = ['file1.txt',
                    'file2.txt']

        when: 'solve is called'
        def results = sut.solve( data )

        then: 'expected calculations are returned'
        def expected = ['A': 10, 'B': 2, 'C': 2, 'CD': 8, 'Z': 2]
        results == expected

        and: 'dump the map just to visually verify the results'
        println results
    }
}
