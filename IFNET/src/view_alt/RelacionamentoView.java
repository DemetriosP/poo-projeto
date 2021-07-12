package view_alt;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.RelacionamentoDAO;
import model.UsuarioModel;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class RelacionamentoView extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private JTextField nomeField2;
	private JTextField nomeField3;
	private JTable table;
	private JTable relTable;
	private JTable exTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RelacionamentoView frame = new RelacionamentoView(new UsuarioModel("a", "a", "a"));
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
	public RelacionamentoView(UsuarioModel usuario) {
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
		pesquisarButton.setBounds(163, 55, 99, 23);
		relacionarUsu.add(pesquisarButton);
		
		JButton relacionarButton = new JButton("Relacionar");
		relacionarButton.setBounds(163, 211, 99, 23);
		relacionarUsu.add(relacionarButton);
		
		JScrollPane relScrollPane = new JScrollPane();
		relScrollPane.setBounds(10, 89, 389, 111);
		relacionarUsu.add(relScrollPane);
		
		String[] usuarioRelCol = {"Usuário","Relacionar"};
		
		DefaultTableModel userRelDM = new DefaultTableModel(usuarios(), usuarioRelCol) {
			public Class<?> getColumnClass(int column) {
		        switch(column)
		        {
		        case 0:
		          return String.class;
		        case 1:
		          return Boolean.class;
		        default:
		            return String.class;
		        }
		      }
		}; 
		
		relTable = new JTable(userRelDM);
		relScrollPane.setViewportView(relTable);
		
		JPanel alterarConfiabilidade = new JPanel();
		tabbedPane.addTab("Alterar Grau de Confibalidade", alterarConfiabilidade);
		alterarConfiabilidade.setLayout(null);
		
		JLabel nomeLabel2 = new JLabel("Nome:");
		nomeLabel2.setBounds(74, 14, 46, 14);
		alterarConfiabilidade.add(nomeLabel2);
		
		nomeField2 = new JTextField();
		nomeField2.setColumns(10);
		nomeField2.setBounds(150, 11, 180, 20);
		alterarConfiabilidade.add(nomeField2);
		
		JButton pesquisarButton_1 = new JButton("Pesquisar");
		pesquisarButton_1.setBounds(173, 42, 89, 23);
		alterarConfiabilidade.add(pesquisarButton_1);
		
		JButton alterarButton = new JButton("Alterar");
		alterarButton.setBounds(173, 211, 89, 23);
		alterarConfiabilidade.add(alterarButton);
		
		JLabel grauLabel = new JLabel("Novo Grau:");
		grauLabel.setBounds(74, 185, 66, 14);
		alterarConfiabilidade.add(grauLabel);
		
		JScrollPane grauScrollPane = new JScrollPane();
		grauScrollPane.setBounds(28, 75, 359, 100);
		alterarConfiabilidade.add(grauScrollPane);
		
		String[] grauRelCol = {"Usuário","Grau de Relacionamento"};
		
		DefaultTableModel grauRelDM = new DefaultTableModel(usuariosRel(usuario), grauRelCol);
		
		JTable grauTable = new JTable(grauRelDM);
		grauScrollPane.setViewportView(grauTable);
		
		JComboBox<String> grauComboBox = new JComboBox<>();
		grauComboBox.setBounds(150, 181, 180, 22);
		alterarConfiabilidade.add(grauComboBox);
		
		String[] grau = {"Conhecidos", "Amigos", "Amigos Próximos"};
		DefaultComboBoxModel<String> md = new DefaultComboBoxModel<>(grau);
		grauComboBox.setModel(md);
		
		JPanel excluir = new JPanel();
		tabbedPane.addTab("Excluir", excluir);
		excluir.setLayout(null);
		
		JLabel nomeLabel3 = new JLabel("Nome:");
		nomeLabel3.setBounds(87, 14, 46, 14);
		excluir.add(nomeLabel3);
		
		nomeField3 = new JTextField();
		nomeField3.setColumns(10);
		nomeField3.setBounds(143, 11, 180, 20);
		excluir.add(nomeField3);
		
		JButton pesquisarButton2 = new JButton("Pesquisar");
		pesquisarButton2.setBounds(166, 42, 89, 23);
		excluir.add(pesquisarButton2);
		
		JButton excluirButton = new JButton("Excluir");
		excluirButton.setBounds(166, 211, 89, 23);
		excluir.add(excluirButton);
		
		JScrollPane exScrollPane = new JScrollPane();
		exScrollPane.setBounds(10, 83, 389, 117);
		excluir.add(exScrollPane);
		
		String[] exUserCol = {"Usuario"};
		
		DefaultTableModel exUserDM = new DefaultTableModel(usuariosRel(usuario), exUserCol); 
		
		exTable = new JTable(exUserDM);
		exScrollPane.setViewportView(exTable);
		
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
		
		Object [][] dadosUserMaisRel = RelacionamentoDAO.usuariosMaisRelacionadosIG();
		return dadosUserMaisRel;
		
	}
	
	public Object[][] usuarios() {
		
		ArrayList<UsuarioModel> usuarios = new ArrayList<>();
		
		usuarios.addAll(AlunoDAO.selecionarAlunos());
		usuarios.addAll(ProfessorDAO.selecionarProfessores());
		
		Object[][] dados = new Object[usuarios.size()][1];
		
		for(int linha = 0; linha < usuarios.size(); linha++) {
			dados[linha][0] = usuarios.get(linha);
		}
		
		return dados;
	}
	
	public Object[][] usuariosRel(UsuarioModel usuario) {
		
		int posicao = 0;
		
		usuario.setRelacionamento(RelacionamentoDAO.selecionarRelacionamento(usuario));
		
		Object[][] dados = new Object[usuario.getRelacionamento().getGrauUsuario().size()][2];
		
		for (Map.Entry<String , ArrayList<UsuarioModel>> mapa : usuario.getRelacionamento().getGrauUsuario().entrySet()) {
			for(UsuarioModel usuarios:mapa.getValue()) {
				dados[posicao][0] = usuarios;
				dados[posicao][1] = mapa.getKey();
				posicao++;
			}
		}
		
		return dados;
		
	}
}
