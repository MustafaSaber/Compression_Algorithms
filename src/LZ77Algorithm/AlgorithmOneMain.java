/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LZ77Algorithm;

import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author mostafa3789
 */
public class AlgorithmOneMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        /*Scanner scanner = new Scanner(System.in);
        LZ77 ff = new LZ77();
        System.out.println("Enter the string you want to compress: ");
        String Input = scanner.nextLine();
        Vector<Tag> cmpt = ff.compress(Input);
        for(int i = 0 ; i < cmpt.size() ; i++)
                cmpt.get(i).printTag();

        System.out.println( "The decompressed string is " + ff.decompress(cmpt));
        scanner.close();*/
        NewJFrame form = new NewJFrame();
        form.setVisible(true);
    }
}
