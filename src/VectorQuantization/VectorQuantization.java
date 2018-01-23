/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VectorQuantization;

import static VectorQuantization.Main.myGUI;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author mostafa3789
 */
public class VectorQuantization {

    public class Block {

        public int x, y, arr[][];

        public Block(int xaxis, int yaxis) {
            x = xaxis;
            y = yaxis;
            arr = new int[y][x];
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    arr[i][j] = 0;
                }
            }
        }
        public void printB(){
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    System.out.print(arr[i][j]+ " ");
                }
                System.out.println();
            }
        }
    }

    public class Node {

        public Block myblock;
        public ArrayList<Block> Blocks = new ArrayList<>();

        public Node(Block a) {
            myblock = a;
        }

        public void addtoarr(Block n) {
            Blocks.add(n);
        }
    }

    public int[][] compress(int width , int height, int[][]pict,int lvls) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        
       String path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\VectorQuantization\\toCompress.txt";
       
       
       //Check scalae nonuniform quantizer comments, the same algorthim this is just on a greater scale
        int Bwidth = 1, Bheight = 1;
        for (int i = 5; i < width; i++) {
            if (width % i == 0) {
                Bwidth = i;
                break;
            }
        }
        for (int i = 5; i < height; i++) {
            if (height % i == 0) {
                Bheight = i;
                break;
            }
        }

        ArrayList<Block> allBlocks = new ArrayList<>();
        for (int i = 0; i < height; i += Bheight) {
            for (int j = 0; j < width; j += Bwidth) {
                Block tem = new Block(Bwidth, Bheight);
                for (int ii = 0; ii < Bheight; ii++) {
                    for (int jj = 0; jj < Bwidth; jj++) {
                        tem.arr[ii][jj] = pict[ii + i][jj + j];
                    }
                }
                allBlocks.add(tem);
            }
        }

        ArrayList<Node> allmeans = new ArrayList<>();
        Block cur = new Block(Bwidth, Bheight);
        Block right = new Block(Bwidth, Bheight);
        Block left = new Block(Bwidth, Bheight);
        // Get the mean of blocks and create the first two nodes
        //allmeans.get(0).myblock = new Block(Bwidth, Bheight);
        for (int i = 0; i < cur.y; i++) {
            for (int j = 0; j < cur.x; j++) {
                for (int z = 0; z < allBlocks.size(); z++) {
                    cur.arr[i][j] += allBlocks.get(z).arr[i][j];
                }
                left.arr[i][j] = (int) (Math.ceil(cur.arr[i][j] / allBlocks.size()) -1);
                right.arr[i][j] = (int) (Math.ceil(cur.arr[i][j] / allBlocks.size())+1);   
            }
        }
        allmeans.add(new Node(right));
        allmeans.add(new Node(left));

        int start = 0;
        //loop until reach the levels required
        for (int coun = 2; coun <= lvls; coun *= 2) {
            // remove previous levels
            for (int j = 0; j < start; j++) {
                allmeans.remove(0);
            }

            //compare means and add the block to the nearest one
            for (int j = 0; j < allBlocks.size(); j++) {
                int index = 0;
                double prev = 2000000000;
                for (int z = 0; z < allmeans.size(); z++) {
                    int te = comMean(allBlocks.get(j), allmeans.get(z).myblock);
                    if (prev > te) {
                        prev = te;
                        index = z;
                    }
                    else if(prev == te && allmeans.get(z).Blocks.size() < allmeans.get(index).Blocks.size()){
                        prev= te;
                        index = z;
                    }
                }
                allmeans.get(index).addtoarr(allBlocks.get(j));
            }
            int currSize = allmeans.size();
            if (coun < lvls) {
                // go throw each mean and split it to two other means from it's blocks
                for (int CN = 0; CN < currSize; CN++) {
                    cur = new Block(Bwidth, Bheight);
                    right = new Block(Bwidth, Bheight);
                    left = new Block(Bwidth, Bheight);
                    
                    for (int i = 0; i < cur.y; i++) {
                        for (int j = 0; j < cur.x; j++) {
                            for (int z = 0; z < allmeans.get(CN).Blocks.size(); z++) {
                                cur.arr[i][j] += allmeans.get(CN).Blocks.get(z).arr[i][j];
                            }
                            if(allmeans.get(CN).Blocks.size()>0){
                            left.arr[i][j] = (int) (Math.ceil(cur.arr[i][j] / allmeans.get(CN).Blocks.size()) - 1);
                            right.arr[i][j] = (int) (Math.ceil(cur.arr[i][j] / allmeans.get(CN).Blocks.size()) + 1);
                            }
                        }
                    }
                   // if(allmeans.get(CN).Blocks.size()>0){
                    allmeans.add(new Node(right));
                    allmeans.add(new Node(left));
                    //}
                    //right.printB();
                    //left.printB();
                }
            }
            start = coun;
        }

        Boolean ch = true;
        ArrayList<Node> temp;
        while (ch) {
            ch = false;
            temp = new ArrayList<>();
            // new means from pre means without spliting
            for (Node CN : allmeans) {
                cur = new Block(Bwidth, Bheight);
                if(CN.Blocks.size()>0){
                    for (int i = 0; i < cur.y; i++) {
                        for (int j = 0; j < cur.x; j++) {
                            for (int z = 0; z < CN.Blocks.size(); z++) {
                                cur.arr[i][j] += CN.Blocks.get(z).arr[i][j];
                            }
                            cur.arr[i][j] = (int) (Math.ceil(cur.arr[i][j] / CN.Blocks.size()));
                        }
                    }
                    temp.add(new Node(cur));
                }
            }
            // assign blocks to the new means
            for (int j = 0; j < allBlocks.size(); j++) {
                int index = 0;
                double prev = 2000000000;
                for (int z = 0; z < temp.size(); z++) {
                    int te = comMean(allBlocks.get(j), temp.get(z).myblock);
                    if (prev > te) {
                        prev = te;
                        index = z;
                    }
                    /*else if(prev == te && temp.get(z).Blocks.size() < temp.get(index).Blocks.size()){
                        prev= te;
                        index = z;
                    }*/
                }
                temp.get(index).addtoarr(allBlocks.get(j));
            }
            for (int n1 = 0; n1 < allmeans.size(); n1++) {
                if (false == checkEq(allmeans.get(n1), temp.get(n1))) {
                    allmeans = temp;
                    ch = true;
                    break;
                }
            }
            allmeans = temp;
        }
        
        path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\VectorQuantization\\means.txt";
        PrintWriter bb = new PrintWriter(path, "UTF-8");
        for (int i = 0; i < allmeans.size(); i++) {
            bb.println((i)+ ": My mean block: ");
            for (int j = 0; j < allmeans.get(i).myblock.y; j++) {
                for (int z = 0; z < allmeans.get(i).myblock.x; z++) {
                    bb.print(allmeans.get(i).myblock.arr[j][z] + " ");
                }
                bb.println();
            }
        }
        bb.close();
        Boolean toBreak = false;
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i = 0 ; i < allBlocks.size(); i++){
            for(int f =0 ; f < allmeans.size(); f++){
                toBreak = false;
                for(int ff=0; ff< allmeans.get(f).Blocks.size(); ff++)
                    if(checkEq(allmeans.get(f).Blocks.get(ff) , allBlocks.get(i))== false)
                    { ans.add(f); toBreak= true; break;}
                if(toBreak) break;
            }
        }
        
        path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\VectorQuantization\\coded.txt";
        bb = new PrintWriter(path, "UTF-8");
        for(int i = 0 ; i < ans.size(); i++)
            bb.print(ans.get(i) + " ");
        bb.close();
        int cb= 0;
        for (int i = 0; i < height; i += Bheight) {
            for (int j = 0; j < width; j += Bwidth) {
                for (int ii = 0; ii < Bheight; ii++) {
                    for (int jj = 0; jj < Bwidth; jj++) {
                        pict[ii + i][jj + j]=allmeans.get(ans.get(cb)).myblock.arr[ii][jj] ;
                    }
                }
                cb++;
            }
        }
        path = "C:\\Users\\Mostafa\\Desktop\\Programming courses and Assigments\\Java Assigments In college\\MultimediaAssigs\\src\\VectorQuantization\\encoded.txt";
        bb = new PrintWriter(path, "UTF-8");
        for (int i = 0; i < height; i ++) {
            for (int j = 0; j < width; j ++) {
                bb.print(pict[i][j]+" ");
            }
            bb.println();
        }
        bb.close();
        return pict;
    }

    public int comMean(Block a, Block b) {
        int ans = 0;
        for (int i = 0; i < a.y; i++) {
            for (int j = 0; j < a.x; j++) {
                ans += (int) (Math.abs(a.arr[i][j] - b.arr[i][j]));
            }
        }
        return ans;
    }

    public Boolean checkEq(Node a, Node b) {
        if (a.Blocks.size() != b.Blocks.size()) {
            return false;
        }
        for (int i = 0; i < a.Blocks.size(); i++) {
            if (checkEq(a.myblock, b.myblock) == true) {
                return false;
            }
        }
        return true;
    }

    public Boolean checkEq(Block a, Block b) {
        for (int i = 0; i < a.y; i++) {
            for (int j = 0; j < a.x; j++) {
                if (a.arr[i][j] != b.arr[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
