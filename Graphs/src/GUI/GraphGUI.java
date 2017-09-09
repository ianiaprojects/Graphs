package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphGUI extends JPanel {

	//check if cost
	private boolean isCost;
	
	//components: nodes, links
	private int elemNumb;
	private int[][] blackGraph;
	private int[][] redGraph;

	private final int frameScale = 30; //max nodes

	//legend
	private final int legendFontSize = 14; //font size
	private final int xLegend = 10;
	private final int yLegend = 20;

	//items properties
	private final int nodeDiameter = 30;
	private final int nodeFontSize = nodeDiameter/2;

	//orientation properties
	private final int graphRadius  = frameScale * nodeDiameter/2;
	private final int xGraphMargin = 150 + graphRadius;
	private final int yGraphMargin = graphRadius;

	//main frame properties
	private JFrame mainFrame;

	private final int frameWidth  = 250 + graphRadius * 2;
	private final int frameHeight = 100 + graphRadius * 2;

	private String applicationName;
	private String firstLinkName;
	private String secondLinkName;
	
	//explicit constructor	
	public GraphGUI(int[][] blackGraph, int[][] redGraph, boolean isCost, String applicationName) {

		this.elemNumb	= blackGraph.length;
		this.blackGraph = blackGraph;
		this.redGraph	= redGraph;
		this.isCost		= isCost;
		this.applicationName = applicationName;
		
		if (applicationName.contains("Dijkstra")) {
			this.setFirstLinkName("short path");
			this.setSecondLinkName("initial graph");
		} else {
			this.setFirstLinkName("initial graph");
			this.setSecondLinkName("final graph");
		}
			
	}

	public void startGUI() {

		//main frame settings
		mainFrame = new JFrame();
		mainFrame.setSize(frameWidth, frameHeight);
		mainFrame.setLocationRelativeTo(null); //middle of the screen
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle(applicationName);

		this.setLocation(0, 0);	//draw panel location
		mainFrame.getContentPane().add(this); //add this panel to frame
		mainFrame.setVisible(true);

	}

	public void paint(Graphics g) {
		drawLegend(g);

		Point[] point = createNodes();

		drawBlackLinks(g, point);
		drawRedLinks(g, point);
		drawNodes(g, point);

	}

	private Point[] createNodes() {

		//create nodes
		double angle = 0;
		double step  = (2 * Math.PI) / elemNumb;

		Point[] point = new Point[elemNumb];

		for (int i = 0; i < elemNumb; i++) {

			int x = (int) (nodeDiameter/2 + graphRadius * Math.cos(angle));
			int y = (int) (nodeDiameter/2 + graphRadius * Math.sin(angle));

			angle += step;

			//array of nodes
			point[i] = new Point(x, y);

		}

		return point;

	}

	private void drawNodes(Graphics graphics, Point[] point) {

		//drawing the nodes
		for (int i = 0; i < elemNumb; i++) {

			drawItem(graphics, (i + 1) + "", point[i]);

		}

	}

	private void drawBlackLinks(Graphics graphics, Point[] point) {

		for (int i = 0; i < elemNumb; i++)
			for(int j = 0; j < elemNumb; j++)
				if (blackGraph[i][j] != 0 && redGraph[i][j] == 0)
					if (blackGraph[i][j] != 0 && blackGraph[j][i] != 0) {

						int moveCost = 0;
						if (i < j)
							moveCost = 15;

						drawBlackLink(graphics, blackGraph[i][j], point[i], point[j], i, j, moveCost);
					}

	}

	private void drawRedLinks(Graphics graphics, Point[] point) {

		for (int i = 0; i < elemNumb; i++)
			for(int j = 0; j < elemNumb; j++)
				if (redGraph[i][j] != 0)
					if (redGraph[j][i] != 0 || blackGraph[i][j] != 0 || blackGraph[i][j] != 0) {

						int moveCost = 0;
						if (i < j)
							moveCost = 15;

						drawRedLink(graphics, redGraph[i][j], point[i], point[j], i, j, moveCost); 					
					}

	}

	private void drawItem(Graphics graphics, String nodeValue, Point nodePosition) {

		//fill the circle
		graphics.setColor(Color.lightGray);
		graphics.fillOval((int) nodePosition.getX() + xGraphMargin, (int) nodePosition.getY() + yGraphMargin, nodeDiameter, nodeDiameter);

		//set border
		graphics.setColor(Color.darkGray);
		graphics.drawOval((int) nodePosition.getX() + xGraphMargin, (int) nodePosition.getY() + yGraphMargin, nodeDiameter, nodeDiameter);

		//fixing string centering issues
		int xItemFix = nodeDiameter/2 - (nodeDiameter/10);
		int yItemFix = nodeDiameter/2 + 2*(nodeDiameter/10);

		//add number
		if (nodeValue.length() > 1)
			xItemFix = xItemFix/2;

		graphics.setColor(Color.BLACK);
		Font font = new Font("Lato", Font.BOLD, nodeFontSize);
		graphics.setFont(font);
		graphics.drawString(nodeValue, (int) nodePosition.getX() + xItemFix + xGraphMargin, (int) nodePosition.getY() + yItemFix + yGraphMargin) ;

	}

	private void drawBlackLink(Graphics graphics, int nodeValue, Point aNodePosition, Point bNodePosition, int i, int j, int moveCost) {

		graphics.setColor(Color.black);
		graphics.drawLine((int)aNodePosition.getX() + nodeDiameter/2 + xGraphMargin, (int)aNodePosition.getY() + nodeDiameter/2 + yGraphMargin,
				(int)bNodePosition.getX() + nodeDiameter/2 + xGraphMargin, (int)bNodePosition.getY() + nodeDiameter/2 + yGraphMargin);

		if (isCost == true) {
			
		//add number
		//fixing string centering issues
		int xItemFix = nodeDiameter/2 - (nodeDiameter/10);
		int yItemFix = nodeDiameter/2 + 2*(nodeDiameter/10);

		graphics.setColor(Color.black);
		Font font = new Font("Lato", Font.BOLD, nodeFontSize);
		graphics.setFont(font);
		graphics.drawString(nodeValue + "", (int)(aNodePosition.getX() + bNodePosition.getX()) / 2 + xGraphMargin + xItemFix,
				(int)(aNodePosition.getY() + bNodePosition.getY()) / 2 + yGraphMargin + yItemFix + moveCost);

		int moveChar = 16;
		if (nodeValue<10)
			moveChar = 8;

		graphics.setColor(Color.black);
		font = new Font("Lato", Font.PLAIN, nodeFontSize-5);
		graphics.setFont(font);
		graphics.drawString((i+1) + "," + (j+1), (int)(aNodePosition.getX() + bNodePosition.getX()) / 2 + xGraphMargin + xItemFix + moveChar,
				(int)(aNodePosition.getY() + bNodePosition.getY()) / 2 + yGraphMargin + yItemFix + moveCost);
		}

	}

	private void drawRedLink(Graphics graphics, int nodeValue, Point aNodePosition, Point bNodePosition, int i, int j, int moveCost) {

		graphics.setColor(Color.red);
		graphics.drawLine((int)aNodePosition.getX() + nodeDiameter/2 + xGraphMargin, (int)aNodePosition.getY() + nodeDiameter/2 + yGraphMargin,
				(int)bNodePosition.getX() + nodeDiameter/2 + xGraphMargin, (int)bNodePosition.getY() + nodeDiameter/2 + yGraphMargin);

		if (isCost == true) {
		
		//add number
		//fixing string centering issues
		int xItemFix = nodeDiameter/2 - (nodeDiameter/10);
		int yItemFix = nodeDiameter/2 + 2*(nodeDiameter/10);

		graphics.setColor(Color.red);
		Font font = new Font("Lato", Font.BOLD, nodeFontSize);
		graphics.setFont(font);
		graphics.drawString(nodeValue + "", (int)(aNodePosition.getX() + bNodePosition.getX()) / 2 + xGraphMargin + xItemFix,
				(int)(aNodePosition.getY() + bNodePosition.getY()) / 2 + yGraphMargin + yItemFix + moveCost);

		int moveChar = 16;
		if (nodeValue<10)
			moveChar = 8;

		graphics.setColor(Color.red);
		font = new Font("Lato", Font.PLAIN, nodeFontSize-5);
		graphics.setFont(font);
		graphics.drawString((i+1) + "," + (j+1), (int)(aNodePosition.getX() + bNodePosition.getX()) / 2 + xGraphMargin + xItemFix + moveChar,
				(int)(aNodePosition.getY() + bNodePosition.getY()) / 2 + yGraphMargin + yItemFix + moveCost);
		}
	}

	private void drawLegend(Graphics graphics) {

		//legend title
		graphics.setColor(Color.black);
		Font font = new Font("Comic MS", Font.PLAIN, legendFontSize);
		graphics.setFont(font);
		graphics.drawString("Graph Legend", xLegend, yLegend);

		//node
		graphics.setColor(Color.black);
		graphics.drawString("Node", xLegend, yLegend + nodeDiameter);

		graphics.setColor(Color.lightGray);
		graphics.fillOval(xLegend + legendFontSize * 3, yLegend + nodeDiameter/2, nodeDiameter/2, nodeDiameter/2);

		graphics.setColor(Color.darkGray);
		graphics.drawOval(xLegend + legendFontSize * 3, yLegend + nodeDiameter/2, nodeDiameter/2, nodeDiameter/2);

		//short path
		graphics.setColor(Color.black);
		graphics.drawString(this.firstLinkName, xLegend, yLegend + nodeDiameter/2 + nodeDiameter);

		graphics.setColor(Color.red);
		graphics.drawLine(xLegend + legendFontSize * 5, yLegend + nodeDiameter + nodeDiameter/2,
				xLegend + nodeDiameter + legendFontSize * 6, yLegend + nodeDiameter + nodeDiameter/2);

		//simple link
		graphics.setColor(Color.black);
		graphics.drawString(this.secondLinkName, xLegend, yLegend + nodeDiameter + nodeDiameter);

		graphics.setColor(Color.black);
		graphics.drawLine(xLegend + legendFontSize * 5, yLegend + nodeDiameter * 2,
				xLegend + nodeDiameter + legendFontSize * 6, yLegend + nodeDiameter * 2);
	}

	public String getFirstLinkName() {
		return firstLinkName;
	}

	public void setFirstLinkName(String firstLinkName) {
		this.firstLinkName = firstLinkName;
	}

	public String getSecondLinkName() {
		return secondLinkName;
	}

	public void setSecondLinkName(String secondLinkName) {
		this.secondLinkName = secondLinkName;
	}

}
