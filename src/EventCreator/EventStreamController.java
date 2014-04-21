package EventCreator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import src.Coin;

import com.espertech.esper.client.EPRuntime;

/**
 * gets the event data from the API and adds it to the event stream
 * 
 * @author GEORGE
 *
 */
public class EventStreamController {

	/**
	 * queries the mintpal exchange's public API for new data
	 * @param name - coin being tracked
	 * @return new mintpal tick event
	 * @throws Exception
	 */
	public static TickEventMintpal getMintpalData(Coin.Name name) throws Exception {
		// get data
        URL oracle = new URL("https://api.mintpal.com/v1/market/stats/" + name.toString().toUpperCase() + "/BTC");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        String str = "";
        String inputLine = "";
        while ((str = in.readLine()) != null)
        	inputLine = str;
        in.close();
        // parse mintpal data
        inputLine = inputLine.replace("\"","").replace("}","").replace("{","");
        String[] tokens = inputLine.split(",|:");
        //System.out.println("Sending tick (Mintpal) - Symbol:" + tokens[3] + " Price: " + Double.parseDouble(tokens[7]) + " Volume: " + Double.parseDouble(tokens[17]));
        if (inputLine != ""){
        	return new TickEventMintpal(Coin.toEnum(tokens[3]), Double.parseDouble(tokens[7]), System.currentTimeMillis(), Double.parseDouble(tokens[17])); // create Mintpal event
        } else {
        	return null;
        }
    }
	
	/**
	 * queries the cryptsy exchange's public API for new data
	 * @param name - coin being tracked
	 * @return new cryptsy tick event
	 * @throws Exception
	 */
	public static TickEventCryptsy getCryptsyData(Coin.Name name) throws Exception {
		// get data
        URL oracle = new URL("http://pubapi.cryptsy.com/api.php?method=singlemarketdata&marketid=" + Coin.toCryptsyMarketId(name));
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        String inputLine = "";
        String str = "";
        while ((str = in.readLine()) != null)
        	inputLine = str;
        in.close();
        // parse cryptsy data
        inputLine = inputLine.replace("\"","").replace("}","").replace("{","");
        String[] tokens = inputLine.split(",|:");
        //System.out.println("Sending tick (Cryptsy) - Symbol: " + tokens[4] + " Price: " + Double.parseDouble(tokens[10]) + " Volume: " + Double.parseDouble(tokens[12]));
		return new TickEventCryptsy(Coin.toEnum(tokens[4]), Double.parseDouble(tokens[10]), System.currentTimeMillis(), Double.parseDouble(tokens[12])); // create cryptsy event
    }
	
	/**
	 * generates events and add them to the streams
	 * @param cepRT - Esper runtime engine
	 * @param name - coin being tracked
	 * @throws Exception
	 */
	public static void GenerateRandomTick(EPRuntime cepRT, Coin.Name name) throws Exception {
        TickEventCryptsy tec = EventStreamController.getCryptsyData(name); // get cryptsy event
        TickEventMintpal tem = EventStreamController.getMintpalData(name); // get mintpal event
        if (tec != null && tem != null){
        	cepRT.sendEvent(tec); // send events to streams
        	cepRT.sendEvent(tem);
        }
    }
	
}
