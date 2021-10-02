package com.ggl.testing;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HexagonFlower implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new HexagonFlower());
	}

	@Override
	public void run() {
		JFrame frame = new JFrame("Hexagon Flower");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new DrawingPanel(), BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
	
	public class DrawingPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private int drawingWidth, drawingHeight, margin;
		
		public DrawingPanel() {
			this.drawingWidth = 800;
			this.drawingHeight = drawingWidth;
			this.margin = 40;
			
			this.setBackground(Color.WHITE);
			int width = drawingWidth + margin + margin;
			int height = drawingHeight + margin + margin;
			this.setPreferredSize(new Dimension(width, height));
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, 
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			
			double x = drawingWidth / 2 + margin;
			double y = drawingHeight / 2 + margin;
			double innerRadius = Math.min(drawingWidth, drawingHeight) / 5;
			double outerRadius = (innerRadius + innerRadius) * 
					Math.cos(Math.toRadians(30));
			
			Polygon polygon = calculateHexagon(x, y, innerRadius);
			g2d.setStroke(new BasicStroke(5f));
			g2d.setColor(Color.BLACK);
			g2d.draw(polygon);
			
			for (int angle = 30; angle < 360; angle += 60) {
				Point2D centerPoint = calculatePoint(x, y, outerRadius, angle);
				double a = centerPoint.getX();
				double b = centerPoint.getY();
				polygon = calculateHexagon(a, b, innerRadius);
				g2d.draw(polygon);
			}
		}
		
		private Polygon calculateHexagon(double x, double y, double radius) {
			Polygon polygon = new Polygon();
			
			for (int angle = 0; angle < 360; angle += 60) {
				Point2D outerPoint = calculatePoint(x, y, radius, angle);
				int a = (int) Math.round(outerPoint.getX());
				int b = (int) Math.round(outerPoint.getY());
				polygon.addPoint(a, b);
			}
			
			return polygon;
		}
		
		private Point2D calculatePoint(double x, double y, double radius, int angle) {
			double theta = Math.toRadians(angle);
			double a = x + Math.cos(theta) * radius;
			double b = y + Math.sin(theta) * radius;
			return new Point2D.Double(a, b);
		}
		
	}

}
