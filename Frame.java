import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Frame extends JFrame implements Serializable,ActionListener,MouseListener,MouseMotionListener{
    private static final long serialVersionUID = 1L;
    private ArrayList<Room>rooms=new ArrayList<>();

    JFrame relRoomAddTypeFrame;
    JFrame relRoomAddAlignFrame;
    

    JComboBox<Object> addRoomComboBox;
    JButton sizeSubmitButton;
    JButton addChairButton;
    JButton addDiningButton;
    JButton addKitchenSinkButton;
    JButton addWashBasinButton;
    JButton addBedButton;
    JButton addTableButton;
    JButton addSofaButton;
    JButton addStoveButton;
    JButton addCommodeButton;
    JButton addShowerButton;
    JButton spareButton;

    JButton deSelectFixtureButton;
    JComboBox<Object> saveComboBox;
    JButton deselectRoomButton;
    JComboBox<Object> addRoomRelativeComboBox;
    JRadioButton bedroomButton;
    JRadioButton washroomButton;
    JRadioButton livingroomButton;
    JRadioButton kitchenButton;

    JRadioButton topButton;
    JRadioButton bottomButton;
    JRadioButton centreButton;
    JRadioButton lefButton;
    JRadioButton righButton;

    JComboBox<Object> rotateFixtureComboBox;

    //JTextField roomSizeTextField;

    JPanel upperPanel;
    JPanel leftPanel;
    JPanel canvasPanel;

    JTextField sizeTextFieldH;
    JTextField sizeTextFieldW;

    public String[] roomTypes={"BEDROOM","WASHROOM","LIVINGROOM","KITCHEN"};
    public Color[] roomColors={Color.GREEN,Color.BLUE,Color.ORANGE,Color.PINK};
    public String[] rotation={"90","180","270"};
    public String[] relativeRoomPos={"NORTH","EAST","SOUTH","WEST"};
    public String[] saveLoadStrings={"SAVE","LOAD"};

    
    public void saveProgress(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            ArrayList<SerializableRoom> serializableRooms = new ArrayList<>();
            for (Room room : rooms) {
                serializableRooms.add(new SerializableRoom(room));
            }
            oos.writeObject(serializableRooms);
            JOptionPane.showMessageDialog(this, "Progress saved successfully!", "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving progress: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }


    }
     // Load method
     public void loadProgress(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            ArrayList<SerializableRoom> serializableRooms = (ArrayList<SerializableRoom>) ois.readObject();
            rooms.clear();
            canvasPanel.removeAll();
    
            for (SerializableRoom sRoom : serializableRooms) {
                Room room = sRoom.toRoom(this);
                rooms.add(room);
                canvasPanel.add(room);
            }
    
            canvasPanel.revalidate();
            canvasPanel.repaint();
            JOptionPane.showMessageDialog(this, "Progress loaded successfully!", "Load", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading progress: " + e.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

     public Frame(){

        for(Room room : rooms) {
            room.addMouseListener(this);
            room.addMouseMotionListener(this);
        }
       

        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("2D Floor Planner");
        
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
         addChairButton=new JButton();
         addDiningButton=new JButton();
         addKitchenSinkButton=new JButton();
         addWashBasinButton=new JButton();
         addBedButton=new JButton();
         addTableButton=new JButton();
         addSofaButton=new JButton();
         addStoveButton=new JButton();
         addCommodeButton=new JButton();
         addShowerButton=new JButton();
        //JButton clearBoardButton=new JButton();

       
        saveComboBox=new JComboBox<>(saveLoadStrings);
        deselectRoomButton=new JButton();
        deSelectFixtureButton=new JButton();
        addRoomRelativeComboBox=new JComboBox(relativeRoomPos);
         rotateFixtureComboBox=new JComboBox(rotation);


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
        
        
       
        

        upperPanel.add(saveComboBox);
        upperPanel.add(deselectRoomButton);
        upperPanel.add(deSelectFixtureButton);
        upperPanel.add(rotateFixtureComboBox);
        upperPanel.add(addRoomRelativeComboBox);
        

        


        
        
        saveComboBox.setBounds(0,0,200,upperPanel.getHeight());
        saveComboBox.setFocusable(false);
        saveComboBox.addActionListener(this);
        
        deselectRoomButton.setText("DESELECT ROOM");
        deselectRoomButton.setBounds(200,0,200,upperPanel.getHeight());
        deselectRoomButton.setFocusable(false);
        deselectRoomButton.setEnabled(false);
        deselectRoomButton.addActionListener(this);
        

        deSelectFixtureButton.setText("DESELECT FIXTURE");
        deSelectFixtureButton.setBounds(400,0,250,upperPanel.getHeight());
        deSelectFixtureButton.setFocusable(false);
        deSelectFixtureButton.setEnabled(false);
        deSelectFixtureButton.addActionListener(this);

        
        rotateFixtureComboBox.setBounds(650,0,250,upperPanel.getHeight());
        rotateFixtureComboBox.setFocusable(false);
        rotateFixtureComboBox.setEnabled(false);
        rotateFixtureComboBox.addActionListener(this);

        
        addRoomRelativeComboBox.setBounds(900,0,350,upperPanel.getHeight());
        addRoomRelativeComboBox.setFocusable(false);
        addRoomRelativeComboBox.setMaximumSize(new Dimension(100, 50));
        addRoomRelativeComboBox.setEnabled(false);
        addRoomRelativeComboBox.addActionListener(this);

        bedroomButton=new JRadioButton("BEDROOM");
        bedroomButton.addActionListener(this);

        washroomButton=new JRadioButton("WASHROOM");
        washroomButton.addActionListener(this);

        livingroomButton=new JRadioButton("LIVINGROOM");
        livingroomButton.addActionListener(this);

        kitchenButton=new JRadioButton("KITCHEN");
        kitchenButton.addActionListener(this);

        ButtonGroup typeButtonGroup=new ButtonGroup();
        typeButtonGroup.add(bedroomButton);
        typeButtonGroup.add(washroomButton);
        typeButtonGroup.add(livingroomButton);
        typeButtonGroup.add(kitchenButton);

        relRoomAddTypeFrame =new JFrame();
        relRoomAddTypeFrame.setVisible(false);
        relRoomAddTypeFrame.setTitle("Select room type");
        relRoomAddTypeFrame.setLayout(new FlowLayout());
        relRoomAddTypeFrame.add(bedroomButton);
        relRoomAddTypeFrame.add(washroomButton);
        relRoomAddTypeFrame.add(livingroomButton);
        relRoomAddTypeFrame.add(kitchenButton);
        relRoomAddTypeFrame.pack();

        topButton=new JRadioButton("Top wall aligned");
        topButton.setEnabled(false);
        topButton.addActionListener(this);

        bottomButton=new JRadioButton("Bottom wall aligned");
        bottomButton.setEnabled(false);
        bottomButton.addActionListener(this);

        centreButton=new JRadioButton("Centre aligned");
        centreButton.setEnabled(false);
        centreButton.addActionListener(this);

        lefButton=new JRadioButton("Left wall aligned");
        lefButton.setEnabled(false);
        lefButton.addActionListener(this);

        righButton=new JRadioButton("Right wall aligned");
        righButton.setEnabled(false);
        righButton.addActionListener(this);

        ButtonGroup alignButtonGroup=new ButtonGroup();
        alignButtonGroup.add(topButton);
        alignButtonGroup.add(bottomButton);
        alignButtonGroup.add(centreButton);
        alignButtonGroup.add(lefButton);
        alignButtonGroup.add(righButton);





        relRoomAddAlignFrame=new JFrame();
        relRoomAddAlignFrame.setVisible(false);
        relRoomAddAlignFrame.setTitle("Select alignment");
        relRoomAddAlignFrame.setLayout(new FlowLayout());
        relRoomAddAlignFrame.add(topButton);
        relRoomAddAlignFrame.add(bottomButton);
        relRoomAddAlignFrame.add(centreButton);
        relRoomAddAlignFrame.add(lefButton);
        relRoomAddAlignFrame.add(righButton);
        relRoomAddAlignFrame.pack();

        




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
        addChairButton.setEnabled(false);
        addChairButton.addActionListener(this);


        addDiningButton.setText("DINING TABLE");
        addDiningButton.setBounds(0,0,leftPanel.getWidth(),40);
        addDiningButton.setFocusable(false);
        addDiningButton.setEnabled(false);
        addDiningButton.addActionListener(this);

        addKitchenSinkButton.setText("KITCHEN SINK");
        addKitchenSinkButton.setBounds(0,0,leftPanel.getWidth(),40);
        addKitchenSinkButton.setFocusable(false);
        addKitchenSinkButton.setEnabled(false);
        addKitchenSinkButton.addActionListener(this);

        addWashBasinButton.setText("WASH BASIN");
        addWashBasinButton.setBounds(0,0,leftPanel.getWidth(),40);
        addWashBasinButton.setFocusable(false);
        addWashBasinButton.setEnabled(false);
        addWashBasinButton.addActionListener(this);


        addBedButton.setText("BED");
        addBedButton.setBounds(0,0,leftPanel.getWidth(),40);
        addBedButton.setFocusable(false);
        addBedButton.setEnabled(false);
        addBedButton.addActionListener(this);

        addTableButton.setText("TABLE");
        addTableButton.setBounds(0,0,leftPanel.getWidth(),40);
        addTableButton.setFocusable(false);
        addTableButton.setEnabled(false);
        addTableButton.addActionListener(this);

        addSofaButton.setText("SOFA");
        addSofaButton.setBounds(0,0,leftPanel.getWidth(),40);
        addSofaButton.setFocusable(false);
        addSofaButton.setEnabled(false);
        addSofaButton.addActionListener(this);

        addStoveButton.setText("STOVE");
        addStoveButton.setBounds(0,0,leftPanel.getWidth(),40);
        addStoveButton.setFocusable(false);
        addStoveButton.setEnabled(false);
        addStoveButton.addActionListener(this);

        addCommodeButton.setText("COMMODE");
        addCommodeButton.setBounds(0,0,leftPanel.getWidth(),40);
        addCommodeButton.setFocusable(false);
        addCommodeButton.setEnabled(false);
        addCommodeButton.addActionListener(this);

        addShowerButton.setText("SHOWER");
        addShowerButton.setBounds(0,0,leftPanel.getWidth(),40);
        addShowerButton.setFocusable(false);
        addShowerButton.setEnabled(false);
        addShowerButton.addActionListener(this);

        


    }
    

    
  
    public class RoomPlacer {
        private ArrayList<Room> existingRooms;
        private int canvasWidth;
        private int canvasHeight;
        private static final int PADDING = 0; // Space between rooms
    
        public RoomPlacer(ArrayList<Room> rooms, int canvasWidth, int canvasHeight) {
            this.existingRooms = rooms;
            this.canvasWidth = canvasPanel.getWidth();
            this.canvasHeight = canvasPanel.getHeight();
        }
    
        public Point calculateNextPosition(int roomWidth, int roomHeight) {
            // If no rooms exist, place at origin
            if (existingRooms.isEmpty()) {
                return new Point(0, 0);
            }
    
            // Create a grid to track occupied spaces
            boolean[][] occupied = new boolean[canvasHeight][canvasWidth];
            
            // Mark existing rooms as occupied
            for (JPanel room : existingRooms) {
                Rectangle bounds = room.getBounds();
                for (int y = bounds.y; y < bounds.y + bounds.height + PADDING; y++) {
                    for (int x = bounds.x; x < bounds.x + bounds.width + PADDING; x++) {
                        if (y < canvasHeight && x < canvasWidth) {
                            occupied[y][x] = true;
                        }
                    }
                }
            }
    
            // Search for next available position in row-major order
            for (int y = 0; y < canvasHeight - roomHeight; y++) {
                for (int x = 0; x < canvasWidth - roomWidth; x++) {
                    if (isSpaceAvailable(occupied, x, y, roomWidth, roomHeight)) {
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

      
    public Fixture getClickFixture(int clickx, int clicky) {
        for (Fixture currFix : clickSelectedRoom.fixtures) {
            if ((clickx > currFix.getX()) && 
                (clickx < (currFix.getX() + currFix.getWidth())) && 
                (clicky > currFix.getY()) && 
                (clicky < (currFix.getY() + currFix.getHeight()))) {
                return currFix;
            }
        }
        return null; // Only return null after checking all fixtures
    }

    private void rotatePanel() {
        // Remove the current panel
        remove(clickSelectedFrameFixture);

        // Create a new panel with rotated layout
        Fixture rotatedPanel = new Fixture(clickSelectedFrameFixture.getType(),clickSelectedFrameFixture.getColor()) {
            @Override
            public Dimension getPreferredSize() {
                Dimension original = clickSelectedFrameFixture.getSize();
                return new Dimension(original.height, original.width);
            }
        };
        rotatedPanel.setBackground(clickSelectedFrameFixture.getBackground());

        // Copy components from original panel
        for (Component comp : clickSelectedFrameFixture.getComponents()) {
            rotatedPanel.add(comp);
        }

        // Add the new rotated panel
        clickSelectedFrameFixture = rotatedPanel;
        clickSelectedRoom.add(clickSelectedFrameFixture);

        // Revalidate and repaint
        revalidate();
        repaint();
    }
    public void addRoom(){}
    private int rotationAngle = 0;
        
        

    Room room1;
    Point pos;
    Room relativeRoom=null;
   
    

    
    private int originalX;
    private int originalY;
    private Room selectedRoom = null;  // Currently selected room
    private int offsetX;              // Mouse click offset X from room's top-left corner
    private int offsetY;              // Mouse click offset Y from room's top-left corner

    //for fixtures
    private int originalFixX;
    private int originalFixY;
    private Fixture selectedFixture = null;  // 
    private int offsetFixX;              // ner
    private int offsetFixY;
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        if(e.getSource()==addRoomComboBox){
            RoomPlacer placer = new RoomPlacer(rooms, canvasPanel.getWidth(), canvasPanel.getHeight());
          

            if(e.getSource()==sizeSubmitButton){
                

                

                canvasPanel.revalidate();
                canvasPanel.repaint();
                }

            if(addRoomComboBox.getSelectedItem()=="BEDROOM"){
                room1=new Room(roomTypes[0],roomColors[0]);
                
                pos = placer.calculateNextPosition(
            Integer.parseInt(sizeTextFieldW.getText()),
            Integer.parseInt(sizeTextFieldH.getText())
        );
        
        if (pos != null) {
            room1.setBounds(pos.x, pos.y, 
                Integer.parseInt(sizeTextFieldW.getText()),
                Integer.parseInt(sizeTextFieldH.getText()));
                
            if(!checkOverlap(room1)) {
                rooms.add(room1);
                room1.setBackground(roomColors[0]);
                room1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                canvasPanel.add(room1);
                room1.setVisible(true);
                room1.addMouseListener(this);
                room1.addMouseMotionListener(this);
                
                canvasPanel.revalidate();
                canvasPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place room here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the room",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }
            }

            if(addRoomComboBox.getSelectedItem()=="WASHROOM"){
                 room1=new Room(roomTypes[1],roomColors[1]);
                 pos = placer.calculateNextPosition(
                    Integer.parseInt(sizeTextFieldW.getText()),
                    Integer.parseInt(sizeTextFieldH.getText())
                );
                
                if (pos != null) {
                    room1.setBounds(pos.x, pos.y, 
                        Integer.parseInt(sizeTextFieldW.getText()),
                        Integer.parseInt(sizeTextFieldH.getText()));
                        
                    if(!checkOverlap(room1)) {
                        rooms.add(room1);
                        room1.setBackground(roomColors[1]);
                        room1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                        canvasPanel.add(room1);
                        room1.setVisible(true);
                        room1.addMouseListener(this);
                        room1.addMouseMotionListener(this);
                        
                        canvasPanel.revalidate();
                        canvasPanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Cannot place room here - overlap detected",
                            "Overlap Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No valid position found for the room",
                        "Position Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }

            if(addRoomComboBox.getSelectedItem()=="LIVINGROOM"){
                room1=new Room(roomTypes[2],roomColors[2]);
                pos = placer.calculateNextPosition(
            Integer.parseInt(sizeTextFieldW.getText()),
            Integer.parseInt(sizeTextFieldH.getText())
        );
        
        if (pos != null) {
            room1.setBounds(pos.x, pos.y, 
                Integer.parseInt(sizeTextFieldW.getText()),
                Integer.parseInt(sizeTextFieldH.getText()));
                
            if(!checkOverlap(room1)) {
                rooms.add(room1);
                room1.setBackground(roomColors[2]);
                room1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                canvasPanel.add(room1);
                room1.setVisible(true);
                room1.addMouseListener(this);
                room1.addMouseMotionListener(this);
                
                canvasPanel.revalidate();
                canvasPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place room here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the room",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }

            }

            if(addRoomComboBox.getSelectedItem()=="KITCHEN"){
                room1=new Room(roomTypes[3],roomColors[3]);
                pos = placer.calculateNextPosition(
            Integer.parseInt(sizeTextFieldW.getText()),
            Integer.parseInt(sizeTextFieldH.getText())
        );
        
        if (pos != null) {
            room1.setBounds(pos.x, pos.y, 
                Integer.parseInt(sizeTextFieldW.getText()),
                Integer.parseInt(sizeTextFieldH.getText()));
                
            if(!checkOverlap(room1)) {
                rooms.add(room1);
                room1.setBackground(roomColors[3]);
                room1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                canvasPanel.add(room1);
                room1.setVisible(true);
                room1.addMouseListener(this);
                room1.addMouseMotionListener(this);
                
                canvasPanel.revalidate();
                canvasPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot place room here - overlap detected",
                    "Overlap Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No valid position found for the room",
                "Position Error",
                JOptionPane.ERROR_MESSAGE);
        }

            }

            
            
        }
        if(e.getSource()==deselectRoomButton){
             // Remove border and clear selection
             deselectRoomButton.setEnabled(false);
             clickSelectedRoom.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
             addRoomRelativeComboBox.setEnabled(false);
             clickSelectedRoom = null;
             

             addChairButton.setEnabled(false);
             addDiningButton.setEnabled(false);
             addKitchenSinkButton.setEnabled(false);
             addWashBasinButton.setEnabled(false);
             addBedButton.setEnabled(false);
             addTableButton.setEnabled(false);
             addSofaButton.setEnabled(false);
             addStoveButton.setEnabled(false);
             addCommodeButton.setEnabled(false);
             addShowerButton.setEnabled(false);
             canvasPanel.revalidate();
             canvasPanel.repaint();
             
        }
        if(e.getSource()==addRoomRelativeComboBox){ 
            if (relativeRoom == null) {
                relativeRoom = new Room(roomTypes[0], roomColors[0]); // Or whatever room type is appropriate
                relativeRoom.setBounds(0, 0, 
                Integer.parseInt(sizeTextFieldW.getText()),
                Integer.parseInt(sizeTextFieldH.getText()));
            }
           
            
            if(addRoomRelativeComboBox.getSelectedItem()=="NORTH"){
                relRoomAddAlignFrame.setVisible(true);
                lefButton.setEnabled(true);
                righButton.setEnabled(true);
                centreButton.setEnabled(true);
                relativeRoom.setLocation(relativeRoom.getX(),clickSelectedRoom.getY()-relativeRoom.getHeight());
                
            }
            if(addRoomRelativeComboBox.getSelectedItem()=="EAST"){
                relRoomAddAlignFrame.setVisible(true);
                topButton.setEnabled(true);
                bottomButton.setEnabled(true);
                centreButton.setEnabled(true);
                relativeRoom.setLocation(clickSelectedRoom.getX()+clickSelectedRoom.getWidth(),relativeRoom.getY());
            }
            if(addRoomRelativeComboBox.getSelectedItem()=="SOUTH"){
                relRoomAddAlignFrame.setVisible(true);
                lefButton.setEnabled(true);
                righButton.setEnabled(true);
                centreButton.setEnabled(true);
                relativeRoom.setLocation(relativeRoom.getX(),clickSelectedRoom.getY()+clickSelectedRoom.getHeight());
            }
             
            if(addRoomRelativeComboBox.getSelectedItem()=="WEST"){
                relRoomAddAlignFrame.setVisible(true);
                topButton.setEnabled(true);
                bottomButton.setEnabled(true);
                centreButton.setEnabled(true);
                relativeRoom.setLocation(clickSelectedRoom.getX()-relativeRoom.getWidth(),relativeRoom.getY());
            }
        }
        if(e.getSource()==topButton){
            relativeRoom.setLocation(relativeRoom.getX(),clickSelectedRoom.getY());
            topButton.setEnabled(false);
            bottomButton.setEnabled(false);
            centreButton.setEnabled(false);
            relRoomAddAlignFrame.setVisible(false);
            relRoomAddTypeFrame.setVisible(true);
        }
        if(e.getSource()==bottomButton){
            relativeRoom.setLocation(relativeRoom.getX(),clickSelectedRoom.getY()+clickSelectedRoom.getHeight()-relativeRoom.getHeight());
            topButton.setEnabled(false);
            bottomButton.setEnabled(false);
            centreButton.setEnabled(false);
            relRoomAddAlignFrame.setVisible(false);
            relRoomAddTypeFrame.setVisible(true);
        
        }
        if(e.getSource()==lefButton){
            relativeRoom.setLocation(clickSelectedRoom.getX(),relativeRoom.getY());
            lefButton.setEnabled(false);
            righButton.setEnabled(false);
            centreButton.setEnabled(false);
            relRoomAddAlignFrame.setVisible(false);
            relRoomAddTypeFrame.setVisible(true);
        }
        if(e.getSource()==righButton){
            relativeRoom.setLocation(clickSelectedRoom.getX()+clickSelectedRoom.getWidth()-relativeRoom.getWidth(),relativeRoom.getY());
            lefButton.setEnabled(false);
            righButton.setEnabled(false);
            centreButton.setEnabled(false);
            relRoomAddAlignFrame.setVisible(false);
            relRoomAddTypeFrame.setVisible(true);
        }
        if(e.getSource()==centreButton){
            if(addRoomRelativeComboBox.getSelectedItem()=="NORTH"){
                relativeRoom.setLocation(clickSelectedRoom.getX()+(clickSelectedRoom.getWidth()/2)-(relativeRoom.getWidth()/2),clickSelectedRoom.getY()-relativeRoom.getHeight());
                lefButton.setEnabled(false);
            righButton.setEnabled(false);
            topButton.setEnabled(false);
            bottomButton.setEnabled(false);
            centreButton.setEnabled(false);
            relRoomAddAlignFrame.setVisible(false);
            relRoomAddTypeFrame.setVisible(true);
            }
            if(addRoomRelativeComboBox.getSelectedItem()=="SOUTH"){
                relativeRoom.setLocation(clickSelectedRoom.getX()+(clickSelectedRoom.getWidth()/2)-(relativeRoom.getWidth()/2),clickSelectedRoom.getY()+clickSelectedRoom.getHeight());
                lefButton.setEnabled(false);
            righButton.setEnabled(false);
            topButton.setEnabled(false);
            bottomButton.setEnabled(false);
            centreButton.setEnabled(false);
            relRoomAddAlignFrame.setVisible(false);
            relRoomAddTypeFrame.setVisible(true);
            }
            if(addRoomRelativeComboBox.getSelectedItem()=="EAST"){
                relativeRoom.setLocation(clickSelectedRoom.getX()+clickSelectedRoom.getWidth(),clickSelectedRoom.getY()+(clickSelectedRoom.getHeight()/2)-(relativeRoom.getHeight()/2));
                lefButton.setEnabled(false);
            righButton.setEnabled(false);
            topButton.setEnabled(false);
            bottomButton.setEnabled(false);
            centreButton.setEnabled(false);
            relRoomAddAlignFrame.setVisible(false);
            relRoomAddTypeFrame.setVisible(true);
            }
            if(addRoomRelativeComboBox.getSelectedItem()=="WEST"){
                relativeRoom.setLocation(clickSelectedRoom.getX()-relativeRoom.getWidth(),clickSelectedRoom.getY()+(clickSelectedRoom.getHeight()/2)-(relativeRoom.getHeight()/2));
                lefButton.setEnabled(false);
            righButton.setEnabled(false);
            topButton.setEnabled(false);
            bottomButton.setEnabled(false);
            centreButton.setEnabled(false);
            relRoomAddAlignFrame.setVisible(false);
            relRoomAddTypeFrame.setVisible(true);
            }
            
           
            
        }

        if(e.getSource()==bedroomButton){
            relativeRoom.setType("BEDROOM");
            relativeRoom.setColor(Color.GREEN);
            relativeRoom.setBackground(Color.GREEN);
            relativeRoom.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            relRoomAddTypeFrame.setVisible(false);
            if(isValidPosition(relativeRoom)){
                relativeRoom.setBounds(relativeRoom.getX(), relativeRoom.getY(), relativeRoom.getWidth(), relativeRoom.getHeight());
                rooms.add(relativeRoom);
                relativeRoom.addMouseListener(this);
                relativeRoom.addMouseMotionListener(this);
                canvasPanel.add(relativeRoom);
                canvasPanel.revalidate();
                canvasPanel.repaint();
                relativeRoom=null;
            }
            else{if(checkOverlap(relativeRoom)){JOptionPane.showMessageDialog(this, 
                "Cannot place room here - overlap detected",
                "Overlap Error",
                JOptionPane.ERROR_MESSAGE);
                relativeRoom=null;}
                if(!isWithinBounds(relativeRoom)){
                JOptionPane.showMessageDialog(this, 
                "Cannot place room here - outside canvas",
                "Boundary Error",
                JOptionPane.ERROR_MESSAGE);
                relativeRoom=null;
                }
                relativeRoom=null;
            }

        }
        if(e.getSource()==washroomButton){
            relativeRoom.setType("WASHROOM");
            relativeRoom.setColor(Color.BLUE);
            relativeRoom.setBackground(Color.BLUE);
            relativeRoom.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            relRoomAddTypeFrame.setVisible(false);
            if(isValidPosition(relativeRoom)){
                rooms.add(relativeRoom);
                relativeRoom.addMouseListener(this);
                relativeRoom.addMouseMotionListener(this);
                canvasPanel.add(relativeRoom);
                canvasPanel.revalidate();
                canvasPanel.repaint();
                relativeRoom=null;
            }
            else{if(checkOverlap(relativeRoom)){JOptionPane.showMessageDialog(this, 
                "Cannot place room here - overlap detected",
                "Overlap Error",
                JOptionPane.ERROR_MESSAGE);
                relativeRoom=null;}
                if(!isWithinBounds(relativeRoom)){
                JOptionPane.showMessageDialog(this, 
                "Cannot place room here - outside canvas",
                "Boundary Error",
                JOptionPane.ERROR_MESSAGE);
                relativeRoom=null;
                }
                relativeRoom=null;
            }

        }
        if(e.getSource()==livingroomButton){
            relativeRoom.setType("LIVINGROOM");
            relativeRoom.setColor(Color.ORANGE);
            relativeRoom.setBackground(Color.ORANGE);
            relativeRoom.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            relRoomAddTypeFrame.setVisible(false);
            if(isValidPosition(relativeRoom)){
                rooms.add(relativeRoom);
                relativeRoom.addMouseListener(this);
                relativeRoom.addMouseMotionListener(this);
                canvasPanel.add(relativeRoom);
                canvasPanel.revalidate();
                canvasPanel.repaint();
                relativeRoom=null;
            }
            else{if(checkOverlap(relativeRoom)){JOptionPane.showMessageDialog(this, 
                "Cannot place room here - overlap detected",
                "Overlap Error",
                JOptionPane.ERROR_MESSAGE);
                relativeRoom=null;}
                if(!isWithinBounds(relativeRoom)){
                JOptionPane.showMessageDialog(this, 
                "Cannot place room here - outside canvas",
                "Boundary Error",
                JOptionPane.ERROR_MESSAGE);
                relativeRoom=null;
                }
                relativeRoom=null;
            }


        }
        if(e.getSource()==kitchenButton){
            relativeRoom.setType("KITCHEN");
            relativeRoom.setColor(Color.PINK);
            relativeRoom.setBackground(Color.PINK);
            relativeRoom.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            relRoomAddTypeFrame.setVisible(false);
            if(isValidPosition(relativeRoom)){
                rooms.add(relativeRoom);
                relativeRoom.addMouseListener(this);
                relativeRoom.addMouseMotionListener(this);
                canvasPanel.add(relativeRoom);
                canvasPanel.revalidate();
                canvasPanel.repaint();
                relativeRoom=null;
            }
            else{if(checkOverlap(relativeRoom)){JOptionPane.showMessageDialog(this, 
                "Cannot place room here - overlap detected",
                "Overlap Error",
                JOptionPane.ERROR_MESSAGE);
                relativeRoom=null;}
                if(!isWithinBounds(relativeRoom)){
                JOptionPane.showMessageDialog(this, 
                "Cannot place room here - outside canvas",
                "Boundary Error",
                JOptionPane.ERROR_MESSAGE);
                relativeRoom=null;
                }
                relativeRoom=null;
            }
        }
        
        
        

        if(e.getSource()==rotateFixtureComboBox){
            if(rotateFixtureComboBox.getSelectedItem()=="90"){
                
                rotateFixture(clickSelectedFrameFixture, 90);
                
                clickSelectedRoom.revalidate();
                clickSelectedRoom.revalidate();
                clickSelectedRoom.repaint();
               
                   
               
                
                

              
            
            }
            if(rotateFixtureComboBox.getSelectedItem()=="180"){
                rotateFixture(clickSelectedFrameFixture, 180);
               
                clickSelectedRoom.revalidate();
                clickSelectedRoom.revalidate();
                clickSelectedRoom.repaint();

               
            }
            if(rotateFixtureComboBox.getSelectedItem()=="270"){
                rotateFixture(clickSelectedFrameFixture, 270);
                
                clickSelectedRoom.revalidate();
                clickSelectedRoom.revalidate();
                clickSelectedRoom.repaint();

                
            }

            
        }
        if(e.getSource()==saveComboBox){
            String selectedOption = (String) saveComboBox.getSelectedItem();
            if ("SAVE".equals(selectedOption)) {
                String filePath = JOptionPane.showInputDialog(this, "Enter file name to save:");
                if (filePath != null && !filePath.isEmpty()) {
                    saveProgress(filePath);
                }
            } else if ("LOAD".equals(selectedOption)) {
                String filePath = JOptionPane.showInputDialog(this, "Enter file name to load:");
                if (filePath != null && !filePath.isEmpty()) {
                    loadProgress(filePath);
                }
            }
  
}

            
        
        if(e.getSource()==addChairButton){
            clickSelectedRoom.addChair();
        }
        if(e.getSource()==addDiningButton){
            clickSelectedRoom.addDiningTable();
        }
        if(e.getSource()==addKitchenSinkButton){
            clickSelectedRoom.addKitchenSink();
        }
        if(e.getSource()==addWashBasinButton){
            clickSelectedRoom.addWashBasin();
        }
        if(e.getSource()==addBedButton){
            clickSelectedRoom.addBed();
        }
        if(e.getSource()==addTableButton){
            clickSelectedRoom.addTable();
        }
        if(e.getSource()==addSofaButton){
            clickSelectedRoom.addSofa();
        }
        if(e.getSource()==addStoveButton){
            clickSelectedRoom.addStove();
        }
        if(e.getSource()==addCommodeButton){
            clickSelectedRoom.addCommode();
        }
        if(e.getSource()==addShowerButton){
            clickSelectedRoom.addShower();
        }
            
        
        if(e.getSource()==deSelectFixtureButton){
           
            deSelectFixtureButton.setEnabled(false);
            clickSelectedFrameFixture.setBorder(BorderFactory.createLineBorder(Color.BLACK,0));
            rotateFixtureComboBox.setEnabled(false);
            clickSelectedFrameFixture=null;
            
        }
       
            
            
        }
    
           
    public Room clickSelectedRoom=null;
    Fixture clickSelectedFrameFixture;
    Fixture rotatedFixture=null;
    

    public Room getClickSelectedRoom(){
        return clickSelectedRoom;
    }
    Fixture theOG;
    ImageIcon currRotImageIcon;
    ImageIcon resizedRotIcon;
    JLabel fixRotLabel;
    Fixture finalFixture;
    private void rotateFixture(Fixture fixture, int angle) {
        if (fixture == null) {
            JOptionPane.showMessageDialog(null, "No fixture selected to rotate.", "Rotation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Room parentRoom = (Room) fixture.getParent();
        if (parentRoom == null) return;
        
        theOG = fixture;
        
        // Calculate new dimensions
        int originalWidth = fixture.getWidth();
        int originalHeight = fixture.getHeight();
        int newWidth = (angle % 180 == 0) ? originalWidth : originalHeight;
        int newHeight = (angle % 180 == 0) ? originalHeight : originalWidth;
        
        // Create rotated fixture
        rotatedFixture = createRotatedFixture(fixture, newWidth, newHeight);
        rotatedFixture.setBounds(
            fixture.getX() + fixture.getWidth()/2 - newWidth/2,
            fixture.getY() + fixture.getHeight()/2 - newHeight/2,
            newWidth,
            newHeight
        );
        rotatedFixture.addMouseListener(this);
        rotatedFixture.addMouseMotionListener(this);
        
        if (fixture.getType().equals("CHAIR")) {  // Use equals() instead of ==
            updateRotatedChair(angle, newWidth, newHeight, parentRoom, fixture);
        }
        if (fixture.getType().equals("DININGTABLE")) {  // Use equals() instead of ==
            updateRotatedDiningTable(angle, newWidth, newHeight, parentRoom, fixture);
        }
        if (fixture.getType().equals("KITCHENSINK")) {  // Use equals() instead of ==
            updateRotatedKitchenSink(angle, newWidth, newHeight, parentRoom, fixture);
        }
        if (fixture.getType().equals("WASHBASIN")) {  // Use equals() instead of ==
            updateRotatedWashBasin(angle, newWidth, newHeight, parentRoom, fixture);
        }
        if (fixture.getType().equals("BED")) {  // Use equals() instead of ==
            updateRotatedBed(angle, newWidth, newHeight, parentRoom, fixture);
        }
        if (fixture.getType().equals("TABLE")) {  // Use equals() instead of ==
            updateRotatedTable(angle, newWidth, newHeight, parentRoom, fixture);
        }
        if (fixture.getType().equals("SOFA")) {  // Use equals() instead of ==
            updateRotatedSofa(angle, newWidth, newHeight, parentRoom, fixture);
        }
        if (fixture.getType().equals("STOVE")) {  // Use equals() instead of ==
            updateRotatedStove(angle, newWidth, newHeight, parentRoom, fixture);
        }
        if (fixture.getType().equals("COMMODE")) {  // Use equals() instead of ==
            updateRotatedCommode(angle, newWidth, newHeight, parentRoom, fixture);
        }
        if (fixture.getType().equals("SHOWER")) {  // Use equals() instead of ==
            updateRotatedShower(angle, newWidth, newHeight, parentRoom, fixture);
        }
        
    }
    
    private Fixture createRotatedFixture(Fixture original, int width, int height) {
        Fixture rotated = new Fixture(original.getType(), original.getColor()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }
        };
        rotated.setBackground(original.getBackground());
        rotated.setLayout(null);
        return rotated;
    }
    
    private void updateRotatedChair(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        String imagePath = String.format("chair_%d.jpg", angle);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            updateParentRoom(parentRoom, originalFixture);
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;
        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            clickSelectedFrameFixture=originalFixture;
            
        }
    }
    private void updateRotatedDiningTable(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        String imagePath = String.format("diningtable_%d.jpeg", angle);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            updateParentRoom(parentRoom, originalFixture);
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;

        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            clickSelectedFrameFixture=originalFixture;
        }
    }
    private void updateRotatedKitchenSink(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        String imagePath = String.format("kitchensink_%d.png", angle);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;
            updateParentRoom(parentRoom, originalFixture);
            
        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            clickSelectedFrameFixture=originalFixture;
        }
    }
    private void updateRotatedWashBasin(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        String imagePath = String.format("washbasin_%d.jpg", angle);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            updateParentRoom(parentRoom, originalFixture);
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;
        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            clickSelectedFrameFixture=originalFixture;
        }
    }
    private void updateRotatedBed(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
        String imagePath = String.format("bed_%d.png", (rotatedFixture.rotCounter)%360);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            updateParentRoom(parentRoom, originalFixture);
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;
            rotatedFixture=null;
            
            
        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            finalFixture=originalFixture;
            finalFixture.addMouseListener(this);
            finalFixture.addMouseMotionListener(this);
            clickSelectedFrameFixture=originalFixture;
            
        }
    }
    private void updateRotatedTable(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        String imagePath = String.format("table_%d.jpg", angle);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            updateParentRoom(parentRoom, originalFixture);
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;
        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            clickSelectedFrameFixture=originalFixture;
        }
    }
    private void updateRotatedSofa(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        String imagePath = String.format("sofa_%d.png", angle);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            updateParentRoom(parentRoom, originalFixture);
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;
        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            clickSelectedFrameFixture=originalFixture;
        }
    }
    private void updateRotatedStove(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        String imagePath = String.format("stove_%d.png", angle);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            updateParentRoom(parentRoom, originalFixture);
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;
        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            clickSelectedFrameFixture=originalFixture;
        }
    }
    private void updateRotatedCommode(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        String imagePath = String.format("commode_%d.png", angle);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            updateParentRoom(parentRoom, originalFixture);
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;
        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            clickSelectedFrameFixture=originalFixture;
        }
    }
    private void updateRotatedShower(int angle, int width, int height, Room parentRoom, Fixture originalFixture) {
        String imagePath = String.format("shower_%d.png", angle);
        ImageIcon icon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(
            icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
        
        JLabel rotLabel = new JLabel(resizedIcon);
        rotLabel.setBounds(0, 0, width, height);
        rotatedFixture.add(rotLabel);
        parentRoom.remove(originalFixture);
        parentRoom.fixtures.remove(originalFixture);
        
        if (parentRoom.isValidFixturePosition(rotatedFixture)) {
            updateParentRoom(parentRoom, originalFixture);
            rotatedFixture.rotCounter=rotatedFixture.rotCounter+angle;
            clickSelectedFrameFixture=rotatedFixture;
        } else {
            showRotationError(parentRoom);
            originalFixture.addMouseListener(this);
            originalFixture.addMouseMotionListener(this);
            parentRoom.add(originalFixture);
            parentRoom.fixtures.add(originalFixture);
            clickSelectedFrameFixture=originalFixture;
        }
    }
    
    private void updateParentRoom(Room parentRoom, Fixture originalFixture) {
        
        parentRoom.add(rotatedFixture);
        parentRoom.fixtures.add(rotatedFixture);
        rotatedFixture.setVisible(true);
        parentRoom.repaint();
        parentRoom.revalidate();
    }
    
    private void showRotationError(Room parentRoom) {
        if (parentRoom.checkFixtureOverlap(rotatedFixture)) {
            JOptionPane.showMessageDialog(this,
                "Cannot rotate fixture - overlapping with another fixture",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        if (!parentRoom.isFixtureWithinBounds(rotatedFixture)) {
            JOptionPane.showMessageDialog(this,
                "Cannot rotate - fixture would be outside room bounds",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    




    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof Room) {
            if(clickSelectedRoom==null){
            deselectRoomButton.setEnabled(true);
            addRoomRelativeComboBox.setEnabled(true);
            
           
       clickSelectedRoom=(Room)e.getSource();
        clickSelectedRoom.setBorder(BorderFactory.createDashedBorder(Color.RED, 20, 10));

        if(clickSelectedRoom.getType()=="BEDROOM"){
            addBedButton.setEnabled(true);
            addTableButton.setEnabled(true);
            addChairButton.setEnabled(true);
            
            
        }
        if(clickSelectedRoom.getType()=="WASHROOM"){
            addWashBasinButton.setEnabled(true);
            addCommodeButton.setEnabled(true);
            addShowerButton.setEnabled(true);
            
        }
        if(clickSelectedRoom.getType()=="LIVINGROOM"){
            addChairButton.setEnabled(true);
            addDiningButton.setEnabled(true);
            addTableButton.setEnabled(true);
            addSofaButton.setEnabled(true);
        }
        if(clickSelectedRoom.getType()=="KITCHEN"){
            addKitchenSinkButton.setEnabled(true);
            addStoveButton.setEnabled(true);
        }
        
    }
    else{JOptionPane.showMessageDialog(this, 
        "cannot select multiple rooms",
        "Error",
        JOptionPane.ERROR_MESSAGE);}
        
    }

    if(e.getSource() instanceof Fixture){
        if(clickSelectedRoom!=null){
            if(clickSelectedFrameFixture==null){
            rotateFixtureComboBox.setEnabled(true);
            Point click=e.getPoint();
            deSelectFixtureButton.setEnabled(true);

            clickSelectedFrameFixture=(Fixture)e.getSource();
        
            clickSelectedFrameFixture.setBorder(BorderFactory.createDashedBorder(Color.RED,10,5));
             }

            else{
                JOptionPane.showMessageDialog(this, 
                     "Cannot select multiple fixtures",
                     "Error",
                     JOptionPane.ERROR_MESSAGE);


            }
        }
        else{
            JOptionPane.showMessageDialog(this, 
                     "Cannot select fixture without selecting room ",
                     "Error",
                     JOptionPane.ERROR_MESSAGE);

        }
    }
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() instanceof Room) {
            if (clickSelectedRoom != null) return;
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
        if (e.getSource() instanceof Fixture && selectedFixture == null) {
            if(clickSelectedRoom!=null){
            selectedFixture = (Fixture) e.getSource();

            // Store original position
            originalFixX = selectedFixture.getX();
            originalFixY = selectedFixture.getY();
    
            // Get mouse offset
            offsetFixX = e.getX();
            offsetFixY = e.getY();
    
            // Optional: Visual feedback
            selectedFixture.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } 
        else{
            JOptionPane.showMessageDialog(this, 
                     "Cannot drag fixture without selecting room",
                     "Error",
                     JOptionPane.ERROR_MESSAGE);

        }
    }
       
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        
            if(selectedRoom != null) {
                // Check for overlap at release position
                if(!isValidPosition(selectedRoom)) {
                    if(checkOverlap(selectedRoom)){
                     // Optionally show a message to user
                     JOptionPane.showMessageDialog(this, 
                     "Cannot place room here - overlap detected",
                     "Overlap Error",
                     JOptionPane.ERROR_MESSAGE);
                     
                    // Snap back to original position immediately
                    selectedRoom.setLocation(originalX, originalY);}
                    if(!isWithinBounds(selectedRoom)){
                         // Optionally show a message to user
                     JOptionPane.showMessageDialog(this, 
                     "Cannot place room here - outside canvas",
                     "Boundary Error",
                     JOptionPane.ERROR_MESSAGE);
                     
                    // Snap back to original position immediately
                    selectedRoom.setLocation(originalX, originalY);
                    }

                    
                   
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
            if (selectedFixture != null) {
                // Check if the final position is valid
                if (!clickSelectedRoom.isValidFixturePosition(selectedFixture)) {
                    // Restore to original position
                    selectedFixture.setLocation(originalFixX, originalFixY);
                    JOptionPane.showMessageDialog(
                        this,
                        "Invalid position: Fixture either overlaps or is outside room bounds.",
                        "Position Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    // Update the fixture's final position in the room's fixture list
                    int index = clickSelectedRoom.fixtures.indexOf(selectedFixture);
                    if (index != -1) {
                        Fixture updatedFixture = clickSelectedRoom.fixtures.get(index);
                        updatedFixture.setLocation(selectedFixture.getX(), selectedFixture.getY());
                    }
                }
        
                // Remove visual feedback
                selectedFixture.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
                selectedFixture = null;
                clickSelectedRoom.revalidate();
                clickSelectedRoom.repaint();
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
        if (selectedFixture != null) {
            // Get the new position (relative to the room)
            Point pFix = SwingUtilities.convertPoint(
                selectedFixture,
                e.getPoint(),
                clickSelectedRoom
            );
    
            // Calculate new position
            int newFixX = pFix.x - offsetFixX;
            int newFixY = pFix.y - offsetFixY;
    
            // Temporarily update the fixture's position
            selectedFixture.setLocation(newFixX, newFixY);
    
            // Check for overlaps and bounds dynamically
            if (!clickSelectedRoom.isValidFixturePosition(selectedFixture)) {
                // Provide visual feedback if the fixture is invalid
                selectedFixture.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else {
                // Reset the border if the position is valid
                selectedFixture.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
            }
    
            clickSelectedRoom.revalidate();
            clickSelectedRoom.repaint();
        }
    }
    


    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
}

