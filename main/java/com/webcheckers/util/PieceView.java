/**
 * Class represents the Piece Data Type
 *
 * @author <a href='mailto:msg4061@rit.edu'>Matthew Glanz</a>
 */

package com.webcheckers.util;

public class PieceView {
    //Represents the options of type and color
    private Type typ;
    private Color col;

    /**
     * Constructor for the Piece Object
     *
     * @param ty the type of checker
     *
     * @param co the color of checker
     */
    public PieceView(Type ty, Color co){
        typ = ty;
        col = co;
    }

    public Type getType(){
        return typ;
    }

    public Color getColor(){
        return col;
    }

}