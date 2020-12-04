package com.cn.configplug.yzm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 验证码生成实现
 * @author Administrator
 *
 */
@Controller
public class YZM {

	/**
	 * 生成验证码
	 * 1.获取初始图片(大小)
	 * 2.画干扰线
	 * 3.画噪点
	 * 4.背景色
	 * @throws IOException 
	 */
	@RequestMapping("/codeImage")
	public void ImportCode(HttpServletRequest req, HttpServletResponse res) throws IOException {
		int width = 200;
		int height = 69;
		// 1.创建图片
		BufferedImage bfi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //生成对应宽高的初始图片
		Graphics graphics = bfi.getGraphics(); //获取图片对象
		//填充背景(矩形左上角的x坐标，y坐标，矩形的宽度，高度)
		graphics.fillRect(0, 0, width, height);
		graphics.setFont(new Font("宋体", Font.BOLD, 40)); //设置字体(String 字体，int 风格，int 字号)
		
		char[] yzm = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		Random r = new Random();
		int index; // 下标
		StringBuffer sb = new StringBuffer();
		// 给验证码赋值，填充不同的颜色
		for(int i=0;i<4;i++) {  //i<4:这里指定验证码为4位
			index = r.nextInt(yzm.length);
			graphics.setColor(getRandomColor()); //设置每个验证码字母颜色
			sb.append(yzm[index]);
			// Graphics2D 旋转是该类的方法 Graphics类没有此方法
//			int degree = r.nextInt() % 30; // 字体角度旋转 小于30度
//			graphics.rotate(degree * Math.PI / 180, (i*20)+2, 45);
			graphics.drawString(yzm[index]+"", i*48, 45);
		}
		
		// 2.添加干扰线
		// 2目前判断可以放置在1中，故为空。预测会不成功
		for(int i=0;i<20;i++) {
			graphics.setColor(getRandomColor());
			graphics.drawLine(r.nextInt(width),r.nextInt(height),r.nextInt(width),r.nextInt(height));
		}
		
		// 3.添加噪点
		for(int i=0;i<90;i++) {
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			// bfi.setRGB(x, y, r.nextInt(255)); //直接在图上是设置噪点
			// 再对象上设置噪点，填充大小为2*2的像素点
			graphics.setColor(getRandomColor());
			graphics.fillRect(x, y, 2, 2);
		}
		
		HttpSession session = req.getSession(); // 保存到session
		session.setAttribute("verificationCode", sb.toString()); // 将验证码保存到session中
		ImageIO.write(bfi, "JPG", res.getOutputStream()); // 写到输出流
	}
	
	// 随机颜色
	public Color getRandomColor() {
		Random random = new Random();
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
}
