import java.util.Random;

public class Die {

    Random random = new Random();
    private int faceValue;

    public int getFaceValue() {
        return faceValue;
    }

    public void rollDie(){
        faceValue=random.nextInt(6)+1;
    }

}
