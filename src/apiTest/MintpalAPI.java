package apiTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * tests the mintpal api for getting market data
 * @author GEORGE
 *
 */
public class MintpalAPI {

	public static void main(String[] args) throws Exception {
        URL oracle = new URL("https://api.mintpal.com/v1/market/summary/");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
	
}
