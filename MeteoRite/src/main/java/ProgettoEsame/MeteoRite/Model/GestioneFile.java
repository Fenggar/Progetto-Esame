package ProgettoEsame.MeteoRite.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Vector;

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
	 * Nota: usa write() e non append() sovrascrive ogni volta.
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
	
	/**
	 * Questo è un overload di salvaFile.
	 * Anzichè stampare un json, scrive su un file txt un double.
	 * 
	 * @param nome_file
	 * @param d
	 * @throws IOException
	 */
	public void salvaFile(String nome_file, Double d) throws IOException 
	{
		String accapo = "\n";
		try {
		FileWriter file = new FileWriter(nome_file+ ".txt");
	
		//rivedere questo toString, forse cambia il formato da json a txt		
		//cambiato toJSONString in toString, non so la differenza ma pare uguale.
            file.append(d.toString());
            file.append(accapo);
            file.flush();
	}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Questo metodo salva su file solo alcune parti del JSONObject con le previsioni.
	 * (solo le parti strettamente necessarie per generare le statistiche).
	 * 
	 * Viene richiamato dalla rotta /saveex
	 * 
	 * @param nome_file
	 * @param jo
	 * @throws IOException
	 * @throws ParseException
	 */
	public void salvaEssenziale(String nome_file, JSONObject jo, String nomeCitta) throws IOException, ParseException
	{
		JSONgest g = new JSONgest();
		JSONObject box = new JSONObject();
		Integer index = 0;
		
		JSONObject nc = new JSONObject();
		String accapo = "\n";
		
		try 
		{
			FileWriter file = new FileWriter(nome_file+ ".json");
			//nomeCitta+="\n";
			nc.put("city", nomeCitta);
			file.append(nc.toJSONString());
			file.append(accapo);
			
			JSONArray jarr = (JSONArray) jo.get("list");
			
			JSONArray ja = g.arrayLoader(jarr); //contiene temperature ogni 3 ore
			
			System.out.println("STAMPO JA (temperature ogni 3 ore):");
			g.arrayPrinter(ja);
			
			JSONArray arr = new JSONArray();
			
			JSONArray fin = new JSONArray(); //array dove metterò box con media
			
			for(int i = 0; i<5; i++)
			{
				//System.out.println("sono nel for di salvaEx");
				arr = g.dataFilter(ja, index); //a questo punto ho un solo array di box su arr. devo fare media
				index = g.getIndexLocal();
				//System.out.println("ho chiamato FILTER da GESTIONE, index = " +index);
				box = g.finalArrayLoader(arr); //contiene media di un intero arr
				//System.out.println("STAMPO DOPO FINALARRAYLOADER: "+i);
				//g.arrayPrinter(arr);
				fin.add(box);
				arr.clear();
				//System.out.println("Ho messo questo box in fin: "+box);
			}
			
			arrayWriter(file, fin); //se non funziona, fare scrittura jsonobject
			
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
			while(file_input.hasNext()) //file_input.hasNext(line)
			{
				String str = file_input.nextLine();
				System.out.println(str);
				data +=(str);
			}
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
	
	/**
	 * Questo metodo carica un JSONArray leggendo da file.
	 * 
	 * Chimato da statisticator();
	 * 
	 * @param nome Nome del file che leggerò
	 * @return restituisco l'array che ho letto
	 */
	public JSONArray caricaDaFile(String nome)
	{
		System.out.println("SONO SU CARICA FILE");
		
		JSONArray arr = new JSONArray();
		JSONObject jo = new JSONObject();
		
		Vector<String> vect = new Vector<String>();
		
		String data = "";
		String line = "";
		try
		{
			Scanner file_input = new Scanner(new BufferedReader(new FileReader(nome)));	  
			while(file_input.hasNext()) //file_input.hasNext(line)
			{
				String str = file_input.nextLine();
				System.out.println(str);
				
				vect.add(str);
				
				
				/*
				 * jo = (JSONObject) JSONValue.parseWithException(str);
				
				arr.add(jo);
				System.out.println("DOPO L'ADD, JO VALE: "+jo);
				for(int i = 0; i<arr.size();i++)
				{
					//jo = (JSONObject) arr.get(0);
					System.out.println("DOPO L'ADD ARR VALE: " +arr.get(i));
				}
				*/
				//jo.clear();
			}
			file_input.close();
			
			for(int i = 0; i<vect.size();i++)
			{
				jo = (JSONObject) JSONValue.parseWithException(vect.get(i));
				arr.add(jo);
				//System.out.println("DOPO L'ADD ARR VALE: " +arr.get(i));
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for(int i = 0; i<arr.size();i++)
		{
			jo = (JSONObject) arr.get(i);
			System.out.println("STAMPO ARRAY DI CARRICA FILE: "+i+") "+jo);
		}
		
		return arr;
	}
	
	/**
	 * Questo metodo stampa un array su un file.
	 * 
	 * @param file
	 * @param arr
	 * @throws IOException
	 */
	public void arrayWriter(FileWriter file, JSONArray arr) throws IOException
	{
		JSONObject box = null;
		String accapo = "\n";
		for(int k = 0; k < arr.size(); k++)
		{
			box = (JSONObject) arr.get(k);
			System.out.println("IL BOX CHE STO PER SCRIVERE NEL FILE: "+box);
			file.append(box.toJSONString());
			file.append(accapo);
		}
	
	}
}
		
	



