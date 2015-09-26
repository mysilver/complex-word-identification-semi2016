package wordnet;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

import java.io.IOException;
import java.net.URL;

public class WordNet {

    IRAMDictionary dictionary;
    WordnetStemmer stemmer;

    public WordNet() throws IOException {
        URL url = getClass().getClassLoader().getResources("dict").nextElement();
        dictionary = new RAMDictionary(url, ILoadPolicy.BACKGROUND_LOAD);
        dictionary.open();
        stemmer = new WordnetStemmer(this.dictionary);
    }

    public int getWordIdCount(String term, POS pos) {
        try {
            String lemma = stemmer.findStems(term, pos).get(0);
            return dictionary.getIndexWord(lemma,pos).getWordIDs().size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTagSenseCount(String term, POS pos) {
        try {
            String lemma = stemmer.findStems(term, pos).get(0);
            return dictionary.getIndexWord(lemma,pos).getTagSenseCount();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        WordNet wordNet = new WordNet();
    }
}
