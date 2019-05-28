package com.jee.demo.blockingqueue.main;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class TestBlockingQueueProducer implements Runnable {
    BlockingQueue<String> queue;
	Random random = new Random();

	public TestBlockingQueueProducer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {

		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(random.nextInt(10));
				String task =Thread.currentThread().getName() + " made a product " + i;

				//System.out.println(task);
				System.out.println("【product" + i + "】");
				queue.put( "【product" + i + "】");
			} catch (InterruptedException e) {
				 
				e.printStackTrace();
			}

		}

	}

}