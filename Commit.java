import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.ArrayList;

import org.junit.Test;

public class Commit
{
/*A prerequisite of a commit is that you need a Tree's SHA1 file location.  In order to get this you you must...
create a Tree object and save it to the objects folder
(Trees can be blank and have no entries for this part of the code)

A Commit also has a few other entries
String 'summary'
String 'author'
String 'date'

A commit constructor takes an optional String of the SHA1 of a parent Commit, and two Strings for author and summary

A commit contains a method to generate a SHA1 String
The inputs for the SHA1 are the SUBSET OF FILE CONTENTS file:
Summary, Date, Author, Tree, and (possibly null) pointer to parent Commit file
(so all the file contents except line 3)

Has a method getDate()
It gets the date as a String in whatever format you like

Has a method to create a Tree which is used in the constructor
Returns the SHA1 of the Tree*/
    String summary, author, date, parent, sha, commitContentSha, commitName;
    File commit;

    public Commit (String parent, String author, String summary) throws Throwable
    {   
       
        String shaOfCommitsTree = createTree();

        //entry is coming from the index file;
        //if the commit has a previous commit it has to have a pointer from the previous tree to the current tree, 
        //commit's tree has the files
        this.summary = summary;
        this.author = author;
        date = "";
        this.parent = parent;
        File path = new File ("objects");
        path.mkdirs();

        String contents = shaOfCommitsTree + "\n" + this.parent + "\n" + "\n" + author + "\n" + date + "\n" + summary;
        String commitName = Blob.encryptPassword (contents);

        File theCommit = new File ("objects/", commitName);
        BufferedWriter bw = new BufferedWriter (new FileWriter ("objects/" + commitName));
            bw.write (contents);
            bw.close();

        //what are you doing with stream


        

        //File commit = new File("Commit");







    }


    public String getCommitName ()
    {
        return commitName;
    }





    public String createTree() throws Throwable
    {

        Tree tree = new Tree();
        String indexContents = Blob.fileToString ("index");
        
        




       if (indexContents.indexOf ("\n") != -1)
       {
        int indexOfNewLine = indexContents.indexOf ("\n");
        ArrayList <String> arrayOfLinesForTree = new ArrayList <String> ();
        
        
        while (indexOfNewLine != -1)
       {
            arrayOfLinesForTree.add (indexContents.substring (0, indexOfNewLine));
            indexContents = indexContents.substring (indexOfNewLine + 1);
            indexOfNewLine = indexContents.indexOf ("\n");

       }

         for (int i = 0; i < arrayOfLinesForTree.size(); i++)
         {
                tree.addLineToTree (arrayOfLinesForTree.get(i));
       }

        
        //I want the Tree's sha for the first line of the Commit


        //need to add previous tree

        if (parent != "" && parent != null)
        {
            BufferedReader prevCommitReader = new BufferedReader (new FileReader (parent));
            String previousTree = prevCommitReader.readLine();
            prevCommitReader.close();
            tree.addLineToTree (previousTree);
        }
        
    }
    return Tree.getCurrentFileName();
       
    }



    public String generateShaString() throws IOException
    {
        int line = 1;
        String contents = "";
        String sha1 = "";
        BufferedReader reader = new BufferedReader(new FileReader(""));
        while (reader.ready())
        {
            if (line == 3)
            {
                reader.readLine();
            }
            else
            {
                contents += reader.readLine();
            }
            line++;
        }
        reader.close();
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(contents.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public String getDate()
    {
        return date;
    }

    public void createCommit () throws Throwable
    {
        Tree tree = new Tree ();
        
        String indexContents = Blob.fileToString ("index");

        //String shaOfContents = Blob.encryptPassword (indexContents);
        



        tree.addLineToTree (indexContents);//contents of index file
        
        //clear out index contents

        Commit.clearTheFile("index");


        //Include an additional entry to the previous Tree


        //if the String parent is empty, this is the first commit, otherwise it's the name of the commit where you read the first line which is the tree of that commit
       
        if (parent.length() > 0)
        {
            BufferedReader br = new BufferedReader (new FileReader (parent));

            String shaPreviousContents = br.readLine();
            String add = "tree : " + shaPreviousContents;
            tree.addLineToTree (add);
            br.close();
        }

    }


    public static void clearTheFile(String fileName) throws IOException {
        FileWriter fwOb = new FileWriter(fileName, false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }








//Method to get Commit's Tree based on a Commit's SHA1

    public String getTreeName (String commitName) throws IOException
    {

        File file = new File (commitName);
        BufferedReader br = new BufferedReader (new FileReader (commitName));
        String treeName = br.readLine();
        br.close();
        return (treeName);

    }


    public void updatePreviousCommit () throws Throwable
    {
        //Line 3 of each Commit should be the SHA of the next commit.  
        //Now that another commit is created, the previous commit's file needs to be updated with the new commit.

        File currentC = new File (sha);
        BufferedReader br = new BufferedReader (new FileReader (sha));
        String prevSha = br.readLine();
        prevSha = br.readLine();

        File prev = new File (prevSha);

        String prevContents = Blob.fileToString (prevSha);
        int index = prevContents.indexOf ("\n\n");

        Commit.clearTheFile (prevSha);

        try (BufferedWriter writer = new BufferedWriter (new FileWriter (prevSha, true)))
        {
            writer.write (prevContents.substring (0, index + 2));
            writer.write (sha);
            writer.write (prevContents.substring (index + 4));

        }
         catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }




}

