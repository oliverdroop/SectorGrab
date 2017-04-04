package gameserver;import java.awt.image.BufferedImage;import java.util.ArrayList;import java.util.List;public class ServerGameBoard {    private List<ServerNode> ServerNodes = new ArrayList<ServerNode>();    private BufferedImage Background;    private ServerConnector ServerConnector;    public ServerGameBoard(ServerConnector sc) {        super();        this.ServerConnector = sc;        InitializeBackground();        CreateServerNodes();        System.out.println("ClientGameBoard created");    }    public void CreateServerNodes() {        for (int x = 0; x < 16; x++) {            for (int y = 0; y < 16; y++) {                ServerNode n = new ServerNode(this, x, y, 0);            }        }    }    public void InitializeBackground() {        this.Background = new BufferedImage(256, 256, 2);        int rgb = 0xFF000000;        for (int x = 0; x < 256; x++) {            for (int y = 0; y < 256; y++) {                if (x < this.getBackground().getWidth() && y < this.getBackground().getHeight())                    this.Background.setRGB(x, y, rgb);            }        }    }    public List<ServerNode> getNodes() {        return this.ServerNodes;    }    public void addNode(ServerNode n) {        this.ServerNodes.add(n);    }    public void removeNode(ServerNode n) {        this.ServerNodes.remove(n);    }    public ServerConnector getServerConnector() {        return this.ServerConnector;    }    public BufferedImage getBackground() {        return this.Background;    }}