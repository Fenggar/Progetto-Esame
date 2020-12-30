package ProgettoEsame.MeteoRite.Controller;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ProgettoEsame.MeteoRite.Model.APICall;
import ProgettoEsame.MeteoRite.Model.Prova;

@RestController
public class RestControllerTest {
	//per il momento stampa un oggetto di prova e cambia il nome della città con quello che viene usato nella chiamata. utile insomma.
	@GetMapping("/prova")
	public Prova metodoProva(@RequestParam(name="citta", defaultValue="Pandoiano") String parametro)
	{
		return new Prova(parametro);
	}
	@PostMapping("/test") //converte il json che metto su postman in un oggetti di Prova
	public Prova provaPost(@RequestBody Prova body)
	{
		return body;
	}
	
	@GetMapping("/url") 
	public void urlTest(@RequestParam(name="citta", defaultValue = "Ponsacco") String par) throws MalformedURLException, IOException
	{
		APICall prova = new APICall("Pontedera","&units=metric");
		String indirizzo = "http://api.openweathermap.org/data/2.5/forecast?q=" +prova.getNomeCitta() +"&appid=" +prova.getKey() +prova.getUnita();
		
		URL url = new URL (indirizzo);
		
		JSONObject jo = null;
		JSONArray ja = null;
		
		
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
			} finally {
			   in.close();
			}
			//System.out.println("Dati scaricati: "+data);
			//if(isObject) {
				jo = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object
				System.out.println("JSONObject scaricato: "+ jo);
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
	}
		
		
		
	
	
		
		
		
		
		
}
	

