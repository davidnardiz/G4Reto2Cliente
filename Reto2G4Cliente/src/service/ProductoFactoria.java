/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 * Factoria del producto
 *
 * @author Gonzalo
 */
public class ProductoFactoria {

    public static ProductoInterface createInterface() {
        ProductoInterface inter = new ProductoRestCliente();
        return inter;
    }
}
