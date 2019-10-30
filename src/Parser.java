import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<String> lines;

    private Parser(List<String> lines) {
        this.lines = lines;
    }

    public final static Parser parse(List lines) {
        final List<String> newLines = new ArrayList<>();
        for (Object line : lines) {
            newLines.add(String.valueOf(line));
        }
        return new Parser(newLines);
    }

    @Override
    public String toString() {
        final StringBuilder constructedString = new StringBuilder();
        final List<String> strings = this.lines;
        for (int i = 0, stringsSize = strings.size(); i < stringsSize; i++) {
            String line = strings.get(i);
            constructedString.append(line);
            if (i != stringsSize - 1) {
                constructedString.append("\n");
            }
        }

        return constructedString.toString();
    }
}
