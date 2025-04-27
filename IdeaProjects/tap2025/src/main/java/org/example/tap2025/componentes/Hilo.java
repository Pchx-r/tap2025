package org.example.tap2025.componentes;


import javafx.scene.control.ProgressBar;

public class Hilo extends Thread {

    private ProgressBar pgbRuta;

    public Hilo(String nombre, ProgressBar pgbRuta) {
        super(nombre);
        this.pgbRuta = pgbRuta;
    }

    @Override
    public void run() {
        super.run();
        double avance = 0;
        while (avance < 1) {
            avance += Math.random() * .01;
            this.pgbRuta.setProgress(avance);
            try {
                sleep((long) (Math.random() * 500));
            } catch (InterruptedException e) {
            }
        }
    }
    /*@Override
    public void run() {
        super.run();
        for (int i = 1; i <= 10; i++) {
            try{
                sleep((long) (Math.random()*3000));
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            System.out.println("El corredor " + this.getName() + " llego al km " + i);
        }*/

}

