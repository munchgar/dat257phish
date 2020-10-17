package org.phish.controllers;

import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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

    private final ObservableList<XYChart.Data<Date, Number>> co2OverTimeList = FXCollections.observableArrayList();

    @FXML
    private LineChart<Date,Number> co2OverTimeChart;

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
                co2OverTimeQuery = "SELECT date, round(SUM(co2),2) AS co2 FROM (SELECT date, round(SUM(distanceKm*litresKilometer*gCO2Litre) / 1000,2) AS co2 " +
                        "FROM transportActivity INNER JOIN vehicles ON FKVehicleId=vehicleId AND transportActivity.FKUserId=vehicles.FKUserId " +
                        "INNER JOIN fuelType ON vehicles.FKfuelType=fuelType.fuelId WHERE transportActivity.FKUserId= ? AND date BETWEEN datetime('now','"+timeFrame.value+"') " +
                        "AND datetime('now','localtime') GROUP BY date UNION SELECT date, round(SUM((co2g*weight) / 1000),2) AS co2 FROM foodConsumptionActivity " +
                        "INNER JOIN foodItem USING(foodID) WHERE userID = ? AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY date) " +
                        "GROUP BY date ORDER BY date ASC;";
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
                populatePieChart(co2SpecificsQuery);
                break;

            case "Food":
                // This query will return the current user's total co2 emissions from food by date
                co2OverTimeQuery = "SELECT date, round(SUM((co2g*weight) / 1000),2) AS co2 FROM foodConsumptionActivity INNER JOIN foodItem USING(foodID) " +
                        "WHERE userID = ? AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY date ORDER BY date ASC";
                co2SpecificsQuery = "SELECT foodName AS sourceName, round(SUM((co2g*weight) / 1000),2) AS co2 FROM foodConsumptionActivity INNER JOIN foodItem USING(foodID) " +
                        "WHERE userID = 1 AND date BETWEEN datetime('now','"+timeFrame.value+"') AND datetime('now','localtime') GROUP BY foodID ORDER BY co2 DESC";
                populatePieChart(co2SpecificsQuery);
                break;
        }
        // Run the selected query and populate the chart with the results
        populateCo2OverTimeChart(co2OverTimeQuery);
    }

    private void populateCo2OverTimeChart(String query) {
        if (dbHandler.connect()) {
            try {
                PreparedStatement pstmt = dbHandler.getConn().prepareStatement(query);
                // Total statement contains user id varchar in multiple places. NOTE: The queries are assumed to only contain user id varchars
                for (int i = 1; i <= pstmt.getParameterMetaData().getParameterCount(); ++i) {
                    pstmt.setInt(i, Main.getCurrentUserId());
                }
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    try {
                        XYChart.Data<Date, Number> data = new XYChart.Data<>(sdf.parse(rs.getString("date")), rs.getDouble("co2"));
                        data.setNode(createDataNode(data.YValueProperty()));
                        co2OverTimeList.add(data);
                    } catch(ParseException e) {
                        System.err.println(e.getMessage());
                    }
                }
                co2OverTimeChart.getData().get(0).getData().addAll(co2OverTimeList);
                System.out.println(co2OverTimeList);
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    private void populatePieChart (String query) {
        List<PieChart.Data> slices = new ArrayList<>();
        if (dbHandler.connect()) {
            try {
                PreparedStatement pstmt = dbHandler.getConn().prepareStatement(query);
                // Total statement contains user id varchar in multiple places. NOTE: The queries are assumed to only contain user id varchars
                for (int i = 1; i <= pstmt.getParameterMetaData().getParameterCount(); ++i) {
                    pstmt.setInt(i, Main.getCurrentUserId());
                }
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    slices.add(new PieChart.Data(rs.getString("sourceName"),rs.getDouble("co2")));
                }
                double total = slices.stream().mapToDouble((PieChart.Data e) -> e.getPieValue()).sum();
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
        pane.getChildren().add(label);

        label.translateYProperty().bind(label.heightProperty().divide(-1.5));
        // label.translateXProperty().bind(label.widthProperty().divide(3));

        return pane;
    }

    private void clearCharts() {
        co2SourcePieChart.getData().clear();
        co2OverTimeChart.getData().clear();
        co2OverTimeList.clear();
    }
}
