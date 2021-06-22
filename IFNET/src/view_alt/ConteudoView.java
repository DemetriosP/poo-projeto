package view_alt;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ConteudoDAO;
import model.ConteudoModel;

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

public class ConteudoView extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private ArrayList<ConteudoModel> conteudos;
	private JTextField tituloField;
	private JTable table;
	private Object[][] data;
	private String[] nomeColuna = {"Título", "Tipo", "Publicador"};
	private DefaultTableModel dm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConteudoView frame = new ConteudoView();
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
	public ConteudoView() {
		setTitle("Conteúdo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 414, 289);
		contentPane.add(tabbedPane);
		
		JPanel pesquisar = new JPanel();
		tabbedPane.addTab("Pesquisar", pesquisar);
		pesquisar.setLayout(null);
		
		JLabel tituloLabel = new JLabel("Título:");
		tituloLabel.setBounds(84, 38, 40, 14);
		pesquisar.add(tituloLabel);
		
		tituloField = new JTextField();
		tituloField.setBounds(134, 35, 180, 20);
		pesquisar.add(tituloField);
		tituloField.setColumns(10);
		
		JButton pesquisarButton = new JButton("Pesquisar");
		pesquisarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data = dados(tituloField.getText());
				dm = new DefaultTableModel(data, nomeColuna);
				table.setModel(dm);
			}
		});
		pesquisarButton.setBounds(153, 80, 89, 23);
		pesquisar.add(pesquisarButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(51, 129, 316, 121);
		pesquisar.add(scrollPane);
		
		this.data = dados(tituloField.getText());
		
		dm = new DefaultTableModel(data, nomeColuna);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setModel(dm);
		scrollPane.setViewportView(table);
		
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
		
		JLabel conteudoLabel = new JLabel("Conteúdo: ");
		conteudoLabel.setBounds(66, 40, 62, 14);
		excluir.add(conteudoLabel);
		
		JComboBox<ConteudoModel> areaBox = new JComboBox<>();
		areaBox.setBounds(138, 36, 180, 22);
		areaBox.setModel(conteudoModel());
		excluir.add(areaBox);
		
		JButton excluirButton = new JButton("Excluir");
		excluirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		excluirButton.setBounds(159, 205, 89, 23);
		excluir.add(excluirButton);
		
	}
	
	private DefaultComboBoxModel<ConteudoModel> conteudoModel() {
		
		DefaultComboBoxModel<ConteudoModel> conteudo = new DefaultComboBoxModel<ConteudoModel>();
		
		conteudos = ConteudoDAO.selecionaConteudos();
		
		for(ConteudoModel cont:conteudos) {
			conteudo.addElement(cont);
		}
		
		return conteudo;
	}
	
	private Object[][] dados(String titulo){
		
		this.conteudos = ConteudoDAO.selecionaConteudos(titulo);
		
		Object[][] conteudos = new Object[this.conteudos.size()][3];
		
		for(int linha = 0; linha < this.conteudos.size(); linha++) {
			conteudos[linha][0] = this.conteudos.get(linha).getTitulo(); 
			conteudos[linha][1] = this.conteudos.get(linha).getTipoConteudo(); 
			conteudos[linha][2] = this.conteudos.get(linha).getPublicador().getNome(); 
		}
		
		return conteudos;
	}
}