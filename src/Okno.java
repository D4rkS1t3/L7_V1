import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
public class Okno {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Moje okno!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new Panel());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("zamykanie okna");
                showCollision();
            }
        });
        frame.setVisible(true);
    }

    public static void showCollision() {
        JFrame frame = new JFrame("Kolizje");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Panel2());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("czyszczenie");
                try (FileWriter fileWriter = new FileWriter("collision.csv", false)) {
                    fileWriter.write(""); // Zapisuje pusty ciąg, czyści plik
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        frame.setVisible(true);

    }
}
