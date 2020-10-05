package org.phish.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.event.Event;
import org.phish.Main;

public class StatisticsPageController {


	public void tabSwitchEvent(Event event) {
		Tab selectedTab = (Tab) event.getTarget();
		if (selectedTab.isSelected()) {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ChartView.fxml"));

			ChartViewController.TimeFrame timeFrame = switch (selectedTab.getId()) {
				case "lastWeekTab" -> ChartViewController.TimeFrame.LAST_WEEK;
				case "lastMonthTab" -> ChartViewController.TimeFrame.LAST_MONTH;
				default -> ChartViewController.TimeFrame.LAST_YEAR;
			};
			try {
				selectedTab.setContent(loader.load());
				ChartViewController chartController = loader.getController();
				chartController.populate(timeFrame);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
