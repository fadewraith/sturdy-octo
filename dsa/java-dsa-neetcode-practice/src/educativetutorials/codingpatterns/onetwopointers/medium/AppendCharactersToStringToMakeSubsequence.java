package educativetutorials.codingpatterns.onetwopointers.medium;

public class AppendCharactersToStringToMakeSubsequence {

    public int appendCharacters(String source, String target) {
        int sourceIndex = 0;                 // current position in source
        int targetIndex = 0;                 // next character index to match in target
        final int sourceLength = source.length();
        final int targetLength = target.length();

        // Walk through source and try to match target in order
        while (sourceIndex < sourceLength && targetIndex < targetLength) {
            if (source.charAt(sourceIndex) == target.charAt(targetIndex)) {
                targetIndex += 1;            // matched target[targetIndex], move to the next needed char
            }
            sourceIndex += 1;                // always advance in source
        }
        // targetLength - targetIndex is exactly how many characters remain unmatched in target
        // and therefore must be appended to source.
        return targetLength - targetIndex;
    }

    public static void main(String[] args) {
        AppendCharactersToStringToMakeSubsequence solution = new AppendCharactersToStringToMakeSubsequence();
        String[] sources = {
                "axbyc",
                "abc",
                "a",
                "ab",
                "xyz"
        };

        String[] targets = {
                "abcde",
                "abcbc",
                "a",
                "aba",
                "abc"
        };

        for (int i = 0; i < sources.length; ++i) {
            int result = solution.appendCharacters(sources[i], targets[i]);
            System.out.println((i + 1) + "\tSource: '" + sources[i] + "'");
            System.out.println("\tTarget: '" + targets[i] + "'");
            System.out.println("\tResult: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
