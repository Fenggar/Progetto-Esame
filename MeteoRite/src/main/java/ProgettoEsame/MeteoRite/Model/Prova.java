package ProgettoEsame.MeteoRite.Model;

public class Prova 
{
	
	private String citta = "Pandoiano";
	private String key = "3b5be1096db5f3df2026d19e0acef27d";
	private String unita = "&units=metric";
	private String indirizzo = "http://api.openweathermap.org/data/2.5/forecast?id="+ citta +"&appid="+ key + unita;
	
	public Prova(String citta)
	{
		this.citta= citta;
	}
	
	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUnita() {
		return unita;
	}

	public void setUnita(String unita) {
		this.unita = unita;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	

}
