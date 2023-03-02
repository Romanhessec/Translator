import java.util.ArrayList;
import java.util.Arrays;

public class Word implements Comparable {

    private String word;
    private String word_en;
    private String type;
    private String[] singular;
    private String[] plural;
    private ArrayList<Definition> definitions;

    public Word() {
    }

    public Word(String word, String word_en, String type, String[] singular, String[] plural, ArrayList<Definition> definitions) {
        this.word = word;
        this.word_en = word_en;
        this.type = type;
        this.singular = singular;
        this.plural = plural;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord_en() {
        return word_en;
    }

    public void setWord_en(String word_en) {
        this.word_en = word_en;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getSingular() {
        return singular;
    }

    public void setSingular(String[] singular) {
        this.singular = singular;
    }

    public String[] getPlural() {
        return plural;
    }

    public void setPlural(String[] plural) {
        this.plural = plural;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", word_en='" + word_en + '\'' +
                ", type='" + type + '\'' +
                ", singular=" + Arrays.toString(singular) +
                ", plural=" + Arrays.toString(plural) +
                ", definitions=" + definitions +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Word theWord = (Word) o;
        return this.word.compareToIgnoreCase(theWord.word);
    }
}
