package EventCreator;

import java.util.Date;

import src.Coin;
import src.Coin.Name;

/**
 * cryptsy event
 * @author GEORGE
 *
 */
public class TickEventCryptsy {

	Coin.Name symbol;
    Double price;
    Date timeStamp;
    Double volume;
    
    public TickEventCryptsy(Name name, double p, long t, double v) {
        symbol = name;
        price = p;
        timeStamp = new Date(t);
        volume = v;
    }
    public double getPrice() {return price;}
    public Name getSymbol() {return symbol;}
    public Date getTimeStamp() {return timeStamp;}
    public double getVolume() {return volume;}

    @Override
    public String toString() {
        return "Symbol: " + symbol + " Price: " + price.toString() + " Time: " + timeStamp.toString() + " Volume: " + volume.toString();
    }
	
}
