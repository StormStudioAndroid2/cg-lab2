package application;

import handlers.Updater;
import javafx.scene.canvas.Canvas;

public class CustomCanvas extends Canvas {
	public CustomCanvas(double width, double height) {
		super(width, height);
	}
	private Updater updater;
	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public void resize(double width, double height) {
		setWidth(width);
		setHeight(height);
		updater.update();
	}
	public void setUpdater(Updater updater) {
		this.updater = updater;
	}
}
