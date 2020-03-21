package com.movies.rest.model;

import java.util.HashMap;

public class TrieNode {
	 public char c;
	 public TrieNode parent;
	 public HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode> ();
	 public boolean isLeaf;
	
     public TrieNode() {}
     
     public TrieNode(char c) {
		this.c = c;
	}
}

