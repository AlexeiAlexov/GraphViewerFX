package application.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import application.controller.MatrixOperator;
import application.model.Matrix;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController {

	public static MainController instance;
	
	public ArrayList<Matrix> matrixs;
	
	@FXML
	public ListView<String> matrixListView;
	
	
	// MATRIX CONTROL BUTTONS
	@FXML
	private Button addMatrixButton;
	@FXML
	private Button loadMatrixButton;
	@FXML
	private Button deleteMatrixButton;
	
	
	// MATRIX OPERATIONS BUTTONS
	// доповненн€
	@FXML
	private Button additionButton;
	// перетин
	@FXML
	private Button crossingButton;
	// обЇднанн€
	@FXML
	private Button associationButton;
	// р≥зниц€
	@FXML
	private Button differenceButton;
	// симетрицна р≥зниц€
	@FXML
	private Button simetricDifferenceButton;
	// оберненн€
	@FXML
	private Button inversionButton;
	// добуток
	@FXML
	private Button productButton;
	// звуженн€
	@FXML
	private Button partButton;
	
	// temp var
	private int index;
	
	@FXML
	private void initialize(){
		
		instance = this;
		
		matrixs = new ArrayList<Matrix>();
		
		
	}

	@FXML
	public void handleListViewClick(MouseEvent e){
		
		if (e.getClickCount() == 2){

			index = matrixListView.getSelectionModel().getSelectedIndex();

			if (index == -1)return;
			
			openMatrix(index);
			
		}
	}
	
	@FXML
	private void handleDelete(){
		index = matrixListView.getSelectionModel().getSelectedIndex();
		
		if (index == -1)return;
		
		matrixs.remove(index);
		matrixListView.getItems().remove(index);
		
	}
	
	@FXML
	private void handleLoadMatrix() throws FileNotFoundException{
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("MATRIX FILE", "*.mtrx"));
		fc.setInitialDirectory(new File(System.getProperty("user.dir")));
		File f = fc.showOpenDialog(Main.instance.stage);
		
		if (f != null){
			loadMatrixFromFile(f);
		}
	}
	
	private void loadMatrixFromFile(File f) throws FileNotFoundException{
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			int size = Integer.parseInt(br.readLine());
			Matrix m = new Matrix(f.getName().split("[.]")[0], size);
			String[] dataParsed;
			for(int x = 0; x < size; x++){
				dataParsed = br.readLine().split(" ");
				for(int y = 0; y < size; y++){
					m.set(x, y, Integer.parseInt(dataParsed[y]));
				}
			}
			
			
			addMatrix(m);
			
			br.close();
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void openMatrix(int index){
        Main.instance.openMatrixViewWindow(index);
	}
	
	@FXML
	private void handleCreateMatrix(){
		Main.instance.openCreateMatrixWindow();
	}
	
	public void addMatrix(Matrix m){
		matrixListView.getItems().add(m.getName());
		m.refreshProperties();
		matrixs.add(m);
	}
	
	@FXML
	private void handleAddition(){
		Matrix m = MatrixOperator.makeOperation(MatrixOperator.ADDITION, 
				matrixs.get(matrixListView.getSelectionModel().getSelectedIndex()), null);
		
		if (m != null){
			MainController.instance.addMatrix(m);
			MainController.instance.openMatrix(MainController.instance.matrixs.size()-1);
		}
		
	}
	
	@FXML
	private void handleInverse(){
		Matrix m = MatrixOperator.makeOperation(MatrixOperator.INVERSE, 
				matrixs.get(matrixListView.getSelectionModel().getSelectedIndex()), null);
		
		if (m != null){
			MainController.instance.addMatrix(m);
			MainController.instance.openMatrix(MainController.instance.matrixs.size()-1);
		}
		
	}
	
	@FXML
	private void handleBinaryOperation(Event e){
		Main.instance.openBinaryOperationWindow(Integer.parseInt(((Button)e.getSource()).getText().split(" ")[0]));
	}
	
	@FXML
	private void handlePartOperation(Event e){
		Matrix m = matrixs.get(matrixListView.getSelectionModel().getSelectedIndex());
		Main.instance.openPartMartrixWindow(m);
	}
}
