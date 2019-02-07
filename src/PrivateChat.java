
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;

public class PrivateChat
{
	final static int DISCONNECTED = 0;
	final static int BEGIN_CONNECT = 1;
	final static int CONNECTED = 2;
	
	public static JFrame FrameMain = null;
	public static JTextArea TxtArea = null;
	public static JTextField TxtField = null;
	public static JLabel LblArea = null;
	public static JTextField IPArea = null;
	public static JTextField PortArea = null;
	public static JButton ButtonLink = null;
	
	public static String IPHost = "Local Host";
	public static int NumPort = 2047;
	public static int StatConn = DISCONNECTED;
	public static boolean IsHost = true;
	
	public PrivateChat() {
		InitGUI();
	}
	
	private static JPanel InitOptPane()
	{
		JPanel WinPane = null;
		ActionAdapter BListener = null;
		
		JPanel OptPane = new JPanel(new GridLayout(6, 2));
		
		WinPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		WinPane.add(new JLabel("Host Name/IP: "));
		IPArea = new JTextField(13);
		
		try {
			IPArea.setText(Client.getHost() + "/" + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		IPArea.setEditable(false);
		WinPane.add(IPArea);
		OptPane.add(WinPane);
		
		ButtonGroup MiscGroup = new ButtonGroup();
		WinPane = new JPanel(new GridLayout(2, 4));
		OptPane.add(WinPane);
		
		JPanel BWinPane = new JPanel(new GridLayout(2, 4));
		BListener = new ActionAdapter()
		{
			public void ActPer(ActionEvent e)
			{
				if(e.getActionCommand().equals("Connecting..."))
				{
					ButtonLink.setEnabled(false);
					StatConn = BEGIN_CONNECT;
					IPArea.setEnabled(false);
					PortArea.setEnabled(false);
					TxtField.setText("");
					TxtField.setEnabled(false);
					LblArea.setText("Online");
					FrameMain.repaint();
				}
				
				else
				{
					ButtonLink.setEnabled(true);
					StatConn = DISCONNECTED;
					IPArea.setEnabled(true);
					PortArea.setEnabled(true);
					TxtField.setText("");
					TxtField.setEnabled(false);
					LblArea.setText("Offline");
					FrameMain.repaint();
				}
			}
			
			public void actionPerformed(ActionEvent arg0) {
				Object o = arg0.getSource();
				
				Client con = new Client(Client.getHost(), Client.getPort(), null, null);
				if(!con.start()) {
					return;
				}
				
				if(o == ButtonLink) {
					String mess = TxtField.getText();
					TxtArea.append("User: " + mess + "\n");
					TxtField.setText("");
				} 
			}
		};
		
		ButtonLink = new JButton("Send Message");
		ButtonLink.setMnemonic(KeyEvent.VK_C);
		ButtonLink.setActionCommand("SendMessage");
		ButtonLink.addActionListener(BListener);
		ButtonLink.setEnabled(true);
		
		BWinPane.add(ButtonLink);
		OptPane.add(BWinPane);
		
		return OptPane;
	}
	
	public static void InitGUI()
	{
		LblArea = new JLabel();
		LblArea.setText("Offline");
		
		JPanel OptPane = InitOptPane();
		
		JPanel TxtMsgPane = new JPanel(new BorderLayout());
		TxtArea = new JTextArea(20, 40);
		TxtArea.setLineWrap(true);
		TxtArea.setEditable(false);
		TxtArea.setForeground(Color.GRAY);
		
		JScrollPane ScrPane = new JScrollPane(TxtArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		TxtField = new JTextField();
		TxtField.setEditable(true);
		TxtField.setEnabled(true);
		TxtMsgPane.add(TxtField, BorderLayout.SOUTH);
		TxtMsgPane.add(ScrPane, BorderLayout.CENTER);
		TxtMsgPane.setPreferredSize(new Dimension(300, 300));
		
		JPanel PMain = new JPanel(new BorderLayout());
		PMain.add(LblArea, BorderLayout.SOUTH);
		PMain.add(OptPane, BorderLayout.WEST);
		PMain.add(TxtMsgPane, BorderLayout.CENTER);
		
		FrameMain = new JFrame("Private Chat");
		FrameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FrameMain.setContentPane(PMain);
		FrameMain.setSize(FrameMain.getPreferredSize());
		FrameMain.setLocation(300, 300);
		FrameMain.pack();
		FrameMain.setVisible(true);
	}
}

class ActionAdapter implements ActionListener
{
	public void actionPerformed(ActionEvent e){
	}
}
