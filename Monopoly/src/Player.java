public class Player {

    private String name;
    private int money;
    private int currentSquareNumber = 0;
    private boolean inJail = false;
    private int doubleCounter = 0;
    private int inJailTurnNumber = 0;

    public Player(String name,int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public int getCurrentSquareNumber() {
        return currentSquareNumber;
    }

    public void setCurrentSquareNumber(int currentSquareNumber) {
        this.currentSquareNumber = currentSquareNumber;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money += money;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public int getDoubleCounter() {
        return doubleCounter;
    }

    public void resetDoubleCounter() {
        doubleCounter = 0;
    }

    public void increaseDoubleCounter(){
        doubleCounter++;
    }

    public int getInJailTurnNumber() {
        return inJailTurnNumber;
    }

    public void setInJailTurnNumber(int inJailTurnNumber) {
        this.inJailTurnNumber = inJailTurnNumber;
    }
}
