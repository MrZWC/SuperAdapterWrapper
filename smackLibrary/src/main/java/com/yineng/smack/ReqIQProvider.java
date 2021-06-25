package com.yineng.smack;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.XmlEnvironment;
import org.jivesoftware.smack.parsing.SmackParsingException;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smack.xml.XmlPullParser;
import org.jivesoftware.smack.xml.XmlPullParserException;

import java.io.IOException;

/**
 * ClassName ReqIQProvider
 * User: zuoweichen
 * Date: 2021/6/16 15:19
 * Description: 用于解析自定义 IQ 数据包的抽象类。每个 IQProvider 都必须在 ProviderManager 类中注册才能使用。这个抽象类的每个实现都必须有一个公共的、无参数的构造函数。
 */
public class ReqIQProvider extends IQProvider {
    @Override
    public IQ parse(XmlPullParser parser, int initialDepth, XmlEnvironment xmlEnvironment) throws XmlPullParserException, IOException, SmackParsingException {
        ReqIQResult iqProvider = new ReqIQResult();
        iqProvider.setType(IQ.Type.result);
        try {
            XmlPullParser.Event event = parser.getEventType();
            while (event != XmlPullParser.Event.END_ELEMENT) {
                switch (event) {
                    case START_DOCUMENT:
                        break;
                    case START_ELEMENT:
                        // 解析IQ xml 节点
                        String name = parser.getName();
//					<iq type="result" id="tV95mrmA" to="xiognshihui@m.com" from="admin@m.com">
//					  <notice xmlns="com:yineng:notice">
//					    <type>orgUpdate</type>
//					  </notice>
//					</iq>
                        if ("req".equals(name)) {
                            iqProvider.setNameSpace(parser.getNamespace());//命名空间
                        } else if ("notice".equals(name)) {
                            iqProvider.setNameSpace(parser.getNamespace());
                        }

                        if ("resp".equals(name)) {
                            iqProvider.setResp(parser.nextText());//

                        }
                        if ("action".equals(name)) {
                            iqProvider.setAction(parser.nextText());//
                        }
                        if ("id".equals(name)) {
                            iqProvider.setId(parser.nextText());//message received id
                        }
                        if ("code".equals(name)) {
                            iqProvider.setCode(Integer.parseInt(parser.nextText()));//
                        }
                        if ("sendTime".equals(name)) {
                            iqProvider.setSendTime(parser.nextText());//收到消息回执时间
                        }
                        if ("type".equals(name)) {
                            iqProvider.setTypeStr(parser.nextText());
                        }
                        break;
                }
                event = parser.next();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return iqProvider;
    }
}
