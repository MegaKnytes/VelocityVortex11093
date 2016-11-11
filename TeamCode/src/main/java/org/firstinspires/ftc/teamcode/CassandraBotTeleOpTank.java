/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.HardwareCassandraBot;
import com.qualcomm.robotcore.util.ElapsedTime;

import static android.os.SystemClock.sleep;


@TeleOp(name="CassandraBot: Teleop Tank", group="CassandraBot")
public class CassandraBotTeleOpTank extends OpMode{

    /* Declare OpMode members. */
    HardwareCassandraBot robot       = new HardwareCassandraBot(); // use the class created to define a Pushbot's hardware


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        updateTelemetry(telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }
    double position = .5;

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double left = 0.0;
        double right = 0.0;
        double sweeper = 0.0;
        double max_pos = 1.0;
        double min_pos = 0.0;
        double increment = 0.01;
        double cycleMS = 50;
        boolean right_bumper1;
        boolean right_bumper2;
        boolean left_bumper2;

        //double senseLight = 0.0;
        //boolean senseTouch;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        // device name 'left_drive' and 'right_drive'
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;
        right_bumper1 = gamepad1.right_bumper;

        int rbumper1int = (right_bumper1) ? 8:1;

        robot.leftMotor.setPower(left/rbumper1int);
        robot.rightMotor.setPower(right/rbumper1int);
        //senseLight = robot.opticalDistanceSensor.getLightDetected();
        //senseTouch = robot.touchSense.isPressed();


        //Run sweeper forward and backward (device name 'sweeper_drive')
        sweeper = -gamepad2.right_stick_y;
        robot.sweeperMotor.setPower(sweeper);

        //Button Motor left and right, using right and left bumper on gamepad 2 (Device name 'button_drive')
        right_bumper2 = gamepad2.right_bumper;
        left_bumper2 = gamepad2.left_bumper;

        if (right_bumper2 && position<max_pos)
        {
            position += increment;
            sleep(50);
        }

        else if (left_bumper2 && position<min_pos)
        {
            position -= increment;
            sleep(50);
        }
        robot.buttonMotor.setPosition(position);

        telemetry.addData("sweeper", "%.2f", sweeper);
        telemetry.addData("left",  "%.2f", left);
        telemetry.addData("right", "%.2f", right);
        telemetry.addData("rbumper1int", rbumper1int);
        telemetry.addData("Servo Position", position);
        //telemetry.addData("senseLight", "%.3f", senseLight);
        //telemetry.addData("touchsensor", senseTouch);

        // test to examine optical distance sensor data (device name 'ods')
        //double odsLightDetected = robot.opticalDistanceSensor.getLightDetected();
        //double odsRawLightDetected = robot.opticalDistanceSensor.getRawLightDetected();
        //telemetry.addData("Raw",    odsRawLightDetected);
        //telemetry.addData("Normal", odsLightDetected);

        updateTelemetry(telemetry);


    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
