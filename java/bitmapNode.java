// Christian Barajas, Christian Cosby, Kevin Nguyen
// Group #15
// CS 560

//source code by Professor Bill Root
//modifications done by students is commented

import java.util.*;


public class bitmapNode {
	int bitmap;
	bitmapNode link;
    //added by student
    ArrayList<bitmapNode> children;
    bitmapNode parent;
    int filledPositions;
    
	public bitmapNode(){
		bitmap = 0;
        link = null;
        children = new ArrayList<bitmapNode>();
        parent = null;
        filledPositions = 0;
	}
	
	public bitmapNode(int map){
		bitmap = map;
        link = null;
        children = new ArrayList<bitmapNode>();
        parent = null;
        filledPositions = 0;
    }
}