import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final String parsedString;

    private Parser(String parsedString) {
        this.parsedString = parsedString;
    }

    public static Parser parse(List lines) {
        final List<String> newLines = new ArrayList<>();
        for (Object line : lines) {
            newLines.add(String.valueOf(line));
        }

        final StringBuilder constructedString = new StringBuilder();
        for (int i = 0, stringsSize = newLines.size(); i < stringsSize; i++) {
            String line = newLines.get(i);
            constructedString.append(line);
            if (i != stringsSize - 1) {
                constructedString.append("\n");
            }
        }

        return new Parser(constructedString.toString());
    }

    public final Parser wordcount() {
        return new Parser(String.valueOf(this.getWords().length));
    }

    public final Parser linecount() {
        return new Parser(String.valueOf(this.parsedString.chars().filter(x -> x == '\n').count() + 1));
    }

    public final Parser echo() {
        final StringBuilder constructedString = new StringBuilder();
        final String[] words = this.getWords();
        for (int i = 0, stringsSize = words.length; i < stringsSize; i++) {
            final String word = words[i];
            constructedString.append(word);
            if (i != stringsSize - 1) {
                constructedString.append(" ");
            }
        }

        return new Parser(constructedString.toString());
    }

    private String[] getWords() {
        return this.parsedString.split("\\s+");
    }

    @Override
    public String toString() {
        return this.parsedString;
    }
}
