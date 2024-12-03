package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Disciplina;

/*
--
-- Estrutura da tabela `Disciplina`
--

CREATE TABLE IF NOT EXISTS `disciplina` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `requisito` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ementa` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `carga_horaria` smallint(5) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

id,nome,requisito,ementa,cargahoraria
 */
public class DisciplinaDAO implements Dao<Disciplina> {

    @Override
    public Disciplina get(int id) {
        Conexao conexao = new Conexao();
        Disciplina disciplina = new Disciplina();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM disciplina WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    disciplina.setId(Integer.parseInt(resultado.getString("ID")));
                    disciplina.setNome(resultado.getString("NOME"));
                    disciplina.setRequisito(resultado.getString("REQUISITO"));
                    disciplina.setEmenta(resultado.getString("EMENTA"));
                    disciplina.setCargahoraria(Integer.parseInt(resultado.getString("CARGAHORARIA")));
                    
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (get disciplina) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return disciplina;
    }

    @Override
    public void insert(Disciplina t) {

        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO disciplina (nome, requisito, ementa, carga_horaria) VALUES (?, ?, ?, ?)");
            sql.setString(1, t.getNome());
            sql.setString(2, t.getRequisito());
            sql.setString(3, t.getEmenta());
            sql.setInt(4, t.getCargahoraria());
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de insert (disciplina) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Disciplina t) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE disciplina SET nome = ?  WHERE ID = ? ");
            sql.setInt(1, t.getId());
            sql.setString(2, t.getNome());
            sql.setString(2, t.getRequisito());
            sql.setString(2, t.getEmenta());
            sql.setInt(1, t.getCargahoraria());

            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de update (alterar disciplina) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM disciplina WHERE ID = ? ");
            sql.setInt(1, id);
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de delete (excluir disciplina) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override

     public ArrayList<Disciplina> getAll() {
        ArrayList<Disciplina> ListaDisciplinas = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM disciplina";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Disciplina disciplina = new Disciplina(
                        resultado.getInt("id"), // Corrigido para minúsculo
                        resultado.getString("nome"),
                        resultado.getString("requisito"),
                        resultado.getString("ementa"),
                        resultado.getInt("carga_horaria") // Corrigido para carga_horaria
                    );
                    ListaDisciplinas.add(disciplina);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (GetAll - disciplina) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return ListaDisciplinas;
    }
}

