package view_alt;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class UsuarioView extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private JTextField prontuarioField;
	private JTextField senhaField;
	private JButton continuarButton;
	private JRadioButton professorRadioButton;
	private JRadioButton alunoRadioButton;
	
	public JButton getContinuarButton() {
		return continuarButton;
	}

	public void setContinuarButton(JButton continuarButton) {
		this.continuarButton = continuarButton;
	}

	public JTextField getNomeField() {
		return nomeField;
	}

	public void setNomeField(JTextField nomeField) {
		this.nomeField = nomeField;
	}

	public JTextField getProntuarioField() {
		return prontuarioField;
	}

	public void setProntuarioField(JTextField prontuarioField) {
		this.prontuarioField = prontuarioField;
	}

	public JTextField getSenhaField() {
		return senhaField;
	}

	public void setSenhaField(JTextField senhaField) {
		this.senhaField = senhaField;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioView frame = new UsuarioView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UsuarioView() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel cadastrarLabel = new JLabel("Cadastrar");
		cadastrarLabel.setBounds(187, 27, 74, 14);
		contentPane.add(cadastrarLabel);
		
		JLabel nomeLabel = new JLabel("Nome: ");
		nomeLabel.setBounds(123, 70, 46, 14);
		contentPane.add(nomeLabel);
		
		JLabel prontuarioLabel = new JLabel("Prontuário: ");
		prontuarioLabel.setBounds(123, 115, 72, 14);
		contentPane.add(prontuarioLabel);
		
		JLabel senhaLabel = new JLabel("Senha:");
		senhaLabel.setBounds(123, 169, 46, 14);
		contentPane.add(senhaLabel);
		
		JLabel tipoLabel = new JLabel("Tipo de Cadastro:");
		tipoLabel.setBounds(80, 220, 99, 14);
		contentPane.add(tipoLabel);
		
		nomeField = new JTextField();
		nomeField.setBounds(216, 70, 86, 20);
		contentPane.add(nomeField);
		nomeField.setColumns(10);
		
		prontuarioField = new JTextField();
		prontuarioField.setBounds(216, 115, 86, 20);
		contentPane.add(prontuarioField);
		prontuarioField.setColumns(10);
		
		senhaField = new JTextField();
		senhaField.setBounds(216, 169, 86, 20);
		contentPane.add(senhaField);
		senhaField.setColumns(10);
		
		professorRadioButton = new JRadioButton("Professor");
		professorRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alunoRadioButton.setSelected(false);
			}
		});
		professorRadioButton.setBounds(272, 216, 108, 23);
		contentPane.add(professorRadioButton);
		
		alunoRadioButton = new JRadioButton("Aluno");
		alunoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				professorRadioButton.setSelected(false);
			}
		});
		alunoRadioButton.setBounds(190, 216, 61, 23);
		contentPane.add(alunoRadioButton);
		
		continuarButton = new JButton("Continuar");
		continuarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continuarAction(e);
			}
		});
		continuarButton.setBounds(172, 261, 89, 23);
		contentPane.add(continuarButton);
	}
	
	private void continuarAction(java.awt.event.ActionEvent evt) {
		
		if(continuarButton.isEnabled()) {
			if((alunoRadioButton.isSelected() || professorRadioButton.isSelected())) {
				if(!senhaField.getText().isEmpty() && !nomeField.getText().isEmpty() && !prontuarioField.getText().isEmpty()) {
					setVisible(false);
				}else {
					JOptionPane.showMessageDialog(null, "É necesssário preencher todos os campos antes de prosseguir.");
				}
			}else {
				JOptionPane.showMessageDialog(null, "É necessário selecionar um dos tipos de cadastro.");
			}
		}
		
	}
	
	
	
	
}
