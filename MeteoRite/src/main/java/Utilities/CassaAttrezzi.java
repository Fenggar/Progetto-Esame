package Utilities;

public class CassaAttrezzi 
{
	/**
	 * Questo metodo serve per troncare il double dato come risultato dalla media. 
	 * E' necessario perchè a quanto pare in questo linguaggio non esiste un modo per decidere quanti decimali hanno i double.
	 * 
	 * @param media Double che contiene il valore da arrotondare
	 * @param m Stringa su cui memorizzo il double (in realtà non è poi strettamente necessaria)
	 * @return
	 */
	public String arrotondatore(double media, String m)
	{
		String ret ="";
		m = Double.toString(media);
		
		char[] sparta = new char[4];
		
		for(int i = 0; i<4 && i<m.length(); i++)
		{
			sparta[i] = m.charAt(i);
			ret += sparta[i];
		}
		
		return ret;
	}

}
