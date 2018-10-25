/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.exceptions;


public class DuplicateUserException extends Exception {

    public DuplicateUserException() {
        super("User already exists in the system");
    }
}
