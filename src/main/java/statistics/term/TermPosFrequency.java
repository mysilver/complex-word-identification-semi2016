package statistics.term;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.logging.StanfordRedwoodConfiguration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class TermPosFrequency {

    Map<String,Integer> t_pos_freq = new HashMap<>();

    Pattern pattern = Pattern.compile("\\)|\\(|,");

    public TermPosFrequency()throws IOException {
        this(Integer.MAX_VALUE);
    }

    public TermPosFrequency(int topTerms) throws IOException {
        // read t_pos_freq.txt
        String file = getClass().getClassLoader().getResources("statistics/t_pos_freq.txt").nextElement().toString().replace("file:/", "").replace("%20"," ");
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null && ++count < topTerms) {
                String[] split = pattern.split(line);
                t_pos_freq.put(split[2], Integer.valueOf(split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int frequency(String term, String pos) {
        return t_pos_freq.get(term+"\t"+pos);
    }

    public static void main(String[] args) throws IOException {

        TermPosFrequency termPosFrequency = new TermPosFrequency();
        Properties properties = new Properties();
        properties.put("annotators","tokenize,ssplit,pos,lemma,ner,parse,dcoref");
        StanfordCoreNLP nlp = new StanfordCoreNLP(properties);
        Annotation annotation = new Annotation("This is a new text.");
        nlp.annotate(annotation);
        nlp.prettyPrint(annotation,System.out);


    }

}
