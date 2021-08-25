package ch.open.dto;

public class NewFact {

    public String fact;

    private NewFact() {
        // Jackson
    }

    public NewFact(String fact) {
        this.fact = fact;
    }

    @Override
    public String toString() {
        return "NewFact{" +
            "fact='" + fact + '\'' +
            '}';
    }
}
