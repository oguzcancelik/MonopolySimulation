public class Square {

    private int squareNumber;
    private String squareName;
    private int ownerNumber = -1;
    private int price;
    private int rent;

    public Square(int squareNumber, String squareName) {
        this.squareNumber = squareNumber;
        this.squareName = squareName;
    }

    public Square(int squareNumber, String squareName, int price) {
        this.squareNumber = squareNumber;
        this.squareName = squareName;
        this.price = price;
    }

    public Square(int squareNumber, String squareName, int price, int rent) {
        this.squareNumber = squareNumber;
        this.squareName = squareName;
        this.price = price;
        this.rent = rent;
    }

    public int getSquareNumber() {
        return squareNumber;
    }

    public String getSquareName() {
        return squareName;
    }
    public int getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(int ownerNumber) {
        this.ownerNumber = ownerNumber;
    }

    public int getPrice() {
        return price;
    }

    public int getRent() {
        return rent;
    }
}
