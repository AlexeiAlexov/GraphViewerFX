package application.view;

import application.Main;
import application.model.Matrix;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateMatrixController {

	public Stage stage;
	
	@FXML
	private TextField nameLabel;
	@FXML
	private TextField sizeLabel;
	
	@FXML
	public void initialize(){
		
	}
	
	@FXML
	private void handleBack(){
		stage.close();
	}
	
	@FXML
	private void handleOk(){
		MainController.instance.addMatrix(new Matrix(nameLabel.getText(), Integer.parseInt(sizeLabel.getText())));
		Main.instance.openMatrixViewWindow(MainController.instance.matrixs.size()-1);
		stage.close();
	}
	
}
