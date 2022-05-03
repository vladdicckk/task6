package ex05;

import ex04.Command;

import java.util.Vector;

/**
 * @author Skorik Artem
 * @version 1.0
 */
public class CommandQueue implements Queue {

    private Vector<Command> tasks;

    private boolean waiting;

    private boolean shutdown;

    public void shutdown() {
        shutdown = true;
    }

    public CommandQueue() {
        tasks = new Vector<Command>();
        waiting = false;
        new Thread(new Worker()).start();
    }

    @Override
    public void put(Command r) {
        tasks.add(r);
        if (waiting) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    @Override
    public Command take() {
        if (tasks.isEmpty()) {
            synchronized (this) {
                waiting = true;
                try {
                    wait();
                } catch (InterruptedException ie) {
                    waiting = false;
                }
            }
        }
        return (Command)tasks.remove(0);
    }

    private class Worker implements Runnable {
        /** Извлекает из очереди
         * готовые к выполнению
         * задачи; шаблон
         * Worker Thread */
        public void run() {
            while (!shutdown) {
                Command r = take();
                r.execute();

            }
        }
    }
}