/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RegularHuffmanAlgorithm;

/**
 *
 * @author mostafa3789
 */
public class Node implements Comparable<Node> {
    private int ferquency = 1;
    private String code;
    private char letter;
    private Node right , left;
    public Node(char temp){
        letter = temp;
        code = "";
        right = left = null;
    }
    public Node(int f , String c, char l ){
        letter = l; ferquency = f; code = c; right = left = null;
    }
    public void setL(char l) { letter = l; }
    public void setC(String c) { code = c; }
    public void setF(int F) { ferquency = F; }
    public char getL() { return letter; }
    public String getC() { return code; }
    public int getF() {return ferquency;}
    public void setRN(Node r) { right = r; }
    public void setLN(Node l) { left = l; }
    public Node getRN() { return right; }
    public Node getLN() { return left; }
    
    
    
    public String toString()
    {
        return "The ferquency " + ferquency + ", The charachter " + letter + ", the code " + code ;   
    }
    
    public int compareTo(Node another) {
        return (this.getF()-another.getF())*-1;
    }
    
    public void setCodes(Node temp)
    {
        if(temp ==  null) return;
        if(temp.getRN() != null) temp.getRN().setC(temp.getC()+"0");
        if(temp.getLN() != null) temp.getLN().setC(temp.getC()+"1");
        setCodes(temp.getRN());
        setCodes(temp.getLN());
    }

}

