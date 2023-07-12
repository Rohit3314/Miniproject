
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Server extends JFrame{
	
	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	// components for GUI
	
	private JLabel heading = new JLabel("ChatApp - Server"); 
	private JTextArea msgarea = new JTextArea();
	private JTextField msginputarea = new JTextField();
	private Font font = new Font("Arial", Font.PLAIN,20);

	// constructor for Server
	
	public Server()
	{
		
		try
		{
						
			server = new ServerSocket(7778);
            System.out.println("Server is ready to accept connections");
			socket = server.accept(); 
						
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        out = new PrintWriter(socket.getOutputStream());
	        
	        createGUI();
	        handleEvents();
	        Reading();
	        
		} catch(Exception e)
		
		{
			e.printStackTrace();
		}		
	}
	
	private void createGUI()
	{
	
		  this.setTitle("Server Messenger");
		  this.setSize(600,600);
		  this.setLocationRelativeTo(null);
		  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  heading.setFont(font);
          msgarea.setFont(font);
          msginputarea.setFont(font);
          
          heading.setHorizontalAlignment(SwingConstants.CENTER);
          heading.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
          
          this.setLayout(new BorderLayout());          
          this.add(heading, BorderLayout.NORTH);
          this.add(msgarea, BorderLayout.CENTER);
          this.add(msginputarea, BorderLayout.SOUTH);
          
		  this.setVisible(true);
		
	}

	private void handleEvents()
	{
		
		msginputarea.addKeyListener(new KeyListener()
				{

					@Override
					public void keyTyped(KeyEvent e) {
						
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
					
						if(e.getKeyCode() == 10)
						{
						String contents = msginputarea.getText();	
						msgarea.append("Me : "+contents+"\n");
						out.println(contents);
						out.flush();
						msginputarea.setText("");
						msginputarea.requestFocus();
						}
						
					}
			
			
				});
		
	}
	
	private void Reading()
	{
		
		Runnable r1 = () -> {
						
			try
			{
				while(true)
					
				{
					String msg = br.readLine();
					if(msg.equals("exit"))
					{
						
						JOptionPane.showMessageDialog(this,"chat finish");
						msginputarea.setEnabled(false);						
						socket.close();
						break;
						
					}
					
					msgarea.append("Client: "+msg+"\n");
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		};
             new Thread(r1).start();	
	}
	
	public static void main(String args[])
	{
		new Server();		
		
	}
	
	
}	