package cn.unis.gmweb.utils;

import cn.unis.gmweb.pojo.QdlRealTimeTree;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

public class myTest {

	@Test
	public void testInteger(){
		QdlRealTimeTree qdl= new QdlRealTimeTree();
	}
	@Test
	public void testDouble(){
		Double dou = 3000.533;
		dou = (double)Math.round(dou*100)/100;
		System.err.println(dou);
	}
	@Test
	public void testJson(){
		String json = "{\\\"repair\\\":{\\\"score\\\":100.0,\\\"num\\\":0.0},\\\"traj\\\":{\\\"score\\\":56.0,\\\"num\\\":8090.0},\\\"recordtime\\\":1544025540753,\\\"abnormal\\\":{\\\"score\\\":100.0,\\\"num\\\":0.0},\\\"alarm\\\":{\\\"score\\\":100.0,\\\"num\\\":0.0},\\\"mac\\\":\\\"e47d5a1cd5b0\\\",\\\"traffic\\\":{\\\"score\\\":88.0,\\\"num\\\":2317.0}}";
		System.err.println(json);
		System.err.println(StringEscapeUtils.unescapeJava(json));
		System.err.println(StringEscapeUtils.unescapeJavaScript(json));
	}
	public static void main(String[] args)  {

		SimpleDateFormat dateFormat= new SimpleDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
		String dt=dateFormat.format(new Date(1544112249520L));
		System.out.println(dt);
		String dt2=dateFormat.format(new Date(1544112253610L));
		System.out.println(dt2);
		System.err.println(System.currentTimeMillis());




	}
}
