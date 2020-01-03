To do 12/27:
1. Try both copies of code, see if one works
2. If neither works, look at telemetry to see if it is updating and getting closer to the value
requested

Observations from 12/27:
1. The robot's front right wheel does not have as much power in Autonomous as it does in Tele-op.
2. The servo position stays the same, even when I change it in the code.

To do 12/28:
1. Check the output voltage from the control hub going to each motor using a multimeter while
continuously going forward (using the controller). If the voltage is the same for each motor, then
the control hub is most likely not the issue.
2. If the voltage is different, use a different control hub and change the code so that the power
for the front right wheel is the same as the other 3 wheels. If the voltage is the same, then keep
the code the same. Try swapping the wires from one motor with another and see if the front
right wheel is still the problem. If this doesn't work, something is probably wrong with the
translation of the code.

Observations from 12/28:
1. Another wheel (the back left wheel) now has the same problem as the front right wheel (MOST
IMPORTANT THING TO FIX)
2. Servos may be working, couldn't test it due to above
