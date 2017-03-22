import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;





public class NetClient {
	private static int UDP_PORT_START = 9002;
	private int udpport;
	TankClientFrame tc;
	DatagramSocket ds = null;
	
	public NetClient(TankClientFrame tc){
		this.tc = tc;
		udpport = UDP_PORT_START++;
		try {
			ds = new DatagramSocket(udpport);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(String IP , int port) {
		Socket s = null;
		try {
			s = new Socket(IP, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpport);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			tc.myTank.id = id;
System.out.println("connected");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (s != null) {
				try {
					s.close();
					s = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		Msg msg = new TankNewMsg(tc.myTank);
		sent(msg);
		
		new Thread(new UDPRecvThread()).start();
	}
	
	
	
	void sent(Msg msg) {
		msg.sent(ds, "127.0.0.1", TankServer.UDP_PORT);
		
	}



	private class UDPRecvThread implements Runnable{

			byte[] buf = new byte[1024];
			@Override
			public void run() {
				while(ds != null){
					DatagramPacket dp = new DatagramPacket(buf, buf.length);
					try {
System.out.println("Start Receive!"+udpport+"    ");
						ds.receive(dp);
						parse(dp);
System.out.println("a Pack has been received from server!");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}
			private void parse(DatagramPacket dp) {
				ByteArrayInputStream bais = new ByteArrayInputStream(buf);
				DataInputStream dis = new DataInputStream(bais);
				int msgType = 1;
				try {
					msgType = dis.readInt();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Msg msg =  null;
				switch (msgType) {
				case Msg.TANK_NEW_MSG:
					msg = new TankNewMsg(tc);
					msg.parse(dis);
					break;
				case Msg.TANK_MOVE_MSG:
					msg = new TankMoveMsg(tc);
					msg.parse(dis);
					break;
				default:
					break;
				}
			}
	}
	
}
