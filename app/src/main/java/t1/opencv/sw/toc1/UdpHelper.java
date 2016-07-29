package t1.opencv.sw.toc1;

import android.net.wifi.WifiManager;
import android.util.Log;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by cn09876 on 16/7/1.
 */

public class UdpHelper implements Runnable
{
    public  Boolean IsThreadDisable = false;//指示监听线程是否终止
    private static WifiManager.MulticastLock lock;
    InetAddress mInetAddress;
    public UdpHelper(WifiManager manager)
    {
        this.lock= manager.createMulticastLock("UDPwifi");
    }

    public void StartListen()
    {
        // UDP服务器监听的端口
        Integer port = 20421;
        // 接收的字节大小，客户端发送的数据不能超过这个大小
        byte[] message = new byte[100];
        try
        {
            // 建立Socket连接
            DatagramSocket datagramSocket = new DatagramSocket(port);
            datagramSocket.setBroadcast(true);
            DatagramPacket datagramPacket = new DatagramPacket(message, message.length);
            try {
                while (!IsThreadDisable)
                {
                    // 准备接收数据
                    Log.e("UDP Demo", "准备接受");
                    this.lock.acquire();
                    datagramSocket.receive(datagramPacket);
                    String strMsg = new String(datagramPacket.getData()).trim();
                    int PeerPort=datagramPacket.getPort();
                    String PeerIP=datagramPacket.getAddress().getHostAddress().toString();
                    Log.e("UDP Demo", PeerIP + ":"+PeerPort +":"+ strMsg);
                    this.lock.release();
                }
            }
            catch (Exception e)
            {
                Log.e("UDP Demo", "udp.error " + e.getMessage());
            }
        }
        catch (Exception e1)
        {
            Log.e("UDP Demo", "udp.error1 " + e1.getMessage());
        }
    }

    public static void send(final String message)
    {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int server_port = 20420;
                        Log.e("UDP Demo", "UDP发送数据:"+message);
                        DatagramSocket s = null;
                        try {
                            s = new DatagramSocket();
                        } catch (Exception e) {
                            Log.e("UDP Demo","初始化udp socker error");
                            return;
                        }

                        InetAddress local = null;
                        try {
                            local = InetAddress.getByName("255.255.255.255");
                        } catch (UnknownHostException e) {
                            Log.e("UDP Demo","取得广播地址出错");
                            return;
                        }
                        int msg_length = message.length();
                        byte[] messageByte = message.getBytes();
                        DatagramPacket p = new DatagramPacket(messageByte, msg_length, local,
                                server_port);
                        try {
                            s.send(p);
                            s.close();
                        } catch (Exception e)
                        {
                            Log.e("UDP Demo","发送udp出错");
                        }

                    }
                }
        ).start();
    }

    @Override
    public void run() {
        StartListen();
    }
}