package products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
//import javafx.beans.property.ListProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProdutoDAO {


// Inserindo produtos na base de dados  
  public void insert(Produto product){
    //Abrindo uma conexão com o banco
    Connection conn = ConnectionFactory.getConnection();
    PreparedStatement stmt = null;

    try{
      stmt = conn.prepareStatement("INSERT INTO produtos (cod, descricao, preco, peso, quantidade) values(?,?,?,?,?)");
      stmt.setInt(1, product.getCod());
      stmt.setString(2,product.getDescricao());
      stmt.setDouble(3, product.getPreco());
      stmt.setDouble(4, product.getPeso());
      stmt.setInt(5, product.getQuantidade());

      stmt.executeUpdate();

      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Cadastro");
      alert.setHeaderText("Cadastrado com sucesso");
      alert.show();

    }catch (SQLException ex){
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao cadastrar produto");
      alert.show();
      
    }finally{
      ConnectionFactory.closeConnection(conn, stmt);
    }

  }

  //Alterando dados do produto ja cadastrado
  public void update(Produto product) {

    Connection conn = ConnectionFactory.getConnection();
    PreparedStatement stmt = null;

    try {
        stmt = conn.prepareStatement("UPDATE produtos SET descricao = ?, preco = ?, peso = ?, quantidade = ? where cod = ?");
       
        stmt.setString(1, product.getDescricao());
        stmt.setDouble(2, product.getPreco());
        stmt.setDouble(3, product.getPeso());
        stmt.setInt(4, product.getQuantidade());
        stmt.setInt(5, product.getCod());


        stmt.executeUpdate();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alteração");
        alert.setHeaderText("Alterado com sucesso");
        alert.show();
        
    } catch (SQLException ex) {
         Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao Alterar produto");
        alert.show();
    } finally {
        ConnectionFactory.closeConnection(conn, stmt);
    }
}

  public void dropQuantidade(double quantidade){
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
        stmt = conn.prepareStatement("UPDATE produtos SET quantidade = quantidade - ?");
       
        stmt.setDouble(1, quantidade);


        stmt.executeUpdate();
        
    } catch (SQLException ex) {
        
    } finally {
        ConnectionFactory.closeConnection(conn, stmt);
    }
  }
  //Deletar produto
public void delete(int cod) {

    Connection conn = ConnectionFactory.getConnection();
    PreparedStatement stmt = null;

    try {

        stmt = conn.prepareStatement("DELETE FROM produtos WHERE cod = ?");
        stmt.setInt(1, cod);
        System.out.println(cod);
        stmt.executeUpdate();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Exclusão");
        alert.setHeaderText("Excluido com sucesso");
        alert.show();
    } catch (SQLException ex) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao Excluir produto");
        alert.show();
    } finally {
        ConnectionFactory.closeConnection(conn, stmt);
    }
}


    
  //Adicionar e dar baixa do produto pelo carrinho
  public String addCart(int cod, double qtd) {

    Connection conn = ConnectionFactory.getConnection();
    PreparedStatement stmt = null;
    ResultSet rs = null;    
    
    String summary = null;try {

      stmt = conn.prepareStatement("SELECT * FROM produtos WHERE cod = ?");
      stmt.setInt(1, cod);
      rs = stmt.executeQuery();

         /*produto.setDescricao(rs.getString("descricao"));
          produto.setPreco(rs.getDouble("preco"));
          produto.setPeso(rs.getDouble("peso"));
          produto.setQuantidade(rs.getInt("quantidade"));*/
         String descricao = "";
         double preco = 0;
         double peso = 0;
         int quantidade = 0;
         
         while (rs.next()) {
             
          descricao = (rs.getString("descricao"));
          preco = (rs.getDouble("preco"));
          peso = (rs.getDouble("peso"));
          quantidade = (rs.getInt("quantidade"));
          
          
         }
         
          System.out.println(quantidade);
          
          if(quantidade >= qtd && qtd > 0) {
              if(descricao != ""){
                  summary = (descricao + "                                 R$ " + (preco*qtd) + "\n Peso: " + peso + "     Quantidade: " + qtd);
                  dropQuantidade(qtd);

              }else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Produto");
                alert.setHeaderText("Inexistente");
                alert.show();
              }
              
          }else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Produto");
            alert.setHeaderText("Quantidade indisponível");
            alert.show();
          }
          
    

  } catch (SQLException ex) {
      Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Produto");
        alert.setHeaderText("Produto não encontrado");
        alert.show();
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
  } finally {
      ConnectionFactory.closeConnection(conn, stmt);
  }
      
      return summary;
}
  
  //Procurar produto
  public List<Produto> searchProduto(int cod) {

  Connection con = ConnectionFactory.getConnection();
  PreparedStatement stmt = null;
  ResultSet rs = null;

  List<Produto> produtos = new ArrayList<>();

  try {

      stmt = con.prepareStatement("SELECT * FROM produtos WHERE cod = ?");
      stmt.setInt(1, cod);
      rs = stmt.executeQuery();

      while (rs.next()) {

          Produto produto = new Produto();

          produto.setCod(rs.getInt("cod"));
          produto.setDescricao(rs.getString("descricao"));
          produto.setPreco(rs.getDouble("preco"));
          produto.setPeso(rs.getDouble("peso"));
          produto.setQuantidade(rs.getInt("quantidade"));
          produtos.add(produto);
      }

  } catch (SQLException ex) {
      Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Produto");
        alert.setHeaderText("Produto não encontrado");
        alert.show();
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
  } finally {
      ConnectionFactory.closeConnection(con, stmt);
  }
  return produtos;
}
  
  //Listando os produtos na tabela - função do tipo array  
  public List<Produto> updateViewTable(){

    Connection conn = ConnectionFactory.getConnection();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    List<Produto> result = new ArrayList<>();

    try{

      stmt = conn.prepareStatement("SELECT * FROM produtos");
      rs = stmt.executeQuery();

      while(rs.next()){
        Produto produto = new Produto();

        produto.setCod(rs.getInt("cod"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setPreco(rs.getDouble("preco"));
        produto.setPeso(rs.getDouble("peso"));
        produto.setQuantidade(rs.getInt("quantidade"));
        result.add(produto);
        
      }

    } catch (SQLException ex) {
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
      ConnectionFactory.closeConnection(conn, stmt);
    }

    return result;    
  };



}


