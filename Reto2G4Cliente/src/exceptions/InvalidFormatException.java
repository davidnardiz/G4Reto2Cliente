/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Gonzalo
 */
public class InvalidFormatException extends Exception {

    /**
     * Creates a new instance of <code>InvalidFormatException</code> without
     * detail message.
     */
    public InvalidFormatException() {
    }

    /**
     * Constructs an instance of <code>InvalidFormatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidFormatException(String msg) {
        super(msg);
    }
}
