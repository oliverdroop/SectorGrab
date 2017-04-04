package gameserver;import java.io.IOException;import java.net.InetSocketAddress;import java.net.Socket;public class PortMigrationRequest {    private int Port = 8001;    private ServerConnector ServerConnector;    public PortMigrationRequest(ServerConnector sc) {        super();        this.ServerConnector = sc;    }    public byte[] Serialize() {        //Random rnd = new Random();        byte[] out = new byte[256];        out[0] = (byte) 0x4D;        out[1] = (byte) 0x50;        byte[] p = new byte[4];        p[0] = 0x00;        p[1] = 0x00;        p[2] = 0x1F;        p[3] = 0x41;        //ByteBuffer bb = ByteBuffer.wrap(p);        for (int x = 0; x < 4; x++) {            out[x + 2] = p[x];        }        System.out.println("Serialized port migration request");        return out;    }    public void AwaitSecondary() {        System.out.println("Awaiting secondary");        Socket s0 = new Socket();        try {            //while (s0.isConnected() == false) {            System.out.println("Port is " + Port);            s0.connect(new InetSocketAddress(ServerConnector.getSockets().get(0).getInetAddress().getHostName(), Port));            this.ServerConnector.removeSocket(this.ServerConnector.getSockets().get(0));            this.ServerConnector.addSocket(s0);            System.out.println("Connected to secondary");            //}        } catch (IOException ioe) {            ioe.printStackTrace();        }    }}