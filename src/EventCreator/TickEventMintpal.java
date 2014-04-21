package EventCreator;

import java.util.Date;

import src.Coin;
import src.Coin.Name;

/**
 * mintpal event
 * 
 * @author GEORGE
 *
 */
public class TickEventMintpal {

	Coin.Name symbol;
    Double price;
    Date timeStamp;
    Double volume;
    
    public TickEventMintpal(Name s, double p, long t, double v) {
        symbol = s;
        price = p;
        timeStamp = new Date(t);
        volume = v;
    }
    public double getPrice() {return price;}
    public Name getSymbol() {return symbol;}
    public Date getTimeStamp() {return timeStamp;}
    public Double getVolume() { return volume;}

    @Override
    public String toString() {
        return "Symbol: " + symbol.toString() + " Price: " + price.toString() + " Time: " + timeStamp.toString() + " Volume: " + volume.toString();
    }
	
}
