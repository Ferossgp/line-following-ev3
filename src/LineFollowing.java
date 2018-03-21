import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

class LineFollowing extends Thread {
	private static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
	private static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	private static EV3ColorSensor leftSensor = new EV3ColorSensor(SensorPort.S1);
    private static EV3ColorSensor rightSensor = new EV3ColorSensor(SensorPort.S4);
    
    private int speed = 150;
    
    private double error = 0;
    private double integral = 0;
    private double derivative = 0;
    private double lastError = 0;
    private double correction = 0;
   
   
    private final double P_GAIN = 150;
    private final double I_GAIN = 0.1;
    private final double D_GAIN = 15;
    
    public void run() {
    	int leftSpeed = speed;
    	int rightSpeed = speed;
    	
        leftMotor.setSpeed(leftSpeed);
        rightMotor.setSpeed(rightSpeed);
        
        while(!Thread.currentThread().isInterrupted()) {
        	SampleProvider LightValueLeft = leftSensor.getRedMode();	
        	SampleProvider LightValueRight = rightSensor.getRedMode();	
        		
            float[] colorLeft = new float[LightValueLeft.sampleSize()];
            float[] colorRight = new float[LightValueRight.sampleSize()];
            
        	LightValueLeft.fetchSample(colorLeft, 0);
            LightValueRight.fetchSample(colorRight, 0);
            
            error = colorRight[0] - colorLeft[0];
           
            System.out.println("Error: " + error);
            
            integral += error;
            System.out.println("Integral: " + integral);
            
            derivative = error - lastError;
            System.out.println("Derivative: " + derivative);
            
            correction = P_GAIN * error + I_GAIN * integral + D_GAIN * derivative;
            
            leftSpeed = speed + (int) correction;
            rightSpeed = speed - (int) correction;
            
            leftMotor.setSpeed(leftSpeed);
            leftMotor.forward();
            
            rightMotor.setSpeed(rightSpeed);
            rightMotor.forward();
            
            lastError = error;
            
            if(Button.ESCAPE.isDown()) {
                this.interrupt();
                break;
            }
        }
    }
}
