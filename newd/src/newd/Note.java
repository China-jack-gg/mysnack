package newd;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class Note {
	private JFrame frame = new JFrame("My Note");
	private JScrollPane center ;//中间滚轮容器
	private JTextArea area = new JTextArea();//中间文本区域
	private String url = "C:\\Users\\86173\\Desktop\\TXT测试";//默认路径文件夹
	private File F = new File(url+"\\My Note.txt");//默认打开文件
	private boolean change = false;//是否作了改动
	private boolean New = false;//是否新建了
	private UndoManager undom ;//类似于撤销重复监听器
	private JMenuBar bar = new JMenuBar();
	
	private JMenu file = new JMenu("文件(F)");//构建选项菜单
	private JMenu compile = new JMenu("编辑(E)");
	private JMenu layout = new JMenu("格式(O)");
	private JMenu look = new JMenu("查看(V)");
	private JMenu help = new JMenu("帮助(H)");
	
	private JMenuItem news = new JMenuItem("新建");//构建文件菜单项
	private JMenuItem open = new JMenuItem("打开");
	private JMenuItem save = new JMenuItem("保存");
	private JMenuItem other = new JMenuItem("另存为");
	private JMenuItem exit = new JMenuItem("退出");
	
	private JMenuItem recall = new JMenuItem("撤回");
	private JMenuItem repet = new JMenuItem("恢复");
	private JMenuItem cut = new JMenuItem("剪切");//构建编辑菜单项
	private JMenuItem copy = new JMenuItem("复制");
	private JMenuItem paste = new JMenuItem("粘贴");
	private JMenuItem delete = new JMenuItem("删除");
	private JMenuItem allsel = new JMenuItem("全选");
	private JMenuItem datetime = new JMenuItem("时间日期");
	
	private JCheckBoxMenuItem wrap = new JCheckBoxMenuItem("自动换行");//构建格式菜单项
	private JMenuItem font = new JMenuItem("字体...");
	
	private JMenuItem bebig = new JMenuItem("放大");
	private JMenuItem besmall = new JMenuItem("缩小");
	private JMenuItem bedefu = new JMenuItem("恢复默认缩放");
	
	private JMenuItem about = new JMenuItem("关于");//构建帮助菜单项
	
	private JPopupMenu pmenu = new JPopupMenu();
	
	private JMenuItem scut = new JMenuItem("剪切");//构建鼠标右击菜单项
	private JMenuItem scopy = new JMenuItem("复制");
	private JMenuItem spaste = new JMenuItem("粘贴");
	private JMenuItem sdelete = new JMenuItem("删除");
	private JMenuItem sallsel = new JMenuItem("全选");
	
	public void iniv() {
		
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
		
		readFont();
		center = new JScrollPane(area);
		
		file.add(news);//构建文件菜单项
		file.add(open);
		file.add(save);
		file.add(other);
		file.addSeparator();//添加分割线
		file.add(exit);
		
		compile.add(recall);
		compile.add(repet);
		compile.addSeparator();
		compile.add(cut);//构建编辑菜单项
		compile.add(copy);
		compile.add(paste);
		compile.add(delete);
		compile.addSeparator();
		compile.add(allsel);
		compile.addSeparator();
		compile.add(datetime);
		
		layout.add(wrap);//构建格式菜单项
		layout.add(font);
		
		look.add(bebig);
		look.add(besmall);
		look.addSeparator();
		look.add(bedefu);
		
		help.add(about);//构建帮助菜单项
		
		bar.add(file);//将选项菜单加入菜单组
		bar.add(compile);
		bar.add(layout);
		bar.add(look);
		bar.add(help);
		
		file.setMnemonic(KeyEvent.VK_F);//设置菜单栏热键
		compile.setMnemonic(KeyEvent.VK_E);
		layout.setMnemonic(KeyEvent.VK_O);
		look.setMnemonic(KeyEvent.VK_V);
		help.setMnemonic(KeyEvent.VK_H);
		
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));//文件菜单项设置快捷键
		news.setAccelerator(KeyStroke.getKeyStroke("control N"));
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		other.setAccelerator(KeyStroke.getKeyStroke("shift control released S"));
		exit.setAccelerator(KeyStroke.getKeyStroke("control E"));
		
		recall.setAccelerator(KeyStroke.getKeyStroke("control Z"));//编辑菜单项设置快捷键
		repet.setAccelerator(KeyStroke.getKeyStroke("control Y"));
		copy.setAccelerator(KeyStroke.getKeyStroke("control C"));
		cut.setAccelerator(KeyStroke.getKeyStroke("control X"));
		paste.setAccelerator(KeyStroke.getKeyStroke("control V"));
		delete.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		allsel.setAccelerator(KeyStroke.getKeyStroke("control A"));
		datetime.setAccelerator(KeyStroke.getKeyStroke("control shift released D"));
		
		font.setAccelerator(KeyStroke.getKeyStroke("control F"));//字体选项卡
		
		bebig.setAccelerator(KeyStroke.getKeyStroke("control shift released MINUS"));//查看菜单项设置快捷键
		besmall.setAccelerator(KeyStroke.getKeyStroke("control MINUS"));
		
		about.setAccelerator(KeyStroke.getKeyStroke("control shift released H"));//关于
		
		scopy.setAccelerator(KeyStroke.getKeyStroke("control C"));//鼠标菜单设置快捷键
		scut.setAccelerator(KeyStroke.getKeyStroke("control X"));
		spaste.setAccelerator(KeyStroke.getKeyStroke("control V"));
		sdelete.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		sallsel.setAccelerator(KeyStroke.getKeyStroke("control A"));
		
		news.addActionListener(new ActionListener() {//新建
			@Override
			public void actionPerformed(ActionEvent e) {
				if(change) {
					if(!New) {
						int f=JOptionPane.showConfirmDialog(frame, "是否保存原文件？", "My Note", JOptionPane.YES_NO_OPTION);
						if(f==JOptionPane.YES_OPTION)Save(F);
					}else {
						int f=JOptionPane.showConfirmDialog(frame, "你想要将更改保存到无标题吗？", "My Note", JOptionPane.YES_NO_OPTION);
						if(f==JOptionPane.YES_OPTION) Save(new File(url+"\\无标题.txt"));//保存到默认路径无标题
					}
				}
				New = true;//新建的
				area.setText("");//清空
				undom = new UndoManager();
				area.getDocument().addUndoableEditListener(undom);//新建也要重新加入撤销监听
				change = false;//新建后的都不是活动状态
				frame.setTitle("无标题");//新建的未保存的没有标题
			}
		});
		
		open.addActionListener(new ActionListener() {//打开
			@Override
			public void actionPerformed(ActionEvent e) {
				if(change) {
					if(New) {//是新建的需要保存
						int f=JOptionPane.showConfirmDialog(frame, "你想要将更改保存到无标题吗？", "My Note", JOptionPane.YES_NO_OPTION);
						if(f==JOptionPane.YES_OPTION) Save(new File(url+"\\无标题.txt"));//保存到默认路径无标题
					}else {
						int f=JOptionPane.showConfirmDialog(frame, "是否保存原文件？", "My Note", JOptionPane.YES_NO_OPTION);
						if(f==JOptionPane.YES_OPTION)Save(F);
					}
				}
				int result=0;
				JFileChooser filec=new JFileChooser(url);//默认文件打开位置
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt文件(*.txt)", "txt");
			   	filec.setFileFilter(filter);//增加文件过滤器
				filec.setDialogTitle("打开");//设置文本选择框标题
				result=filec.showOpenDialog(frame);
				if(result==JFileChooser.APPROVE_OPTION) {//表示选择了确定按钮
					F=filec.getSelectedFile();//更新文件位置
					Read(F);
					New = false;//意味着不是新建的了
				}
			}
		});
		
		save.addActionListener(new ActionListener() {//保存
			@Override
			public void actionPerformed(ActionEvent e) {
				if(change) {
					if(New) {//是新文件
						int result=0;
						JFileChooser filec=new JFileChooser(url);
						FileNameExtensionFilter filter = new FileNameExtensionFilter("txt文件(*.txt)", "txt");
					   	filec.setFileFilter(filter);//增加文件过滤器
						String defaultFileName="My Note"+".txt";//默认文件夹名字
						filec.setSelectedFile(new File(defaultFileName)); //设置默认文件名
						filec.setDialogTitle("另存为");//设置文本选择框标题
						result=filec.showSaveDialog(frame);
						if(result==JFileChooser.APPROVE_OPTION) {//表示选择了确定按钮
							File doc = filec.getCurrentDirectory();//目录
							File file = filec.getSelectedFile();//要保存的文件
							String[] st = doc.list();
							boolean falg = false;
							for(String x:st) {
								if(x.compareTo(file.getName())==0)falg = true;//目录下存在名字一样的文件
							}
							if(falg) {
								int f=JOptionPane.showConfirmDialog(null, "是否覆盖？", "My Note", JOptionPane.YES_NO_OPTION);
								if(f==JOptionPane.YES_OPTION) {
									F=file;//更改默认文件位置
									Save(F);
									New = false;
								}
							}
							else {
								F=filec.getSelectedFile();//更改默认文件
								Save(F);
								New = false;//保存后不是新窗口
							}
						}
					}
					else {
						Save(F);
						change = false;//保存过后就没有改动了
						frame.setTitle(F.getName());//窗口状态
					}
				}
			}
		});
		
		other.addActionListener(new ActionListener() {//另存为
			@Override
			public void actionPerformed(ActionEvent e) {
				int result=0;
				JFileChooser filec=new JFileChooser(url);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt文件(*.txt)", "txt");
			   	filec.setFileFilter(filter);//增加文件过滤器
				String defaultFileName="My Note"+".txt";//默认文件夹名字
				filec.setSelectedFile(new File(defaultFileName)); //设置默认文件名
				filec.setDialogTitle("另存为");//设置文本选择框标题
				result=filec.showSaveDialog(frame);
				if(result==JFileChooser.APPROVE_OPTION) {//表示选择了确定按钮
					File doc = filec.getCurrentDirectory();//目录
					File file = filec.getSelectedFile();//要保存的文件
					String[] st = doc.list();
					boolean falg = false;
					for(String x:st) {
						if(x.compareTo(file.getName())==0)falg = true;//目录下存在名字一样的文件
					}
					if(falg) {
						int f=JOptionPane.showConfirmDialog(null, "是否覆盖？", "My Note", JOptionPane.YES_NO_OPTION);
						if(f==JOptionPane.YES_OPTION) {
							F=file;//更改默认文件位置
							Save(F);
							New = false;//保存就不是新建的了
						}
					}
					else {
						F=filec.getSelectedFile();//更改默认文件
						Save(F);
						New = false;//保存就不是新建的了
					}
				}
			}
		});
		
		exit.addActionListener(new ActionListener(){//退出
			@Override
			public void actionPerformed(ActionEvent e) {
				saveFont();//保存字体格式
				if(change) {
					if(New) {
						int res=JOptionPane.showConfirmDialog(frame, "是否退出？", "My Note", JOptionPane.YES_NO_OPTION);
						if(res==JOptionPane.YES_OPTION) {
							int f=JOptionPane.showConfirmDialog(frame, "是否保存到：" + url + "\\无标题.txt", 
																								"My Note", JOptionPane.YES_NO_OPTION);
							if(f==JOptionPane.YES_OPTION)Save(new File(url+"\\无标题.txt"));
							System.exit(0);
						}
					} else {
						int res=JOptionPane.showConfirmDialog(frame, "是否退出？", "My Note", JOptionPane.YES_NO_OPTION);
						if(res==JOptionPane.YES_OPTION) {
							int f=JOptionPane.showConfirmDialog(frame, "是否保存到："+F.getPath(), "My Note", JOptionPane.YES_NO_OPTION);
							if(f==JOptionPane.YES_OPTION)Save(F);
							System.exit(0);
						}
					}
				}
				else {
					System.exit(0);
				}
			}
		});
		
		recall.addActionListener(new ActionListener() {//撤销
			@Override
			public void actionPerformed(ActionEvent e) {
				if(undom.canUndo()) {//判断是否能撤销
					undom.undo();//撤销操作
				}
			}
		});
		
		repet.addActionListener(new ActionListener() {//重复
			@Override
			public void actionPerformed(ActionEvent e) {
				if(undom.canRedo()) {//判断是否能恢复
					undom.redo();//恢复操作
				}
			}
		});
		
		cut.addActionListener(new ActionListener() {//剪切
			@Override
			public void actionPerformed(ActionEvent e) {
				area.cut();
			}
		});
		
		scut.addActionListener(new ActionListener() {//剪切
			@Override
			public void actionPerformed(ActionEvent e) {
				area.cut();
			}
		});
		
		copy.addActionListener(new ActionListener() {//复制
			@Override
			public void actionPerformed(ActionEvent e) {
				area.copy();
			}
		});
		
		scopy.addActionListener(new ActionListener() {//复制
			@Override
			public void actionPerformed(ActionEvent e) {
				area.copy();
			}
		});
		
		paste.addActionListener(new ActionListener() {//粘贴
			@Override
			public void actionPerformed(ActionEvent e) {
				area.paste();
			}
		});
		
		spaste.addActionListener(new ActionListener() {//粘贴
			@Override
			public void actionPerformed(ActionEvent e) {
				area.paste();
			}
		});
		
		delete.addActionListener(new ActionListener() {//删除
			@Override
			public void actionPerformed(ActionEvent e) {
				area.replaceSelection("");
			}
		});
		
		sdelete.addActionListener(new ActionListener() {//删除
			@Override
			public void actionPerformed(ActionEvent e) {
				area.replaceSelection("");
			}
		});
		
		allsel.addActionListener(new ActionListener() {//全选
			@Override
			public void actionPerformed(ActionEvent e) {
				area.selectAll();
			}
		});
		
		sallsel.addActionListener(new ActionListener() {//全选
			@Override
			public void actionPerformed(ActionEvent e) {
				area.selectAll();
			}
		});
		
		datetime.addActionListener(new ActionListener() {//日期
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy 年 MM 月 dd 日  HH 时 mm 分 ss 秒 ");//设置时间格式
				Date date = new Date(System.currentTimeMillis());//获取时间
				String data = formatter.format(date);//得到时间String形式
				JOptionPane.showMessageDialog(frame,data, "当前时间", JOptionPane.INFORMATION_MESSAGE);//提示对话框
			}
		});
		
		wrap.addActionListener(new ActionListener() {//自动换行
			@Override
			public void actionPerformed(ActionEvent e) {
				if(wrap.getState())area.setLineWrap(true);
				else area.setLineWrap(false);
			}
		});
		
		font.addActionListener(new ActionListener() {//字体
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame fm = new JFrame("字体");
				
				String t1[] = new String[] {"宋体","楷体","隶书","微软雅黑","黑体","仿宋","华文彩云"};
				String t2[] = new String[] {"常规","倾斜","加粗","加粗倾斜"};
				String t3[] = new String[] {"初号","小初","一号","小一","二号","小二","三号","小三","四号","小四","五号","小五","六号","小六"};
				JComboBox<String> j1 = new JComboBox<String>(t1);
				JComboBox<String> j2 = new JComboBox<String>(t2);
				JComboBox<String> j3 = new JComboBox<String>(t3);
				
				j3.setSelectedIndex(5);//设置显示项
				
				JPanel pan1 = new JPanel();
				JPanel pan2 = new JPanel();
				JPanel pan3 = new JPanel();
				
				JLabel lab = new JLabel("我的文本文档");
				
				JButton ensure = new JButton("确定");
				JButton cancel = new JButton("取消");
				
				pan1.setLayout(new GridLayout(2,3));
				pan1.add(new JLabel("字体："));
				pan1.add(new JLabel("字形："));
				pan1.add(new JLabel("大小："));
				pan1.add(j1);
				pan1.add(j2);
				pan1.add(j3);
				
				pan2.add(new JLabel("示例："));
				pan2.add(lab);
				
				pan3.setLayout(new GridLayout(2,4));
				pan3.add(new JLabel());//空东西
				pan3.add(new JLabel());//空东西
				pan3.add(new JLabel());//空东西
				pan3.add(new JLabel());//空东西
				pan3.add(new JLabel());//空东西
				pan3.add(new JLabel());//空东西
				pan3.add(ensure);
				pan3.add(cancel);
			  	
				lab.setBorder(BorderFactory.createBevelBorder(1));//设置样例边框
				lab.setFont(new Font("宋体",Font.PLAIN,18));//设置默认字体
				
				j1.addActionListener(new ActionListener() {//第一个下拉框
					@Override
					public void actionPerformed(ActionEvent e) {
						lab.setFont(new Font((String)j1.getSelectedItem(),lab.getFont().getStyle(),lab.getFont().getSize()));
					}
				});
				
				j2.addActionListener(new ActionListener() {//第二个下拉框
					@Override
					public void actionPerformed(ActionEvent e) {
						String type = (String)j2.getSelectedItem();
						if(type.compareTo("常规") == 0)
							lab.setFont(new Font(lab.getFont().getFamily(),Font.PLAIN,lab.getFont().getSize()));
						if(type.compareTo("加粗") == 0)
							lab.setFont(new Font(lab.getFont().getFamily(),Font.BOLD,lab.getFont().getSize()));
						if(type.compareTo("倾斜") == 0)
							lab.setFont(new Font(lab.getFont().getFamily(),Font.ITALIC,lab.getFont().getSize()));
						if(type.compareTo("加粗倾斜") == 0)
							lab.setFont(new Font(lab.getFont().getFamily(),Font.BOLD+Font.ITALIC,lab.getFont().getSize()));
						
					}
				});
				
				j3.addActionListener(new ActionListener() {//第三个下拉框
					@Override
					public void actionPerformed(ActionEvent e) {
						String type = (String)j3.getSelectedItem();
						int size = 0;
						if(type.compareTo("初号") == 0)size=36;
						if(type.compareTo("小初") == 0)size=30;
						if(type.compareTo("一号") == 0)size=26;
						if(type.compareTo("小一") == 0)size=24;
						if(type.compareTo("二号") == 0)size=22;
						if(type.compareTo("小二") == 0)size=18;
						if(type.compareTo("三号") == 0)size=17;
						if(type.compareTo("小三") == 0)size=16;
						if(type.compareTo("四号") == 0)size=15;
						if(type.compareTo("小四") == 0)size=14;
						if(type.compareTo("五号") == 0)size=13;
						if(type.compareTo("小五") == 0)size=12;
						if(type.compareTo("六号") == 0)size=11;
						if(type.compareTo("小六") == 0)size=10;
						lab.setFont(new Font(lab.getFont().getFamily(),lab.getFont().getStyle(),size));//设置字号
					}
				});
				
				ensure.addActionListener(new ActionListener() {//确认按钮
					@Override
					public void actionPerformed(ActionEvent e) {
						area.setFont(lab.getFont());
						fm.dispose();//关闭字体对话框
					}
				});
				
				cancel.addActionListener(new ActionListener() {//取消按钮
					@Override
					public void actionPerformed(ActionEvent e) {
						fm.dispose();//关闭字体对话框
					}
				});
				
				fm.setLayout(new GridLayout(3,1));
				fm.add(pan1);
				fm.add(pan2);
				fm.add(pan3);
				fm.setLocation(500,150);
				fm.setSize(300,200);
				fm.setVisible(true);
				fm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		bebig.addActionListener(new ActionListener() {//放大
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font = area.getFont();
				if(font.getSize() < 1000) {
					area.setFont(new Font(font.getFamily(),font.getStyle(),font.getSize()+1));//控制大小不超限
					if(area.getFont().getSize() > 10)besmall.setEnabled(true);
					if(font.getSize() == 999)bebig.setEnabled(false);
				}
				else bebig.setEnabled(false);//按钮不可用
			}
		});
		
		besmall.addActionListener(new ActionListener() {//缩小
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font = area.getFont();
				if(font.getSize()>10) {
					area.setFont(new Font(font.getFamily(),font.getStyle(),font.getSize()-1));//控制大小不超限
					if(area.getFont().getSize() < 1000)bebig.setEnabled(true);
					if(font.getSize() == 11)besmall.setEnabled(false);
				}
				else besmall.setEnabled(false);//按钮不可用
			}
		});
		
		bedefu.addActionListener(new ActionListener() {//恢复默认缩放
			@Override
			public void actionPerformed(ActionEvent e) {
				area.setFont(new Font(area.getFont().getFamily(),area.getFont().getStyle(),18));
				bebig.setEnabled(true);//按钮可用
				besmall.setEnabled(true);
			}
		});
		
		about.addActionListener(new ActionListener() {//关于
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("My Note");
				JLabel lab = new JLabel("My Note So Simple !");
				lab.setHorizontalAlignment(JLabel.CENTER);
				frame.add(lab,BorderLayout.CENTER);
				frame.setLocation(350,150);
				frame.setSize(400,100);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		Read(F);//打开时读取默认文件
		
		area.getDocument().addDocumentListener(new DocumentListener() {//触发了表示作了改动
			@Override
			public void removeUpdate(DocumentEvent e) {
				change = true;
				if(frame.getTitle().compareTo("无标题")==0 || frame.getTitle().compareTo("*无标题")==0)frame.setTitle("*无标题");
				else frame.setTitle("*"+F.getName());//未保存状态
				recall.setEnabled(true);//动了就是可以撤销的
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				change = true;
				if(frame.getTitle().compareTo("无标题")==0 || frame.getTitle().compareTo("*无标题")==0)frame.setTitle("*无标题");
				else frame.setTitle("*"+F.getName());//未保存状态
				recall.setEnabled(true);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				change = true;
				if(frame.getTitle().compareTo("无标题")==0 || frame.getTitle().compareTo("*无标题")==0)frame.setTitle("*无标题");
				else frame.setTitle("*"+F.getName());//未保存状态
				recall.setEnabled(true);
			}
		});
		
		pmenu.add(scut);//鼠标右击菜单
		pmenu.add(scopy);
		pmenu.add(spaste);
		pmenu.add(sdelete);
		pmenu.addSeparator();
		pmenu.add(sallsel);
		area.add(pmenu);
		
		area.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//鼠标单击事件
				if(e.isMetaDown())//鼠标右击
					pmenu.show(area,e.getX(),e.getY());
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {//关闭窗口
			@Override
			public void windowClosing(WindowEvent e) {
				saveFont();//保存字体格式
				if(change) {
					if(New) {
						int res=JOptionPane.showConfirmDialog(frame, "是否退出？", "My Note", JOptionPane.YES_NO_OPTION);
						if(res==JOptionPane.YES_OPTION) {
							int f=JOptionPane.showConfirmDialog(frame, "是否保存到：" + url + "\\无标题.txt", 
																								"My Note", JOptionPane.YES_NO_OPTION);
							if(f==JOptionPane.YES_OPTION)Save(new File(url+"\\无标题.txt"));
							System.exit(0);
						}
					} else {
						int res=JOptionPane.showConfirmDialog(frame, "是否退出？", "My Note", JOptionPane.YES_NO_OPTION);
						if(res==JOptionPane.YES_OPTION) {
							int f=JOptionPane.showConfirmDialog(frame, "是否保存到："+F.getPath(), "My Note", JOptionPane.YES_NO_OPTION);
							if(f==JOptionPane.YES_OPTION)Save(F);
							System.exit(0);
						}
					}
				}
				else {
					System.exit(0);
				}
			}
		});
		
		
		MouseWheelListener wheel = center.getMouseWheelListeners()[0];//获取滚动事件
		center.removeMouseWheelListener(wheel);//移除滚动事件
		center.addMouseWheelListener(new MouseAdapter() {//添加新的滚动事件
			@Override
		    public void mouseWheelMoved(MouseWheelEvent e){ 
				if(e.isControlDown()){//当ctrl键被按下，滚动为放大缩小 
				Font font = area.getFont(); //获取当前文本域字体
		        if(e.getWheelRotation()<0 && font.getSize() < 1000){//如果滚动条向前就放大文本
		        	area.setFont(new Font(font.getFamily(),font.getStyle(),font.getSize()+1)); 
		        	if(area.getFont().getSize() > 10)besmall.setEnabled(true);//按钮可以使用
		        	if(area.getFont().getSize() >= 1000)bebig.setEnabled(false);
		        }else if(e.getWheelRotation()>0 && font.getSize() > 10){//滚动条向后就缩小文本 
		        	area.setFont(new Font(font.getFamily(),font.getStyle(),font.getSize()-1)); 
		        	if(area.getFont().getSize() < 1000)bebig.setEnabled(true);
		        	if(area.getFont().getSize() <= 10)besmall.setEnabled(false);
		        } 
		      } else {
		    	  center.addMouseWheelListener(wheel);
		    	  wheel.mouseWheelMoved(e);//触发滚动事件
		    	  center.removeMouseWheelListener(wheel);
		      }
		    } 
		});
		
		frame.setJMenuBar(bar);//将菜单组加入容器
		frame.add(center,BorderLayout.CENTER);
		frame.setLocation(300,100);
		frame.setSize(1000,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//设置关闭效果减半，仅触发WindowListener中的windowClosing方法
	}
	public void Read(File f) {//读到文本区域
		if(!f.exists()) {
			JOptionPane.showMessageDialog(frame, "文件不存在", "My Note", JOptionPane.ERROR_MESSAGE);//提示信息错误对话框
		}else {
			try {
				FileInputStream in = new FileInputStream(f);
				int len = (int )f.length()*2;
				byte bt[] = new byte[len];
				int l = in.read(bt);
				area.setText("");//清空
				area.setText(new String(bt,0,l));
				change = false;//读取肯定作了插入操作会触发事件
				undom = new UndoManager();//每次读取后就添加一个新的撤销监听
				area.getDocument().addUndoableEditListener(undom);//读取之后再添加撤销,监听文本是否能撤销或者重复
				frame.setTitle(F.getName());
				in.close();
			} catch (IOException e) {}
		}
	}
	public void Save(File f) {//保存到文件
		frame.setTitle(F.getName());//设置窗口状态
		try {
			FileOutputStream out = new FileOutputStream(f);
			byte[] bt = area.getText().getBytes();
			change = false;
			out.write(bt);
			out.close();
		} catch (IOException e) {}
	}
	public void saveFont() {//保存字体格式
		FileOutputStream file = null ;
		try {//保存字体格式
			file = new FileOutputStream("C:\\Users\\86173\\Desktop\\java程序\\我的记事本\\font.txt");
			DataOutputStream filedata = new DataOutputStream(file);
			filedata.writeUTF(area.getFont().getFamily());
			file.write(area.getFont().getStyle());
			file.write(area.getFont().getSize());
			file.close();
			filedata.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void readFont() {//读取系统字体
		FileInputStream file = null ;
		try {//读取字体格式
			file = new FileInputStream("C:\\Users\\86173\\Desktop\\java程序\\我的记事本\\font.txt");
			DataInputStream filedata = new DataInputStream(file);
			String f1 = filedata.readUTF();
			int f2 = filedata.read();
			int f3 = filedata.read();
			area.setFont(new Font(f1,f2,f3));
			file.close();
			filedata.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Note().iniv();
	}
}
