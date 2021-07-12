package view_alt;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.AreaDAO;
import model.AreaModel;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AreaView extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private ArrayList<AreaModel> areas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaView frame = new AreaView();
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
	public AreaView() {
		setTitle("Area");
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
		cadastrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AreaDAO.inserirArea(new AreaModel(nomeField.getText()));
			}
		});
		cadastrarButton.setBounds(169, 204, 89, 23);
		cadastrar.add(cadastrarButton);
		
		JPanel excluir = new JPanel();
		tabbedPane.addTab("Excluir", excluir);
		excluir.setLayout(null);
		
		JLabel areaLabel = new JLabel("\u00C1rea:");
		areaLabel.setBounds(82, 40, 46, 14);
		excluir.add(areaLabel);
		
		JComboBox<AreaModel> areaBox = new JComboBox<>();
		areaBox.setBounds(138, 36, 180, 22);
		areaBox.setModel(areaModel());
		excluir.add(areaBox);
		
		JButton excluirButton = new JButton("Excluir");
		excluirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AreaDAO.excluirArea(((AreaModel) areaBox.getSelectedItem()).getNome());
				areaBox.setModel(areaModel());
			}
		});
		excluirButton.setBounds(159, 205, 89, 23);
		excluir.add(excluirButton);
		
	}
	
	private DefaultComboBoxModel<AreaModel> areaModel() {
		
		DefaultComboBoxModel<AreaModel> area = new DefaultComboBoxModel<AreaModel>();
		
		areas = AreaDAO.selecionarAreas();
		
		for(AreaModel ar:areas) {
			area.addElement(ar);
		}
		
		return area;
	}
	
}
