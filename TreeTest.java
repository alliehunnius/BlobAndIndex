import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TreeTest 
{
    private static Tree tree;
    public TreeTest() throws Throwable
    {
        tree = new Tree();
    }

    @Test
    void testRemoveTree() throws Throwable 
    {
        tree.addLineToTree("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree.addLineToTree("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");

        tree.removefromTree("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        
        String expected = "blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt";

        File file = new File("objects", "bc323153dcce17da2a8cd62cb240abdc49f3fe7b"); //SHA1 of the content
        assertTrue(file.exists());
        assertEquals(expected, tree.content());
    }

    @Test
    void testAddTree() throws Throwable 
    {
        tree.addLineTree("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree.addLineTree("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        
        String expected = "blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt" + "\n" + 
        "tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b";

        File file = new File("objects", "80aaaaaea78ef9525bf854dcb1d60e2abe087221"); //SHA1 of the content
        assertTrue(file.exists());
        assertEquals(expected, tree.content());
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
    void testDirectory () throws Throwable 
    {
        File tester = new File("directory");
        File file1 = new File ("directory", "file1");
        File file2 = new File ("directory", "file2");
        File file3 = new File ("directory", "file3");
        Tree tree = new Tree ();
        tree.addDirectory ("newDirectory");
        File file = new File ("newDirectory");
        assertTrue (file.exists());
    }

    @Test
    void testDirectory2 () throws Throwable
    {
        File tester = new File("directory");
        File file1 = new File ("directory", "file1");
        File file2 = new File ("directory", "file2");
        File file3 = new File ("directory", "file3");
        File folder1 = new File ("folder1");
        File folder2 = new File ("folder2");
        tree.addDirectory ("newDirectory");
        File file = new File ("newDirectory");
        assertTrue (file.exists());
        //create a file in the inner folders?

    }
}
