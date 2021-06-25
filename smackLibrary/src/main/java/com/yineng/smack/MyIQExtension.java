package com.yineng.smack;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.XmlEnvironment;

/**
 * ClassName MyIQExtension
 * User: zuoweichen
 * Date: 2021/6/17 11:45
 * Description: 描述
 */
public class MyIQExtension implements ExtensionElement {

    private String paramsJson;

    private String nameSpace;

    private int action = -1;

    public MyIQExtension(String paramsJson, String nameSpace, int action) {
        this.paramsJson = paramsJson;
        this.nameSpace = nameSpace;
        this.action = action;
    }

    @Override
    public String getNamespace() {
        return nameSpace;
    }

    @Override
    public String getElementName() {
        return "req";
    }

    @Override
    public CharSequence toXML(XmlEnvironment xmlEnvironment) {
        StringBuilder buf = new StringBuilder();
        buf.append(" >");
        if (action > 0) {
            buf.append("<action>");
            buf.append(action);
            buf.append("</action>");
        }
        buf.append("<params>");
        if (paramsJson != null) {
            buf.append(paramsJson);
        } else {
            buf.append("{}");
        }
        buf.append("</params>");


        return buf.toString();
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public void setParamsJson(String JsonString) {
        this.paramsJson = JsonString;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

}
