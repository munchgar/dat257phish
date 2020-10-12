package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import org.phish.classes.FoodItem;
import org.phish.database.DBHandler;

import java.lang.Math;

import javafx.scene.layout.VBox;
import org.phish.Main;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class CalculatorPageController {
    DBHandler dbHandler = new DBHandler();

    double outputHousehold = 0;
    double outputAir = 0;
    double outputVehicle = 0;
    double outputFood = 0;
    double outputPublicTransport = 0;
    double amount = 0;
    int foodCount = 0;
    int vehicleCount = 0;
    int publicTransportCount = 0;
    private static ArrayList<FoodItem> foodItemList = new ArrayList<>();

    static ObservableList<FoodItem> foodChoices;

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
            "Train in Sweden",
            "Train outside of Sweden",
            "Ferry"
    );

    @FXML
    TextField txtAmountMember;
    @FXML
    TextField txtBillPrice;
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
    @FXML
    ChoiceBox chboxHouseType;

    @FXML
    public void initialize() throws SQLException {
        fetchFoodItems();
    }


    private void fetchFoodItems() throws SQLException {
        String SQLquery = "SELECT * FROM foodItem";
        dbHandler.connect();
        ResultSet rs = dbHandler.execQuery(SQLquery);
//        try (Connection conn = dbHandler.connect();
//             Statement stmt = conn.createStatement();
//              = stmt.executeQuery(SQLquery))

        while (rs.next()) {
            foodItemList.add(new FoodItem(rs.getInt("foodID"), rs.getString("foodName"), rs.getDouble("co2g")));
        }
        foodChoices = FXCollections.observableArrayList(foodItemList);
//        } catch(SQLException e) {
//            System.out.println(e.getMessage());
//        }

    }

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
            ChoiceBox choiceFoodType = new ChoiceBox<FoodItem>(foodChoices);
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
                        switch ((String) (((ChoiceBox) vBoxVehicleSize.getChildren().toArray()[count]).getValue())) {
                            case "Small" -> outputTemp += (amount * 63) / 1000;
                            case "Medium" -> outputTemp += (amount * 79) / 1000;
                            case "Large" -> outputTemp += (amount * 106) / 1000;
                            default -> System.out.println("Please enter what size of vehicle you were traveling in");
                        }
                    }
                    //If The choice is a diesel car, then check what size they chose and calculate co2 based on km
                    else if (((ChoiceBox) vBoxVehicleType.getChildren().toArray()[count]).getValue().equals("Diesel Car")) {
                        switch ((String) (((ChoiceBox) vBoxVehicleSize.getChildren().toArray()[count]).getValue())) {
                            case "Small" -> outputTemp += (amount * 40) / 1000;
                            case "Medium" -> outputTemp += (amount * 55) / 1000;
                            case "Large" -> outputTemp += (amount * 73) / 1000;
                            default -> System.out.println("Please enter what size of vehicle you were traveling in");
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
                    tempOutput += (amount * (((FoodItem) ((ChoiceBox) vBoxFoodType.getChildren().toArray()[count]).getValue()).getCo2g())) / 1000; // ¯\_(ツ)_/¯ <-- WTF
                }
                count++;
            }
            outputFood = tempOutput;
            System.out.println("CO2: " + outputFood + "kg");
            amount = 0;
        }
    }

    public void CalculatePublicTransport(ActionEvent actionEvent) throws IOException {
        int count = 0;
        double outputTemp = 0;
        for (Node txtTransportAmount : vBoxTransportAmount.getChildren()) {
            if (!(((TextField) txtTransportAmount).getText().equals("")) && ((TextField) txtTransportAmount).getText().matches("[0-9]+") && ((TextField) txtTransportAmount).getText().length() < 8) {
                amount = Integer.parseInt(((TextField) txtTransportAmount).getText());
                switch ((String) (((ChoiceBox) vBoxTransportType.getChildren().toArray()[count]).getValue())) {
                    case "Bus" -> outputTemp += (amount * 27) / 1000;
                    case "Train in Sweden" -> outputTemp += (amount * 10) / 1000;
                    case "Train outside of Sweden" -> outputTemp += (amount * 37) / 1000;
                    case "Ferry" -> outputTemp += (amount * 170) / 1000;
                    default -> System.out.println("Please enter what public transport you were traveling with");
                }
            }
            count++;
        }
        outputPublicTransport = outputTemp;
        System.out.println("CO2: " + outputPublicTransport + "kg");
        amount = 0;
    }

    public void CalculateHousehold(ActionEvent actionEvent) throws IOException {
        if (!(txtBillPrice.getText().equals("")) && txtBillPrice.getText().matches("[0-9]+") && txtBillPrice.getText().length() < 8) {
            if (!(txtAmountMember.getText().equals("")) && txtAmountMember.getText().matches("[0-9]+") && txtAmountMember.getText().length() < 8) {
                switch ((String) chboxHouseType.getValue()) {
                    //Calcs blir till int, vilket inte är så nice så ska fixa det så det blir double
                    case "Apartment" -> outputHousehold = (double) ((Integer.parseInt(txtBillPrice.getText()) * 46) / (Integer.parseInt(txtAmountMember.getText()))) / 1000;
                    case "Villa" -> outputHousehold = (double) ((Integer.parseInt(txtBillPrice.getText()) * 46) / (Integer.parseInt(txtAmountMember.getText()))) / 1000;
                    case "Town House" -> outputHousehold = (double) ((Integer.parseInt(txtBillPrice.getText()) * 46) / (Integer.parseInt(txtAmountMember.getText()))) / 1000;
                    default -> System.out.println("Error");
                }
                System.out.println("CO2: " + outputHousehold + "kg");
            } else {
                System.out.println("Please enter the amount of people you are living with first");
            }
        } else {
            System.out.println("Please enter how much your electrical bill was on first");
        }
    }

    public void CalculateResult(ActionEvent actionEvent) {
        double outputResult = outputVehicle + outputPublicTransport + outputFood + outputAir + outputHousehold;
        System.out.println("Your total emissions has added up to " + outputResult + "Kg (CO2)");
    }
}

