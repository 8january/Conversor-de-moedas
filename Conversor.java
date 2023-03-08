package zero.conversor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Conversor extends JFrame {

	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension halfDimension = new Dimension(dimension.width * 2 / 5, dimension.height / 2);

	private JPanel painelPrincipal = new JPanel();
	private JPanel painelSecundario = new JPanel();

	private JTextField resultado = new JTextField();

	private String moedas = "USD,BRL,EUR,JPY,GBP,CAD,AUD,CHF,CNY,HKD,NZD,SEK,KRW,SGD,NOK,MXN,INR,RUB,ZAR,TRY,IDR,TWD,SAR,AED,PLN,THB,DKK,MYR,HUF,CZK.";
	private String[] listaDeMoedas = moedas.split(",");

	private JButton converter = new JButton("Converter");

	private JComboBox<Object> moedaOrigem = new JComboBox<Object>(listaDeMoedas),
			moedaDestino = new JComboBox<Object>(listaDeMoedas);

	Color corDeFundo = new Color(215, 180, 20);
	Color corSecundaria = new Color(250, 220, 155);

	private Resposta resposta = new Resposta();

	public Conversor() {
		this.setSize(halfDimension);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setPainelPrincipalESecundario();
		setResultado();
		setBotoes();

		this.setVisible(true);
	}

	private void setPainelPrincipalESecundario() {

		painelPrincipal.setBackground(corDeFundo);
		painelSecundario.setBackground(corSecundaria);

		painelPrincipal.setLayout(null);
		painelSecundario.setLayout(null);

		painelPrincipal.setSize(halfDimension.width, halfDimension.height);
		painelSecundario.setSize(halfDimension.width, halfDimension.height);

		painelSecundario.setLocation(0, halfDimension.height / 5);

		this.add(painelSecundario);
		this.add(painelPrincipal);

		setListaDeMoedas();
	}

	private void setListaDeMoedas() {

		moedaOrigem.setSize(100, 25);
		moedaDestino.setSize(100, 25);

		moedaOrigem.setLocation(halfDimension.width / 2 - moedaOrigem.getWidth() * 2, 50);
		moedaDestino.setLocation(halfDimension.width / 2 + moedaDestino.getWidth(), 50);

		this.painelSecundario.add(moedaOrigem);
		this.painelSecundario.add(moedaDestino);
	}

	private void setResultado() {
		resultado.setSize(150, 25);
		resultado.setLocation(halfDimension.width / 2 - 75, 50);

		this.painelSecundario.add(resultado);
	}

	private void setBotoes() {
		converter.setSize(150, 45);
		converter.setLocation(halfDimension.width / 2 - 75, 100);

		Calcular calculo = new Calcular();
		converter.addActionListener(calculo);

		this.painelSecundario.add(converter);

	}

	private class Calcular implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String origem = moedaOrigem.getSelectedItem().toString();
			String destino = moedaDestino.getSelectedItem().toString();

			String aCalcular = resultado.getText();

			String resultadoFinal = resposta.converte(origem, destino, aCalcular);

			resultado.setText(resultadoFinal);
		}

	}

}
