package zero.conversor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

@SuppressWarnings("serial")
public class Conversor extends JFrame {

	private Font padrao = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	private Font padrao2 = new Font(Font.SANS_SERIF, Font.BOLD, 50);

	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension halfDimension = new Dimension(dimension.width / 3, dimension.height * 5 / 6);

	private JLabel moedaHero = new JLabel();

	private JPanel painelPrincipal = new JPanel();
	private JPanel painelSecundario = new JPanel();

	private JFormattedTextField resultado1;
	private JFormattedTextField resultado2;

	private String moedas = "USD,BRL,EUR,JPY,GBP,CAD,AUD,CHF,CNY,HKD,NZD,SEK,KRW,SGD,NOK,MXN,INR,RUB,ZAR,TRY,IDR,TWD,SAR,AED,PLN,THB,DKK,MYR,HUF,CZK";
	private String simbolos = "$,R$,€,¥,£,$,AUD,CHF,¥,HK$,NZ$,kr,₩,S$,kr,$,MX$,₹,₽,R,₹,Rp,﷼,AED,zł,฿,kr,RM, Ft,Kč";

	private String[] listaDeMoedas = moedas.split(",");
	private String[] listaDeSimbolos = simbolos.split(",");

	private JButton converter = new JButton("Converter");

	private JComboBox<Object> moedaOrigem = new JComboBox<Object>(listaDeMoedas),
			moedaDestino = new JComboBox<Object>(listaDeMoedas);

	Color corDeFundo = new Color(0, 0, 30);
	Color corSecundaria = new Color(0, 0, 25);
	Color corComboBox = new Color(0, 0, 255);

	private Resposta resposta = new Resposta();

	public Conversor() {
		this.setTitle("Conversor de moedas");
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

		moedaOrigem.setSize(100, 45);
		moedaDestino.setSize(100, 45);

		moedaHero.setFont(padrao2);
		moedaHero.setBounds(100, 12, 400, 100);
		moedaHero.setForeground(Color.white);
		moedaHero.setText("...");

		this.painelPrincipal.add(moedaHero);

		moedaOrigem.setLocation(halfDimension.width / 2 + 50, 55);
		moedaDestino.setLocation(halfDimension.width / 2 + 50, 205);

		moedaOrigem.setBackground(corComboBox);
		moedaOrigem.setForeground(Color.white);

		moedaDestino.setBackground(corComboBox);
		moedaDestino.setForeground(Color.white);

		this.painelSecundario.add(moedaOrigem);
		this.painelSecundario.add(moedaDestino);
	}

	private void setResultado() {

		NumberFormat decimalFormat = DecimalFormat.getInstance();
		decimalFormat.setMinimumFractionDigits(2);
		decimalFormat.setMaximumFractionDigits(2);

		NumberFormatter decimalFormatter = new NumberFormatter(decimalFormat);
		decimalFormatter.setValueClass(Double.class);
		decimalFormatter.setAllowsInvalid(false);

		resultado1 = new JFormattedTextField(decimalFormatter);
		resultado2 = new JFormattedTextField(decimalFormatter);

		resultado1.setSize(175, 45);
		resultado1.setBorder(null);
		resultado1.setLocation(halfDimension.width / 2 - 150, 50);
		resultado1.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLUE));
		resultado1.setBackground(corSecundaria);

		resultado2.setSize(175, 45);
		resultado2.setBorder(null);
		resultado2.setLocation(halfDimension.width / 2 - 150, 200);
		resultado2.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLUE));
		resultado2.setBackground(corSecundaria);

		resultado1.setForeground(Color.white);
		resultado2.setForeground(Color.white);

		resultado1.setFont(padrao);
		resultado2.setFont(padrao);

		this.painelSecundario.add(resultado1);
		this.painelSecundario.add(resultado2);
	}

	private void setBotoes() {
		converter.setSize(300, 45);
		converter.setLocation(halfDimension.width / 2 - 150, 300);

		Calcular calculo = new Calcular();
		converter.addActionListener(calculo);

		converter.setBackground(corComboBox);
		converter.setForeground(Color.white);
		converter.setFont(padrao);

		this.painelSecundario.add(converter);

	}

	private class Calcular implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			moedaHero.setText("De " + listaDeSimbolos[moedaDestino.getSelectedIndex()] + " Para "
					+ listaDeSimbolos[moedaOrigem.getSelectedIndex()]);

			String simbolo = listaDeSimbolos[moedaDestino.getSelectedIndex()];

			String origem = moedaOrigem.getSelectedItem().toString();
			String destino = moedaDestino.getSelectedItem().toString();

			String aCalcular = resultado1.getText();

			if(origem!=destino) {
				String resultadoFinal = resposta.converte(origem, destino, aCalcular.replace(".", ""));
				resultadoFinal = formatarNumero(Double.parseDouble(resultadoFinal));
				resultado2.setText(simbolo + " " + resultadoFinal);
			}else resultado2.setText(simbolo + " " + aCalcular);

		}

		public String formatarNumero(double numero) {
			DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
			simbolos.setDecimalSeparator(',');
			simbolos.setGroupingSeparator('.');

			DecimalFormat formato = new DecimalFormat("###,###.##", simbolos);
			return formato.format(numero);
		}
	}

}
