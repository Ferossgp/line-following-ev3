# Line following EV3
The robot was programmed using LeJos and EJDK.
The robot construction is the base one built using standard instruction from the box.
For easier process and better results I used 2 light sensors.
PID was selected using empirical analysis:
```java
private final double P_GAIN = 150;
private final double I_GAIN = 0.1;
private final double D_GAIN = 15;    
```
They can be adjusted more, but this result is also satisfing the condition. Speed is 150, increasing speed makes
my robot to not pass the sharp angles.
Also increasing the I or D makes robot more unstable and shaking around the line.

Video: [Youtube](https://www.youtube.com/watch?v=YOoPPf9Z0c4)


