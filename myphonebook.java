import java.sql.*;
import java.awt.*;
import java.awt.event.*;
class MyWindow extends Frame implements ActionListener
{
	Panel p1,p2,p3,p4;
	Label pb,fname,lname,cno;
	Button btn1,btn2,btn3,btn4,btn5,btn6;
	TextField txt1,txt2,txt3;
	TextArea txr;
	Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
	int rn=0;
	
	
	public MyWindow()
	{
		super("My Phone Book");
		addWindowListener(new WindowAdapter()
							{
								public void windowClosing(WindowEvent we)
								{
									System.exit(0);
								}
							}
						);
		setSize(400,300);
		p1=new Panel();
		p2=new Panel();
		p3=new Panel();
		p4=new Panel();
		
		pb=new Label("PHONE BOOK");
		fname=new Label("First Name");
		lname=new Label("Last Name");
		cno=new Label("Contact Number");
		txt1=new TextField(30);
		txt2=new TextField(30);
		txt3=new TextField(30);
		btn1=new Button("Add New");
		btn2=new Button("Save");
		btn3=new Button("Cancel");
		btn4=new Button("Delete");
		btn5=new Button("Search");
		btn6=new Button("Exit");
		txr=new TextArea(5,300);
			
		p1.setLayout(new FlowLayout());
		p1.add(pb);
		pb.setFont(new Font("Arial",Font.BOLD,18));
		p2.setLayout(new GridLayout(3,2));
		p2.add(fname);
		p2.add(txt1);
		p2.add(lname);
		p2.add(txt2);
		p2.add(cno);
		p2.add(txt3);
		
		p3.setLayout(new GridLayout(2,3));
		p3.add(btn1);
		p3.add(btn2);
		p3.add(btn3);
		p3.add(btn4);
		p3.add(btn5);
		p3.add(btn6);
		
		p4.setLayout(new BorderLayout());
		p4.add(txr,BorderLayout.NORTH);
		
		setLayout(new GridLayout(4,1));
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		
		setResizable(false);
		setVisible(true);
	}
	String Fname="",Lname="",Cno="";
			
	
	public void actionPerformed(ActionEvent ae)
	{
		
		try
		{
			String driver="oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url="jdbc:oracle:thin:@localhost:1521:ORCL";
			String userid="scott";
			String password="tiger";
			con=DriverManager.getConnection(url,userid,password);
			if(ae.getSource()==btn1)
			{
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
				txr.setText(txr.getText()+"\n");
				txr.setText(txr.getText()+"Enter New Contact");
				txt1.requestFocus();
			}
			if(ae.getSource()==btn2)
			{
				Fname=txt1.getText();
				Lname=txt2.getText();
				Cno=txt3.getText();
				String query="INSERT INTO PHONEBOOK(FName,LName,Contact)VALUES(?,?,?)";
				ps=con.prepareStatement(query);
				ps.setString(1,Fname);
				ps.setString(2,Lname);
				ps.setString(3,Cno);
				ps.executeUpdate();
				txr.setText(txr.getText()+"\n");
				txr.setText(txr.getText()+"Contact Saved");	
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
				
			}
			if(ae.getSource()==btn3)
			{
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
				txr.setText(txr.getText()+"\n");
				txr.setText(txr.getText()+"Cancelled");
				txt1.requestFocus();

			}
			if(ae.getSource()==btn4)
			{
				Fname=txt1.getText();
				Lname=txt2.getText();
				Cno=txt3.getText();
				String query="DELETE FROM PHONEBOOK WHERE FName='" + Fname + "' or lname='" + Lname + "' or contact='" + Cno + "'";
				stmt=con.createStatement();
				rn=stmt.executeUpdate(query);
				if(rn>0)
				{	
					txr.setText(txr.getText()+"\n");
					txr.setText(txr.getText()+"Contact Deleted");
				}
				else
					txr.setText("Contact Not Found");
			
			}
			if(ae.getSource()==btn5)
			{
				Fname=txt1.getText();
				Lname=txt2.getText();
				Cno=txt3.getText();
				
				String query="SELECT * FROM PHONEBOOK WHERE FName='" + Fname + "' or lname='" + Lname + "' or contact='" + Cno + "'";
				stmt=con.createStatement();
	
				rs=stmt.executeQuery(query);
				if(rs.next())
				{
					txt1.setText(rs.getString("FNAME"));
					txt2.setText(rs.getString("LNAME"));
					txt3.setText(rs.getString("CONTACT"));
					txr.setText("Contact Found");
				}
				else
					txr.setText("Contact Not Found");
			
			}
			if(ae.getSource()==btn6)
			{
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		
	}
}
class Demo
{
	public static void main(String[] args)
	{
		new MyWindow();
	}
}
