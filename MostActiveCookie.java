import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MostActiveCookie {


/**
 *  In the main, we call on the helper functions to do the following:
 *  1. dataFile method: Read the file data into an ArrayList
 *  2. filterByDate method: Filter out all the cookies that aren't on the specified date
 *  3. Counter method: Create a hashmap that identifies how many times a cookie appears on the specific date
 *  4. frequentCookie method: Finally, we figure out the number of times the most frequent cookie appears, then we identify the cookies that appear at that frequency
 * 
*/

public static void main(String args[]) {

        // First ensure that arguments are correct
        if (args.length != 3 || !args[1].equals("-d")) 
            {
                System.out.println("Improper usage. Please input the following form: java mostActiveCookie <filename> -d <date>");
                return;
            }

        String fileName =  args[0];
        String date = args[2];

        // Call helper functions
        ArrayList<String> data = dataFile(fileName);
        ArrayList<String> data1 = filterByDate(data, date);
        HashMap<String, Integer> hmapData = Counter(data1);
        ArrayList<String> dataFinal = frequentCookie(hmapData);

            for (String bigC : dataFinal) {
                System.out.println(bigC);
            }
            

    }


    
      /***
     * Method to parse the file and create an arrayList output
     * Initializes an ArrayList, File, and Scanner
     */
     
    public static ArrayList<String> dataFile (String fileName) {


        // Initialize new scanner, and read and write values from input file into arrayList

       ArrayList<String> fileData = new ArrayList<>();
    try {
        File newFile = new File(fileName);
        Scanner s = new Scanner(newFile);
            while (s.hasNextLine()) 
                {
                    String line = s.nextLine();
                    fileData.add(line);
                }
            s.close();
       }
    
        // Throws a file not found error for an invalid user input
    catch (FileNotFoundException x) 
        {
            System.out.println("File name not found:" + fileName);
        }

        return fileData;
    }
   
        /***
         *  Method to filter out only the cookies from a select date
         *  Initializes an ArrayList, 2 SimpleDateFormats, then 2 strings for cookie and date
         */
  
        public static ArrayList<String> filterByDate(ArrayList <String> parsedData, String Date) {
            
            ArrayList<String> filteredByDate = new ArrayList<>();

            // Note, simpledateformats added due to parsing file filtering everything out
            SimpleDateFormat csvDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Read each entry line-by-line, splitting each entry into the cookie and data part
            // Then, we only add the cookies that match the inputted time stamp
           
            for (String entry : parsedData) {
                String [] parts = entry.split(",");
                String cookie = parts[0].trim();

                try {
                Date csvDate = csvDateFormat.parse(parts[1].trim().split(" ")[0]); 
                String formattedCsvDate = inputDateFormat.format(csvDate); 

                if (formattedCsvDate.equals(Date)) { 
                    filteredByDate.add(cookie); 
                }
                   }

                   catch (Exception e) {
                    
                }

             }
            
    
        return filteredByDate;

        }

        /***
         *  Helper function to create a HashMap that ties each cookie value to a counter
         *  Initializes a HashMap
         */

        public static HashMap<String, Integer> Counter(ArrayList<String> filteredDate) {
            
            HashMap <String, Integer> cookieCounter = new HashMap<>();

            // Iterates through the filteredData ArrayList, then either adds a new cookie
            // HashMap pair, or updates the counter for a cookie by +1

            for (String cookie : filteredDate) {
                cookieCounter.put(cookie, cookieCounter.getOrDefault(cookie, 0) + 1);
            }


        return cookieCounter;

        }
    
        /***
         * Final helper to iterate through the counted cookies HashMap and isolate the cookies with the most frequent timestamps
         * Initializes two counter variables and an ArrayList
         */
    
         public static ArrayList<String> frequentCookie(HashMap<String, Integer> countedCookie) {

            int maxCount=0;
            int count=0;
            ArrayList<String> bigCookie = new ArrayList<>();

            /***
             * 
             * Here, we're doing two loops; the first one is to find the highest cookie count value, the number of times the most frequent cookie appears
             * Once we have the max value, we iterate through to find all the values that have that highest count 
             * This is mainly to ensure that if there are multiple cookies, we are returning all of them
             * 
            */
           
           // Find the max count
            for (HashMap.Entry<String, Integer> entry : countedCookie.entrySet()) {
                count = entry.getValue();

                if (count > maxCount)
                    maxCount = count;
            }

            // With the max count, find all cookies that have a matching count value
                 for (HashMap.Entry<String, Integer> entry : countedCookie.entrySet()) {
                        int tally = entry.getValue();
                        
                        if (tally == maxCount) {
                            bigCookie.add(entry.getKey());
                         }
                 }

            return bigCookie;

         }
    
    
}
