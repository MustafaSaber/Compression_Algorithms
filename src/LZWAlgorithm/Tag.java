/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LZWAlgorithm;

/**
 *
 * @author mostafa3789
 */
public class Tag {
    private int indexDict;
    public Tag(int ID) { indexDict = ID; }
    public void setI(int ID) { indexDict = ID; }
    public int getI () { return indexDict; }
    @Override
    public String toString() { return "< " + indexDict + " >"; }
}
