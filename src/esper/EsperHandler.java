package esper;

import EventCreator.EventStreamController;
import EventCreator.TickEventCryptsy;
import EventCreator.TickEventMintpal;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import src.Coin;

/**
 * Starts Esper engine and prints results
 * @author GEORGE
 *
 */
public class EsperHandler {
	
	// result data
	static double maxCryptsyVolume = 0;
	static double maxMintpalVolume = 0;
	static double arbitrageOpportunityMax = 0;
	static boolean timerStarted = false;
	static long timerStart = 0;
	static long timerEnd = 0;
	static long maxTime = 0;
	//static int arbitrageProfit = 0;
	
	/**
	 * UpdateListener class for Esper
	 *
	 * The UpdateListener fires when an event triggers the EPL query
	 */
    public static class TickListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            System.out.println("Event received: " + newData[0].getUnderlying());
            // check max volume on Mintpal
            if ((double)newData[0].get("max(MintpalTick.volume)") > maxMintpalVolume){
            	maxMintpalVolume = (double)newData[0].get("max(MintpalTick.volume)");
            }
            // check max volume on Cryptsy
            if ((double)newData[0].get("max(CryptsyTick.volume)") > maxCryptsyVolume){
            	maxCryptsyVolume = (double)newData[0].get("max(CryptsyTick.volume)");
            }
            // check arbitrage price
            if ((double)newData[0].get("CryptsyTick.price") != (double)newData[0].get("MintpalTick.price")){
            	System.out.println("\t-- Price Divergence --");
            	double aom = Math.abs((double)newData[0].get("CryptsyTick.price") - (double)newData[0].get("MintpalTick.price"));
            	if (aom > arbitrageOpportunityMax){
            		arbitrageOpportunityMax = aom;
            	}
            	// start timer
            	if (timerStarted == false){
            		System.out.println("\t-- Timer Started --");
            		timerStarted = true;
            		timerStart = System.currentTimeMillis();
            	}
            } else {
            	// end timer
            	if (timerStarted == true){
            		timerStarted = false;
            		timerEnd = System.currentTimeMillis();
            		System.out.println("\t-- Timer Ended --");
            		if (maxTime < Math.abs(timerEnd - timerStart)){
            			maxTime = Math.abs(timerEnd - timerStart);
            		}
            	}
            }
        }
    }
	
    /**
     * starts the execution of the esper engine
     * 
     * @param name - enum of coin to monitor
     * @throws Exception
     */
	public static void StartEsperEngine(Coin.Name name) throws Exception{
		// intialize the esper engine
		Configuration cepConfig = new Configuration();
        cepConfig.addEventType("CryptsyTick", TickEventCryptsy.class.getName()); // initialize cryptsy stream
        cepConfig.addEventType("MintpalTick", TickEventMintpal.class.getName()); // initialize mintpal stream
        EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
 
        EPAdministrator cepAdm = cep.getEPAdministrator();
        // add EPL query
        EPStatement cepStatement = cepAdm.createEPL("select MintpalTick.price, CryptsyTick.price, max(MintpalTick.volume), max(CryptsyTick.volume) from MintpalTick.win:time(120 min) as mt, CryptsyTick.win:time(120 min)");
        cepStatement.addListener(new TickListener());
        
        // generate events over next 59 minutes
        for (int i = 0; i < 60*118; i++) {
        	EventStreamController.GenerateRandomTick(cepRT, name);
            Thread.sleep(1000);
        }
        printResults(name);
	}
	
	/**
	 * prints results in a readable fashion
	 * @param name - name of coin
	 */
	public static void printResults(Coin.Name name){
		System.out.println();
        System.out.println("----- ----- RESULTS ----- -----");
        System.out.println();
        System.out.println("COIN: " + name.toString());
        System.out.println();
        System.out.println("VOLUME: ");
        System.out.println("\tMax mintpal volume: " + maxMintpalVolume + " BTC");
        System.out.println("\tMax cryptsy volume: " + maxCryptsyVolume + " " + name.toString());
        System.out.println();
        System.out.println("Arbitrage: ");
        System.out.println("\tMax price divergence: " + arbitrageOpportunityMax);
        if (timerStarted == true){
        	timerStarted = false;
    		timerEnd = System.currentTimeMillis();
    		if (maxTime < Math.abs(timerEnd - timerStart)){
    			maxTime = Math.abs(timerEnd - timerStart);
    		}
        }
        System.out.println("\tMax time of price divergence: " + maxTime + " milleseconds");
        //System.out.println("\tPossible profit: " + arbitrageProfit + " BTC");
        System.out.println();
        System.out.println("----- ----- RESULTS ----- -----");
        
        maxCryptsyVolume = 0;
    	maxMintpalVolume = 0;
    	arbitrageOpportunityMax = 0;
    	//arbitrageProfit = 0;
	}
	
}
