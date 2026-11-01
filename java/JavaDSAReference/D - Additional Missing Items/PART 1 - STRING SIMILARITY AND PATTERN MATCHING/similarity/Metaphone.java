package algorithms.strings.similarity;

/**
 * METAPHONE ALGORITHM
 *
 * WHAT IT IS:
 * An improved phonetic algorithm over Soundex (Lawrence Philips, 1990). It uses a much
 * larger set of rules to handle inconsistencies in English spelling and pronunciation
 * more accurately. Similar-sounding words get the same code: "Smith" and "Smyth" -> SM0.
 *
 * OUTPUT ALPHABET: 16 consonant sounds  B X S K J T F H L M N P R 0 W Y
 *   ('0' (zero) stands for "th", 'X' stands for "sh"). Vowels are kept only as the first letter.
 *
 * RULES (this follows the classic rule set, as also used by Apache Commons Codec):
 * Start of word:  AE, GN, KN, PN, WR -> drop first letter;  X -> S;  WH -> W
 * Repeated adjacent letters count once (except C).
 *   B -> B, but silent in final "MB" (dumb)
 *   C -> X in "CIA" and "CH" (but K in "SCH"), S before I/E/Y (silent in SCI/SCE/SCY), else K
 *   D -> J in "DGE"/"DGI"/"DGY", else T
 *   G -> silent in "GH" not before a vowel and in "GN"/"GNED"; J before I/E/Y; else K
 *   H -> silent after C,S,P,T,G or when not followed by a vowel; else H
 *   K -> silent after C;  P -> F before H;  Q -> K;  V -> F;  X -> KS;  Z -> S
 *   S -> X in "SH", "SIO", "SIA";  T -> X in "TIA"/"TIO", 0 in "TH", silent in "TCH"
 *   W, Y -> kept only before a vowel
 *   F J L M N R -> unchanged
 *
 * COMPLEXITY: O(N) for a word of length N.
 * (Double Metaphone, a later version, handles many non-English names too.)
 */
public class Metaphone {

    private static final String VOWELS = "AEIOU";
    private static final String FRONT_VOWELS = "EIY";
    private static final String MAKES_H_SILENT = "CSPTG";

    /** Full Metaphone code (no length limit). */
    public static String getCode(String s) {
        return getCode(s, Integer.MAX_VALUE);
    }

    /** Metaphone code cut to maxLength characters (classic implementations use 4). */
    public static String getCode(String s, int maxLength) {
        if (s == null) return "";
        String w = s.toUpperCase().replaceAll("[^A-Z]", "");
        if (w.isEmpty()) return "";
        if (w.length() == 1) return w;

        // ---- initial-letter exceptions ----
        if (w.startsWith("KN") || w.startsWith("GN") || w.startsWith("PN")
                || w.startsWith("AE") || w.startsWith("WR")) {
            w = w.substring(1);
        } else if (w.charAt(0) == 'X') {
            w = "S" + w.substring(1);
        } else if (w.startsWith("WH")) {
            w = "W" + w.substring(2);
        }

        StringBuilder code = new StringBuilder();
        int len = w.length();

        for (int n = 0; n < len && code.length() < maxLength; n++) {
            char c = w.charAt(n);

            // Doubled letters sound like one (except C: "accident" -> AKS...)
            if (c != 'C' && prev(w, n) == c) continue;

            switch (c) {
                case 'A': case 'E': case 'I': case 'O': case 'U':
                    if (n == 0) code.append(c); // vowels only matter at the start
                    break;

                case 'B':
                    if (!(prev(w, n) == 'M' && n == len - 1)) code.append('B'); // "dumb"
                    break;

                case 'C':
                    if (prev(w, n) == 'S' && FRONT_VOWELS.indexOf(next(w, n)) >= 0) break; // "science": silent
                    if (w.startsWith("CIA", n)) { code.append('X'); break; }               // "special"
                    if (FRONT_VOWELS.indexOf(next(w, n)) >= 0) { code.append('S'); break; } // "city"
                    if (prev(w, n) == 'S' && next(w, n) == 'H') { code.append('K'); break; } // "school"
                    if (next(w, n) == 'H') {                                                 // "church"
                        // Word-initial "CH" + consonant sounds like K ("Christ")
                        code.append(n == 0 && len >= 3 && !isVowel(w.charAt(2)) ? 'K' : 'X');
                        break;
                    }
                    code.append('K');
                    break;

                case 'D':
                    if (next(w, n) == 'G' && FRONT_VOWELS.indexOf(next(w, n + 1)) >= 0) {
                        code.append('J'); // "edge", "budget"
                        n += 2;
                    } else {
                        code.append('T');
                    }
                    break;

                case 'G':
                    if (next(w, n) == 'H' && n + 1 == len - 1) break;                  // final "GH": "sigh"
                    if (next(w, n) == 'H' && n + 2 < len && !isVowel(w.charAt(n + 2))) break; // "night"
                    if (n > 0 && (w.startsWith("GN", n) && n + 2 == len
                            || w.startsWith("GNED", n) && n + 4 == len)) break;        // "sign", "signed"
                    if (FRONT_VOWELS.indexOf(next(w, n)) >= 0 && prev(w, n) != 'G') {
                        code.append('J');                                                // "gem"
                    } else {
                        code.append('K');                                                // "go"
                    }
                    break;

                case 'H':
                    if (n == len - 1) break;                                   // final H is silent
                    if (n > 0 && MAKES_H_SILENT.indexOf(w.charAt(n - 1)) >= 0) break; // part of CH/SH/PH/TH/GH
                    if (isVowel(next(w, n))) code.append('H');
                    break;

                case 'K':
                    if (prev(w, n) != 'C') code.append('K'); // "back": CK sounds once
                    break;

                case 'P':
                    code.append(next(w, n) == 'H' ? 'F' : 'P'); // "phone"
                    break;

                case 'Q':
                    code.append('K');
                    break;

                case 'S':
                    if (w.startsWith("SH", n) || w.startsWith("SIO", n) || w.startsWith("SIA", n)) {
                        code.append('X'); // "ship", "session"
                    } else {
                        code.append('S');
                    }
                    break;

                case 'T':
                    if (w.startsWith("TIA", n) || w.startsWith("TIO", n)) { code.append('X'); break; } // "nation"
                    if (w.startsWith("TCH", n)) break;                                             // "watch"
                    code.append(w.startsWith("TH", n) ? '0' : 'T');                               // "the" -> 0
                    break;

                case 'V':
                    code.append('F');
                    break;

                case 'W':
                case 'Y':
                    if (isVowel(next(w, n))) code.append(c); // "water", "yes"; silent in "how", "say"
                    break;

                case 'X':
                    code.append("KS");
                    break;

                case 'Z':
                    code.append('S');
                    break;

                default: // F, J, L, M, N, R
                    code.append(c);
            }
        }
        if (code.length() > maxLength) code.setLength(maxLength); // "X" -> "KS" can overshoot by one
        return code.toString();
    }

    private static boolean isVowel(char c) {
        return VOWELS.indexOf(c) >= 0;
    }

    private static char prev(String w, int n) {
        return n > 0 ? w.charAt(n - 1) : '\0';
    }

    private static char next(String w, int n) {
        return n + 1 < w.length() ? w.charAt(n + 1) : '\0';
    }

    public static void main(String[] args) {
        System.out.println("--- METAPHONE DEMO ---");
        String[][] pairs = {
            {"Smith", "Smyth"}, {"Knight", "Night"}, {"Philip", "Filip"},
            {"Catherine", "Kathryn"}, {"Wright", "Rite"},
            {"Thompson", "Tomson"} // known limitation: classic Metaphone always reads TH as "th" (0)
        };
        for (String[] p : pairs) {
            String a = getCode(p[0]), b = getCode(p[1]);
            System.out.println(String.format("%-10s -> %-6s  %-8s -> %-6s  %s", p[0], a, p[1], b, a.equals(b) ? "MATCH" : "differ"));
        }

        // Classic test sentence (expected codes from the reference implementation)
        String sentence = "The quick brown fox jumped over the lazy dogs";
        StringBuilder sb = new StringBuilder();
        for (String word : sentence.split(" ")) sb.append(getCode(word, 4)).append(' ');
        System.out.println("\n" + sentence);
        System.out.println(sb.toString().trim()); // Expected: 0 KK BRN FKS JMPT OFR 0 LS TKS
    }
}
