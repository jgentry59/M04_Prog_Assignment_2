import java.util.*;
import java.io.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java CountKeywords <Java source file>");
            System.exit(1);
        }

        String filename = args[0];
        File file = new File(filename);

        if (file.exists()) {
            int keywordCount = countKeywords(file);
            System.out.println("Total keywords in " + filename + ": " + keywordCount);
        } else {
            System.out.println("File " + filename + " does not exist");
        }
    }

    public static int countKeywords(File file) throws Exception {
        String[] keywords = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
                "const", "continue", "default", "do", "double", "else", "enum", "extends", "for", "final",
                "finally", "float", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
                "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
                "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile",
                "while", "true", "false", "null"};

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        int count = 0;
        boolean inComment = false;
        boolean inString = false;

        Scanner input = new Scanner(file);

        while (input.hasNext()) {
            String line = input.nextLine().trim();

            if (!inComment) {
                if (line.startsWith("/*")) { // Check for multi line comment start
                    inComment = true;
                    if (line.endsWith("*/")) {
                        inComment = false; // Check for multi line comment end
                    }
                } else if (line.startsWith("//")) {
                    continue; // Skip single line comments
                }

                for (int i = 0; i < line.length(); i++) {
                    char currentChar = line.charAt(i);

                    if (currentChar == '"') {
                        inString = !inString; // Toggle string flag
                    }

                    if (!inString && !inComment) { // Check if not in string or comment
                        StringBuilder wordBuilder = new StringBuilder();

                        while (i < line.length() && Character.isJavaIdentifierPart(line.charAt(i))) {
                            wordBuilder.append(line.charAt(i));
                            i++;
                        }

                        String word = wordBuilder.toString();
                        if (keywordSet.contains(word)) {
                            count++;
                        }
                    }
                }
            }

            if (inComment && line.endsWith("*/")) {
                inComment = false; // Check for end of multi line comment
            }
        }

        return count;
    }
}
