package com.zhiyin.ad.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhiyin.ad.AdApplication;
import com.zhiyin.ad.config.AdTimerConfig;
import com.zhiyin.ad.entity.AdAllowSite;
import com.zhiyin.ad.entity.AdAudioDetail;
import com.zhiyin.ad.entity.AdBasicInfo;
import com.zhiyin.ad.service.IAdAllowSiteService;
import com.zhiyin.ad.service.IAdAudioDetailService;
import com.zhiyin.ad.service.IAdBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hg on 2016/7/15.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AdApplication.class})
@WebAppConfiguration
public class AdAllowSiteServiceImplTest  {

    @Autowired
    private IAdBasicInfoService adBasicInfoService;

    @Autowired
    private IAdAudioDetailService adAudioDetailService;

    @Autowired
    private IAdAllowSiteService adAllowSiteService;

    @Before
    public void ini(){

    }

    /**
     * 添加一个广告，并且添加2个音频，并投放到3个热点
     * @throws Exception
     */
    @Test
    public void testDeleteByPrimaryKey() throws Exception {

        AdBasicInfo ad = new AdBasicInfo();
        ad.setTitle("AdTest");
        ad.setCompanyId(1L);
        ad.setShelfOnTime(DateTime.now().toDate());
        ad.setShelfOffTime(DateTime.now().plusMinutes(AdTimerConfig.TimerInterval).toDate());
        Long adId = adBasicInfoService.insertSelectiveGet(ad);

        for(int i=1;i<3;i++){
            AdAudioDetail detail = new AdAudioDetail();
            detail.setAdId(adId);
            detail.setRoleId((long)i);
            detail.setTitle("Ad_"+i);
            detail.setDuration(10);
            detail.setSavePath("a.mp3");
            adAudioDetailService.insertSelectiveGet(detail);
        }
        List<AdAudioDetail> list = adAudioDetailService.selectByAd(adId);
        Assert.assertTrue( list.size() == 2);

        for(int i=1; i<4; i++){
            AdAllowSite tmp = new AdAllowSite();
            tmp.setAdId(adId);
            tmp.setAddrId( (long)i);
            adAllowSiteService.insertSelectiveGet(tmp);
        }
        List<AdAllowSite> sites = adAllowSiteService.selectByAd(adId);
        Assert.assertTrue( sites.size()== 3);
    }

    @Test
    public void test() throws Exception {
        List<AdBasicInfo> sel = adBasicInfoService.selectWillShelfOn();
        log.info(JSON.toJSONString(sel));
    }

    @Test
    public void testDate(){
        System.out.println( DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void tt() throws Exception {
        int codePoint = 128149;

//        char c = 0x2202;//aka 8706 in decimal.  codepoints are in hex.
        char[] c = Character.toChars(codePoint);

        String s = String.valueOf(c);
        log.info(s);
        StringBuffer sb = new StringBuffer();
        sb.append(Character.toChars(127467));
        sb.append(Character.toChars(127479));
        System.out.println(sb);
//
        AdBasicInfo ad = new AdBasicInfo();
        ad.setCompanyId(1L);
         s = StringEscapeUtils.unescapeJava("\uF09F9294\n"); // s contains the euro symbol followed by newline

        ad.setTitle("\u2202" +"sss" + sb + "sd");
        ad.setShelfOnTime(DateTime.now().toDate());
        ad.setShelfOffTime(DateTime.now().plusMinutes(AdTimerConfig.TimerInterval).toDate());
        Long adId = adBasicInfoService.insertSelectiveGet(ad);

        AdBasicInfo sel = adBasicInfoService.selectById(adId);
        log.info(JSON.toJSONString(sel));

    }
}