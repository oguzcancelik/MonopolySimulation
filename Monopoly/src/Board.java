import java.io.*;
import java.util.Scanner;

public class Board {

    private Player[] players;
    private Square[] squares = new Square[40];
    private Die die1 = new Die();
    private Die die2 = new Die();
    private Scanner sc;
    private BufferedWriter bw = null;

    public Board() {
    }

    public void generateSquares(){

        try {
            sc = new Scanner(new File("Monopoly-Lots.csv"));
        } catch (Exception e) {
            System.out.println("Monopoly-Lots.csv can not found.");
        }
        sc.nextLine();
        while (sc.hasNext()) {
            String[] temp = sc.nextLine().split(";");
            int a = Integer.parseInt(temp[0]);
            if(a <= 40){
                squares[a - 1] = new Lot_Square(a - 1, "Square " + a, Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
            }
        }
        for (int i = 0; i < 40; i++) {
            if (squares[i] != null) {
                continue;
            }
            switch (i) {
                case 0:
                    squares[i] = new Go_Square(i, "Go Square");
                    break;
                case 4:
                    squares[i] = new Income_Tax_Square(i, "Income Tax Square");
                    break;
                case 5:
                    squares[i] = new Railroad_Square(i, "Rail Road 1", 200);
                    break;
                case 10:
                    squares[i] = new Jail_Square(i, "Jail Square");
                    break;
                case 12:
                    squares[i] = new Utility_Square(i, "Electric Utility", 150);
                    break;
                case 15:
                    squares[i] = new Railroad_Square(i, "Rail Road 2", 200);
                    break;
                case 20:
                    squares[i] = new Free_Parking_Square(i, "Free Parking Square");
                    break;
                case 25:
                    squares[i] = new Railroad_Square(i, "Rail Road 3", 200);
                    break;
                case 28:
                    squares[i] = new Utility_Square(i, "Water Utility", 150);
                    break;
                case 30:
                    squares[i] = new Go_To_Jail_Square(i, "Go To Jail Square");
                    break;
                case 35:
                    squares[i] = new Railroad_Square(i, "Rail Road 4", 200);
                    break;
                case 38:
                    squares[i] = new Luxury_Tax_Square(i, "Luxury Tax Square");
                    break;
                default:
                    squares[i] = new Square(i, "Square " + (i + 1));
            }
        }
    }

    public void generatePlayers(String[] playerNames, int startingMoney) {
        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++) {
            players[i] = new Player(playerNames[i], startingMoney);
        }
    }

    public void play() {

        int currentPlayerNumber = players.length;

        for (int turnNumber = 0; 0 < currentPlayerNumber; turnNumber++) {

            for (int i = 0, a = 0; i < players.length; i++, a++) {

                if(players[i].getMoney() <= 0 ){
                    a--;
                    continue;
                } else if (currentPlayerNumber == 1){
                    write("The winner is "+ players[i].getName() + ".");
                    currentPlayerNumber--;
                    break;
                }

                write(players[i].getName() + "'s turn. Turn number: " + (turnNumber + 1));

                switch (a + 1) {
                    case 1:
                        write(players[i].getName() + " is 1st player.");
                        break;
                    case 2:
                        write(players[i].getName() + " is 2nd player.");
                        break;
                    case 3:
                        write(players[i].getName() + " is 3rd player.");
                        break;
                    default:
                        write(players[i].getName() + " is " + (a + 1) + "th player.");
                }

                write(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                write(players[i].getName() + " is currently at " + squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");

                if (!players[i].isInJail()) {

                    die1.rollDie();
                    die2.rollDie();

                    write(players[i].getName() + " rolled the dice.");
                    write("Dice show: " + die1.getFaceValue() + " and " + die2.getFaceValue());

                    if(die1.getFaceValue() == die2.getFaceValue()){
                        players[i].increaseDoubleCounter();
                    } else {
                        players[i].resetDoubleCounter();
                    }

                    if(players[i].getDoubleCounter() == 3){
                        players[i].setInJail(true);
                        players[i].setCurrentSquareNumber(10);
                        players[i].resetDoubleCounter();
                        players[i].setInJailTurnNumber(turnNumber);
                        write(players[i].getName() + " rolled doubles in three consecutive turn.");
                        write(players[i].getName() + " is send to Jail Square.");
                        write(players[i].getName() + " is now at Jail Square.");
                        write("\r");
                        write("\r");
                        continue;
                    }

                    write("Total value of dice : " + (die1.getFaceValue() + die2.getFaceValue()));
                    write(players[i].getName() + " moved " + (die1.getFaceValue() + die2.getFaceValue()) + " squares.");
                    players[i].setCurrentSquareNumber((players[i].getCurrentSquareNumber() + die1.getFaceValue() + die2.getFaceValue()) % 40);
                    write(players[i].getName() + " landed in " + squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");

                    switch (squares[players[i].getCurrentSquareNumber()].getClass().getName()) {
                        case "Go_Square":
                            players[i].setMoney(200);
                            write(players[i].getName() + " earned $200.");
                            write(players[i].getName() + "'s current total cash is $" + players[i].getMoney() + ".");
                            break;
                        case "Income_Tax_Square":
                            write(players[i].getName() + " paid $" + (players[i].getMoney() / 10) + " as Income Tax.");
                            players[i].setMoney(-(players[i].getMoney() / 10));
                            write(players[i].getName() + "'s current total cash is $" + players[i].getMoney() + ".");
                            break;
                        case "Go_To_Jail_Square":
                            players[i].setInJail(true);
                            players[i].setCurrentSquareNumber(10);
                            players[i].resetDoubleCounter();
                            players[i].setInJailTurnNumber(turnNumber);
                            write(players[i].getName() + " is send to Jail Square.");
                            write(players[i].getName() + " is now at Jail Square.");
                            break;
                        case "Luxury_Tax_Square":
                            players[i].setMoney(-75);
                            if (players[i].getMoney() > 0){
                                write(players[i].getName() + " paid $75 as Luxury Tax.");
                                write(players[i].getName() + "'s total cash is $" + players[i].getMoney() + ".");
                            } else {
                                write(players[i].getName()+" does not have enough money to pay the Luxury Tax.");
                            }
                            break;
                        case "Lot_Square":
                            if (squares [players[i].getCurrentSquareNumber()].getOwnerNumber() == -1) {

                                if (players[i].getMoney() > squares[players[i].getCurrentSquareNumber()].getPrice()) {

                                    die1.rollDie();
                                    write(players[i].getName() + " rolled a die to buy this square.");
                                    write("Die shows " + die1.getFaceValue() + ".");

                                    if (die1.getFaceValue() > 4) {
                                        players[i].setMoney(-squares[players[i].getCurrentSquareNumber()].getPrice());
                                        squares[players[i].getCurrentSquareNumber()].setOwnerNumber(i);
                                        write(players[i].getName() + " bought the " + squares[players[i].getCurrentSquareNumber()]
                                                .getSquareName() + " for $"+squares[players[i].getCurrentSquareNumber()].getPrice()+".");
                                        write(players[i].getName() + "'s current total cash is $" + players[i].getMoney() + ".");
                                    } else {
                                        write("No actions. Next player's turn.");
                                    }
                                } else {
                                    write(players[i].getName() + " does not have enough money to buy " +
                                            squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");
                                    write("No actions. Next player's turn.");
                                }
                            } else if(squares[players[i].getCurrentSquareNumber()].getOwnerNumber() == i){
                                write(players[i].getName()+" owns this square.");
                                write("No actions. Next player's turn.");
                            } else {
                                players[i].setMoney(-squares[players[i].getCurrentSquareNumber()].getRent());
                                if(players[i].getMoney() > 0){
                                   players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].
                                           setMoney(squares[players[i].getCurrentSquareNumber()].getRent());
                                    write(players[i].getName() + " paid $" + squares[players[i].getCurrentSquareNumber()].getRent() +
                                            " as rent to " + players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getName() + ".");
                                    write(players[i].getName() + "'s current total cash is $" + players[i].getMoney() + ".");
                                    write(players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getName()+"'s current total cash is $"
                                            +players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getMoney() + ".");
                                } else {
                                    write(players[i].getName()+" does not have enough money to pay the rent for this square.");
                                }
                            }
                            break;
                        case "Railroad_Square":
                            if (squares[players[i].getCurrentSquareNumber()].getOwnerNumber() == -1) {

                                if (players[i].getMoney() > squares[players[i].getCurrentSquareNumber()].getPrice()) {

                                    die1.rollDie();
                                    write(players[i].getName() + " rolled a die to buy this square.");
                                    write("Die shows " + die1.getFaceValue() + ".");

                                    if (die1.getFaceValue() > 4) {
                                        players[i].setMoney(-squares[players[i].getCurrentSquareNumber()].getPrice());
                                        squares[players[i].getCurrentSquareNumber()].setOwnerNumber(i);
                                        write(players[i].getName() + " bought the " + squares[players[i].getCurrentSquareNumber()]
                                                .getSquareName() + " for $"+squares[players[i].getCurrentSquareNumber()].getPrice() + ".");
                                        write(players[i].getName() + "'s current total cash is $" + players[i].getMoney() + ".");
                                    } else {
                                        write("No actions. Next player's turn.");
                                    }
                                } else {
                                    write(players[i].getName() + " does not have enough money to buy " +
                                            squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");
                                    write("No actions. Next player's turn.");
                                }
                            } else if(squares[players[i].getCurrentSquareNumber()].getOwnerNumber() == i){
                                write(players[i].getName()+" owns this square.");
                                write("No actions. Next player's turn.");
                            } else {
                                die1.rollDie();
                                write(players[i].getName() + " rolled a die.");
                                write("Die shows " + die1.getFaceValue() + ".");
                                players[i].setMoney(-(die1.getFaceValue() * 5) + 25);
                                if(players[i].getMoney() > 0){
                                    players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].setMoney((die1.getFaceValue() * 5) + 25);
                                    write(players[i].getName() + " paid $" + (die1.getFaceValue() * 5) + 25 +
                                            " as rent to " + players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getName() + ".");
                                    write(players[i].getName() + "'s current total cash is $" + players[i].getMoney() + ".");
                                    write(players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getName()+"'s current total cash is $"
                                            +players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getMoney() + ".");
                                } else {
                                    write(players[i].getName()+" does not have enough money to pay the rent for this square.");
                                }
                            }
                            break;
                        case "Utility_Square":
                            if (squares[players[i].getCurrentSquareNumber()].getOwnerNumber() == -1) {

                                if (players[i].getMoney() > squares[players[i].getCurrentSquareNumber()].getPrice()) {

                                    die1.rollDie();
                                    write(players[i].getName() + " rolled a die.");
                                    write("Die shows " + die1.getFaceValue() + ".");

                                    if (die1.getFaceValue() > 4) {
                                        players[i].setMoney(-squares[players[i].getCurrentSquareNumber()].getPrice());
                                        squares[players[i].getCurrentSquareNumber()].setOwnerNumber(i);
                                        write(players[i].getName() + " bought the " + squares[players[i].getCurrentSquareNumber()]
                                                .getSquareName() + " for $"+squares[players[i].getCurrentSquareNumber()].getPrice()+ ".");
                                        write(players[i].getName() + "'s current total cash is $" + players[i].getMoney() + ".");
                                    } else {
                                        write("No actions. Next player's turn.");
                                    }
                                } else {
                                    write(players[i].getName() + " does not have enough money to buy " +
                                            squares[players[i].getCurrentSquareNumber()].getSquareName() + ".");
                                    write("No actions. Next player's turn.");
                                }
                            } else if(squares[players[i].getCurrentSquareNumber()].getOwnerNumber() == i){
                                write(players[i].getName()+" owns this square.");
                                write("No actions. Next player's turn.");
                            } else {
                                die1.rollDie();
                                write(players[i].getName() + " rolled a die.");
                                write("Die shows " + die1.getFaceValue() + ".");
                                players[i].setMoney(-die1.getFaceValue() * 10);
                                if(players[i].getMoney() > 0){
                                    players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].setMoney(die1.getFaceValue() * 10);
                                    write(players[i].getName() + " paid $" + die1.getFaceValue() * 10 +
                                            " as rent to " + players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getName() + ".");
                                    write(players[i].getName() + "'s current total cash is $" + players[i].getMoney() + ".");
                                    write(players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getName()+"'s current total cash is $"
                                            +players[squares[players[i].getCurrentSquareNumber()].getOwnerNumber()].getMoney() + ".");
                                } else {
                                    write(players[i].getName()+" does not have enough money to pay the rent for this square.");
                                }
                            }
                            break;
                        default:
                            write("No actions. Next player's turn.");
                    }
                } else if (turnNumber <= players[i].getInJailTurnNumber() + 3){
                    if (players[i].getMoney() > 50) {
                        players[i].setMoney(-50);
                        players[i].setInJail(false);
                        write(players[i].getName() + " paid $50 to get out of the jail.");
                        write(players[i].getName() + "'s current total cash is $" + players[i].getMoney() + ".");
                    } else {
                        die1.rollDie();
                        die2.rollDie();
                        write(players[i].getName() + " rolled the dice to get out of the jail.");
                        write("Dice show: " + die1.getFaceValue() + " and " + die2.getFaceValue());

                        if (die1.getFaceValue() == die2.getFaceValue()){
                            players[i].setInJail(false);
                            write(players[i].getName() + " rolled doubles and got out of the jail.");
                        } else if (turnNumber == players[i].getInJailTurnNumber() + 3){
                            players[i].setMoney(-50);
                            write(players[i].getName() + " couldn't get out of the jail in three turns.");
                        } else {
                            write(players[i].getName() + " couldn't get out of the jail in this turn.");
                        }
                    }
                }
                if (players[i].getMoney() <= 0) {
                    write(players[i].getName() + " went bankrupt and removed from the game.");
                    for(int j = 0; j < squares.length; j++){
                        if( squares[j].getOwnerNumber() == i){
                            squares[j].setOwnerNumber(-1);
                        }
                    }
                    currentPlayerNumber--;
                }
                write("\r");
                write("\r");
            }
        }
    }

    public void write(String output){

        System.out.println(output);

        try {
            bw = new BufferedWriter(new FileWriter("monopoly-output.txt", true));
            bw.write(output);
            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) { }
            }
        }
    }

}
