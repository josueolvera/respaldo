package mx.bidg.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Desarrollador on 23/02/2017.
 */
public class FtpConnect {

    FTPClient ftpClient = null;

    public FtpConnect() throws Exception{
        ftpClient = new FTPClient();
    }

    public void connect(String host, String user, String password) throws Exception{
       this.ftpClient.connect(host);
        boolean login = this.ftpClient.login(user,password);
        if (login){
            System.out.println("Si conecto!!!!!!!!!");
            this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            this.ftpClient.enterLocalPassiveMode();
        }
    }

    public void disconnect() {
        if (this.ftpClient.isConnected()) {
            try {
                this.ftpClient.logout();
                this.ftpClient.disconnect();
                System.out.println("si desconecto!!!!!");
            } catch (IOException f) {
                // do nothing as file is already downloaded from FTP server
            }
        }
    }

    public void downloadFile (String remoteFilePath, String localFilePath) throws Exception {
        InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath);
        int returnCode;
        returnCode = ftpClient.getReplyCode();
        if (inputStream == null || returnCode == 550) {
            System.out.println("No existe el archivo!!!!!");
        }else {
            FileOutputStream fos = new FileOutputStream(localFilePath);
            boolean download = false;
            try {
                download = ftpClient.retrieveFile(remoteFilePath,
                        fos);
                if (download) {
                    System.out.println("File downloaded successfully !");
                } else {
                    System.out.println("Error in downloading file !");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
