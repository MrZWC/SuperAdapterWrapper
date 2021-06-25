package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.superadapterwrapper.R;
import com.idonans.lang.util.ToastUtil;
import com.socks.library.KLog;
import com.yineng.smack.MyStanzaListener;
import com.yineng.smack.ReqIQ;
import com.yineng.smack.ReqIQProvider;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.MessageBuilder;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.sasl.provided.SASLPlainMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.ping.PingManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

//MessageRetractionManager 消息撤销管理器
//ChatStateManager 聊天状态管理器
//DeliveryReceiptManager 消息回执管理器
//MultiUserChatManager 多用户聊天室管理器
public class SmackActivity extends AppCompatActivity {

    private AbstractXMPPConnection mConnection;
    private static final String TAG = "SmackActivityIM";
    private TextView login_bt;
    private TextView send_bt;
    private TextView multiuserchatmanager_btn;
    private TextView mSendStanzaBtn;

    public static void start(Context context) {
        Intent intent = new Intent(context, SmackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smack);
        login_bt = findViewById(R.id.login_bt);
        send_bt = findViewById(R.id.send_bt);
        multiuserchatmanager_btn = findViewById(R.id.multiuserchatmanager_btn);
        mSendStanzaBtn = findViewById(R.id.sendStanza_btn);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    send();
                } catch (SmackException.NotConnectedException | InterruptedException | XmppStringprepException e) {
                    e.printStackTrace();
                }
            }
        });
        multiuserchatmanager_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiUserChatManagerTest();
            }
        });
        mSendStanzaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStanza();
            }
        });
        initXmpp();
    }

    private void initXmpp() {
        Observable.just("")
                .map(new Function<String, Object>() {
                    @Override
                    public Object apply(String s) throws Throwable {

                        try {
                            XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder()
                                    .setUsernameAndPassword("a7ff7421-d453-4a37-ba8a-233c95cf5c65", "password")
                                    .setResource("Msg_Phone")
                                    .setXmppDomain("messenger.yineng.com.cn")
                                    .setHostAddress(InetAddress.getByName("10.6.6.152"))
                                    .setPort(6000)
                                    .enableDefaultDebugger()
                                    .setCompressionEnabled(false)
                                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                                    .setSendPresence(true);
                            mConnection = new XMPPTCPConnection(configBuilder.build());
                            SASLAuthentication.blacklistSASLMechanism(SASLPlainMechanism.DIGESTMD5);
                            configureProviders();
                            //重连管理器 自动重连处理
                            ReconnectionManager.setDefaultReconnectionPolicy(ReconnectionManager.ReconnectionPolicy.RANDOM_INCREASING_DELAY);
                            ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(mConnection);
                            reconnectionManager.enableAutomaticReconnection();
                            mConnection.addConnectionListener(new ConnectionListener() {
                                @Override
                                public void connecting(XMPPConnection connection) {
                                    //连接正在连接过程中的通知。

                                    KLog.i(TAG, "连接正在连接过程中的通知");
                                }

                                @Override
                                public void connected(XMPPConnection connection) {
                                    //连接已成功连接到远程端点的通知
                                    KLog.i(TAG, "连接已成功连接到远程端点的通知");
                                    if (connection.isConnected()) {
                                        ToastUtil.show("连接成功");
                                    }
                                }

                                @Override
                                public void authenticated(XMPPConnection connection, boolean resumed) {
                                    //连接已通过身份验证的通知。
                                    KLog.i(TAG, "连接已通过身份验证的通知");
                                }

                                @Override
                                public void connectionClosed() {
                                    //通知连接正常关闭
                                    KLog.i(TAG, "通知连接正常关闭");
                                }

                                @Override
                                public void connectionClosedOnError(Exception e) {
                                    KLog.i(TAG, "连接因异常而关闭的通知。");
                                    Timber.e(e);
                                }
                            });
                            // Connect to the server
                            if (!mConnection.isConnected()) {
                                mConnection.connect();
                            }
                            login();
                            // Disconnect from the server
                            //connection.disconnect();
                        } catch (IOException | InterruptedException | XMPPException | SmackException e) {
                            e.printStackTrace();
                        }
                        return "";
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Throwable {


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {


                    }
                });

    }

    private void login() {
        // Log into the server
        //mConnection.addStanzaListener();
        try {
            if (mConnection != null && mConnection.isConnected()) {
                mConnection.login();
            }
        } catch (XMPPException | SmackException | IOException | InterruptedException e) {
            Timber.e(e);
        }
    }

    private void configureProviders() {
        // add ReqIQProvider
        ReqIQProvider reqIQProvider = new ReqIQProvider();
        ProviderManager.addIQProvider("req", "com:yineng:clientinit", reqIQProvider);
        ProviderManager.addIQProvider("req", "com:yineng:chathistory", reqIQProvider);
        PingManager pingManager = PingManager.getInstanceFor(mConnection);
        PingManager.setDefaultPingInterval(600);
        //pingManager.registerPingFailedListener();
        /*mConnection.addAsyncStanzaListener(new StanzaListener() {
            @Override
            public void processStanza(Stanza packet) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {

            }
        }, new AbstractExactJidTypeFilter() {
            @Override
            protected Jid getJidToInspect(Stanza stanza) {
                return null;
            }
        });*/
        //mConnection.addAsyncStanzaListener();
        //mConnection.addSyncStanzaListener();
        mConnection.addStanzaListener(new StanzaListener() {
            @Override
            public void processStanza(Stanza packet) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
                KLog.i(TAG, packet.toString());
            }
        }, null);
    }

    private int num = 0;

    private void sendStanza() {
        ReqIQ iq = new ReqIQ();
        try {
            iq.setParamsJson("");
            //iq.setTo(JidCreate.bareFrom("admin@" + mConnection.getXMPPServiceDomain().toString()));
            iq.setTo(JidCreate.bareFrom("admin@messenger.yineng.com.cn"));
            iq.setAction(1);
            //e72be6a8-8878-4f24-9862-30856285c385@messenger.yineng.com.cn/Msg_Phone
            //com:yineng:chathistory
            iq.setNameSpace("com:yineng:chathistory");
            //iq.setFrom("e72be6a8-8878-4f24-9862-30856285c385@messenger.yineng.com.cn/Msg_Phone");
            //iq.setFrom(JidCreate.bareFrom("a7ff7421-d453-4a37-ba8a-233c95cf5c65@messenger.yineng.com.cn/Msg_Phone"));
            mConnection.addOneTimeSyncCallback(new MyStanzaListener() {
                @Override
                public void processStanza(Stanza packet) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
                    KLog.i(TAG, packet.toString());
                }
            }, null);
            mConnection.sendStanza(iq);
        } catch (SmackException.NotConnectedException | InterruptedException | XmppStringprepException e) {
            Timber.e(e);
        }

    }

    private void send() throws SmackException.NotConnectedException, InterruptedException, XmppStringprepException {
        ChatManager chatManager = ChatManager.getInstanceFor(mConnection);
        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                KLog.i(TAG, "New message from " + from + ": " + message.getBody());
               /* try {
                    chat.send(message.getBody());
                } catch (SmackException.NotConnectedException | InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        });
        chatManager.addOutgoingListener(new OutgoingChatMessageListener() {
            @Override
            public void newOutgoingMessage(EntityBareJid to, MessageBuilder messageBuilder, Chat chat) {
                KLog.i(TAG, "newOutgoingMessage=" + messageBuilder.getBody());
            }
        });
        EntityBareJid jid = JidCreate.entityBareFrom("935e3366-ae16-4da1-935c-e0f6adfeea20@messenger.yineng.com.cn");
        Chat chat = chatManager.chatWith(jid);
      /*  Message stanza = mConnection
                .getStanzaFactory()
                .buildMessageStanza()
                .ofType(Message.Type.chat)
                .setBody("测试")
                .build();*/
        chat.send("{\n" +
                "    \"content\":\"111111111" + (num++) +
                "\",\n" +
                "    \"customAvatars\":[\n" +
                "\n" +
                "    ],\n" +
                "    \"files\":[\n" +
                "\n" +
                "    ],\n" +
                "    \"images\":[\n" +
                "\n" +
                "    ],\n" +
                "    \"msgType\":1,\n" +
                "    \"resource\":\"Msg_Phone\",\n" +
                "    \"sendName\":\"曾桂花(app)\",\n" +
                "    \"style\":{\n" +
                "        \"bold\":false,\n" +
                "        \"fontFamily\":\"宋体\",\n" +
                "        \"fontSize\":12,\n" +
                "        \"foreground\":\"#FF000000\",\n" +
                "        \"italic\":false,\n" +
                "        \"underline\":false\n" +
                "    },\n" +
                "    \"video\":{\n" +
                "        \"client\":0,\n" +
                "        \"status\":0\n" +
                "    },\n" +
                "    \"voice\":{\n" +
                "        \"sentSuccess\":false,\n" +
                "        \"time\":0\n" +
                "    }\n" +
                "}");
    }

    private void multiUserChatManagerTest() {
        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(mConnection);
        try {
            List<EntityBareJid> joinedRooms = manager.getJoinedRooms(mConnection.getUser());
            KLog.i(joinedRooms);

        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     /*   MamManager mamManager=MamManager.getInstanceFor(mConnection);
        try {
            MamManager.MamQuery mamQuery = mamManager.queryMostRecentPage(mConnection.getUser().asBareJid(), 20);
            KLog.i(mamQuery);
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (SmackException.NotLoggedInException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnection != null) {
            mConnection.disconnect();
        }
    }
}