package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Aluno;

public class AlunoDAO {

    public void Inserir(Aluno Aluno) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO alunos (nome, email, celular, cpf, senha, endereco, cidade, bairro, cep)"
        + " VALUES (?,?,?,?,?,?,?,?,?)");
            sql.setString(1, Aluno.getNome());
            sql.setString(2, Aluno.getEmail());
            sql.setString(3, Aluno.getCelular());
            sql.setString(4, Aluno.getCpf());
            sql.setString(5, Aluno.getSenha());
            sql.setString(6, Aluno.getEndereco());
            sql.setString(7, Aluno.getCidade());
            sql.setString(8, Aluno.getBairro());
            sql.setString(9, Aluno.getCep());
            sql.executeUpdate();

       } catch (SQLException e) {
            e.printStackTrace(); // Para imprimir o erro no console
            throw new RuntimeException("Erro ao executar a operação no banco de dados.", e);
        } finally {
            conexao.closeConexao();
        }
    }

    public Aluno getAluno(int id) throws Exception {
    Conexao conexao = new Conexao();
    try {
        PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM alunos WHERE ID = ?");
        sql.setInt(1, id);
        ResultSet resultado = sql.executeQuery();

        if (resultado.next()) {
            Aluno aluno = new Aluno();
            aluno.setId(resultado.getInt("ID"));
            aluno.setNome(resultado.getString("NOME"));
            aluno.setEmail(resultado.getString("EMAIL"));
            aluno.setCelular(resultado.getString("CELULAR"));
            aluno.setCpf(resultado.getString("CPF"));
            aluno.setSenha(resultado.getString("SENHA"));
            aluno.setEndereco(resultado.getString("ENDERECO"));
            aluno.setCidade(resultado.getString("CIDADE"));
            aluno.setBairro(resultado.getString("BAIRRO"));
            aluno.setCep(resultado.getString("CEP"));
            return aluno;
        }
        return null; // Aluno não encontrado
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar aluno no banco de dados.", e);
    } finally {
        conexao.closeConexao();
    }
}

    public void Alterar(Aluno Aluno) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE alunos SET nome = ?, email = ?, celular = ?, cpf = ?, senha = ?, endereco = ?, cidade = ?, bairro = ?, cep = ? WHERE ID = ?");
            sql.setString(1, Aluno.getNome());
            sql.setString(2, Aluno.getEmail());
            sql.setString(3, Aluno.getCelular());
            sql.setString(4, Aluno.getCpf());
            sql.setString(5, Aluno.getSenha());
            sql.setString(6, Aluno.getEndereco());
            sql.setString(7, Aluno.getCidade());
            sql.setString(8, Aluno.getBairro());
            sql.setString(9, Aluno.getCep());
            sql.setInt(10, Aluno.getId());
            System.out.println("SQL: " + sql.toString());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de update (alterar) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public void Excluir(Aluno Aluno) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM alunos WHERE ID = ? ");
            sql.setInt(1, Aluno.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de delete (excluir) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public ArrayList<Aluno> ListaDeAlunos() {
        ArrayList<Aluno> meusAlunos = new ArrayList();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM alunos order by nome";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Aluno Aluno = new Aluno(resultado.getString("NOME"),
                            resultado.getString("EMAIL"),
                            resultado.getString("CELULAR"),
                            resultado.getString("CPF"),
                            resultado.getString("SENHA"),
                            resultado.getString("ENDERECO"),
                            resultado.getString("CIDADE"),
                            resultado.getString("BAIRRO"),
                            resultado.getString("CEP"));
                    Aluno.setId(Integer.parseInt(resultado.getString("id")));
                    meusAlunos.add(Aluno);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query de select (ListaDeAlunos) incorreta");
        } finally {
            conexao.closeConexao();
        }
        
     System.out.println("Tamanho da lista de alunos: " + meusAlunos.size());
        return meusAlunos;
    }

  public Aluno Logar(Aluno Aluno) throws Exception {
    Conexao conexao = new Conexao();
    try {
        PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM alunos WHERE cpf=? and senha =? LIMIT 1");
        sql.setString(1, Aluno.getCpf());
        sql.setString(2, Aluno.getSenha());
        ResultSet resultado = sql.executeQuery();
        Aluno AlunoObtido = new Aluno();
        if (resultado.next()) {
            AlunoObtido.setId(Integer.parseInt(resultado.getString("ID")));
            AlunoObtido.setNome(resultado.getString("NOME"));
            AlunoObtido.setEmail(resultado.getString("EMAIL"));
            AlunoObtido.setCelular(resultado.getString("CELULAR"));
            AlunoObtido.setCpf(resultado.getString("CPF"));
            AlunoObtido.setSenha(resultado.getString("SENHA"));
            AlunoObtido.setEndereco(resultado.getString("ENDERECO"));
            AlunoObtido.setCidade(resultado.getString("CIDADE"));
            AlunoObtido.setBairro(resultado.getString("BAIRRO"));
            AlunoObtido.setCep(resultado.getString("CEP"));
            return AlunoObtido;
        } else {
            return null;
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Query de select (get) incorreta");
    } finally {
        conexao.closeConexao();
    }
}

public boolean inserirAlunoNaTurma(int alunoId, int professorId, int disciplinaId, String codigoTurma) {
    Conexao conexao = new Conexao();
    try {
        // Prepara a query de inserção
        PreparedStatement sql = conexao.getConexao().prepareStatement(
            "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigo_turma, nota) VALUES (?, ?, ?, ?, 0)"
        );
        
        // Define os parâmetros
        sql.setInt(1, professorId);
        sql.setInt(2, disciplinaId);
        sql.setInt(3, alunoId);
        sql.setString(4, codigoTurma);
        
        // Executa a inserção
        int rowsAffected = sql.executeUpdate();
        
        // Se uma linha foi afetada, a operação foi bem-sucedida
        return rowsAffected > 0;

    } catch (SQLException e) {
        e.printStackTrace(); // Imprime o erro no console
        return false; // Retorna false em caso de erro
    } finally {
        conexao.closeConexao(); // Fecha a conexão
    }
}
public boolean isAlunoInscritoNaTurma(int alunoId, String codigoTurma) {
    Conexao conexao = new Conexao();
    // Verifica no banco se o aluno já está inscrito na turma
    String sql = "SELECT 1 FROM turmas WHERE aluno_id = ? AND codigo_turma = ?";
    try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
        stmt.setInt(1, alunoId);
        stmt.setString(2, codigoTurma);
        ResultSet rs = stmt.executeQuery();
        return rs.next();  // Se houver algum registro, o aluno está inscrito
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
