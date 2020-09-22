package org.phish.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;

public class ChartViewController {

    public enum TimeFrame {
        LAST_WEEK,
        LAST_MONTH,
        LAST_YEAR
    }

    @FXML
    private LineChart<String,Number> co2OverTimeChart;

    @FXML
    private PieChart co2SourcePieChart;

    private XYChart.Series co2OverTimeSeries = new XYChart.Series();

    public void initialize() {
        co2OverTimeChart.getData().add(co2OverTimeSeries);
    }

    // Currently a dummy method, should connect to db and append data relevant to specified timeframe and possibly other factors
    private void populateLineChart (TimeFrame timeFrame, XYChart.Series chartSeries) {

        switch (timeFrame) {
            case LAST_WEEK:
                chartSeries.getData().add(new XYChart.Data("Sunday", 20));
                chartSeries.getData().add(new XYChart.Data("Monday", 55));
                chartSeries.getData().add(new XYChart.Data("Tuesday", 12));
                break;
            case LAST_MONTH:
                chartSeries.getData().add(new XYChart.Data("Sep 17", 30));
                chartSeries.getData().add(new XYChart.Data("Sep 18", 32));
                chartSeries.getData().add(new XYChart.Data("Sep 19", 40));
                chartSeries.getData().add(new XYChart.Data("Sep 20", 20));
                chartSeries.getData().add(new XYChart.Data("Sep 21", 55));
                chartSeries.getData().add(new XYChart.Data("Sep 22", 12));
                break;
            default:
                chartSeries.getData().add(new XYChart.Data("Jan", 10));
                chartSeries.getData().add(new XYChart.Data("Feb", 15));
                chartSeries.getData().add(new XYChart.Data("Mar", 25));
                chartSeries.getData().add(new XYChart.Data("Apr", 30));
                chartSeries.getData().add(new XYChart.Data("May", 32));
                chartSeries.getData().add(new XYChart.Data("Jun", 40));
                chartSeries.getData().add(new XYChart.Data("Jul", 20));
                chartSeries.getData().add(new XYChart.Data("Aug", 55));
                chartSeries.getData().add(new XYChart.Data("Sep", 12));
                break;
        }
    }

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

    public void populate (TimeFrame timeFrame) {
        populateLineChart(timeFrame, co2OverTimeSeries);
        populatePieChart(timeFrame, co2SourcePieChart);
    }
}
