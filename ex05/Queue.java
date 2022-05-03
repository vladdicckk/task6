package ex05;

import ex04.Command;

/**
 * @author Skorik Artem
 * @version 1.0
 */
public interface Queue {
    void put(Command cmd);

    Command take();
}