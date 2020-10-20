package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.phish.classes.FoodItem;
import org.phish.database.DBHandler;

import java.lang.Math;

import javafx.scene.layout.VBox;
import org.phish.Main;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class CalculatorPageController {
    DBHandler dbHandler = DBHandler.getInstance();

    double outputHousehold = 0;
    double outputAir = 0;
    double outputFood = 0;
    double outputPublicTransport = 0;
    double amount = 0;
    int foodCount = 1;
    int publicTransportCount = 1;
    private static ArrayList<FoodItem> foodItemList = new ArrayList<>();

    static ObservableList<FoodItem> foodChoices;

    static ObservableList<String> publicTransportType = FXCollections.observableArrayList(
            "Type of Public Transport",
            "Bus",
            "Train in Sweden",
            "Train outside of Sweden",
            "Ferry"
    );
    @FXML
    StackPane stackPaneCalc;
    @FXML
    Text errorTextFlight, errorTextHouse, errorTextPublicTransport, errorTextFood;
    @FXML
    TextField txtAmountMember, txtKilometerAir, txtBillPrice;
    @FXML
    VBox vBoxFoodType, vBoxTransportAmount, vBoxFoodAmount, vBoxTransportType, vBoxCalcsButtons;
    @FXML
    private BorderPane activitiesBorderPane, allEmissionsBorderPane;

    @FXML
    public void initialize() throws SQLException, IOException {
        foodItemList.clear(); // Avoid duplicating the list when user re-initializes the view
        fetchFoodItems();
        AddFood(null);
        AddPublicTransport(null);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("TransportActivities.fxml"));
        BorderPane centerView = loader.load();
        activitiesBorderPane.setCenter(centerView);
        loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("AllEmissions.fxml"));
        centerView = loader.load();
        allEmissionsBorderPane.setCenter(centerView);
    }

    private void fetchFoodItems() throws SQLException {
        String SQLquery = "SELECT * FROM foodItem";
        if (dbHandler.connect()) {
            ResultSet rs = dbHandler.execQuery(SQLquery);

            while (rs.next()) {
                foodItemList.add(new FoodItem(rs.getInt("foodID"), rs.getString("foodName"), rs.getDouble("co2g")));
            }
            foodChoices = FXCollections.observableArrayList(foodItemList);

        } else {
            System.err.println("bad :(");
        }
    }

    public void AddFood(ActionEvent actionEvent) throws IOException {
        //Creates a new choiceBox for food types
        ChoiceBox choiceFoodType = new ChoiceBox<FoodItem>(foodChoices);
        choiceFoodType.setId("choiceFoodType" + foodCount);
        choiceFoodType.setValue(foodChoices.get(0));
        //Creates a new textField for food amount
        TextField txtFoodAmount = new TextField();
        txtFoodAmount.setId("txtFoodAmount" + foodCount);
        foodCount++;
        //Adds all of the created elements to their designated vBox
        vBoxFoodType.getChildren().add(choiceFoodType);
        vBoxFoodAmount.getChildren().add(txtFoodAmount);
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

    public void CalculateAir(ActionEvent actionEvent) throws IOException {
        if (!txtKilometerAir.getText().equals("") && txtKilometerAir.getText().matches("[0-9]+") && txtKilometerAir.getText().length() > 1 && txtKilometerAir.getText().length() < 8) {
            //If the kilometers traveled are less than 1500km this equation will be used
            if (Integer.parseInt(txtKilometerAir.getText()) < 1500) {
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
            errorTextFlight.setText("Activity successfully added");
            errorTextFlight.setFill(Color.GREEN);
            outputAir = (double)Math.round(outputAir*100)/100;
            amount = 0;
        }else {
            errorTextFlight.setText("Please Fill in the required areas (at double digits)");
            errorTextFlight.setFill(Color.RED);
        }
    }

    public void CalculateFood(ActionEvent actionEvent) throws IOException {
        boolean error = false;
        int count = 0;
        double tempOutput = 0;
        for (Node txtFoodAmount : vBoxFoodAmount.getChildren()) {
            if (!(((TextField) txtFoodAmount).getText().equals("")) && ((TextField) txtFoodAmount).getText().matches("[0-9]+") && ((TextField) txtFoodAmount).getText().length() < 8) {
                FoodItem foodItem = ((FoodItem) ((ChoiceBox) vBoxFoodType.getChildren().toArray()[count]).getValue());
                amount = Integer.parseInt(((TextField) txtFoodAmount).getText());
                addFoodConsumptionActivity(foodItem, amount);

                tempOutput += (amount * foodItem.getCo2g()) / 1000;
            }else {
                error = true;
            }
            count++;
        }
        if(error){
            errorTextFood.setText("Please fill in the required areas (Enter 0 if you accidentally added a food type)");
            errorTextFood.setFill(Color.RED);
        }else {
            outputFood = (double)Math.round(tempOutput*100)/100;
            errorTextFood.setText("Activity successfully added");
            errorTextFood.setFill(Color.GREEN);
            amount = 0;
        }
    }

    // Adds a new consumption activity in the database. The database will automatically append the date column with the current date.
    private void addFoodConsumptionActivity(FoodItem foodItem, double weight) {
        String SQLquery = "INSERT INTO foodConsumptionActivity (userID, foodID, weight) VALUES (?,?,?)";
        if (dbHandler.connect()) {
            try {
                PreparedStatement pstmt = dbHandler.getConn().prepareStatement(SQLquery);
                pstmt.setInt(1, Main.getCurrentUserId());
                pstmt.setInt(2, foodItem.getID());
                pstmt.setDouble(3, weight);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                if (e.getErrorCode() == 19) {
                    try {
                        // If met with a constraint error (code 19), the user has already logged this food item for today.
                        // Just add the additional weight to the already existing entry.
                        String addQuery = "UPDATE foodConsumptionActivity SET weight = weight + ? WHERE userID = ? AND foodID = ? AND date = ?";
                        PreparedStatement updateStmt = dbHandler.getConn().prepareStatement(addQuery);
                        updateStmt.setDouble(1, weight);
                        updateStmt.setInt(2, Main.getCurrentUserId());
                        updateStmt.setInt(3, foodItem.getID());
                        updateStmt.setString(4, LocalDate.now().toString()); // YYYY-MM-DD
                        updateStmt.executeUpdate();
                    } catch (SQLException exception) {
                        System.err.println(exception.getMessage());
                    }
                } else {
                    System.err.println(e.getMessage());
                }
            }
        } else {
            System.err.println("Connection not good :(");
        }
    }

    public void CalculatePublicTransport(ActionEvent actionEvent) throws IOException {
        boolean error = false;
        String errorString = "";
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
                    default -> {errorString = "Please enter the transport type you were traveling with"; error = true;}
                }
            }else {
                error = true;
                errorString = "Please fill in the amount traveled in km (Enter 0 if you accidentally added a transport type)";
            }
            count++;
        }
        if(error){
            errorTextPublicTransport.setText(errorString);
            errorTextPublicTransport.setFill(Color.RED);
        }else {
            outputPublicTransport = (double)Math.round(outputTemp*100)/100;
            errorTextPublicTransport.setText("Activity successfully added");
            errorTextPublicTransport.setFill(Color.GREEN);
            amount = 0;
        }
    }

    public void CalculateHousehold(ActionEvent actionEvent) throws IOException {
        if (!(txtBillPrice.getText().equals("")) && txtBillPrice.getText().matches("[0-9]+") && txtBillPrice.getText().length() < 8) {
            if (!(txtAmountMember.getText().equals("")) && txtAmountMember.getText().matches("[0-9]+") && txtAmountMember.getText().length() < 8) {
                outputHousehold = (double) Math.round((((Integer.parseInt(txtBillPrice.getText()) * 46) / (Integer.parseInt(txtAmountMember.getText()))) / 1000)*100)/100;
                errorTextHouse.setText("Activity successfully added");
                errorTextHouse.setFill(Color.GREEN);
            } else {
                errorTextHouse.setText("Please enter the amount of people you are living with first");
                errorTextHouse.setFill(Color.RED);
            }
        } else {
            errorTextHouse.setText("Please enter how much your electrical bill was on first");
            errorTextHouse.setFill(Color.RED);
        }
    }

    //Switches the scene in calculator-page
    public void showScene(ActionEvent actionEvent){
        int btnValue;
        switch (((Button)actionEvent.getSource()).getText()) {
            case "Food" -> btnValue = 1;
            case "House" -> btnValue = 2;
            case "Flight" -> btnValue = 3;
            case "Personal Transport" -> btnValue = 4;
            case "Public Transport" -> btnValue = 5;
            case "Results" -> btnValue = 6;
            default -> btnValue = 0;
        }
        for (Node btnSceneSelect : vBoxCalcsButtons.getChildren()) {
            btnSceneSelect.setDisable(false);
            if((actionEvent.getSource().equals(btnSceneSelect))) {
                btnSceneSelect.setDisable(true);
            }
        }
        for (Node anchorPaneStack : stackPaneCalc.getChildren()) {
            anchorPaneStack.setVisible(anchorPaneStack.getId().equals("anchor" + btnValue));
        }
    }
}