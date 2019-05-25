import mySet.CustomHashSet;
import myList.CustomList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator {
    private ArrayList<Token> programm;
    private Stack<Token> stack;

    private HashMap<String, Integer> nums = new HashMap<>();
    private HashMap<String, CustomList<Integer>> lists = new HashMap<>();
    private HashMap<String, CustomHashSet<Integer>> sets = new HashMap<>();

    public Calculator(ArrayList<Token> programm) {
        this.programm = programm;
        stack = new Stack<>();
    }

    void go() {
        int var1, var2;
        for (int i = 0; i < programm.size(); i++) {
            Token token = programm.get(i);
            if (token.getType().equals(TokenType.CONST))
                stack.push(token);
            else if (token.getType().equals(TokenType.VAR)) {
                if (!nums.containsKey(token.getContent()))
                    nums.put(token.getContent(), 0);
                stack.push(token);
            } else if (token.getType().equals(TokenType.OPERATION)) {
                var1 = getValue(stack.pop());
                Token var = stack.pop();
                var2 = getValue(var);

                switch (token.getContent()) {
                    case "+":
                        stack.push(new Token(TokenType.CONST, String.valueOf(var2 + var1)));
                        break;
                    case "-":
                        stack.push(new Token(TokenType.CONST, String.valueOf(var2 - var1)));
                        break;
                    case "*":
                        stack.push(new Token(TokenType.CONST, String.valueOf(var2 * var1)));
                        break;
                    case "/":
                        if (var2 != 0)
                            stack.push(new Token(TokenType.CONST, String.valueOf(var2 / var1)));
                        else
                            System.out.println("Divide by zero!");
                        break;
                    case "=":
                        nums.put(var.getContent(), var1);
                        break;
                    case ">":
                        pushBool(var2 > var1);
                        break;
                    case "<":
                        pushBool(var2 < var1);
                        break;
                    case ">=":
                        pushBool(var2 >= var1);
                        break;
                    case "<=":
                        pushBool(var2 <= var1);
                        break;
                    case "==":
                        pushBool(var2 == var1);
                        break;
                    case "!=":
                        pushBool(var2 != var1);
                        break;
                }
            } else if (token.getType().equals(TokenType.GOTO)) {
                i = getValue(stack.pop()) - 1;

            } else if (token.getType().equals(TokenType.GOTO_IF)) {
                var1 = getValue(stack.pop());
                if (stack.pop().getContent().equals("0")) {
                    i = var1 - 1;
                }
            } else {
                int value;
                switch (token.getType()) {
                    case GET:
                        value = getValue(stack.pop());
                        stack.push(new Token(TokenType.CONST, String.valueOf(lists.get(stack.pop().getContent()).getValue(value))));//положить в стэк констату, которую получаем из сета с заданным именем по заданному индексу
                        break;
                    case DELETE:
                        value = getValue(stack.pop());
                        if (stack.peek().getType().equals(TokenType.SET))
                            sets.get(stack.pop().getContent()).delete(value);
                        else
                            lists.get(stack.pop().getContent()).delete(value);
                        break;
                    case PRINT:
                        if (stack.peek().getType().equals(TokenType.SET))
                            sets.get(stack.pop().getContent()).print();
                        else
                            lists.get(stack.pop().getContent()).print();
                        break;
                    case UPDATE:
                        break;
                    case ADD:
                        value = getValue(stack.pop());
                        if (stack.peek().getType().equals(TokenType.SET))
                            sets.get(stack.pop().getContent()).add(value);
                        else
                            lists.get(stack.pop().getContent()).add(value);
                        break;
                    case SET:
                        if (!sets.containsKey(token.getContent()))
                            sets.put(token.getContent(), new CustomHashSet<>());
                        stack.push(token);
                        break;
                    case LIST:
                        if (!lists.containsKey(token.getContent()))
                            lists.put(token.getContent(), new CustomList<>());

                        stack.push(token);
                        break;
                }
            }
            //printStack();
            //printVars();
        }
        printVars();
    }

    private int getValue(Token token) {
        if (!token.getType().equals(TokenType.VAR))
            return Integer.valueOf(token.getContent());
        else
            return nums.get(token.getContent());
    }

    private void printStack() {
        System.out.println();
        System.out.print("Stack: ");
        for (Token token1 : stack) {
            System.out.print(token1.getContent() + " ");
        }
    }

    private void printVars() {
        System.out.print("Variables: ");
        System.out.println(nums);
        System.out.println("Lists: ");
        for (Map.Entry<String, CustomList<Integer>> entry : lists.entrySet()) {
            System.out.println(entry.getKey() + ": ");
            entry.getValue().print();
        }
        System.out.println("Sets: ");
        for (Map.Entry<String, CustomHashSet<Integer>> entry : sets.entrySet()) {
            System.out.println(entry.getKey() + ": ");
            entry.getValue().print();
        }
    }

    private void pushBool(boolean result) {
        if (result) {
            stack.push(new Token(TokenType.CONST, "1"));
        } else {
            stack.push(new Token(TokenType.CONST, "0"));
        }
    }
}
