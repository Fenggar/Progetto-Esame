package ProgettoEsame.MeteoRite.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import JSONHandler.JSONgest;  

public class GestioneFile 
{
	/**
	 * Questo metodo salva un JSONObject su file.
	 * Viene richiamato dalla rotta "/save"
	 * 
	 * @param nome_file Questo parametro contiene il nome che si vuole dare al file .txt che verrà creato.
	 * @param jo Questo parametro contiene il JSONObject con le previsioni che verrà stampato.
	 * @throws IOException
	 */
	public void salvaFile(String nome_file, JSONObject jo) throws IOException 
	{
		try {
		FileWriter file = new FileWriter(nome_file+ ".json");
	
		//rivedere questo toString, forse cambia il formato da json a txt		
		//cambiato toJSONString in toString, non so la differenza ma pare uguale.
            file.write(jo.toString());
            file.flush();
	}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/*possibile bug:
	 * siccome non c'è un modo migliore per prendere la data (o almeno google non lo sa), memorizziamo un solo char.
	 * questo non crea problemi per 5 mesi all'anno (una cifra consistente).
	 * nei restanti mesi, nelle notti di luna piena, quando si arriva alla fine del mese essendoci un 31 ed un 01. 
	 * Per ora questa è un'eventualità che non si verificherà perchè l'esame è il 25.
	 * Ne riparleremo al prossimo appello.
	 */
	
	/**
	 * Questo metodo salva su file solo alcune parti del JSONObject con le previsioni.
	 * @param nome_file
	 * @param jo
	 * @throws IOException
	 * @throws ParseException
	 */
	public void salvaEssenziale(String nome_file, JSONObject jo) throws IOException, ParseException
	{
		JSONgest g = new JSONgest();
		JSONObject box = new JSONObject();
		try 
		{
			FileWriter file = new FileWriter(nome_file+ ".json");
		
			JSONArray jarr = (JSONArray) jo.get("list");
			
			JSONArray ja = g.arrayLoader(jarr);
			JSONArray arr[] = new JSONArray[5];
//c'è un problema con l'array. non carica per niente arr[].
//dice solo che arr[j] è null
//testare array di jsonarray
			//non ho capito ancora perchè; ripercorrere dall'inizio, aggiungere print per capire cosa diventa cosa.
			//comunque per semplicità la funzione potrebbe non ritornare l'array.
			arr = g.dataFilter(ja,arr);
			
			//salvo i valori per il primo confornto
		
			//j =0;
			JSONArray fin = new JSONArray();
			fin = g.finalArrayLoader(arr);
			
			for(int k = 0; k < fin.size(); k++)
			{
				box = (JSONObject) fin.get(k);
				file.append(box.toJSONString());
			}
			
	    file.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Questo è un test per la lettura di un file.
	 * Funaiona ma non serve a niente, infatti è usato solo in un test.
	 * @param nome
	 */
	public void leggiFileTest(String nome) 
	{
		String data = "";
		String line = "";
		try
		{
			Scanner file_input = new Scanner(new BufferedReader(new FileReader(nome)));	  
			String str = file_input.nextLine();
			System.out.println(str);
			
			file_input.close();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	

	/**
	 * Altro test per la lettura di un file.
	 * Neanche questo serve effettivamente.
	 * @param nome
	 */
	public void leggiFile(String nome) 
	{
		/*
		 * STRING TO JSON
		 * 
		 * String nome = "prova";
		JSONParser parser = new JSONParser();
		JSONObject ogg = (JSONObject) parser.arse(nome);
		
		*/
		
		try {
			Scanner file_input = new Scanner(new BufferedReader(new FileReader(nome)));	  
			String str = file_input.nextLine();
			System.out.println(str);
			
			
			
			JSONObject jo;
			jo = (JSONObject) JSONValue.parseWithException(str);	 //parse JSON Object
			System.out.println("JSONObject letto: "+ jo);
			
			file_input.close();
			
		}
		catch (IOException e) //| ParseException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	
}
		
		
		/*try {
			PrintWriter file_output = new PrintWriter(new BufferedWriter(new FileWriter(nome_file)));
			
				file_output.println(jo);
			
			file_output.close();
			System.out.println("File salvato!");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		}
		
		*/
		
	



