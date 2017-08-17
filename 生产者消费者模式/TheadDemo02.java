package com.chenparty.demo;

class Data {
	private String name;
	private String title;
	// flag = true：表示允许生产，但是不允许消费者取走
	// flag = false：表示生产完毕，允许消费者取走，但是不能够生产
	private boolean flag = true;
	public synchronized void get() {
		// TODO Auto-generated method stub
		if(flag==false) {
			try {
				super.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(this.name + " = " + this.title);
		this.flag = true;
	}

	public synchronized void set(String name, String title) {
		if(flag==true) {
			super.notify();
		}
		// TODO Auto-generated method stub
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.name = name;
		this.title = title;
	}
}

class DataConsumer implements Runnable {
	private Data data;

	public DataConsumer(Data data) {
		this.data = data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 50; i++) {
			synchronized (this) {
				this.data.get();
			}
		}
	}
}

class DataProvider implements Runnable {
	private Data data;

	public DataProvider(Data data) {
		this.data = data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 50; i++) {
			if (i % 2 == 0) {
				this.data.set("张子强", "有理想有抱负的有志青年");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				this.data.set("吴佳利", "多愁善感的小佳利");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}

public class TheadDemo02 {
	public static void main(String[] args) {
		Data data = new Data();
		DataProvider dataProvider = new DataProvider(data);
		DataConsumer dataConsumer = new DataConsumer(data);
		new Thread(dataProvider).start();
		new Thread(dataConsumer).start();
	}
}
