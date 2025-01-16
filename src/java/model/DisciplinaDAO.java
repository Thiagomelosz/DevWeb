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
    Disciplina disciplina = null; // Inicializa como null para indicar ausência de registro
    try {
        PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM disciplina WHERE id = ?");
        sql.setInt(1, id);
        ResultSet resultado = sql.executeQuery();

        if (resultado.next()) { // Verifica se há resultados
            disciplina = new Disciplina(
                resultado.getInt("id"),          // Usa getInt para colunas numéricas
                resultado.getString("nome"),    // Nome da coluna em minúsculo
                resultado.getString("requisito"),
                resultado.getString("ementa"),
                resultado.getInt("carga_horaria")
            );
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar disciplina: " + e.getMessage());
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
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE disciplina SET nome = ?, requisito = ?, ementa = ?, carga_horaria = ? WHERE ID = ?");
            sql.setString(1, t.getNome());         // Nome da disciplina
            sql.setString(2, t.getRequisito());    // Requisito
            sql.setString(3, t.getEmenta());       // Ementa
            sql.setInt(4, t.getCargahoraria());    // Carga horária
            sql.setInt(5, t.getId());  

            sql.executeUpdate();

            int rowsAffected = sql.executeUpdate();
        if (rowsAffected > 0) {
          
        } else {
            System.err.println("Nenhuma disciplina foi encontrada para atualizar. Verifique o ID.");
        }

    } catch (SQLException e) {
        System.err.println("Query de update (alterar disciplina) incorreta: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
}

    @Override
 public void delete(int id) {
        String query = "DELETE FROM disciplina WHERE ID = ?";

        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(query);
            sql.setInt(1, id);
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir disciplina: " + e.getMessage());
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

