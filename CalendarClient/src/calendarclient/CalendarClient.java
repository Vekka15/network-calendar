
package calendarclient;

import calendargui.CalendarPanel;
import calendargui.LoginPanel;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CalendarClient {
    static InetAddress addr;
     byte[] ipAddr;
    static int port = Integer.parseInt("1234");
    static byte[] message = new byte[15];
    static int bitsCount; //liczba zwroconych bitow
    static public DataOutputStream out;
    static public DataInputStream in;
    static JFrame frame;
    
  
    
    public static void main(String[] args) {
        
        //POŁĄCZENIE
          try
      {
         InetAddress addr = InetAddress.getByName("192.168.0.15");
         System.out.println("Connecting to " + addr + " on port "+  port);
         Socket client = new Socket(addr, port);
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         out = new DataOutputStream(outToServer);
         InputStream inFromServer = client.getInputStream();
         in = new DataInputStream(inFromServer);
      }catch(IOException c)
      {
         c.printStackTrace();
      }
        
          //GUI
       frame = new JFrame();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(1050,750);

        CardLayout cardLay = new CardLayout();
        JPanel cardPanel = new JPanel();
        LoginPanel loginPanel = new LoginPanel(out,in,cardLay,cardPanel);
        CalendarPanel calendar = new CalendarPanel();
        cardPanel.setLayout(cardLay);
        cardPanel.add(loginPanel,"1");
        cardPanel.add(calendar,"2");
        cardLay.show(cardPanel,"1");
        frame.add(cardPanel);
        
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter()
            {
                 DataOutputStream wyjscie = out; 
            public void windowClosing(WindowEvent e)
                {
                 
                try {
                    System.out.println("weszlo");
                    InetAddress addr = InetAddress.getByName("192.168.0.15");
         System.out.println("Connecting to " + addr + " on port "+  port);
         Socket client = new Socket(addr, port);
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         out = new DataOutputStream(outToServer);
                    out.write("exity".getBytes());
                } catch (IOException c) {
                   c.printStackTrace();
                }
                   
                }
            });
        
       
    }
    
    
    
    
}
