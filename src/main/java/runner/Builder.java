/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import javax.persistence.Persistence;

/**
 *
 * @author ejer
 */
public class Builder {

    public static void main(String[] args) {
        Persistence.generateSchema("yolo", null);
    }
}
