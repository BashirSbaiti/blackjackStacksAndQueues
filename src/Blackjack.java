/*		i value chart 
		0=player;
		1=bot1;
		2=bot2;
		3=bot3;
		4=bot4;
		5=bot5;
		6=bot6;
		7=dealer;
		
		ALSO:
		cards are stored as Suit|Value
		(suits are 1-4)
 */
import java.util.Stack;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
public class Blackjack {
	
	static Stack<String> deck = new Stack<>();
	static LinkedQueueGeneric<String> pHand = new LinkedQueueGeneric<String>();
	static LinkedQueueGeneric<String> dHand = new LinkedQueueGeneric<String>();
	static LinkedQueueGeneric<String> b1Hand = new LinkedQueueGeneric<String>();
	static LinkedQueueGeneric<String> b2Hand = new LinkedQueueGeneric<String>();
	static LinkedQueueGeneric<String> b3Hand = new LinkedQueueGeneric<String>();
	static LinkedQueueGeneric<String> b4Hand = new LinkedQueueGeneric<String>();
	static LinkedQueueGeneric<String> b5Hand = new LinkedQueueGeneric<String>();
	static LinkedQueueGeneric<String> b6Hand = new LinkedQueueGeneric<String>();
	static boolean pDone;
	static boolean dDone;
	static boolean b1Done;
	static boolean b2Done;
	static boolean b3Done;
	static boolean b4Done;
	static boolean b5Done;
	static boolean b6Done;
	static int bal = 100;
	static Random rand = new Random();
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int bet = 0;
		initDeck();
		System.out.println("What will you bet, your current balance is " + bal);
		int in = s.nextInt();
		if(in==0) {
			return;
		}
		else {
			bet = in;
			bal -= bet;
			if(bal<=0) {
				System.out.println("You are out of money");
				return;
			}
		}
		for(int i = 0; i < 100; i++) {
			shuffleDeck();
		}
		initDeal();
		while(true) {
			System.out.println("Hands are delt");
			System.out.println("Your deck could sum to any of the following: "+deckSum(0));
			System.out.println("The Dealer is showing: " + dHand.peek().substring(2) + " and has " + (dHand.size()-1) + " other card(s).");
			System.out.println("Bot 1 is showing a(n) " + b1Hand.peek().substring(2) + " and has " + (b1Hand.size()-1) + " other card(s).");
			System.out.println(b1Hand);
			System.out.println("Bot 2 is showing a(n) " + b2Hand.peek().substring(2) + " and has " + (b2Hand.size()-1) + " other card(s).");
			System.out.println("Bot 3 is showing a(n) " + b3Hand.peek().substring(2) + " and has " + (b3Hand.size()-1) + " other card(s).");
			System.out.println("Bot 4 is showing a(n) " + b4Hand.peek().substring(2) + " and has " + (b4Hand.size()-1) + " other card(s).");
			System.out.println("Bot 5 is showing a(n) " + b5Hand.peek().substring(2) + " and has " + (b5Hand.size()-1) + " other card(s).");
			System.out.println("Bot 6 is showing a(n) " + b6Hand.peek().substring(2) + " and has " + (b6Hand.size()-1) + " other card(s).");
			
			System.out.println("hit or stay?");
			System.out.println();
			String ch = s.next();
			System.out.println();
			
			decide();
			
			if(ch.toUpperCase().equals("HIT")) {
				pHand.enqueue(deck.pop());
				if(lost(0)) {
					System.out.println("Bust! Your deck summed to "+deckSum(0));
					bal-=bet;
					if(bal<=0) {
						end();
						break;
					}
				}
				if(lost(1)) {
					System.out.println("Bot 1 went bust with hand sums "+deckSum(1));
				}
				if(lost(2)) {
					System.out.println("Bot 2 went bust with hand sums "+deckSum(2));
				}
				if(lost(3)) {
					System.out.println("Bot 3 went bust with hand sums "+deckSum(3));
				}
				if(lost(4)) {
					System.out.println("Bot 4 went bust with hand sums "+deckSum(4));
				}
				if(lost(5)) {
					System.out.println("Bot 5 went bust with hand sums "+deckSum(5));
				}
				if(lost(6)) {
					System.out.println("Bot 6 went bust with hand sums "+deckSum(6));
				}
				if(lost(7)) {
					System.out.println("The dealer went bust with hand sums "+deckSum(7));
					bal+=bet * 2;
				}
				
			}
		}
	}
	private static void end() {
		System.out.println("You finished with $"+bal+". Thank you for playing!");
	}
	public static void initDeck(){
		deck.clear();
		for(int s = 1; s <= 4; s++) {
			for(int v = 1; v<=10 ; v++) {
				if(v==1) {
					deck.push(Integer.toString(s)+"|a"); //ace flag
				}
				else if(v==10) {
					deck.push(Integer.toString(s)+"|"+Integer.toString(v));
					deck.push(Integer.toString(s)+"|"+Integer.toString(v));
					deck.push(Integer.toString(s)+"|"+Integer.toString(v));
					deck.push(Integer.toString(s)+"|"+Integer.toString(v));
				}
				else {
					deck.push(Integer.toString(s)+"|"+Integer.toString(v));
				}
			}
		}
	}
	public static void shuffleDeck() {
		Stack<String> a0 = new Stack<>();
		Stack<String> a1 = new Stack<>();
		Stack<String> a2 = new Stack<>();
		Stack<String> a3 = new Stack<>();
		int oldDeckSize = deck.size();
		while(deck.size() > 0) {
			int flob = rand.nextInt(4);
			switch(flob) {
			case 0:
				a0.push(deck.pop());
				break;
			case 1:
				a1.push(deck.pop());
				break;
			case 2:
				a2.push(deck.pop());
				break;
			case 3:
				a3.push(deck.pop());
				break;
			}
		}
		while(deck.size()!=oldDeckSize) {
			switch(rand.nextInt(4)) {
			case 0:
				if(a0.size() != 0) {
					deck.push(a0.pop());
				}
				break;
			case 1:
				if(a1.size() != 0) {
					deck.push(a1.pop());
				}
				break;
			case 2:
				if(a2.size() != 0) {
					deck.push(a2.pop());
				}
				break;
			case 3:
				if(a3.size() != 0) {
					deck.push(a3.pop());
				}
				break;
			}
		}
	}
	public static void initDeal() {
		for(int i = 0; i < 2; i++) {
			pHand.enqueue(deck.pop());
			b1Hand.enqueue(deck.pop());
			b2Hand.enqueue(deck.pop());
			b3Hand.enqueue(deck.pop());
			b4Hand.enqueue(deck.pop());
			b5Hand.enqueue(deck.pop());
			b6Hand.enqueue(deck.pop());
			dHand.enqueue(deck.pop());
		}
	}
	public static void deal() {
		pHand.enqueue(deck.pop());
		b1Hand.enqueue(deck.pop());
		b2Hand.enqueue(deck.pop());
		b3Hand.enqueue(deck.pop());
		b4Hand.enqueue(deck.pop());
		b5Hand.enqueue(deck.pop());
		b6Hand.enqueue(deck.pop());
		dHand.enqueue(deck.pop());
	}
	public static ArrayList<Integer> deckSum(int i) { // i represents which deck is to be summed
		ArrayList<Integer> al = new ArrayList<Integer>();
		LinkedQueueGeneric hands[]= new LinkedQueueGeneric[8];
		
		hands[0]=pHand;
		hands[01]=b1Hand;
		hands[02]=b2Hand;
		hands[03]=b3Hand;
		hands[04]=b4Hand;
		hands[05]=b5Hand;
		hands[06]=b6Hand;
		hands[07]=dHand;
		
		LinkedQueueGeneric<String> StopMessingWithMyQueues = new LinkedQueueGeneric<String>();
		Iterator<String> it = hands[i].iterator();
		while(it.hasNext()) {
			StopMessingWithMyQueues.enqueue(it.next());
		}
		
		while(StopMessingWithMyQueues.size()>0) {
			if(StopMessingWithMyQueues.peek().substring(2,3).equals("a")) {
				al.add(-1);
				StopMessingWithMyQueues.dequeue();
			}
			else {
				al.add(Integer.valueOf(StopMessingWithMyQueues.peek().substring(2)));
				StopMessingWithMyQueues.dequeue();
			}
		}
		// Summation
		
		int sum = 0;
		ArrayList<Integer> possSums = new ArrayList<Integer>();
		Iterator<Integer> itr = al.iterator();
		possSums.add(0);
		int c = 0;
		while(itr.hasNext()) {
			int foo = itr.next();
			int goodVarName = possSums.size();
			if(foo==-1) {
				for(int i1 = 0; i1 < goodVarName; i1++) {
					int die = possSums.get(i1);
					die+=1;
					possSums.remove(i1);
					possSums.add(i1, die);
					possSums.add(die+10);
				}
			}
			else {
				for(int i12 = 0; i12 < goodVarName; i12++) {
					int die = possSums.get(i12);
					die+=foo;
					possSums.remove(i12);
					possSums.add(i12, die);
				}
			}
			c++;
		}
/************************************************************
 * UNSCALED RUBBISH
		if(al.get(0)!=-1 && al.get(1)!=-1) { 
			possSums.add(al.get(0)+al.get(1));
		}
		else if(al.get(0)==-1 && al.get(1)==-1) {
			possSums.add(2);
			possSums.add(12);
		}
		else if(al.get(0)!=-1 && al.get(1)==-1) {
			possSums.add(al.get(0)+1);
			possSums.add(al.get(0)+11);
		}
		else {
			possSums.add(al.get(1)+1);
			possSums.add(al.get(1)+11);
		}
************************************************************/

		return nukeDuplicates(possSums);
	}
	public static boolean lost(int i) {
		int c = 0;
		Object[] possaSums = deckSum(i).toArray();
		for(int i1 = 0; i1<possaSums.length;i1++) {
			if((Integer)possaSums[i1]>=21) {
				c++;
			}
		}
		System.out.println(c+" != "+(possaSums.length-1));
		return c==possaSums.length-1;
	}
	
	@SuppressWarnings("unchecked")
	public static void decide() { //TODO: place the "iDone = true" statements;
		//LinkedQueueGeneric<String>;
		LinkedQueueGeneric hands[]= new LinkedQueueGeneric[8];
		hands[0]=pHand;
		hands[01]=b1Hand;
		hands[02]=b2Hand;
		hands[03]=b3Hand;
		hands[04]=b4Hand;
		hands[05]=b5Hand;
		hands[06]=b6Hand;
		hands[07]=dHand;
		for(int i = 1; i < 8; i++) {
			System.out.println(deckSum(i).get(0)+"sum \t in deck composed of "+hands[i]+" number "+i+"\t BEFORE");
			while(deckSum(i).get(0)<=17) {
				hands[i].enqueue(deck.pop());
			}
			System.out.println(deckSum(i).get(0)+"sum \t in deck composed of "+hands[i]+" number "+i+"\t AFTER");
		}
		return;
	}
	
	public static boolean won(int i) {
		Iterator<Integer> itr = deckSum(i).iterator();
		while(itr.hasNext()) {
			if(itr.next()==21) {
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<Integer> nukeDuplicates(ArrayList<Integer> al) {
		Iterator<Integer> itr = al.iterator();
		ArrayList<Integer> arls = new ArrayList<Integer>();
		while(itr.hasNext()) {
			int next = itr.next();
			if(!arls.contains(next)) {
				arls.add(next);
			}
		}
		return arls;
	}
}
	

