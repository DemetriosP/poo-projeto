package view_alt;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ConteudoDAO;
import dao.DisciplinaDAO;
import dao.GrupoDAO;
import model.DisciplinaModel;
import model.GrupoModel;

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


public class GrupoView extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private ArrayList<GrupoModel> gruposDisciplinas;
	private ArrayList<GrupoModel> grupos;
	private ArrayList<DisciplinaModel> disciplinas;
	private JTable grupoDisciplinaTable;
	private Object[][] grupoDisciplinaData;
	private Object[][] maisUsuariosData;
	private String[] nomeColunaGrupoDis = {"Grupo", "Disciplina", "Tipo"};
	private String[] nomeColunaMaisUsuarios = {"Grupo", "Usuários"};
	private DefaultTableModel grupoDisDM;
	private DefaultTableModel maisUsuariosDM;
	private JTable maisUsuariosTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrupoView frame = new GrupoView();
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
	public GrupoView() {
		setTitle("Grupo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 414, 289);
		contentPane.add(tabbedPane);
		
		JPanel pesquisarGrupo = new JPanel();
		tabbedPane.addTab("Pesquisar Grupo Por Disciplina", null, pesquisarGrupo, null);
		pesquisarGrupo.setLayout(null);
		
		JLabel disciplinaLabel = new JLabel("Disciplina:");
		disciplinaLabel.setBounds(79, 41, 66, 14);
		pesquisarGrupo.add(disciplinaLabel);
		
		JComboBox<DisciplinaModel> disciplinaBox = new JComboBox<>();
		disciplinaBox.setBounds(145, 37, 180, 22);
		disciplinaBox.setModel(disciplinaModel());
		pesquisarGrupo.add(disciplinaBox);
		
		JButton pesquisarButton = new JButton("Pesquisar");
		pesquisarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grupoDisciplinaData = dados(disciplinaBox.getSelectedItem().toString());
				grupoDisDM = new DefaultTableModel(grupoDisciplinaData, nomeColunaGrupoDis);
				grupoDisciplinaTable.setModel(grupoDisDM);
			}
		});
		pesquisarButton.setBounds(153, 80, 89, 23);
		pesquisarGrupo.add(pesquisarButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 129, 326, 105);
		pesquisarGrupo.add(scrollPane);
		
		this.grupoDisciplinaData = dados(disciplinaBox.getSelectedItem().toString());
		
		grupoDisDM = new DefaultTableModel(grupoDisciplinaData, nomeColunaGrupoDis);
		
		grupoDisciplinaTable = new JTable();
		grupoDisciplinaTable.setModel(grupoDisDM);
		scrollPane.setViewportView(grupoDisciplinaTable);
		
		JPanel grupoMaisUsu = new JPanel();
		tabbedPane.addTab("Top Grupos Por Usuários", null, grupoMaisUsu, null);
		grupoMaisUsu.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 33, 345, 179);
		grupoMaisUsu.add(scrollPane_1);
		
		this.maisUsuariosData = dadosMaisUsuarios();
		
		maisUsuariosDM = new DefaultTableModel(maisUsuariosData, nomeColunaMaisUsuarios);
		
		maisUsuariosTable = new JTable();
		maisUsuariosTable.setModel(maisUsuariosDM);
		scrollPane_1.setViewportView(maisUsuariosTable);
		
		JPanel entrar = new JPanel();
		tabbedPane.addTab("Entrar", entrar);
		entrar.setLayout(null);
		
		JLabel grupoLabel_1 = new JLabel("Grupo: ");
		grupoLabel_1.setBounds(80, 41, 62, 14);
		entrar.add(grupoLabel_1);
		
		JComboBox<GrupoModel> grupoBox_1 = new JComboBox<GrupoModel>();
		grupoBox_1.setBounds(152, 37, 180, 22);
		grupoBox_1.setModel(grupoModel());
		entrar.add(grupoBox_1);
		
		JButton entrarButton = new JButton("Entrar");
		entrarButton.setBounds(173, 206, 89, 23);
		entrar.add(entrarButton);
		
		JPanel cadastrar = new JPanel();
		tabbedPane.addTab("Cadastrar", cadastrar);
		cadastrar.setLayout(null);
		
		JLabel nomeLabel = new JLabel("Nome: ");
		nomeLabel.setBounds(91, 28, 46, 14);
		cadastrar.add(nomeLabel);
		
		nomeField = new JTextField();
		nomeField.setBounds(147, 25, 180, 20);
		cadastrar.add(nomeField);
		nomeField.setColumns(10);
		
		JButton cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(169, 195, 89, 23);
		cadastrar.add(cadastrarButton);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(91, 122, 46, 14);
		cadastrar.add(lblTipo);
		
		JComboBox<String> tipoBox = new JComboBox<>();
		tipoBox.setBounds(147, 118, 180, 22);
		cadastrar.add(tipoBox);
		
		String[] tipo = {"Trabalho", "Pesquisa"};
		DefaultComboBoxModel<String> md = new DefaultComboBoxModel<>(tipo);
		tipoBox.setModel(md);
		
		JLabel disciplinaLabel2 = new JLabel("Disciplina");
		disciplinaLabel2.setBounds(91, 76, 46, 14);
		cadastrar.add(disciplinaLabel2);
		
		JComboBox<DisciplinaModel> disciplinaBox_1 = new JComboBox<DisciplinaModel>();
		disciplinaBox_1.setBounds(147, 72, 180, 22);
		disciplinaBox_1.setModel(disciplinaModel());
		cadastrar.add(disciplinaBox_1);
		
		JPanel excluir = new JPanel();
		tabbedPane.addTab("Excluir", excluir);
		excluir.setLayout(null);
		
		JLabel grupoLabel = new JLabel("Grupo: ");
		grupoLabel.setBounds(66, 40, 62, 14);
		excluir.add(grupoLabel);
		
		JComboBox<GrupoModel> grupoBox = new JComboBox<>();
		grupoBox.setBounds(138, 36, 180, 22);
		grupoBox.setModel(grupoModel());
		excluir.add(grupoBox);
		
		JButton excluirButton = new JButton("Excluir");
		excluirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		excluirButton.setBounds(159, 205, 89, 23);
		excluir.add(excluirButton);
		
	}
	
	private DefaultComboBoxModel<GrupoModel> grupoModel() {
		
		DefaultComboBoxModel<GrupoModel> grupo = new DefaultComboBoxModel<GrupoModel>();
		
		grupos = GrupoDAO.selecionaGrupo();
		
		for(GrupoModel gru:grupos) {
			grupo.addElement(gru);
		}
		
		return grupo;
	}
	
	private DefaultComboBoxModel<DisciplinaModel> disciplinaModel() {
		
		DefaultComboBoxModel<DisciplinaModel> disciplina = new DefaultComboBoxModel<DisciplinaModel>();
		
		disciplinas = DisciplinaDAO.selecionarDisciplinas();
		
		for(DisciplinaModel ar:disciplinas) {
			disciplina.addElement(ar);
		}
		
		return disciplina;
	}
	
	private Object[][] dados(String disciplinaNome){
		
		this.gruposDisciplinas = GrupoDAO.selecionaGrupoPorDisciplina(disciplinaNome);
		
		Object[][] grupos = new Object[this.gruposDisciplinas.size()][3];
		
		for(int linha = 0; linha < this.gruposDisciplinas.size(); linha++) {
			grupos[linha][0] = this.gruposDisciplinas.get(linha).getNome(); 
			grupos[linha][1] = this.gruposDisciplinas.get(linha).getDisciplina().getNome(); 
			grupos[linha][2] = this.gruposDisciplinas.get(linha).getTipo(); 
		}
		
		return grupos;
	}
	
	private Object[][] dadosMaisUsuarios(){
		
		Object[][] dados  = GrupoDAO.consultarGruposMaisUsuarios();
		
		return dados;
	}
}