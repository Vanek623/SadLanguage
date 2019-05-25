import java.util.ArrayList;

class Parser {
    private ArrayList<Token> tokens;
    private int pos = -1;
    private boolean firstCheck = true;

    Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    boolean lang(boolean hasOpenBody) {
        if (firstCheck) {
            for (Token token : tokens) {
                if (token.getType().equals(TokenType.UNKNOWN)) {
                    System.out.println("Code has unknown words...");
                    return false;
                }
            }
            firstCheck = false;
        }
        int lastPos = pos;
        while (pos < tokens.size() - 1) {
            boolean endBody = close_barace_c();
            if (!(endBody && hasOpenBody) && !((exp_value() || exp_collect()) && eol()) && !exp_goto()) {
                System.out.println("The error was founded after the word № " + pos + "(" + tokens.get(pos).getContent() + ")");
                pos = lastPos;
                return false;
            }
            if (endBody) {
                back();
                break;
            }
        }
        return true;
    }

    private boolean exp_collect() {
        return exp_set() || exp_list();
    }

    private boolean exp_set() {
        int lastPos = pos;
        if (set() && (col_op() || print()) && eol()) {
            back();
            return true;
        } else
            pos = lastPos;
        return false;
    }

    private boolean exp_list() {
        int lastPos = pos;
        if (list() && (col_op() || print()) && eol()) {
            back();
            return true;
        } else
            pos = lastPos;
        return false;
    }

    private boolean col_op() {
        int lastPos = pos;
        if ((add() || get() || delete() || update()) && (var() || num()))
            return true;
        else
            pos = lastPos;
        return false;
    }

    private boolean exp_goto() {
        return exp_while() || exp_if() || exp_for() || exp_else();
    }

    private boolean exp_while() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.WHILE))
            return exp_braces() && open_brace_c() && lang(true) && close_barace_c();
        else
            back();
        return false;
    }

    private boolean exp_if() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.IF))
            return exp_braces() && open_brace_c() && lang(true) && close_barace_c();
        else
            back();
        return false;
    }

    private boolean open_brace_c() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.CURLY_BRAKER_OPEN)) {
            return true;
        } else
            back();
        return false;
    }

    private boolean close_barace_c() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.CURLY_BRAKER_CLOSE))
            return true;
        else
            back();
        return false;
    }

    private boolean exp_for() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.FOR))
            return open_brace_r() && exp_value() && eol() && exp_value() && eol() && exp_value() && close_barace_r() && open_brace_c() && lang(true) && close_barace_c();
        else
            back();
        return false;
    }

    private boolean open_brace_r() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.ROUND_BRAKER_OPEN)) {
            return true;
        } else
            back();
        return false;
    }

    private boolean close_barace_r() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.ROUND_BRAKER_CLOSE))
            return true;
        else
            back();
        return false;
    }

    private boolean exp_else() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.ELSE))
            //return open_brace_c() && lang() && close_barace_c();
            return lang(true);
        else
            back();
        return false;
    }

    private boolean exp_value() {
        int lastPos = pos;

        if (exp_braces() && operation()) {
            //System.out.println("Brace Op success on " + pos);
            return exp_value();
        }
        pos = lastPos;


        if (var() && operation() && (list() || set()) && get() && (num() || var()) && eol()){
            back();
            return true;
        }
        pos = lastPos;

        if ((var() || num()) && operation()) {
            //System.out.println("Var Op success on " + pos);
            return exp_value();
        }

        pos = lastPos;
        if (exp_braces() && (eol() || close_barace_r())) {
            back();
            //System.out.println("Single brace success on " + pos);
            return true;
        }

        pos = lastPos;
        if ((var() || num()) && (close_barace_r() || eol())) {
            back();
            //System.out.println("Single var success on " + pos);
            return true;
        } else {
            pos = lastPos;
            //System.out.println("Main error on " + pos);
            return false;
        }
    }

    /*
            Проверяет скобочные выражения
            Оканчивает работу на позиции закрывающей скобки
     */
    private boolean exp_braces() {
        int lastPos = pos;
        if (open_brace_r() && exp_value() && close_barace_r()) {
            //System.out.println("Brace success on " + pos);
            return true;
        } else {
            //System.out.println("Brace error on " + pos);
            pos = lastPos;
            return false;
        }
    }

    private boolean var() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.VAR))
            return true;
        else
            back();
        return false;
    }

    private boolean num() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.CONST))
            return true;
        else
            back();
        return false;
    }

    private boolean operation() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.OPERATION))
            return true;
        else
            back();
        return false;
    }

    private boolean eol() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.END_LINE))
            return true;
        else
            back();
        return false;
    }

    private boolean set() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.SET))
            return true;
        else
            back();
        return false;
    }

    private boolean list() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.LIST))
            return true;
        else
            back();
        return false;
    }

    private boolean add() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.ADD))
            return true;
        else
            back();
        return false;
    }

    private boolean print() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.PRINT))
            return true;
        else
            back();
        return false;
    }

    private boolean update() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.UPDATE))
            return true;
        else
            back();
        return false;
    }

    private boolean delete() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.DELETE))
            return true;
        else
            back();
        return false;
    }

    private boolean get() {
        next();
        if (tokens.get(pos).getType().equals(TokenType.GET))
            return true;
        else
            back();
        return false;
    }

    private void next() {
        if (pos < tokens.size() - 1)
            pos++;
    }

    private void back() {
        pos--;
    }

    ArrayList<Token> optimise() {
        pos = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getType().equals(TokenType.FOR))
                tokens = forToWhile(i);
        }
        return tokens;
    }

    private ArrayList<Token> forToWhile(int begin) {
        int end;
        int i = begin;
        ArrayList<Token> tmp = new ArrayList<>();
        ArrayList<Token> out = new ArrayList<>();

        while (true) {
            if (tokens.get(i).getType().equals(TokenType.CURLY_BRAKER_OPEN)) {      //Поиск конца головы цикла фор
                end = i + 1;
                break;
            } else tmp.add(tokens.get(i));
            i++;
        }

        i = 2;

        for (int j = 0; j < begin; j++) {       //добавление к выходному списку все что было до цикла
            out.add(tokens.get(j));
        }
        do {
            out.add(tmp.get(i));                //добавление инициализации счетчика
            i++;
        } while (!tmp.get(i).getType().equals(TokenType.END_LINE));

        i++;
        out.add(new Token(TokenType.END_LINE, ";"));
        out.add(new Token(TokenType.WHILE, "while"));
        out.add(new Token(TokenType.ROUND_BRAKER_OPEN, "("));

        do {
            out.add(tmp.get(i));        //добавление условия
            i++;
        } while (!tmp.get(i).getType().equals(TokenType.END_LINE));

        i++;
        out.add(new Token(TokenType.ROUND_BRAKER_CLOSE, ")"));
        out.add(new Token(TokenType.CURLY_BRAKER_OPEN, "{"));

        do {
            out.add(tmp.get(i));
            i++;
        } while (!tmp.get(i).getType().equals(TokenType.ROUND_BRAKER_CLOSE));

        out.add(new Token(TokenType.END_LINE, ";"));

        for (int j = end; j < tokens.size(); j++) {
            out.add(tokens.get(j));
        }
        return out;
    }
}
