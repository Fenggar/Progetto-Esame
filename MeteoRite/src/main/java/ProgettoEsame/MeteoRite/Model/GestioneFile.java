package ProgettoEsame.MeteoRite.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class GestioneFile 
{
	
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
		
	



