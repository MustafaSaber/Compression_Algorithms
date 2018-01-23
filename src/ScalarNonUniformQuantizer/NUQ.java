/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScalarNonUniformQuantizer;

import static ScalarNonUniformQuantizer.Main.curGUI;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.regex.Pattern;

/**
 *
 * @author mostafa3789
 */
public class NUQ {

    public String compress(int numberOfLevels) throws FileNotFoundException, IOException {
        /*Before anything this code could made by pure recursion,
        I would recommed you to make it recursive as it will be more simple in,
        vector quantization.*/
        int i = 0;
        ArrayList<Integer> originalNumbers = new ArrayList<>();
        String path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ScalarNonUniformQuantizer\\toCompressVector.txt";
        BufferedReader ff = new BufferedReader(new FileReader(path));
        String rr = "";
        while (true) {
            rr = ff.readLine();
            if (rr == null) {
                break;
            }
            String[] gh = rr.split(Pattern.quote(" "));
            for(int g = 0 ; g < gh.length; g++)
                originalNumbers.add(Integer.parseInt(gh[g]));
        }
        
        ArrayList<Node> allMeans = new ArrayList<>();
        int currMean = 0;
        //Calculate the first mean from all data.
        for (Integer originalNumber : originalNumbers) {
            currMean += originalNumber;
        }
        
        //split into two and add them
        allMeans.add(new Node(Math.ceil((currMean / originalNumbers.size()) - 1)));
        allMeans.add(new Node(Math.ceil((currMean / originalNumbers.size()) + 1)));
        int start = 0;
        /*
        a loop to add means every time we calculate a new mean we split into two
        and add the two so every time it increase by i*2
                                         mean
                            right                         left
                right right     right left      left right      left left
        see the pattern
        */
        for (i = 2; i <= numberOfLevels; i *= 2) {
            //This loop to delete the prev nodes
            // aka prev means that we splited from
            for (int j = 0; j < start; j++) {
                allMeans.remove(0);
            }
            
            //Put data to the nearest mean
            for (int j = 0; j < originalNumbers.size(); j++) {
                int index = 0;
                double prev = 2000000000;
                for (int z = 0; z < allMeans.size(); z++) {
                    if (prev > Math.abs(allMeans.get(z).getMyMean() - originalNumbers.get(j))) {
                        prev = Math.abs(allMeans.get(z).getMyMean() - originalNumbers.get(j));
                        index = z;
                    }
                }
                allMeans.get(index).addtoarr(originalNumbers.get(j));
            }
            int currSize = allMeans.size();
            //To check if we need to add new levels or just delete the prev!
            if (i < numberOfLevels) {
                for (int j = 0; j < currSize; j++) {
                    currMean = 0;
                    for (int z = 0; z < allMeans.get(j).getMyArray().size(); z++) {
                        currMean += allMeans.get(j).getMyArray().get(z);
                    }
                    allMeans.add(new Node(Math.ceil(currMean / allMeans.get(j).getMyArray().size()) - 1));
                    allMeans.add(new Node(Math.ceil(currMean / allMeans.get(j).getMyArray().size()) + 1));
                }
            }
            start = i;
        }
        
        //Sort data in every node
        for (Node x : allMeans) {
            Collections.sort(x.getMyArray());
        }
        
        Boolean ch = true;
        ArrayList<Node> temp;
        
        //Keep calculate means until stable
        //Stable: the prev means equal curr means.
        while (ch) {
            ch = false;
            temp = new ArrayList<>();
            for (i = 0; i < allMeans.size(); i++) {
                currMean = 0;
                for (int z = 0; z < allMeans.get(i).getMyArray().size(); z++) {
                    currMean += allMeans.get(i).getMyArray().get(z);
                }
                temp.add(new Node(currMean / allMeans.get(i).getMyArray().size()));
            }
            for (int j = 0; j < originalNumbers.size(); j++) {
                int index = 0;
                double prev = 20000000;
                for (int z = 0; z < temp.size(); z++) {
                    if (prev > Math.abs(temp.get(z).getMyMean() - originalNumbers.get(j))) {
                        prev = Math.abs(temp.get(z).getMyMean() - originalNumbers.get(j));
                        index = z;
                    }
                }
                temp.get(index).addtoarr(originalNumbers.get(j));
            }
            for (Node x : temp) {
                Collections.sort(x.getMyArray());
            }
            
//            print(allMeans);
//            System.out.println("\n");
//            print(temp);
            for (int n1 = 0, n2 = 0; n1 < allMeans.size(); n1++, n2++) {
                if (false == checkEq(allMeans.get(n1), temp.get(n2))) {
                    allMeans = temp;
                    ch = true;
                    break;
                }
            }
            allMeans = temp;
        }
        ff.close();
        String toReturn ="";
        path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ScalarNonUniformQuantizer\\CompressedVector.txt";
        PrintWriter bb = new PrintWriter(path, "UTF-8");
        for (Integer cu : originalNumbers) {
            if (cu < allMeans.get(0).getMyArray().get(0)) {
                bb.print(0 + " ");
                toReturn += 0 + " ";
            } else if (cu > allMeans.get(allMeans.size() - 1).getMyArray().get(allMeans.get(allMeans.size() - 1).getMyArray().size() - 1)) {
                bb.print(allMeans.get(allMeans.size() - 1).getMyMean() + " ");
                toReturn += allMeans.get(allMeans.size() - 1).getMyMean() + " ";
            } else {
                for (int g = 0; g < allMeans.size(); g++) {
                    if (cu <= allMeans.get(g).getMyArray().get(allMeans.get(g).getMyArray().size() - 1)) {
                        bb.print(g + " ");
                        toReturn += g + " ";
                        break;
                    }
                }
            }
        }
        bb.close();
        path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ScalarNonUniformQuantizer\\QuantizationVector.txt";
        bb = new PrintWriter(path, "UTF-8");
        for(int g = 0; g < allMeans.size(); g++){
            bb.println(g + " " +allMeans.get(g).getMyMean());
        }
        for(int g = 0; g < allMeans.size(); g++){
            curGUI.Area().append(g + " " +allMeans.get(g).getMyArray().get(allMeans.get(g).getMyArray().size()-1)+ " " + allMeans.get(g).getMyMean() + "\n");
        }
        bb.close();
        return toReturn;
    }

    public Boolean checkEq(Node a, Node b) {
        if (a.getMyArray().size() != b.getMyArray().size()) {
            return false;
        }
        for (int i = 0; i < a.getMyArray().size(); i++) {
            if (a.getMyArray().get(i) != b.getMyArray().get(i)) {
                return false;
            }
        }
        return true;
    }

    public void print(ArrayList<Node> n) {
        System.out.println("current list nodes: ");
        for (int i = 0; i < n.size(); i++) {
            System.out.println(n.get(i).toString());
        }
    }
    
    public String decompress() throws FileNotFoundException, IOException{
        ArrayList<Integer> compressedVector =  new ArrayList<>();
        ArrayList<Double> Quantized =  new ArrayList<>();
        String path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ScalarNonUniformQuantizer\\CompressedVector.txt";
        BufferedReader ff = new BufferedReader(new FileReader(path));
        String currnum = "";
        while(true){
            currnum = ff.readLine();
            if(currnum==null) break;
            String[] rr = currnum.split(Pattern.quote(" "));
            for(int i = 0 ; i < rr.length; i++)
                compressedVector.add(Integer.parseInt(rr[i]));
        }
        ff.close();
        path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ScalarNonUniformQuantizer\\QuantizationVector.txt";
        ff = new BufferedReader(new FileReader(path));
        while(true){
            currnum = ff.readLine();
            if(currnum==null) break;
            String[] rr = currnum.split(Pattern.quote(" "));
            Quantized.add(Double.parseDouble(rr[rr.length-1]));
        }
        ff.close();
        
        path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\ScalarNonUniformQuantizer\\Decompressed.txt";
        PrintWriter bb = new PrintWriter(path, "UTF-8");
        String toReturn ="";
        for(int i = 0 ; i < compressedVector.size();i++)
        {
            bb.print(Quantized.get(compressedVector.get(i))+ " ");
            toReturn += Quantized.get(compressedVector.get(i))+ " ";
        }
        bb.close();
        return toReturn;
    }
}
