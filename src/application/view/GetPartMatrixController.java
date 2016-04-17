package application.view;


import application.model.Matrix;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class GetPartMatrixController {

	private static final int PREF_WIDTH = 34;
	private static final int PREF_HEIGHT = 34;
	
	private static final int WIDTH_PADDING = 10;
	private static final int HEIGHT_PADDING = 10;

	private static final Color COLOR_ZERO = Color.AQUAMARINE;
	private static final Color COLOR_ONE = Color.GOLD;
	
	private static final Background BG_ACTIVE = new Background(new BackgroundFill(COLOR_ONE, CornerRadii.EMPTY, Insets.EMPTY));
	private static final Background BG_INACTIVE = new Background(new BackgroundFill(COLOR_ZERO, CornerRadii.EMPTY, Insets.EMPTY));
	
	public Matrix m;
	
	public Stage stage;
	
	public MatrixViewerController matrixViewerController;
	
	@FXML
	private HBox hbox;
	
	@FXML
	private HBox xhbox;
	
	@FXML
	private AnchorPane anchorPane;
	
	
	@FXML
	private Button saveButton;
	
	private Label[] xlabels;
	
	private Label[][] fields;
	private VBox[] vboxs;
	
	private boolean[] activated;
	
	@FXML
	private void initialize(){}
	
	public void refresh(){
		
		int potW = m.getSize() * (PREF_WIDTH + WIDTH_PADDING) - WIDTH_PADDING;
		int potH = 50 + m.getSize() * (PREF_HEIGHT + HEIGHT_PADDING) - HEIGHT_PADDING;
		
		anchorPane.setPrefSize( potW > 400 ? potW : 400, potH > 450 ? potH : 450);
				

		xlabels = new Label[m.getSize()];
		
		vboxs = new VBox[m.getSize()];
		fields = new Label[m.getSize()][m.getSize()];
		
		stage.setResizable(false);
		
		activated = new boolean[m.getSize()];
		for(int i = 0; i < m.getSize(); i++)activated[i] = true;
		
		EventHandler<MouseEvent> eh = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Label l = ((Label)event.getSource());
				int pos = -1;
				for(int i = 0; i < m.getSize(); i++){
					if (xhbox.getChildren().get(i) == l){pos = i;break;}
				}
				
				activated[pos] = !activated[pos];
				l.setBackground(activated[pos]? BG_ACTIVE : BG_INACTIVE);
				
				for(int i = 0; i < m.getSize(); i++){
					if (activated[pos] && activated[i]){
						fields[i][pos].setBackground(BG_ACTIVE);
						fields[pos][i].setBackground(BG_ACTIVE);
					}
					else{
						fields[i][pos].setBackground(BG_INACTIVE);
						fields[pos][i].setBackground(BG_INACTIVE);
					}
				}
				
			}
		};
		
		Label tf;
		for(int x = 0; x < m.getSize(); x++){
			tf = new Label("X"+(x+1));
			tf.setBackground(BG_ACTIVE);
			tf.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
			tf.setAlignment(Pos.CENTER);
			tf.setTextAlignment(TextAlignment.CENTER);
			xlabels[x] = tf;
			tf.setOnMouseClicked(eh);
			xhbox.getChildren().add(tf);
		}
		
		hbox.setSpacing(WIDTH_PADDING);
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
				
			    
				tf.setBackground(BG_ACTIVE);
				tf.setPrefSize(PREF_WIDTH, PREF_HEIGHT);

				fields[x][y] = tf;
				
				currentBox.getChildren().add(tf);
					
			}
		}
		
		tf = null;
		
	}
	
	@FXML
	private void handleSave(){
		int size = 0;
		String name = m.getName() + "_";
		for(int i = 0; i < m.getSize(); i++){
			if (activated[i]){ 
				name += "x" + (i+1);
				size++;
			}
		}
		
		Matrix result = new Matrix(name, size);
		int rx = 0;
		int ry = 0;
		
		for(int y = 0; y < m.getSize(); y++){
			for(int x = 0; x < m.getSize(); x++){
				
				if (activated[x] && activated[y]){
					result.set(rx, ry, m.get(x, y));
					rx++;
				}
			}
			if (rx == size)ry++;
			rx = 0;
			if (ry == size)break;
		}
		MainController.instance.addMatrix(result);
		
		stage.close();
	}
	
}
