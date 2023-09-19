import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertFalse;

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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IndexTest 
{   
    private static Index index;
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

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        /*
         * Utils.deleteFile("junit_example_file_data.txt");
         * Utils.deleteFile("index");
         * Utils.deleteDirectory("objects");
         */
    }
    public IndexTest() throws Throwable
    {
        index = new Index();
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


    @Test
    void testAddBlobs() throws Throwable 
    {
        index.initialize();
        index.addBlobs("file1");

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

    @Test
    @DisplayName("Testing index File and 'object' Folder Creation")
    void testInitialize() throws IOException 
    {
        //Index index = new Index();
        index.initialize();

        File file = new File("index");
        Path path = Paths.get("objects");
        //assertTrue(file.isDirectory());

        assertTrue(file.exists());
        assertTrue(Files.exists(path));
    }

    @Test
    void existInIndex() throws Throwable
    {
        index.addBlobs("file1");

        File file = new File("file1");
        String content = "something is here";
        assertTrue(file.exists()); 
        assertEquals(content, readFile("file1")); 
    }

    @Test
    void testRemoveBlob() throws Throwable {

        index.addBlobs("file1");
        index.removeBlob("file1");

        File file = new File("objects", "53d45fe9bb51b94c43b04b6fcbc0d8aa874c9ed6");
        
        assertTrue(!file.exists()); 
    }

    //Adding a File to the index given a filename
    @Test
    void checkIndexAdd() throws Throwable
    {
        index.addBlobs("file1");
        String actual = readFile("index");
        String expected = "file1" + " : " + "53d45fe9bb51b94c43b04b6fcbc0d8aa874c9ed6" + "\n"; //SHA1 of the content in "file1"
        assertEquals(expected, actual);
    }

    @Test
    void checkIndexRemove() throws Throwable
    {
        index.addBlobs("file1");
        index.removeBlob("file1");

        String actual = readFile("index");
        String expected = ""; 
        assertEquals(expected, actual);
    }
}