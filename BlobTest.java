import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BlobTest 
{
    private static Blob blob;

@BeforeAll
    static void setUpBeforeClass() throws Exception 
    {
        /*
         * Utils.writeStringToFile("junit_example_file_data.txt", "test file contents");
         * Utils.deleteFile("index");
         * Utils.deleteDirectory("objects");
         */
        createFile("file1", "something is here");
        createFile("file2", "something is also here");
    }

    private static void createFile(String fileName, String content) throws IOException
    {
        File f = new File(fileName); 
        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        fw.write(content);
        fw.close();
    }
    private String readFile(String fileName) throws IOException 
    {
		BufferedReader br = new BufferedReader(new FileReader(fileName)); // the name of the file that want to read
		try {
			String string = "";
			while (br.ready()) {
				string += (char) br.read(); // read the char in the file, store to a string
			}
			br.close();
			return string; // return the string
		} catch (FileNotFoundException e) // if the file name is not found
		{
			return "File not found, whoops!";
		}
    }


    public BlobTest() throws Throwable
    {
        blob = new Blob("file1");
    }

    @Test
    void testEncryptPassword() throws IOException {
        String content = readFile("file1");
        String actual = Blob.encryptPassword(content);
        String expected = "53d45fe9bb51b94c43b04b6fcbc0d8aa874c9ed6"; //actual content: something is here
        assertEquals(expected, actual);
    }

    @Test
    void testFileToString() throws Throwable {
        String expected = Blob.fileToString("file1");
        String actual = "something is here";
        assertEquals(expected, actual);
    }

    @Test
    void testOriginalName() {
        String actual = blob.originalName();
        String expected = "file1";
        assertEquals(expected, actual);
    }

    @Test
    void testStringToFile() throws IOException {

        Blob.stringToFile("something is here", "file1");
        
        String expected = "something is here";
        File file = new File("objects", "53d45fe9bb51b94c43b04b6fcbc0d8aa874c9ed6"); //SHA1 of the content
        Path filePath = Paths.get("objects", "53d45fe9bb51b94c43b04b6fcbc0d8aa874c9ed6");
        List<String> temp = Files.readAllLines(filePath);
        String actual = "";
        for (String c : temp) 
        {
            actual += c;
        }
        assertTrue(file.exists());
        assertEquals(expected, actual);
    }
}
