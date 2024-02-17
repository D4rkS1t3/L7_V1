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

    private void saveCollison(Kula k1, Kula k2) {
        File fileName=new File("collision.txt");
        try {
            FileWriter file=new FileWriter(fileName, true);
            file.append("kula1 x:"+k1.x+", y:"+k1.y+" rozmiar "+k1.size+" kula2 x:"+k2.x+", y:"+k2.y+" rozmiar "+k2.size+"\n");
            file.close();
        } catch (IOException e) {
            System.err.println(e.getCause());
        }
    }

    private void checkSave(Kula k1, Kula k2) {
        if (listaKul.size()>50) {
            sumCollision++;
            if (sumCollision>=30) {
                saveCollison(k1,k2);
                sumCollision=0;
            }
        }
        else {
            saveCollison(k1,k2);
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

    private class Kula {
        public int x, y, size, xspeed, yspeed;
        public Color color;
        private final int MAX_SPEED = 5;

        public Kula(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
            do {
                xspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
            } while (xspeed==0);
            do {
                yspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
            } while (yspeed==0);
            }

        public void update(int maxWidth, int maxHeight) {
            x += xspeed;
            y += yspeed;
            if (x <= 0 || x + size >= maxWidth) {
                xspeed = -xspeed;
            }
            if (y <= 0 || y + size >= maxHeight) {
                yspeed = -yspeed;
            }
        }
    }
}
