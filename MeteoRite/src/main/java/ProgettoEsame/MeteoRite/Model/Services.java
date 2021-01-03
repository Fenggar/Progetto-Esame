package ProgettoEsame.MeteoRite.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class Services 
{
	private JSONObject jo= null;
	private JSONArray ja = null;
	private APICall call = new APICall("Pandoiano");
	
	public void nameToID(String nome) throws MalformedURLException
	{
		String ID ="";
		
		forecastNome(nome);
		System.out.println("CHIAMATO FORCASTNAME DA nameToID");
		JSONObject app = call.getCast();
		JSONObject city = (JSONObject) app.get("city");
		Object idCitta =city.get("id");
		System.out.println("idCitta: "+idCitta);
		
		ID =idCitta.toString();
		System.out.println("id:" +ID);
		call.setId(ID);
		
		System.out.println("CONVERTITO ID");
		
	}
	
	public JSONObject call() throws MalformedURLException
	{
		APICall prova = new APICall("Pontedera","&units=metric");
		String indirizzo = "http://api.openweathermap.org/data/2.5/forecast?q=" +prova.getNomeCitta() +"&appid=" +prova.getKey() +prova.getUnita();
		
		URL url = new URL (indirizzo);
		
		try {
			URLConnection openConnection = new URL(indirizzo).openConnection();
			InputStream in = openConnection.getInputStream();
			
			String data = "";
			String line = "";
			try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
			   }
			} 
			finally 
			{
			   in.close();
			}
			
				this.jo = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object
				//System.out.println("JSONObject scaricato: "+ jo);
			/*} else {
				ja = (JSONArray) JSONValue.parseWithException(data);	//parse JSON Array
				System.out.println("JSONArray scaricato: "+ja);
				System.out.println("IL JSONArray scaricato ha "+ ja.size()+" elementi:");
			
				for(int i=0;i<ja.size();i++) {
					jo = (JSONObject) ja.get(i);
					System.out.println(i+") "+jo.get("name"));
				}
			}*/

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	
	public void forecastNome(String nomeCitta) throws MalformedURLException
	{
		call.setNomeCitta(nomeCitta);
		//System.out.println(call.getNomeCitta());
		
		String indirizzo = "http://api.openweathermap.org/data/2.5/forecast?q="+ call.getNomeCitta() +"&appid="+ call.getKey() +call.getUnita();
		//System.out.println(indirizzo);
		
		URL url = new URL (indirizzo);
		
		try {
			URLConnection openConnection = new URL(indirizzo).openConnection();
			InputStream in = openConnection.getInputStream();
			
			String data = "";
			String line = "";
			try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
			   }
			} 
			finally 
			{
			   in.close();
			}
			
				jo = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object
				//System.out.println("JSONObject scaricato: "+ jo);
			/*} else {
				ja = (JSONArray) JSONValue.parseWithException(data);	//parse JSON Array
				System.out.println("JSONArray scaricato: "+ja);
				System.out.println("IL JSONArray scaricato ha "+ ja.size()+" elementi:");
			
				for(int i=0;i<ja.size();i++) {
					jo = (JSONObject) ja.get(i);
					System.out.println(i+") "+jo.get("name"));
				}
			}*/

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		call.setCast(jo);
		System.out.println("FATTO CHIAMATA NOME");
		//return jo;
		
	}
	
	
	
	public JSONObject forecastID(String nomeCitta) throws MalformedURLException
	{
		
		nameToID(nomeCitta);
		
		String indirizzo = "http://api.openweathermap.org/data/2.5/forecast?id="+ call.getId() +"&appid="+ call.getKey() +call.getUnita();
		System.out.println(indirizzo);
		
		
		URL url = new URL (indirizzo);
		
		try {
			URLConnection openConnection = new URL(indirizzo).openConnection();
			InputStream in = openConnection.getInputStream();
			
			String data = "";
			String line = "";
			try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
			   }
			} 
			finally 
			{
			   in.close();
			}
			
		jo = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("FATTO CHIAMATA ID");
		
		return jo;
	}
}

	//per il prossimo futuro
	/*
	 * for(int i=0;i<this.ja.size();i++) {
					JSONObject jo = (JSONObject) this.ja.get(i);
					System.out.println(i+") "+jo.get("title"));
				}
	 */
	

