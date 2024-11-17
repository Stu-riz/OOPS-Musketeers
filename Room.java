
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;



public class Room extends JPanel {
    String type;
    Color color;
    ArrayList<Fixture> fixtures=new ArrayList<>();
    

    public Room(String type,Color color){
        this.type=type;
        this.color=color;
    }

    public String getType() { return type; }
    public Color getColor() { return color; }
    public void addFixture(Room room){


    }

   
    
}
