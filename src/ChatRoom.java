
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatRoom extends JFrame implements ActionListener { // EXTENDS JFRAME FOR GUI & IMPLEMENTS ACTIONLISTENER FOR BUTTON ACTIONS

	JLabel name, message, chatLog;
	static JTextField txtMessageArea, displayName;
	JButton joinRoom, send, leave, whoIsIn, btnHelp;
	JTextArea txtStatusArea;
	boolean connected; // TRUE OR FALSE FOR CONNECTION STATUS (WHEN CONNECTED TO CLIENT)
	Client client; // CLIENT OBJECT
	int defaultPort;
	String defaultHost;

	ChatRoom() {
		super("Chat Room"); // NAME OF GUI
		setDefaultCloseOperation(EXIT_ON_CLOSE); // CLOSES GUI WHEN 'X' IS PRESSED
		setSize(813, 397);
		setLocation(350, 50);
		getContentPane().setLayout(null);
		
		name = new JLabel("Display Name:");
		name.setFont(new Font("Tahoma", Font.PLAIN, 13));
		name.setBounds(10, 101, 93, 24);
		getContentPane().add(name);
		
		message = new JLabel("Message:");
		message.setFont(new Font("Tahoma", Font.PLAIN, 13));
		message.setBounds(10, 133, 74, 24);
		getContentPane().add(message);
		
		displayName = new JTextField("Anonymous");
		displayName.setBounds(100, 104, 114, 20);
		displayName.removeActionListener(this);
		getContentPane().add(displayName);
		
		txtMessageArea = new JTextField();
		txtMessageArea.setBounds(74, 136, 154, 78);
		getContentPane().add(txtMessageArea);
		
		txtStatusArea = new JTextArea();
		txtStatusArea.setBounds(238, 37, 531, 310);
		txtStatusArea.setEditable(false); // CHAT LOG NOT EDITABLE BY USER
		getContentPane().add(txtStatusArea);
		
		chatLog = new JLabel("Chat Log");
		chatLog.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chatLog.setBounds(457, 11, 74, 24);
		getContentPane().add(chatLog);
		
		joinRoom = new JButton("Join");
		joinRoom.setBounds(10, 225, 89, 23);
		getContentPane().add(joinRoom);
		joinRoom.addActionListener(this); // BUTTON LISTENER
		
		leave = new JButton("Leave");
		leave.setBounds(125, 259, 89, 23);
		getContentPane().add(leave);
		leave.addActionListener(this);
		
		send = new JButton("Send");
		send.setBounds(125, 225, 89, 23);
		getContentPane().add(send);
		send.addActionListener(this);
		
		whoIsIn = new JButton("Users");
		whoIsIn.setBounds(10, 259, 89, 23);
		getContentPane().add(whoIsIn);
		
		btnHelp = new JButton("Help");
		btnHelp.setBounds(71, 293, 89, 23);
		btnHelp.addActionListener(new ActionListener() { // METHOD FOR WHEN 'HELP' BUTTON IS PRESSED
			public void actionPerformed(ActionEvent arg0) {
				new Help();
			}
		});
		getContentPane().add(btnHelp);
		whoIsIn.addActionListener(this);
		
        setVisible(true);
	}

	public void append(String str) { // EDITS THE CHAT LOG AREA
		txtStatusArea.append(str);
		txtStatusArea.setCaretPosition(txtStatusArea.getText().length() - 1);
	}

	public void actionPerformed(ActionEvent e) { // HAS ALL BUTTON EVENTS (EXCEPT 'HELP' BUTTON WHICH IS ABOVE)
		Object o = e.getSource(); // OBJECTS LISTENS FOR WHICH BUTTON IS PRESSED
		if(o == joinRoom) {
			String displayName = txtMessageArea.getText().trim(); // RETRIEVES DISPLAY NAME 

			client = new Client(Client.getHost(), Client.getPort(), displayName, this); // STARTS CLIENT THREAD (WHICH CONNECTS TO SERVER)
			if(!client.start()) { // IF CONNECTION TO CLIENT FAILS BREAK THE LOOP
				return;
			}

			txtMessageArea.setText(""); // BLANK MESSAGE AREA
			connected = true; // CONNECTION TO CLIENT IS TRUE
			
			joinRoom.setEnabled(false); // BUTTON NO LONGER ACTIVE/CLICKABLE
		}
		
		else if(o == whoIsIn) { // RUNS IF THE BUTTON PRESSED IS 'WHOSIN'
			client.sendMessage(new MessageHandler(MessageHandler.WHOISIN, ""));	// SENDS MESSAGE TO CLIENT SIDE METHOD 'SENDMESSAGE' TO GET LIST OF ONLINE USERS			
			return;
		}
		
		else if(o == send) { // IF BUTTON PRESSED IS 'SEND'		
			if(connected) { // IF CONNECTION TO CLIENT IS ALIVE
				
				if(txtMessageArea.getText().isEmpty()) { // IF MESSAGE FIELD IS BLANK THIS RUNS
					JOptionPane.showMessageDialog(null, "No message to send", "Error", JOptionPane.ERROR_MESSAGE); // POP UP SAYING NO MESSAGE TO SEND
				}
				else { // IF THERE IS A MESSAGE TO SEND
					displayName.setEnabled(false); // CAN NO LONGER EDIT DISPLAY NAME
					client.sendMessage(new MessageHandler(MessageHandler.MESSAGE, displayName.getText() + ": " + txtMessageArea.getText())); // SENDS MESSAGE TO CLIENT SIDE METHOD 'SENDMESSAGE'				
					txtMessageArea.setText(""); // MESSAGE FIELD IS SET TO BLANK
					return;
				}
			}
			else { // IF THE CONNECTION TO CLIENT IS NOT ALIVE
				JOptionPane.showMessageDialog(null, "Please enter a display name", "Error", JOptionPane.ERROR_MESSAGE); // DISPLAYS WHEN CONNECTION IS NOT ALIVE SINCE DISPLAY NAME HAS NOT BEEN SET
			}
		} 
		
		else if(o == leave) { // RUNS IF BUTTON PRESSED IS 'LEAVE'
			dispose(); // CLOSES GUI
			new Options(); // LOADS OPTIONS GUI
		}
	}
}

