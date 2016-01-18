/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dropboxfolder;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mateusz
 */
public class Send implements Runnable{
    private File file;
    private DbxClient client;
    private AtomicInteger numberOfFiles;
    private String directPath;
    void addFile(String directPath, File file,DbxClient client,AtomicInteger numberOfFiles){
        this.file = file;
        this.client=client;
        this.numberOfFiles=numberOfFiles;
        this.directPath = directPath;
    }
    @Override
    public void run() {
                FileInputStream inputStream=null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            DbxEntry.File uploadedFile = client.uploadFile(directPath+"/"+file.getName(),
                DbxWriteMode.add(), file.length(), inputStream);
            numberOfFiles.getAndIncrement();
        } catch (DbxException | IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                    try {
                        inputStream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }
        file.delete();
    }
    
}
