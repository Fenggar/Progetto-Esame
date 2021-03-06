//commento per nuovo commit.
package ProgettoEsame.MeteoRite.Model;

import org.json.simple.JSONObject;

public class APICall 
{
	
	private String nomeCitta;
	private String unita = "&units=metric";
	private static String key = "3b5be1096db5f3df2026d19e0acef27d";
	private String id;
	private JSONObject cast;
	
	//private String indirizzo = "http://api.openweathermap.org/data/2.5/forecast?q=="+ nomeCitta +"&appid="+ key + unita;
	
	/**
	 * questo è un overload del costruttore 
	 * serve per la chiamata della città 
	 * @param nomeCitta
	 */
	public APICall(String nomeCitta)
	{
		this.nomeCitta=nomeCitta;
	}
	
	/**
	 * questo è un overload del costruttore 
	 * @param nomeCitta
	 * @param id
	 */
	public APICall(String nomeCitta, String id)
	{
		this.nomeCitta=nomeCitta;
		this.id=id;
	}
/**
 * questo è un overload del costruttore
 * serve per la chiamata all'API per nome di città e per ID corrispondente alla città.
 * Abbiamo poi specificato nella chiamata 
 * l'unità di misura della temperatura in celsius e non in 
 * kelvin che invece era di default 
 * @param nomeCitta
 * @param id
 * @param unita
 */
	public APICall(String nomeCitta,String id, String unita)
	{
		this.nomeCitta=nomeCitta;
		this.id=id;
		this.unita=unita;
	}
	
	/*public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	*/
	
	
/**
 * prende la key per accedere all'API
 * @return
 */
	public static String getKey() {
		return key;
	}
	
	/**
	 * sono un get  e un set riferito all'unità di musira 
	 * @return
	 */
	public String getUnita() {
		return unita;
	}
	public void setUnita(String unita) {
		this.unita = unita;
	}
	
	/**
	 * sono un get  e un set riferito al nome della città per la  chiamata all' API
	 * @return
	 */
	public String getNomeCitta() {
		return nomeCitta;
	}

	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}

	/**
	 * sono un get  e un set riferito al ID della città per la chiamata all' API
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * sono un get  e un set riferito JASON
	 * @return
	 */
	public JSONObject getCast() {
		return cast;
	}
	
	/**
	 * setter del parametro cast
	 * @param cast
	 */
	public void setCast(JSONObject cast) {
		this.cast = cast;
	}
	
	
	
	
	
	
	
}
