/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dropboxfolder;

import java.io.Serializable;

/**
 *
 * @author Mateusz
 */
public class DropboxKey implements Serializable{
    private String name, APP_KEY, SECRET, token;
    DropboxKey(String name, String APP_KEY, String SECRET, String token){
    this.name=name;
    this.APP_KEY=APP_KEY;
    this.SECRET=SECRET;
    this.token=token;
}
    DropboxKey(String APP_KEY, String SECRET, String token){
    this.APP_KEY=APP_KEY;
    this.SECRET=SECRET;
    this.token=token;
}

    public String getName() {
        return name;
    }

    public String getAPP_KEY() {
        return APP_KEY;
    }

    public String getSECRET() {
        return SECRET;
    }

    public String getToken() {
        return token;
    }
    
}
