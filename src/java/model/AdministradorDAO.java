package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Administrador;

/*
-- Estrutura da tabela `Administradors`

CREATE TABLE IF NOT EXISTS `Administrador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `senha` varchar(8) NOT NULL,
  `endereco` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

 */
public class AdministradorDAO {

       public void Inserir(Administrador Administrador) throws Exception {
            Conexao conexao = new Conexao();
            try {
                System.out.println("Preparando para inserir administrador:");
                System.out.println("ID: " + Administrador.getId());
                System.out.println("Nome: " + Administrador.getNome());
                System.out.println("CPF: " + Administrador.getCpf());
                System.out.println("Endereço: " + Administrador.getEndereco());
                System.out.println("Senha: " + Administrador.getSenha());
                System.out.println("Aprovado: " + Administrador.getAprovado());

                // Adicionando 'aprovado' no comando INSERT
                PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO Administrador (nome, cpf, endereco, senha, aprovado)"
                        + " VALUES (?,?,?,?,?)");
                sql.setString(1, Administrador.getNome());
                sql.setString(2, Administrador.getCpf());
                sql.setString(3, Administrador.getEndereco());
                sql.setString(4, Administrador.getSenha());
                sql.setInt(5, Administrador.getAprovado()); // Adicionando valor para o campo 'aprovado'

                int rowsAffected = sql.executeUpdate();
                System.out.println("Linhas afetadas na inserção: " + rowsAffected);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Erro ao inserir o administrador: " + e.getMessage(), e);
            } finally {
                conexao.closeConexao();
            }
        }

    public Administrador getAdministrador(int id) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Administrador WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado.next()) {
                Administrador administrador = new Administrador();
                    administrador.setId(Integer.parseInt(resultado.getString("ID")));
                    administrador.setNome(resultado.getString("NOME"));
                    administrador.setCpf(resultado.getString("CPF"));
                    administrador.setEndereco(resultado.getString("ENDERECO"));
                    administrador.setSenha(resultado.getString("SENHA"));
                    return administrador;
                } return null;   
            
        } catch (SQLException e) {
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public void Alterar(Administrador Administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE Administrador SET nome = ?, cpf = ?, endereco = ?, senha = ?  WHERE ID = ? ");
            sql.setString(1, Administrador.getNome());
            sql.setString(2, Administrador.getCpf());
            sql.setString(3, Administrador.getEndereco());
            sql.setString(4, Administrador.getSenha());
            sql.setInt(5, Administrador.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de update (alterar) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public void Excluir(Administrador Administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM Administrador WHERE ID = ? ");
            sql.setInt(1, Administrador.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de delete (excluir) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public ArrayList<Administrador> ListaDeAdministrador() {
    ArrayList<Administrador> meusAdministradores = new ArrayList<>();
    Conexao conexao = new Conexao();
    try {
        String selectSQL = "SELECT * FROM Administrador ORDER BY nome";
        PreparedStatement preparedStatement;
        preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
        ResultSet resultado = preparedStatement.executeQuery();
        
        while (resultado.next()) {
            // Criando o objeto Administrador com os valores recuperados
            Administrador administrador = new Administrador(
                resultado.getInt("id"), // ID do Administrador
                resultado.getString("NOME"), // Nome
                resultado.getString("CPF"), // CPF
                resultado.getString("SENHA"), // Senha
                resultado.getInt("APROVADO"), // Aprovado (int)
                resultado.getString("ENDERECO") // Endereço
            );
            
            // Adicionando o objeto à lista
            meusAdministradores.add(administrador);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Query de select (ListaDeAdministradores) incorreta", e);
    } finally {
        conexao.closeConexao();
    }
    return meusAdministradores;
}

    public Administrador Logar(Administrador Administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Administrador WHERE cpf=? and senha =? LIMIT 1");
            sql.setString(1, Administrador.getCpf());
            sql.setString(2, Administrador.getSenha());
            ResultSet resultado = sql.executeQuery();
            Administrador AdministradorObtido = new Administrador();
            if (resultado.next() != false) {
                //while (resultado.next()) {
                    AdministradorObtido.setId(Integer.parseInt(resultado.getString("ID")));
                    AdministradorObtido.setNome(resultado.getString("NOME"));
                    AdministradorObtido.setCpf(resultado.getString("CPF"));
                    AdministradorObtido.setEndereco(resultado.getString("ENDERECO"));
                    AdministradorObtido.setSenha(resultado.getString("SENHA"));
                //}
                return AdministradorObtido;
            }
        return null;        

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }
    
    public void aprovarAdmin(int id) throws SQLException {
        Conexao conexao = new Conexao();
        try {
            String sql = "UPDATE Administrador SET aprovado = 1 WHERE id = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Administrador com ID " + id + " aprovado com sucesso.");
            } else {
                System.out.println("Nenhuma linha foi afetada. Verifique o ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao aprovar administrador: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }


}
