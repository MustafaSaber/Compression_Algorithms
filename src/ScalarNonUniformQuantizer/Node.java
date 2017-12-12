/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScalarNonUniformQuantizer;

import java.util.ArrayList;

/**
 *
 * @author mostafa3789
 */
public class Node {
    private double MyMean;
    private ArrayList<Integer> MyNumber = new ArrayList<>();
    
    public void setMyMean(int n) { MyMean = n; }
    public void addtoarr(int n) { MyNumber.add(n);}
    public double getMyMean() {return MyMean;}
    public ArrayList<Integer> getMyArray() {return MyNumber;}
    Node(double m){ MyMean = m; }
    
    @Override
    public String toString(){
     String ans ="My mean: " + MyMean + "\nMy array numbers = ";
     for(int i = 0 ; i < MyNumber.size(); i++)
         ans+=MyNumber.get(i) + " ";
     return ans;
    }
}
