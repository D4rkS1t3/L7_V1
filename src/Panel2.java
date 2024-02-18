import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Panel2 extends JPanel {
    private ArrayList<Kula> listaKolizji = new ArrayList<>();

    public Panel2() {
        setBackground(Color.BLACK);
        odczytajKolizje("collision.csv");
    }

    private void odczytajKolizje(String nazwaPliku) {
        File plik = new File(nazwaPliku);
        try (Scanner scanner = new Scanner(plik)) {
            while (scanner.hasNextLine()) {
                String linia = scanner.nextLine();
                String[] dane = linia.split(",");
                int x = Integer.parseInt(dane[0]);
                int y = Integer.parseInt(dane[1]);
                int size = Integer.parseInt(dane[2]);
                listaKolizji.add(new Kula(x, y, size));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku: " + nazwaPliku);
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Kula k : listaKolizji) {
            g.setColor(k.color);
            g.drawOval(k.x - k.size / 2, k.y - k.size / 2, k.size, k.size);
        }
    }
}
