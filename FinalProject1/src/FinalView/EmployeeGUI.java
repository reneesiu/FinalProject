package FinalView;

import java.awt.*;
import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLDataException;
import FinalModel.Employee;
import FinalModel.EmployeeCRUD;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.DefaultTableColumnModel;
//throws SQLException, IOException        

public class EmployeeGUI extends JFrame{
   public static final int width= 410;
   public static final int height= 510;
   
   private Employee emp;
   private Employee emp2;
   private Employee emp3;
   private EmployeeCRUD crud=new EmployeeCRUD();
   
   private JTabbedPane tp;
   private JPanel panel1;
   private JPanel panel2;
   private JPanel panel3;
   private JPanel panel4;
   private JTable table4;
   
   //Panel 1 Objs
   private JLabel lblTitle;
   private JLabel lbllname;
   private JLabel lblfname;
   private JLabel lbladdress1;
   private JLabel lbladdress2;
   private JLabel lblcity;
   private JLabel lblstate;
   private JLabel lblDOB;
   private JLabel lblsalary;
   
   private JTextField txtTitle;
   private JTextField txtlname;
   private JTextField txtfname;
   private JTextField txtaddress1;
   private JTextField txtaddress2;
   private JTextField txtcity;
   private JTextField txtstate;
   private JTextField txtDOB;
   private JTextField txtsalary;
   
   private JButton btnSave;
   private JButton btnClear;
   
   //Panel 2 Obj


    public EmployeeGUI(){
            super();
            createPanel();
            setFrame();
            }        

    private void createPanel(){
        super.setLayout(null);
        //Panels
        tp = new JTabbedPane();
        panel1= new JPanel(null);
        panel2= new JPanel(null);
        panel3=new JPanel(null);
        panel4= new JPanel(new BorderLayout());
        
        tp.setBounds(0,0,400,470);
        tp.addTab("Insert", panel1);
        tp.addTab("Delete", panel2);
        tp.addTab("Update", panel3);
        tp.addTab("List", panel4);
        
        this.add(tp);
        
        //Panel1 setup    
        panel1.setSize(400, 480);
        lblTitle=new JLabel("EMPLOYEE INPUT DATA");
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setForeground(Color.BLUE);
        lblTitle.setBounds(100, 20, 220, 30);
        panel1.add(lblTitle);

        lbllname=new JLabel("Last Name : ");
        txtlname=new JTextField(20);
        lbllname.setHorizontalAlignment(JLabel.RIGHT);
        lbllname.setBounds(100, 70, 100,25);
        txtlname.setBounds(200, 70, 100,25);
        panel1.add(lbllname);
        panel1.add(txtlname);

        lblfname=new JLabel("First Name : ");
        txtfname=new JTextField(20);
        lblfname.setHorizontalAlignment(JLabel.RIGHT);
        lblfname.setBounds(100, 105, 100,25);
        txtfname.setBounds(200, 105, 100,25);
        panel1.add(lblfname);
        panel1.add(txtfname);
        lbladdress1=new JLabel("Address 1 : ");
        txtaddress1=new JTextField(20);
        lbladdress1.setHorizontalAlignment(JLabel.RIGHT);
        lbladdress1.setBounds(100, 140, 100,25);
        txtaddress1.setBounds(200, 140, 120,25);
        panel1.add(lbladdress1);
        panel1.add(txtaddress1);

        lbladdress2=new JLabel("Address 2 : ");
        txtaddress2=new JTextField(20);
        lbladdress2.setHorizontalAlignment(JLabel.RIGHT);
        lbladdress2.setBounds(100, 175, 100,25);
        txtaddress2.setBounds(200, 175, 120,25);
        panel1.add(lbladdress2);
        panel1.add(txtaddress2);

        lblcity=new JLabel("City : ");
        txtcity=new JTextField(20);
        lblcity.setHorizontalAlignment(JLabel.RIGHT);
        lblcity.setBounds(100, 210, 100,25);
        txtcity.setBounds(200, 210, 100,25);
        panel1.add(lblcity);
        panel1.add(txtcity);
        
        lblstate=new JLabel("State : ");
        txtstate=new JTextField(20);
        lblstate.setHorizontalAlignment(JLabel.RIGHT);
        lblstate.setBounds(100, 245, 100,25);
        txtstate.setBounds(200, 245, 100,25);
        panel1.add(lblstate);
        panel1.add(txtstate);

        lblDOB=new JLabel("DOB : ");
        txtDOB=new JTextField(20);
        lblDOB.setHorizontalAlignment(JLabel.RIGHT);
        lblDOB.setBounds(100, 280, 100,25);
        txtDOB.setBounds(200, 280, 100,25);
        panel1.add(lblDOB);
        panel1.add(txtDOB);

        lblsalary=new JLabel("Salary : ");
        txtsalary=new JTextField(20);
        lblsalary.setHorizontalAlignment(JLabel.RIGHT);
        lblsalary.setBounds(100, 315, 100,25);
        txtsalary.setBounds(200, 315, 100,25);
        panel1.add(lblsalary);
        panel1.add(txtsalary);
        
        btnSave=new JButton("Save Employee");
        btnSave.addActionListener(new Save());
        btnSave.setBounds(80, 360, 130, 25);
        panel1.add(btnSave);

        btnClear=new JButton("Clear");
        btnClear.addActionListener(new Clear());
        btnClear.setBounds(230, 360, 100, 25);
        panel1.add(btnClear);

}
    private void setFrame(){
        super.setTitle("Employee System");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocation(200,100);
        super.setResizable(false);
        super.setSize(width,height);
        super.setVisible(true);
    }
    
    private class Save implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String lname= txtlname.getText();
            String fname= txtfname.getText();
            String address1= txtaddress1.getText();
            String address2= txtaddress2.getText();
            String city= txtcity.getText();
            String state= txtstate.getText();
            String DOB= txtDOB.getText();
            double salary= Double.parseDouble(txtsalary.getText());
            emp = new Employee(lname, fname, address1, address2, city, state, DOB, salary);
            try{
                crud.insertUser(emp);
            } 
            catch(Exception ex){
                System.out.println(ex);
            }
            JOptionPane.showMessageDialog(null, "Employee succesfully added");            
        }
    }
    private class Clear implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            txtlname.setText("");
            txtfname.setText("");
            txtaddress1.setText("");
            txtaddress2.setText("");
            txtcity.setText("");
            txtstate.setText("");
            txtDOB.setText("");
            
        }
    }
}