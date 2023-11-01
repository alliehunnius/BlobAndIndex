import java.io.File;

public class manualTester {
    public static void main(String[] args) throws Throwable {


        //  File file = new File ("testAdd");
        // Index index = new Index ();
        // index.initialize();
        // index.addBlobs ("testAdd");
        // Commit commit = new Commit ("", "me", "testerCommit");
       
        
        // Index testDay3 = new Index();
        // testDay3.initialize();
        // testDay3.addBlobs("testAdd");
        // testDay3.addBlobs("secondAdd");
        // testDay3.removeBlobFromIndexFile ("testAdd");
        // testDay3.addBlobs ("thirdAdd");

        // Tree tree = new Tree();
        // tree.addToTree ("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        // tree.addToTree ("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        // tree.removeFromTree ("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        
        Tree tree = new Tree();
        tree.addLineToTree ("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        tree.addLineToTree ("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");




        Commit commit = new Commit ("", "me", "testerCommit");
        File file = new File ("/objects" + commit.getCommitName());

    }
}
 