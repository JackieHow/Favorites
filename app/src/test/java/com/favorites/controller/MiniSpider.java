package com.favorites.controller;

import org.apache.commons.io.FileUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shilei on 2017/4/11.
 */
public class MiniSpider {

    //    //爬取《交大新闻网》的所有信息，并将信息打印到控制台！
    public static void main(String[] args) throws IOException {
        int mainPage = 3;  //判断主页标识符
        Document doc = null;
        String url = "https://www.zhihu.com/";
        String cookie = "SINAGLOBAL=2140864281428.8025.1580542522893; _s_tentry=www.google.com; Apache=9432090533637.88.1585900119911; ULV=1585900119931:27:1:4:9432090533637.88.1585900119911:1585667819687; Ugrow-G0=5c7144e56a57a456abed1d1511ad79e8; YF-V5-G0=b1b8bc404aec69668ba2d36ae39dd980; login_sid_t=ccbfc06a4acea9f330f005f9bf0a4a60; cross_origin_proto=SSL; UOR=,,www.alexa.cn; WBtopGlobal_register_version=3d5b6de7399dfbdb; SCF=AheyYDhqwNJNTLPVEyw2kvzWOl3ggf9q6UUM396juHmxM9A8Kv2BPa2BLtyr3ISrnlEgcX63BxoGg_w79pDsA-8.; SUB=_2A25zjCGCDeRhGeVJ6lcW9y7FyDmIHXVQ-BRKrDV8PUNbmtAfLUXRkW9NT-z9riN5DlWMcDNXmmX0_ALfMAlqPpXz; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5JagOMQ1XHSoy9uWhCkCGx5JpX5K2hUgL.FoeNeK-NS054e0-2dJLoI7phdcxLPXp4dc_aPfnt; SUHB=0M2FwEsleEl7vK; ALF=1586596933; SSOLoginState=1585992155; un=18356707895; YF-Page-G0=4358a4493c1ebf8ed493ef9c46f04cae|1585992176|1585992155; wb_view_log_3715770935=1920*10801.25; webim_unReadCount=%7B%22time%22%3A1585992158036%2C%22dm_pub_total%22%3A0%2C%22chat_group_client%22%3A0%2C%22chat_group_notice%22%3A0%2C%22allcountNum%22%3A3%2C%22msgbox%22%3A0%7D";
        String a = "SINAGLOBAL=2140864281428.8025.1580542522893; _s_tentry=www.google.com; Apache=9432090533637.88.1585900119911; ULV=1585900119931:27:1:4:9432090533637.88.1585900119911:1585667819687; Ugrow-G0=5c7144e56a57a456abed1d1511ad79e8; YF-V5-G0=b1b8bc404aec69668ba2d36ae39dd980; login_sid_t=ccbfc06a4acea9f330f005f9bf0a4a60; cross_origin_proto=SSL; UOR=,,www.alexa.cn; WBtopGlobal_register_version=3d5b6de7399dfbdb; SCF=AheyYDhqwNJNTLPVEyw2kvzWOl3ggf9q6UUM396juHmxM9A8Kv2BPa2BLtyr3ISrnlEgcX63BxoGg_w79pDsA-8.; SUB=_2A25zjCGCDeRhGeVJ6lcW9y7FyDmIHXVQ-BRKrDV8PUNbmtAfLUXRkW9NT-z9riN5DlWMcDNXmmX0_ALfMAlqPpXz; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5JagOMQ1XHSoy9uWhCkCGx5JpX5K2hUgL.FoeNeK-NS054e0-2dJLoI7phdcxLPXp4dc_aPfnt; SUHB=0M2FwEsleEl7vK; ALF=1586596933; SSOLoginState=1585992155; un=18356707895; YF-Page-G0=4358a4493c1ebf8ed493ef9c46f04cae|1585992176|1585992155; wb_view_log_3715770935=1920*10801.25; webim_unReadCount=%7B%22time%22%3A1585992158036%2C%22dm_pub_total%22%3A0%2C%22chat_group_client%22%3A0%2C%22chat_group_notice%22%3A0%2C%22allcountNum%22%3A3%2C%22msgbox%22%3A0%7D";
        String[] b = a.split(";");
        Map map = new HashMap();

        for (int i = 0; i < b.length; i++) {
            map.put(b[i].split("=")[0], b[i].split("=")[1]);
        }

        doc = Jsoup.connect(url).cookies(map).get();
        Whitelist whitelist = new Whitelist();
        String content = null;
        URL url1 = new URL(url);
        String A = "a";
        String P = "p";
        String LI = "li";
        String SPAN = "span";
        String hostUrl = url1.getHost().toLowerCase();
        List<String> tempList = new ArrayList<>();

        if (hostUrl.contains("tomall")) {                       //天猫
            tempList.add("时尚");
        } else if (hostUrl.contains("jd")) {                    //京东
            tempList.add("时尚");
        } else if (hostUrl.contains("weibo")) {                 //微博
            tempList.add("娱乐");
        } else if (hostUrl.contains("baidu")) {                 //百度
            tempList = cleanList(doc, A);
        } else if (hostUrl.contains("csdn")) {                  //CSDN
            tempList = cleanList(doc, P);
        } else if (hostUrl.contains("qq") || hostUrl.contains("sina")) {        //QQ和新浪
            if (url.split("/").length < mainPage) { //首页
                tempList = cleanList(doc, A);
                tempList.addAll(cleanList(doc, P));
            } else {
                tempList.addAll(cleanList(doc, P));
            }
        } else if (hostUrl.contains("sohu")) {                  //搜狐网
            if (url.split("/").length < mainPage) {
                tempList = cleanList(doc, LI);
            } else {
                tempList = cleanList(doc, P);
            }
        } else if (hostUrl.contains("xinhuanet")) {             //新华网
            if (url.split("/").length < mainPage) {
                tempList = cleanList(doc, LI);
            } else {
                tempList = cleanList(doc, P);
            }

        } else if (hostUrl.contains("huanqiu") || hostUrl.contains("china.com.cn")) {
            tempList = cleanList(doc, P);
        } else if (hostUrl.contains("zhihu")) {
            tempList = cleanList(doc, SPAN);
        } else {
            tempList = cleanList(doc, P);
            if (tempList.size() < 20) {
                tempList.addAll(cleanList(doc, A));
                tempList.addAll(cleanList(doc, LI));

            }
        }

        System.out.println(tempList.toString());
        TestFile(tempList);
    }

    /**
     * doc按照元素过滤
     *
     * @param doc
     * @param eleType
     * @return 过滤掉html的正常文字list
     */
    public static List<String> cleanList(Document doc, String eleType) {
        String temp = doc.body().getElementsByTag(eleType).html();
        List<String> tempList = new ArrayList<>();
        for (String i : temp.split("\n")) {
            Document tempDoc = Jsoup.parse(i);
            i = StringFilter(tempDoc.text());
            if (i.length() <= 5) {
                continue;
            } else {
                tempList.add(i);
            }
        }

        return tempList;
    }

    /**
     * 正则符号过滤器
     *
     * @param str
     * @return
     */
    public static String StringFilter(String str) { // tab不同的空格【　】
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘”“’\\-℃　]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        str = m.replaceAll("").trim();
        return str;
    }

    /**
     * 写入本地文件
     *
     * @param list
     * @throws IOException
     */
    public static void TestFile(List<String> list) throws IOException {
        String file = "temp.txt";
        File name = new File(file);
        FileUtils.writeByteArrayToFile(name, list.toString().getBytes(), false);
    }

}
