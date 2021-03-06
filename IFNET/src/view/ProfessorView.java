package view;

import dao.AreaDAO;
import dao.DisciplinaDAO;
import model.AreaModel;
import model.DisciplinaModel;
import model.ProfessorModel;
import model.UsuarioModel;

public class ProfessorView {
	
	public static ProfessorModel cadastrar(UsuarioModel usuario) {
		
		ProfessorModel professor;
		AreaModel area;
		DisciplinaModel disciplina;
		
		area = AreaView.escolherArea(AreaDAO.selecionarAreas());
		
		disciplina = DisciplinaView.escolherDisciplina(DisciplinaDAO.selecionarDisciplinas());
		
		professor = new ProfessorModel(usuario.getNome(), usuario.getProntuario(), 
				usuario.getSenha(), area, disciplina);
	
		System.out.println("Cadastro realizado com sucesso!");
		
		return professor;
			
	}

}
