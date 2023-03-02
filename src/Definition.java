import java.util.Arrays;

public class Definition implements Comparable{

    private String dict;
    private String dictType;
    private int year;
    private String[] text;

    public Definition() {
    }

    public Definition(String dict, String dictType, int year, String[] text) {
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        this.text = text;
    }

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Definitions{" +
                "dict='" + dict + '\'' +
                ", dictType='" + dictType + '\'' +
                ", year=" + year +
                ", text=" + Arrays.toString(text) +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Definition definition = (Definition)o;
        return this.getYear() - definition.getYear();
    }
}
