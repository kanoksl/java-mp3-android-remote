package com.kanoksilp.javamediaplayerremote;

import android.util.Log;

import java.io.*;
import java.net.*;

/**
 * Created by Kanoksilp on 2015-04-02.
 */
public class MediaControlClient implements Runnable {

    private static final String LOG_TAG = "MediaControlClient";

    private Thread thread;
    private String threadName;

    private String hostName;
    private int portNumber;

    private ClientStatus status;

    public ClientStatus getStatus() {
        return this.status;
    }

    public MediaControlClient(String threadName, String hostName, int portNumber) {
        this.threadName = threadName;
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.status = ClientStatus.NOT_RUNNING;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    @Override
    public void run() {
        try {
            startClient();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        }

    }

    private Socket serverSocket;
    private PrintWriter output;

    private void startClient() throws IOException {
        Log.i("Main", "Attempting to connect to host " + hostName + " on port " + portNumber + ".");

        this.status = ClientStatus.RUNNING_NOT_CONNECTED;

        try {
            serverSocket = new Socket(hostName, portNumber);
            output = new PrintWriter(serverSocket.getOutputStream(), true);
        } catch (UnknownHostException e) {
            Log.e(LOG_TAG, e.toString());
            this.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
            this.close();
        }

        this.status = ClientStatus.RUNNING_CONNECTED;

        send("hello");
    }


    public void send(String message) {
        output.println(message);
    }

    public void close() {
        this.status = ClientStatus.NOT_RUNNING;
        try {
            output.close();
            serverSocket.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    public static enum ClientStatus {
        RUNNING_NOT_CONNECTED, RUNNING_CONNECTED, NOT_RUNNING
    }
}



    /*
    private void startClient() throws IOException {
        Log.i("Main", "Attemping to connect to host " + hostName + " on port " + portNumber + ".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(hostName, 10008);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            Log.e(LOG_TAG, e.toString());
            //System.exit(1);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
            //System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));

        out.println("HELLO FROM ANDROID");

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();

    }*/