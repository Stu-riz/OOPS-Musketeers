
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class Room extends JPanel implements Serializable, MouseListener,MouseMotionListener {
    private static final long serialVersionUID = 1L;
    String type;
    Color color;
    private Frame frame=Project2.frame;
    transient public ArrayList<Fixture> fixtures=new ArrayList<>();
    

    public Room(String type,Color color){
        this.type=type;
        this.color=color;
        this.setLayout(null); // Allow absolute positioning for fixtures
}
     private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serialize default fields
        // Optionally, serialize custom transient fields here
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Deserialize default fields
        this.fixtures = new ArrayList<>(); // Reinitialize transient fields
        this.setLayout(null); // Reapply layout
    }

    public String getType() { return type; }
    public Color getColor() { return color; }
    public ArrayList<Fixture> getFixtures() {
        return fixtures;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    public class FixturePlacer {
        private ArrayList<Fixture> existingFixtures;
        private int canvasWidth;
        private int canvasHeight;
        private static final int PADDING = 0; // Space between rooms
    
        public FixturePlacer(ArrayList<Fixture> fixtures, int canvasWidth, int canvasHeight) {
            this.existingFixtures = fixtures;
            this.canvasWidth = frame.getClickSelectedRoom().getWidth();
            this.canvasHeight = frame.getClickSelectedRoom().getHeight();
        }
    
        public Point calculateNextFixturePosition(int fixtureWidth, int fixtureHeight) {
            // If no rooms exist, place at origin
            if (existingFixtures.isEmpty()) {
                return new Point(0, 0);
            }
    
            // Create a grid to track occupied spaces
            boolean[][] occupied = new boolean[canvasHeight][canvasWidth];
            
            // Mark existing rooms as occupied
            for (Fixture fixture : existingFixtures) {
                Rectangle bounds = fixture.getBounds();
                for (int y = bounds.y; y < bounds.y + bounds.height + PADDING; y++) {
                    for (int x = bounds.x; x < bounds.x + bounds.width + PADDING; x++) {
                        if (y < canvasHeight && x < canvasWidth) {
                            occupied[y][x] = true;
                        }
                    }
                }
            }
    
            // Search for next available position in row-major order
            for (int y = 0; y < canvasHeight - fixtureHeight; y++) {
                for (int x = 0; x < canvasWidth - fixtureWidth; x++) {
                    if (isSpaceAvailable(occupied, x, y, fixtureWidth, fixtureHeight)) {
                        return new Point(x, y);
                    }
                }
            }
    
            return null; // No valid position found
        }
    
        private boolean isSpaceAvailable(boolean[][] occupied, int x, int y, 
                                       int width, int height) {
            // Check if the entire room space is available
            for (int dy = 0; dy < height + PADDING; dy++) {
                for (int dx = 0; dx < width + PADDING; dx++) {
                    if (y + dy >= canvasHeight || x + dx >= canvasWidth || 
                        occupied[y + dy][x + dx]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
    public boolean checkFixtureOverlap(Fixture currentFixture) {
        int x1 = currentFixture.getX();
        int y1 = currentFixture.getY();
        int w1 = currentFixture.getWidth();
        int h1 = currentFixture.getHeight();
    
        // Check against all other rooms in the ArrayList
        for (Fixture otherFixture : fixtures) {
            // Skip if we're checking against the same room
            if (otherFixture == currentFixture) {
                continue;
            }
    
            // Get the bounds of the other room
            int x2 = otherFixture.getX();
            int y2 = otherFixture.getY();
            int w2 = otherFixture.getWidth();
            int h2 = otherFixture.getHeight();
    
            // Check if the rooms overlap
            // Two rooms overlap if they overlap both horizontally and vertically
            boolean overlapX = (x1 < x2 + w2) && (x1 + w1 > x2);
            boolean overlapY = (y1 < y2 + h2) && (y1 + h1 > y2);
    
            // If both overlap in X and Y direction, we have an intersection
            if (overlapX && overlapY) {
                return true;  // Found an overlap
            }
            
        }
        
        return false;

    }



    public  boolean isFixtureWithinBounds(Fixture fixture) {
        return fixture.getX() >= 0 && 
               fixture.getY() >= 0 && 
               fixture.getX() + fixture.getWidth() <= frame.getClickSelectedRoom().getWidth() && 
               fixture.getY() + fixture.getHeight() <= frame.getClickSelectedRoom().getHeight();
    }

    public boolean isValidFixturePosition(Fixture fixture) {
        return !checkFixtureOverlap(fixture) && isFixtureWithinBounds(fixture);
    }

    
    public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
    Image img = icon.getImage();  
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
    return new ImageIcon(resizedImage);
}
    Point fixpos;
    public Fixture fix1;
    ImageIcon currImageIcon;
    ImageIcon resizedIcon;

    public void addChair(){
        FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("chair_0.jpg");
        fix1 = new Fixture("CHAIR", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }
        

        

    }
    
    public void addDiningTable(){
        FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("diningtable_0.jpeg");
        fix1 = new Fixture("DININGTABLE", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public void addKitchenSink(){
        FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("kitchensink_0.png");
        fix1 = new Fixture("KITCHENSINK", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public void addWashBasin(){
        FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("washbasin_0.jpg");
        fix1 = new Fixture("WASHBASIN", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public void addBed(){
        FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("bed_0.png");
        fix1 = new Fixture("BED", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public void addTable(){
        FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("table_0.jpg");
        fix1 = new Fixture("TABLE", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public void addSofa(){FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("sofa_0.png");
        fix1 = new Fixture("SOFA", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }}
    public void addStove(){
        FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("stove_0.png");
        fix1 = new Fixture("STOVE", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public void addCommode(){FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("commode_0.png");
        fix1 = new Fixture("COMMODE", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }}
    public void addShower(){FixturePlacer fixPlacer = new FixturePlacer(fixtures, frame.getClickSelectedRoom().getWidth(), frame.getClickSelectedRoom().getHeight());
        fixpos = fixPlacer.calculateNextFixturePosition(
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        
        currImageIcon = new ImageIcon("shower_0.png");
        fix1 = new Fixture("SHOWER", currImageIcon);
        
        resizedIcon = new ImageIcon(
            currImageIcon.getImage().getScaledInstance(
                Integer.parseInt(frame.sizeTextFieldW.getText()), 
                Integer.parseInt(frame.sizeTextFieldH.getText()), 
                Image.SCALE_SMOOTH)
        );
        
        JLabel fixLabel = new JLabel(resizedIcon);
        fixLabel.setBounds(0, 0, 
            Integer.parseInt(frame.sizeTextFieldW.getText()), 
            Integer.parseInt(frame.sizeTextFieldH.getText())
        );
        fix1.add(fixLabel);
        
        if (fixpos != null) {
            // Set the fixture's bounds to the calculated position
            fix1.setBounds(
                fixpos.x, 
                fixpos.y, 
                Integer.parseInt(frame.sizeTextFieldW.getText()),
                Integer.parseInt(frame.sizeTextFieldH.getText())
            );
            
            if(!checkFixtureOverlap(fix1)) {
                fixtures.add(fix1);
                frame.getClickSelectedRoom().add(fix1);
                fix1.setVisible(true);
                fix1.addMouseListener(frame);
                fix1.addMouseMotionListener(frame);
                
                frame.getClickSelectedRoom().revalidate();
                frame.getClickSelectedRoom().repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place fixture here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the fixture",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }}
        
        
        
        JLabel clickSelectedFixture;
    @Override
    public void mouseDragged(MouseEvent e) {
       
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof JLabel) {
            if(clickSelectedFixture==null){
                clickSelectedFixture=(JLabel)e.getSource();
                frame.saveComboBox.setEnabled(false);
                

            }
            else{
                JOptionPane.showMessageDialog(this, 
            "cannot select multiple fixtures",
            "Error",
            JOptionPane.ERROR_MESSAGE);
            }
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

   
    
}
