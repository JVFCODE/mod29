import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements IClienteDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/banco_de_dados";
    private static final String USER = "root";
    private static final String PASSWORD = "senha";

    @Override
    public void adicionarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.executeUpdate();
            System.out.println("Cliente adicionado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente buscarClientePorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Cliente cliente = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public List<Cliente> buscarClientesPorNome(String nome) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nome + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    @Override
    public List<Cliente> listarTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    @Override
    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setInt(4, cliente.getId());
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Cliente não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removerCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Cliente removido com sucesso!");
            } else {
                System.out.println("Cliente não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> buscarClientesPorEmail(String email) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    @Override
    public int contarClientes() {
        String sql = "SELECT COUNT(*) AS total FROM clientes";
        int total = 0;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
}
