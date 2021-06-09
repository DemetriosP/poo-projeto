package view;

import java.util.Scanner;

import dao.CursoDAO;
import model.AlunoModel;
import model.CursoModel;
import model.UsuarioModel;

public class AlunoView {
	
	static Scanner leitura = new Scanner(System.in);

	public static AlunoModel cadastrar(UsuarioModel usuario) {
		
		String email;
		
		AlunoModel aluno;
		
		CursoModel curso;
			
		System.out.print("Informe o e-mail: ");
		email = leitura.nextLine();
		
		curso = CursoView.escolherCurso(CursoDAO.selecionarCursos());
		
		aluno = new AlunoModel(usuario.getNome(), usuario.getProntuario(), usuario.getSenha(), email, curso);
		
		System.out.println("Cadastro realizado com sucesso!");
			
		return aluno;
			
	}
	
	
}


