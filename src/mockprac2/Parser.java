package mockprac2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Parser {
    private final String parsedString;
    private final boolean grabEmpty;

    private Parser(String parsedString) {
        this(parsedString, false);
    }

    private Parser(String parsedString, boolean grabEmpty) {
        this.parsedString = parsedString;
        this.grabEmpty = grabEmpty;
    }

    public static Parser parse(List lines) {
        return Parser.parse(lines, false);
    }

    public static Parser parse(List lines, boolean grabEmpty) {
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

        return new Parser(constructedString.toString(), grabEmpty);
    }

    public final Parser wordcount() {
        if (this.parsedString.isEmpty()) {
            return new Parser("0");
        }
        return new Parser(String.valueOf(this.getWords().length));
    }

    public final Parser linecount() {
        if (this.grabEmpty) {
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

        Arrays.stream(this.getSentences()).forEach(sentence -> {
            if (sentence.contains(word)) {
                sentencesWithWord.add(sentence);
            }
        });

        if (sentencesWithWord.size() == 0) {
            return new Parser("", true);
        }

        return Parser.parse(sentencesWithWord);
    }

    public final Parser chop(int start, int end) {
        final List<String> sentencesAfterChop = new ArrayList<>();

        Arrays.stream(this.getSentences()).forEach(sentence -> {
            if (start <= sentence.length()) {
                final int actualStart = start > 0 ? start - 1 : 0;
                final int actualEnd = end <= sentence.length() ? end : sentence.length();
                final String newSentence = sentence.substring(actualStart, actualEnd);
                sentencesAfterChop.add(newSentence);
            } else {
                sentencesAfterChop.add("");
            }
        });

        return Parser.parse(sentencesAfterChop);
    }

    public final Parser shuffle() {
        final List<String> sentencesAfterShuffle = new ArrayList<>();

        Arrays.stream(this.getSentences()).forEach(sentence -> {
            final Parser sentenceParser = new Parser(sentence);
            final String[] wordsInSentence = sentenceParser.getWords();
            final StringBuilder sentenceAfterShuffle = new StringBuilder();
            for (int i = 0, stringsSize = wordsInSentence.length; i < stringsSize; i++) {
                final String word = wordsInSentence[i];
                if (word.length() > 3) {
                    int[] letterIndexes = IntStream.range(0, word.length()).filter(c -> Character.isLetter(word.charAt(c))).toArray();
                    final int secondIndex = letterIndexes[1];
                    final int secondLastIndex = letterIndexes[letterIndexes.length - 2];

                    final String newWord = word.substring(0, secondIndex) +
                            word.substring(secondIndex + 1, secondLastIndex + 1) +
                            word.charAt(secondIndex) +
                            word.substring(secondLastIndex + 1);
                    sentenceAfterShuffle.append(newWord);
                } else {
                    // don't manipulate
                    sentenceAfterShuffle.append(word);
                }

                if (i != stringsSize - 1) {
                    sentenceAfterShuffle.append(" ");
                }
            }
            sentencesAfterShuffle.add(sentenceAfterShuffle.toString());
        });

        return Parser.parse(sentencesAfterShuffle);
    }

    private String[] getWords() {
        return this.parsedString.split("\\s+");
    }

    private String[] getSentences() {
        return this.parsedString.split("\\r?\\n", -1);
    }

    @Override
    public String toString() {
        return this.parsedString;
    }
}
