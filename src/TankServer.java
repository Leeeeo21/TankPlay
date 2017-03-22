import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;






public class TankServer {
	
	public static final int TCP_PORT = 8888;
	public static final int UDP_PORT = 6666;
	private int id = 100;
	List<Client> clients = new ArrayList<Client>();
	
	public static void main(String[] args) {
		new TankServer().ServerStart();
		
	}
	
	public void ServerStart(){		

		new Thread(new UDPThread()).start();
		
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(TCP_PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while (true) {
			Socket s = null;
			try {
				s = ss.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				int udpPort = dis.readInt();
				Client c = new Client(s.getInetAddress().getHostAddress(), udpPort);
				clients.add(c);
System.out.println("A client was Connect,Position in clients "+clients.size());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeInt(id++);
				System.out.println("A client Connected!Add:" + s.getInetAddress() + ":" + s.getPort()+":"+udpPort);
			} catch (IOException e) {
				try {
					ss.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally {
				if(s!=null){
					try {
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	}
	}

	
	private class Client{
		String IP;
		int udpport;
		public Client(String iP, int udpport) {
			this.IP = iP;
			this.udpport = udpport;
		}

	}
	
	private class UDPThread implements Runnable{

		byte[] buf = new byte[1024];
		@Override
		public void run() {
			DatagramSocket ds = null;
			
			try {
				ds = new DatagramSocket(UDP_PORT);
			} catch (SocketException e) {
				e.printStackTrace();
			}
System.out.println("Server is Started at Port: "+UDP_PORT);
			while(ds != null){
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
System.out.println("a Pack has been received!");
					for(int i = 0;i < clients.size();i++){
						Client c = clients.get(i);
						dp.setSocketAddress(new InetSocketAddress(c.IP, c.udpport));
					
System.out.println("a Pack has been Sent to"+c.IP+"   "+c.udpport);				
						ds.send(dp);
System.out.println("a Pack has been Sent to"+i);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}	
	}

}
