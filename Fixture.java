
import java.awt.Color;
import javax.swing.JPanel;

public class Fixture extends JPanel {
    String type;
    Color color;

    

    public Fixture(String type,Color color){
        this.type=type;
        this.color=color;
    }

    public String getType() { return type; }
    public Color getColor() { return color; }
    
   
}
