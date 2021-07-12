package view_alt;

import dao.AlunoDAO;
import dao.RelacionamentoDAO;
import dao.UsuarioDAO;

public class Teste {
	
	public static void main(String[] args) {
		
		//AlunoView aluno = new AlunoView();
		//aluno.setVisible(true);
		
		//AreaView area = new AreaView();
		//area.setVisible(true);
		
		//ConteudoView conteudo = new ConteudoView();
		//conteudo.setVisible(true);
		
		//CursoView curso = new CursoView();
		//curso.setVisible(true);
		
		//DisciplinaView disciplina = new DisciplinaView();
		//disciplina.setVisible(true);
		
		//GrupoView grupo = new GrupoView();
		//grupo.setVisible(true);
		
		//ProfessorView professor = new ProfessorView();
		//professor.setVisible(true);
		
		RelacionamentoView relacionamento = new RelacionamentoView(UsuarioDAO.selecionaUsuario("BP3007685"));
		relacionamento.setVisible(true);
		
		//UsuarioView usuario = new UsuarioView();
		//usuario.setVisible(true);
		
		
	}

}
