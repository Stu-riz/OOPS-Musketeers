
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class SerializableFixture implements Serializable{
    private static final long serialVersionUID = 1L;

     private String type;
    private int x, y, width, height;
    private int rotCounter; // Rotation state
    private byte[] imageData; // Image data

    public SerializableFixture(Fixture fixture) {
        this.type = fixture.getType();
        this.x = fixture.getX();
        this.y = fixture.getY();
        this.width = fixture.getWidth();
        this.height = fixture.getHeight();
        this.rotCounter = fixture.rotCounter;

        // Serialize image
        if (fixture.getIcon() instanceof ImageIcon) {
            try {
                ImageIcon icon = (ImageIcon) fixture.getIcon();
                BufferedImage bufferedImage = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );
                Graphics g = bufferedImage.createGraphics();
                icon.paintIcon(null, g, 0, 0);
                g.dispose();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                imageData = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                imageData = null;
            }
        }
    }

    public Fixture toFixture(Frame frame) {
        Fixture fixture = new Fixture(type, null); // Create fixture without icon
        fixture.setBounds(x, y, width, height);
        fixture.rotCounter = rotCounter;

        // Deserialize image
        if (imageData != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
                BufferedImage bufferedImage = ImageIO.read(bais);
                ImageIcon icon = new ImageIcon(bufferedImage);
                fixture.setIcon(icon); // Set the icon for the fixture
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add listeners
        fixture.addMouseListener(frame);
        fixture.addMouseMotionListener(frame);

        return fixture;
    }
    
}
