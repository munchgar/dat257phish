package org.phish.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public enum TimeFrame {
        LAST_WEEK("-6 days"),
        LAST_MONTH("-30 days"),
        LAST_YEAR("-364 days");

        public final String value;

        TimeFrame(String value) {
            this.value = value;
        }
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final ObservableList<XYChart.Data<Date, Double>> co2OverTimeList = FXCollections.observableArrayList();

    @FXML
    private LineChart<Date,Double> co2OverTimeChart;

    @FXML
    private PieChart co2SourcePieChart;

    private final DBHandler dbHandler = DBHandler.getInstance();

    private String co2OverTimeQuery, co2SpecificsQuery;

    // Populates all the charts with the timeFrame and category provided.
    public void populate (TimeFrame timeFrame, String category) {
        clearCharts();
        co2OverTimeChart.getData().add(new XYChart.Series<>());

        switch (category) {
            case "Total":
                // TODO: Add more categories to total (and maybe implement this in a way that doesn't result in a gigantic union of selects)
                // Sums up all emissions by date in the given timeframe
                co2OverTimeQuery =
                        "SELECT date, round(SUM(co2),2) AS co2 FROM (SELECT date, round(SUM(distanceKm*litresKilometer*gCO2Litre) / 1000,2) AS co2 " +
                        "FROM transportActivity INNER JOIN vehicles ON FKVehicleId=vehicleId AND transportActivity.FKUserId=vehicles.FKUserId " +
                        "INNER JOIN fuelType ON vehicles.FKfuelType=fuelType.fuelId WHERE transportActivity.FKUserId= ? AND date BETWEEN datetime('now','"+timeFrame.value+"') " +
                        "AND datetime('now','localtime') GROUP BY date " +

                        "UNION " +

                        "SELECT date, round(SUM((co2g*weight) / 1000),2) AS co2 FROM foodConsumptionActivity " +
                        "INNER JOIN foodItem USING(foodID) WHERE userID = ? AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY date) " +
                        "GROUP BY date ORDER BY date ASC";

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
                // TODO: Implement
                break;
            case "Transport":
                co2OverTimeQuery = "SELECT date, round(SUM(distanceKm*litresKilometer*gCO2Litre) / 1000,2) AS co2 from transportActivity " +
                        "INNER JOIN vehicles ON FKVehicleId=vehicleId AND transportActivity.FKUserId=vehicles.FKUserId " +
                        "INNER JOIN fuelType ON vehicles.FKfuelType=fuelType.fuelId WHERE transportActivity.FKUserId=? " +
                        "AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY date ORDER BY date ASC";
                co2SpecificsQuery = "SELECT activityName AS sourceName, round(SUM((distanceKm*litresKilometer*gCO2Litre) / 1000),2) AS co2 FROM transportActivity " +
                        "INNER JOIN vehicles ON FKVehicleId=vehicleId AND transportActivity.FKUserId=vehicles.FKUserId " +
                        "INNER JOIN fuelType ON vehicles.FKfuelType=fuelId WHERE transportActivity.FKUserId = ? AND date between datetime('now','"+timeFrame.value+"') " +
                        "AND date('now','localtime') GROUP BY activityName ORDER BY co2 DESC";
                break;

            case "Food":
                // This query will return the current user's total co2 emissions from food by date
                co2OverTimeQuery = "SELECT date, round(SUM((co2g*weight) / 1000),2) AS co2 FROM foodConsumptionActivity INNER JOIN foodItem USING(foodID) " +
                        "WHERE userID = ? AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY date ORDER BY date ASC";
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
                        XYChart.Data<Date, Double> data = new XYChart.Data<>(sdf.parse(rs.getString("date")), rs.getDouble("co2"));
                        co2OverTimeList.add(data);
                    } catch(ParseException e) {
                        e.printStackTrace();
                    }
                }
                co2OverTimeChart.getData().get(0).getData().addAll(co2OverTimeList);

                // Add tooltips (must be done after the data is added to the chart)
                for (XYChart.Data<Date,Double> d : co2OverTimeChart.getData().get(0).getData()) {
                    Tooltip toolTip = new Tooltip(String.format("%s\nCO2: %.2fkg",sdf.format(d.XValueProperty().get()),d.YValueProperty().get()));
                    Tooltip.install(d.getNode(),toolTip);
                }
                System.out.println(co2OverTimeList);
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
                System.out.println(total);
                for (PieChart.Data slice : slices) {
                    co2SourcePieChart.getData().add(new PieChart.Data(slice.getName(),slice.getPieValue() / total));
                }
                System.out.println(co2SourcePieChart.getData());
                co2SourcePieChart.getData().forEach(data -> {
                    String percentage = String.format("%.2f%%", data.getPieValue() * 100);
                    Tooltip toolTip = new Tooltip(percentage);
                    System.out.println(data.getNode());
                    Tooltip.install(data.getNode(), toolTip);
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearCharts() {
        co2SourcePieChart.getData().clear();
        co2OverTimeChart.getData().clear();
        co2OverTimeList.clear();
    }
}
