package nosql.aislike.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable{
	private static final long serialVersionUID = 5190927946812430075L;
	private Long id;
	private String meno;
	private String priezvisko;
	private char kodPohlavie;
	private String skratkaAkadTitul;
	private List<Studium> studium = new ArrayList<Studium>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMeno() {
		return meno;
	}
	public void setMeno(String meno) {
		this.meno = meno;
	}
	public String getPriezvisko() {
		return priezvisko;
	}
	public void setPriezvisko(String priezvisko) {
		this.priezvisko = priezvisko;
	}
	public char getKodPohlavie() {
		return kodPohlavie;
	}
	public void setKodPohlavie(char kodPohlavie) {
		this.kodPohlavie = kodPohlavie;
	}
	public String getSkratkaAkadTitul() {
		return skratkaAkadTitul;
	}
	public void setSkratkaAkadTitul(String skratkaAkadTitul) {
		this.skratkaAkadTitul = skratkaAkadTitul;
	}
	public List<Studium> getStudium() {
		return studium;
	}
	public void setStudium(List<Studium> studium) {
		this.studium = studium;
	}
	
	
}
