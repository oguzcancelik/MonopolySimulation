import java.util.Arrays;
import java.util.Scanner;

public class Monopoly_Game {
    public static void main(String[] args) {

        if(args.length < 2){
            System.out.println("\nWrong argument number.");
            System.out.println("\nUsage: java Monopoly_Game playerNumber initialCash");
            return;
        } else {
            try {
                if (Integer.parseInt(args[0]) < 2 || Integer.parseInt(args[0]) > 8){
                    System.out.println("\nWrong input.");
                    System.out.println("\nPlayer number should be between 2 and 8.");
                    return;
                }

                if (Integer.parseInt(args[1]) <= 0){
                    System.out.println("\nWrong input.");
                    System.out.println("\nInitial money should be greater than 0.");
                    return;
                }
            } catch(Exception e){
                System.out.println("\nWrong input.");
                System.out.println("\nPlayer number and initial money must be integers.");
                return;
            }
        }

        Scanner input = new Scanner(System.in);
        Die die1 = new Die();
        Die die2 = new Die();
        Board board = new Board();
        int playerNumber = Integer.parseInt(args[0]);

        String playerNames[] = new String[playerNumber];
        int startingDiceValues[] = new int[playerNumber];

        for (int i = 0; i < playerNumber; i++) {
            System.out.print("Please enter the name of player" + (i + 1) + ":  ");
            playerNames[i] = input.next();
            die1.rollDie();
            die2.rollDie();
            startingDiceValues[i] = die1.getFaceValue() + die2.getFaceValue();

            for (int j = 0; j < i; j++) {
                if (startingDiceValues[i] == startingDiceValues[j]) {
                    die1.rollDie();
                    die2.rollDie();
                    startingDiceValues[i] = die1.getFaceValue() + die2.getFaceValue();
                    j = -1;
                }
            }
        }

        board.write("\r");
        board.write("Dice tournament started.");
        board.write("\r");
        for (int i = 0; i < startingDiceValues.length; i++) {
            board.write(playerNames[i] + "'s dice value is: " + startingDiceValues[i]);
        }

        int[] sdvClone = startingDiceValues.clone();
        String[] pnClone = playerNames.clone();
        Arrays.sort(startingDiceValues);

        for (int i = 0; i < startingDiceValues.length; i++) {
            for (int j = 0; j < startingDiceValues.length; j++) {
                if (sdvClone[i] == startingDiceValues[j]) {
                    playerNames[playerNames.length - j - 1] = pnClone[i];
                }
            }
        }

        board.write("\r");
        board.write("Dice tournament ended. Current positions:");
        board.write("\r");
        for (int i = 0; i < startingDiceValues.length; i++) {

            switch (i + 1) {
                case 1:
                    board.write("1st player is " + playerNames[i] + " with a dice value of: " + startingDiceValues[startingDiceValues.length - i - 1]);
                    break;
                case 2:
                    board.write("2nd player is " + playerNames[i] + " with a dice value of: " + startingDiceValues[startingDiceValues.length - i - 1]);
                    break;
                case 3:
                    board.write("3rd player is " + playerNames[i] + " with a dice value of: " + startingDiceValues[startingDiceValues.length - i - 1]);
                    break;
                default:
                    board.write((i + 1) + "th player is " + playerNames[i] + " with a dice value of: " + startingDiceValues[startingDiceValues.length - i - 1]);
            }
        }
        board.write("\r");
        startingDiceValues = null;
        sdvClone = null;
        pnClone = null;

        board.generateSquares();
        board.generatePlayers(playerNames, Integer.parseInt(args[1]));
        board.play();
    }
}