
import java.io.Serializable;

public class SerializableFixture implements Serializable{
    private static final long serialVersionUID = 1L;

    private String type;
    private int x, y, width, height;
    private int rotCounter; // Rotation state

    public SerializableFixture(Fixture fixture) {
        this.type = fixture.getType();
        this.x = fixture.getX();
        this.y = fixture.getY();
        this.width = fixture.getWidth();
        this.height = fixture.getHeight();
        this.rotCounter = fixture.rotCounter;
    }

    public Fixture toFixture(Frame frame) {
        Fixture fixture = new Fixture(type, null); // Set icon manually later if needed
        fixture.setBounds(x, y, width, height);
        fixture.rotCounter = rotCounter;

        // Add listeners
        fixture.addMouseListener(frame);
        fixture.addMouseMotionListener(frame);

        return fixture;
    }
    
}
