package rdTapDecoderExample;
import java.io.FileReader;  
import com.opencsv.CSVReader;  

public class rdTapDecoderFromCSV {


	public static void main(String[] args) {
		CSVReader reader = null;  
		rdTapDecoder rd = new rdTapDecoder();

		try {  

			reader = new CSVReader(new FileReader(args[0]));  
			reader.readNextSilently();
			
			String [] nextLine;  
			//reads one line at a time  
			while ( (nextLine = reader.readNext() ) != null ) {  
//				System.out.println(nextLine[3]);

				System.out.print(nextLine[0] + ", ");
				rd.decode(1,nextLine[3]);;
				
				System.out.print("\n");  
			}  
		}  catch (Exception e) {  
			e.printStackTrace();  
		}  

//		new rdTapDecoder().rdTapDecoderInstance(args[0]);

	}

}
