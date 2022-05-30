package views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import products.Produto;
import products.ProdutoDAO;



public class carrinhoController {

    //hide
    @FXML
    private Pane WebcamPanel;

    @FXML
    private Button addBtn;

    @FXML
    private TextField codTxt;
    
    @FXML
    private TextArea cartSumarry;
    
    @FXML
    private Spinner<Integer> qtdChange;


    //Visible
    @FXML
    private ImageView closeBtn;

    @FXML
    private ImageView addProduto;

    @FXML
    private ImageView textEmpty;

    private void initSpinner() {
        qtdChange.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10)
        );
    }



    @FXML
    void closeApp(MouseEvent event) {
        System.exit(0);
    }    

    @FXML
    void openCodInput(MouseEvent event) {

        showInputCod();
        
        cartSumarry.setVisible(false);
        if(textEmpty.getOpacity() == 1) {
            textEmpty.setOpacity(0);

        }else {
            
        }
    }

    @FXML
    void addToCart(MouseEvent event) {
        adicionar();
    }

    public void showInputCod() {
        WebcamPanel.setVisible(true);   
        initSpinner();             
    }

    public void adicionar() {
        ProdutoDAO dao = new ProdutoDAO(); 
        Produto produto = new Produto();

        int codigo = Integer.parseInt(codTxt.getText());
        double quantidade = qtdChange.getValue();   
        
        /*String descricao = produto.getDescricao();
        double preco = produto.getPreco();
        double peso = produto.getPeso();*/
        
        String summary = dao.addCart(codigo, quantidade);
        
        if(summary != null) {
                cartSumarry.setVisible(true);
                cartSumarry.appendText(summary);
                cartSumarry.appendText("\n======================\n\n");
                
                WebcamPanel.setVisible(false);
        }
        

        

    }
};