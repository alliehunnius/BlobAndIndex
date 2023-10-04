import java.io.BufferedReader;
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
    String summary, author, date, parent, sha;
    File commit;

    public Commit (String parent, String author, String summary) throws Throwable
    {
        Tree tree = new Tree();
        tree.addTree("");
        sha = tree.getCurrentFileName();
        this.summary = summary;
        this.author = author;
        date = "";
        this.parent = parent;
        File path = new File ("objects");
        path.mkdirs();
        FileOutputStream stream = new FileOutputStream(new File(path , "Commit"));
        //File commit = new File("Commit");
    }








    public void generateShaString() throws IOException
    {
        int line = 1;
        String contents = "";
        String sha1;
        BufferedReader reader = new BufferedReader(new FileReader("Commit"));
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
        //return sha1;
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
        



        tree.addTree (indexContents);//contents of index file
        
        //clear out index contents

        Commit.clearTheFile();


        //Include an additional entry to the previous Tree


        //if the String parent is empty, this is the first commit, otherwise it's the name of the commit where you read the first line which is the tree of that commit
       
        if (parent.length() > 0)
        {
            BufferedReader br = new BufferedReader (new FileReader (parent));

            String shaPreviousContents = br.readLine();
            String add = "tree : " + shaPreviousContents;
            tree.addTree (add);
            br.close();
        }

    }


    public static void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter("index", false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }








//Method to get Commit's Tree based on a Commit's SHA1

    public String getTreeSha (String shaCommit) throws IOException
    {

        File file = new File (shaCommit);
        BufferedReader br = new BufferedReader (new FileReader (shaCommit));
        String treeSha = br.readLine();
        br.close();
        return (treeSha);

    }



















}