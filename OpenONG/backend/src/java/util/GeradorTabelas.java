package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GeradorTabelas {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OpenONGPU");
        emf.close();
    }
}