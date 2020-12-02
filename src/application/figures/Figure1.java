package application.figures;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import math.Vector;

import java.util.ArrayList;

public class Figure1 extends Figure {
	public Figure1(Slider[] params) {
		super();

		this.params = params;
	}

	@Override
	public void draw(Canvas canvas) {
		Slider paramA = params[0];

		GraphicsContext gc = canvas.getGraphicsContext2D();

		ArrayList<ArrayList<Vector>> polygons = new ArrayList<>();

		gc.setStroke(Color.BLACK);

		Vector topRadiusPoint = new Vector(0.0, 0.0, 0-paramA.getValue()/2, 1.0);

		for (int i = 0; i<12; ++i) {
			polygons.add(new ArrayList<Vector>());
		}

		double angle =2*Math.PI*0/10;
		double x1 = topRadiusPoint.getX()+paramA.getValue()*Math.cos(angle);
		double y1 = topRadiusPoint.getY()+paramA.getValue()*Math.sin(angle);
		ArrayList<Vector> topVector = new ArrayList<>();
		ArrayList<Vector> bottomVector = new ArrayList<>();
		double cc;
		topVector.add(new Vector(x1,y1,0-paramA.getValue()/2,1));
		bottomVector.add(new Vector(x1,y1,-paramA.getValue(),1));
		topVector.set(0, mat.transform(topVector.get(0)).perspectiveDivide());
		bottomVector.set(0, mat.transform(bottomVector.get(0)).perspectiveDivide());
		polygons.get(0).add(topVector.get(0));
		polygons.get(1).add(bottomVector.get(0));

		for (int i = 2; i <=10; ++i) {
			 angle =2*Math.PI*(i-1)/10;
			 x1 = topRadiusPoint.getX()+paramA.getValue()*Math.cos(angle);
			 y1 = topRadiusPoint.getY()+paramA.getValue()*Math.sin(angle);
			 topVector.add(new Vector(x1,y1,-paramA.getValue()/2,1));
			 bottomVector.add(new Vector(x1,y1,-paramA.getValue(),1));
			 topVector.set(i-1, mat.transform(topVector.get(i-1)).perspectiveDivide());
			 bottomVector.set(i-1, mat.transform(bottomVector.get(i-1)).perspectiveDivide());
			 polygons.get(0).add(topVector.get(i-1));
			 polygons.get(1).add(bottomVector.get(i-1));
			 polygons.get(i).add(topVector.get(i-2));
			 polygons.get(i).add(topVector.get(i-1));
			 polygons.get(i).add(bottomVector.get(i-1));
			 polygons.get(i).add(bottomVector.get(i-2));

		}
		polygons.get(11).add(topVector.get(9));
		polygons.get(11).add(topVector.get(0));
		polygons.get(11).add(bottomVector.get(0));
		polygons.get(11).add(bottomVector.get(9));
		for (int i = 1; i <12; ++i){
			if (isVisiblePolygon(polygons.get(i),-paramA.getValue())) {
				drawPolygon(gc, polygons.get(i), polygons, -paramA.getValue());
			}
		}

		drawBack(polygons,-paramA.getValue(),gc);

	}


	private void drawPolygon(GraphicsContext gc, ArrayList<Vector> polygon, ArrayList<ArrayList<Vector>> polygons, double z) {
		for (int i = 0; i < polygon.size()-1; ++i) {
			drawLine(polygon.get(i),polygon.get(i+1),gc,polygons,z);
		}
		drawLine(polygon.get(0),polygon.get(polygon.size()-1),gc,polygons,z);

	}
	private void drawLine(Vector v1,Vector v2,GraphicsContext gc, ArrayList<ArrayList<Vector>> polygons,double z) {
		double lastX = v1.getX();
		double lastY = v1.getY();
		double lastZ = v1.getZ();

		for (int i = 1; i <= 1000-1; ++i) {
			double newX = (v1.getX()+(i/(1000d-i))*v2.getX())/((i/(1000d-i))+1);
			double newY = (v1.getY()+(i/(1000d-i))*v2.getY())/((i/(1000d-i))+1);
			double newZ = (v1.getZ()+(i/(1000d-i))*v2.getZ())/((i/(1000d-i))+1);
			Vector v3 = new Vector(lastX,lastY,lastZ,1);
			Vector v4 = new Vector(newX,newY,newZ,1);
			gc.strokeLine(v3.getX(),v3.getY(),v4.getX(),v4.getY());
			lastX = newX;
			lastY = newY;
			lastZ = newZ;
		}
	}
	private void drawBack(ArrayList<ArrayList<Vector>> polygons,double z, GraphicsContext gc) {
		for (int j = 0; j < polygons.get(1).size(); ++j) {
		Vector v1 = polygons.get(0).get(j);
			Vector v2 = polygons.get(0).get((j+1)%polygons.get(0).size());
			double lastX = v1.getX();
			double lastY = v1.getY();
			double lastZ = v1.getZ();
			for (int i = 1; i <= 1000-1; ++i) {
				double newX = (v1.getX() + (i / (1000d - i)) * v2.getX()) / ((i / (1000d - i)) + 1);
				double newY = (v1.getY() + (i / (1000d - i)) * v2.getY()) / ((i / (1000d - i)) + 1);
				double newZ = (v1.getZ() + (i / (1000d - i)) * v2.getZ()) / ((i / (1000d - i)) + 1);
				Vector v3 = new Vector(lastX, lastY, lastZ, 1);
				Vector v4 = new Vector(newX, newY, newZ, 1);
				lastX = newX;
				lastY = newY;
				lastZ = newZ;
				boolean flag = true;
				for (int k = 1; k < 12; ++k) {
					if (isVisiblePolygon(polygons.get(k),z) && pnpoly(polygons.get(k).size(),polygons.get(k),v4.getX(),v4.getY())) {
						flag = false;
					}
				}
				if (flag) {
					gc.strokeLine(v3.getX(), v3.getY(), v4.getX(), v4.getY());
				}
			}
		}
	}


	private boolean isVisiblePolygon( ArrayList<Vector> polygon,double z1) {
		double[] x = new double[]{polygon.get(0).getX(),polygon.get(1).getX(),polygon.get(2).getX() };
		double[] y = new double[]{polygon.get(0).getY(),polygon.get(1).getY(),polygon.get(2).getY() };
		double[] z = new double[]{polygon.get(0).getZ(),polygon.get(1).getZ(),polygon.get(2).getZ() };

		double A = y[0]*(z[1] - z[2]) + y[1]*(z[2] - z[0]) + y[2]*(z[0] - z[1]);
		double B = z[0]*(x[1] - x[2]) + z[1]*(x[2] - x[0]) + z[2]*(x[0] - x[1]);
		double C = x[0]*(y[1] - y[2]) + x[1]*(y[2] - y[0]) + x[2]*(y[0] - y[1]);
		double D = x[0]*(y[1]*z[2] - y[2]*z[1]) + x[1]*(y[2]*z[0] - y[0]*z[2]) + x[2]*(y[0]*z[1] - y[1]*z[0]);
		D = -D;
		Vector v1 = new Vector(0.0, 0.0, z1, 1.0);
		if (A*v1.getX()+v1.getY()*B+v1.getZ()*C+D>0) {
			return true;
		}
		return false;
	}

	private boolean pnpoly(int npol, ArrayList<Vector> p, double x, double y)
	{
		boolean c = false;
		for (int i = 0, j = npol - 1; i < npol; j = i++)
		{
			if ((((p.get(i).getY() <= y) && (y < p.get(j).getY())) || ((p.get(j).getY() <= y) && (y < p.get(i).getY()))) &&
					(((p.get(j).getY() - p.get(i).getY()) != 0) && (x > ((p.get(j).getX() - p.get(i).getX()) * (y - p.get(i).getY()) / (p.get(j).getY() - p.get(i).getY()) + p.get(i).getX()))))
				c = !c;
		}
		return c;
	}


	private Slider[] params;
}
