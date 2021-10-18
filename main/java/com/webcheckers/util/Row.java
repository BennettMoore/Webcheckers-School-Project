/**
 * Class represents the Row Data Type
 *
 * @author <a href='mailto:msg4061@rit.edu'>Matthew Glanz</a>
 */

package com.webcheckers.util;
import java.lang.Iterable;
import java.util.Iterator;


public class Row implements Iterable{
    private final int index;
    private final Space[] space;
    private final boolean flipRow;


    /**
     * Constructor for the BoardView Object
     *
     * @param ind An index representing the row within the board
     * @param sArr An array of spaces in the row
     * @param flipRow whether or not to flip the row horizontally. This follows similar rules to `flipBoard` in
     *                BoardView, so refer to that documentation for more details on the semantics of this parameter.
     */
    public Row(int ind, Space[] sArr, boolean flipRow){
        index = ind;
        space = sArr;
        this.flipRow = flipRow;
    }

    /**
     * Getter for the index position of the row
     *
     * @return
     *   An int representing the index for the row
     */
    public int getIndex(){
        return index;
    }


    /**
     * Iterator Function
     *
     * @return An iterator object
     */
    @Override
    public Iterator<Space> iterator() {
        Iterator<Space> it = new Iterator<Space>() {
            private int currentIndex = flipRow ? 7 : 0;

            private int nextIndex() {
                if (flipRow) {
                    return currentIndex--;
                } else {
                    return currentIndex++;
                }
            }

            @Override
            public boolean hasNext() {
                return 0 <= currentIndex && currentIndex <= 7 && space[currentIndex] != null;
            }

            @Override
            public Space next() {
                return space[nextIndex()];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
