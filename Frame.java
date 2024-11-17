import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Frame extends JFrame implements ActionListener,MouseListener,MouseMotionListener{
    private ArrayList<Room>rooms=new ArrayList<>();

    
    JComboBox<Object> addRoomComboBox;
    JButton sizeSubmitButton;
    JButton selectRoomButton;
    JButton spareButton;

    //JTextField roomSizeTextField;

    JPanel upperPanel;
    JPanel leftPanel;
    JPanel canvasPanel;

    JTextField sizeTextFieldH;
    JTextField sizeTextFieldW;

    public String[] roomTypes={"BEDROOM","WASHROOM","LIVINGROOM","KITCHEN"};
    public Color[] roomColors={Color.GREEN,Color.BLUE,Color.ORANGE,Color.PINK};

    
    
    

     public Frame(){

        for(Room room : rooms) {
            room.addMouseListener(this);
            room.addMouseMotionListener(this);
        }
       

        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setLayout(null);
        this.setVisible(true);
        
        
        

       

         upperPanel = new JPanel();
         leftPanel = new JPanel();
         canvasPanel = new JPanel();

        upperPanel.setBackground(Color.BLACK);
        leftPanel.setBackground((new Color(235,254,255)));
        canvasPanel.setBackground(Color.GRAY);
        
        upperPanel.setLayout(null);
        leftPanel.setLayout(new GridLayout(14,1));
        canvasPanel.setLayout(null);

       

        upperPanel.setBounds(0,0,this.getWidth(),50);
        leftPanel.setBounds(0,50,200,this.getHeight()-50);
        canvasPanel.setBounds(200,50,this.getWidth()-200,this.getHeight()-50);

        upperPanel.setVisible(true);
        leftPanel.setVisible(true);
        canvasPanel.setVisible(true);

        canvasPanel.addMouseListener(this);
        canvasPanel.addMouseMotionListener(this);
        

        this.add(upperPanel);
        this.add(leftPanel);
        this.add(canvasPanel);

        JLabel label1=new JLabel();
        label1.setText("MENU BAR");
        label1.setFont(new Font("SERIF",Font.PLAIN,15));
        label1.setForeground(new Color(98,137,60));
        label1.setHorizontalTextPosition(JLabel.LEFT);

        

        addRoomComboBox=new JComboBox(roomTypes);
        JButton addDoorButton=new JButton();
        JButton addWindowButton=new JButton();
        JButton addChairButton=new JButton();
        JButton addDiningButton=new JButton();
        JButton addKitchenSinkButton=new JButton();
        JButton addWashBasinButton=new JButton();
        JButton addBedButton=new JButton();
        JButton addTableButton=new JButton();
        JButton addSofaButton=new JButton();
        JButton addStoveButton=new JButton();
        JButton addCommodeButton=new JButton();
        JButton addShowerButton=new JButton();
        //JButton clearBoardButton=new JButton();

       
        JButton saveButton=new JButton();
        selectRoomButton=new JButton();
        JButton selectFixtureButton=new JButton();
        JButton dragDropButton=new JButton();
        JButton rotateFixtureButton=new JButton();


        sizeTextFieldH=new JTextField("400",5);
        sizeTextFieldW=new JTextField("400",5);
        
        sizeTextFieldW.setBounds(1350, 0, 100, 50);
        sizeTextFieldH.setBounds(1450,0,100,50);
        spareButton=new JButton();
        sizeSubmitButton=new JButton("W,H");
        sizeSubmitButton.setBounds(1250,0,100,50);
        sizeSubmitButton.addActionListener(this);

        upperPanel.add(sizeSubmitButton);
        upperPanel.add(sizeTextFieldH);
        upperPanel.add(sizeTextFieldW);
        sizeSubmitButton.setVisible(true);
        sizeTextFieldH.setVisible(true);
        sizeTextFieldW.setVisible(true);
        
       
        

        upperPanel.add(saveButton);
        upperPanel.add(selectRoomButton);
        upperPanel.add(selectFixtureButton);
        upperPanel.add(rotateFixtureButton);
        upperPanel.add(dragDropButton);
        

        


        saveButton.setText("SAVE");
        saveButton.setFont(new Font("Arial",Font.PLAIN,12));
        saveButton.setBounds(0,0,200,upperPanel.getHeight());
        saveButton.setFocusable(false);
        
        selectRoomButton.setText("SELECT A ROOM");
        selectRoomButton.setFont(new Font("Arial",Font.PLAIN,12));
        selectRoomButton.setBounds(200,0,200,upperPanel.getHeight());
        selectRoomButton.setFocusable(false);

        selectFixtureButton.setText("SELECT A FIXTURE");
        selectFixtureButton.setBounds(400,0,250,upperPanel.getHeight());
        selectFixtureButton.setFocusable(false);

        rotateFixtureButton.setText("ROTATE FIXTURE");
        rotateFixtureButton.setBounds(650,0,250,upperPanel.getHeight());
        rotateFixtureButton.setFocusable(false);

        dragDropButton.setText("DRAG AND DROP");
        dragDropButton.setBounds(900,0,350,upperPanel.getHeight());
        dragDropButton.setFocusable(false);
        dragDropButton.setMaximumSize(new Dimension(100, 50));

        upperPanel.revalidate();
        upperPanel.repaint();
        
        

      
        leftPanel.add(addRoomComboBox);
        leftPanel.add(addDoorButton);
        leftPanel.add(addWindowButton);
        leftPanel.add(addChairButton);
        leftPanel.add(addDiningButton);
        leftPanel.add(addKitchenSinkButton);
        leftPanel.add(addWashBasinButton);
        leftPanel.add(addBedButton);
        leftPanel.add(addTableButton);
        leftPanel.add(addSofaButton);
        leftPanel.add(addStoveButton);
        leftPanel.add(addCommodeButton);
        leftPanel.add(addShowerButton);
        //leftPanel.add(clearBoardButton);

        

        
        addRoomComboBox.setBounds(0,0,leftPanel.getWidth(),40);
        addRoomComboBox.setFocusable(false);
        addRoomComboBox.setBackground(Color.ORANGE);
        addRoomComboBox.addActionListener(this);

        


        addDoorButton.setText("ADD DOOR");
        addDoorButton.setBounds(0,0,leftPanel.getWidth(),40);
        addDoorButton.setFocusable(false);
        addDoorButton.setBackground(Color.GREEN);

        addWindowButton.setText("ADD WINDOW");
        addWindowButton.setBounds(0,0,leftPanel.getWidth(),40);
        addWindowButton.setFocusable(false);
        addWindowButton.setBackground(Color.PINK);

        //clearBoardButton.setText("CLEAR CANVAS");
        //clearBoardButton.setBounds(0,0,100,40);
        //clearBoardButton.setFocusable(false);
        //clearBoardButton.setBackground(Color.RED);

        addChairButton.setText("CHAIR");
        addChairButton.setBounds(0,0,leftPanel.getWidth(),40);
        addChairButton.setFocusable(false);


        addDiningButton.setText("DINING TABLE");
        addDiningButton.setBounds(0,0,leftPanel.getWidth(),40);
        addDiningButton.setFocusable(false);

        addKitchenSinkButton.setText("KITCHEN SINK");
        addKitchenSinkButton.setBounds(0,0,leftPanel.getWidth(),40);
        addKitchenSinkButton.setFocusable(false);

        addWashBasinButton.setText("WASH BASIN");
        addWashBasinButton.setBounds(0,0,leftPanel.getWidth(),40);
        addWashBasinButton.setFocusable(false);

        addBedButton.setText("BED");
        addBedButton.setBounds(0,0,leftPanel.getWidth(),40);
        addBedButton.setFocusable(false);

        addTableButton.setText("TABLE");
        addTableButton.setBounds(0,0,leftPanel.getWidth(),40);
        addTableButton.setFocusable(false);

        addSofaButton.setText("SOFA");
        addSofaButton.setBounds(0,0,leftPanel.getWidth(),40);
        addSofaButton.setFocusable(false);

        addStoveButton.setText("STOVE");
        addStoveButton.setBounds(0,0,leftPanel.getWidth(),40);
        addStoveButton.setFocusable(false);

        addCommodeButton.setText("COMMODE");
        addCommodeButton.setBounds(0,0,leftPanel.getWidth(),40);
        addCommodeButton.setFocusable(false);

        addShowerButton.setText("SHOWER");
        addShowerButton.setBounds(0,0,leftPanel.getWidth(),40);
        addShowerButton.setFocusable(false);




    }
    

    private static class Gap {
        int rowY;
        int startX;
        int availableWidth;
        
        Gap(int rowY, int startX, int availableWidth) {
            this.rowY = rowY;
            this.startX = startX;
            this.availableWidth = availableWidth;
        }
    }
    public Point calculateNextPosition(
                                           int newPanelWidth, 
                                           int newPanelHeight) {

                    int PADDING = 0; // Add some padding between rooms
    
    if (rooms.isEmpty()) {
        return new Point(PADDING, PADDING);
    }

    // Create a TreeMap to store rows and their heights
    // Key is Y coordinate, Value is the height of tallest room in that row
    TreeMap<Integer, Integer> rowHeights = new TreeMap<>();
    
    // Create a map to store occupied spaces in each row
    // Key is Y coordinate, Value is list of X ranges that are occupied
    Map<Integer, List<Range>> occupiedSpaces = new HashMap<>();
    
    // First pass: Identify all rows and their heights
    for (Room room : rooms) {
        int rowY = room.getY();
        int roomHeight = room.getHeight();
        
        // Update row height if this room is taller
        rowHeights.merge(rowY, roomHeight, Math::max);
        
        // Add occupied range to the row
        List<Range> rowRanges = occupiedSpaces.computeIfAbsent(rowY, k -> new ArrayList<>());
        rowRanges.add(new Range(room.getX(), room.getX() + room.getWidth()));
    }
    
    // Try to fit in existing rows first
    for (Map.Entry<Integer, List<Range>> entry : occupiedSpaces.entrySet()) {
        int rowY = entry.getKey();
        List<Range> occupiedRanges = entry.getValue();
        
        // Sort ranges by start position
        Collections.sort(occupiedRanges);
        
        // Check for gap at the start
        if (!occupiedRanges.isEmpty() && occupiedRanges.get(0).start > PADDING + newPanelWidth) {
            return new Point(PADDING, rowY);
        }
        
        // Check gaps between rooms
        for (int i = 0; i < occupiedRanges.size() - 1; i++) {
            Range current = occupiedRanges.get(i);
            Range next = occupiedRanges.get(i + 1);
            
            int gapSize = next.start - current.end;
            if (gapSize >= newPanelWidth + PADDING * 2) {
                return new Point(current.end + PADDING, rowY);
            }
        }
        
        // Check if there's space at the end of the row
        if (!occupiedRanges.isEmpty()) {
            Range lastRange = occupiedRanges.get(occupiedRanges.size() - 1);
            int endX = lastRange.end + PADDING;
            if (endX + newPanelWidth + PADDING <= canvasPanel.getWidth()) {
                return new Point(endX, rowY);
            }
        }
    }
    
    // If we couldn't fit in existing rows, create a new row
    int newRowY;
    if (rowHeights.isEmpty()) {
        newRowY = PADDING;
    } else {
        // Get the last row's Y coordinate and height
        Map.Entry<Integer, Integer> lastRow = rowHeights.lastEntry();
        newRowY = lastRow.getKey() + lastRow.getValue() + PADDING;
    }
    
    // Make sure we're not exceeding canvas height
    if (newRowY + newPanelHeight + PADDING > canvasPanel.getHeight()) {
        // If we would exceed height, try to optimize layout or show error
        JOptionPane.showMessageDialog(this, 
            "Not enough space to add new room. Consider reorganizing the layout.",
            "Space Error",
            JOptionPane.WARNING_MESSAGE);
        return null;
    }
    
    return new Point(PADDING, newRowY);
}
        

private static class Range implements Comparable<Range> {
    int start;
    int end;
    
    Range(int start, int end) {
        this.start = start;
        this.end = end;
    }
    
    @Override
    public int compareTo(Range other) {
        return Integer.compare(this.start, other.start);
    }
}
    
  Room overlappedRoom;


    public boolean checkOverlap(Room currentRoom) {
        int x1 = currentRoom.getX();
        int y1 = currentRoom.getY();
        int w1 = currentRoom.getWidth();
        int h1 = currentRoom.getHeight();
    
        // Check against all other rooms in the ArrayList
        for (Room otherRoom : rooms) {
            // Skip if we're checking against the same room
            if (otherRoom == currentRoom) {
                continue;
            }
    
            // Get the bounds of the other room
            int x2 = otherRoom.getX();
            int y2 = otherRoom.getY();
            int w2 = otherRoom.getWidth();
            int h2 = otherRoom.getHeight();
    
            // Check if the rooms overlap
            // Two rooms overlap if they overlap both horizontally and vertically
            boolean overlapX = (x1 < x2 + w2) && (x1 + w1 > x2);
            boolean overlapY = (y1 < y2 + h2) && (y1 + h1 > y2);
    
            // If both overlap in X and Y direction, we have an intersection
            if (overlapX && overlapY) {
                return true;  // Found an overlap
            }
            otherRoom=overlappedRoom;
        }
        
        return false;

    }



    private boolean isWithinBounds(Room room) {
        return room.getX() >= 0 && 
               room.getY() >= 0 && 
               room.getX() + room.getWidth() <= canvasPanel.getWidth() && 
               room.getY() + room.getHeight() <= canvasPanel.getHeight();
    }

    public boolean isValidPosition(Room room) {
        return !checkOverlap(room) && isWithinBounds(room);
    }

      
    


    public void addRoom(){}
        
        

    Room room1;
    Point pos;
    
    private int originalX;
    private int originalY;
    private Room selectedRoom = null;  // Currently selected room
    private int offsetX;              // Mouse click offset X from room's top-left corner
    private int offsetY;              // Mouse click offset Y from room's top-left corner
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        if(e.getSource()==addRoomComboBox){
          

            if(e.getSource()==sizeSubmitButton){
                

                

                canvasPanel.revalidate();
                canvasPanel.repaint();
                }

            if(addRoomComboBox.getSelectedItem()=="BEDROOM"){
                room1=new Room(roomTypes[0],roomColors[0]);
                pos = calculateNextPosition(Integer.parseInt(sizeTextFieldW.getText()),Integer.parseInt(sizeTextFieldH.getText()));
                if (pos != null) {  // Check if position was found
                    room1.setBounds(pos.x, pos.y, 
                                   Integer.parseInt(sizeTextFieldW.getText()),
                                   Integer.parseInt(sizeTextFieldH.getText()));
                    // ... rest of room addition code ...
                }
                room1.setBounds(pos.x,pos.y, Integer.parseInt(sizeTextFieldW.getText()),Integer.parseInt(sizeTextFieldH.getText()));
                if(!checkOverlap(room1)){
                    rooms.add(room1);
               
                    room1.setBackground(roomColors[0]);
                    room1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                     canvasPanel.add(room1);
                    room1.setVisible(true);
                    room1.addMouseListener(this);
                    room1.addMouseMotionListener(this);

                    upperPanel.revalidate();
                    upperPanel.repaint();

                    canvasPanel.revalidate();
                    canvasPanel.repaint();
                    System.out.println(addRoomComboBox.getSelectedItem());
                }
                else{
                    JOptionPane.showMessageDialog(this, 
                    "Cannot place room here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
                    
                }
            }

            if(addRoomComboBox.getSelectedItem()=="WASHROOM"){
                 room1=new Room(roomTypes[1],roomColors[1]);
                pos = calculateNextPosition(Integer.parseInt(sizeTextFieldW.getText()),Integer.parseInt(sizeTextFieldH.getText()));
                 if (pos != null) {  // Check if position was found
                    room1.setBounds(pos.x, pos.y, 
                                   Integer.parseInt(sizeTextFieldW.getText()),
                                   Integer.parseInt(sizeTextFieldH.getText()));
                    // ... rest of room addition code ...
                }
                 rooms.add(room1);
                 room1.setBounds(pos.x,pos.y, Integer.parseInt(sizeTextFieldW.getText()),Integer.parseInt(sizeTextFieldH.getText()));
                room1.setBackground(roomColors[1]);
                room1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                canvasPanel.add(room1);
                room1.setVisible(true);
                room1.addMouseListener(this);
                room1.addMouseMotionListener(this);
                canvasPanel.revalidate();
                canvasPanel.repaint();
                
                System.out.println(addRoomComboBox.getSelectedItem());

            }

            if(addRoomComboBox.getSelectedItem()=="LIVINGROOM"){
                room1=new Room(roomTypes[2],roomColors[2]);
                pos = calculateNextPosition(Integer.parseInt(sizeTextFieldW.getText()),Integer.parseInt(sizeTextFieldH.getText()));
                if (pos != null) {  // Check if position was found
                    room1.setBounds(pos.x, pos.y, 
                                   Integer.parseInt(sizeTextFieldW.getText()),
                                   Integer.parseInt(sizeTextFieldH.getText()));
                    // ... rest of room addition code ...
                }
                rooms.add(room1);
                room1.setBounds(pos.x,pos.y, Integer.parseInt(sizeTextFieldW.getText()),Integer.parseInt(sizeTextFieldH.getText()));
                room1.setBackground(roomColors[2]);
                room1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                canvasPanel.add(room1);
                room1.setVisible(true);
                room1.addMouseListener(this);
                room1.addMouseMotionListener(this);
                
                canvasPanel.revalidate();
                canvasPanel.repaint();
              
                System.out.println(addRoomComboBox.getSelectedItem());

            }

            if(addRoomComboBox.getSelectedItem()=="KITCHEN"){
                room1=new Room(roomTypes[3],roomColors[3]);
                pos = calculateNextPosition(Integer.parseInt(sizeTextFieldW.getText()),Integer.parseInt(sizeTextFieldH.getText()));
                if (pos != null) {  // Check if position was found
                    room1.setBounds(pos.x, pos.y, 
                                   Integer.parseInt(sizeTextFieldW.getText()),
                                   Integer.parseInt(sizeTextFieldH.getText()));
                    // ... rest of room addition code ...
                }
                rooms.add(room1);
                room1.setBounds(pos.x,pos.y, Integer.parseInt(sizeTextFieldW.getText()),Integer.parseInt(sizeTextFieldH.getText()));
                room1.setBackground(roomColors[3]);
                room1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                canvasPanel.add(room1);
                room1.setVisible(true);
                room1.addMouseListener(this);
                room1.addMouseMotionListener(this);
                canvasPanel.revalidate();
                canvasPanel.repaint();
                System.out.println(addRoomComboBox.getSelectedItem());

            }

            
            
        }

        if(e.getSource()==selectRoomButton){
            
    }
            
            
        }
    
           
    
        
    




    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() instanceof Room) {
            selectedRoom = (Room)e.getSource();
            // Store original position
            originalX = selectedRoom.getX();
            originalY = selectedRoom.getY();
            // Get mouse coordinates relative to the room
            offsetX = e.getX();
            offsetY = e.getY();
            // Optional: Visual feedback
            selectedRoom.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
        
       
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        
            if(selectedRoom != null) {
                // Check for overlap at release position
                if(checkOverlap(selectedRoom)) {
                    // Snap back to original position immediately
                    selectedRoom.setLocation(originalX, originalY);
                    
                    // Optionally show a message to user
                    JOptionPane.showMessageDialog(this, 
                        "Cannot place room here - overlap detected",
                        "Overlap Error",
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    // No overlap detected, update the room's position in the ArrayList
                    int index = rooms.indexOf(selectedRoom);
                    if (index != -1) {
                        // Get the current position of the room
                        int newX = selectedRoom.getX();
                        int newY = selectedRoom.getY();
                        
                        // Update the room's position in the ArrayList
                        rooms.get(index).setLocation(newX, newY);
                        
                        // Debug output to verify the update
                        System.out.println("Room " + selectedRoom.getType() + 
                                         " moved to position: (" + newX + ", " + newY + ")");
                    }
                }
                
                // Remove border and clear selection
                selectedRoom.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                selectedRoom = null;
                canvasPanel.revalidate();
                canvasPanel.repaint();
            }
       
    }
    @Override
    public void mouseEntered(MouseEvent e) {
       
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }


    @Override
    public void mouseDragged(MouseEvent e) {
         if(selectedRoom != null) {
            // Get the new position (relative to the canvas)
            Point p = SwingUtilities.convertPoint(
                selectedRoom,
                e.getPoint(),
                canvasPanel
            );
            
            // Calculate new position
            int newX = p.x - offsetX;
            int newY = p.y - offsetY;
            
            // Set new position
            selectedRoom.setLocation(newX, newY);
            
            // Check for overlaps
            if(checkOverlap(selectedRoom)) {
                // If overlap, could provide visual feedback
                selectedRoom.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else {
                selectedRoom.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
            
            canvasPanel.revalidate();
            canvasPanel.repaint();
        }
        
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
}

