package view_alt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.RelacionamentoDAO;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class RelacionamentoView extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private JTextField textField;
	private JTextField grauField;
	private JTextField textField_1;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RelacionamentoView frame = new RelacionamentoView();
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
	public RelacionamentoView() {
		setTitle("Relacionamento");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 414, 289);
		contentPane.add(tabbedPane);
		
		JPanel relacionarUsu = new JPanel();
		tabbedPane.addTab("Relacionar Usuários", null, relacionarUsu, null);
		relacionarUsu.setLayout(null);
		
		JLabel nomeLabel = new JLabel("Nome:");
		nomeLabel.setBounds(84, 27, 46, 14);
		relacionarUsu.add(nomeLabel);
		
		nomeField = new JTextField();
		nomeField.setBounds(140, 24, 180, 20);
		relacionarUsu.add(nomeField);
		nomeField.setColumns(10);
		
		JButton pesquisarButton = new JButton("Pesquisar");
		pesquisarButton.setBounds(163, 55, 89, 23);
		relacionarUsu.add(pesquisarButton);
		
		JButton relacionarButton = new JButton("Relacionar");
		relacionarButton.setBounds(163, 211, 89, 23);
		relacionarUsu.add(relacionarButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 89, 389, 111);
		relacionarUsu.add(scrollPane_1);
		
		String[] usuarioRelacionar = {"Usuário","Relacionar"};
		
		DefaultTableModel maisUserDM = new DefaultTableModel(usuarioMaisRelacionado(), usuarioRelacionar); 
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JPanel alterarConfiabilidade = new JPanel();
		tabbedPane.addTab("Alterar Grau de Confibalidade", alterarConfiabilidade);
		alterarConfiabilidade.setLayout(null);
		
		JLabel nomeLabel_1 = new JLabel("Nome:");
		nomeLabel_1.setBounds(74, 14, 46, 14);
		alterarConfiabilidade.add(nomeLabel_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(150, 11, 180, 20);
		alterarConfiabilidade.add(textField);
		
		JButton pesquisarButton_1 = new JButton("Pesquisar");
		pesquisarButton_1.setBounds(173, 42, 89, 23);
		alterarConfiabilidade.add(pesquisarButton_1);
		
		JButton alterarButton = new JButton("Alterar");
		alterarButton.setBounds(173, 211, 89, 23);
		alterarConfiabilidade.add(alterarButton);
		
		JLabel grauLabel = new JLabel("Novo Grau:");
		grauLabel.setBounds(74, 177, 66, 14);
		alterarConfiabilidade.add(grauLabel);
		
		grauField = new JTextField();
		grauField.setColumns(10);
		grauField.setBounds(150, 174, 180, 20);
		alterarConfiabilidade.add(grauField);
		
		JPanel excluir = new JPanel();
		tabbedPane.addTab("Excluir", excluir);
		excluir.setLayout(null);
		
		JLabel nomeLabel_2 = new JLabel("Nome:");
		nomeLabel_2.setBounds(87, 14, 46, 14);
		excluir.add(nomeLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(143, 11, 180, 20);
		excluir.add(textField_1);
		
		JButton pesquisarButton_2 = new JButton("Pesquisar");
		pesquisarButton_2.setBounds(166, 55, 89, 23);
		excluir.add(pesquisarButton_2);
		
		JButton excluirButton = new JButton("Excluir");
		excluirButton.setBounds(166, 191, 89, 23);
		excluir.add(excluirButton);
		
		JPanel usuariosMaisRelacionados = new JPanel();
		tabbedPane.addTab("Top Usuários Mais Relacionados", usuariosMaisRelacionados);
		usuariosMaisRelacionados.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 389, 183);
		usuariosMaisRelacionados.add(scrollPane);
		
		String[] maisUsuariosColuna = {"Usuario","Prontuário", "Relacionamentos"};
		
		DefaultTableModel maisUserDM = new DefaultTableModel(usuarioMaisRelacionado(), maisUsuariosColuna); 
		
		table = new JTable();
		table.setModel(maisUserDM);
		scrollPane.setViewportView(table);
		
	}
	
	public Object[][] usuarioMaisRelacionado(){
		
		Object [][] dadosUserMaisRel = RelacionamentoDAO.usuariosMaisRelacionados();
		return dadosUserMaisRel;
		
	}
	
	

}
