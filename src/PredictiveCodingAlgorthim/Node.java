/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PredictiveCodingAlgorthim;

/**
 *
 * @author mostafa3789
 */
public class Node {
    
    private int value;
    private int min;
    private int max;
    
    public Node (int v , int mi , int ma){value = v ; min = mi ; max = ma;}
    
    public void setvalue(int v){ value = v; }
    public void setmin(int v){ min = v; }
    public void setmax(int v){ max = v; }
    
    public int getvalue(){ return value;}
    public int getmin(){ return min;}
    public int getmax(){ return max;}
    
}
