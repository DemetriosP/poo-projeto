package view_alt;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.CursoDAO;
import dao.DisciplinaDAO;
import model.CursoModel;
import model.DisciplinaModel;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class CursoView extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private JTextField semestresField;
	private ArrayList<CursoModel> cursos;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CursoView frame = new CursoView();
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
	public CursoView() {
		setTitle("Curso");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 414, 289);
		contentPane.add(tabbedPane);
		
		JPanel cadastrar = new JPanel();
		tabbedPane.addTab("Cadastrar", cadastrar);
		cadastrar.setLayout(null);
		
		JLabel nomeLabel = new JLabel("Nome: ");
		nomeLabel.setBounds(59, 14, 46, 14);
		cadastrar.add(nomeLabel);
		
		JLabel semestresLabel = new JLabel("Semestres:");
		semestresLabel.setBounds(59, 53, 68, 14);
		cadastrar.add(semestresLabel);
		
		nomeField = new JTextField();
		nomeField.setBounds(162, 11, 180, 20);
		cadastrar.add(nomeField);
		nomeField.setColumns(10);
		
		semestresField = new JTextField();
		semestresField.setBounds(162, 50, 180, 20);
		cadastrar.add(semestresField);
		semestresField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 91, 326, 110);
		cadastrar.add(scrollPane);
		
		String[] disciplinasSem = {"Semestre", "Disciplina", "Presente"};
		
		DefaultTableModel disciplinaSem = new DefaultTableModel(disciplinaSem(), disciplinasSem) {
			
			public Class<?> getColumnClass(int column)
		      {
		        switch(column)
		        {
		        case 0:
		          return String.class;
		        case 1:
		          return String.class;
		        case 2:
		          return Boolean.class;
		        default:
		            return String.class;
		        }
		      }
		};
		
		table = new JTable();
		table.setModel(disciplinaSem);
		scrollPane.setViewportView(table);
		
		JButton cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(162, 212, 89, 23);
		cadastrar.add(cadastrarButton);
		
		JPanel excluir = new JPanel();
		tabbedPane.addTab("Excluir", excluir);
		excluir.setLayout(null);
		
		JLabel cursoLabel = new JLabel("Curso:");
		cursoLabel.setBounds(74, 40, 46, 14);
		excluir.add(cursoLabel);
		
		JComboBox<CursoModel> cursoBox = new JComboBox<>();
		cursoBox.setBounds(164, 36, 180, 22);
		cursoBox.setModel(cursoModel());
		excluir.add(cursoBox);
		
		JButton excluirButton = new JButton("Excluir");
		excluirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		excluirButton.setBounds(159, 205, 89, 23);
		excluir.add(excluirButton);
		
	}
	
	private DefaultComboBoxModel<CursoModel> cursoModel() {
		
		DefaultComboBoxModel<CursoModel> curso = new DefaultComboBoxModel<CursoModel>();
		
		cursos = CursoDAO.selecionarCursos();
		
		for(CursoModel cur:cursos) {
			curso.addElement(cur);
		}
		
		return curso;
	}
	
	private Object[][] disciplinaSem(){
		
		ArrayList<DisciplinaModel> disciplinas = new ArrayList<>();
		
		disciplinas = DisciplinaDAO.selecionarDisciplinas();
		
		Object[][] dados = new Object[disciplinas.size()][3];
		
		for(int linha = 0; linha < disciplinas.size(); linha++) {
			dados[linha][0] = "";
			dados[linha][1] = disciplinas.get(linha);
			dados[linha][2] = false;
		}
		
		return dados;
	}
}
