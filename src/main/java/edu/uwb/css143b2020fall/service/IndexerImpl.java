package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexerImpl implements Indexer {
    public Map<String, List<List<Integer>>> index(List<String> docs) {
        Map<String, List<List<Integer>>> indexes = new HashMap<>();
        if (docs == null) {
            return indexes;
        }
        int listSize = docs.size();
        for (int i = 0; i < docs.size();i++) {
            String[] docWords = docs.get(i).trim().split("\\s+");
            Map<String, List<Integer>> wordLocations = wordLocations(docWords);
            for (String key : wordLocations.keySet()) {
                if (!indexes.containsKey(key)) {
                    List<List<Integer>> docsAndLocs = new ArrayList<>(listSize);
                    for (int fill = 0; fill < listSize; fill++) {
                        docsAndLocs.add(new ArrayList<>());
                    }
                    docsAndLocs.set(i, wordLocations.get(key));
                    indexes.put(key,docsAndLocs);
                } else {
                    indexes.get(key).set(i , wordLocations.get(key));
                }
            }
        }
        return indexes;
    }

    private Map<String, List<Integer>> wordLocations(String[] words) {
        Map<String, List<Integer>> wordLocations = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i];
            if (currentWord.equals("")) {
                return wordLocations;
            }
            if (!wordLocations.containsKey(currentWord)) {
                List<Integer> wordLocation = new ArrayList<>();
                wordLocation.add(i);
                wordLocations.put(currentWord, wordLocation);
            } else {
                wordLocations.get(currentWord).add(i);
            }
        }
        return wordLocations;
    }
}