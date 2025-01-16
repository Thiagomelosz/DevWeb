package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Professor;

/*
-- Estrutura da tabela `Professors`

CREATE TABLE IF NOT EXISTS `Professors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 NOT NULL,
  `cpf` varchar(14) CHARACTER SET utf8 NOT NULL,
  `senha` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
// id, nome, email, cpf, senha
 */


public class ProfessorDAO {

    public void Inserir(Professor Professor) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO professores (nome, email, cpf, senha)"
                    + " VALUES (?,?,?,?)");
            sql.setString(1, Professor.getNome());
            sql.setString(2, Professor.getEmail());
            sql.setString(3, Professor.getCpf());
            sql.setString(4, Professor.getSenha());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            conexao.closeConexao();
        }
    }

    public Professor getProfessor(int id) throws Exception {
        Conexao conexao = new Conexao();
        try {
            Professor Professor = new Professor();
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM professores WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    // id, nome, email, cpf, senha
                    Professor.setId(Integer.parseInt(resultado.getString("ID")));
                    Professor.setNome(resultado.getString("NOME"));
                    Professor.setEmail(resultado.getString("EMAIL"));
                    Professor.setCpf(resultado.getString("CPF"));
                    Professor.setSenha(resultado.getString("SENHA"));
                }
            }
            return Professor;

        } catch (SQLException e) {
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public void Alterar(Professor Professor) throws Exception {
        Conexao conexao = new Conexao();
        try {
            // id, nome, email, cpf, senha
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE professores SET nome = ?, email = ?, cpf = ?, senha = ? WHERE ID = ?");
            sql.setString(1, Professor.getNome());
            sql.setString(2, Professor.getEmail());
            sql.setString(3, Professor.getCpf());
            sql.setString(4, Professor.getSenha());
            sql.setInt(5, Professor.getId());
            System.out.println("SQL: " + sql.toString());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de update (alterar) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public void Excluir(Professor Professor) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM professores WHERE ID = ? ");
            sql.setInt(1, Professor.getId());
            System.out.println("SQL: " + sql.toString());
            sql.executeUpdate();
            

        } catch (SQLException e) {
            throw new RuntimeException("Query de delete (excluir) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public ArrayList<Professor> ListaDeProfessores() {
        ArrayList<Professor> meusProfessores = new ArrayList();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM professores order by nome";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Professor Professor = new Professor(resultado.getString("NOME"),
                            resultado.getString("EMAIL"),
                            resultado.getString("CPF"),
                            resultado.getString("SENHA"));
                    Professor.setId(Integer.parseInt(resultado.getString("id")));
                    meusProfessores.add(Professor);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query de select (ListaDeProfessors) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return meusProfessores;
    }

    public Professor Logar(Professor Professor) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM professores WHERE cpf=? and senha =? LIMIT 1");
            sql.setString(1, Professor.getCpf());
            sql.setString(2, Professor.getSenha());
            ResultSet resultado = sql.executeQuery();
            Professor ProfessorObtido = new Professor();
            if (resultado.next() != false) {
                //while (resultado.next()) {
                    ProfessorObtido.setId(Integer.parseInt(resultado.getString("ID")));
                    ProfessorObtido.setNome(resultado.getString("NOME"));
                    ProfessorObtido.setEmail(resultado.getString("EMAIL"));
                    ProfessorObtido.setCpf(resultado.getString("CPF"));
                    ProfessorObtido.setSenha(resultado.getString("SENHA"));
            return ProfessorObtido;
            }
        return null;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

}