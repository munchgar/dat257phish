package org.phish.controllers;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.phish.Main;

public class StatisticsPageController {

	@FXML
	private ListView<String> categoriesList;

	@FXML
	private TabPane tabs;

	// TODO: Get this data from DB controller (possibly using a Category interface instead of just strings?)
	private final ObservableList<String> categories = FXCollections.observableArrayList("Total","Housing", "Flights","Transport","Food");

	private ChartViewController chartController;

	private ChartViewController.TimeFrame timeFrame;

	// Initialize tabs and lists by clearing tab selection, adding fetched categories, selecting default values and setting up event listeners
	@FXML
	public void initialize() {
		timeFrame = ChartViewController.TimeFrame.LAST_WEEK;

		tabs.getSelectionModel().clearSelection();
		tabs.getSelectionModel().selectedItemProperty().addListener((changed, oldTab, newTab) -> tabSwitchEvent(newTab));

		categoriesList.setItems(categories);
		categoriesList.getSelectionModel().select(0);

		tabs.getSelectionModel().select(0);

		categoriesList.getSelectionModel().selectedItemProperty().addListener((changed, oldVal, newVal) -> categorySwitchEvent(newVal));
	}

	private void categorySwitchEvent(String newCategory) {
		chartController.populate(timeFrame, newCategory);
	}

	// Triggered when user switches tab. Loads a new ChartView instance and calls the controller's
	// populate method with the appropriate timeframe and category as parameters.
	public void tabSwitchEvent(Tab selectedTab) {
		if (categoriesList.getItems().isEmpty() || selectedTab == null) {
			return;
		}
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("ChartView.fxml"));

		timeFrame = switch (selectedTab.getId()) {
			case "lastWeekTab" -> ChartViewController.TimeFrame.LAST_WEEK;
			case "lastMonthTab" -> ChartViewController.TimeFrame.LAST_MONTH;
			default -> ChartViewController.TimeFrame.LAST_YEAR;
		};
		try {
			selectedTab.setContent(loader.load());
			chartController = loader.getController();
			chartController.populate(timeFrame, categoriesList.getSelectionModel().getSelectedItem());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
