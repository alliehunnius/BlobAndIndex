import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class CommitTest 
{
    // public CommitTest(String parent, String author, String summary) throws Throwable
    // {
    //     commit = new Commit(parent, author, summary);
    // }

    @Test
    public void createOneCommit() throws Throwable {
        String fileName = "testAdd";
        File file = new File (fileName);
        Index index = new Index();
        index.initialize();
        index.addBlobs (fileName);
        Commit test = new Commit ("", "me", "test");
        File newFile = new File ("/objects" + test.getCommitName());
        assertTrue (newFile.exists());
    }

    @Test
    public void createTwoCommit() throws Throwable {
        Commit test = new Commit ("", "me", "firstTest");
        File file = new File ("/objects" + test.getCommitName());

        Commit second = new Commit (test.getCommitName(), "me", "secondTest");
        File file2 = new File ("/objects" + second.getCommitName());

        assertTrue (file.exists());
        assertTrue (file2.exists());

        String testContents = Blob.fileToString (test.getCommitName());
        String secondContents = Blob.fileToString (second.getCommitName());
        assertTrue (testContents.contains (second.getCommitName()));
        assertTrue (secondContents.contains (test.getCommitName()));

    }
    
    @Test
    public void createFourCommit() throws Throwable {

        Commit first = new Commit ("", "me","firstTest");
        String firstName = first.getCommitName();
        File file1 = new File ("/objects" + firstName);
        
        Commit second = new Commit (firstName, "me", "secondTest");
        String secondName = second.getCommitName();
        File file2 = new File ("/objects" + secondName);

        Commit third = new Commit (secondName, "me", "thirdTest");
        String thirdName = third.getCommitName();
        File file3 = new File ("/objects" + thirdName);

        Commit fourth = new Commit (thirdName, "me", "fourthTest");
        String fourthName = fourth.getCommitName();
        File file4 = new File ("/objects" + fourthName);

        assertTrue (file1.exists());
        assertTrue (file2.exists());
        assertTrue (file3.exists());
        assertTrue (file4.exists());

        String firstContents = Blob.fileToString (firstName);
        String secondContents = Blob.fileToString (secondName);
        String thirdContents = Blob.fileToString (thirdName);
        String fourthContents = Blob.fileToString (fourthName);

        assertTrue (firstContents.contains (secondName));
        assertTrue (secondContents.contains (firstName));

        assertTrue (secondContents.contains (thirdName));
        assertTrue (thirdContents.contains (secondName));
        
        assertTrue (thirdContents.contains (fourthName));
        assertTrue (fourthContents.contains (thirdName));

    }


    //(Make sure the tests clean up their files and folder state properly)

}
