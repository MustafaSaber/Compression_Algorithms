/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LZWAlgorithm;

import java.util.Scanner;
import java.util.Vector;
import javafx.util.Pair;

/**
 *
 * @author mostafa3789
 */
public class Main {
    public static void main(String[] args){
//        LZW ff = new LZW();
//        Scanner In = new Scanner(System.in);
//        System.out.println("Enter the required String: ");
//        String x = In.nextLine();
//        Vector<Tag> f  = new Vector<Tag>();
//        f = ff.compress(x);
//        for(Tag temp : f)
//        {
//            System.out.println(temp.toString());
//        }
//        String Ans = ff.decompress(f);
//        System.out.println("the decompressed String : " + Ans);

        LZWGUI ff = new LZWGUI();
        ff.setVisible(true);
    }
    
}
