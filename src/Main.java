import mySet.CustomHashSet;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        /*
        Правила языка:
        1. Переменные помечать символом $
        2. Else относится только к тому if, в чьем теле он находится
        3. Если операция с листом, то перед его названием ставим @, если сет, то #
         */

        String calculationProgramm = "$a = 1 + ( 1 + ( 1 + ( 1 + 1 ) ) ) + 1 ; ";       //it works = 6
        String calcProgr2 = "$a = 200 + 14 * 2 ; $b = 1400 + 22 * 4 ; ";
        String ifElseProgramm = "if ( $a > 1 ) { $a = $a + 1 ; else $a = $b + 1 ; } ";                         //it works
        String ifProgramm = "if ( $a > 1 ) { $a = $a + 1 ; } ";                         //it works
        String whileProgramm = "while ( $a > 1 ) { $a = $a + 1 ; } ";                         //it works
        String polizeProgramm = "$a = 3 + 4 * 2 / ( 1 - 5 ) * 2 ; ";
        String polizeProgramm2 = "$a + $b * ( $c - $d / ( 10 + $x ) + $y ) - $e ; ";
        String forProgramm = "for ( $a = 1 ; $a < 10 ; $a = $a + 1 ) { $b = $b + 1 ; } $c = $b ; ";   //it works
        String mainProgramm = "$a = 1 ; \n" +
                "$b = $a + 2 ; \n" +
                "if ( $a > $b ) { \n" +
                "\tif ( $a > $b + 2 ) { \n" +
                "\t\t$a = $b ; \n" +
                "\t} \n" +
                "\telse \n" +
                "\t\t$b = $a ; \n" +
                "\t} \n" +
                "$a = $a * 2 * ( $b + 2 * ( $a + 1 ) ) ; ";
        String goProg = "$a = 10 ; if ( $a > 2 ) { $b = 1 ; else $b = 0 ; } ";
        String listProg = "@L1 <+ 1; @L1 <+ 2; @L1 <+ 3; @L1 <+ 4; @L1 <+ 5; @L1 >>> ; @L1 <- 3; @L1 >>>; $a = @L1 >> 2;";
        String setProg = "#set1 <+ 1; #set1 <+ 11; #set1 <+ 13; #set1 <+ 14; #set1 <+ 3; #set1 <+ 4; #set1 <+ 8; #set1 <+ 9; #set1 <+ 10; #set1 <+ 0;";
        String finalProg = "$a=1; $b=0; for ($i=0;$i<6;$i=$i+1) { $a=$a+$b; $b=$a+$b; @list1<+$a; @list1<+$b; } @list1 >>>; #set1 <+ $a; #set1 <+ $b; ";

        /*
        CustomHashSet<String> set = new CustomHashSet<>();
        set.add("Kama");
        set.add("Pula");
        set.add("Pushka");
        set.add("Gonka");
        set.add("Balalaika");
        set.add("Alo");
        set.add("Korzina");
        set.add("dedok");

        set.print();
        */



        Lexer lexer = new Lexer(finalProg);

        ArrayList<Token> tokens = lexer.tokenize();
        //lexer.printWords();

        System.out.println();
        Parser parser = new Parser(tokens);

        if(parser.lang(false)){
            System.out.println("Ошибок не обнаружено!");
            tokens = parser.optimise();

            RPN rpn = new RPN(tokens);
            tokens = rpn.translate();

            Calculator calculator = new Calculator(tokens);

            calculator.go();
        }
        else System.out.println("Ошибки найдены!");

        /*
        CustomList<Integer> list = new CustomList<>();
        list.Insert(1);
        list.Insert(1);
        list.Insert(2);
        list.Insert(3);
        list.Insert(4);
        list.Insert(5);

        list.print();
        System.out.println();

        System.out.println(list.getValue(1));
        System.out.println();
        list.print();
        */


        //lexer.printWords();
        //System.out.println();
    }
}
