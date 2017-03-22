import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMoveMsg implements Msg{
	int msgType = Msg.TANK_MOVE_MSG;
	TankClientFrame tc = null;
	int id,x,y;
	Dir dir;
	
	public TankMoveMsg(int id, Dir dir,int x,int y) {
		this.id = id;
		this.dir = dir;
		this.x = x;
		this.y = y;
	}
	public TankMoveMsg(TankClientFrame tc) {
		this.tc= tc;
	}
	@Override
	public void parse(DataInputStream dis) {
		try {
			if(dis == null){
				return;
			}
			int id = dis.readInt();
			if(tc.myTank.id == id){
				return;
			}
			int x = dis.readInt();
			int y= dis.readInt();
			Dir dir = Dir.values()[dis.readInt()];//根据下标取出枚举类型的值
			boolean exist = false;
			for(int i = 0; i<tc.tanks.size();i++){
				Tank t= tc.tanks.get(i);
				if (t.id == id) {
					t.x = x;
					t.y = y;
					t.dir = dir;
					exist = true;
					System.out.println("收到其他坦克移动信息");
					break;
					
				}
			}
System.out.println("MOve  id:" + id + "-x:" + x + "-y:" + y + "-dir:" + dir);
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
			dos.writeInt(id);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
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
