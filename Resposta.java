package zero.conversor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Resposta {

	private String url = "https://economia.awesomeapi.com.br/last/";
	private String moedaOrigem = "";
	private String moedaDestino = "";
	private String valor = "";

	public String converte(String moedaOrigem, String moedaDestino, String valor) {

		String resultado = null;
		this.moedaOrigem = moedaOrigem;
		this.moedaDestino = moedaDestino;
		this.valor = valor;

		String completeUrl = url + moedaOrigem + "-" + moedaDestino;
		resultado = Double.toString(Double.parseDouble(valor) * resultadoHttp(completeUrl));

		return resultado;
	}

	private double resultadoHttp(String stringUrl) {
		double valorConvertido = 0;

		try {
			URL url = new URL(stringUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();

			JsonObject jsonObject = new Gson().fromJson(content.toString(), JsonObject.class);
			JsonElement value = jsonObject.get(moedaOrigem+moedaDestino);

			System.out.println(value.getAsJsonObject().get("high").getAsDouble());
			valorConvertido = value.getAsJsonObject().get("high").getAsDouble();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return valorConvertido;

	}


}
