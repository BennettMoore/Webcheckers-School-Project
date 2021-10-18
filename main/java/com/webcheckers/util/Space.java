/**
 * Class represents the Space Data Type
 *
 * @author <a href='mailto:msg4061@rit.edu'>Matthew Glanz</a>
 */

package com.webcheckers.util;

public class Space {
    private final int cellIdx;
    private final PieceView piece;

    /**
     * Constructor for the BoardView Object
     *
     * @param idx
     *   The index of the space in the row
     * @param p1
     *   The piece or null on the space
     */
    public Space(int idx, PieceView p1){
        cellIdx = idx;
        piece = p1;
    }

    /**
     * Getter for the index of the space in the row
     *
     * @return
     *   The index of the space in the row as an int
     */
    public int getCellIdx(){
        return cellIdx;
    }

    /**
     * Determines if the space is valid
     *
     * @return
     *   A boolean
     */
    public boolean isValid(){
        return piece == null;
    }

    /**
     * Getter for the piece on the space
     *
     * @return
     *  The piece object or null
     */
    public PieceView getPiece(){
        return piece;
    }


}
