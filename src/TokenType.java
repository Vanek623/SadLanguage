public enum TokenType {
    VAR,
    OPERATION,
    CONST,
    GOTO,
    GOTO_IF,

    ROUND_BRAKER_OPEN, // (
    ROUND_BRAKER_CLOSE, // )
    CURLY_BRAKER_OPEN, // {
    CURLY_BRAKER_CLOSE, // }

    IF,
    ELSE,

    WHILE,
    FOR,
    END_LINE,

    LIST,   // @
    SET,    // #

    ADD,    // <+
    PRINT,  // >>> var/@/# eol
    DELETE, // -> var eol
    GET,    // >>
    UPDATE, // <<

    UNKNOWN
}
