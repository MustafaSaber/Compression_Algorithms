/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LZ77Algorithm;

/**
 *
 * @author mostafa3789
 */
public class Tag {
    private int Position;
    private int Length;
    private char NextSymbol;

    public int getPosition() { return Position; }
    public int getLength() { return Length; }
    public char getNS() { return NextSymbol; }

    public void setPosition(int newP) { Position = newP; }
    public void setLength( int newL) { Length = newL; }
    public void setNS( char newNS) { NextSymbol = newNS; }
    public void printTag() { System.out.println("< " + Position + ", " + Length + ", " + NextSymbol + " >"); }
    Tag( int newp , int newl , char newNS) { Position = newp ; Length = newl ; NextSymbol = newNS;}
    public String toString() { return "<" + Position + "," + Length  + "," + NextSymbol + ">"; } 
}
