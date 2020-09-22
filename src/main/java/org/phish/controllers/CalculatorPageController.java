package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.lang.Math;
import org.phish.Main;

import java.io.IOException;

public class CalculatorPageController {
    double outputAir = 0;
    double outputCar = 0;
    double kilometers = 0;
    @FXML
    Button btnCalcAir;
    @FXML
    TextField txtKilometerAir;
    @FXML
    Button btnCalcCar;
    @FXML
    TextField txtKilometerCar1;
    @FXML
    TextField txtKilometerCar2;
    @FXML
    ChoiceBox choiceTypeOfCar1;
    @FXML
    ChoiceBox choiceTypeOfCar2;
    @FXML
    ChoiceBox choiceSizeOfCar1;
    @FXML
    ChoiceBox choiceSizeOfCar2;

    @FXML //Calculates the CO2 output from the inputs of kilometers
    public void CalculateAir(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnCalcAir) {
            if (!txtKilometerAir.getText().equals("") && txtKilometerAir.getText().matches("[0-9]+") && txtKilometerAir.getText().length() > 1) {
                //If the kilometers traveled are less than 1500km this equation will be used
                if (Integer.parseInt(txtKilometerAir.getText()) < 1500) {
                    //((x+50)•114x^-0,658•uTtw•LF)•(1+HF(x)+uWtT)  <-- This is the equation used
                    kilometers = Integer.parseInt(txtKilometerAir.getText());
                    outputAir = ((kilometers + 50) * 114 * (Math.pow(kilometers, -0.658)) * 0.0735 * 0.8);
                    if (kilometers < 500) {
                         outputAir *= (1 + 1.2);
                    } else if (kilometers <= 1000 && kilometers >= 500) {
                        outputAir *= (1 + (0.9 * ((kilometers - 500) / 500)) + 1.2);
                    } else if (kilometers > 1000)
                        outputAir *=  (1 + 0.9 + 1.2);
                }
                //If the kilometers traveled are less than 5000km and more than 1500km this equation will be used
                else if (Integer.parseInt(txtKilometerAir.getText()) > 1499 && Integer.parseInt(txtKilometerAir.getText()) < 5001) {
                    //((x+50)•(0,94 - 1,1•10^-5x)•uTtw•LF)•(1+HF(x)+uWtT)  <-- This is the equation used
                    kilometers = Integer.parseInt(txtKilometerAir.getText());
                    outputAir = ((kilometers + 50) * (0.94 - 1.1 * Math.pow(10, -5)) * 0.0735 * 0.8);
                    if (kilometers < 500) {
                         outputAir *= (1 + 1.2);
                    } else if (kilometers <= 1000 && kilometers >= 500) {
                        outputAir *= (1 + (0.9 * ((kilometers - 500) / 500)) + 1.2);
                    } else if (kilometers > 1000) {
                        outputAir *= (1 + 0.9 + 1.2);
                    }
                }
                //If the kilometers traveled are more than 5000km this equation will be used
                else if (Integer.parseInt(txtKilometerAir.getText()) > 5000) {
                    //((x+50)•0,89•uTtw•LF)•(1+HF(x)+uWtT)  <-- This is the equation used
                    kilometers = Integer.parseInt(txtKilometerAir.getText());
                    outputAir = ((kilometers + 50) * 0.89 * 0.0735 * 0.8);
                    if (kilometers < 500) {
                         outputAir *= (1 + 1.2);
                    } else if (kilometers <= 1000 && kilometers >= 500) {
                        outputAir *= (1 + (0.9 * ((kilometers - 500) / 500)) + 1.2);
                    } else if (kilometers > 1000) {
                        outputAir *= (1 + 0.9 + 1.2);
                    }
                }
                System.out.println("CO2: " + outputAir + "kg");
                kilometers = 0;
            }
        }
    }

    public void CalculateCar(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnCalcCar) {
            if (!txtKilometerCar1.getText().equals("") && txtKilometerCar1.getText().matches("[0-9]+")) {
                kilometers = Integer.parseInt(txtKilometerCar1.getText());
                //If The choice is a petrol car, then check what size they chose and calculate co2 based on km
                if(choiceTypeOfCar1.getValue().equals("Petrol Car")){
                    if(choiceSizeOfCar1.getValue().equals("Small")){
                        outputCar = (kilometers*63)/1000;
                    }else if(choiceSizeOfCar1.getValue().equals("Medium")){
                        outputCar = (kilometers*79)/1000;
                    }else if(choiceSizeOfCar1.getValue().equals("Large")){
                        outputCar = (kilometers*106)/1000;
                    }else if(choiceSizeOfCar1.getValue().equals("Size")) {
                        System.out.println("Please Enter What size of Car you were traveling in");
                    }
                }
                //If The choice is a diesel car, then check what size they chose and calculate co2 based on km
                else if(choiceTypeOfCar1.getValue().equals("Diesel Car")){
                    if(choiceSizeOfCar1.getValue().equals("Small")){
                        outputCar = (kilometers*40)/1000;
                    }else if(choiceSizeOfCar1.getValue().equals("Medium")){
                        outputCar = (kilometers*55)/1000;
                    }else if(choiceSizeOfCar1.getValue().equals("Large")){
                        outputCar = (kilometers*73)/1000;
                    }else if(choiceSizeOfCar1.getValue().equals("Size")) {
                        System.out.println("Please Enter Whar size of Car you were traveling in");
                    }
                }
                //If they have entered kilometers but not what type of car, then ask them to choose what type of car
                else if(choiceTypeOfCar1.getValue().equals("Type of Car")){
                    System.out.println("Please Choose a type of Car OR remove the entered kilometers before calculating");
                    //Block from calculating until fixed? Display a warning or something?
                }
            }
            //*********** CAR NR2
            if (!txtKilometerCar2.getText().equals("") && txtKilometerCar2.getText().matches("[0-9]+")) {
                kilometers = Integer.parseInt(txtKilometerCar1.getText());
                //If The choice is a petrol car, then check what size they chose and calculate co2 based on km
                if(choiceTypeOfCar2.getValue().equals("Petrol Car")){
                    if(choiceSizeOfCar2.getValue().equals("Small")){
                        outputCar += (kilometers*63)/1000;
                    }else if(choiceSizeOfCar2.getValue().equals("Medium")){
                        outputCar += (kilometers*79)/1000;
                    }else if(choiceSizeOfCar2.getValue().equals("Large")){
                        outputCar += (kilometers*106)/1000;
                    }else if(choiceSizeOfCar2.getValue().equals("Size")) {
                        System.out.println("Please Enter What size of Car you were traveling in");
                    }
                }
                //If The choice is a diesel car, then check what size they chose and calculate co2 based on km
                else if(choiceTypeOfCar2.getValue().equals("Diesel Car")){
                    if(choiceSizeOfCar2.getValue().equals("Small")){
                        outputCar += (kilometers*40)/1000;
                    }else if(choiceSizeOfCar2.getValue().equals("Medium")){
                        outputCar += (kilometers*55)/1000;
                    }else if(choiceSizeOfCar2.getValue().equals("Large")){
                        outputCar += (kilometers*73)/1000;
                    }else if(choiceSizeOfCar2.getValue().equals("Size")) {
                        System.out.println("Please Enter What size of Car you were traveling in");
                    }
                }
                //If they have entered kilometers but not what type of car, then ask them to choose what type of car
                else if(choiceTypeOfCar2.getValue().equals("Type of Car")){
                    System.out.println("Please Choose a type of Car OR remove the entered kilometers before calculating");
                    //Block from calculating until fixed? Display a warning or something?
                }
            }
            System.out.println("CO2: " + outputCar + "kg");
            kilometers = 0;
        }
    }
}


