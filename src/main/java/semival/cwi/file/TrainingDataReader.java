package semival.cwi.file;

import semival.cwi.ComplexTermCandidate;
import semival.cwi.ComplexWordSentence;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TrainingDataReader {

    public static List<ComplexWordSentence> read(String filePath) throws IOException {

        List<ComplexWordSentence> result = new ArrayList<ComplexWordSentence>();

        List<String> oneSentence = null;
        String pSentence = null;
        for (String line : Files.readAllLines(Paths.get(filePath), Charset.defaultCharset())) {
            String[] tabs = line.split("\t");
            if (oneSentence == null) {
                oneSentence = new ArrayList<String>();
                oneSentence.add(line);
                pSentence = tabs [0];
            }
            else if (!tabs[0].equals(pSentence)) {
                result.add(createSentence(oneSentence));
                oneSentence = new ArrayList<String>();
                pSentence = tabs[0];
                oneSentence.add(line);
            } else
                oneSentence.add(line);
        }
        return result;
    }

    private static ComplexWordSentence createSentence(List<String> rows) {
        ComplexWordSentence result = new ComplexWordSentence();
        for (String row : rows) {
            String[] lines = row.split("\t");
            result.setText(lines[0]);
            result.addTerm(new ComplexTermCandidate(lines[1], Integer.valueOf(lines[3])==1, Integer.valueOf(lines[2])));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        List<ComplexWordSentence> read = read("/home/mohammad-ali/Desktop/cwi_training.txt");
        int count = 0;
        int complexWordCount = 0;
        int totalCWICount = 0;
        for (ComplexWordSentence complexWordSentence : read) {
            for (ComplexTermCandidate complexTermCandidate : complexWordSentence.getTerms()) {
                if (complexTermCandidate.isComplex())
                    complexWordCount ++;
                totalCWICount++;
            }
            System.out.println("#" + (++count) + " " + complexWordSentence);

        }
        System.out.println("Complex term count :" + complexWordCount);
        System.out.println("Total Candidate Terms :" + totalCWICount);
        System.out.println("ratio (complex/total) :" + complexWordCount*1.0 / totalCWICount);

    }
}
