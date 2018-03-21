import lejos.hardware.Button;
import lejos.hardware.Sound;

public class Main {

    public static void main(String[] args) {
        LineFollowing robot = new LineFollowing();
        Sound.beepSequence();
        robot.run();

        Button.ESCAPE.waitForPress();
        robot.interrupt();
    }
}
