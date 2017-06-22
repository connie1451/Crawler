package ZhihuCrawler;
import java.util.ArrayList;
import java.util.regex.*;

public class Zhihu {
	 public String question;// 问题
	 public String questionDescription;// 问题描述
	 public String zhihuUrl;// 网页链接
	 public ArrayList<String> answers;// 存储所有回答的数组
	 // 构造方法初始化数据
	 public Zhihu(String url) {
	  // 初始化属性
	  question = "";
	  questionDescription = "";
	  zhihuUrl = "";
	  answers = new ArrayList<String>();
	  // 判断url是否合法
	  if (getRealUrl(url)) {
	   System.out.println("正在抓取" + zhihuUrl);
	   // 根据url获取该问答的细节
	   String content = Zcrawler.GetStr(zhihuUrl);
	   Pattern pattern;
	   Matcher matcher;
	   // 匹配标题
	   pattern = Pattern.compile("QuestionHeader-title\">(.+?)<");
	   matcher = pattern.matcher(content);
	   if (matcher.find()) {
	    question = matcher.group(1);
	   }
	   // 匹配描述
	   pattern = Pattern
	     .compile("QuestionHeader-detail.+?text\">(.+?)<");
	   matcher = pattern.matcher(content);
	   if (matcher.find()) {
	    questionDescription = matcher.group(1);
	   }
	   // 匹配答案
	   pattern = Pattern.compile("CopyrightRichText-richText\"><p>(.+?)<");
	   matcher = pattern.matcher(content);
	   boolean isFind = matcher.find();
	   while (isFind) {
	    answers.add(matcher.group(1));
	    isFind = matcher.find();
	   }
	  }
	 }
	 // 根据自己的url抓取自己的问题和描述和答案
	 public boolean getAll() {
	  return true;
	 }
	 // 处理url
	 boolean getRealUrl(String url) {
	  // 将http://www.zhihu.com/question/22355264/answer/21102139
	  // 转化成http://www.zhihu.com/question/22355264
	  // 否则不变
	  Pattern pattern = Pattern.compile("question/(.*?)/");
	  Matcher matcher = pattern.matcher(url);
	  if (matcher.find()) {
	   zhihuUrl = "https://www.zhihu.com/question/" + matcher.group(1);
	  } else {
	   return false;
	  }
	  return true;
	 }
	 @Override
	 public String toString() {
	  return "问题：" + question + "\n" + "描述：" + questionDescription + "\n"
	    + "链接：" + zhihuUrl + "\n回答：" + answers.size() + "\n";
	 }
	}
