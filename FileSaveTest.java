import java.io.FileReader;
import java.io.IOException;

public class FileSaveTest {

	public static void main(String[] args) {
		FileReader fr;
		String[] text = null;
		int heading = 0;
		
		try {
			//read the text of the file and store it
			fr = new FileReader("F:\\DebugTest.txt");
			text = readAllLines(fr).split("\n");
		}
		catch(IOException e) {
			//catch any errors and send them to the console
			System.out.println(e.getMessage());
		}
		
		//if we successfully got the contents of the file extract the heading from them
		if(text != null) {
			try {
				heading = Integer.parseInt(text[1]);
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println(text.length);
				System.out.println(e);
				System.out.println(e.toString());
				System.out.println(e.getLocalizedMessage());
			}
		}
		System.out.println(heading);
	}

	public static String readAllLines(FileReader fr) throws IOException {
		String str = "";
		int i;
		while((i=fr.read()) != -1) {
			str+=(char)i;
		}
		return str;
	}
}
