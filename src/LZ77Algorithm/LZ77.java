/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LZ77Algorithm;

import java.util.Vector;

/**
 *
 * @author mostafa3789
 */
public class LZ77 {
    
    public LZ77() {}
    public Vector<Tag> compress(String Input)
    {
        //That code I guess very bad as their is some built-in functions 
        //to get the index of the last repeated string but I did it manual without this function.
        //Things will be more easy with the function :)
        Vector<Tag> tags = new Vector<Tag>();
        int tempPosition = 0 , tempLength = 0;
        char tempNS = '-';
        Boolean check = false;
        // Iterating on the length of the input
        for(int i = 0 ; i < Input.length(); i++)
        {
            tempPosition = tempLength = 0;
            tempNS = '-';
            check = false;
            // check if not the last item, if the last item I will just put a tag with 
            //null next charachter,see the else section
            if(i < Input.length() - 1)
            {
                //a loop to take a sub-string from the next charachters to compare with prev one
                //to take max repeated charachters
                for(int j = i+1 ; j < Input.length(); j++)
                {
                    String toCompare = Input.substring(i, j);
                    //if toCompare size became greater than all the prev
                    //then the next iterations will be useless
                    if(toCompare.length() >  i - 0) break;
                    //to compare the characters before my pointer with
                    //ones after my pointer
                    for(int z = 0 ; z <= i-1 ; z++)
                    {
                        String matchingString = Input.substring(z, z + toCompare.length());
                        if( toCompare.equals(matchingString) && (z + toCompare.length()) <= i ) 
                        {
                            tempPosition =  i - z;
                            tempLength = toCompare.length();
                            tempNS = Input.charAt(toCompare.length()+i);
                            check = true;
                            //System.out.println( "j = " + j + " , z = " + z + "  ,i = " + i + " " + toCompare + " == " + matchingString);
                        }
                    }
                }
            }
            else
            {
                for(int z = 0 ; z <= i-1 ; z++)
                {
                    if( Input.charAt(i) == Input.charAt(z)) 
                    {
                        tempPosition =  i - z;
                        tempLength = 1;
                        tempNS = '-';
                        check = true;
                    }
                }
            }
            if(check){ 
                    tags.add(new Tag(tempPosition , tempLength , tempNS));
                    i+= tempLength;
            }
            else {
                    tags.add(new Tag(0 , 0 , Input.charAt(i)));
            }
        }
        return tags;
    }
	
    public String decompress(Vector<Tag> temp)
    {
        String ans = "";
        int j = 0;
        //Takes vector of the tags
        // return the required steps
        //take required number of characters
        // but the next char and go to the next tag
        for(int i = 0 ; i < temp.size() ; i++)
        {
            j = ans.length() - temp.get(i).getPosition();
            for(int z = 0 ; z < temp.get(i).getLength(); z++ )
                    ans += ans.charAt(j+z);
            if(temp.get(i).getNS() != '-') ans+= temp.get(i).getNS();
        }
        return ans;
    }
    
}
