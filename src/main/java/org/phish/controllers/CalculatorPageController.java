package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.lang.Math;
import javafx.scene.layout.VBox;
import org.phish.Main;
import java.io.IOException;

public class CalculatorPageController {
    double outputAir = 0;
    double outputVehicle = 0;
    double outputFood = 0;
    double kilometers = 0;
    int foodCount = 0;
    int vehicleCount = 0;
    static ObservableList<String> foodChoices = FXCollections.observableArrayList(
            "Type of Food",
            "Beef",
            "Lamb",
            "Pork",
            "Chicken",
            "Other ruminant meat",
            "Other monogastric meat",
            "Eggs",
            "Milk/Yogurt",
            "Cream",
            "Butter",
            "Cheese",
            "Bread",
            "Pasta",
            "Rice",
            "Other cereals",
            "Apples",
            "Oranges",
            "Bananas",
            "Other regional fruits",
            "Other imported fruits",
            "Onions",
            "Carrots & other root vegetables",
            "Tomatoes",
            "Cabbage",
            "Lettuce",
            "Other regional vegetables",
            "Other imported vegetables",
            "Coffee",
            "Tea",
            "Sweets",
            "Cod",
            "Salmon",
            "Vegetarian meat substitute",
            "Vegetarian dairy substitute"
    );
    static ObservableList<String> vehicleTypes = FXCollections.observableArrayList(
            "Type of Vehicle",
            "Petrol Car",
            "Diesel Car"
    );
    static ObservableList<String> vehicleSizes = FXCollections.observableArrayList(
            "Size",
            "Small",
            "Medium",
            "Large"
    );
    @FXML
    Button btnCalcAir;
    @FXML
    Button btnCalcVehicle;
    @FXML
    Button btnAddMoreFood;
    @FXML
    Button btnAddVehicle;
    @FXML
    Button btnCalcFood;
    @FXML
    TextField txtKilometerAir;
    @FXML
    VBox vBoxFoodType;
    @FXML
    VBox vBoxFoodAmount;
    @FXML
    VBox vBoxVehicleType;
    @FXML
    VBox vBoxVehicleSize;
    @FXML
    VBox vBoxVehicleAmount;


    public void AddVehicle(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnAddVehicle) {
            //Creates a new choiceBox for vehicle types
            ChoiceBox choiceVehicleType = new ChoiceBox<String>(vehicleTypes);
            choiceVehicleType.setId("choiceVehicleType"+vehicleCount);
            choiceVehicleType.setMaxWidth(Integer.MAX_VALUE);
            choiceVehicleType.setValue("Type of Vehicle");
            //Creates a new choiceBox for vehicle sizes
            ChoiceBox choiceVehicleSize = new ChoiceBox<String>(vehicleSizes);
            choiceVehicleSize.setId("choiceVehicleSize"+vehicleCount);
            choiceVehicleSize.setMaxWidth(Integer.MAX_VALUE);
            choiceVehicleSize.setValue("Size");
            //Creates a new textField for driven amount
            TextField txtVehicleAmount = new TextField();
            txtVehicleAmount.setId("txtVehicleAmount"+vehicleCount);
            txtVehicleAmount.setMaxWidth(Integer.MAX_VALUE);
            vehicleCount++;
            //Adds all of the created elements to their designated vBox
            vBoxVehicleType.getChildren().add(choiceVehicleType);
            vBoxVehicleSize.getChildren().add(choiceVehicleSize);
            vBoxVehicleAmount.getChildren().add(txtVehicleAmount);
        }
    }

    public void AddMoreFood(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnAddMoreFood) {
            //Creates a new choiceBox for food types
            ChoiceBox choiceFoodType = new ChoiceBox<String>(foodChoices);
            choiceFoodType.setId("choiceFoodType"+foodCount);
            choiceFoodType.setMaxWidth(Integer.MAX_VALUE);
            choiceFoodType.setValue("Type of Food");
            //Creates a new textField for food amount
            TextField txtFoodAmount = new TextField();
            txtFoodAmount.setId("txtFoodAmount"+foodCount);
            txtFoodAmount.setMaxWidth(Integer.MAX_VALUE);
            foodCount++;
            //Adds all of the created elements to their designated vBox
            vBoxFoodType.getChildren().add(choiceFoodType);
            vBoxFoodAmount.getChildren().add(txtFoodAmount);
        }
    }

    public void CalculateVehicle(ActionEvent actionEvent) throws IOException {
        double outputTemp = 0;
        int count = 0;
        if (actionEvent.getSource() == btnCalcVehicle) {
            for(Node txtVehicleAmount : vBoxVehicleAmount.getChildren()) {
                if(!(((TextField) txtVehicleAmount).getText().equals("")) && ((TextField) txtVehicleAmount).getText().matches("[0-9]+") && ((TextField) txtVehicleAmount).getText().length() < 8){
                    kilometers = Integer.parseInt(((TextField) txtVehicleAmount).getText());
                    //If The choice is a petrol car, then check what size they chose and calculate co2 based on km
                    if(((ChoiceBox)vBoxVehicleType.getChildren().toArray()[count]).getValue().equals("Petrol Car")){
                        if(((ChoiceBox)vBoxVehicleSize.getChildren().toArray()[count]).getValue().equals("Small")){
                            outputTemp += (kilometers*63)/1000;
                        }else if(((ChoiceBox)vBoxVehicleSize.getChildren().toArray()[count]).getValue().equals("Medium")){
                            outputTemp += (kilometers*79)/1000;
                        }else if(((ChoiceBox)vBoxVehicleSize.getChildren().toArray()[count]).getValue().equals("Large")){
                            outputTemp += (kilometers*106)/1000;
                        }else if(((ChoiceBox)vBoxVehicleSize.getChildren().toArray()[count]).getValue().equals("Size")){
                            System.out.println("Please enter what size of vehicle you were traveling in");
                        }
                    }
                    //If The choice is a diesel car, then check what size they chose and calculate co2 based on km
                    else if(((ChoiceBox)vBoxVehicleType.getChildren().toArray()[count]).getValue().equals("Diesel Car")){
                        if(((ChoiceBox)vBoxVehicleSize.getChildren().toArray()[count]).getValue().equals("Small")){
                            outputTemp += (kilometers*40)/1000;
                        }else if(((ChoiceBox)vBoxVehicleSize.getChildren().toArray()[count]).getValue().equals("Medium")){
                            outputTemp += (kilometers*55)/1000;
                        }else if(((ChoiceBox)vBoxVehicleSize.getChildren().toArray()[count]).getValue().equals("Large")){
                            outputTemp += (kilometers*73)/1000;
                        }else if(((ChoiceBox)vBoxVehicleSize.getChildren().toArray()[count]).getValue().equals("Size")){
                            System.out.println("Please enter what size of vehicle you were traveling in");
                        }
                    }
                    //If no vehicle type has been selected
                    else if(((ChoiceBox)vBoxVehicleType.getChildren().toArray()[count]).getValue().equals("Type of Vehicle")) {
                        System.out.println("Please choose a type of vehicle OR remove the entered kilometers before calculating");
                        //Block from calculating until fixed? Display a warning or something?
                    }
                }
                count++;
            }
            outputVehicle = outputTemp;
            System.out.println("CO2: "+outputVehicle+"kg");
            kilometers = 0;
        }
    }

    public void CalculateAir(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnCalcAir) {
            if (!txtKilometerAir.getText().equals("") && txtKilometerAir.getText().matches("[0-9]+") && txtKilometerAir.getText().length() > 1 && txtKilometerAir.getText().length() < 8) {
                //If the kilometers traveled are less than 1500km this equation will be used
                if (Integer.parseInt(txtKilometerAir.getText()) < 1500) {
                    //This is the equation used here -> ((x+50)•114x^-0,658•uTtw•LF)•(1+HF(x)+uWtT)
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
                    //This is the equation used here -> ((x+50)•(0,94 - 1,1•10^-5x)•uTtw•LF)•(1+HF(x)+uWtT)
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
                    //This is the equation used here -> ((x+50)•0,89•uTtw•LF)•(1+HF(x)+uWtT)
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

    public void CalculateFood(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnCalcFood) {
            for (Node txtVehicleAmount : vBoxVehicleAmount.getChildren()) {

            }
        }
    }
}


