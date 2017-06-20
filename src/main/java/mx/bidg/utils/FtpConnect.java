package mx.bidg.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPCommand;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;

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
//        InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath);
//        int returnCode;
//        returnCode = ftpClient.getReplyCode();
//        if (inputStream == null || returnCode == 550) {
//            System.out.println("No existe el archivo!!!!!");
//        }else {
//            FileOutputStream fos = new FileOutputStream(localFilePath);
//            boolean download = false;
//            try {
//                download = ftpClient.retrieveFile(remoteFilePath,
//                        fos);
//                if (download) {
//                    System.out.println("File downloaded successfully !");
//                } else {
//                    System.out.println("Error in downloading file !");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


        //aqui iria la direccion del repositorio cambiar por variable
        this.ftpClient.changeWorkingDirectory("site/data");
        System.out.println("Directorio: " +  this.ftpClient.printWorkingDirectory());
        FTPFile[] ftpFiles = this.ftpClient.listFiles();

        if (ftpFiles != null && ftpFiles.length > 0) {
            //loop thru files
            for (FTPFile file : ftpFiles) {
                if (file.getName().equals(remoteFilePath)){
                    if (!file.isFile()) {
                        continue;
                    }
                    //get output stream
                    OutputStream output;
                    output = new FileOutputStream(localFilePath);
                    //get the file from the remote system
                    boolean download;
                    download = this.ftpClient.retrieveFile(file.getName(), output);
                    if (download) {
                        System.out.println("File downloaded successfully !");
                    } else {
                        System.out.println("Error in downloading file !");
                    }
                    //close output stream
                    output.close();
                }
            }
        }
    }
}
