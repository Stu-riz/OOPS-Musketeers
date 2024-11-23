
import java.awt.Component;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
    public ImageIcon getIcon() {
    for (Component component : getComponents()) {
        if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            if (label.getIcon() instanceof ImageIcon) {
                return (ImageIcon) label.getIcon();
            }
        }
    }
    return null;
}
public void setIcon(ImageIcon icon) {
    // Remove all existing components (e.g., old JLabel with the previous icon)
    this.removeAll();

    // Create a new JLabel with the provided icon
    if (icon != null) {
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(0, 0, this.getWidth(), this.getHeight()); // Fit the icon to the fixture bounds
        this.add(iconLabel);
    }

    // Revalidate and repaint to refresh the UI
    this.revalidate();
    this.repaint();
}
    
   
}
