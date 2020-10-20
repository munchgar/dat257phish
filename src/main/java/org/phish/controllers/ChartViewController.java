package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import org.phish.Main;
import org.phish.database.DBHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ChartViewController {

    private static final double CO2_GOAL_YEAR = 2000.0;

    public enum TimeFrame {
        LAST_WEEK("-6 days",CO2_GOAL_YEAR / 52),
        LAST_MONTH("-30 days",CO2_GOAL_YEAR / 12),
        LAST_YEAR("-364 days",CO2_GOAL_YEAR);

        public final String value;
        public final double co2Goal;


        TimeFrame(String value, double co2Goal) {
            this.value = value;
            this.co2Goal = co2Goal;
        }
    }

    private static final String CHART_COLOR = String.format("rgba(%d,%d,%d,1)",0, 184, 148);


    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final ObservableList<XYChart.Data<Date, Double>> co2OverTimeList = FXCollections.observableArrayList();

    @FXML
    private LineChart<Date,Double> co2OverTimeChart;

    @FXML
    private PieChart co2SourcePieChart;

    @FXML
    private CheckBox showGoalCheckBox;

    private final DBHandler dbHandler = DBHandler.getInstance();

    private String co2OverTimeQuery, co2SpecificsQuery;

    private TimeFrame timeFrame;

    // Populates all the charts with the timeFrame and category provided.
    public void populate (TimeFrame timeFrame, String category) {
        clearCharts();
        this.timeFrame = timeFrame;
        co2OverTimeChart.getData().add(new XYChart.Series<>());

        switch (category) {
            case "Total":
                showGoalCheckBox.setVisible(true);
                // Sums up all emissions by date in the given timeframe
                co2OverTimeQuery =
                        "SELECT t.*, SUM(co2) OVER (ORDER BY date ASC) AS acc_co2 FROM " +
                        "(SELECT date, round(SUM(co2),2) AS co2 FROM (SELECT date, round(SUM(distanceKm*litresKilometer*gCO2Litre) / 1000,2) AS co2 " +
                        "FROM transportActivity INNER JOIN vehicles ON FKVehicleId=vehicleId AND transportActivity.FKUserId=vehicles.FKUserId " +
                        "INNER JOIN fuelType ON vehicles.FKfuelType=fuelType.fuelId WHERE transportActivity.FKUserId= ? AND date BETWEEN datetime('now','"+timeFrame.value+"') " +
                        "AND datetime('now','localtime') GROUP BY date " +

                        "UNION " +

                        "SELECT date, round(SUM((co2g*weight) / 1000),2) AS co2 FROM foodConsumptionActivity " +
                        "INNER JOIN foodItem USING(foodID) WHERE userID = ? AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY date) " +
                        "GROUP BY date ORDER BY date ASC) t";

                co2SpecificsQuery=
                        "SELECT 'Food' AS sourceName, round(SUM(co2g*weight) / 1000,2) AS co2 FROM foodConsumptionActivity " +
                        "INNER JOIN foodItem USING(foodID) WHERE userID = ? AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') " +

                        "UNION " +

                        "SELECT 'Transport' AS sourceName, round(SUM(distanceKm*litresKilometer*gCO2Litre) / 1000,2) AS co2 " +
                        "FROM transportActivity INNER JOIN vehicles ON FKVehicleId=vehicleId AND transportActivity.FKUserId=vehicles.FKUserId " +
                        "INNER JOIN fuelType ON vehicles.FKfuelType=fuelType.fuelId WHERE transportActivity.FKUserId= ? AND date BETWEEN datetime('now','"+timeFrame.value+"') " +
                        "AND datetime('now','localtime')";
                break;
            case "Housing":

                break;
            case "Flights":
                co2OverTimeQuery = "SELECT t.*, SUM(co2) OVER (ORDER BY date ASC) AS acc_co2 FROM " +
                        "(SELECT date, SUM(co2) AS co2 FROM flightActivity WHERE userID= ? " +
                        "AND date between datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY date ORDER BY date ASC) t";
                co2SpecificsQuery = "SELECT date AS sourceName, co2 FROM flightActivity WHERE userID = ? " +
                        "AND date between datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') ORDER BY co2 DESC";
                break;
            case "Transport":
                co2OverTimeQuery = "SELECT t.*, SUM(co2) OVER (ORDER BY date ASC) AS acc_co2 FROM " +
                        "(SELECT date, round(SUM(distanceKm*litresKilometer*gCO2Litre) / 1000,2) AS co2 from transportActivity " +
                        "INNER JOIN vehicles ON FKVehicleId=vehicleId AND transportActivity.FKUserId=vehicles.FKUserId " +
                        "INNER JOIN fuelType ON vehicles.FKfuelType=fuelType.fuelId WHERE transportActivity.FKUserId=? " +
                        "AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY date ORDER BY date ASC) t";
                co2SpecificsQuery = "SELECT activityName AS sourceName, round(SUM((distanceKm*litresKilometer*gCO2Litre) / 1000),2) AS co2 FROM transportActivity " +
                        "INNER JOIN vehicles ON FKVehicleId=vehicleId AND transportActivity.FKUserId=vehicles.FKUserId " +
                        "INNER JOIN fuelType ON vehicles.FKfuelType=fuelId WHERE transportActivity.FKUserId = ? AND date between datetime('now','"+timeFrame.value+"') " +
                        "AND date('now','localtime') GROUP BY activityName ORDER BY co2 DESC";
                break;

            case "Food":
                // This query will return the current user's total co2 emissions from food by date
                co2OverTimeQuery = "SELECT t.*, SUM(co2) OVER (ORDER BY date ASC) AS acc_co2 FROM " +
                        "(SELECT date, round(SUM((co2g*weight) / 1000),2) AS co2 FROM foodConsumptionActivity INNER JOIN foodItem USING(foodID) " +
                        "WHERE userID = ? AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY date ORDER BY date ASC) t";
                co2SpecificsQuery = "SELECT foodName AS sourceName, round(SUM((co2g*weight) / 1000),2) AS co2 FROM foodConsumptionActivity INNER JOIN foodItem USING(foodID) " +
                        "WHERE userID = ? AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY foodID ORDER BY co2 DESC";
                break;
        }
        // Run the selected query and populate the chart with the results
        populateCo2OverTimeChart();
        populatePieChart();
    }

    private void populateCo2OverTimeChart() {
        if (dbHandler.connect()) {
            try {
                PreparedStatement pstmt = dbHandler.getConn().prepareStatement(co2OverTimeQuery);
                // Total statement contains user id varchar in multiple places. NOTE: The queries are assumed to only contain user id varchars
                for (int i = 1; i <= pstmt.getParameterMetaData().getParameterCount(); ++i) {
                    pstmt.setInt(i, Main.getCurrentUserId());
                }
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    try {
                        XYChart.Data<Date, Double> data = new XYChart.Data<>(sdf.parse(rs.getString("date")), rs.getDouble("acc_co2"));
                        co2OverTimeList.add(data);
                    } catch(ParseException e) {
                        e.printStackTrace();
                    }
                }
                co2OverTimeChart.getData().get(0).getData().addAll(co2OverTimeList);
                // Set color
                co2OverTimeChart.getData().get(0).getNode().lookup(".chart-series-line").setStyle(String.format("-fx-stroke: %s", CHART_COLOR));

                // Add tooltips (must be done after the data is added to the chart)
                for (XYChart.Data<Date,Double> d : co2OverTimeChart.getData().get(0).getData()) {
                    d.getNode().lookup(".chart-line-symbol").setStyle(String.format("-fx-background-color: %s, whitesmoke;",CHART_COLOR));
                    Tooltip toolTip = new Tooltip(String.format("%s\nCO2: %.2fkg",sdf.format(d.XValueProperty().get()),d.YValueProperty().get()));
                    Tooltip.install(d.getNode(),toolTip);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void populatePieChart () {
        List<PieChart.Data> slices = new ArrayList<>();
        if (dbHandler.connect()) {
            try {
                PreparedStatement pstmt = dbHandler.getConn().prepareStatement(co2SpecificsQuery);
                // Total statement contains user id varchar in multiple places. NOTE: The queries are assumed to only contain user id varchars
                for (int i = 1; i <= pstmt.getParameterMetaData().getParameterCount(); ++i) {
                    pstmt.setInt(i, Main.getCurrentUserId());
                }
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    slices.add(new PieChart.Data(rs.getString("sourceName"),rs.getDouble("co2")));
                }
                // Need to get the total value so that percentages can be calculated
                double total = slices.stream().mapToDouble(PieChart.Data::getPieValue).sum();
                for (PieChart.Data slice : slices) {
                    co2SourcePieChart.getData().add(new PieChart.Data(slice.getName(),slice.getPieValue() / total));
                }
                co2SourcePieChart.getData().forEach(data -> {
                    String percentage = String.format("%.2f%%", data.getPieValue() * 100);
                    Tooltip toolTip = new Tooltip(percentage);
                    Tooltip.install(data.getNode(), toolTip);
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Show the maximum co2 per capita goal
    @FXML
    private void toggleGoalView(ActionEvent e) {
        if (!co2OverTimeChart.getData().isEmpty()) {
            if (!showGoalCheckBox.isSelected()) {
                co2OverTimeChart.getData().remove(1); // Second series (index 1) is always the "goal" series
            }
            else {
                XYChart.Data<Date, Double> horizontalMarker1 = new XYChart.Data<>(co2OverTimeChart.getData().get(0).getData().get(0).getXValue(), timeFrame.co2Goal);
                XYChart.Data<Date, Double> horizontalMarker2 = new XYChart.Data<>(co2OverTimeChart.getData().get(0).getData().get(co2OverTimeList.size() - 1).getXValue(), timeFrame.co2Goal);
                co2OverTimeChart.getData().add(new XYChart.Series<>(FXCollections.observableArrayList(horizontalMarker1, horizontalMarker2)));
            }
        }

    }

    private void clearCharts() {
        co2SourcePieChart.getData().clear();
        co2OverTimeChart.getData().clear();
        co2OverTimeList.clear();
        showGoalCheckBox.setVisible(false);
    }
}
