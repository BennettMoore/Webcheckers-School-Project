/**
 * Class represents the BoardView Complex Data Type
 *
 * @author <a href='mailto:msg4061@rit.edu'>Matthew Glanz</a>
 */

package com.webcheckers.util;

import java.lang.Iterable;
import java.util.Iterator;


public class BoardView implements Iterable {
    private final Row[] row;
    private final boolean flipBoard;

    /**
     * Constructor for the BoardView Object
     *
     * @param rArr An array of rows
     * @param flipBoard whether to flip the board vertically. This doesn't effect the IDs and coordinates associated
     *                  with each cell on the board - a downward move on a flipped board will report the same
     *                  coordinates to the server as an upward move on an unflipped board. It's just used for rendering
     *                  purposes.
     */
    public BoardView(Row[] rArr, boolean flipBoard){
        row = rArr;
        this.flipBoard = flipBoard;
    }

    /**
     * Iterator Function
     *
     * @return An iterator object
     */
    @Override
    public Iterator<Row> iterator() {
        Iterator<Row> it = new Iterator<Row>() {
            private int currentIndex = flipBoard ? 7 : 0;

            private int nextIndex() {
                if (flipBoard) {
                    return currentIndex--;
                } else {
                    return currentIndex++;
                }
            }

            @Override
            public boolean hasNext() {
                return 0 <= currentIndex && currentIndex <= 7 && row[currentIndex] != null;
            }

            @Override
            public Row next() {
                return row[nextIndex()];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
