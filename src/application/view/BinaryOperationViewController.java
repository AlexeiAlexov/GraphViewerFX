package application.view;

import application.controller.MatrixOperator;
import application.model.Matrix;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static application.controller.MatrixOperator.*;

public class BinaryOperationViewController {

	public Stage stage;
	
	private int id;
	
	@FXML
	public ChoiceBox<String> leftOperandChoiceBox;
	@FXML
	public ChoiceBox<String> rightOperandChoiceBox;
	@FXML
	public Label operationLabel;
	
	@FXML
	public void initialize(){
		leftOperandChoiceBox.getItems().addAll(MainController.instance.matrixListView.getItems());
		rightOperandChoiceBox.getItems().addAll(MainController.instance.matrixListView.getItems());
		
		
		
	}
	
	public void setId(int id){
		this.id = id;
		String title;
		String symbol;
		switch(id){
		case ASSOCIATION:
			title = "ASSOCIATION";
			symbol = "ASS";
			break;
		case CROSSING:
			title = "CROSSING";
			symbol = "CROSS";
			break;
		case DIFFERENCE:
			title = "DIFFERENCE";
			symbol = "\\";
			break;
		case PRODUCT:
			title = "PRODUCT";
			symbol = "PROD";
			break;
		case SIMETRIC_DIFFERENCE:
			title = "SIMETRIC DIFFERENCE";
			symbol = "SIM \\";
			break;
		default:
			title = null;
			symbol = null;
		}
		System.out.println(title + " " + symbol);
		stage.setTitle(title);
		operationLabel.setText(symbol);
	}
	
	@FXML
	public void handleOk(){
		System.out.println("1");
		int leftIndex = leftOperandChoiceBox.getSelectionModel().getSelectedIndex();
		int rightIndex = rightOperandChoiceBox.getSelectionModel().getSelectedIndex();
		
		if (leftIndex == -1 || rightIndex == -1){stage.close();return;}
		
		Matrix m = MatrixOperator.makeOperation(id, MainController.instance.matrixs.get(leftIndex), 
				MainController.instance.matrixs.get(rightIndex));
		System.out.println(m);
		if (m != null){
			MainController.instance.addMatrix(m);
			MainController.instance.openMatrix(MainController.instance.matrixs.size()-1);
			stage.close();
		}
		
	}
	
	@FXML
	public void handleCancel(){
		stage.close();
	}
}
