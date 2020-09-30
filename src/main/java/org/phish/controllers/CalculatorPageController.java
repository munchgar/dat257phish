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
import java.util.Map;

public class CalculatorPageController {
    double outputAir = 0;
    double outputVehicle = 0;
    double outputFood = 0;
    double amount = 0;
    int foodCount = 0;
    int vehicleCount = 0;
    int publicTransportCount = 0;
    private static final Map<String, Double> foodMap = Map.ofEntries(
      Map.entry("Type of Food",0.0), Map.entry("Beef",48.4), Map.entry("Lamb",45.4), Map.entry("Pork",6.0), Map.entry("Chicken",2.06),
                Map.entry("Other ruminant meat",48.1), Map.entry("Other monogastric meat",4.2), Map.entry("Eggs",1.6), Map.entry("Milk/Yogurt",1.1), Map.entry("Cream",6.0),
                Map.entry("Butter",12.7), Map.entry("Cheese",9.7), Map.entry("Bread",0.8), Map.entry("Pasta",1.2), Map.entry("Rice",2.3), Map.entry("Other cereals",1.0),
                Map.entry("Apples",0.3), Map.entry("Oranges",0.8), Map.entry("Bananas",0.6), Map.entry("Other regional fruits",0.3), Map.entry("Other imported fruits",3.5),
                Map.entry("Onions",0.1), Map.entry("Carrots & other root vegetables",0.2), Map.entry("Tomatoes",1.5), Map.entry("Cabbage",0.5), Map.entry("Lettuce",0.3),
                Map.entry("Other regional vegetables",0.3), Map.entry("Other imported vegetables",3.5), Map.entry("Coffee",1.0), Map.entry("Tea",1.0), Map.entry("Salmon",2.7),
                Map.entry("Sweets",1.9), Map.entry("Cod",1.6), Map.entry("Vegetarian meat substitute",0.8), Map.entry("Vegetarian dairy substitute",0.9)
    );

    static ObservableList<String> foodChoices = FXCollections.observableArrayList(
            "Type of Food", "Beef", "Lamb", "Pork", "Chicken", "Other ruminant meat", "Other monogastric meat", "Eggs", "Milk/Yogurt", "Cream",
            "Butter", "Cheese", "Bread", "Pasta", "Rice", "Other cereals", "Apples", "Oranges", "Bananas", "Other regional fruits",
            "Other imported fruits", "Onions", "Carrots & other root vegetables", "Tomatoes", "Cabbage", "Lettuce", "Other regional vegetables",
            "Other imported vegetables", "Coffee", "Tea", "Sweets", "Cod", "Salmon", "Vegetarian meat substitute", "Vegetarian dairy substitute"
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
    static ObservableList<String> publicTransportType = FXCollections.observableArrayList(
            "Type of Public Transport",
            "Bus",
            "Train"
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
    @FXML
    VBox vBoxTransportType;
    @FXML
    VBox vBoxTransportAmount;


    public void AddVehicle(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnAddVehicle) {
            //Creates a new choiceBox for vehicle types
            ChoiceBox choiceVehicleType = new ChoiceBox<String>(vehicleTypes);
            choiceVehicleType.setId("choiceVehicleType" + vehicleCount);
            choiceVehicleType.setValue("Type of Vehicle");
            //Creates a new choiceBox for vehicle sizes
            ChoiceBox choiceVehicleSize = new ChoiceBox<String>(vehicleSizes);
            choiceVehicleSize.setId("choiceVehicleSize" + vehicleCount);
            choiceVehicleSize.setValue("Size");
            //Creates a new textField for driven amount
            TextField txtVehicleAmount = new TextField();
            txtVehicleAmount.setId("txtVehicleAmount" + vehicleCount);
            vehicleCount++;
            //Adds all of the created elements to their designated vBox
            vBoxVehicleType.getChildren().add(choiceVehicleType);
            vBoxVehicleSize.getChildren().add(choiceVehicleSize);
            vBoxVehicleAmount.getChildren().add(txtVehicleAmount);
        }
    }

    public void AddFood(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnAddMoreFood) {
            //Creates a new choiceBox for food types
            ChoiceBox choiceFoodType = new ChoiceBox<String>(foodChoices);
            choiceFoodType.setId("choiceFoodType" + foodCount);
            choiceFoodType.setValue("Type of Food");
            //Creates a new textField for food amount
            TextField txtFoodAmount = new TextField();
            txtFoodAmount.setId("txtFoodAmount" + foodCount);
            foodCount++;
            //Adds all of the created elements to their designated vBox
            vBoxFoodType.getChildren().add(choiceFoodType);
            vBoxFoodAmount.getChildren().add(txtFoodAmount);
        }
    }

    public void AddPublicTransport(ActionEvent actionEvent) throws IOException {
        //Creates a new choiceBox for public transport types
        ChoiceBox choiceTransportType = new ChoiceBox<String>(publicTransportType);
        choiceTransportType.setId("choiceTransportType" + publicTransportCount);
        choiceTransportType.setValue("Type of Public Transport");
        //Creates a new textField for food amount
        TextField txtTransportAmount = new TextField();
        txtTransportAmount.setId("txtTransportAmount" + publicTransportCount);
        publicTransportCount++;
        //Adds all of the created elements to their designated vBox
        vBoxTransportType.getChildren().add(choiceTransportType);
        vBoxTransportAmount.getChildren().add(txtTransportAmount);
    }

    public void CalculateVehicle(ActionEvent actionEvent) throws IOException {
        double outputTemp = 0;
        int count = 0;
        if (actionEvent.getSource() == btnCalcVehicle) {
            for (Node txtVehicleAmount : vBoxVehicleAmount.getChildren()) {
                if (!(((TextField) txtVehicleAmount).getText().equals("")) && ((TextField) txtVehicleAmount).getText().matches("[0-9]+") && ((TextField) txtVehicleAmount).getText().length() < 8) {
                    amount = Integer.parseInt(((TextField) txtVehicleAmount).getText());
                    //If The choice is a petrol car, then check what size they chose and calculate co2 based on km
                    if (((ChoiceBox) vBoxVehicleType.getChildren().toArray()[count]).getValue().equals("Petrol Car")) {
                        switch((String)(((ChoiceBox) vBoxVehicleSize.getChildren().toArray()[count]).getValue())){
                            case "Small":
                                outputTemp += (amount * 63) / 1000;
                                break;
                            case "Medium":
                                outputTemp += (amount * 79) / 1000;
                                break;
                            case "Large":
                                outputTemp += (amount * 106) / 1000;
                                break;
                            default:
                                System.out.println("Please enter what size of vehicle you were traveling in");
                                break;
                        }
                    }
                    //If The choice is a diesel car, then check what size they chose and calculate co2 based on km
                    else if (((ChoiceBox) vBoxVehicleType.getChildren().toArray()[count]).getValue().equals("Diesel Car")) {
                        switch((String)(((ChoiceBox) vBoxVehicleSize.getChildren().toArray()[count]).getValue())){
                            case "Small":
                                outputTemp += (amount * 40) / 1000;
                                break;
                            case "Medium":
                                outputTemp += (amount * 55) / 1000;
                                break;
                            case "Large":
                                outputTemp += (amount * 73) / 1000;
                                break;
                            default:
                                System.out.println("Please enter what size of vehicle you were traveling in");
                                break;
                        }
                    }
                    //If no vehicle type has been selected
                    else if (((ChoiceBox) vBoxVehicleType.getChildren().toArray()[count]).getValue().equals("Type of Vehicle")) {
                        System.out.println("Please choose a type of vehicle OR remove the entered kilometers before calculating");
                        //Block from calculating until fixed? Display a warning or something?
                    }
                }
                count++;
            }
            outputVehicle = outputTemp;
            System.out.println("CO2: " + outputVehicle + "kg");
            amount = 0;
        }
    }

    public void CalculateAir(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnCalcAir) {
            if (!txtKilometerAir.getText().equals("") && txtKilometerAir.getText().matches("[0-9]+") && txtKilometerAir.getText().length() > 1 && txtKilometerAir.getText().length() < 8) {
                //If the kilometers traveled are less than 1500km this equation will be used
                if (Integer.parseInt(txtKilometerAir.getText()) < 1500) {
                    //This is the equation used here -> ((x+50)•114x^-0,658•uTtw•LF)•(1+HF(x)+uWtT)
                    amount = Integer.parseInt(txtKilometerAir.getText());
                    outputAir = ((amount + 50) * 114 * (Math.pow(amount, -0.658)) * 0.0735 * 0.8);
                    if (amount < 500) {
                        outputAir *= (1 + 1.2);
                    } else if (amount <= 1000 && amount >= 500) {
                        outputAir *= (1 + (0.9 * ((amount - 500) / 500)) + 1.2);
                    } else if (amount > 1000)
                        outputAir *= (1 + 0.9 + 1.2);
                }
                //If the kilometers traveled are less than 5000km and more than 1500km this equation will be used
                else if (Integer.parseInt(txtKilometerAir.getText()) > 1499 && Integer.parseInt(txtKilometerAir.getText()) < 5001) {
                    //This is the equation used here -> ((x+50)•(0,94 - 1,1•10^-5x)•uTtw•LF)•(1+HF(x)+uWtT)
                    amount = Integer.parseInt(txtKilometerAir.getText());
                    outputAir = ((amount + 50) * (0.94 - 1.1 * Math.pow(10, -5)) * 0.0735 * 0.8);
                    if (amount < 500) {
                        outputAir *= (1 + 1.2);
                    } else if (amount <= 1000 && amount >= 500) {
                        outputAir *= (1 + (0.9 * ((amount - 500) / 500)) + 1.2);
                    } else if (amount > 1000) {
                        outputAir *= (1 + 0.9 + 1.2);
                    }
                }
                //If the kilometers traveled are more than 5000km this equation will be used
                else if (Integer.parseInt(txtKilometerAir.getText()) > 5000) {
                    //This is the equation used here -> ((x+50)•0,89•uTtw•LF)•(1+HF(x)+uWtT)
                    amount = Integer.parseInt(txtKilometerAir.getText());
                    outputAir = ((amount + 50) * 0.89 * 0.0735 * 0.8);
                    if (amount < 500) {
                        outputAir *= (1 + 1.2);
                    } else if (amount <= 1000 && amount >= 500) {
                        outputAir *= (1 + (0.9 * ((amount - 500) / 500)) + 1.2);
                    } else if (amount > 1000) {
                        outputAir *= (1 + 0.9 + 1.2);
                    }
                }
                System.out.println("CO2: " + outputAir + "kg");
                amount = 0;
            }
        }
    }

    public void CalculateFood(ActionEvent actionEvent) throws IOException {
        int count = 0;
        double tempOutput = 0;
        if (actionEvent.getSource() == btnCalcFood) {
            for (Node txtFoodAmount : vBoxFoodAmount.getChildren()) {
                if (!(((TextField) txtFoodAmount).getText().equals("")) && ((TextField) txtFoodAmount).getText().matches("[0-9]+") && ((TextField) txtFoodAmount).getText().length() < 8) {
                    amount = Integer.parseInt(((TextField) txtFoodAmount).getText());
                    tempOutput += (amount*foodMap.get(((ChoiceBox) vBoxFoodType.getChildren().toArray()[count]).getValue()))/1000;
                }
                count++;
            }
            outputFood = tempOutput;
            System.out.println(outputFood);
        }
    }

    public void CalculatePublicTransport(ActionEvent actionEvent) throws IOException {
        System.out.println("hej :)");
    }
}


