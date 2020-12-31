package ProgettoEsame.MeteoRite.Model;

public class APICall 
{
	private String nomeCitta;
	private String unita = "&units=metric";
	private static String key = "3b5be1096db5f3df2026d19e0acef27d";
	
	//private String indirizzo = "http://api.openweathermap.org/data/2.5/forecast?q=="+ nomeCitta +"&appid="+ key + unita;
	
	public APICall(String nomeCitta)
	{
		this.nomeCitta=nomeCitta;
	}
	
	public APICall(String nomeCitta, String unita)
	{
		this.nomeCitta=nomeCitta;
		this.unita=unita;
	}
	
	/*public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	*/

	public static String getKey() {
		return key;
	}
	
	public String getUnita() {
		return unita;
	}
	public void setUnita(String unita) {
		this.unita = unita;
	}
	
	public String getNomeCitta() {
		return nomeCitta;
	}

	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}
	
	
	
}
