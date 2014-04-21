package apiTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * tests the cryptsy api for getting market data
 * @author GEORGE
 *
 */
public class CryptsyTest {

	public static void main(String[] args) throws Exception {
        URL oracle = new URL("http://pubapi.cryptsy.com/api.php?method=marketdatav2");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
	
}
