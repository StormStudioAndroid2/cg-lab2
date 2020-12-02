package application.figures;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector;

public class CoordsSystemFigure extends Figure {
	@Override
	public void draw(Canvas canvas) {
		final double AXIS_SIZE = 250.0;
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());

		Vector axisCenter = new Vector(0.0, 0.0, 0.0, 1.0);
		Vector axisPointX = new Vector(AXIS_SIZE, 0.0, 0.0, 1.0);
		Vector axisPointY = new Vector(0.0, AXIS_SIZE, 0.0, 1.0);
		Vector axisPointZ = new Vector(0.0, 0.0, AXIS_SIZE, 1.0);
		Vector negAxisPointX = new Vector(-AXIS_SIZE, 0.0, 0.0, 1.0);
		Vector negAxisPointY = new Vector(0.0, -AXIS_SIZE, 0.0, 1.0);
		Vector negAxisPointZ = new Vector(0.0, 0.0, -AXIS_SIZE, 1.0);
		axisCenter = mat.transform(axisCenter).perspectiveDivide();
		axisPointX = mat.transform(axisPointX).perspectiveDivide();
		axisPointY = mat.transform(axisPointY).perspectiveDivide();
		axisPointZ = mat.transform(axisPointZ).perspectiveDivide();
		negAxisPointX = mat.transform(negAxisPointX).perspectiveDivide();
		negAxisPointY = mat.transform(negAxisPointY).perspectiveDivide();
		negAxisPointZ = mat.transform(negAxisPointZ).perspectiveDivide();
		gc.setStroke(Color.RED);
		gc.strokeLine(axisCenter.getX(), axisCenter.getY(), axisPointX.getX(), axisPointX.getY());
		gc.strokeLine(axisCenter.getX(), axisCenter.getY(), negAxisPointX.getX(), negAxisPointX.getY());

		gc.setStroke(Color.GREEN);
		gc.strokeLine(axisCenter.getX(), axisCenter.getY(), axisPointY.getX(), axisPointY.getY());
		gc.strokeLine(axisCenter.getX(), axisCenter.getY(), negAxisPointY.getX(), negAxisPointY.getY());

		gc.setStroke(Color.BLUE);
		gc.strokeLine(axisCenter.getX(), axisCenter.getY(), axisPointZ.getX(), axisPointZ.getY());
		gc.strokeLine(axisCenter.getX(), axisCenter.getY(), negAxisPointZ.getX(), negAxisPointZ.getY());
		gc.fillText("X",axisPointX.getX(),axisPointX.getY());
		gc.fillText("Y",axisPointY.getX(),axisPointY.getY());
		gc.fillText("Z",axisPointZ.getX(),axisPointZ.getY());


	}
}
