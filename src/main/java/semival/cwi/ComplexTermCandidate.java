package semival.cwi;

/**
 * Created by mohammad-ali on 9/23/15.
 */
public class ComplexTermCandidate {

    private boolean complex = false;

    private String word;
    private int index;

    public ComplexTermCandidate(String word, boolean complex, int index) {
        this.complex = complex;
        this.word = word;
        this.index = index;
    }

    public boolean isComplex() {
        return complex;
    }

    public void setComplex(boolean complex) {
        this.complex = complex;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "ComplexTermCandidate{" +
                "complex=" + complex +
                ", word='" + word + '\'' +
                ", index=" + index +
                '}';
    }
}
