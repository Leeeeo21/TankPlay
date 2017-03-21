import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMsg {

	Tank tank = null;
	public TankMsg(Tank tank) {
		this.tank = tank;
	}
	
	public TankMsg() {
		
	}
	

	public void sent(DatagramSocket ds,String IP,int udpPort){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(tank.id);
			dos.writeInt(tank.x);
			dos.writeInt(tank.y);
			dos.writeInt(tank.dir.ordinal());
			dos.writeBoolean(tank.isLive());
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
			int id = dis.readInt();
			int x = dis.readInt();
			int y= dis.readInt();
			Dir dir = Dir.values()[dis.readInt()];//根据下标取出枚举类型的值
			boolean Live = dis.readBoolean();
			boolean friend = dis.readBoolean();
			System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" + dir + "-good:" + friend+"Live:"+Live);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}


}
