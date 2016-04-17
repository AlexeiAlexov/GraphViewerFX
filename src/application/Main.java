package application;

import java.io.IOException;

import application.model.Matrix;
import application.view.BinaryOperationViewController;
import application.view.CreateMatrixController;
import application.view.EditMatrixController;
import application.view.GetPartMatrixController;
import application.view.MatrixViewerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Main instance;
	
	public Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {

			instance = this;
			stage = primaryStage;
			
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainM.fxml"));
            Scene scene = new Scene((AnchorPane)loader.load());
            
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openCreateMatrixWindow(){
		try {
        	
			Stage st = new Stage();
			
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CreateMatrixView.fxml"));
            
            
            
            Scene scene = new Scene((AnchorPane)loader.load());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			CreateMatrixController cmc = loader.getController();
            cmc.stage = st;
			
			st.setAlwaysOnTop(true);
			st.setScene(scene);
			
			
			st.initModality(Modality.APPLICATION_MODAL);
			st.requestFocus();
			st.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openBinaryOperationWindow(int id){
		try {
			
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/BinaryOperationView.fxml"));
            
			Scene scene = new Scene((AnchorPane)loader.load());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Stage st = new Stage();
			st.setScene(scene);
			
			BinaryOperationViewController controller = loader.getController();
			controller.stage = st;
			controller.setId(id);
			
			st.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openMatrixViewWindow(int index){
		try {
			
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MatrixViewer.fxml"));
            
			Scene scene = new Scene((AnchorPane)loader.load());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Stage st = new Stage();
			st.setScene(scene);
			
			MatrixViewerController controller = loader.getController();
            
			controller.matrixIndex = index;
			controller.stage = st;
			controller.refresh();
			
			st.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openEditMartrixWindow(Matrix m, MatrixViewerController mvc){
		try {
			
			
        	FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource("view/EditMatrixView.fxml"));
            
        	Scene scene = new Scene((AnchorPane)loader.load());
            
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Stage st = new Stage();
			
			EditMatrixController controller = loader.getController();
			controller.m = m;
			controller.stage = st;
			controller.matrixViewerController = mvc;
			controller.refresh();
			
			st.setAlwaysOnTop(true);
			st.setScene(scene);
			st.initModality(Modality.APPLICATION_MODAL);
			st.requestFocus();
			
			st.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void openPartMartrixWindow(Matrix m){
		try {
			
			
        	FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource("view/GetPartMatrixView.fxml"));
            
        	Scene scene = new Scene((AnchorPane)loader.load());
            
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Stage st = new Stage();
			
			GetPartMatrixController controller = loader.getController();
			controller.m = m;
			controller.stage = st;
			controller.refresh();
			
			st.setAlwaysOnTop(true);
			st.setScene(scene);
			st.initModality(Modality.APPLICATION_MODAL);
			st.requestFocus();
			
			st.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
