import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JTextArea;

/* 
 *  Problem producenta i konsumenta
 *
 *  Autor: Adam Skowroñski
 *   Data: 4 Grudnia 2017 r.
 */


abstract class  Worker extends Thread {
	

	public static void sleep(int millis){
		try {
			Thread.sleep(millis);
			} catch (InterruptedException e) { }
	}
	
	public static void sleep(int min_millis, int max_milis){
		sleep(ThreadLocalRandom.current().nextInt(min_millis, max_milis));
	}
	

	static int itemID = 0;
	
	int number;
	Buffer buffer;
	JTextArea text;
	boolean isPaused = false;
	
	public abstract void pauseSimulation();
	public abstract void  resumeSimulation();
	
	@Override
	public abstract void run();
	
	
}


class Producer extends Worker {

	
		public static int MIN_PRODUCER_TIME = 100;
		public static int MAX_PRODUCER_TIME = 1000;
	
	public Producer(Buffer buffer,int number,JTextArea text){ 
		this.number = number;
		this.buffer=buffer;
		this.text=text;
		this.start();
	}
	
	
	
	@Override
	public void run(){ 
		int item;
		while(true){
			
			item = itemID++;
			this.text.append("Producent <" + number + ">   produkuje: " + item + "\n");
			sleep(MIN_PRODUCER_TIME, MAX_PRODUCER_TIME);
			
			
			buffer.give(this, item);
			
			while(isPaused)
			{
				try
                {
                    sleep(5L);
                }
                catch(InterruptedException e) { }
			}
		}
		
	}

	@Override
	public void pauseSimulation() {
		isPaused = true;
		
	}

	@Override
	public void resumeSimulation() {
		isPaused = false;
		
	}
	
} 


class Consumer extends Worker {
	

		public static int MIN_CONSUMER_TIME = 100;
		public static int MAX_CONSUMER_TIME = 1000;
	
	public Consumer(Buffer buffer ,int number,JTextArea text){ 
		this.number = number;
		this.buffer = buffer;
		this.text=text;
		this.start();
	}
	
	

	@Override
	public void run(){ 
		int item;
		while(true){
			item = buffer.take(this);
			
			
			sleep(MIN_CONSUMER_TIME, MAX_CONSUMER_TIME);
			this.text.append("Konsument <" + number + ">       zu¿y³: " + item+ "\n");
			while(isPaused)
			{
				try
                {
                    sleep(5L);
                }
                catch(InterruptedException e) { }
			}
		}
	}

	@Override
	public void pauseSimulation() {
		isPaused = true;
	}

	@Override
	public void resumeSimulation() {
		isPaused = false;
		
	}
	
} 


class Buffer {
	
	private ArrayBlockingQueue<Integer> content;
	private int size;

	public Buffer(int size) {
		 this.content = new ArrayBlockingQueue<Integer>(size,true);
		 this.size = size;
	}
	
	public synchronized int take(Consumer consumer){
		consumer.text.append("Konsument <" + consumer.number + "> chce zabrac\n");
		while (content.isEmpty()){
			try { consumer.text.append("Konsument <" + consumer.number + ">   bufor pusty - czekam\n");
				  wait();
				} catch (InterruptedException e) { }
		}
		int item;
		
			item = content.poll();
			consumer.text.append("Konsument <" + consumer.number + ">      zabral: " + item+"\n");
		notifyAll();
		return item;
	}

	public synchronized void give(Producer producer, int item){
		producer.text.append("Producent <" + producer.number + ">  chce oddac: " + item + "\n");
		while (content.size() == size){ 
			try { producer.text.append("Producent <" + producer.number + ">   bufor zajety - czekam\n");
				  wait();
				} catch (InterruptedException e) { }
		}
		try {
			content.put(item);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		producer.text.append("Producent <" + producer.number + ">       oddal: " + item + "\n");
		notifyAll();
	}
	
} 


public class ProducerConsumer{

	
	
} 

