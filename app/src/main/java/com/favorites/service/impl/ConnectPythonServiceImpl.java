package com.favorites.service.impl;

import com.favorites.service.ConnectPythonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service("ConnectPythonService")
public class ConnectPythonServiceImpl implements ConnectPythonService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getResult(String str) {
        String test_0 = "2月28日下午，房天下控股、中指控股董事长莫天全携当代置业执行董事兼总裁张鹏，业之峰装饰集团董事长张钧，21世" +
                "纪不动产中国区总裁兼CEO卢航，中经联盟秘书长、优铺网创始人陈云峰联合直播发声，畅谈疫情影响下房地产行业的发展。同时，" +
                "这也是房天下控股董事长莫天全首次试水直播。 莫天全在直播中介绍称，疫情下的房地产及互联网行业迎来了发展的挑战和机遇。" +
                "1 月30号，房天下面向全行业免费开放在线直播开始直播卖房，2 月6号面向产业精英推出直播大赛；2 月26号 文旅地产界也开始" +
                "举行330多个全国优秀文旅项目亮相。 莫天全表示，过去一个月的时间里面，房天下累计在线直播卖房超过了12万场，这还不包括" +
                "二手房，观看人次累计过亿规模，近30万行业的从业人员加入了房天下的直播卖房大军。 当代置业执行董事兼董事长张鹏在直播中" +
                "表示，自疫情发生以来，当代置业在三方面做了一番功夫，首先是捐款捐物，直接去承担一个企业的责任。公司第一时间已向武汉" +
                "捐赠200万人民币，并且募集了一个千万规模的慈善基金。第二个就是， 公司也积极响应国家号召，做好多项防疫举措。尤其是95" +
                "个项目的6000位职员，与小区业主并肩作战。在房地产行业按下暂停键的同时，公司也在思考，如何通过提供更好的产品来服务社" +
                "区。 张鹏表示，长期来看，企业应建立起自己的差异化竞争力，提升产品服务配套。 21 世纪不动产中国区总裁兼CEO卢航在直播" +
                "中表示，在疫情期间，21 世纪不动产迅速推了一个21线上大学，把过去收费的课程剪出一个免费的精华版，帮大家去分析，假设" +
                "疫情过后，第一步一动起来以后，到底哪些生意可能会先复苏， 一分析特别简单，新房，第二就是租赁，第三就是一定要抓线上" +
                "的看房，VR看房线上直播，就是线上的动作。 卢航认为，近段时间，经纪人也陆续有线上成交，为什么能做到这一点？其实这考" +
                "验的是平时对线上客户的维护。各地中介门店也应在政府的指导下，规范的逐步复工。 中经联盟秘书长、优铺网创始人陈云峰认" +
                "为疫情下，大家终于注意到这次物业公司的必要性。面对疫情，更多的优秀物业在默默的付出，我们也突然意识到物业对我们非常" +
                "重要。而物业抢着在香港都轮着在上市，陈云峰认为对于物业公司是个重大的利好， 大家都突然意识到这些大的商场可以不去，但" +
                "是你离不开物业公司的保障，所以物业公司是受到了重视。 陈云峰还表示，当前市场下，商业及商业地产也面临较大的挑战，一些" +
                "对商办项目的限制其实是不利于服务业发展、以及大众创业的，应该呼吁适当放松这方面的政策。 资料显示，疫情期间，房天下积" +
                "极给出优惠，助力新房二手房市场在线成交。新房方面，房天下举办网上房交会，为开发商提供了一个很好地网上交易平台，更为" +
                "购房者提供累计超过10亿的优惠。二手房方面，房天下免费开放了几万个网上门店， 另有一个大优惠，为抗疫复产的二手房经纪公" +
                "司、经纪人推出了翻倍赠送服务产品的大力度优惠政策。给经纪人提供支持，共同对抗疫情，推动市场复苏。";
        if (str.isEmpty() || str == null) {
            str = test_0;
        }

        String[] arguments = new String[]{"python", "./static/python/load_test.py", str, str, str};
        String result = null;

        try {
            Process proc = Runtime.getRuntime().exec(arguments);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result = line.replaceAll("\\[", "").replaceAll("\\]", "")
                        .replaceAll("\\'", "");
                logger.info("执行py脚本成功,获得预测分类为" + result);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.info("执行py脚本失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> getResultList(String str) {
        List<String> resultList = new ArrayList<>();

        String test_0 = "2月28日下午，房天下控股、中指控股董事长莫天全携当代置业执行董事兼总裁张鹏，业之峰装饰集团董事长张钧，21世" +
                "纪不动产中国区总裁兼CEO卢航，中经联盟秘书长、优铺网创始人陈云峰联合直播发声，畅谈疫情影响下房地产行业的发展。同时，" +
                "这也是房天下控股董事长莫天全首次试水直播。 莫天全在直播中介绍称，疫情下的房地产及互联网行业迎来了发展的挑战和机遇。" +
                "1 月30号，房天下面向全行业免费开放在线直播开始直播卖房，2 月6号面向产业精英推出直播大赛；2 月26号 文旅地产界也开始" +
                "举行330多个全国优秀文旅项目亮相。 莫天全表示，过去一个月的时间里面，房天下累计在线直播卖房超过了12万场，这还不包括" +
                "二手房，观看人次累计过亿规模，近30万行业的从业人员加入了房天下的直播卖房大军。 当代置业执行董事兼董事长张鹏在直播中" +
                "表示，自疫情发生以来，当代置业在三方面做了一番功夫，首先是捐款捐物，直接去承担一个企业的责任。公司第一时间已向武汉" +
                "捐赠200万人民币，并且募集了一个千万规模的慈善基金。第二个就是， 公司也积极响应国家号召，做好多项防疫举措。尤其是95" +
                "个项目的6000位职员，与小区业主并肩作战。在房地产行业按下暂停键的同时，公司也在思考，如何通过提供更好的产品来服务社" +
                "区。 张鹏表示，长期来看，企业应建立起自己的差异化竞争力，提升产品服务配套。 21 世纪不动产中国区总裁兼CEO卢航在直播" +
                "中表示，在疫情期间，21 世纪不动产迅速推了一个21线上大学，把过去收费的课程剪出一个免费的精华版，帮大家去分析，假设" +
                "疫情过后，第一步一动起来以后，到底哪些生意可能会先复苏， 一分析特别简单，新房，第二就是租赁，第三就是一定要抓线上" +
                "的看房，VR看房线上直播，就是线上的动作。 卢航认为，近段时间，经纪人也陆续有线上成交，为什么能做到这一点？其实这考" +
                "验的是平时对线上客户的维护。各地中介门店也应在政府的指导下，规范的逐步复工。 中经联盟秘书长、优铺网创始人陈云峰认" +
                "为疫情下，大家终于注意到这次物业公司的必要性。面对疫情，更多的优秀物业在默默的付出，我们也突然意识到物业对我们非常" +
                "重要。而物业抢着在香港都轮着在上市，陈云峰认为对于物业公司是个重大的利好， 大家都突然意识到这些大的商场可以不去，但" +
                "是你离不开物业公司的保障，所以物业公司是受到了重视。 陈云峰还表示，当前市场下，商业及商业地产也面临较大的挑战，一些" +
                "对商办项目的限制其实是不利于服务业发展、以及大众创业的，应该呼吁适当放松这方面的政策。 资料显示，疫情期间，房天下积" +
                "极给出优惠，助力新房二手房市场在线成交。新房方面，房天下举办网上房交会，为开发商提供了一个很好地网上交易平台，更为" +
                "购房者提供累计超过10亿的优惠。二手房方面，房天下免费开放了几万个网上门店， 另有一个大优惠，为抗疫复产的二手房经纪公" +
                "司、经纪人推出了翻倍赠送服务产品的大力度优惠政策。给经纪人提供支持，共同对抗疫情，推动市场复苏。";
        if (str.isEmpty() || str == null) {
            str = test_0;
        }

        String[] arguments = new String[]{"python", "./static/python/load_test.py", "H:/JavaCode/ScriptSpider-master/text.txt" };

        try {
            Process proc = Runtime.getRuntime().exec(arguments);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                String result = line.replaceAll("\\[", "").replaceAll("\\]", "")
                        .replaceAll("\\'", "");
                resultList.add(result);
                logger.info("执行py脚本成功,获得预测分类为" + result);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.info("执行py脚本失败");
            e.printStackTrace();
        }
        return resultList;
    }
}
