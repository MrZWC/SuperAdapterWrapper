package com.yineng.smack;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.Stanza;

/**
 * ClassName MyStanzaListener
 * User: zuoweichen
 * Date: 2021/6/18 9:30
 * Description: 描述
 */
public class MyStanzaListener<T extends Stanza> implements StanzaListener {
    @Override
    public void processStanza(Stanza packet) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {

    }
}
