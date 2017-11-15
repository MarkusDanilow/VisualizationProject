//https://stackoverflow.com/questions/1968293/connect-to-remote-mysql-database-through-ssh-using-java
//by User: Ankur, createdate: 2016-03-26 17:46, accessdate:2017-11-13 16:24:14


package com.tsh.connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class RemoteMySQLDB {


    private static void doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort,
            String strRemoteHost, int nLocalPort, int nRemotePort) throws JSchException {
        final JSch jsch = new JSch();
        Session session = jsch.getSession(strSshUser, strSshHost, 22);
        session.setPassword(strSshPassword);

        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
    }

    public static void main(String[] args) {
        try {
        	System.out.println("Establishing ssh...");
            String strSshUser = "liquidsolution.de"; // SSH loging username
            String strSshPassword = "-"; // SSH login password
            String strSshHost = "-"; // hostname or ip or
                                                            // SSH server
            int nSshPort = 22; // remote SSH host port number
            String strRemoteHost = "-"; // hostname or
                                                                    // ip of
                                                                    // your
                                                                    // database
                                                                    // server
            int nLocalPort = 3366; // local port number use to bind SSH tunnel
            int nRemotePort = 3306; // remote port number of your database
            String strDbUser = "-"; // database loging username
            String strDbPassword = "-"; // database login password

            RemoteMySQLDB.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort,
                    nRemotePort);
        	System.out.println("ssh ok...");
        	System.out.println("Establishing jdbc...");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:" + nRemotePort, strDbUser,
                    strDbPassword);
            con.close();
            System.out.println("done");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}