package newd;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import java.sql.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

interface Database{
	static final String Driver="com.mysql.cj.jdbc.Driver";//引擎
	static final String Url="jdbc:mysql://localhost:3306/test?useSSL=true&serverTimezone=UTC&characterEncoding=UTF-8";//数据库连接
}
class Login{//登录窗口
	JFrame frame = new JFrame("My Note");
	JPanel pan1 = new JPanel();
	JPanel pan2 = new JPanel();
	JPanel pan3 = new JPanel();
	JLabel lab1 = new JLabel("My Note 登录",JLabel.CENTER);
	JLabel lab2 = new JLabel("用户名：");
	JLabel lab3 = new JLabel("密码：");
	JTextField t1 = new JTextField();//用户名
	JPasswordField t2 = new JPasswordField();//密码
	JButton btn1 = new JButton("确定");
	JButton btn2 = new JButton("清除");
	JButton btn3 = new JButton("注册");
	public Login(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//设置显示风格为当前系统风格
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btn1.setFocusable(false);
		btn2.setFocusable(false);
		btn3.setFocusable(false);
		
		lab1.setForeground(Color.white);//设置背景
		pan1.setBackground(Color.blue);
		pan1.add(lab1);
		
		pan2.setLayout(new GridLayout(2,2));
		pan2.add(lab2);
		t1.setColumns(10);
		t2.setColumns(10);
		pan2.add(t1);
		pan2.add(lab3);
		pan2.add(t2);
		
		pan3.add(btn1);
		pan3.add(btn2);
		pan3.add(btn3);
		
		t1.addActionListener(new ActionListener() {//进行确认
			@Override
			public void actionPerformed(ActionEvent e) {
				ensure();
			}
		});
		
		t2.addActionListener(new ActionListener() {//进行确认
			@Override
			public void actionPerformed(ActionEvent e) {
				ensure();
			}
		});
		
		btn1.addActionListener(new ActionListener() {//主界面的确定
			@Override
			public void actionPerformed(ActionEvent e) {
				ensure();
			}
		});
		
		btn2.addActionListener(new ActionListener() {//主界面的清除
			@Override
			public void actionPerformed(ActionEvent e) {
				t1.setText("");
				t2.setText("");
			}
		});
		
		btn3.addActionListener(new ActionListener() {//主界面的注册按钮

			@Override
			public void actionPerformed(ActionEvent e) {
				close();
				JFrame frame = new JFrame("新用户注册");
				JLabel lab1 = new JLabel("输入新用户名：",JLabel.RIGHT);
				JLabel lab2 = new JLabel("输入密码：",JLabel.RIGHT);
				JLabel lab3 = new JLabel("重新输入密码：",JLabel.RIGHT);
				JLabel lab4 = new JLabel("");
				JTextField t1 = new JTextField();
				JPasswordField t2 = new JPasswordField();
				JPasswordField t3 = new JPasswordField();
				JButton btn1 = new JButton("确定");
				JButton btn2 = new JButton("清除");
				
				btn1.setFocusable(false);
				btn2.setFocusable(false);
				
				lab4.setForeground(Color.red);
				
				t1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String s1=t1.getText();
						String s2=t2.getText();
						String s3=t3.getText();
						String pattern1="^[A-z]{1,10}$";//用户名只能是10个字母
						String pattern2="^\\w{8,15}$";//密码只能是字母数字下划线
						if(!Pattern.matches(pattern1,s1)) {
							lab4.setText("用户名格式不正确!");
							t1.setText("");
							t2.setText("");
							t3.setText("");
						}
						else if(!Pattern.matches(pattern2,s2)) {
							lab4.setText("密码格式不正确!");
							t2.setText("");
							t3.setText("");
						}
						else if(s2.compareTo(s3)!=0) {
							lab4.setText("俩次密码不一样!");
							t2.setText("");
							t3.setText("");
						}
						else {
							String root="root";
							String password="121022gui";
							Connection con = null;
							Statement st = null;
							PreparedStatement pre = null;
							boolean f=false;//判断用户名是否冲突
							try {
								String sql="select root from account";
								con = ConnectMysql.getCon(root, password);
								st = con.createStatement();
								ResultSet set = st.executeQuery(sql);
								while(set.next()) {
									if(set.getString(1).compareTo(s1)==0) {
											lab4.setText("用户名冲突!");
											t1.setText("");
											t2.setText("");
											t3.setText("");
											f=true;
											break;
										}
									}
									if(f==false) {
										sql="insert into account(root,password) values('"+s1+"','"+s2+"')";
										st.executeUpdate(sql);
										frame.dispose();
										JOptionPane.showMessageDialog(frame, "注册成功", "My Note", JOptionPane.INFORMATION_MESSAGE);
									}
									con.close();
									st.close();
								} catch (SQLException e1) {}
								
							}
					}
				});
				
				t2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String s1=t1.getText();
						String s2=t2.getText();
						String s3=t3.getText();
						String pattern1="^[A-z]{1,10}$";//用户名只能是10个字母
						String pattern2="^\\w{8,15}$";//密码只能是字母数字下划线
						if(!Pattern.matches(pattern1,s1)) {
							lab4.setText("用户名格式不正确!");
							t1.setText("");
							t2.setText("");
							t3.setText("");
						}
						else if(!Pattern.matches(pattern2,s2)) {
							lab4.setText("密码格式不正确!");
							t2.setText("");
							t3.setText("");
						}
						else if(s2.compareTo(s3)!=0) {
							lab4.setText("俩次密码不一样!");
							t2.setText("");
							t3.setText("");
						}
						else {
							String root="root";
							String password="121022gui";
							Connection con = null;
							Statement st = null;
							PreparedStatement pre = null;
							boolean f=false;//判断用户名是否冲突
							try {
								String sql="select root from account";
								con = ConnectMysql.getCon(root, password);
								st = con.createStatement();
								ResultSet set = st.executeQuery(sql);
								while(set.next()) {
									if(set.getString(1).compareTo(s1)==0) {
											lab4.setText("用户名冲突!");
											t1.setText("");
											t2.setText("");
											t3.setText("");
											f=true;
											break;
										}
									}
									if(f==false) {
										sql="insert into account(root,password) values('"+s1+"','"+s2+"')";
										st.executeUpdate(sql);
										frame.dispose();
										JOptionPane.showMessageDialog(frame, "注册成功", "My Note", JOptionPane.INFORMATION_MESSAGE);
									}
									con.close();
									st.close();
								} catch (SQLException e1) {}
								
							}
					}
				});
				
				t3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String s1=t1.getText();
						String s2=t2.getText();
						String s3=t3.getText();
						String pattern1="^[A-z]{1,10}$";//用户名只能是10个字母
						String pattern2="^\\w{8,15}$";//密码只能是字母数字下划线
						if(!Pattern.matches(pattern1,s1)) {
							lab4.setText("用户名格式不正确!");
							t1.setText("");
							t2.setText("");
							t3.setText("");
						}
						else if(!Pattern.matches(pattern2,s2)) {
							lab4.setText("密码格式不正确!");
							t2.setText("");
							t3.setText("");
						}
						else if(s2.compareTo(s3)!=0) {
							lab4.setText("俩次密码不一样!");
							t2.setText("");
							t3.setText("");
						}
						else {
							String root="root";
							String password="121022gui";
							Connection con = null;
							Statement st = null;
							PreparedStatement pre = null;
							boolean f=false;//判断用户名是否冲突
							try {
								String sql="select root from account";
								con = ConnectMysql.getCon(root, password);
								st = con.createStatement();
								ResultSet set = st.executeQuery(sql);
								while(set.next()) {
									if(set.getString(1).compareTo(s1)==0) {
											lab4.setText("用户名冲突!");
											t1.setText("");
											t2.setText("");
											t3.setText("");
											f=true;
											break;
										}
									}
									if(f==false) {
										sql="insert into account(root,password) values('"+s1+"','"+s2+"')";
										st.executeUpdate(sql);
										frame.dispose();
										JOptionPane.showMessageDialog(frame, "注册成功", "My Note", JOptionPane.INFORMATION_MESSAGE);
									}
									con.close();
									st.close();
								} catch (SQLException e1) {}
								
							}
					}
				});
					
				btn1.addActionListener(new ActionListener() {//注册里面的确定按钮
					@Override
					public void actionPerformed(ActionEvent e) {
						String s1=t1.getText();
						String s2=t2.getText();
						String s3=t3.getText();
						String pattern1="^[A-z]{1,10}$";//用户名只能是10个字母
						String pattern2="^\\w{8,15}$";//密码只能是字母数字下划线
						if(!Pattern.matches(pattern1,s1)) {
							lab4.setText("用户名格式不正确!");
							t1.setText("");
							t2.setText("");
							t3.setText("");
						}
						else if(!Pattern.matches(pattern2,s2)) {
							lab4.setText("密码格式不正确!");
							t2.setText("");
							t3.setText("");
						}
						else if(s2.compareTo(s3)!=0) {
							lab4.setText("俩次密码不一样!");
							t2.setText("");
							t3.setText("");
						}
						else {
							String root="root";
							String password="121022gui";
							Connection con = null;
							Statement st = null;
							PreparedStatement pre = null;
							boolean f=false;//判断用户名是否冲突
							try {
								String sql="select root from account";
								con = ConnectMysql.getCon(root, password);
								st = con.createStatement();
								ResultSet set = st.executeQuery(sql);
								while(set.next()) {
									if(set.getString(1).compareTo(s1)==0) {
											lab4.setText("用户名冲突!");
											t1.setText("");
											t2.setText("");
											t3.setText("");
											f=true;
											break;
										}
									}
									if(f==false) {
										sql="insert into account(root,password) values('"+s1+"','"+s2+"')";
										st.executeUpdate(sql);
										frame.dispose();
										JOptionPane.showMessageDialog(frame, "注册成功", "My Note", JOptionPane.INFORMATION_MESSAGE);
									}
									con.close();
									st.close();
								} catch (SQLException e1) {}
								
							}
						}
						
					});
					
					btn2.addActionListener(new ActionListener() {//注册里面的清除按钮

						@Override
						public void actionPerformed(ActionEvent e) {
							lab4.setText("");
							t1.setText("");
							t2.setText("");
							t3.setText("");
						}
						
					});
					
					frame.addWindowListener(new WindowAdapter() {//关闭窗口
						public void windowClosing(WindowEvent e) {
							frame.dispose();
							open();
						}
					});
					frame.setLayout(new GridLayout(6,2));
					frame.add(new JLabel());
					frame.add(new JLabel());
					frame.add(lab1);
					frame.add(t1);
					frame.add(lab2);
					frame.add(t2);
					frame.add(lab3);
					frame.add(t3);
					frame.add(new JLabel());
					frame.add(lab4);
					frame.add(btn1);
					frame.add(btn2);
					frame.setLocation(600,300);
					frame.setSize(250,200);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			
		});
		frame.add(pan1,BorderLayout.NORTH);
		frame.add(pan2,BorderLayout.CENTER);
		frame.add(pan3,BorderLayout.SOUTH);
		frame.setLocation(600,300);
		frame.setSize(250,150);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void ensure() {//链接登录功能
		String root="root";
		String password="121022gui";
		Connection con = null;
		Statement st = null;
		PreparedStatement pre = null;
		String s1 = t1.getText();
		String s2 = t2.getText();
		boolean f = false;//判断用户名或密码是否正确
		try {
			String sql="select * from account";
			con = ConnectMysql.getCon(root, password);
			st = con.createStatement();
			ResultSet set = st.executeQuery(sql);
			while(set.next()) {
				if(set.getString(1).compareTo(s1)==0 && set.getString(2).compareTo(s2)==0) f = true ;//用户名和密码都正确才行
			}
			if(f) {
				JOptionPane.showMessageDialog(frame, "登录成功", "My Note", JOptionPane.INFORMATION_MESSAGE);
				close();
				new Note().iniv();//创建记事本
			}
			else JOptionPane.showMessageDialog(frame, "用户名或密码不正确", "My Note", JOptionPane.INFORMATION_MESSAGE);
			con.close();
			st.close();
		} catch (SQLException e1) {}
	}
	public void close() {//隐藏主窗口
		this.frame.setVisible(false);
	}
	public void open()  {//显示主窗口
		this.frame.setVisible(true);
	}
}

public class ConnectMysql{//链接数据库
	Connection con = null;
	PreparedStatement pre = null;
	Statement st = null;
	public static Connection getCon(String root,String password) {//连接数据库
		Connection con = null;
		try {
			Class.forName(Database.Driver);
			con = DriverManager.getConnection(Database.Url,root,password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("出错");
			e.printStackTrace();
		}
		return con;
	}
	public static void main(String[] args){
		new Login();
	}
}