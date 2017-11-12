package eu.jedlik.ErettsegiValasztasokJava;

enum Partok {GYEP, HEP, TISZ, ZEP, Fuggetlen;}

public class Jeloles  implements Comparable<Jeloles>{

	private int Korzet;
	private Integer SzavazatokSzama;
	private String Vezeteknev;
	private String Kereszteknev;
	private Partok PartJele;
	
	public int getKorzet()
	{
		return this.Korzet;
	}
	
	public Integer getSzavazatokSzama(){
		return this.SzavazatokSzama;
	}

	public String getVezeteknev(){
		return this.Vezeteknev;
	}
	
	public String getKeresztnev(){
		return this.Kereszteknev;
	}
	
	public Partok getPartJele(){
		return this.PartJele;
	}
	
	public String getNev(){
		return this.Vezeteknev + " " + this.Kereszteknev;
	}
	
	public Jeloles(String[] m){
		Korzet = Integer.parseInt(m[0]);
		SzavazatokSzama = Integer.parseInt(m[1]);
		Vezeteknev = m[2];
		Kereszteknev = m[3];
		PartJele = Partok.valueOf(m[4].compareTo("-") == 0 ? "Fuggetlen" : m[4]);
	}

	public String txtEgySor(int körzet) {
		return String.format("%d. %s %s", körzet, this.getNev(), this.getPartJele().name());
	}

	@Override
	public boolean equals(Object object) {
		boolean egyenlő = false;
		if (object != null && object instanceof Jeloles)
		{
			Jeloles másik = (Jeloles) object;
			egyenlő = this.getNev().equals(másik.getNev());
		}
		return egyenlő;
	}
	
	public int compareTo(Jeloles f) {
		return this.SzavazatokSzama.compareTo(f.getSzavazatokSzama());
	}

}
