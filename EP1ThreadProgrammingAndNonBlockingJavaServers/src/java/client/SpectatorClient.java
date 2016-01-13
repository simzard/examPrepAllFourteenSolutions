/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author simon
 */
public class SpectatorClient {

    private Socket link;

    private BufferedReader in;
    private PrintWriter out;

    public SpectatorClient(Socket link) throws IOException {
        this.link = link;
        in = new BufferedReader(
                new InputStreamReader(link.getInputStream()));
        out = new PrintWriter(link.getOutputStream(), true);
    }

    public String getData() throws IOException {
        in.readLine();
        out.println("monitor");
        String result = in.readLine() + "\n\n"; //out.println("Total spectators: " + sum);

        int amount = 0;
        try {
            
            amount = Integer.parseInt(in.readLine().split(" ")[0]); // out.println(count + " turnstile(s) submitted");
        } catch (NumberFormatException e) {
            
            amount = 0;
        }
        
        
        if (amount > 0) {
            result += "Total spectators for each turnstile\n";
            result += "-----------------------------------\n";
            
            for (int i = 0; i < amount; i++) {
                result += in.readLine() + "\n";
            }
        }
        return result;
    }

}
