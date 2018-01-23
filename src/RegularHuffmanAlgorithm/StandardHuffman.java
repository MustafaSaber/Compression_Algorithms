/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RegularHuffmanAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javafx.util.Pair;

/**
 *
 * @author mostafa3789
 */
public class StandardHuffman {
    
    
    private static Vector<String> dicOfKeys = new Vector<>() ;
    private static Vector<String> dicOfValues = new Vector<>() ;
    public String compress(String text)
    {
        
        ArrayList<Node> nodes = new ArrayList<>();
        Boolean check = false;
        /*
        Creating node for every char, if already exist just increase
        it's ferquency. Then sorting them.
        */
        for(int i = 0 ; i < text.length() ; i++)
        {
            check = false;
            for(Node f: nodes){
                if(f.getL()== text.charAt(i)){
                    check = true; f.setF(f.getF()+1);
                }
            }
            if(!check) nodes.add(new Node(text.charAt(i)));
        }
        Collections.sort(nodes);
        
        /*
            build the tree, every time take the last 2 elements,
            make them the right and left of th third one the re-sort
            until we have only one node in the nodes vector
        */
        while(nodes.size()>=2){
            Node temp = new Node(nodes.get(nodes.size()-1).getF()+ nodes.get(nodes.size()-2).getF(),"", '-' );
            temp.setRN(nodes.get(nodes.size()-1));
            temp.setLN(nodes.get(nodes.size()-2));
            nodes.remove(nodes.size()-1);
            nodes.remove(nodes.size()-1);
            nodes.add(temp);
            Collections.sort(nodes);
        }
       //Create the code for every letter
        nodes.get(0).setCodes(nodes.get(0));
        //Create the dictionaries
        navigate(nodes.get(0));
        
        String comString = "";
        for(int i = 0 ; i < text.length(); i++)
        {
            comString+= dicOfValues.get(dicOfKeys.indexOf(Character.toString(text.charAt(i))));
        }
        return comString;
    }
    
    public void navigate(Node head ){
        if(head == null) return;
        if(head.getL() != '-') {
            dicOfKeys.addElement(Character.toString(head.getL()));
            dicOfValues.addElement(head.getC());
        }
        navigate(head.getRN());
        navigate(head.getLN());
    }
    
    
    public String decompress(String compressed) {
        for(int i = 0 ; i < dicOfKeys.size(); i++)
            System.out.println(dicOfKeys.get(i) + " " + dicOfValues.get(i));
        String ans = "";
        String f = "";
        for(int i = 0 ; i < compressed.length(); i++) {
            if(dicOfValues.contains(f+compressed.charAt(i))){
                ans+= dicOfKeys.get(dicOfValues.indexOf(f+compressed.charAt(i)));
                f="";
            }
            else f+= compressed.charAt(i);
        }
        if(!"".equals(f)) ans+= dicOfKeys.get(dicOfValues.indexOf(f));
        return ans;
    }
}
