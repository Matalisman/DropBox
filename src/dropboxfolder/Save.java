package dropboxfolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Save {
    private ArrayList<DropboxKey> keyList = new ArrayList();
    Save(DropboxKey key){
        Read readKeyList = new Read();
        this.keyList=readKeyList.getKeyList();
        keyList.add(key);
        this.Write();
    }
    private void Write(){
        ObjectOutputStream Zapis = null;
        try {
            Zapis = new ObjectOutputStream(new FileOutputStream("DropboxKey.dat"));
        } catch (IOException ex) {
        }
        try {
            Zapis.writeObject(keyList);
        } catch (IOException ex) {
        }
    }
}
