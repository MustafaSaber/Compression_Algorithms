/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArithmaticCodingAlgorithm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javafx.util.Pair;

/**
 *
 * @author mostafa3789
 */
public class AC {
    
    public void CreateProb(String curr) throws FileNotFoundException, UnsupportedEncodingException{
        String Path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ArithmaticCodingAlgorithm\\ProbsFile.txt";
        PrintWriter ff = new PrintWriter(Path, "UTF-8");
        ArrayList<Double> prob = new ArrayList<Double>();
        for(int i = 0 ; i < 128; i++) { prob.add(0.0);}
        
        for(int i = 0 ; i < curr.length(); i++) {
            prob.set((int)curr.charAt(i), prob.get(curr.charAt(i))+1);
        }
        for(int i = 0 ; i < 128; i++) {
            if(!prob.get(i).equals(0.0)) prob.set(i, prob.get(i)/curr.length());
        }
        for(int i = 0 ; i < 128; i++) {
            if(!prob.get(i).equals(0.0)) ff.println((char)i + " " + prob.get(i));
        }
        ff.close();
    }
    
    public ArrayList<Node> CreateRange() throws FileNotFoundException, IOException{
        String Path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ArithmaticCodingAlgorithm\\ProbsFile.txt";
        BufferedReader ff = new BufferedReader(new FileReader(Path));
        ArrayList<Node> toReturn = new ArrayList<>();
        ArrayList<Double> currProb = new ArrayList();
        for(int i = 0 ; i < 128; i++) { toReturn.add(new Node()); currProb.add(0.0); }
        String x = "";
        while(true){
            x = ff.readLine();
            if(x ==  null) break;
            String[] sp = x.split(Pattern.quote(" "));
            currProb.set((int)sp[0].charAt(0), Double.parseDouble(sp[1]));
        }
        ff.close();
        Double prev = 0.0;
        for(int i = 0 ; i < currProb.size(); i++ ){
            if(!currProb.get(i).equals(0.0)){
                toReturn.get(i).LowerLimit = prev;
                toReturn.get(i).UpperLimit = currProb.get(i) + prev;
                prev += currProb.get(i);
            }
        }
        
       /* for(int i = 0 ; i < 128; i++) {
            if(!toReturn.get(i).UpperLimit.equals(0.0) )
                System.out.println( toReturn.get(i).LowerLimit + " " + toReturn.get(i).UpperLimit);
        }*/
        return toReturn;
    }
    
    public void compress(ArrayList<Node> Ranges, String curr) throws FileNotFoundException, UnsupportedEncodingException{
        String Path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ArithmaticCodingAlgorithm\\ResultAndIterations.txt";
        PrintWriter ff = new PrintWriter(Path, "UTF-8");
        Double Lower = 0.0 , Upper = 1.0 , range = 1.0;
        for(int i = 0 ; i < curr.length(); i++){
            Upper = Lower +  range*Ranges.get((int)curr.charAt(i)).UpperLimit;
            Lower = Lower + range*Ranges.get((int)curr.charAt(i)).LowerLimit;
            range = Upper-Lower;
            System.out.println(curr.charAt(i)+ "Upper = " + Upper + " ,Lower = " + Lower);
        }
        ff.println("The key value: " + (Upper+Lower)/2);
        ff.println("Number of Iterations: " + curr.length());
        ff.close();
    }
    
    
    public String decompress() throws IOException{
        String Path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ArithmaticCodingAlgorithm\\ResultAndIterations.txt";
        String ans = "";
        BufferedReader ff = new BufferedReader(new FileReader(Path));
        String all = "";
        while(true){
            String x = ff.readLine();
            if(x ==  null) break;
            all = all + x + "\n"; 
        }
        String[] sp = all.split(Pattern.quote("\n"));
        ArrayList<Node> Ranges = CreateRange();
        String[] currArr;
        currArr = sp[1].split(Pattern.quote(" "));
        int currSize = Integer.parseInt(currArr[currArr.length-1]);
        currArr = sp[0].split(Pattern.quote(" "));
        Double currVal = Double.parseDouble(currArr[currArr.length-1]);
        Double lower = 0.0 , Upper = 0.0, range;
        while(currSize-- > 0){
            for(int i = 0 ; i < 128; i++){
                if(Ranges.get(i).LowerLimit <= currVal && currVal <= Ranges.get(i).UpperLimit)
                {
                    ans+= (char)i;
                    lower = Ranges.get(i).LowerLimit;
                    Upper = Ranges.get(i).UpperLimit;
                    break;
                }
            }
            range = Upper - lower;
            currVal = (currVal - lower)/range;
        }
        Path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ArithmaticCodingAlgorithm\\Decompressed.txt";
        PrintWriter f = new PrintWriter(Path , "UTF-8");
        f.println(ans);
        f.close();
        return ans;
    }
}
