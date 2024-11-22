
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Fixture extends JPanel implements Serializable {
    private static final long serialVersionUID = 1L;
     String type;
    transient ImageIcon icon;
    public int rotCounter=0;

    

    public Fixture(String type,ImageIcon icon){
        this.type=type;
        this.icon=icon;
        this.setLayout(null);
    }
     private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serialize default fields
        // Save the image icon path if necessary
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Deserialize default fields
        // Recreate icon from file path if necessary
    }

    public String getType() { return type; }
    public ImageIcon getColor() { return icon; }
    
   
}
