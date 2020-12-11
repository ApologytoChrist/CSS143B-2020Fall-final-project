package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearcherImpl implements Searcher {
    public List<Integer> search(String keyPhrase, Map<String, List<List<Integer>>> index) {
        List<Integer> result = new ArrayList<>();
        // add your code
        if (keyPhrase == null || keyPhrase.equals("")) {
            return result;
        }
        String[] keyWords = keyPhrase.trim().split("\\s+");
        String firstWord = keyWords[0];
        List<List<Integer>> wordDocs = wordDocsHelper(index, firstWord);
        if (wordDocs == null) {
            return result;
        }
        List<Integer> wordDocsAppear = new ArrayList<>();
        for (int i = 0; i < wordDocs.size(); i++) {
            if (!wordDocs.get(i).isEmpty()) {
                wordDocsAppear.add(i);
            }
        }

        if (keyWords.length > 1) {

            for (int i = 0; i < wordDocsAppear.size(); i++) {
                Integer whichDoc = wordDocsAppear.get(i);
                List<Integer> firstWordLocs = getIntegers(wordDocs, whichDoc);
                boolean match = true;
                for (int j = 1; j < keyWords.length; j++) {
                    String otherWord = keyWords[j];
                    List<List<Integer>> otherWordDocs = wordDocsHelper(index, otherWord);
                    List<Integer> otherWordLocs = getIntegers(otherWordDocs, whichDoc);
                    match = phraseCheckHelper(firstWordLocs, otherWordLocs, j);
                    if (!match) {
                        break;
                    }
                }
                if (match) {
                    result.add(whichDoc);
                }
            }
        } else {
            result = wordDocsAppear;
        }
        return result;
    }

    private boolean phraseCheckHelper(List<Integer> firstWordLocs, List<Integer> otherWordLocs, Integer counter) {
        boolean match = false;
        for (int currentLocation : firstWordLocs) {
            if (otherWordLocs.contains(currentLocation + counter)) {
                match = true;
            }
        }
        return match;
    }

    private List<List<Integer>> wordDocsHelper(Map<String, List<List<Integer>>> index, String key) {
        return index.get(key);
    }

    private List<Integer> getIntegers(List<List<Integer>> wordDocs, Integer whichDoc) {
        return wordDocs.get(whichDoc);
    }
}