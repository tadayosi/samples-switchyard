package com.redhat.samples.switchyard;

import java.io.File;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.filesystem.nativefs.NativeFileSystemFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

public class FTPServer {

    private static final int FTP_PORT = 2121;

    private FtpServer ftpServer;

    private FtpServer createServer() {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(FTP_PORT);
        serverFactory.addListener("default", listenerFactory.createListener());
        PropertiesUserManagerFactory managerFactory = new PropertiesUserManagerFactory();
        managerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
        managerFactory.setFile(new File("src/test/resources/users.properties"));
        UserManager createUserManager = managerFactory.createUserManager();
        serverFactory.setUserManager(createUserManager);

        NativeFileSystemFactory fileSystemFactory = new NativeFileSystemFactory();
        fileSystemFactory.setCreateHome(true);
        serverFactory.setFileSystem(fileSystemFactory);

        return serverFactory.createServer();
    }

    public FTPServer start() throws FtpException {
        ftpServer = createServer();
        ftpServer.start();
        return this;
    }

    public void stop() {
        ftpServer.stop();
    }

    public static void main(String[] args) throws Exception {
        new FTPServer().start();
    }

}
