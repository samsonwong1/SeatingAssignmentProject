import java.util.ArrayList;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.InputMismatchException;
import javax.swing.Timer;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class EnrollmentSystem {
    private static ArrayList<Student> studentList = new ArrayList <Student>();
    public EnrollmentSystem() {
        generateJTable();
    }

    // private static boolean run;

    // public void setVisible(boolean run){
    //     this.run = run;
    // }


    public static void generateJTable() {

        // create JFrame and JTable
        JFrame frame = new JFrame();
      
        // create a table model and set a Column Identifiers to this model
        Object[] columns = { "Name", "ID", "Cohort",
        "Friends" };
        DefaultTableModel model = new DefaultTableModel();
        //set columns header
        JTable table = new JTable(model){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        model.setColumnIdentifiers(columns);
    
        // Change A JTable Background Color, Font Size, Font Color, Row Height
        table.setForeground(Color.black);
        Font font = new Font("Calibri", Font.ITALIC, 15);
        table.setFont(font);
        table.setRowHeight(20);
        
        // create JTextFields to hold the value
         JTextField textName = new JTextField();
         JTextField textID = new JTextField();
         JTextField textCohort = new JTextField();
         JTextField textFriends = new JTextField();

        
        // create JButtons to add the action
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");
        JButton btnBack = new JButton("Back");


        JLabel errorMessage = new JLabel();
        errorMessage.setForeground (Color.red);
        errorMessage.setFont(new Font("Calibri", Font.ITALIC, 25));

       // JComboBox<Student> friendPrefOne = new JComboBox<Student>();


        errorMessage.setBounds(350, 250, 300, 25);
        
        textName.setBounds(20, 240, 150, 25);
        textID.setBounds(20, 270, 150, 25);
        textCohort.setBounds(20, 300, 150, 25);
        textFriends.setBounds(20, 330, 150, 25);


        btnBack.setBounds(10, 210, 100, 25);
        btnAdd.setBounds(200, 240, 100, 25);
        btnUpdate.setBounds(200, 285, 100, 25);
        btnDelete.setBounds(200, 330, 100, 25);
        
        // create JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);
        
        frame.setLayout(null);
        
        frame.add(pane);
        
        // add JTextFields to the jframe
        frame.add(textName);
        frame.add(textID);
        frame.add(textCohort);
        frame.add(textFriends);
        frame.add(errorMessage);
        
        // add JButtons to the jframe
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnUpdate);
        frame.add(btnBack);
        
        // create an array of objects to set the row data
        Object[] data = new Object[4];
        
        // button add row - Clicked on Add Button
        btnAdd.addActionListener(new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
        
            try{
                data[0] = textName.getText();
                data[1] = Integer.valueOf(textID.getText());
                data[2] = textCohort.getText();
                data[3] = textFriends.getText();
            }catch(NumberFormatException a){
                // data[0]=null;
                // data[1]=null;
                // data[2]=null;
                // data[3]=null;
  
                for (int i=0; i <4; i++){
                    data[i]= null;
                }
                // for(Object objects: data){
                //     objects == null;
                // }   

                errorMessage.setText("Inccorect Inputs");
                clearErrorMessage(errorMessage);
            }

        textName.setText("");
        textID.setText("");
        textCohort.setText("");
        textFriends.setText("");
        addStudent((String)data[0],(int)data[1], (String)data[2]);
        model.addRow(data);


        }
        });

        // button remove row - Clicked on Delete Button
        btnDelete.addActionListener(new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
        
        // i = the index of the selected row
        int i = table.getSelectedRow();

        if (i >= 0) {
        // remove a row from jtable
        //removeStudent((int)model.getValueAt(i,1));
        removeStudent(i);
        model.removeRow(i);
        textName.setText("");
        textID.setText("");
        textCohort.setText("");
        textFriends.setText("");
        } else {
        errorMessage.setText("Please select a valid row");
        clearErrorMessage(errorMessage);

        }
        }
        });
        
        // get selected row data From table to textfields
        table.addMouseListener(new MouseAdapter() {
        
        @Override
        public void mouseClicked(MouseEvent e) {
        
        // i = the index of the selected row
        int i = table.getSelectedRow();
        
        textName.setText(model.getValueAt(i, 0).toString());
        textID.setText(model.getValueAt(i, 1).toString());
        textCohort.setText(model.getValueAt(i, 2).toString());
        textFriends.setText(model.getValueAt(i, 3).toString());
        }
        });
        
        // button update row - Clicked on Update Button
        btnUpdate.addActionListener(new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {

        displayStudents();
        
        // i = the index of the selected row
        int i = table.getSelectedRow();
        
        if (i >= 0) {
            try{

                data[0] = textName.getText();
                data[1] = Integer.valueOf(textID.getText());
                data[2] = textCohort.getText();
                data[3] = textFriends.getText();

                model.setValueAt(data[0], i, 0);
                model.setValueAt(data[1], i, 1);
                model.setValueAt(data[2], i, 2);
                model.setValueAt(data[3], i, 3);


            }catch(NumberFormatException a){
                errorMessage.setText("Inccorect Inputs");
                clearErrorMessage(errorMessage);
            }

         updateStudent(i,(String)data[0],(int)data[1], (String)data[2]);

        } else {
            errorMessage.setText("Please select a valid row");
            clearErrorMessage(errorMessage);
            
        }
        }
        });

         btnBack.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SystemManager();
            }
            });
        
        frame.setSize(900, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        }

    public static void addStudent(String name, int id, String cohort ) {  

        studentList.add(new Student(name, id, cohort));

    }


    public static void removeStudent(int i) {

        studentList.remove(i);

        // for(int i = 0; i < studentList.size(); i++){
        //     if(studentList.get(i).getId() == id){
        //         studentList.remove(i);
        //     } 
        // }

    }

    public static void updateStudent(int i, String name, int id, String cohort) {
        studentList.get(i).setName(name);
        studentList.get(i).setID(id);
        studentList.get(i).setCohort(cohort);
    }


    public static void clearErrorMessage(JLabel errorMessage){

        //if you spam update error, the timer doesnt work
        Timer timer = new Timer (2500, new ActionListener(){
            public void actionPerformed (ActionEvent e){
                errorMessage.setText("");
            }
        });
        timer.start();
        
    }

    public  static void displayStudents() {

        for(Student s: studentList){

            System.out.println(s.getId());
        }        
    }

}
