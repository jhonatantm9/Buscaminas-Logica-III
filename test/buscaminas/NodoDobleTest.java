/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jhona
 */
public class NodoDobleTest {
    
    public NodoDobleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDato method, of class NodoDoble.
     */
    @Test
    public void testGetDato() {
        System.out.println("getDato");
        NodoDoble instance = new NodoDoble(5);
        Object expResult = 5;
        Object result = instance.getDato();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDato method, of class NodoDoble.
     */
    @Test
    public void testSetDato() {
        System.out.println("setDato");
        Object dato = 10;
        NodoDoble instance = new NodoDoble(5);
        instance.setDato(dato);
        assertEquals(dato, instance.getDato());
    }

    /**
     * Test of getLi method, of class NodoDoble.
     */
    @Test
    public void testGetLi() {
        System.out.println("getLi");
        NodoDoble li = new NodoDoble(5);
        NodoDoble instance = new NodoDoble(10);
        instance.setLi(li);
        NodoDoble result = instance.getLi();
        assertEquals(li, result);
    }

    /**
     * Test of setLi method, of class NodoDoble.
     */
    @Test
    public void testSetLi() {
        System.out.println("setLi");
        NodoDoble li = new NodoDoble(5);
        NodoDoble instance = new NodoDoble(10);
        instance.setLi(li);
        NodoDoble result = instance.getLi();
        assertEquals(li, result);
    }

    /**
     * Test of getLd method, of class NodoDoble.
     */
    @Test
    public void testGetLd() {
        System.out.println("getLd");
        NodoDoble ld = new NodoDoble(8);
        NodoDoble instance = new NodoDoble(4);
        instance.setLd(ld);
        NodoDoble result = instance.getLd();
        assertEquals(ld, result);
    }

    /**
     * Test of setLd method, of class NodoDoble.
     */
    @Test
    public void testSetLd() {
        System.out.println("setLd");
        NodoDoble ld = new NodoDoble(8);
        NodoDoble instance = new NodoDoble(4);
        instance.setLd(ld);
        NodoDoble result = instance.getLd();
        assertEquals(ld, result);
    }
    
}
