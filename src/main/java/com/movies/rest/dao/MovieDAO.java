package com.movies.rest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.springframework.stereotype.Repository;

import com.movies.rest.model.Movie;
import com.movies.rest.model.Movies;
import com.movies.rest.util.TrieNode;

@Repository
public class MovieDAO {
	private TrieNode root;
	LinkedHashSet<String> words;
	LinkedHashSet<String> orgs;
	TrieNode prefixRoot;
	String curPrefix;

	static Movies list = new Movies();

	public MovieDAO() {
		root = new TrieNode();
		words = new LinkedHashSet<String>();
		orgs = new LinkedHashSet<String>();
	}

	// Inserts a word into the trie.
	public void insert(String word) {
		orgs.add(word);
		String pass = word.toLowerCase();
		HashMap<Character, TrieNode> children = root.children;

		TrieNode crntparent;

		crntparent = root;

		// cur children parent = root

		for (int i = 0; i < pass.length(); i++) {
			char c = pass.charAt(i);

			TrieNode t;
			if (children.containsKey(c)) {
				t = children.get(c);
			} else {
				t = new TrieNode(c);
				t.parent = crntparent;
				children.put(c, t);
			}

			children = t.children;
			crntparent = t;

			// set leaf node
			if (i == pass.length() - 1)
				t.isLeaf = true;
		}
	}

	// Returns if the word is in the trie.
	public boolean search(String word) {
		String pass = word.toLowerCase();
		TrieNode t = searchNode(pass);
		if (t != null && t.isLeaf) {
			return true;
		} else {
			return false;
		}
	}

	// Returns if there is any word in the trie
	// that starts with the given prefix.
	public boolean startsWith(String prefix) {
		String pass = prefix.toLowerCase();
		if (searchNode(pass) == null) {
			return false;
		} else {
			return true;
		}
	}

	public TrieNode searchNode(String str) {
		String pass = str.toLowerCase();
		Map<Character, TrieNode> children = root.children;
		TrieNode t = null;
		for (int i = 0; i < pass.length(); i++) {
			char c = pass.charAt(i);
			if (children.containsKey(c)) {
				t = children.get(c);
				children = t.children;
			} else {
				return null;
			}
		}

		prefixRoot = t;
		curPrefix = pass;
		words.clear();
		return t;
	}

	public void wordsFinderTraversal(TrieNode node, int offset) {

		if (node.isLeaf == true) {

			TrieNode altair;
			altair = node;

			Stack<String> hstack = new Stack<String>();

			while (altair != prefixRoot) {
				hstack.push(Character.toString(altair.c));
				altair = altair.parent;
			}

			String wrd = curPrefix;

			while (hstack.empty() == false) {
				wrd = wrd + hstack.pop();
			}

			// println(wrd);
			words.add(wrd);

		}

		Set<Character> kset = node.children.keySet();
		Iterator itr = kset.iterator();
		ArrayList<Character> aloc = new ArrayList<Character>();

		while (itr.hasNext()) {
			Character ch = (Character) itr.next();
			aloc.add(ch);
		}

		// here you can play with the order of the children
		for (int i = 0; i < aloc.size(); i++) {
			wordsFinderTraversal(node.children.get(aloc.get(i)), offset + 2);
		}

	}

	public List<String> displayFoundWords(int limit) {
		List<String> result = new ArrayList<>();
		int count = 0;
		for (String word : words) {
			for (String org : orgs) {
				if (count == limit)
					break;
				if (org.equalsIgnoreCase(word)) {
					result.add(org);
					count++;
				}
			}
		}
		return result;
	}

	public Movies getAllMovies() {
		return list;
	}

	public void addMovie(Movie movie) {
		list.getMovieList().add(movie);
	}
}
