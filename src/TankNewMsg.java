import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankNewMsg implements Msg{
	int msgType = TANK_NEW_MSG;
	Tank tank = null;
	TankClientFrame tc = null;
	
	public TankNewMsg(Tank tank) {
		this.tank = tank;
	}
	
	public TankNewMsg() {
		
	}
	public TankNewMsg(TankClientFrame tc) {
		this.tc = tc;
	}

	public void parse(DataInputStream dis) {

		try {
			if(dis == null){
				return;
			}
			int id = dis.readInt();
			if(tc.myTank.id == id){
				System.out.println("这是自己坦克的消息，不再处理"+tc.myTank.id);
				return;
			}
			System.out.println("这是其他坦克的消息，开始处理"+tc.myTank.id);
			int x = dis.readInt();
			int y= dis.readInt();
			Dir dir = Dir.values()[dis.readInt()];//根据下标取出枚举类型的值
			boolean friend = dis.readBoolean();
			boolean exist = false;
			for(int i=0; i < tc.tanks.size();i ++){
				if(id == tc.tanks.get(i).id){
					exist = true;
					break;
				}
				else break;
			}
			if(!exist){
				Tank t = new Tank(x, y, friend, dir, tc);
				t.id = id;
				tc.tanks.add(t);
				TankNewMsg tnMsg = new TankNewMsg(tc.myTank);
				tc.nc.sent(tnMsg);
			}
			System.out.println("新生成一辆");
System.out.println("Send   id:" + id + "-x:" + x + "-y:" + y + "-dir:" + dir + "-good:" + friend);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void sent(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
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
}
