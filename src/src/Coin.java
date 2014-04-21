package src;

/**
 * Type of coin being tracked
 * 
 * @author GEORGE
 *
 */
public class Coin {

	/**
	 * enum that holds the symbol of the coin
	 */
	public enum Name {
		DOGE, FLT, LTC, BC, AUR, POT, EMC2, ZET, TGC, BAT, MINT, PPC, NMC, WC
	}
	
	/**
	 * converts a coin symbol to the corresponding cryptsy market id
	 * @param abrv - coin enum in question
	 * @return returns market id
	 */
	public static int toCryptsyMarketId(Name abrv){
		switch(abrv){
			case DOGE:
				return 132;
			case FLT:
				return 192;
			case LTC:
				return 3;
			case BC:
				return 179;
			case AUR:
				return 160;
			case POT:
				return 173;
			case EMC2:
				return 188;
			case ZET:
				return 85;
			case TGC:
				return 130;
			case BAT:
				return 181;
			case MINT:
				return 156;
			case PPC:
				return 28;
			case NMC:
				return 29;
			case WC:
				return 195;
			default:
				return 0;
		}
	}
	
	/**
	 * converts a coin name to its enum
	 * @param abrv - string of coin name
	 * @return - enum of coin
	 */
	public static Name toEnum(String abrv){
		// parse coin name
		String switcher = abrv.toLowerCase();
		switcher = switcher.replace("coin","");
		switcher = switcher.replace(" ","");
		switcher = switcher.replace("#","");
		switcher = switcher.replace("\n|\t|\r","");
		switch (switcher){
			case "doge":
				return Name.DOGE;
			case "flutter":
				return Name.FLT;
			case "lite":
				return Name.LTC;
			case "black":
				return Name.BC;
			case "aurora":
				return Name.AUR;
			case "pot":
				return Name.POT;
			case "emc2":
				return Name.EMC2;
			case "zeta":
				return Name.ZET;
			case "tiger":
				return Name.TGC;
			case "bat":
				return Name.BAT;
			case "mint":
				return Name.MINT;
			case "peer":
				return Name.PPC;
			case "name":
				return Name.NMC;
			case "white":
				return Name.WC;
			default:
				return null;
		}
	}
	
}
