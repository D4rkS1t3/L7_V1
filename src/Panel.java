import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements MouseWheelListener {
    private ArrayList<Kula> listaKul;
    private int size = 20;
    private Timer timer;
    private final int DELAY = 16; // dla 60fps -> 1s/60 = 0,016s
    private int sumCollision=0;

    public Panel() {
        listaKul = new ArrayList<>();
        setBackground(Color.BLACK);
        addMouseListener(new Event());
        timer = new Timer(DELAY, new Event());
        timer.start();
        addMouseWheelListener(this);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int noteches=e.getWheelRotation();
        size-=noteches*5;
        if (size<10) {
            size=10;
        } else if (size>100) {
            size=100;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Kula k : listaKul) {
            g.setColor(k.color);
            g.fillOval(k.x, k.y, k.size, k.size); // Używam fillOval zamiast drawOval dla lepszego wizualnego efektu
        }
        g.setColor(Color.YELLOW);
        g.drawString(Integer.toString(listaKul.size()), 40, 40);
    }

    private void checkCollison() {
        for (int i = 0; i < listaKul.size(); i++) {
            Kula k1=listaKul.get(i);
            for (int j = i+1; j <listaKul.size() ; j++) {
                Kula k2=listaKul.get(j);
                double dx=k2.x-k1.x;
                double dy=k2.y-k1.y;
                double distance=Math.sqrt(dx*dx+dy*dy);
                if (distance<k1.size/2+k2.size/2) {
                    resolveCollsion(k1, k2);
                    checkSave(k1,k2);
                }
            }
        }
    }

    public void saveCollision(Kula kula) {
        try (FileWriter fileWriter = new FileWriter("collision.csv", true)) {
            fileWriter.write(kula.x + "," + kula.y + "," + kula.size + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void checkSave(Kula k1, Kula k2) {
        if (listaKul.size()>50) {
            sumCollision++;
            if (sumCollision>=30) {
                saveCollision(k1);
                saveCollision(k2);
                sumCollision=0;
            }
        }
        else {
            saveCollision(k1);
            saveCollision(k2);
        }
    }

    private void resolveCollsion(Kula k1, Kula k2) {
        // Prosty model zderzeń,
        // gdzie kule wymieniają się prędkościami (dla uproszczenia)
        int tempXSpeed = k1.xspeed;
        int tempYSpeed=k1.yspeed;
        k1.xspeed=k2.xspeed;
        k1.yspeed=k2.yspeed;
        k2.xspeed=tempXSpeed;
        k2.yspeed=tempYSpeed;
    }

    private class Event implements MouseListener, ActionListener {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            listaKul.add(new Kula(e.getX() - size / 2, e.getY() - size / 2, size)); // Centrowanie kul względem kliknięcia
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void actionPerformed(ActionEvent e) {
            checkCollison();
            for (Kula k : listaKul) {
                k.update(getWidth(), getHeight());
            }
            repaint();
        }
    }


}
