package src;

import java.util.List;

import esper.EsperHandler;
import src.Coin.Name;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * monitors twitter for pump/dump notifications and track those values for arbitrage
 * 	opportunities and volume comparisons
 * 
 * @author GEORGE
 *
 */
public class TwitterMonitor {
	
	// tweet data
	static String orbicosLastTweet = "";
	static String originalChilliLastTweet = "";
	static String cryptopumpingLastTweet = "";
	static String equalpumpsLastTweet = "";
	static String InsaneDumpsLastTweet = "";
	static String PumpDumpTestLastTweet = "";
	
	/**
	 * starts the twitter process
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		Twitter twitter = TwitterMonitor.setKeys(); // handle twitter authentication
		Name name = null;
		
		while( true ){
			try {
	        	Paging paging = new Paging(1, 1);
	        	// check user - orbicos
	        	List<Status> statuses = twitter.getUserTimeline("orbicos",paging); // get tweets
	            for (Status status : statuses) { // for each status
	            	if (!(orbicosLastTweet.equals(status.getText()))){
	            		orbicosLastTweet = status.getText();
	                	System.out.println(status.getUser().getName() + ":" + status.getText());
	                	name = searchTweet(orbicosLastTweet); // check if pump/dump
	                	if (name != null){ // if it is a pump/dump
	                		EsperHandler.StartEsperEngine(name); // start esper engine
	                		name = null;
	                	}
	            	}
	            }
	            // check user - originalchilli
	            statuses = twitter.getUserTimeline("originalchilli",paging);
	            for (Status status : statuses) {
	            	if (!(originalChilliLastTweet.equals(status.getText()))){
	            		originalChilliLastTweet = status.getText();
	                	System.out.println(status.getUser().getName() + ":" + status.getText());
	                	name = searchTweet(originalChilliLastTweet);
	                	if (name != null){
	                		EsperHandler.StartEsperEngine(name);
	                		name = null;
	                	}
	            	}
	            }
	            // check user - cryptopumping
	            statuses = twitter.getUserTimeline("cryptopumping",paging);
	            for (Status status : statuses) {
	            	if (!(cryptopumpingLastTweet.equals(status.getText()))){
	            		cryptopumpingLastTweet = status.getText();
	                	System.out.println(status.getUser().getName() + ":" + status.getText());
	                	name = searchTweet(cryptopumpingLastTweet);
	                	if (name != null){
	                		EsperHandler.StartEsperEngine(name);
	                		name = null;
	                	}
	            	}
	            }
	            // check user - equalpumps
	            statuses = twitter.getUserTimeline("equalpumps",paging);
	            for (Status status : statuses) {
	            	if (!(equalpumpsLastTweet.equals(status.getText()))){
	            		equalpumpsLastTweet = status.getText();
	                	System.out.println(status.getUser().getName() + ":" + status.getText());
	                	name = searchTweet(equalpumpsLastTweet);
	                	if (name != null){
	                		EsperHandler.StartEsperEngine(name);
	                		name = null;
	                	}
	            	}
	            }
	            // check user - InsaneDumps
	            statuses = twitter.getUserTimeline("InsaneDumps",paging);
	            for (Status status : statuses) {
	            	if (!(InsaneDumpsLastTweet.equals(status.getText()))){
	            		InsaneDumpsLastTweet = status.getText();
	                	System.out.println(status.getUser().getName() + ":" + status.getText());
	                	name = searchTweet(InsaneDumpsLastTweet);
	                	if (name != null){
	                		EsperHandler.StartEsperEngine(name);
	                		name = null;
	                	}
	            	}
	            }
	            // checks test twitter - PumpDumpTest
	            statuses = twitter.getUserTimeline("PumpDumpTest",paging);
	            for (Status status : statuses) {
	            	if (!(PumpDumpTestLastTweet.equals(status.getText()))){
	            		PumpDumpTestLastTweet = status.getText();
	                	System.out.println(status.getUser().getName() + ":" + status.getText());
	                	name = searchTweet(PumpDumpTestLastTweet);
	                	if (name != null){
	                		EsperHandler.StartEsperEngine(name);
	                		name = null;
	                	}
	            	}
	            }
	            
	            // wait so as to not go over twitter's rate limits
	            System.out.println("sleeping");
	            Thread.sleep(1000*60);
	            System.out.println("waking");
	        } catch (TwitterException e) {
	            System.err.print("Failed to search tweets: " + e.getMessage());
	            // e.printStackTrace();
	        }
		}
	}
	
	/**
	 * authenticates with the twitter api
	 * @return suthenticated twitter access
	 */
	private static Twitter setKeys(){
		// set API access keys
		String CONSUMER_KEY = "00S5CYTnevOShbgNnVSATULQB";
        String CONSUMER_KEY_SECRET = "PjbGj9HXD9Fv5aYfjla7ouDC5AYf6jrXLWlG80Pph8vY9NhvRk";
        String TWITTER_TOKEN = "221952669-oDXTxJhDDnmFoLr3ATNVXGGlrx1wHrnWE2TAUjkU";
        String TWITTER_TOKEN_SECRET = "sxGOI61j9c1kq86TPUqBqaRE6O8tOVtWwr3DdGXSfsm7M";
        // perform authentication
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
        AccessToken accessToken = new AccessToken(TWITTER_TOKEN, TWITTER_TOKEN_SECRET);
        twitter.setOAuthAccessToken(accessToken);
        return twitter;
	}
	
	/**
	 * searches a tweet for if it is starting a pump/dump
	 * @param str - tweet in question
	 * @return returns the name of the coin to monitor
	 */
	private static Coin.Name searchTweet(String str){
		String tweet = str.toLowerCase();
		boolean isPumpDump = false;
		boolean isSoon = true;
		Coin.Name coinName = null;
		// parse tweet
		String[] tokens = tweet.split(" ");
		for (String s : tokens){
			// is it about a pump/dump
			if (s.equals("pump") || s.equals("dump")){
				isPumpDump = true;
			} else if (s.equals("seconds") || s.equals("secs") || s.equals("sec") || s.equals("second")){
				isSoon = true;
			} else if (s.equals("minutes") || s.equals("mins") || s.equals("min") || s.equals("minute")){
				isSoon = true;
			} else if (s.equals("hour") || s.equals("hr") || s.equals("hrs") || s.equals("hours")){ // will it happen much later
				isSoon = false;
			} else {
				if(Coin.toEnum(s) != null){ // determine if coin name
					coinName = Coin.toEnum(s); // convert to enum
				}
			}
		}
		if (isPumpDump == true && isSoon == true && coinName != null){ // it is a pump
			System.out.println("PUMP DUMP DETECTED");
			return coinName;
		} else {
			return null;
		}
	}
	
}
