
import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;

public class Help {
	
	public Help() {
		JFrame help = new JFrame();
		help.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		help.setTitle("Help");
		help.setLocation(300, 300);
		help.setVisible(true);
		help.setSize(510, 409);
		help.getContentPane().setLayout(null);
		
		JTextArea assist = new JTextArea();
		assist.setEditable(false);
		// LINE BELOW DISPLAYS A MESSAGE ON THE GUI
		assist.setText("To use this application it is very simple: \r\n1) To sign in enter your username and password.\r\r\n\tIf you do not have a account, select 'Register' on the first screen.\r\r\n2) Register allows you to choose a username and password:\r\r\n\tIf the username is taken, you will be notified and would need to change it.\r\r\n3) To join the group room, select the 'Group Chat' button on options:\r\r\n\ta) This will allow you to talk to multiple users.\r\r\n4) Private chat will allow you to talk to one other online user on options.\r\r\n5) To sign out, simply select the 'Log out' button.\r\r\n6) To view online users, it is on the group chat room.\r\r\n7) In the group room you need to set a display name in the given appropriate area and then press join.\r\r\n8) To send a message on the group chat, enter the room, select the send button after entering your message in the approptiate area given.");
		assist.setBounds(10, 46, 471, 277);
		assist.setLineWrap(true);
		help.getContentPane().add(assist);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				help.dispose(); // CLOSES GUI
			}
		});
		btnOk.setBounds(194, 334, 89, 23);
		help.getContentPane().add(btnOk);
		
		JLabel label = new JLabel("Help:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(10, 21, 93, 24);
		help.getContentPane().add(label);
	}
}
