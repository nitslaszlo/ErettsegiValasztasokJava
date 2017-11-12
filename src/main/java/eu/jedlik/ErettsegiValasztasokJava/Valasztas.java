package eu.jedlik.ErettsegiValasztasokJava;

import java.awt.Label;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Valasztas {

	public Valasztas() {

		ArrayList<Jeloles> j = new ArrayList<Jeloles>();

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Választás - ");
		f.setVisible(true);

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.setVisible(true);
		f.add(p);

		p.add(new Label("1. feladat: Beolvas - szavazatok.txt"));
		try {
			for (String i : Files.readAllLines(Paths.get("szavazatok.txt"))) {
				j.add(new Jeloles(i.split(" ")));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(f, e.getMessage());
		}

		// 2. feladat - adatok száma
		p.add(new Label("2. feladat: A helyhatósági választáson " + j.size() + " képviselőjelölt indult."));

		// 3. feladat - keresés
		f.pack();
		p.add(new Label("3. feladat"));
		String név = JOptionPane.showInputDialog("Adja meg a képviselő vezeték és keresztnevét: ");
		Jeloles tmp = new Jeloles(String.format("0 0 %s -", név).split(" "));
		if (!j.contains(tmp)) {
			p.add(new Label("Ilyen nevű képviselőjelölt nem szerepel a nyilvántartásban!"));
		} else {
			int index = j.indexOf(tmp);
			p.add(new Label("A képviselő " + j.get(index).getSzavazatokSzama() + " szavazatot kapott"));
		}

		int szum = 0;
		for (Jeloles i : j)
			szum += i.getSzavazatokSzama();
		p.add(new Label(String.format("4. feladat: A választason %s állampolgar, a jogosultak %.2f%%-a vett részt.",
				szum, 100.0 * szum / 12345)));

		p.add(new Label("5. feladat: Pártonkénti eredmények"));
		int[] pSzum = new int[5];
		for (Jeloles i : j) {
			pSzum[i.getPartJele().ordinal()] += i.getSzavazatokSzama();
		}
		p.add(new Label(String.format("Gyümölcsevők Pártja = %.2f%%", 100.0 * pSzum[0] / szum)));
		p.add(new Label(String.format("Húsevők Pártja = %.2f%%", 100.0 * pSzum[1] / szum)));
		p.add(new Label(String.format("Tejivók Szövetsége = %.2f%%", 100.0 * pSzum[2] / szum)));
		p.add(new Label(String.format("Zöldségevők Pártja = %.2f%%", 100.0 * pSzum[3] / szum)));
		p.add(new Label(String.format("Független Jelöltek = %.2f%%", 100.0 * pSzum[4] / szum)));

		p.add(new Label("6. feladat: A legtöbb szavazatot szerző képviselők"));
		int maxSzavazat = Collections.max(j).getSzavazatokSzama();
		for (Jeloles i : j) {
			if (i.getSzavazatokSzama() == maxSzavazat) {
				p.add(new Label("     " + i.getNev() + "-" + i.getPartJele()));
			}
		}

		p.add(new Label("7. feladat: Kiír - kepviselok.txt"));
		int[] körzetMaxSzavazat = new int[9];
		int[] körzetGyőztesIndexe = new int[9];
		for (int i=0; i <j.size(); i++) {
			if (j.get(i).getSzavazatokSzama() > körzetMaxSzavazat[j.get(i).getKorzet()]) {
				körzetMaxSzavazat[j.get(i).getKorzet()] = j.get(i).getSzavazatokSzama();
				körzetGyőztesIndexe[j.get(i).getKorzet()] = i;
			}
		}

		ArrayList<String> ki = new ArrayList<String>();
		for (int i = 1; i < körzetGyőztesIndexe.length; i++) {
			ki.add(j.get(körzetGyőztesIndexe[i]).txtEgySor(i));
		}
		try {
			Files.write(Paths.get("kepviselok.txt"), ki);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(f, e.getMessage());
		}
		f.pack();
	}
}