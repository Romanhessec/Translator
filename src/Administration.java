import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Administration {

    //i'm using a hashmap to store the words
    static HashMap<String, List<Word>> words;

    public Administration() {
    }

    static boolean addWord(Word word, String language){

        //if we don't have any words of that language
        if (words.get(language) == null){
            ArrayList<Word> wordsToPut = new ArrayList<>();
            wordsToPut.add(word);
            words.put(language, wordsToPut);
            return true;
        }

        List<Word> wordsInLanguage = words.get(language);
        for (Word wordInLanguage : wordsInLanguage){
            if (wordInLanguage.getWord().equals(word.getWord())){

                //we check if we have to update the word (different "text" field for the Definition)
                ArrayList<Definition> wordDefinitions = wordInLanguage.getDefinitions();
                ArrayList<Definition> newWordDefinitions = word.getDefinitions();
                if (newWordDefinitions == null)
                    return false;

                for (Definition definition : wordDefinitions){
                    for (Definition newDefinition : newWordDefinitions) {
                        if (definition.getDict().equals(newDefinition.getDict())) {
                            if (!Arrays.equals(definition.getText(), newDefinition.getText())) {
                                wordInLanguage.getDefinitions().add(newDefinition);
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        }

        words.get(language).add(word);
        return true;
    }

    static boolean removeWord(String word, String language){

        List<Word> wordsInLanguage = words.get(language);
        for (Word wordInLanguage : wordsInLanguage){
            if (wordInLanguage.getWord().equals(word)){
                wordsInLanguage.remove(wordInLanguage);
                return true;
            }
        }

        return false;
    }

    static boolean addDefinitionForWord(String word, String language, Definition definition){

        //we don't have words of that language - corner case
        if (words.get(language) == null){
            return false;
        }

        List<Word> wordsInLanguage = words.get(language);

        for (Word wordInLanguage : wordsInLanguage){
            if (wordInLanguage.getWord().equals(word)){

                ArrayList<Definition> wordDefinitions = wordInLanguage.getDefinitions();
                for (Definition wordDefinition : wordDefinitions){
                    if (wordDefinition.getDict().equals(definition.getDict())){
                        if (!Arrays.equals(wordDefinition.getText(), definition.getText())){
                            wordInLanguage.getDefinitions().add(definition);
                            return true;
                        }
                        return false;
                    }
                }
                wordInLanguage.getDefinitions().add(definition);
                return true;
            }
        }
        //we are never gonna get here, I putted it just so the compiler won't complain
        return false;
    }

    static boolean removeDefiniton(String word, String language, String dictionary){

        List<Word> wordsInLanguage = words.get(language);

        String dic = dictionary.split("#")[0];
        String dicType = dictionary.split("#")[1];
        int year = Integer.parseInt(dictionary.split("#")[2]);

        for(Word wordInLanguage : wordsInLanguage){
            if (wordInLanguage.getWord().equals(word)){
                ArrayList<Definition> wordInLanguageDefinitions = wordInLanguage.getDefinitions();

                for (Definition wordInLanguageDefiniton : wordInLanguageDefinitions){

                    //the first 2 ifs is done because of my tests;
                    if(wordInLanguageDefiniton.getDict() == null)
                        continue;
                    if(wordInLanguageDefiniton.getDictType() == null)
                        continue;
                    if(wordInLanguageDefiniton.getDict().equals(dic))
                        if(wordInLanguageDefiniton.getDictType().equals(dicType))
                            if(wordInLanguageDefiniton.getYear() == year){
                                wordInLanguageDefinitions.remove(wordInLanguageDefiniton);
                                return true;
                            }

                }
                //we didn't find the definition
                return false;
            }
        }

        return false;
    }

    static String translateWord(String word, String fromLanguage, String toLanguage){

        //we save in word_en the word in english in order to search it in the other language (toLanguage)
        String word_en = null;

        List<Word> wordsInFirstLanguage = words.get(fromLanguage);
        for (Word wordFrom : wordsInFirstLanguage){
            if (wordFrom.getWord().equals(word))
                word_en = wordFrom.getWord_en();
        }

        List<Word> wordsInSecondLanguage = words.get(toLanguage);
        for (Word wordIn: wordsInSecondLanguage){
            if (wordIn.getWord_en().equals(word_en))
                return wordIn.getWord();
        }

        return "Couldn't find the word in the language you requested.";
    }

    static String translateSentence(String sentence, String fromLanguage, String toLanguage){

        List<Word> wordsInFirstLanguage = words.get(fromLanguage);
        if (wordsInFirstLanguage == null)
            return "Error - couldn't find the language.";
        List<Word> wordsInSecondLanguage = words.get(toLanguage);
        if (wordsInSecondLanguage == null)
            return "Error - couldn't find the language.";

        String returnSentence = "";
        String[] sentenceSplitted = sentence.split(" ");

        boolean foundWord = false;

        for (String word : sentenceSplitted){
            for (Word wordInFirstLanguage : wordsInFirstLanguage){
                if (wordInFirstLanguage.getWord().equals(word)){
                    for (Word wordInSecondLanguage : wordsInSecondLanguage){
                        if (wordInSecondLanguage.getWord_en().equals(wordInFirstLanguage.getWord_en())) {
                            returnSentence = returnSentence + wordInSecondLanguage.getWord() + " ";
                            foundWord = true;
                            break;
                        }
                    }
                    //if we get here, we didnt find the word in the second language
                    if (foundWord == false)
                        returnSentence = returnSentence + word + " ";
                    else
                        foundWord = false;
                }
            }

        }
        return returnSentence + ".";
    }

    static ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage){

        ArrayList<String> returnStrings = new ArrayList<>();
        String returnFirstSentence = "";
        String returnSecondSentence = "";
        String returnThirdSentence = "";

        List<Word> wordsInFirstLanguage = words.get(fromLanguage);
        if (wordsInFirstLanguage == null){
            returnStrings.add("Error - couldn't find the language.");
            return returnStrings;
        }
        List<Word> wordsInSecondLanguage = words.get(toLanguage);
        if (wordsInSecondLanguage == null){
            returnStrings.add("Error - couldn't find the language.");
            return returnStrings;
        }

        String[] sentenceSplitted = sentence.split(" ");

        boolean foundWord = false;

        for (String word : sentenceSplitted){
            for (Word wordInFirstLanguage : wordsInFirstLanguage){
                if (wordInFirstLanguage.getWord().equals(word)){
                    for (Word wordInSecondLanguage : wordsInSecondLanguage){
                        if (wordInSecondLanguage.getWord_en().equals(wordInFirstLanguage.getWord_en())) {
                            foundWord = true;
                            ArrayList<Definition> definitions = wordInSecondLanguage.getDefinitions();
                            boolean isSynonym = false;
                            for (Definition definition : definitions){
                                if (definition.getDictType().equals("synonyms")){
                                    isSynonym = true;
                                    String[] text = definition.getText();
                                    //first word is the actual word, not a synonym
                                    returnFirstSentence = returnFirstSentence + wordInSecondLanguage.getWord() + " ";
                                    if (text.length == 0)
                                        break;
                                    returnSecondSentence = returnSecondSentence + text[0] + " ";
                                    if (text.length == 1)
                                        break;
                                    returnThirdSentence = returnThirdSentence + text[1] + " ";
                                    break;
                                }
                            }
                            if (!isSynonym) {
                                returnFirstSentence = returnFirstSentence + wordInSecondLanguage.getWord() + " ";
                                if (!returnSecondSentence.equals("")){
                                    returnSecondSentence = returnSecondSentence + wordInSecondLanguage.getWord() + " ";
                                    if (!returnThirdSentence.equals(" "))
                                        returnThirdSentence = returnThirdSentence + wordInSecondLanguage.getWord() +
                                                " ";
                                }
                            }
                        }
                    }
                    //if we get here, we didnt find the word in the second language
                    if (!foundWord){
                        returnFirstSentence = returnFirstSentence + word + " ";
                        if (!returnSecondSentence.equals("")){
                            returnSecondSentence = returnSecondSentence + word + " ";
                            if (!returnThirdSentence.equals(""))
                                returnThirdSentence = returnThirdSentence + word + " ";
                        }
                    }
                    else
                        foundWord = false;
                }
            }
        }
        returnStrings.add(returnFirstSentence);
        if (!returnSecondSentence.equals("")){
            returnStrings.add(returnSecondSentence);
            if (!returnThirdSentence.equals(""))
                returnStrings.add(returnThirdSentence);
        }
        return returnStrings;
    }

    static ArrayList<Definition> getDefinitionsForWord(String word, String language){

        ArrayList<Definition> returnDefinitions = new ArrayList<>();

        List<Word> wordsInLanguage = words.get(language);
        for (Word wordInLanguage : wordsInLanguage){
            if (wordInLanguage.getWord().equals(word)){
                returnDefinitions = wordInLanguage.getDefinitions();
            }
        }

        if (returnDefinitions.isEmpty()){
            return null;
        }
        Collections.sort(returnDefinitions);
        return returnDefinitions;
    }

    static void exportDictionary(String language) {

        List<Word> wordsInLanguage = words.get(language);
        if (wordsInLanguage.isEmpty()) {
            System.out.println("Error - we haven't been able to find the language");
            return;
        }

        //first, we sort the definitions of each word
        for (Word word : wordsInLanguage)
            Collections.sort(word.getDefinitions());

        //then, we sort the words
        Collections.sort(wordsInLanguage);

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String toPut = "out_files/" + language + "_dict.json";
            Writer writer = Files.newBufferedWriter(Paths.get(toPut));

            gson.toJson(wordsInLanguage, writer);

            writer.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println("It worked! Check out_files");
        return;
    }

    //wrote this method in order to test my other methods
    static void printWords(){

        for (String key : words.keySet()){
            List<Word> toPrint = words.get(key);
            if (toPrint.size() == 0)
                continue;
            System.out.println(toPrint);
        }
    }

    public static void main(String[] args) {

        Initialization init = new Initialization();
        File folder = new File("in");
        ArrayList<Path> files = init.fileInit(folder);
        words = init.wordsInit(files);

        Testing testing = new Testing();
        testing.testingAddWord();
        testing.testingRemoveWord();
        testing.testingAddDefinitionForWord();
        testing.testingRemoveDefinition();
        testing.testingTranslateWord();
        testing.testingTranslateSentence();
        testing.testingTranslateSentences();
        testing.testingGetDefinitionForWord();
        testing.testingExportDictionary();
    }
}
