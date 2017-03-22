import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMsg {

	Tank tank = null;
	TankClientFrame tc = null;
	
	public TankMsg(Tank tank) {
		this.tank = tank;
	}
	
	public TankMsg() {
		
	}
	public TankMsg(TankClientFrame tc) {
		this.tc = tc;
	}
	
	

	public void sent(DatagramSocket ds,String IP,int udpPort){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(tank.id);
			dos.writeInt(tank.x);
			dos.writeInt(tank.y);
			dos.writeInt(tank.dir.ordinal());
			dos.writeBoolean(tank.isFriend());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DatagramPacket dp = 
				new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,new InetSocketAddress(IP,udpPort));
		try {
			ds.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void parse(DataInputStream dis) {

		try {
			if(dis == null){
				return;
			}
			int id = dis.readInt();
			if(tc.myTank.id == id){
				System.out.println("�����Լ�̹�˵���Ϣ�����ٴ���"+tc.myTank.id);
				return;
			}
			System.out.println("��������̹�˵���Ϣ����ʼ����"+tc.myTank.id);
			int x = dis.readInt();
			int y= dis.readInt();
			Dir dir = Dir.values()[dis.readInt()];//�����±�ȡ��ö�����͵�ֵ
			boolean friend = dis.readBoolean();
			System.out.println("û�и�̹�ˣ�������һ��");
			Tank t = new Tank(x, y, friend, dir, tc);
			tc.tanks.add(t);
System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" + dir + "-good:" + friend);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


}
