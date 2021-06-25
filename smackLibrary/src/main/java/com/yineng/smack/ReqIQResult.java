package com.yineng.smack;


import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smackx.mam.element.MamElements;

public class ReqIQResult extends IQ {
    private String nameSpace;

    private String resp;

    private int code = -1;

    private String id;

    private String action;

    /**
     * 消息回执时间
     */
    private String sendTime;

    /**
     * 组织机构变更通知的类型
     */
    private String type;

    protected ReqIQResult() {
        super(QUERY_ELEMENT, MamElements.NAMESPACE);
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {
        xml.append("<req xmlns=\"");
        xml.append(nameSpace);
        xml.append("\">");
        if (resp != null && !resp.equals("")) {
            xml.append("<resp>");
            xml.append(resp);
            xml.append("</resp>");
        }
        if (code != -1) {
            xml.append("<code>");
            xml.append(code + "");
            xml.append("</code>");
        }
        if (action != null && !action.equals("")) {
            xml.append("<action>");
            xml.append(action);
            xml.append("</action>");
        }
        if (id != null && !id.equals("")) {
            xml.append("<id>");
            xml.append(id);
            xml.append("</id>");
        }
        //消息回执时间
        if (sendTime != null && !sendTime.equals("")) {
            xml.append("<sendTime>");
            xml.append(sendTime);
            xml.append("</sendTime>");
        }
        xml.append("</req>");
        return xml;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getTypeStr() {
        return type;
    }

    public void setTypeStr(String typeStr) {
        this.type = typeStr;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }
}
