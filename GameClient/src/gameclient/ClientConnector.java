package gameclient;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;import java.net.InetSocketAddress;import java.net.Socket;import java.nio.ByteBuffer;import java.util.ArrayList;import java.util.List;import javax.swing.Timer;public class ClientConnector {    private List<Socket> Sockets = new ArrayList<Socket>();    private int BasePort = 8000;    private String HostIP = "147.147.53.26";    private ClientGameBoard ClientGameBoard;    private Visualizer Visualizer;    private Timer ClientInputTimer = new Timer(100, new ActionListener() {        public void actionPerformed(ActionEvent e) {            Listen();            //System.out.println("Attempting to perform action");            //if (Sockets.size() > 0) {            //    ProcessBuffer(ReadBuffer(getSockets().get(0), 256));            //}        }    });    public ClientConnector() {        super();        try {            Socket s0 = new Socket();            this.addSocket(s0);            s0.connect(new InetSocketAddress(HostIP, BasePort));            //ClientInputTimer.start();            ProcessBuffer(ReadBuffer(getSockets().get(0), 256));        } catch (IOException ioe) {            ioe.printStackTrace();        }    }    public byte[] ReadBuffer(Socket s, int size) {        System.out.println("Attempting to read buffer");        int BufferSize = size;        byte[] out = null;        //byte[] out = new byte[BufferSize];        try {            InputStreamReader isr = new InputStreamReader(s.getInputStream());            BufferedReader br = new BufferedReader(isr);            int lim = 0;            while (br.ready() == false) {                //wait                int x = 0;            }            byte[] o = new byte[BufferSize];            while (br.ready() && lim < BufferSize) {                byte b = (byte) br.read();                o[lim] = b;                lim += 1;            }            out = o;            //byte[] ba = this.ReadBuffer(s0, 256);            //System.out.println("Received: " + ba);            for (int x = 0; x < BufferSize; x++) {                byte b = out[x];            }        } catch (IOException ioe) {            ioe.printStackTrace();        } catch (Exception e) {            e.printStackTrace();        }        return out;    }    public void ProcessBuffer(byte[] bufr) {        if (bufr.equals(null) == false) {            System.out.println("Attempting to process buffer");            byte[] ClassID = new byte[2];            for (int x = 0; x < 2; x++) {                ClassID[x] = bufr[x];            }            List<byte[]> ClassLib = new ArrayList<byte[]>();            ClassLib.add(new byte[] { 0x4D, 0x50 });            ClassLib.add(new byte[] { 0x4E, 0x47 });            //Search Library of ClassIDs            for (int c = 0; c < ClassLib.size(); c++) {                boolean mtc1 = true;                System.out.println("Searching ClassLib");                //Break down ID into 2 bytes                for (int x = 0; x < 2; x++) {                    byte b = ClassLib.get(c)[x];                    if (b == (ClassID[x])) {                    } else {                        mtc1 = false;                        //System.out.println("match is false: c = " + c + ", x = " + x);                    }                }                if (mtc1 == true) {                    ByteBuffer b0 = ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x00, ClassID[0] });                    ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x00, ClassID[1] });                    System.out.println("Matched incoming buffer: c = " + c + ", " + b0.getInt() + ", " + b1.getInt());                    if (c == 0) {                        //Migrate port request                        System.out.println("Received port migration request");                        PortMigrationRequest pmr0 = new PortMigrationRequest(this);                        pmr0.Deserialize(bufr);                        Listen();                        //ClientInputTimer.start();                    }                    if (c == 1) {                        System.out.println("Received new game request");                        this.ClientGameBoard = new ClientGameBoard(this);                        this.Visualizer = new Visualizer(this);                    }                }            }        } else {            System.out.println("null buffer received");        }    }    public void Listen() {        ProcessBuffer(this.ReadBuffer(this.getSockets().get(0), 256));    }    public List<Socket> getSockets() {        return this.Sockets;    }    public void addSocket(Socket s) {        this.Sockets.add(s);    }    public void removeSocket(Socket s) {        this.Sockets.remove(s);    }    public static void main(String[] args) {        new ClientConnector();    }    public ClientGameBoard getClientGameBoard() {        return this.ClientGameBoard;    }    public void setClientGameBoard(ClientGameBoard b) {        this.ClientGameBoard = b;    }    public Visualizer getVisualizer() {        return this.Visualizer;    }}