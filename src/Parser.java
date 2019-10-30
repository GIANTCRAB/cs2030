import java.util.ArrayList;
import java.util.Arrays;
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
        if (this.parsedString.isEmpty()) {
            return new Parser("0");
        }
        return new Parser(String.valueOf(this.getWords().length));
    }

    public final Parser linecount() {
        if (this.parsedString.isEmpty()) {
            return new Parser("0");
        }
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

    public final Parser grab(String word) {
        final List<String> sentencesWithWord = new ArrayList<>();

        Arrays.stream(this.parsedString.split("\n")).forEach(sentence -> {
            if (sentence.contains(word)) {
                sentencesWithWord.add(sentence);
            }
        });

        return Parser.parse(sentencesWithWord);
    }

    private String[] getWords() {
        return this.parsedString.split("\\s+");
    }

    @Override
    public String toString() {
        return this.parsedString;
    }
}
