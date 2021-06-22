package view_alt;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.DisciplinaDAO;
import model.DisciplinaModel;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DisciplinaView extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private ArrayList<DisciplinaModel> disciplinas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisciplinaView frame = new DisciplinaView();
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
	public DisciplinaView() {
		setTitle("Disciplina");
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
		nomeLabel.setBounds(93, 44, 46, 14);
		cadastrar.add(nomeLabel);
		
		nomeField = new JTextField();
		nomeField.setBounds(149, 41, 180, 20);
		cadastrar.add(nomeField);
		nomeField.setColumns(10);
		
		JButton cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(169, 204, 89, 23);
		cadastrar.add(cadastrarButton);
		
		JPanel excluir = new JPanel();
		tabbedPane.addTab("Excluir", excluir);
		excluir.setLayout(null);
		
		JLabel disciplinaLabel = new JLabel("Disciplina:");
		disciplinaLabel.setBounds(69, 40, 59, 14);
		excluir.add(disciplinaLabel);
		
		JComboBox<DisciplinaModel> areaBox = new JComboBox<>();
		areaBox.setBounds(138, 36, 180, 22);
		areaBox.setModel(disciplinaModel());
		excluir.add(areaBox);
		
		JButton excluirButton = new JButton("Excluir");
		excluirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		excluirButton.setBounds(159, 205, 89, 23);
		excluir.add(excluirButton);
		
	}
	
	private DefaultComboBoxModel<DisciplinaModel> disciplinaModel() {
		
		DefaultComboBoxModel<DisciplinaModel> disciplina = new DefaultComboBoxModel<DisciplinaModel>();
		
		disciplinas = DisciplinaDAO.selecionarDisciplinas();
		
		for(DisciplinaModel ar:disciplinas) {
			disciplina.addElement(ar);
		}
		
		return disciplina;
	}
	
}
