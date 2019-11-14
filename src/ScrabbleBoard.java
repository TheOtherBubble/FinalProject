import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ScrabbleBoard {
    private Random random = new Random(); //random number generator
    private char[][] board = new char[15][15]; //array with the actual words and blank spaces
    private int[][] letterMultiply = new int[15][15]; //array with letter multipliers
    private int[][] wordMultiply = new int[15][15]; //array with word multipliers
    private final int[] scoreOfLetter = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10}; //used to find score of each letter by calling to array
    private ArrayList<Character> characters = new ArrayList<Character>(); //all characters that can be drawn in an array list
    private ArrayList<Character> player1Letters = new ArrayList<Character>(); //player 1's letters that are taken from array list
    private ArrayList<Character> player2Letters = new ArrayList<Character>(); //player 2's letters that are taken from array list
    private ArrayList<Character> mirrorPlayer1Letters = new ArrayList<Character>(); //mirror of letters to make testing easier
    private ArrayList<Character> mirrorPlayer2Letters = new ArrayList<Character>(); //mirror of letters to make testing easier
    private int player1Score = 0; //score of player 1
    private int player2Score = 0; //score of player 2

    public ScrabbleBoard() {
        for (int i = 0; i < 15; i++) { //initializing the board and multipliers
            for (int j = 0; j < 15; j++) {
                board[i][j] = ' ';
                letterMultiply[i][j] = 1;
                wordMultiply[i][j] = 1;
            }
        }
        for (int i = 0; i < 15; i += 7) { //triple word multipliers
            for (int j = 0; j < 15; j += 7) {
                wordMultiply[i][j] = 3;
            }
        }
        wordMultiply[7][7] = 2; //center multipliers
        for (int i = 1; i < 5; i++) { //diagonal multipliers
            wordMultiply[i][i] = 2;
            wordMultiply[i][14 - i] = 2;
            wordMultiply[14 - i][i] = 2;
            wordMultiply[14 - i][14 - i] = 2;
        }
        for (int i = 0; i < 15; i += 7) { //letter multipliers less evenly spaced out and more complex to do
            for (int j = 3; j < 15; j += 8) {
                letterMultiply[i][j] = 2;
            }
        }
        for (int i = 1; i < 15; i += 4) {
            for (int j = 1; j < 15; j += 4) {
                letterMultiply[i][j] = 3;
            }
        }
        for (int i = 1; i < 15; i += 12) {
            for (int j = 1; j < 15; j += 12) {
                letterMultiply[i][j] = 1;
            }
        }
        for (int i = 3; i < 15; i += 8) {
            for (int j = 0; j < 15; j += 7) {
                letterMultiply[i][j] = 2;
            }
        }
        for (int i = 2; i < 13; i += 2) {
            for (int j = 6; j < 9; j += 2) {
                if (i != 4 && i != 10) {
                    letterMultiply[i][j] = 2;
                }
            }
        }
        for (int i = 2; i < 13; i += 2) {
            for (int j = 6; j < 9; j += 2) {
                if (i != 4 && i != 10) {
                    letterMultiply[j][i] = 2;
                }
            }
        }

        for (int i = 0; i < 9; i++) { //adding a certain number of each character to the total characters
            characters.add('A');
            characters.add('I');
            if (i != 8) {
                characters.add('O');
            }
        }
        for (int i = 0; i < 12; i++) {
            characters.add('E');
        }
        for (int i = 0; i < 6; i++) {
            characters.add('T');
            characters.add('N');
            characters.add('R');
        }
        for (int i = 0; i < 4; i++) {
            characters.add('D');
            characters.add('L');
            characters.add('S');
            characters.add('U');
            if (i != 3) {
                characters.add('G');
            }
        }
        for (int i = 0; i < 2; i++) {
            characters.add('B');
            characters.add('C');
            characters.add('F');
            characters.add('H');
            characters.add('M');
            characters.add('P');
            characters.add('V');
            characters.add('W');
            characters.add('Y');
        }
        characters.add('J');
        characters.add('K');
        characters.add('Q');
        characters.add('X');
        characters.add('Z');

        for (int i = 0; i < 7; i++) { //giving player one random letters from the character list
            int character = random.nextInt(characters.size());
            player1Letters.add(characters.get(character));
            characters.remove(character);
        }
        for (int i = 0; i < 7; i++) { //giving player two random letters from the character list
            int character = random.nextInt(characters.size());
            player2Letters.add(characters.get(character));
            characters.remove(character);
        }

        //copying the actual letters
        mirrorPlayer1Letters = player1Letters;
        mirrorPlayer2Letters = player2Letters;
    }

    public StringBuilder printBoard() {
        StringBuilder finalString = new StringBuilder();
        System.out.printf("%16d -- %-14d", player1Score, player2Score); //scoreboard
        finalString.append("\n  ");
        for (int i = 0; i < 15; i++) { //letters on top
            finalString.append(" ");
            finalString.append((char)(i + 65)); //puts the letters on top relative to i
        }
        finalString.append("\n  ");
        for (int j = 0; j < 31; j++) { //dashed line
            finalString.append("-");
        }
        finalString.append("\n");
        for (int i = 0; i < 15; i++) { //printing actual board and spacing
            finalString.append((char) (i + 65)).append(" ");
            finalString.append("|");
            for (int j = 0; j < 15; j++) {
                finalString.append(board[i][j]);
                finalString.append("|");
            }
            finalString.append("\n  ");
            for (int j = 0; j < 31; j++) {
                finalString.append("-");
            }
            finalString.append("\n");
        }
        return finalString; //returning string to be printed
    }
    public void printPlayer1Letters() {
        System.out.print(player1Letters + "\n"); //printing player one's letters
    }

    public void printPlayer2Letters() {
        System.out.print(player2Letters + "\n"); //printing player two's letters
    }

    public void addWord(int startingRow, int startingColumn, boolean vertical, String word) { //adding a word to the bord
        String Word = word.toUpperCase(); //making sure given word is uppercase
        for (int i = 0; i < Word.length(); i++) { // running for loop for length of word
            if (vertical) { //writing word into array depending on vertical or not
                board[startingRow + i][startingColumn] = Word.charAt(i);
            }
            else {
                board[startingRow][startingColumn + i] = Word.charAt(i);
            }
        }
    }
    public boolean checkIfWordInPlayer1Letters(String word, int row, int column, boolean vertical, boolean first) throws FileNotFoundException {
        String Word = word.toUpperCase();
        ArrayList<Integer> filledCharacters= new ArrayList<Integer>(); //finding if any characters in word already on board
        for (int i = 0; i < Word.length(); i++) { //checks if the place on board is empty or has right characters
            if (vertical) {
                if (board[row + i][column] != ' ') {
                    if (board[row + i][column] == Word.charAt(i)) {
                        filledCharacters.add(i);
                    }
                    else {
                        return false;
                    }
                }
            }
            else {
                if (board[row][column + i] != ' ') {
                    if (board[row][column + i] == Word.charAt(i)) {
                        filledCharacters.add(i);
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        for (int i = 0; i < Word.length(); i++) { //removes characters from player's letters if they need to be removed
            if (!filledCharacters.contains(i)) {
                if (mirrorPlayer1Letters.contains(Word.charAt(i))) {
                    mirrorPlayer1Letters.remove(i);
                }
                else {
                    mirrorPlayer1Letters = player1Letters;
                    return false;
                }
            }
        }
        StringBuilder spaces = new StringBuilder();
        StringBuilder spacesAround = new StringBuilder();
        for (int i = 0; i < Word.length(); i++) { //spaces for actual word
            spaces.append(' ');
        }
        for (int i = 0; i < (2 * Word.length()) + 2; i++) { //spaces for around word
            spacesAround.append(' ');
        }
        StringBuilder position = new StringBuilder();
        for (int i = 0; i < Word.length(); i++) { //portion of board that will be taken up by word
            if (vertical) {
                position.append(board[row + i][column]);
            }
            else {
                position.append(board[row][column + i]);
            }
        }
        StringBuilder around = new StringBuilder();
        if (position == spaces && !first) { //only checks around if the space where words goes is empty because word must connect to original
            if (vertical) {
                around.append(board[row - 1][column]);
                around.append(board[row + Word.length()][column]);
                for (int i = -1; i < 2; i += 2) {
                    for (int j = 0; j < Word.length(); j++) {
                        around.append(board[row + j][column + i]);
                    }
                }
            }
            else {
                around.append(board[row][column - 1]);
                around.append(board[row][column + Word.length()]);
                for (int i = -1; i < 2; i += 2) {
                    for (int j = 0; j < Word.length(); j++) {
                        around.append(board[row + i][column + j]);
                    }
                }
            }
        }
        if (around == spacesAround) { //if spaces around word empty then not valid
            return false;
        }
        refillPlayer1Letters();
        return true;
    }
    public boolean checkIfWordInPlayer2Letters(String word, int row, int column, boolean vertical) {
        String Word = word.toUpperCase();
        ArrayList<Integer> filledCharacters= new ArrayList<Integer>(); //finding if any characters in word already on board
        for (int i = 0; i < Word.length(); i++) { //checks if the place on board is empty or has right characters
            if (vertical) {
                if (board[row + i][column] != ' ') {
                    if (board[row + i][column] == Word.charAt(i)) {
                        filledCharacters.add(i);
                    }
                    else {
                        return false;
                    }
                }
            }
            else {
                if (board[row][column + i] != ' ') {
                    if (board[row][column + i] == Word.charAt(i)) {
                        filledCharacters.add(i);
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        for (int i = 0; i < Word.length(); i++) { //removes characters from player's letters if they need to be removed
            if (!filledCharacters.contains(i)) {
                if (mirrorPlayer2Letters.contains(Word.charAt(i))) {
                    mirrorPlayer2Letters.remove(i);
                }
                else {
                    mirrorPlayer2Letters = player2Letters;
                    return false;
                }
            }
        }
        StringBuilder spaces = new StringBuilder();
        StringBuilder spacesAround = new StringBuilder();
        for (int i = 0; i < Word.length(); i++) { //spaces for word
            spaces.append(' ');
        }
        for (int i = 0; i < (2 * Word.length()) + 2; i++) { //spaces for around word
            spacesAround.append(' ');
        }
        StringBuilder position = new StringBuilder();
        for (int i = 0; i < Word.length(); i++) { //gets the spaces on board when word goes
            if (vertical) {
                position.append(board[row + i][column]);
            }
            else {
                position.append(board[row][column + i]);
            }
        }
        StringBuilder around = new StringBuilder();
        if (position == spaces) { //only runs if the place where word goes is empty
            if (vertical) {
                around.append(board[row - 1][column]);
                around.append(board[row + Word.length()][column]);
                for (int i = -1; i < 2; i += 2) {
                    for (int j = 0; j < Word.length(); j++) {
                        around.append(board[row + j][column + i]);
                    }
                }
            }
            else {
                around.append(board[row][column - 1]);
                around.append(board[row][column + Word.length()]);
                for (int i = -1; i < 2; i += 2) {
                    for (int j = 0; j < Word.length(); j++) {
                        around.append(board[row + i][column + j]);
                    }
                }
            }
        }
        if (around == spacesAround) { //if space around word is empty
            return false;
        }
        refillPlayer2Letters();
        return true;
    }
    public void refillPlayer1Letters() {
        player1Letters = mirrorPlayer1Letters; //sets array to actual size
        for (int i = player1Letters.size(); i < 7; i++) { //adds characters until full
            int character = random.nextInt(characters.size());
            player1Letters.add(characters.get(character));
            characters.remove(character);
        }
        mirrorPlayer1Letters = player1Letters; //sets up mirror again
    }
    public void refillPlayer2Letters() {
        player2Letters = mirrorPlayer2Letters; //sets array to actual size
        for (int i = player2Letters.size(); i < 7; i++) { //adds characters until full
            int character = random.nextInt(characters.size());
            player2Letters.add(characters.get(character));
            characters.remove(character);
        }
        mirrorPlayer2Letters = player2Letters; //sets up mirror again
    }
    public void addToScorePlayer1(int row, int column, boolean vertical, String word) {
        String Word = word.toUpperCase();
        int score;
        int totalScore = 0;
        int wordMultiplier = 1; //sets up word multipliers early to use later
        for (int i = 0; i < Word.length(); i++) { //uses array of scores to determine how much each letter is worth
            score = scoreOfLetter[Word.charAt(i) - 65];
            if (vertical) {
                score *= letterMultiply[row + i][column];
            } else {
                score *= letterMultiply[row][column + i];
            }
            totalScore += score;
        }
        for (int i = 0; i < Word.length(); i++) { //multiplies entire word
            if (vertical) {
                wordMultiplier *= wordMultiply[row + i][column];
            } else {
                wordMultiplier *= wordMultiply[row][column + i];
            }
        }
        totalScore *= wordMultiplier; //multiplies score
        player1Score += totalScore; //adds to score
    }
    public void addToScorePlayer2(int row, int column, boolean vertical, String word) {
        String Word = word.toUpperCase();
        int score;
        int totalScore = 0;
        int wordMultiplier = 1; //sets up word multipliers early to use later
        for (int i = 0; i < Word.length(); i++) {
            score = scoreOfLetter[Word.charAt(i) - 65];
            if (vertical) {
                score *= letterMultiply[row + i][column];
            } else {
                score *= letterMultiply[row][column + i];
            }
            totalScore += score;
        }
        for (int i = 0; i < Word.length(); i++) {
            if (vertical) {
                wordMultiplier *= wordMultiply[row + i][column];
            } else {
                wordMultiplier *= wordMultiply[row][column + i];
            }
        }
        totalScore *= wordMultiplier;
        player2Score += totalScore;
    }
    public boolean checkIfWord(String Word) throws FileNotFoundException {
        String wordFromList;
        String word = Word.toLowerCase();
        Scanner fileReader = new Scanner(new File("words.txt"));
        while (fileReader.hasNext()) {
            wordFromList = fileReader.nextLine();
            if (wordFromList.equals(word)) {
                fileReader.close();
                return true;
            }
        }
        return false;
    }
    public void endGame() {
        if (characters.size() < 10) {
            System.out.printf("That is the end of the game, the final score was Player 1:%d Player 2:%d", player1Score, player2Score);
            System.exit(1);
        }
    }
}