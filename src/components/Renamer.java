package components;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Renamer {
	/*
	 * Sorts array of files alphabetically, then iterates through those files,
	 * skipping hidden files, and renames them based on the ArrayList of names
	 * (pulled from the CSV).
	 */
	public static void renameAll(ArrayList<String> names, File[] fileList) {
		Arrays.sort(fileList);
		int skipFile = 0;
		for (int i = 0; i < fileList.length && i < names.size() + skipFile; i++) {
			if (fileList[i].isHidden()) {
				skipFile++;
			} else {
				rename(names.get(i - skipFile + 1), fileList[i]);
			}

		}

	}
	// Renames one file. Used in the renameAll method.
	public static void rename(String name, File file) {
		String path = file.getPath();
		String extension = path.substring(path.indexOf("."));
		File f1 = new File(path);
		System.out.println("f1: " + f1.getPath());
		File f2 = new File(file.getParent() + "\\" + name + extension);
		System.out.println("f2: " + f2.getPath());
		System.out.println(f1.renameTo(f2));

	}
}
