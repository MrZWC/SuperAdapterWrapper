package com.yineng.smack;

import org.jivesoftware.smack.packet.Element;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smackx.mam.element.MamElements;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName ReqIQ
 * User: zuoweichen
 * Date: 2021/6/17 10:28
 * Description: 描述
 */
public class ReqIQ extends IQ {
    private String paramsJson;

    private String nameSpace;

    private int action = -1;


    public ReqIQ() {
        //super(QUERY_ELEMENT, MamElements.NAMESPACE);
        super("req", "com:yineng:chathistory");
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {
        List<Element> elements = new ArrayList<>();
        elements.add(new MyIQExtension(this.paramsJson, this.nameSpace, this.action));
        xml.append(elements);
        return xml;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setParamsJson(String JsonString) {
        this.paramsJson = JsonString;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
