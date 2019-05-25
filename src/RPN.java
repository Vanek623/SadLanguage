import java.util.ArrayList;
import java.util.Stack;

class RPN {
    private ArrayList<Token> tokens;
    private ArrayList<Token> result;
    private Stack<Token> stack = new Stack<>();

    RPN(ArrayList<Token> tokens) {
        this.tokens = tokens;
        result = new ArrayList<>();
    }

    ArrayList<Token> translate() {
        for (Token tmp : tokens) {
            if (tmp.getType().equals(TokenType.VAR) || tmp.getType().equals(TokenType.CONST)
                    || tmp.getType().equals(TokenType.LIST) || tmp.getType().equals(TokenType.SET))
                result.add(tmp);
            else if (tmp.getType().equals(TokenType.ROUND_BRAKER_CLOSE)) {
                while (true) {
                    if (stack.peek().getType().equals(TokenType.ROUND_BRAKER_OPEN)) {
                        stack.pop();
                        break;
                    }
                    result.add(stack.pop());
                }

            } else if (tmp.getType().equals(TokenType.OPERATION)) {
                while (true) {
                    if (stack.empty() || tmp.getPriority() > stack.peek().getPriority() || !stack.peek().getType().equals(TokenType.OPERATION)) {
                        stack.push(tmp);
                        break;
                    } else
                        result.add(stack.pop());
                }

            } else if (tmp.getType().equals(TokenType.END_LINE)) {
                while (true) {
                    if (stack.empty() || tmp.getPriority() > stack.peek().getPriority())
                        break;
                    else result.add(stack.pop());
                }

            } else if (tmp.getType().equals(TokenType.CURLY_BRAKER_OPEN)) {
                addGoTo(!stack.peek().getType().equals(TokenType.ELSE));
                stack.push(tmp);
                stack.push(new Token(TokenType.CONST, String.valueOf(result.size() - 2)));

            } else if (tmp.getType().equals(TokenType.CURLY_BRAKER_CLOSE)) {
                String ref = stack.pop().getContent();
                stack.pop();
                result.get(Integer.valueOf(ref)).setContent(String.valueOf(result.size()));

                if (!stack.pop().getType().equals(TokenType.IF)) {
                    result.add(new Token(TokenType.CONST, stack.pop().getContent()));
                    result.add(new Token(TokenType.GOTO));
                    result.get(Integer.valueOf(ref)).setContent(String.valueOf(result.size()));
                    //stack.push(new Token(TokenType.CONST, String.valueOf(result.size() - 2)));
                }
            } else if (tmp.getType().equals(TokenType.ELSE)) {
                addGoTo(false);
                result.get(Integer.valueOf(stack.pop().getContent())).setContent(String.valueOf(result.size()));
                stack.push(new Token(TokenType.CONST, String.valueOf(result.size() - 2)));

            } else if (tmp.getType().equals(TokenType.WHILE)) {
                stack.push(new Token(TokenType.CONST, String.valueOf(result.size())));
                stack.push(tmp);

            } else {
                stack.push(tmp);
            }

            //printLog();
        }

        printLog();
        return result;
    }

    private void addGoTo(boolean hasCond) {
        result.add(new Token(TokenType.CONST));
        if (!hasCond)
            result.add(new Token(TokenType.GOTO));
        else
            result.add(new Token(TokenType.GOTO_IF));
    }

    void printLog(){
        System.out.print("RPN: ");
        for (Token token : result) {
            System.out.print(token.getContent() + " ");
        }
        System.out.println();
        System.out.print("Stack: ");
        for (Token token : stack) {
            System.out.print(token.getContent() + " ");
        }
        System.out.println();
    }
}
