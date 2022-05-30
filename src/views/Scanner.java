package views;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Scanner extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(this.getClass().getResource("scanner_tela.fxml"));
    stage.initStyle(StageStyle.UNDECORATED);
    stage.setScene(new Scene(root));
    stage.show();  
        
  }
  
  
}