
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class SerializableRoom implements Serializable {
     private static final long serialVersionUID = 1L;

    private String type;
    private Color color;
    private int x, y, width, height;
    private ArrayList<SerializableFixture> fixtures;

    public SerializableRoom(Room room) {
        this.type = room.getType();
        this.color = room.getColor();
        this.x = room.getX();
        this.y = room.getY();
        this.width = room.getWidth();
        this.height = room.getHeight();
        this.fixtures = new ArrayList<>();
        for (Fixture fixture : room.getFixtures()) {
            this.fixtures.add(new SerializableFixture(fixture)); // Convert fixtures to serializable
        }
    }

    public Room toRoom(Frame frame) {
        Room room = new Room(type, color);
        room.setBounds(x, y, width, height);
        room.setBackground(color); // Restore color
        room.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK, 2)); // Restore border
        room.setType(type);
        // Re-add listeners
        room.addMouseListener(frame);
        room.addMouseMotionListener(frame);

        // Restore fixtures
        for (SerializableFixture sFixture : fixtures) {
            Fixture fixture = sFixture.toFixture(frame);
            room.add(fixture);
            room.getFixtures().add(fixture); // Add to room's fixture list
        }
        return room;
    }
}
