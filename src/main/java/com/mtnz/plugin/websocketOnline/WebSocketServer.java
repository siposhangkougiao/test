package com.mtnz.plugin.websocketOnline;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint("/websocket/{username}/{roomNumber}")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    private String username;
    private String phone;
    public String roomNumber;

    public String getPhone() {
        return phone;
    }
    private String getUsername() {
        return username;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();


    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("username")String username,@PathParam("roomNumber")String roomNumber)throws Exception{
        this.username  =new String(username.getBytes("iso-8859-1"),"utf-8");
        this.session = session;
        this.phone = phone;
        this.roomNumber=roomNumber;
        webSocketSet.add(this);//加入set中
        //MailMap.addUser(username,this);
        addOnlineCount();      //在线数加1
        System.out.println("在线人数加1-----------------》"+getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println(username+"来自客户端的消息:" + message);
        /*String[] str= message.split(",");
        String temp="";
        if("1".equals(str[0])){
            temp="订单";
        }else if("2".equals(str[0])){
            temp="建议";
        }else{
            temp="vip申請";
        }*/
        //群发消息
        for(WebSocketServer item: webSocketSet){
            try {
                if(item.getRoomNumber().equals(roomNumber)){
                    item.sendMessage(username+"发送了"+message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static void main(String[] args) {
            //try {
                 //Map<String, WebSocketServer> userMap = MailMap.getAll();
                /*for(Map.Entry<String,WebSocketServer> e :userMap.entrySet()){
                    System.out.println("key:"+e.getKey()+",value:"+e.getValue());
                }
                WebSocketServer ws=MailMap.getConnWebSocketByName("系统管理员");
                if(null!=ws){
                    ws.sendMessage("各客服请注意，现有客戶，提交了,请尽快处理！");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
    }

    public static void send(String message)throws IOException{
        for(WebSocketServer webSocketServer: WebSocketServer.getWebSocketSet()){

            webSocketServer.sendMessage(webSocketServer.getUsername()+message);
        }
    }

}
