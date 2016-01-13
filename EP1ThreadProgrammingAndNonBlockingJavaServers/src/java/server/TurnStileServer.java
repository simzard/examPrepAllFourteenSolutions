/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simon
 */
public class TurnStileServer {

    private Map<String, Integer> spectators = new HashMap();

    private static int nextTurnStileId = 0;

    public Map<String, Integer> getSpectators() {
        return spectators;
    }
    
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        //serverSocket.bind(new InetSocketAddress("localhost", 9090));
        while (true) {
            new TurnStileServerTask(serverSocket.accept()).start();
        }
    }

    private class TurnStileServerTask extends Thread {

        private Socket link;
        private BufferedReader in;
        private PrintWriter out;

        private String turnStileId;

        public String getTurnStileId() {
            return turnStileId;
        }

        public TurnStileServerTask(Socket link) {
            this.link = link;
        }

        public void init() throws IOException {
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            out = new PrintWriter(link.getOutputStream(), true);
        }

        public synchronized void addSpectators(int number) {
            Integer currentAmountFromThisTurnStile = spectators.get(turnStileId);
            if (currentAmountFromThisTurnStile == null) {
                // this means it is the first time
                currentAmountFromThisTurnStile = 0;
            }

            spectators.put(turnStileId, currentAmountFromThisTurnStile + number);
        }

        public void handleClient() throws IOException {
            out.println("Enter 'turnstile' or 'monitor'");
            String text = in.readLine();
            if (text.equalsIgnoreCase("turnstile") || text.equalsIgnoreCase("monitor")) {
                switch (text.toLowerCase()) {
                    case "turnstile":
                        // client accepted, increase id and give it to client
                        turnStileId = "TURNSTILE-" + nextTurnStileId++;
                        out.println("Your id is: " + turnStileId);
                        while (true) {
                            out.println("Enter a number of spectators - 0 to stop");
                        // await number of spectators from client

                            int number = -1;
                            do {
                                try {
                                    number = Integer.parseInt(in.readLine());
                                } catch (NumberFormatException e) {
                                    number = -1;
                                }

                            } while (number == -1);
                            if (number != 0)
                                addSpectators(number);
                            else {
                                break;
                            }
                        }
                        break;
                    case "monitor":
                        // return current amount of spectators
                        long sum = 0;
                        int count = 0;
                        for (Map.Entry<String, Integer> entry : spectators.entrySet()) {
                            sum += entry.getValue();
                            count++;
                        }
                        out.println("Total spectators: " + sum);
                        out.println(count + " turnstile(s) submitted");
                        if (count > 0) {
                            //out.println("Total spectators for each turnstile");
                            //out.println("-----------------------------------");
                            for (Map.Entry<String, Integer> entry : spectators.entrySet()) {
                                out.println(entry.getKey() + ": " + entry.getValue());
                            }
                        }

                        break;
                }
            }

        }

        public void close() throws IOException {
            in.close();
            out.close();
            link.close();
        }

        public void run() {
            try {
                init();
                handleClient();
                close();
            } catch (IOException ex) {
                Logger.getLogger(TurnStileServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new TurnStileServer().start();
    }
}
