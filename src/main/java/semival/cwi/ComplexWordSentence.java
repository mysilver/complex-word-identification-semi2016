package semival.cwi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammad-ali on 9/23/15.
 */
public class ComplexWordSentence {

    private String text;
    private List<ComplexTermCandidate> terms = new ArrayList<ComplexTermCandidate>();

    public ComplexWordSentence() {
    }

    public ComplexWordSentence(String text, List<ComplexTermCandidate> terms) {
        this.text = text;
        this.terms = terms;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ComplexTermCandidate> getTerms() {
        return terms;
    }

    public void setTerms(List<ComplexTermCandidate> terms) {
        this.terms = terms;
    }

    public void addTerm(ComplexTermCandidate term) {
        this.terms.add(term);
    }

    @Override
    public String toString() {
        return "ComplexWordSentence{" +
                "text='" + text + '\'' +
                ", terms=" + terms +
                '}';
    }
}
