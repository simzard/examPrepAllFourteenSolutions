/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadprogrammingproducerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simon
 */
public class ThreadProgrammingProducerConsumer {

    public static int TOTAL_NUMBERS;
    
    class ProducerThread extends Thread {
        
        BlockingQueue S1;
        BlockingQueue S2;
        String threadName;
        
        public ProducerThread(BlockingQueue S1, BlockingQueue S2, String threadName) {
            this.S1 = S1;
            this.S2 = S2;
            this.threadName = threadName;
        }
        
        public void run() {
            
            while(S1.peek() != null) {
                int nextNumber = (Integer) S1.poll();
                System.out.println(threadName + ": taking " + nextNumber + " from S1");
                long result = fib(nextNumber);
                System.out.println(threadName + ": inserting " + result + " into S2");
                try {
                    S2.put(result);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadProgrammingProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            System.out.println(threadName + ": stopping");
            
        }
        
        private long fib(long n) {
            if ((n == 0) || (n == 1)) {
                return n;
            
            } else {
                return fib(n - 1) + fib(n - 2);
            }
        }
    }
    
    class ConsumerThread extends Thread {
        private BlockingQueue S2;
        
        private long sum = 0;
        private int numbersLeft = TOTAL_NUMBERS;
        
        public ConsumerThread(BlockingQueue S2) {
            this.S2 = S2;
        }
        
        public void run() {
            while ( numbersLeft != 0   ) {
                long nextNumber = 0;
                try {
                    nextNumber = (Long)S2.take();
                    System.out.println("c1: taking " + nextNumber + " and adding to sum");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadProgrammingProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                }
                numbersLeft--;
                sum += nextNumber;
                
                
            }
            System.out.println("c1: sum is " + sum);
            
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public void testProducers(int numProducer) {
               
        BlockingQueue<Integer> S1 = new ArrayBlockingQueue(1000);
        S1.add(4);
        S1.add(5);
        S1.add(8);
        S1.add(12);
        S1.add(21);
        S1.add(22);
        S1.add(34);
        S1.add(35);
        S1.add(36);
        S1.add(37);
        S1.add(42);
       
        TOTAL_NUMBERS = S1.size();
        
        BlockingQueue<Integer> S2 = new ArrayBlockingQueue(1);
        
        ProducerThread[] threads = new ProducerThread[numProducer];
        
        for (int i = 0; i < numProducer; i++) {
            threads[i] = new ProducerThread(S1, S2, "P"+(i+1));
            threads[i].start();
        }
        
        
        
        ConsumerThread c1 = new ConsumerThread(S2);
        c1.start();
        try {
            c1.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadProgrammingProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    public static void main(String[] args) {
        ThreadProgrammingProducerConsumer prog = new ThreadProgrammingProducerConsumer();
        
        System.out.println("\nTEST with 1 producer thread");
        long timeBefore = System.currentTimeMillis();
        prog.testProducers(1);
        long timeAfter = System.currentTimeMillis();
        long elapsedTime1 = timeAfter - timeBefore;
        
        System.out.println("\nTEST with 2 producer threads");
        timeBefore = System.currentTimeMillis();
        prog.testProducers(2);
        timeAfter = System.currentTimeMillis();
        long elapsedTime2 = timeAfter - timeBefore;
        
        System.out.println("\nTEST with 3 producer threadss");
        timeBefore = System.currentTimeMillis();
        prog.testProducers(3);
        timeAfter = System.currentTimeMillis();
        long elapsedTime3 = timeAfter - timeBefore;
                
        System.out.println("\nTEST with 4 producer threads");
        timeBefore = System.currentTimeMillis();
        prog.testProducers(4);
        timeAfter = System.currentTimeMillis();
        long elapsedTime4 = timeAfter - timeBefore;
        
        System.out.println("\n1 thread took: " + elapsedTime1 / 1000.0 + " seconds");
        System.out.println("2 threads took: " + elapsedTime2 / 1000.0 + " seconds");
        System.out.println("3 threads took: " + elapsedTime3 / 1000.0 + " seconds");
        System.out.println("4 threads took: " + elapsedTime4 / 1000.0 + " seconds");
    }
    
}
