/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 3;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    public int wordlength = DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    HashSet<String> wordSet; //dictionary. contains all words.
    HashMap<String, ArrayList> lettersToWord;
    HashMap<Integer, ArrayList> sizeToWords;//Key:word length Value: ArrayList of words at that length

    //Reads the dictionary txt file and stores it
    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        HashSet<String> wordset = new HashSet<String>();
        HashMap<String, ArrayList> lettersToWord = new HashMap<String, ArrayList>();
        HashMap<Integer, ArrayList> wordList = new HashMap<Integer, ArrayList>();
        ArrayList<String> tempList;

        //reads the dictionary txt and store words to hashset
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordset.add(word);


        }
        //group the stored words by sortLetters, put them into lettersToWord
        //group the stored words by length, put them into wordList. Key:wordLength Value:List of words

        Iterator pointer = wordset.iterator();
        while (pointer.hasNext()) {
            String tempString = pointer.next().toString();
            String tempSorted = sortLetters(tempString); //tempSorted = sorted word post -> opst

            if (lettersToWord.containsKey(tempSorted)) {
                tempList = lettersToWord.get(tempSorted);
                tempList.add(tempString);
            } else {
                tempList = new ArrayList<String>();
                tempList.add(tempString);
                lettersToWord.put(tempSorted, tempList);
            }

            int length = tempString.length();
            if (wordList.containsKey(length)) {
                tempList = wordList.get(length);
                tempList.add(tempString);
                wordList.put(length, tempList);
            } else {
                tempList = new ArrayList<>();
                tempList.add(tempString);
                wordList.put(length, tempList);
            }

        }


        this.wordSet = wordset;
        this.lettersToWord = lettersToWord;
        this.sizeToWords = wordList;
    }

    public boolean isGoodWord(String word, String base) {
        boolean result = false;
        if (wordSet.contains(word) && !word.contains(base)) {
            result = true;
        }
        return result;
    }


    // Takes a string and find all its anagrams
    public List<String> getAnagrams(String targetWord) {

        ArrayList<String> result;
        String convertedTargetWord = sortLetters(targetWord);
        result = lettersToWord.get(convertedTargetWord);
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (char temp : alphabet) {
            String newWord = word + temp;
            String sortedNewWord = sortLetters(newWord);
            if (lettersToWord.containsKey(sortedNewWord)) {
                result.addAll(lettersToWord.get(sortedNewWord));
            }
        }


        return result;
    }

    //called by Default action
    public String pickGoodStarterWord() {


        String result = "";

        int maxlength = MAX_WORD_LENGTH;
        int minimumAnagrams = MIN_NUM_ANAGRAMS;

        int randomNumberOne;
        String randomWord;

        //get the arraylist that contains all words with length = wordlength
        ArrayList<String> fromSizeToWords = sizeToWords.get(wordlength);
        int pickedArrayListLength = fromSizeToWords.size();
        //make a new arraylist that contains word with wordlength and  has minimum number of Anagrams
        ArrayList<String> possibleWords = new ArrayList<String>();
        for (String key : lettersToWord.keySet()) {
            if (key.length() == wordlength && lettersToWord.get(key).size() >= minimumAnagrams) {
                possibleWords.addAll(lettersToWord.get(key));
            }
        }
        randomNumberOne = random.nextInt(possibleWords.size());
        result = possibleWords.get(randomNumberOne);




        return result;

    }

    public void increaseWordLength(){
        if(this.wordlength>=7){
            this.wordlength=7;
        }
        else{
            this.wordlength+=1;
        }
    }
    public void decreaseWordLength(){
        if(this.wordlength<=3){
            this.wordlength=3;
        }
        else{
            this.wordlength-=1;
        }
    }

    //Takes input string and returns it in alphabetical sorted order
    //in: post return: opst
    public String sortLetters(String word) {
        String result;
        char[] temp = word.toCharArray();
        Arrays.sort(temp);
        result = new String(temp);

        return result;
    }
}
