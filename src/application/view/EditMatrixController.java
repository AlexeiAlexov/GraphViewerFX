package application.view;


import application.model.Matrix;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class EditMatrixController {

	private static final int PREF_WIDTH = 34;
	private static final int PREF_HEIGHT = 34;
	
	private static final int WIDTH_PADDING = 10;
	private static final int HEIGHT_PADDING = 10;

	private static final Color COLOR_ZERO = Color.AQUAMARINE;
	private static final Color COLOR_ONE = Color.GOLD;
	
	
	public Matrix m;
	
	public Stage stage;
	
	public MatrixViewerController matrixViewerController;
	
	@FXML
	private HBox hbox;
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private TextField renameMatrixTextField;
	
	@FXML
	private Button saveButton;
	
	private Label[][] fields;
	private VBox[] vboxs;
	
	@FXML
	private void initialize(){}
	
	public void refresh(){
		
		int potW = m.getSize() * (PREF_WIDTH + WIDTH_PADDING) - WIDTH_PADDING;
		int potH = 50 + m.getSize() * (PREF_HEIGHT + HEIGHT_PADDING) - HEIGHT_PADDING;
		
		anchorPane.setPrefSize( potW > 400 ? potW : 400, potH > 450 ? potH : 450);
		
		renameMatrixTextField.setText(m.getName());
		
		vboxs = new VBox[m.getSize()];
		fields = new Label[m.getSize()][m.getSize()];
		
		stage.setResizable(false);
		
		EventHandler<MouseEvent> eh = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Label l = ((Label)event.getSource()); 
				if (l.getText().charAt(0) == '0')  l.setText("1"); else l.setText("0");  
				l.setBackground(new Background(new BackgroundFill(l.getText().charAt(0) == '0'? COLOR_ZERO : COLOR_ONE, CornerRadii.EMPTY, Insets.EMPTY)));
			}
		};
		
		hbox.setSpacing(WIDTH_PADDING);
		Label tf;
		VBox currentBox;
		for(int x = 0; x < fields.length; x++){
		
			vboxs[x] = new VBox();
			currentBox = vboxs[x];
			currentBox.setAlignment(Pos.CENTER);
			currentBox.setSpacing(HEIGHT_PADDING);
			
			hbox.getChildren().add(currentBox);

			for(int y = 0; y < fields.length; y++){
				 
				tf = new Label(m.get(x, y)+"");
				tf.setAlignment(Pos.CENTER);
				tf.setTextAlignment(TextAlignment.CENTER);
				
			    
				tf.setBackground(new Background(new BackgroundFill(m.get(x, y) == 0? COLOR_ZERO : COLOR_ONE, CornerRadii.EMPTY, Insets.EMPTY)));
				tf.setOnMouseClicked(eh);
				tf.setPrefSize(PREF_WIDTH, PREF_HEIGHT);

				fields[x][y] = tf;
				
				currentBox.getChildren().add(tf);
					
			}
		}
		
		tf = null;
		
	}
	
	@FXML
	private void handleSave(){
		for(int y = 0; y < fields.length; y++){
			for(int x = 0; x < fields.length; x++){
				m.set(x, y, Integer.parseInt(fields[x][y].getText()));
			}
		}
		m.refreshProperties();
		m.setName(renameMatrixTextField.getText());
		matrixViewerController.setMatrix(m);
		matrixViewerController.showProperties();
		
		stage.close();
	}
	
}
