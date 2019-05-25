import java.util.ArrayList;
import java.util.regex.Pattern;

class Lexer {
    private String input;
    private int max_pos;

    private ArrayList<Token> tokens;

    private String[] regexes = {
            "for",
            "while",
            "if",
            "else",

            "\\(",
            "\\)",
            "\\{",
            "}",

            "<\\+",
            ">>>",
            "<<",
            ">>",
            "<-",

            "[a-zA-Z0-9]+",      //CONST
            "\\$[a-zA-Z0-9]*", //VAR
            "@[a-zA-Z0-9]*",    //LIST
            "#[a-zA-Z0-9]*",    //SET
            "[-=<>*/+!]+", // OPERATION
            ";"         //END_LINE
    };
    private TokenType[] types = {
            TokenType.FOR,
            TokenType.WHILE,
            TokenType.IF,
            TokenType.ELSE,

            TokenType.ROUND_BRAKER_OPEN,
            TokenType.ROUND_BRAKER_CLOSE,
            TokenType.CURLY_BRAKER_OPEN,
            TokenType.CURLY_BRAKER_CLOSE,

            TokenType.ADD,
            TokenType.PRINT,
            TokenType.UPDATE,
            TokenType.GET,
            TokenType.DELETE,

            TokenType.CONST,
            TokenType.VAR,
            TokenType.LIST,
            TokenType.SET,
            TokenType.OPERATION,
            TokenType.END_LINE,
            TokenType.UNKNOWN
    };


    Lexer(String input) {
        tokens = new ArrayList<>();
        this.input = input.replaceAll("[\n\t ]", "");
        max_pos = this.input.length();

        System.out.println(this.input);
    }

    ArrayList<Token> tokenize() {
        int pos = 0;
        String str = "";

        while (true) {
           // System.out.println(str);
            if (pos == max_pos) {
                tokens.add(new Token(types[success(str)], str));
                break;
            }

            String tmp = str + input.charAt(pos);
            int count = successCount(tmp);

            if (count == 0) {
                pos--;
                tokens.add(new Token(types[success(str)], str));
                str = "";
            } else
                str += input.charAt(pos);

            pos++;
        }

        return tokens;
    }

    private int success(String str) {
        for (int i = 0; i < regexes.length; i++)
            if (Pattern.matches(regexes[i], str))
                return i;

        return regexes.length;
    }

    private int successCount(String str) {
        int count = 0;

        for (String regex : regexes)
            if (Pattern.matches(regex, str))
                count++;

        //System.out.println("Совпадений " + count);
        return count;
    }

    public void printWords() {
        int i = 0;
        for (Token token : tokens) {
            System.out.println(i + ". " + token.getType() + ": " + token.getContent() + ", priority: " + token.getPriority());
            i++;
        }
    }
}
