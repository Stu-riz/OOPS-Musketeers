import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Project {

    public static void main(String[] args) {
        
        JFrame frame= new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(new BorderLayout(10,10));
        frame.setVisible(true);

        JPanel upperPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel canvasPanel = new JPanel();

        upperPanel.setBackground(Color.BLACK);
        leftPanel.setBackground((new Color(235,254,255)));
        canvasPanel.setBackground(Color.GRAY);

        upperPanel.setPreferredSize(new Dimension(100,100));
        leftPanel.setPreferredSize(new Dimension(150,100));
        canvasPanel.setPreferredSize(new Dimension(100,100));

        frame.add(upperPanel,BorderLayout.NORTH);
        frame.add(leftPanel,BorderLayout.WEST);
        frame.add(canvasPanel,BorderLayout.CENTER);

        JLabel label1=new JLabel();
        label1.setText("MENU BAR");
        label1.setFont(new Font("SERIF",Font.PLAIN,15));
        label1.setForeground(new Color(98,137,60));
        label1.setHorizontalTextPosition(JLabel.LEFT);

        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(label1);

        JButton addRoomButton=new JButton();
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
        JButton clearBoardButton=new JButton();

        /*
         *JButtons save,selectroom,draganddrop,selectfixture,rotatefixture
         */
        JButton saveButton=new JButton();
        JButton selectRoomButton=new JButton();
        JButton selectFixtureButton=new JButton();
        JButton dragDropButton=new JButton();
        JButton rotateFixtureButton=new JButton();


        upperPanel.add(saveButton);
        upperPanel.add(selectRoomButton);
        upperPanel.add(selectFixtureButton);
        upperPanel.add(rotateFixtureButton);
        upperPanel.add(dragDropButton);

        upperPanel.setLayout(new GridLayout(1,5));


        saveButton.setText("SAVE");
        saveButton.setBounds(0,0,100,40);
        saveButton.setFocusable(false);
        
        selectRoomButton.setText("SELECT A ROOM");
        selectRoomButton.setBounds(0,0,100,40);
        selectRoomButton.setFocusable(false);

        selectFixtureButton.setText("SELECT A FIXTURE");
        selectFixtureButton.setBounds(0,0,100,40);
        selectFixtureButton.setFocusable(false);

        rotateFixtureButton.setText("ROTATE FIXTURE");
        rotateFixtureButton.setBounds(0,0,100,40);
        rotateFixtureButton.setFocusable(false);

        dragDropButton.setText("DRAG AND DROP");
        dragDropButton.setBounds(0,0,100,40);
        dragDropButton.setFocusable(false);
        

      
        leftPanel.add(addRoomButton);
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
        leftPanel.add(clearBoardButton);

        leftPanel.setLayout(new GridLayout(14,1));

        addRoomButton.setText("ADD ROOM");
        addRoomButton.setBounds(0,0,100,40);
        addRoomButton.setFocusable(false);
        addRoomButton.setBackground(Color.ORANGE);

        addDoorButton.setText("ADD DOOR");
        addDoorButton.setBounds(0,0,100,40);
        addDoorButton.setFocusable(false);
        addDoorButton.setBackground(Color.GREEN);

        addWindowButton.setText("ADD WINDOW");
        addWindowButton.setBounds(0,0,100,40);
        addWindowButton.setFocusable(false);
        addWindowButton.setBackground(Color.PINK);

        clearBoardButton.setText("CLEAR CANVAS");
        clearBoardButton.setBounds(0,0,100,40);
        clearBoardButton.setFocusable(false);
        clearBoardButton.setBackground(Color.RED);

        addChairButton.setText("CHAIR");
        addChairButton.setBounds(0,0,40,40);
        addChairButton.setFocusable(false);


        addDiningButton.setText("DINING TABLE");
        addDiningButton.setBounds(0,0,40,40);
        addDiningButton.setFocusable(false);

        addKitchenSinkButton.setText("KITCHEN SINK");
        addKitchenSinkButton.setBounds(0,0,40,40);
        addKitchenSinkButton.setFocusable(false);

        addWashBasinButton.setText("WASH BASIN");
        addWashBasinButton.setBounds(0,0,40,40);
        addWashBasinButton.setFocusable(false);

        addBedButton.setText("BED");
        addBedButton.setBounds(0,0,40,40);
        addBedButton.setFocusable(false);

        addTableButton.setText("TABLE");
        addTableButton.setBounds(0,0,40,40);
        addTableButton.setFocusable(false);

        addSofaButton.setText("SOFA");
        addSofaButton.setBounds(0,0,40,40);
        addSofaButton.setFocusable(false);

        addStoveButton.setText("STOVE");
        addStoveButton.setBounds(0,0,40,40);
        addStoveButton.setFocusable(false);

        addCommodeButton.setText("COMMODE");
        addCommodeButton.setBounds(0,0,40,40);
        addCommodeButton.setFocusable(false);

        addShowerButton.setText("SHOWER");
        addShowerButton.setBounds(0,0,40,40);
        addShowerButton.setFocusable(false);

        

       


        
        }





}



