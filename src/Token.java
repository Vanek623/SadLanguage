class Token {
    private TokenType type;
    private String content;
    private int priority;

    public Token(TokenType type) {
        this.type = type;
        if(TokenType.GOTO.equals(type))
            content = "!";
        else if(TokenType.GOTO_IF.equals(type))
            content = "!F";
    }

    Token(TokenType type, String content) {
        this.type = type;
        this.content = content;

        switch (type){
            case OPERATION:
            case ADD:
            case UPDATE:
            case PRINT:
            case DELETE:
            case GET:
                setOperationPriority();
                break;
            case CURLY_BRAKER_CLOSE:
            case END_LINE:
            case ROUND_BRAKER_CLOSE:
                priority = 7;
                break;
            case IF:
            case ELSE:
            case WHILE:
            case CONST:
            case CURLY_BRAKER_OPEN:
            case ROUND_BRAKER_OPEN:
                priority = 6;
                break;
        }
    }

    private void setOperationPriority(){
        if(content.equals("+") || content.equals("-"))
            priority = 9;
        else if(content.equals("*") || content.equals("/"))
            priority = 10;
        else
            priority = 8;
    }

    public TokenType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getPriority(){
        return priority;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
