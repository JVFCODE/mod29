import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/loja";
    private static final String USER = "root";
    private static final String PASSWORD = "senha";

    // Método para cadastrar um produto
    public void cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produto.getNome());
            pstmt.setDouble(2, produto.getPreco());
            pstmt.setInt(3, produto.getQuantidade());
            pstmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um produto
    public void excluirProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Produto excluído com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar um produto
    public void atualizarProduto(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produto.getNome());
            pstmt.setDouble(2, produto.getPreco());
            pstmt.setInt(3, produto.getQuantidade());
            pstmt.setInt(4, produto.getId());
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar um produto pelo ID
    public Produto buscarProduto(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        Produto produto = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    // Método para buscar todos os produtos
    public List<Produto> buscarTodosProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public static void main(String[] args) {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // Exemplo de uso
        Produto produto1 = new Produto(1, "Notebook", 2500.00, 10);
        produtoDAO.cadastrarProduto(produto1);

        Produto produto2 = produtoDAO.buscarProduto(1);
        System.out.println(produto2);

        produto2.setPreco(2300.00);
        produtoDAO.atualizarProduto(produto2);

        List<Produto> produtos = produtoDAO.buscarTodosProdutos();
        for (Produto p : produtos) {
            System.out.println(p);
        }

        produtoDAO.excluirProduto(1);
    }
}
