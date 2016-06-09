// Kevin Nguyen, Christian Barajas, Christian Cosby
// Group #15
// CS 560

//based on source code given by Professor Bill Root, San Diego State University
//modified by students
//all functions written by students except main(), which was modified
//first five class variables, written by Professor Root
//part of constructor written by Professor Root, moved from main to constructor and
//modified to take input from .txt file

package puzzle;

import java.util.*;
import java.io.*;

public class cs560project
{
    public static int N;
    public static int[][][] mapTable;
    public static int[] xInvTable;
    public static int[] yInvTable;
    public static int[] zInvTable;
    public static Scanner scan;
    private static ArrayList<piece3D> pieces;
    private int amountOfPieces;
    private int time;
    private int filledAllPositions;
    private int level;
    private boolean solutionFound;
    private ArrayList<String> answer;
    
    public cs560project(String fileName)
    {
        int x,y,z;
        pieces = new ArrayList<piece3D>();
        try
        {
            scan = new Scanner(new FileReader(fileName));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found. Will try to run anyways.");
        }
        int sizeOfCube = scan.nextInt();
        //System.out.println(sizeOfCube);
        N = sizeOfCube;
        
        mapTable = new int[N][N][N];
        xInvTable = new int[N*N*N];
        yInvTable = new int[N*N*N];
        zInvTable = new int[N*N*N];
        int count = 0;
        for(int ix=0; ix<N; ix++)
        {
            for(int iy=0; iy<N;iy++)
            {
                for(int iz=0; iz<N; iz++)
                {
                    mapTable[ix][iy][iz] = 1 << count;
                    xInvTable[count] = ix;
                    yInvTable[count] = iy;
                    zInvTable[count] = iz;
                    count++;
                }
            }
        }
        
        amountOfPieces = scan.nextInt();
        //System.out.println(amountOfPieces);
        for(int i=0; i<amountOfPieces; i++)
        {
            int currentPiece = scan.nextInt();
            //System.out.println(currentPiece);
            int amountOfUnits = scan.nextInt();
            //System.out.println(amountOfUnits);
            piece3D current3Dpiece = new piece3D((char)currentPiece, amountOfUnits);
            pieces.add(current3Dpiece);
            for(int j=0; j<amountOfUnits; j++)
            {
                x = scan.nextInt();
                //System.out.print(x + " ");
                y = scan.nextInt();
                //System.out.print(y + " ");
                z = scan.nextInt();
                //System.out.print(z + "\n");
                pieces.get(i).setCube(j, x, y, z);
            }
            pieces.get(i).findAllPossiblePositionsAsBitmaps();
            filledAllPositions = getInt(N);
            solutionFound = false;
        }
    }
    
    //Pieces are sorted from least amount of possible positions to most
	//decided not to use, since it doesn't noticeably improve running time
    public void sort()
    {
        ArrayList newArrayList = new ArrayList<piece3D>();
        for(int i=0; i<amountOfPieces; i++)
        {
            int index = 0;
            piece3D tempPiece = pieces.get(0);
            for(int j=0; j<pieces.size(); j++)
            {
                if(pieces.get(j).amountOfPositions<tempPiece.amountOfPositions)
                {
                    tempPiece = pieces.get(j);
                    index = j;
                }
            }
            newArrayList.add(tempPiece);
            pieces.remove(index);
        }
        pieces = newArrayList;
        System.out.println("Pieces are sorted from least amount of possible positions to most.\npieces size: " + pieces.size() + " amount of Pieces: " + amountOfPieces);
        int pieceNum = 1;
        for(piece3D piece : pieces)
        {
            System.out.println("piece: " + pieceNum + " amount of possible positions: " + piece.amountOfPositions);
            pieceNum++;
        }
    }
    
    public void populate()
    {
        //sort();
        time = 0;
        for(piece3D piece : pieces)
        {
            time++;
            bitmapNode currentNode = piece.possiblePositionsBitmaps;
            while(currentNode!=null)
            {
                time++;
                piece.possiblePositionsBitmaps.children.add(currentNode);
                currentNode = currentNode.link;
            }
        }//for every piece, its bitmapNode.children is all the nodes in the linkedlist of that bitmapNode
        //System.out.println("populate time: " + time);
    }
    
    //will find a solution to puzzle
    public void solve()
    {
        populate();
        bitmapNode root = new bitmapNode();
        level = 0;
        time = 0;
        root.children = pieces.get(0).possiblePositionsBitmaps.children;
        for(bitmapNode child : root.children)
        {
            time++;
            if(solutionFound)
                break;;
            child.parent = root;
            child.filledPositions = child.bitmap;
            visit(child);
        }
        //System.out.println("time: " + time);
    }
    
    //used in conjunction with solve() to perform Depth-First-Search
    public void visit(bitmapNode node)
    {
        if(level==amountOfPieces-1)
        {
            if(node.filledPositions==filledAllPositions)
            {
                System.out.println("Solution found");
                bitmapNode traverser = node;
                int pieceNum = amountOfPieces;
                while(traverser.parent!=null)
                {
                    System.out.println("piece " + pieceNum + " bitmap " + getBitmap(traverser));
                    pieceNum--;
                    traverser = traverser.parent;
                }
                solutionFound = true;
            }
            return;
        }
        level++;
        node.children = pieces.get(level).possiblePositionsBitmaps.children;
        for(bitmapNode child : node.children)
        {
            time++;
            if(solutionFound)
                return;
            child.parent = node;
            if((child.bitmap & node.filledPositions)!=0)
                continue;
            else
                child.filledPositions = child.bitmap | node.filledPositions;
            visit(child);
        }
        level--;
    }
    
    //used to get an int whose binary representation is all 1's that represents a filled cube/puzzle
    public int getInt(int N)
    {
        String binary = "";
        int stringLength = N*N*N;
        for(int i=0; i<stringLength; i++)
        {
            binary += "1";
        }
        int intResult = Integer.parseInt(binary, 2);
        return intResult;
    }
    
    //formats to show bitmap positions not filled that are left of furthermost left 1
    public String getBitmap(bitmapNode traveler)
    {
        String currentBitmap = Integer.toBinaryString(traveler.bitmap);
        while(currentBitmap.length()<N*N*N)
        {
            currentBitmap = "0" + currentBitmap;
        }
        return currentBitmap;
    }
    
    public String getBitmap(String currentBitmap)
    {
        while(currentBitmap.length()<N*N*N)
        {
            currentBitmap = "0" + currentBitmap;
        }
        return currentBitmap;
    }
    
    //.txt files are input by creating a cs560project instance and the name of the file is the constructor parameter
    public static void main(String[] args)
    {
        cs560project puzzle = new cs560project("inputs/cs560input.txt");
        puzzle.solve();
    }
}// closes class

