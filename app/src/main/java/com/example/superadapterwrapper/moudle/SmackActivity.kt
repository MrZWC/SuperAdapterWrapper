package com.example.superadapterwrapper.moudle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.R
import com.example.superadapterwrapper.moudle.SmackActivity
import com.idonans.lang.util.ToastUtil
import com.socks.library.KLog
import com.yineng.smack.MyStanzaListener
import com.yineng.smack.ReqIQ
import com.yineng.smack.ReqIQProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jivesoftware.smack.*
import org.jivesoftware.smack.SmackException.*
import org.jivesoftware.smack.XMPPException.XMPPErrorException
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smack.provider.ProviderManager
import org.jivesoftware.smack.sasl.provided.SASLPlainMechanism
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.muc.MultiUserChatManager
import org.jivesoftware.smackx.ping.PingManager
import org.jxmpp.jid.impl.JidCreate
import org.jxmpp.stringprep.XmppStringprepException
import timber.log.Timber
import java.io.IOException
import java.net.InetAddress

//MessageRetractionManager 消息撤销管理器
//ChatStateManager 聊天状态管理器
//DeliveryReceiptManager 消息回执管理器
//MultiUserChatManager 多用户聊天室管理器
class SmackActivity : AppCompatActivity() {
    lateinit var mConnection: XMPPTCPConnection
    private var login_bt: TextView? = null
    private var send_bt: TextView? = null
    var multiuserchatmanager_btn: TextView? = null
    private var mSendStanzaBtn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smack)
        login_bt = findViewById(R.id.login_bt)
        send_bt = findViewById(R.id.send_bt)
        multiuserchatmanager_btn = findViewById(R.id.multiuserchatmanager_btn)
        mSendStanzaBtn = findViewById(R.id.sendStanza_btn)
        login_bt?.setOnClickListener(View.OnClickListener { login() })
        send_bt?.setOnClickListener {
            try {
                send()
                it.visibility = View.VISIBLE;
            } catch (e: NotConnectedException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: XmppStringprepException) {
                e.printStackTrace()
            }
        }
        multiuserchatmanager_btn?.setOnClickListener(View.OnClickListener { multiUserChatManagerTest() })
        mSendStanzaBtn?.setOnClickListener { sendStanza() }
        initXmpp()
    }

    private fun initXmpp() {
        Observable.just("")
            .map<Any> {
                try {
                    val configBuilder = XMPPTCPConnectionConfiguration.builder()
                        .setUsernameAndPassword("a7ff7421-d453-4a37-ba8a-233c95cf5c65", "password")
                        .setResource("Msg_Phone")
                        .setXmppDomain("messenger.yineng.com.cn")
                        .setHostAddress(InetAddress.getByName("10.6.6.152"))
                        .setPort(6000)
                        .enableDefaultDebugger()
                        .setCompressionEnabled(false)
                        .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                        .setSendPresence(true)
                    mConnection = XMPPTCPConnection(configBuilder.build())
                    SASLAuthentication.blacklistSASLMechanism(SASLPlainMechanism.DIGESTMD5)
                    configureProviders()
                    //重连管理器 自动重连处理
                    ReconnectionManager.setDefaultReconnectionPolicy(ReconnectionManager.ReconnectionPolicy.RANDOM_INCREASING_DELAY)
                    val reconnectionManager = ReconnectionManager.getInstanceFor(mConnection)
                    reconnectionManager.enableAutomaticReconnection()
                    mConnection.addConnectionListener(object : ConnectionListener {
                        override fun connecting(connection: XMPPConnection) {
                            //连接正在连接过程中的通知。
                            KLog.i(TAG, "连接正在连接过程中的通知")
                        }

                        override fun connected(connection: XMPPConnection) {
                            //连接已成功连接到远程端点的通知
                            KLog.i(TAG, "连接已成功连接到远程端点的通知")
                            if (connection.isConnected) {
                                ToastUtil.show("连接成功")
                            }
                        }

                        override fun authenticated(connection: XMPPConnection, resumed: Boolean) {
                            //连接已通过身份验证的通知。
                            KLog.i(TAG, "连接已通过身份验证的通知")
                        }

                        override fun connectionClosed() {
                            //通知连接正常关闭
                            KLog.i(TAG, "通知连接正常关闭")
                        }

                        override fun connectionClosedOnError(e: Exception) {
                            KLog.i(TAG, "连接因异常而关闭的通知。")
                            Timber.e(e)
                        }
                    })
                    // Connect to the server
                    if ((mConnection?.isConnected())!!) {
                        mConnection?.connect()
                    }
                    login()
                    // Disconnect from the server
                    //connection.disconnect();
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: XMPPException) {
                    e.printStackTrace()
                } catch (e: SmackException) {
                    e.printStackTrace()
                }
                ""
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ KLog.i(TAG, "失败") }) { KLog.i(TAG, "失败") }
    }

    private fun login() {
        // Log into the server
        //mConnection.addStanzaListener();
        try {
            if (mConnection.isConnected) {
                mConnection.login()
            }
        } catch (e: XMPPException) {
            Timber.e(e)
        } catch (e: SmackException) {
            Timber.e(e)
        } catch (e: IOException) {
            Timber.e(e)
        } catch (e: InterruptedException) {
            Timber.e(e)
        }
    }

    private fun configureProviders() {
        // add ReqIQProvider
        val reqIQProvider = ReqIQProvider()
        ProviderManager.addIQProvider("req", "com:yineng:clientinit", reqIQProvider)
        ProviderManager.addIQProvider("req", "com:yineng:chathistory", reqIQProvider)
        val pingManager = PingManager.getInstanceFor(mConnection)
        PingManager.setDefaultPingInterval(600)
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
        mConnection!!.addStanzaListener({ packet -> KLog.i(TAG, packet.toString()) }, null)
    }

    private var num = 0
    private fun sendStanza() {
        val iq = ReqIQ()
        try {
            iq.setParamsJson("")
            //iq.setTo(JidCreate.bareFrom("admin@" + mConnection.getXMPPServiceDomain().toString()));
            iq.to = JidCreate.bareFrom("admin@messenger.yineng.com.cn")
            iq.setAction(1)
            //e72be6a8-8878-4f24-9862-30856285c385@messenger.yineng.com.cn/Msg_Phone
            //com:yineng:chathistory
            iq.nameSpace = "com:yineng:chathistory"
            //iq.setFrom("e72be6a8-8878-4f24-9862-30856285c385@messenger.yineng.com.cn/Msg_Phone");
            //iq.setFrom(JidCreate.bareFrom("a7ff7421-d453-4a37-ba8a-233c95cf5c65@messenger.yineng.com.cn/Msg_Phone"));
            /*  mConnection?.addOneTimeSyncCallback(StanzaListener {
                  @Throws(
                      NotConnectedException::class,
                      InterruptedException::class,
                      NotLoggedInException::class
                  )
                  fun processStanza(packet: Stanza) {
                      KLog.i(TAG, packet.toString())
                  }
              }, null)*/
            mConnection?.sendStanza(iq)
            mConnection?.addOneTimeSyncCallback(StanzaListener {
                KLog.i(TAG, it.toString())
            }, null);
        } catch (e: NotConnectedException) {
            Timber.e(e)
        } catch (e: InterruptedException) {
            Timber.e(e)
        } catch (e: XmppStringprepException) {
            Timber.e(e)
        }
    }

    @Throws(
        NotConnectedException::class,
        InterruptedException::class,
        XmppStringprepException::class
    )
    private fun send() {
        val chatManager = ChatManager.getInstanceFor(mConnection)
        chatManager.addIncomingListener { from, message, chat ->
            KLog.i(TAG, "New message from " + from + ": " + message.body)
            /* try {
                        chat.send(message.getBody());
                    } catch (SmackException.NotConnectedException | InterruptedException e) {
                        e.printStackTrace();
                    }*/
        }
        chatManager.addOutgoingListener { to, messageBuilder, chat ->
            KLog.i(
                TAG,
                "newOutgoingMessage=" + messageBuilder.body
            )
        }
        val jid =
            JidCreate.entityBareFrom("935e3366-ae16-4da1-935c-e0f6adfeea20@messenger.yineng.com.cn")
        val chat = chatManager.chatWith(jid)
        /*  Message stanza = mConnection
                .getStanzaFactory()
                .buildMessageStanza()
                .ofType(Message.Type.chat)
                .setBody("测试")
                .build();*/chat.send(
            """{
    "content":"111111111${num++}",
    "customAvatars":[

    ],
    "files":[

    ],
    "images":[

    ],
    "msgType":1,
    "resource":"Msg_Phone",
    "sendName":"曾桂花(app)",
    "style":{
        "bold":false,
        "fontFamily":"宋体",
        "fontSize":12,
        "foreground":"#FF000000",
        "italic":false,
        "underline":false
    },
    "video":{
        "client":0,
        "status":0
    },
    "voice":{
        "sentSuccess":false,
        "time":0
    }
}"""
        )
    }

    private fun multiUserChatManagerTest() {
        val manager = MultiUserChatManager.getInstanceFor(mConnection)
        try {
            val joinedRooms = manager.getJoinedRooms(
                mConnection!!.user
            )
            KLog.i(joinedRooms)
        } catch (e: NoResponseException) {
            e.printStackTrace()
        } catch (e: XMPPErrorException) {
            e.printStackTrace()
        } catch (e: NotConnectedException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
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

    override fun onStart() {
        super.onStart()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mConnection != null) {
            mConnection!!.disconnect()
        }
    }

    companion object {
        private const val TAG = "SmackActivityIM"
        fun start(context: Context) {
            val intent = Intent(context, SmackActivity::class.java)
            context.startActivity(intent)
        }
    }
}