package org.phish.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.lang.Math;
import org.phish.Main;

import java.io.IOException;

public class CalculatorPageController {
    double outputAir = 0;
    double kilometers = 0;
    @FXML
    Button btnBeraknaFlyg;
    @FXML
    TextField txtKilometerFlyg;

    @FXML
    public void CalculateAir(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnBeraknaFlyg) {
            if (!txtKilometerFlyg.getText().equals("") && txtKilometerFlyg.getText().matches("[0-9]+") && txtKilometerFlyg.getText().length() > 1) {
                if (Integer.parseInt(txtKilometerFlyg.getText()) < 1500) {
                    //((x+50)•114x^-0,658•uTtw•LF)•(1+HF(x)+uWtT)
                    kilometers = Integer.parseInt(txtKilometerFlyg.getText());
                    outputAir = ((kilometers + 50) * 114 * (Math.pow(kilometers, -0.658)) * 0.0735 * 0.8);
                    if (kilometers < 500) {
                         outputAir *= (1 + 1.2);
                    } else if (kilometers <= 1000 && kilometers >= 500) {
                        outputAir *= (1 + (0.9 * ((kilometers - 500) / 500)) + 1.2);
                    } else if (kilometers > 1000)
                        outputAir *=  (1 + 0.9 + 1.2);
                }

                else if (Integer.parseInt(txtKilometerFlyg.getText()) > 1499 && Integer.parseInt(txtKilometerFlyg.getText()) < 5001) {
                    //((x+50)•(0,94 - 1,1•10-5x)•uTtw•LF)•(1+HF(x)+uWtT)
                    kilometers = Integer.parseInt(txtKilometerFlyg.getText());
                    outputAir = ((kilometers + 50) * (0.94 - 1.1 * Math.pow(10, -5)) * 0.0735 * 0.8);
                    if (kilometers < 500) {
                         outputAir *= (1 + 1.2);
                    } else if (kilometers <= 1000 && kilometers >= 500) {
                        outputAir *= (1 + (0.9 * ((kilometers - 500) / 500)) + 1.2);
                    } else if (kilometers > 1000) {
                        outputAir *= (1 + 0.9 + 1.2);
                    }
                }

                else if (Integer.parseInt(txtKilometerFlyg.getText()) > 5000) {
                    //((x+50)•0,89•uTtw•LF)•(1+HF(x)+uWtT)
                    kilometers = Integer.parseInt(txtKilometerFlyg.getText());
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
            }
        }
    }
}


