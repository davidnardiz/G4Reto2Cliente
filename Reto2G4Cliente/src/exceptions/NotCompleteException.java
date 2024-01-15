/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author David
 */
public class NotCompleteException extends Exception {

    /**
     * Creates a new instance of <code>notCompleteException</code> without
     * detail message.
     */
    public NotCompleteException() {
    }

    /**
     * Constructs an instance of <code>notCompleteException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotCompleteException(String msg) {
        super(msg);
    }
}
