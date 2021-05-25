package ifnet;

import dao.GrupoDAO;
import dao.UsuarioDAO;

public class Teste {
	
	public static void main(String[] args) {
		
		Grupo grupo = new Grupo("ATVIDADES", new Disciplina("Linguagem e Programação"), new Professor("BP3007685"), "Trabalho");
		
		//grupo.getUsuariosGrupo().add(new Usuario("BP500"));
		//grupo.getUsuariosGrupo().add(new Usuario("BP670"));
		
		//UsuarioDAO.insere(new Usuario("Dexter", "BP500", "1453"));
		//UsuarioDAO.insere(new Usuario("Olivia", "BP670", "3652"));
		
		
		System.out.println(GrupoDAO.selecionaGrupoID(grupo));
		
	}

}
