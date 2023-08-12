import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
public class Server  implements ActionListener {
	JTextField text1;
	static JPanel p2;
	static Box vertical = Box.createVerticalBox();
	static JFrame f = new JFrame();
	static DataOutputStream dout;
	
	Server(){
		f.setLayout(null);
		JPanel p1=new JPanel();
		p1.setBackground(new Color(7, 94, 84));
		p1.setBounds(0, 0, 500, 70);
		p1.setLayout(null);
		f.add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(10, 20, 25, 25);
		p1.add(back);
		
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(50, 10, 50, 50);
		p1.add(profile);
		
		JLabel name = new JLabel("Server");
		name.setBounds(110, 10, 100, 50);
		name.setForeground(Color.white);
		name.setFont(new Font("Sans_Serif",Font.BOLD , 20));
		p1.add(name);
		
		
		
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel video = new JLabel(i9);
		video.setBounds(330, 25, 30, 30);
		p1.add(video);
		
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel phone = new JLabel(i12);
		phone.setBounds(390, 25, 30, 30);
		p1.add(phone);
		
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel three_icon = new JLabel(i15);
		three_icon.setBounds(450, 25, 10, 25);
		p1.add(three_icon);
		
		 p2 = new JPanel();
		p2.setBounds(5, 75, 490, 650);
		f.add(p2);
		
		text1 = new JTextField();
		text1.setBounds(5, 655, 350, 40);
		text1.setFont(new Font("Sans_Serif", Font.PLAIN, 20));
		f.add(text1);
		
		JButton send = new JButton("Send");
		send.setBounds(360, 655, 120, 40);
		send.setBackground(new Color(7, 94, 84));
		send.setForeground(Color.white);
		send.setFont(new Font("Sans_Serif", Font.PLAIN, 20));
		send.addActionListener(this);
		f.add(send);
		
		f.setSize(500,750);
		f.setLocation(200, 150);
		f.getContentPane().setBackground(Color.WHITE);
		f.setUndecorated(true);
		f.setVisible(true);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		try {
			String out = text1.getText();
			
			JPanel p3 = formatLabel(out);
			
			p2.setLayout(new BorderLayout());
			
			JPanel right = new JPanel(new BorderLayout());
			right.add(p3, BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(17));
			p2.add(vertical, BorderLayout.PAGE_START);
			
			dout.writeUTF(out);
			
			text1.setText("");
			
			f.repaint();
			f.invalidate();
			f.validate(); 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static JPanel formatLabel(String out) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style='width: 150px '>" + out + "</p></html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 18));
		output.setBackground(new Color(37, 211, 102));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15, 15, 15, 50));
		
		panel.add(output);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		
		return panel;
	}
	
	public static void main(String[] args) {
		new Server();
		
		try {
			try (ServerSocket skt = new ServerSocket(6000)) {
				while(true) {
					Socket s = skt.accept();
					DataInputStream din = new DataInputStream(s.getInputStream());
					dout = new DataOutputStream(s.getOutputStream());
					
					while(true) {
						String msg = din.readUTF();
						JPanel panel = formatLabel(msg);
						
						JPanel left = new JPanel(new BorderLayout());
						left.add(panel, BorderLayout.LINE_START);
						vertical.add(left);
						f.validate();
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
		
	}


