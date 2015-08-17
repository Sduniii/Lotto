package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import application.Main;
import tsdun.sdunzehmke.de.SduniRandom;

public class Core6Aus49 {
	
	public static ArrayList<String> get(SduniRandom rand, int count){
		ArrayList<String> result = new ArrayList<>();
		long zz = -1L;
		ArrayList<Long> zahlen = new ArrayList<Long>();
		for (int i = count; i > 0; i--) {
			for (int j = 6; j > 0; j--) {
				Long l = rand.next(1L, 49L);
				while (zahlen.contains(l)) {
					l = rand.next(1L, 49L);
				}
				zahlen.add(l);
			}
			Collections.sort(zahlen);
			if (zz == -1L) {
				zz = rand.next(0L, 9L);
			}
			zahlen.add(zz);
			ArrayList<String> num = new ArrayList<String>();
			try {
				File fle = new File("lottozahlen.csv");
				if(!fle.exists()){
					fle = new File(Main.class.getResource("lottozahlen.csv").toExternalForm());
				}
				BufferedReader br = new BufferedReader(new FileReader(fle));
				String line;
				while ((line = br.readLine()) != null) {
					String[] linee = line.trim().split(",");
					for (int j = 0; j < zahlen.size() - 2; j++) {
						for (int m = 2; m < linee.length - 3; m++) {
							if (String.valueOf(zahlen.get(j)).toLowerCase().equals(linee[m].trim().toLowerCase()) && !num.contains(linee[m])) {
								// System.out.print(linee[m] + ";");
								num.add(linee[m]);
								break;
							}
						}
						if (num.size() > 3) {
							num.add("---> " + line);
							break;
						}

					}
					if (num.size() > 3) {
						break;
					} else {
						num.clear();
					}
				}
				br.close();
				if (num.size() > 3) {
					i++;
					result.add(zahlen.toString() + System.lineSeparator()+"Zahlen schon enthalten: " + num.toString());
				} else {
					result.add(zahlen.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			zahlen.clear();
			num.clear();
		}
		
		return result;
	}

}
