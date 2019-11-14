import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScrabbleMain {
    private static Scanner scanner = new Scanner(System.in); //scanner used in program
    public static void main (String [] args) throws FileNotFoundException {
        ScrabbleBoard scrabbleBoard = new ScrabbleBoard(); //setting up main object
        String input;
        int turn = 1;
        int row = 7; //default row, column, and turn
        int column = 7;
        boolean vertical;
        System.out.print(scrabbleBoard.printBoard()); //printing board and letters
        scrabbleBoard.printPlayer1Letters();
        System.out.print("Player one would you like your word to be vertical or horizontal? ");
        input = scanner.next(); //getting first direction
        vertical = vertical(input);
        do {
            System.out.print("Player one input your first word > ");
            input = scanner.next();
        } while (!scrabbleBoard.checkIfWord(input) || !scrabbleBoard.checkIfWordInPlayer1Letters(input,row,column,vertical,true));
        scrabbleBoard.addWord(row,column,vertical,input);
        scrabbleBoard.addToScorePlayer1(row,column,vertical,input); //changing score
        System.out.print(scrabbleBoard.printBoard()); //printing board
        while (true) {
            turn++; //switches between player one and two
            if (turn % 2 == 0) {
                do { //loop asks for all information then tests if it will work, if not then restarts
                    scrabbleBoard.printPlayer2Letters();
                    System.out.print("Player two would you like your word to be vertical or horizontal? ");
                    input = scanner.next();
                    vertical = vertical(input);
                    System.out.print("Player two which row would you like your word to start in? ");
                    row = letterToNumber(scanner.next().charAt(0));
                    System.out.print("Player two which column would you like your word to start in? ");
                    column = letterToNumber(scanner.next().charAt(0));
                    System.out.print("Player two input your word > ");
                    input = scanner.next();
                } while (!scrabbleBoard.checkIfWord(input) || !scrabbleBoard.checkIfWordInPlayer2Letters(input,row,column,vertical));
                scrabbleBoard.addToScorePlayer2(row,column,vertical,input); //changing score
            }
            else {
                do { //loop asks for all information then tests if it will work, if not then restarts
                    scrabbleBoard.printPlayer1Letters();
                    System.out.print("Player one would you like your word to be vertical or horizontal? ");
                    input = scanner.next();
                    vertical = vertical(input);
                    System.out.print("Player one which row would you like your word to start in? ");
                    row = letterToNumber(scanner.next().charAt(0));
                    System.out.print("Player one which column would you like your word to start in? ");
                    column = letterToNumber(scanner.next().charAt(0));
                    System.out.print("Player one input your word > ");
                    input = scanner.next();
                } while (!scrabbleBoard.checkIfWord(input) || !scrabbleBoard.checkIfWordInPlayer1Letters(input,row,column,vertical, false));
                scrabbleBoard.addToScorePlayer1(row,column,vertical,input); //changing score
            }
            scrabbleBoard.addWord(row,column,vertical,input); //adding word to board
            System.out.print(scrabbleBoard.printBoard()); //printing board
            scrabbleBoard.endGame(); //testing if game is over
        }
    }
    private static int letterToNumber(char letter) {
        String temp = String.valueOf(letter);
        temp = temp.toUpperCase(); //making sure letter given is uppercase
        char newLetter = temp.charAt(0);
        return (int)(newLetter) - 65; //converting it to the value it has in the array
    }
    private static boolean vertical(String vertical) {
        return vertical.charAt(0) == 'V' | vertical.charAt(0) == 'v'; //return if starts with a v, so any word that doesn't begin with a v will be treated as horizontal
    }
}