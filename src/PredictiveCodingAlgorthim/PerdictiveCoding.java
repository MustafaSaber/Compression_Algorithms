/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PredictiveCodingAlgorthim;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mostafa3789
 */
public class PerdictiveCoding {
    
    public int[][] encode(int noOfBits , int[][] arr , int width , int height) throws FileNotFoundException, UnsupportedEncodingException
    {
        ArrayList<Integer> allnumbers = new ArrayList<>();
        
        for(int i = 0 ; i < height; i++)
            for(int j = 0; j < width; j++)
                allnumbers.add(arr[i][j]);
        
        int num1 = allnumbers.get(0) , num2 = allnumbers.get(0);
        for(int i = 1; i < allnumbers.size(); i++){
            num1 = Math.max(allnumbers.get(i), num1);
            num2 = Math.min(allnumbers.get(i), num2);
        }
        int stepSize = (int) ((num1-num2)/Math.pow(2, noOfBits));
        
        ArrayList<Node> myRanges = new ArrayList<>();
        int cum =0;
        while(cum<num1)
        {
            myRanges.add(new Node( (int)((cum+(cum+stepSize-1))/2) , cum , cum+stepSize-1));
            cum+=stepSize;
        }
        
        
        ArrayList<Integer> diff = new ArrayList<>();
        ArrayList<Integer> quantization = new ArrayList<>();
        ArrayList<Integer> dequantization = new ArrayList<>();
        ArrayList<Integer> decoded = new ArrayList<>();
        diff.add(0);
        quantization.add(allnumbers.get(0));
        dequantization.add(allnumbers.get(0));
        decoded.add(allnumbers.get(0));
        
        for(int i = 1 ; i < allnumbers.size(); i++) diff.add(Math.abs(allnumbers.get(i)-allnumbers.get(i-1)));
        
        for(int i = 1 ; i < diff.size(); i++)
        {
            if(diff.get(i)< myRanges.get(0).getmin()) quantization.add(0);
            else if(diff.get(i) > myRanges.get(myRanges.size()-1).getmax()) quantization.add(myRanges.size()-1);
            else{
                for(int j =0 ; j < myRanges.size(); j++){
                    if(diff.get(i)>= myRanges.get(j).getmin() && diff.get(i)<= myRanges.get(j).getmax())
                    {
                        quantization.add(j); break;
                    }
                }
            }
        }
        
        for(int i = 1; i < quantization.size(); i++) dequantization.add(myRanges.get(quantization.get(i)).getvalue());
        
        for(int i = 1 ; i < dequantization.size(); i++) decoded.add(decoded.get(i-1)+dequantization.get(i));
        
        String path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\PredictiveCodingAlgorthim\\table.txt";
        PrintWriter p = new PrintWriter(path, "UTF-8");
        for(int i = 0; i < allnumbers.size(); i++)
            p.println("Original number: " + allnumbers.get(i) + " current diff number: " + diff.get(i) 
            + " , quantizated number: " + quantization.get(i) + " , dequantizated number: " + dequantization.get(i)
            + " , decoded number: " + decoded.get(i));
        
        int coun = 0;
        for(int i = 0 ; i < height; i++)
            for(int j = 0; j < width; j++ , coun++){
                arr[i][j] = decoded.get(coun);
           }
        p.close();
        return arr;
    }
    
    
}
