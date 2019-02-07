
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class Options {

	public Options() {
		JFrame options = new JFrame();
		options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // CLOSES GUI
		options.setTitle("Options"); // NAME OF GUI
		options.setSize(506, 291);
		options.setLocation(300, 300);
		options.getContentPane().setLayout(null);
		
		JLabel lblOptions = new JLabel("Options & Help");
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptions.setBounds(147, 11, 175, 37);
		lblOptions.setFont(new Font("Tahoma", Font.BOLD, 20));
		options.getContentPane().add(lblOptions);
		
		JButton btnPrivateChat = new JButton("Private Chat");
		btnPrivateChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.dispose(); // CLOSES GUI
				new PrivateChat(); // OPENS GUI
			}
		});
		btnPrivateChat.setBounds(93, 217, 103, 23);
		options.getContentPane().add(btnPrivateChat);
		
		JButton btnGroupChat = new JButton("Group Chat");
		btnGroupChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.dispose(); // CLOSES GUI
				new ChatRoom(); // OPENS GUI
			}
		});
		btnGroupChat.setBounds(208, 217, 103, 23);
		options.getContentPane().add(btnGroupChat);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.dispose();
				Object[] options = {"Goodbye"};
				JOptionPane.showOptionDialog(null, "Goodbye!", "Logging out", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]); // LOADS WHEN USER SELECTS LOG OUT
				new LoginScreen();
			}
		});
		btnLogOut.setBounds(321, 217, 90, 23);
		options.getContentPane().add(btnLogOut);
		
		JTextArea txtrToUseThis = new JTextArea();
		txtrToUseThis.setText("To use this application it is very simple:\r\n1) To join the group room, select the 'Group Chat' button:\r\n\ta) This will allow you to talk to multiple users.\r\n2) Private chat will allow you to talk to one other online user.\r\n3) To sign out, simply select the 'Log out' button.\r\n4) To view online users, it is on the group chat room.");
		txtrToUseThis.setEditable(false);
		txtrToUseThis.setBounds(10, 75, 470, 131);
		options.getContentPane().add(txtrToUseThis);
		
		JLabel lblHelp = new JLabel("Help:");
		lblHelp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHelp.setBounds(20, 50, 93, 24);
		options.getContentPane().add(lblHelp);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

		options.setVisible(true);
	}
}

