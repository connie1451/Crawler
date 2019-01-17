package com.connie.crawl.main;
import java.net.*;
import java.io.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.sql.*;
public class Zcrawler {
	//连接并抓取网页内容，并返回一个string
	static String GetStr(String url){
		String result="";
		BufferedReader in= null;
		try{
			URL realUrl=new URL(url);
			URLConnection connection=realUrl.openConnection();
			connection.connect();
			in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line=in.readLine())!=null){
				result+=line;
			}
		}catch(Exception e){
			System.out.println("发送get请求出错！"+e);
			e.printStackTrace();
		}finally{
			try{
				if(in!=null)
					in.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return result;
	}
	//将抓取完的内容进行正则匹配，返回匹配结果
	static ArrayList<Zhihu> GetRecommendations(String content) {
		// 预定义一个ArrayList来存储结果
		  ArrayList<Zhihu> results = new ArrayList<Zhihu>();
		  // 用来匹配url，也就是问题的链接
		  Pattern pattern = Pattern
		    .compile("<h2>.+?question_link.+?href=\"(.+?)\".+?</h2>");
		  Matcher matcher = pattern.matcher(content);
		  // 是否存在匹配成功的对象
		  Boolean isFind = matcher.find();
		  while (isFind) {
		   // 定义一个知乎对象来存储抓取到的信息
		   Zhihu zhihuTemp = new Zhihu(matcher.group(1));
		   // 添加成功匹配的结果
		   results.add(zhihuTemp);
		   // 继续查找下一个匹配对象
		   isFind = matcher.find();
		  }
		  return results;
		 }
	//定义数据库的连接和存储 
	static  void DBstoring(ArrayList<Zhihu> z){
		Connection con;
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/spider?useUnicode=true&characterEncoding=UTF-8";
		String user="root";
		String password="123456";
		//存入数据库
		try{
			Class.forName(driver);
			//连接数据库
			con=DriverManager.getConnection(url,user,password);
			if(!con.isClosed())
				System.out.println("connect db successful");
			//创建statement对象，用来执行sql
			Statement statement=con.createStatement();
			//要执行的sql
			for(Zhihu i:z){
				PreparedStatement psql;
				psql = con.prepareStatement("insert into Zdata "
				        + "values(?,?,?,?)");
				psql.setString(1,null );
				psql.setString(2,i.question );
				psql.setString(3, i.questionDescription);
				psql.setInt(4, i.answers.size());
				psql.executeUpdate();
			}
			con.close();			
		}catch(ClassNotFoundException e){
			//数据库驱动类异常处理
			System.out.println("can't find the Driver");
			e.printStackTrace();
		}catch(SQLException e){
			//数据库连接异常
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			System.out.println("DB storing success");
		}
		
	}
	public static void main(String[] args) {
		  // 定义即将访问的链接
		  String url = "https://www.zhihu.com/explore/recommendations";
		  // 访问链接并获取页面内容
		  String content = GetStr(url);
		  //测试的时候打开，System.out.print(content);
		  // 获取编辑推荐
		  ArrayList<Zhihu> myZhihu =GetRecommendations(content);
		  // 打印结果
		  System.out.println(myZhihu);
		  DBstoring(myZhihu);
		 }

}
