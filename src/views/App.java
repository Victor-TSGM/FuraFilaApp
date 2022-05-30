package views;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    public void setStage(Stage myStage) {
        stage = myStage;
    }

    public static Stage getStage() {
        return stage;
    }

    static Stage stage = new Stage();

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // FXMLLoader loader = new
        // FXMLLoader(this.getClass().getResource("src/fxml/layout.fxml"));
        Parent root = FXMLLoader.load(this.getClass().getResource("login.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.show();
        setStage(stage);

    }

    public static void fechar() {
        stage.close();
    }

}
