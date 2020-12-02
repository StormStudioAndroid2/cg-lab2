package controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import application.CustomCanvas;
import handlers.HandlerFigures;

public class MainController {
	@FXML
	private void initialize() {
		CustomCanvas canvas = new CustomCanvas(canvasHolder.getPrefWidth(), canvasHolder.getPrefHeight());

		AnchorPane.setBottomAnchor(canvas, 0.0);
		AnchorPane.setTopAnchor(canvas, 0.0);
		AnchorPane.setLeftAnchor(canvas, 0.0);
		AnchorPane.setRightAnchor(canvas, 0.0);

		Slider[] paramsFig1 = new Slider[] {param1A};
		HandlerFigures handlerFigures = new HandlerFigures(canvas, tabPane, paramsFig1);
		canvas.setUpdater(handlerFigures);
		for (int i = 0; i < paramsFig1.length; ++i) {
			paramsFig1[i].valueProperty().addListener(handlerFigures);
		}

		canvas.setOnMousePressed(handlerFigures);
		canvas.setOnMouseDragged(handlerFigures);
		canvasHolder.getChildren().add(canvas);
	}

	@FXML
	private AnchorPane canvasHolder;
	@FXML
	private TabPane tabPane;
	@FXML
	private Slider param1A;

}
