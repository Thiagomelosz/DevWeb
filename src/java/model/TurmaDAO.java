package model;

import entidade.Aluno;
import entidade.Disciplina;
import entidade.Turma;
import java.sql.*;
import java.util.ArrayList;

public class TurmaDAO {


  public boolean insert(Turma t) {
    Conexao conexao = new Conexao();
    try {
        // Monta a consulta SQL para inserir uma nova turma
        String sqlQuery = "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigo_turma, nota) VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
        
        // Definindo os parâmetros da consulta
        sql.setInt(1, t.getProfessorId());
        sql.setInt(2, t.getDisciplinaId());
        sql.setInt(3, t.getAlunoId());
        sql.setString(4, t.getCodigoTurma());
        sql.setDouble(5, t.getNota());
        
        // Executa a inserção no banco de dados
        int resultado = sql.executeUpdate();
        if (resultado > 0) {
            return true;  // Inserção bem-sucedida
        } else {
            
            return false;  // Caso contrário, falhou
        }
    } catch (SQLException e) {
        // Captura e imprime o erro detalhado
       
        e.printStackTrace();  // Isso vai ajudar a capturar mais detalhes sobre a falha
        return false;  // Falha na operação
    } finally {
        conexao.closeConexao();
    }
}
public ArrayList<Turma> getAll() {
    ArrayList<Turma> turmas = new ArrayList<>();
    Conexao conexao = new Conexao();
    try {
        // Consulta SQL para pegar apenas 'codigo_turma' e 'nome' (da disciplina)
        String sqlQuery = "SELECT t.id, t.codigo_turma, d.nome AS nome_disciplina "
                        + "FROM turmas t "
                        + "JOIN disciplina d ON t.disciplina_id = d.id;";

        Statement stmt = conexao.getConexao().createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);

        while (rs.next()) {
            // Criação do objeto Turma
            Turma turma = new Turma();
            
            // Preenchendo com os dados da consulta (apenas 'codigo_turma' e 'nome_disciplina')
            turma.setCodigoTurma(rs.getString("codigo_turma"));
            turma.setNomeDisciplina(rs.getString("nome_disciplina"));
            turma.setId(rs.getInt("id"));
            // Adiciona a turma na lista
            turmas.add(turma);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar turmas: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
    return turmas;
}


 
public ArrayList<Turma> listaDeTurmas() {
    ArrayList<Turma> minhasTurmas = new ArrayList<>();
    Conexao conexao = new Conexao();
    try {
        // Consulta SQL para pegar as informações necessárias
        String selectSQL = "SELECT "
                 + "t.id AS turma_id, "
                 + "t.codigo_turma, "
                 + "d.id AS disciplina_id, "
                 + "d.nome AS nome_disciplina, "
                 + "p.id AS professor_id, "
                 + "p.nome AS professor, "
                 + "a.id AS aluno_id, "
                 + "a.nome AS aluno_nome "
                 + "FROM turmas t "
                 + "JOIN disciplina d ON t.disciplina_id = d.id "
                 + "JOIN professores p ON p.id = t.professor_id "
                 + "LEFT JOIN alunos a ON t.aluno_id = a.id "  // Alterado para LEFT JOIN
                 + "ORDER BY t.codigo_turma";

        PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
        ResultSet resultado = preparedStatement.executeQuery();

        if (resultado != null) {
            while (resultado.next()) {
                // Criação do objeto Turma com os dados do banco
                Turma turma = new Turma();
                turma.setId(resultado.getInt("turma_id"));
                turma.setCodigoTurma(resultado.getString("codigo_turma"));
                turma.setNomeDisciplina(resultado.getString("nome_disciplina"));
                turma.setNomeProfessor(resultado.getString("professor"));

                // Captura o nome do aluno, se houver
                String nomeAluno = resultado.getString("aluno_nome");
                if (nomeAluno != null) {
                    turma.setNomeAluno(nomeAluno);  // Aluno encontrado
                } else {
                    turma.setNomeAluno("Aluno não inscrito");  // Caso aluno não esteja inscrito
                }

                // Adiciona a turma à lista
                minhasTurmas.add(turma);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar turmas: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }

    return minhasTurmas;
}


public ArrayList<Turma> listaDeTurmasAluno() {
    ArrayList<Turma> minhasTurmas = new ArrayList<>();
    Conexao conexao = new Conexao();
    try {
        // Nova consulta SQL com GROUP BY e correção de espaços
        String selectSQL = "SELECT t.codigo_turma, MIN(t.id) AS id, d.nome AS nome_disciplina, p.nome AS professor " +
                           "FROM turmas t " +
                           "JOIN disciplina d ON t.disciplina_id = d.id " +
                           "JOIN professores p ON p.id = t.professor_id " +
                           "JOIN alunos a ON a.id = t.aluno_id " +  // Faltava espaço aqui
                           "GROUP BY t.codigo_turma, d.nome, p.nome " +
                           "ORDER BY t.codigo_turma";

        PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
        ResultSet resultado = preparedStatement.executeQuery();

        if (resultado != null) {
            while (resultado.next()) {
                // Criação do objeto Turma com os dados do banco
                Turma turma = new Turma();
                turma.setCodigoTurma(resultado.getString("codigo_turma"));
                turma.setId(resultado.getInt("id"));  // O ID agora é o valor mínimo encontrado para cada código_turma
                turma.setNomeDisciplina(resultado.getString("nome_disciplina"));
                turma.setNomeProfessor(resultado.getString("professor"));

                // Adiciona a turma à lista
                minhasTurmas.add(turma);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar turmas: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }

    return minhasTurmas;
}






    public Turma get(int id) {
        Conexao conexao = new Conexao();
        Turma turma = null;
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM turmas WHERE id = ?");         
           
            sql.setInt(1, id);
            ResultSet rs = sql.executeQuery();
            
            if (rs.next()) {
  
                turma = new Turma(
                rs.getInt("id"),
                rs.getInt("professor_id"),
                rs.getInt("disciplina_id"),
                rs.getInt("aluno_id"),
                rs.getString("codigo_turma"),
                rs.getDouble("nota")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return turma;
    }

    public void update(Turma t) {
        Conexao conexao = new Conexao();
        try {
            // Consulta SQL para atualizar as informações de uma turma
            String sqlQuery = "UPDATE turmas SET professor_id = ?, disciplina_id = ?, aluno_id = ?, codigo_turma = ?, nota = ? WHERE id = ?";
            
            PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
            
            // Definindo os parâmetros da consulta
            sql.setInt(1, t.getProfessorId());
            sql.setInt(2, t.getDisciplinaId());
            sql.setInt(3, t.getAlunoId());
            sql.setString(4, t.getCodigoTurma());
            sql.setDouble(5, t.getNota());
            sql.setInt(6, t.getId()); 
            
            // Executando a atualização
            sql.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            // Consulta para excluir uma turma pelo ID
            String sqlQuery = "DELETE FROM turmas WHERE id = ?";
            
            PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
            sql.setInt(1, id);
            
            // Executa a exclusão
            sql.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
        public boolean inscreverAluno(int alunoId, int turmaId) {
            Conexao conexao = new Conexao();
            String sql = "INSERT INTO turma(turma_id, aluno_id) VALUES (?, ?)";
            try {
                PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
                stmt.setInt(1, turmaId);
                stmt.setInt(2, alunoId);
                int linhasAfetadas = stmt.executeUpdate();
                return linhasAfetadas > 0; 
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conexao.closeConexao();
            }
        }
        
         public ArrayList<Turma> TurmasRelatorio() {
        ArrayList<Turma> turmas = new ArrayList<>();
        String query = "SELECT t.id AS turma_id, t.codigo_turma, a.id AS aluno_id, a.nome AS aluno_nome, t.nota, d.id AS disciplina_id " +
                       "FROM turmas t " +
                       "JOIN alunos a ON a.id = t.aluno_id " +
                       "JOIN disciplina d ON d.id = t.disciplina_id";

        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(query);
            ResultSet resultado = sql.executeQuery();


            while (resultado.next()) {

                Aluno aluno = new Aluno();
                aluno.setId(resultado.getInt("aluno_id"));
                aluno.setNome(resultado.getString("aluno_nome"));
                
                DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
                Disciplina disciplina = new Disciplina();


                Turma turma = new Turma();
                turma.setId(resultado.getInt("turma_id"));
                turma.setCodigoTurma(resultado.getString("codigo_turma"));
                turma.setDisciplinaId(resultado.getInt("disciplina_id"));
                turma.setNota(resultado.getDouble("nota"));


                turma.addAluno(aluno);

                turmas.add(turma);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas com alunos e notas: " + e.getMessage());
        }

        return turmas;
    }
         
         
   public Turma getTurmaPorId(int id) {
    Conexao conexao = new Conexao();
    Turma turma = null;
    try {
        String sqlQuery = "SELECT * FROM turmas WHERE id = ?";
        PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
        sql.setInt(1, id);
        ResultSet rs = sql.executeQuery();
        if (rs.next()) {
            turma = new Turma();
            turma.setId(rs.getInt("id"));
            turma.setProfessorId(rs.getInt("professor_id"));
            turma.setDisciplinaId(rs.getInt("disciplina_id"));
            turma.setAlunoId(rs.getInt("aluno_id"));
            turma.setCodigoTurma(rs.getString("codigo_turma"));
            turma.setNota(rs.getDouble("nota"));
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar turma por ID: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
    return turma;
    }
   
    public int contarAlunosNaTurma(String codigoTurma) {
    Conexao conexao = new Conexao();
    int contagem = 0;
    try {
        String sqlQuery = "SELECT COUNT(aluno_id) AS total FROM turmas WHERE codigo_turma = ?";
        PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
        sql.setString(1, codigoTurma);
        ResultSet rs = sql.executeQuery();
        if (rs.next()) {
            contagem = rs.getInt("total");
        }
    } catch (SQLException e) {
        System.err.println("Erro ao contar alunos na turma: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
    return contagem;
}
public boolean isAlunoInscrito(int alunoId, String codigoTurma) {
    Conexao conexao = new Conexao();
    boolean inscrito = false;
    try {
        String sqlQuery = "SELECT COUNT(*) AS total FROM turmas WHERE aluno_id = ? AND codigo_turma = ?";
        PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
        sql.setInt(1, alunoId);
        sql.setString(2, codigoTurma);
        
        System.out.println("Verificando inscrição para alunoId: " + alunoId + " na turma: " + codigoTurma); // Log de depuração
        
        ResultSet rs = sql.executeQuery();
        if (rs.next()) {
            inscrito = rs.getInt("total") > 0; // Se houver pelo menos um resultado, significa que o aluno está inscrito
        }
    } catch (SQLException e) {
        System.err.println("Erro ao verificar inscrição do aluno: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
    
    // Log final para verificar o retorno
    System.out.println("Aluno " + alunoId + " inscrito na turma " + codigoTurma + ": " + inscrito);
    
    return inscrito;
}


    public int contarProfessorNaTurma(int professorId) {
    Conexao conexao = new Conexao();
    int contagem = 0;
    try {
        String sqlQuery = "SELECT COUNT(professor_id) AS total FROM turmas WHERE professor_id = ?";
        PreparedStatement sql = conexao.getConexao().prepareStatement(sqlQuery);
        sql.setInt(1, professorId);
        ResultSet rs = sql.executeQuery();
        if (rs.next()) {
            contagem = rs.getInt("total");
        }
    } catch (SQLException e) {
        System.err.println("Erro ao contar professores na turma: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
    return contagem;
}


   

}

