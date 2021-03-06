package tools;

import java.util.concurrent.CountDownLatch;

import abstractmath.Field28;

public class Test {
	public static void main(String[] args) {
//		multiThread(1);
//		int n = Integer.parseInt(args[0]);
//		multiThread(n);
		mixTest(1 << 24);
	}
	public static final void multiThread(int n) {
		System.out.println("-----------test multiple thread------------");
		byte p = 0b0001110;
		p |= -128;
		Field28 field = new Field28(p);
		Counter c = new Counter();
		CountDownLatch cdl = new CountDownLatch(n);
		MultiThreadTest[] ms = new MultiThreadTest[n];
		for (int i = 0; i < n; ++i)
			ms[i] = new MultiThreadTest(c, 1000000 / n, field, cdl);
		c.setStart();
		for (int i = 0; i < n; ++i)
			ms[i].start();
		try {
			cdl.await();
			System.out.println(c.getTime() + " ms");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static final void singleThread() {
		System.out.println("-----------test single thread------------");
		byte b = 0b001110;
		b |= -128;
		byte p = 0b0001110;
		p |= -128;
		byte minus1 = -1;
		Field28 field = new Field28(p);
		System.out.println("p = " + toBin(p));
		System.out.println("b = " + toBin(b));
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; ++i) {
			b = field.multiply(b, minus1);
		}
		long end = System.currentTimeMillis();
		System.out.println("after multiply b = " + toBin(b));
		System.out.println("time used for 1000000 mutiply: " + (end - start) + " ms");
	}
	public static final String toBin(byte b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; ++i) {
			sb.append((b >> (7 - i)) & 1);
		}
		return sb.toString();
	}
	
	public static final void mixTest(final int n) {
		final byte[][] matrix = {
				{ 2, 3, 1, 1 },
				{ 1, 2, 3, 1 },
				{ 1, 1, 2, 3 },
				{ 3, 1, 1, 2 }
		};
		final byte p = (byte) 0b10001101;
		final Field28 f = new Field28(p);
		byte[] data = new byte[4];
		data[0] = data[1] = data[2] = data[3] = 0;
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; ++i) {
			data = f.AESMixColumns(matrix, data);
		}
		long end = System.currentTimeMillis();
		System.out.println(n + " times " + "time used: " + (end - start) + " ms");
	}
}
