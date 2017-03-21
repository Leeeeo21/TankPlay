import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TankServer {
	
	public static final int TCP_PORT = 8888;
	private int id = 100;
	List<Client> clients = new ArrayList<Client>();
	public void start(){
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
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeInt(id++);
				s.close();
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
	public static void main(String[] args) {
		new TankServer().start();
	}
	
	public class Client{
		String IP;
		int udpport;
		public Client(String iP, int udpport) {
			this.IP = iP;
			this.udpport = udpport;
		}

	}


}
