package components;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Renamer {
	public static void rename(ArrayList<String> names, File[] fileList) {
		Arrays.sort(fileList);
		int skipFile = 0;
		for (int i = 0; i < fileList.length && i < names.size() + skipFile; i++) {
			if (fileList[i].isHidden()) {
				skipFile++;
			}
			else {
				String path = fileList[i].getPath();
				String extension = path.substring(path.indexOf("."));
				File f1 = new File(path);
				System.out.println("f1: " + f1.getPath());
				File f2 = new File(fileList[i].getParent() + "\\" + names.get(i - skipFile + 1 ) + extension);
				System.out.println("f2: " + f2.getPath());
				System.out.println(f1.renameTo(f2));
			}
			

		}
	}
}
