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
public class IncorrectCredentialsException extends Exception {

    /**
     * Creates a new instance of <code>IncorrectCredentialsException</code>
     * without detail message.
     */
    public IncorrectCredentialsException() {
    }

    /**
     * Constructs an instance of <code>IncorrectCredentialsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public IncorrectCredentialsException(String msg) {
        super(msg);
    }
}
