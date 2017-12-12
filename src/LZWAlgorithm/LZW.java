/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LZWAlgorithm;

import java.util.Vector;
import javafx.util.Pair;

/**
 *
 * @author mostafa3789
 */
public class LZW {
    static private int curr = 128;
    
    public Vector<Tag> compress(String needCompress){
        Vector <Tag> all = new Vector<>();
        String currS = "";
        Vector<String> names = new Vector<>();
        Vector<Integer> index = new Vector<>();
        for(int i = 0 ; i < 128; i++)
        {
            names.addElement(Character.toString((char)i));
            index.addElement(i);
        }
        for(int i = 0 ; i < needCompress.length(); i++)
        {
            if(names.contains(currS + needCompress.charAt(i))) currS += needCompress.charAt(i);
            else
            {
                all.addElement(new Tag(index.get(names.indexOf(currS))));
                names.addElement(currS + needCompress.charAt(i));
                index.addElement(curr);
                curr++;
                currS = Character.toString(needCompress.charAt(i));
            }
        }
        if(currS.length()==1) all.addElement(new Tag((int)currS.charAt(0)));
        else {
            all.addElement(new Tag(index.get(names.indexOf(currS))));
        }
        return all;   
    }
    
    public String decompress(Vector<Tag> Tags){
        curr = 128;
        String required = "";
        Vector <Pair<String , Integer>> dict = new Vector<>();
        //required += (char)Tags.get(0).getI();
        String pre = "";
        Boolean che;
        int tempj =0;
        for(int i = 0 ; i < Tags.size() ; i++)
        {
            che = true;
            if(Tags.get(i).getI() < 128)
            {
                //System.out.print(pre + " - ");
                for(int j = 0; j < dict.size(); j++)
                    if(dict.get(j).getKey().equals(pre + (char)Tags.get(i).getI())) { che = false; break; }
                if(che && i!=0)
                {
                    dict.addElement(new Pair(pre + (char)Tags.get(i).getI() , curr));
                    curr++;
                }
                required += (char)Tags.get(i).getI();
                pre = Character.toString((char)Tags.get(i).getI());
                //System.out.println( pre + " -> " +required);
            }
            else 
            {
                for(int j = 0; j < dict.size(); j++)
                    if(dict.get(j).getValue().equals(Tags.get(i).getI())) { tempj = j; che = false; break; }
                if(che)
                {
                    //System.out.print(pre + " - ");
                    required += pre + pre.charAt(0);
                    dict.addElement(new Pair(pre + pre.charAt(0) , Tags.get(i).getI()));
                    if(curr == Tags.get(i).getI()) curr++;
                    pre += pre.charAt(0);
                    //System.out.println(pre + " -> " + required);
                }
                else
                {
                    //System.out.print(pre + " - ");
                    required += dict.get(tempj).getKey();
                    dict.addElement(new Pair(pre+dict.get(tempj).getKey().charAt(0) , curr));
                    curr++;
                    pre = dict.get(tempj).getKey();
                    //System.out.println(pre + " -> " + required);
                }
            }
        }
        return required;
    }
}
