package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import application.Main;
import tsdun.sdunzehmke.de.SduniRandom;

public class CoreEuro {

	public static ArrayList<String> get(SduniRandom rand, int count, int anz, boolean debug) {

		try {
			ArrayList<String> result = new ArrayList<>();
			ArrayList<Long> zahlen = new ArrayList<Long>();
			 ArrayList<String> buffer = new ArrayList<String>();

			BufferedReader br;
			File fle = new File("eurojackpot.csv");
			if (!fle.exists()) {
				br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("eurojackpot.csv")));
			} else {
				br = new BufferedReader(new FileReader(fle));			
			}
			String line;
			while ((line = br.readLine()) != null) {
				buffer.add(line);			
			}
			br.close();
			
			for (int i = count; i > 0; i--) {
				for (int j = 5; j > 0; j--) {
					Long l = rand.next(1L, 50L);
					while (zahlen.contains(l)) {
						l = rand.next(1L, 50L);
					}
					zahlen.add(l);
				}
				Collections.sort(zahlen);
				ArrayList<Long> tmp = new ArrayList<Long>();
				for (int m = 2; m > 0; m--) {
					Long l = rand.next(1L, 10L);
					while (tmp.contains(l)) {
						l = rand.next(1L, 10L);
					}
					tmp.add(l);
				}
				Collections.sort(zahlen);
				zahlen.addAll(tmp);
				ArrayList<String> num = new ArrayList<String>();

				int h = 0;
				for(String ln : buffer){
					if (h == 0 && debug) {
						result.add(ln);
						h++;
					}
					String[] linee = ln.trim().split(";");
					for (int j = 0; j < zahlen.size() - 3; j++) {
						for (int m = 2; m < linee.length - 3; m++) {
							if (String.valueOf(zahlen.get(j)).toLowerCase().equals(linee[m].trim().toLowerCase()) && !num.contains(linee[m])) {
								// System.out.print(linee[m] + ";");
								num.add(linee[m]);
								break;
							}
						}
						if (num.size() > anz-1) {
							num.add("---> " + ln);
							break;
						}

					}
					if (num.size() > anz-1) {
						break;
					} else {
						num.clear();
					}
				}
				if (num.size() > anz-1) {
					i++;
					result.add(zahlen.toString() + System.lineSeparator() + "Zahlen schon enthalten: " + num.toString());
				} else {
					result.add(zahlen.toString());
				}
				zahlen.clear();
				num.clear();

			}
			buffer.clear();
			return result;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}

