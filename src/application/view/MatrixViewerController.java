package application.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import application.Main;
import application.model.Matrix;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MatrixViewerController {

	public static MatrixViewerController currentControllerEdit;
	
	public int matrixIndex;
	public Matrix m;
	public Stage stage;
	
	private GraphicsContext gc2d;
	
	private int[] graphX;
	private int[] graphY;
	
	private final Color[] COLORS = {
			Color.BLUE,
			Color.RED,
			Color.GREEN,
			Color.GOLD,
			Color.VIOLET,
			Color.DEEPSKYBLUE
	};
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private Button saveMatrixButton;
	@FXML
	private Button changeMatrixButton;
	
	@FXML
	private Label matrixNameLabel;
	
	@FXML
	private VBox vbox;
	
	@FXML
	private void initialize(){
		
	}

	public void refresh(){
		
		m = MainController.instance.matrixs.get(matrixIndex);
		
		matrixNameLabel.setText(m.getName());
		initGraph();
		redraw();
		showProperties();
	}
	
	public void showProperties(){
		
		vbox.getChildren().clear();
		
		if (m.getProperty(Matrix.SYMETRIC))addProp("Симетричність");
		if (m.getProperty(Matrix.ASYMETRIC))addProp("Асиметричність");
		if (m.getProperty(Matrix.REFLEX))addProp("Рефлексивність");
		if (m.getProperty(Matrix.ANTIREFLEX))addProp("Антирефлексивність");
		if (m.getProperty(Matrix.ANTISYMETRIC))addProp("Антисиметричність");
		if (m.getProperty(Matrix.TRANSITIVE))addProp("Транзитивність");
		if (m.getProperty(Matrix.NEGATIVE_TRANSITIVE))addProp("Негативна транзитивність");
		if (m.getProperty(Matrix.STRONG_TRANSITIVE))addProp("Сильна транзитивність");
		if (m.getProperty(Matrix.CONNECTED))addProp("Зв`язність");
		
	}
	
	private void addProp(String s){
		vbox.getChildren().add(new Label(s));
	}
	
	@FXML
	public void handleSaveMatrix() throws IOException{
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("MATRIX FILE", "*.mtrx"));
		fc.setInitialFileName(m.getName() + ".mtrx");
		fc.setInitialDirectory(new File(System.getProperty("user.dir")));
		File f = fc.showSaveDialog(Main.instance.stage);
		
		if (f != null){
			saveMatrix(f);
		}
	}
	
	private void saveMatrix(File f) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("" + m.getSize());
		bw.newLine();
		String tempString;
		for(int y = 0; y < m.getSize(); y++){
			tempString = "" + m.get(0, y);
			for(int x = 1; x < m.getSize(); x++){
				tempString += " " + m.get(x, y);
			}
			bw.write(tempString);
			bw.newLine();
			bw.flush();
		}
		bw.flush();
		bw.close();
	}
	
	@FXML
	public void handleChangeMatrix(){
		Main.instance.openEditMartrixWindow(m, this);
	}
	
	
	private static final int R = 20;
	private static final int R_SMALL = 5;
	private static final double ANGLE_DIFF = 0.3;
	private static int distance;
	private static int CENTER_X;
	private static int CENTER_Y;
	
	private void initGraph(){
		CENTER_X = (int) (canvas.getWidth() / 2);
		CENTER_Y = (int) (canvas.getHeight() / 2);
		distance = (int) (canvas.getHeight()/4);
		graphX = new int[m.getSize()];
		graphY = new int[m.getSize()];
		for(int i = 0; i < m.getSize(); i++){
			
			graphX[i] = (int)(canvas.getWidth() / 2 - 
					distance * Math.cos(Math.PI * 2 / m.getSize() * i));

			graphY[i] = (int)(canvas.getHeight() / 2 - 
					distance * Math.sin(Math.PI * 2 / m.getSize() * i));
		}
	}
	
	private void redraw(){
		gc2d = canvas.getGraphicsContext2D();
		gc2d.setFill(Color.WHITE);
		gc2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		
		drawPaths(gc2d);
		drawGraphs(gc2d);
		
	}
	
	private void drawPaths(GraphicsContext g){
		g.setStroke(Color.BLACK);
		g.setFill(Color.BLACK);
		g.setLineWidth(2);
		
		double tempPointX;
		double tempPointY;
		
		double tempPointLeftX;
		double tempPointLeftY;
		
		double tempPointRightX;
		double tempPointRightY;
		

		for(int i = 0; i < m.getSize(); i++){
			for(int j = 0; j < m.getSize(); j++){
				if(m.get(i, j) == 1){
					if (i != j){
						
						g.strokeLine(graphX[i], graphY[i], graphX[j], graphY[j]);
						
						float a = (float) 
								Math.acos(Math.abs(graphX[i] - graphX[j]) / getLength(graphX[i], graphY[i], graphX[j], graphY[j]));
					
						tempPointX = graphX[i] + Math.cos(a) * R * ((graphX[i] < graphX[j])? 1 : -1);
						tempPointY = graphY[i] + Math.sin(a) * R * ((graphY[i] < graphY[j])? 1 : -1);
						
						tempPointLeftX = tempPointX + R_SMALL * Math.cos(a - ANGLE_DIFF) * ((graphX[i] <= graphX[j])? 1 : -1);
						tempPointLeftY = tempPointY + R_SMALL * Math.sin(a - ANGLE_DIFF) * ((graphY[i] <= graphY[j])? 1 : -1);
						
						tempPointRightX = tempPointX + R_SMALL * Math.cos(a + ANGLE_DIFF) * ((graphX[i] <= graphX[j])? 1 : -1);
						tempPointRightY = tempPointY + R_SMALL * Math.sin(a + ANGLE_DIFF) * ((graphY[i] <= graphY[j])? 1 : -1);
						
						g.strokeLine(tempPointX, tempPointY, tempPointLeftX, tempPointLeftY);
						g.strokeLine(tempPointX, tempPointY, tempPointRightX, tempPointRightY);
						
					}
					else{
						float a = (float)
								Math.acos(Math.abs(graphX[i] - CENTER_X) / getLength(graphX[i], graphY[i], (float)CENTER_X, (float)CENTER_X));
						
						g.strokeArc(graphX[i] - 30 - Math.cos(a) * 30 * ((graphX[i] < CENTER_X)? 1 : -1), graphY[i] - 30 - Math.sin(a) * 30 * ((graphY[i] < CENTER_Y)? 1 : -1), 60, 60, 0, 360, ArcType.OPEN);
						
					}
				}
			}
		}
	}
	
	private void drawGraphs(GraphicsContext g){
		int colorIndex = 0;
		g.setStroke(Color.WHITE);
		g.setLineWidth(1);
		for(int i = 0; i < m.getSize(); i++){
			g.setFill(COLORS[colorIndex++ % COLORS.length]);
			
			g.fillArc((double)graphX[i] - R, (double)graphY[i] - R, (double) R * 2, (double) R * 2, 0.0, 360.0, ArcType.ROUND);
			
			g.strokeText(i + "", graphX[i] - 3, graphY[i] + 4);
		}
	}
	
	private float getLength(float x1, float y1, float x2, float y2){
		return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); 
	}
	
	public void setMatrix(Matrix m){
		this.m = m;
		MainController.instance.matrixs.set(matrixIndex, m);
		initGraph();
		redraw();
	}

}
