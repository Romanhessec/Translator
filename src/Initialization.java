import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Initialization {

    public Initialization() {
    }

    public ArrayList<Path> fileInit(File folder){

        File[] listOfFiles = folder.listFiles();
        ArrayList<Path> files = new ArrayList<>();

        for (File file: listOfFiles){
            if (file.isFile()){
                files.add(file.toPath());
            }
        }

        return files;
    }

    public HashMap<String, List<Word>> wordsInit(ArrayList<Path> files) {

        HashMap<String, List<Word>> words = new HashMap<>();

        try {
            for (Path file : files) {

                Reader reader = Files.newBufferedReader(file);
                ArrayList<Word> wordsTemp = new Gson().fromJson(reader, new TypeToken<ArrayList<Word>>() {}.getType());

                String lang = file.getFileName().toString().split("_")[0];

                for (Word word : wordsTemp) {
                    //if the lang key is already there, we just add it to the list
                    if (words.containsKey(lang)) {
                        words.get(lang).add(word);
                    //otherwise, we make a new list with just 1 element;
                    } else {
                        List<Word> wList = new ArrayList<>();
                        wList.add(word);
                        words.put(lang, wList);
                    }
                }
                reader.close();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return words;
    }
}
