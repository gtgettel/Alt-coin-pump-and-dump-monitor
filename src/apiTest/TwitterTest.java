package apiTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
 
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * tests the Twitter API
 * @author GEORGE
 *
 */
public class TwitterTest {
 
    public static void main(String args[]){
        // The factory instance is re-useable and thread safe.
        String CONSUMER_KEY = "00S5CYTnevOShbgNnVSATULQB";
        String CONSUMER_KEY_SECRET = "PjbGj9HXD9Fv5aYfjla7ouDC5AYf6jrXLWlG80Pph8vY9NhvRk";
        String TWITTER_TOKEN = "221952669-oDXTxJhDDnmFoLr3ATNVXGGlrx1wHrnWE2TAUjkU";
        String TWITTER_TOKEN_SECRET = "sxGOI61j9c1kq86TPUqBqaRE6O8tOVtWwr3DdGXSfsm7M";
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
        AccessToken accessToken = new AccessToken(TWITTER_TOKEN, TWITTER_TOKEN_SECRET);
        twitter.setOAuthAccessToken(accessToken);
        
        try {
        	Paging paging = new Paging(1, 100);
        	List<Status> statuses = twitter.getUserTimeline("google",paging);
            for (Status status : statuses) {
                System.out.println(status.getUser().getName() + ":" +
                                   status.getText());
            }
        } catch (TwitterException e) {
            System.err.print("Failed to search tweets: " + e.getMessage());
            // e.printStackTrace();
        }
    }
}
        