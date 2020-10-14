package org.phish.controllers;

import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.phish.classes.DateAxis;


import javafx.fxml.FXML;
import javafx.scene.chart.*;
import org.phish.Main;
import org.phish.database.DBHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class ChartViewController {

    public enum TimeFrame {
        LAST_WEEK,
        LAST_MONTH,
        LAST_YEAR
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final ObservableList<XYChart.Data<Date, Number>> co2OverTimeList = FXCollections.observableArrayList();

    @FXML
    private DateAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<Date,Number> co2OverTimeChart;

    @FXML
    private PieChart co2SourcePieChart;

    private DBHandler dbHandler = DBHandler.getInstance();

    private void populatePieChart (TimeFrame timeFrame, PieChart chart) {
        switch (timeFrame) {
            case LAST_WEEK:
                chart.getData().add(new PieChart.Data("Transport",50));
                chart.getData().add(new PieChart.Data("Housing",15));
                chart.getData().add(new PieChart.Data("Food",10));
                chart.getData().add(new PieChart.Data("Other",25));
                break;
            case LAST_MONTH:
                chart.getData().add(new PieChart.Data("Transport",30));
                chart.getData().add(new PieChart.Data("Housing",20));
                chart.getData().add(new PieChart.Data("Food",10));
                chart.getData().add(new PieChart.Data("Other",25));
                break;
            default:
                chart.getData().add(new PieChart.Data("Transport",40));
                chart.getData().add(new PieChart.Data("Housing",15));
                chart.getData().add(new PieChart.Data("Food",25));
                chart.getData().add(new PieChart.Data("Other",20));
                break;
        }
    }

    // Populates all the charts with the timeFrame and category provided.
    public void populate (TimeFrame timeFrame, String category) {
        clearCharts();
        co2OverTimeChart.getData().add(new XYChart.Series<>(co2OverTimeList));
        switch (category) {
            case "Total":
                //populateLineChart(timeFrame, co2OverTimeList);
                populatePieChart(timeFrame, co2SourcePieChart);
                break;
            case "Housing":
                //populateLineChart(timeFrame, co2OverTimeList);
                break;
            case "Food":
                // This query will return the current user's total co2 emissions from food by date
                String SQLquery = "SELECT date, SUM(co2g*weight) AS co2 FROM foodConsumptionActivity INNER JOIN foodItem USING(foodID) " +
                        "WHERE userID = ? AND date BETWEEN datetime('now',?) AND datetime('now','localtime') GROUP BY date ORDER BY date ASC";
                populateCo2OverTimeChart(timeFrame, SQLquery);
                break;
        }
    }

    private void populateCo2OverTimeChart(TimeFrame timeFrame, String query) {
        if (dbHandler.connect()) {
            try {
                PreparedStatement pstmt = dbHandler.getConn().prepareStatement(query);
                pstmt.setInt(1, Main.getCurrentUserId());
                pstmt.setString(2,timeFrame == TimeFrame.LAST_WEEK ? "-6 days" : timeFrame == TimeFrame.LAST_MONTH ? "-30 days" : "-364 days");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    try {
                        XYChart.Data<Date, Number> data = new XYChart.Data<>(sdf.parse(rs.getString("date")), rs.getDouble("co2"));
                        data.setNode(createDataNode(data.YValueProperty()));
                        co2OverTimeList.add(new XYChart.Data<>(sdf.parse(rs.getString("date")), rs.getDouble("co2")));
                    } catch(ParseException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    private static Node createDataNode(ObjectExpression<Number> value) {
        Label label = new Label();
        label.textProperty().bind(value.asString("%,.2f"));

        Pane pane = new Pane();
        pane.setShape(new Circle(6));
        pane.setScaleShape(false);

        label.translateYProperty().bind(label.heightProperty().divide(-1.5));

        return pane;
    }

    private void clearCharts() {
        co2SourcePieChart.getData().clear();
        co2OverTimeChart.getData().clear();
        co2OverTimeList.clear();
    }
}
