import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Testing {

    public Testing() {}

    public void testingAddWord(){

        //some fields are null when I add words, I did it so I won't make it overly complicated
        //be wary - they might throw some NullPointerExceptions
        System.out.println("Testing addWord ----------- \n");

        Word wordTrue = new Word("oniric", "dream", null, null, null, null);
        System.out.println("wordTrue " + Administration.addWord(wordTrue, "ro"));

        Word wordTrueOtherLang = new Word("italiano", "italian", null, null, null,
                null);
        System.out.println("wordTrueOtherLang " + Administration.addWord(wordTrueOtherLang, "it"));

        String[] testPisica = {"mâță", "cotoroabă", "cătușă"};
        Definition definitionTestPisica = new Definition("Dicționar de sinonime", "synonyms", 1998,
                testPisica);
        ArrayList<Definition> definitionsTestPisica = new ArrayList<>();
        definitionsTestPisica.add(definitionTestPisica);

        Word wordFalse = new Word("pisică", "cat", null, null, null,
                definitionsTestPisica);
        System.out.println("wordFalse " + Administration.addWord(wordFalse, "ro"));
    }

    public void testingRemoveWord(){

        System.out.println("\nTesting removeWord ----------- \n");

        System.out.println("wordFalse " + Administration.removeWord("sarcofag", "ro"));
        System.out.println("wordTrue " + Administration.removeWord("italiano", "it"));
    }

    public void testingAddDefinitionForWord(){

        System.out.println("\nTesting addDefinitionForWord ----------- \n");

        String[] testPisica = {"mâță", "cotoroabă", "cătușă"};
        Definition definitionTestPisica = new Definition("Dicționar de sinonime", "synonyms", 1998,
                testPisica);

        System.out.println("defintionFalse: " +  Administration.addDefinitionForWord("pisică", "ro",
                definitionTestPisica));

        String[] testPisicaDefinition = {"leu"};
        Definition addDefinitionForWordTestTrue = new Definition(null, null, 1990,
                testPisicaDefinition);

        System.out.println("definitionTrue: " + Administration.addDefinitionForWord("pisică", "ro",
                addDefinitionForWordTestTrue));
    }

    public void testingRemoveDefinition(){

        //acel String Dictionary, pentru a fii unic si pentru ca am avut libertatea de a-l gandi cum dorim, l-am
        //construit ca o combinatie intre Definition.dict, dictType si year, toate separate prin "#" pentru regex
        System.out.println("\nTesting removeDefinition ----------- \n");

        String removeDefinitionTrue= "Dicționar de sinonime#synonyms#1998";
        System.out.println("removeDefinitionTrue: " + Administration.removeDefiniton("pisică", "ro",
                removeDefinitionTrue));

        String removeDefinitionFalse = "Dictionar de sinonime#something#1473";
        System.out.println("removeDefinitionFalse: " + Administration.removeDefiniton("pisică", "ro",
                removeDefinitionFalse));
    }

    public void testingTranslateWord(){

        System.out.println("\nTesting translateWord ----------- \n");
        System.out.println("translateWord pisică from ro to fr: " + Administration.translateWord("pisică",
                "ro", "fr"));
        System.out.println("translateWord not found: " + Administration.translateWord("jeu",
                "fr", "ro"));
    }

    public void testingTranslateSentence(){

        System.out.println("\nTesting translateSentence ----------- \n");
        System.out.println("translateSentence from ro (pisică merge) to sp: " + Administration.translateSentence
                ("pisică merge", "ro", "sp"));
        System.out.println("translateSentence from ro (oniric merge) to sp: " + Administration.translateSentence
                ("oniric merge", "ro", "sp"));
        System.out.println("translateSentence language not found case: " +  Administration.translateSentence
                ("pisică merge", "ro", "kz"));
    }

    public void testingTranslateSentences(){

        System.out.println("\nTesting translateSentences ----------- \n");
        System.out.println("translateSentence from ro (pisică merge) to sp: " + Administration.translateSentences
                ("pisică merge", "ro", "sp"));
        System.out.println("translateSentence from ro (oniric merge) to sp: " + Administration.translateSentences
                ("oniric merge", "ro", "sp"));
    }

    public void testingGetDefinitionForWord(){

        System.out.println("\nTesting getDefinitionsForWord ----------- \n");
        System.out.println("getDefinitionsForWord: câine: " + Administration.getDefinitionsForWord
                ("câine", "ro"));
        System.out.println("getDefinitonsFroWord: salopetă: " + Administration.getDefinitionsForWord
                ("salopetă", "ro"));
    }

    public void testingExportDictionary(){

        //ro language won't work because I added some words with null definitions beforehand

        System.out.println("\nTesting exportDictionary ----------- \n");
        System.out.print("exportDictionary: sp: ");
        Administration.exportDictionary("sp");

        System.out.print("exportDictionary: fr: ");
        Administration.exportDictionary("fr");

        System.out.print("exportDictionary: it: ");
        Administration.exportDictionary("it");
    }
}
