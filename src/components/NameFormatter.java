package components;


import java.util.ArrayList;
import java.util.Scanner;

public class NameFormatter {
	public static ArrayList<String> Renamer(Scanner input) {
		ArrayList<String> names = new ArrayList<String>();
		while (input.hasNextLine()) {
			names.add(input.nextLine());
		}
		for (int i = 0; i < names.size(); i++) {
			String name = names.get(i);

			String TS = name.substring(0, name.indexOf(","));

			String lastName = name.substring(TS.length() + 1);
			lastName = lastName.substring(0, lastName.indexOf(","));

			String firstName = name.substring(TS.length() + lastName.length() + 2);
			firstName = firstName.substring(0, firstName.indexOf(","));

			String studentID = name.substring(TS.length() + lastName.length() + firstName.length() + 3);

			names.set(i, lastName + ", " + firstName + ", " + studentID);

		}
		return names;
	}
}
