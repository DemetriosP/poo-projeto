package view_alt;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.CursoDAO;
import model.CursoModel;

import javax.swing.JComboBox;

public class AlunoView extends JFrame {

	private JPanel contentPane;
	private JTextField emailField;
	private JButton cadastrarButton;
	private ArrayList<CursoModel> cursos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlunoView frame = new AlunoView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AlunoView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel cadastrarLabel = new JLabel("Cadastrar Aluno");
		cadastrarLabel.setBounds(175, 27, 100, 14);
		contentPane.add(cadastrarLabel);
		
		JLabel emailLabel = new JLabel("E-mail:");
		emailLabel.setBounds(88, 111, 46, 14);
		contentPane.add(emailLabel);
		
		JLabel cursoLabel = new JLabel("Curso:");
		cursoLabel.setBounds(88, 156, 72, 14);
		contentPane.add(cursoLabel);
		
		emailField = new JTextField();
		emailField.setBounds(153, 111, 180, 20);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(175, 261, 100, 23);
		contentPane.add(cadastrarButton);
		
		JComboBox<CursoModel> cursoBox = new JComboBox<>();
		cursoBox.setBounds(153, 152, 180, 22);
		cursoBox.setModel(cursoModel());
		contentPane.add(cursoBox);
		
	}
	
	private DefaultComboBoxModel<CursoModel> cursoModel() {
		
		DefaultComboBoxModel<CursoModel> curso = new DefaultComboBoxModel<CursoModel>();
		
		cursos = CursoDAO.selecionarCursos();
		
		for(CursoModel cur:cursos) {
			curso.addElement(cur);
		}
		
		return curso;
	}
	
	
}
