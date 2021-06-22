package view_alt;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.AreaDAO;
import dao.CursoDAO;
import dao.DisciplinaDAO;
import model.AreaModel;
import model.CursoModel;
import model.DisciplinaModel;

public class ProfessorView extends JFrame {

	private JPanel contentPane;
	private JButton cadastrarButton;
	private ArrayList<AreaModel> areas;
	private ArrayList<DisciplinaModel> disciplinas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorView frame = new ProfessorView();
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
	public ProfessorView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel cadastrarLabel = new JLabel("Cadastrar Professor");
		cadastrarLabel.setBounds(175, 27, 121, 14);
		contentPane.add(cadastrarLabel);
		
		JLabel areaLabel = new JLabel("\u00C1rea:");
		areaLabel.setBounds(88, 111, 46, 14);
		contentPane.add(areaLabel);
		
		JLabel disciplinaLabel = new JLabel("Disciplina:");
		disciplinaLabel.setBounds(88, 156, 72, 14);
		contentPane.add(disciplinaLabel);
		
		JComboBox<AreaModel> areaBox = new JComboBox<>();
		areaBox.setBounds(153, 111, 180, 20);
		areaBox.setModel(areaModel());
		contentPane.add(areaBox);
		
		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(175, 261, 100, 23);
		contentPane.add(cadastrarButton);
		
		JComboBox<DisciplinaModel> disciplinaBox = new JComboBox<>();
		disciplinaBox.setBounds(153, 152, 180, 22);
		disciplinaBox.setModel(disciplinaModel());
		contentPane.add(disciplinaBox);
	}
	
	private DefaultComboBoxModel<AreaModel> areaModel() {
		
		DefaultComboBoxModel<AreaModel> area = new DefaultComboBoxModel<AreaModel>();
		
		areas = AreaDAO.selecionarAreas();
		
		for(AreaModel ar:areas) {
			area.addElement(ar);
		}
		
		return area;
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
