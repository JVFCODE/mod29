import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCAlunos {

    private static final String URL = "jdbc:mysql://localhost:3306/escola";
    private static final String USER = "root";
    private static final String PASSWORD = "senha";

    // Método para cadastrar um aluno
    public void cadastrarAluno(String nome, int idade) {
        String sql = "INSERT INTO alunos (nome, idade) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setInt(2, idade);
            pstmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para consultar um aluno pelo ID
    public void consultarAluno(int id) {
        String sql = "SELECT * FROM alunos WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome") + ", Idade: " + rs.getInt("idade"));
            } else {
                System.out.println("Aluno não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar todos os alunos
    public List<String> buscarTodosAlunos() {
        List<String> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                alunos.add("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome") + ", Idade: " + rs.getInt("idade"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }

    // Método para atualizar um aluno
    public void atualizarAluno(int id, String nome, int idade) {
        String sql = "UPDATE alunos SET nome = ?, idade = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setInt(2, idade);
            pstmt.setInt(3, id);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Aluno atualizado com sucesso!");
            } else {
                System.out.println("Aluno não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um aluno
    public void excluirAluno(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Aluno excluído com sucesso!");
            } else {
                System.out.println("Aluno não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JDBCAlunos jdbcAlunos = new JDBCAlunos();

        // Exemplo de uso
        jdbcAlunos.cadastrarAluno("João Silva", 20);
        jdbcAlunos.consultarAluno(1);
        jdbcAlunos.atualizarAluno(1, "João Silva", 21);
        jdbcAlunos.excluirAluno(1);

        List<String> alunos = jdbcAlunos.buscarTodosAlunos();
        for (String aluno : alunos) {
            System.out.println(aluno);
        }
    }
}
