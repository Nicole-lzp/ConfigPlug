package com.cn.configplug.yzm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/code")
public class CodeController {
	
	// 校验验证码
	@RequestMapping("/checkCode")
	@ResponseBody
	public Map<String, Object> checkCode(String code, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		String verificationCode = (String) session.getAttribute("verificationCode");
		if(verificationCode.equalsIgnoreCase(code)) {
			map.put("valid", true);
		} else {
			map.put("valid", false);
		}
		return map;
	}

	// 生成验证码
	@RequestMapping("/image")
	public void image(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		
		BufferedImage bfi = new BufferedImage(80, 25, BufferedImage.TYPE_INT_RGB); //生成对应宽高的初始图片
		Graphics g = bfi.getGraphics();
		g.fillRect(0, 0, 80, 25);
		
		// 验证码字符范围
		char[] yzm = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		Random r = new Random();
		int index;
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<4; i++) {
			index = r.nextInt(yzm.length);
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			Font font = new Font("宋体", 30, 20);
			g.setFont(font);
			g.drawString(yzm[index] + "", (i*20)+2, 23);
			sb.append(yzm[index]);
		}
		
		// 添加噪点
		int area = (int) (0.02*60*25);
		for(int i=0; i<area; ++i) {
			int x = (int)(Math.random()*80);
			int y = (int)(Math.random()*25);
			bfi.setRGB(x, y, (int)(Math.random()*255));
		}
		
		// 设置验证码中的干扰线
		for(int i=0; i<3; i++) {
			int xstart = (int)(Math.random()*80);
			int ystart = (int)(Math.random()*25);
			int xend = (int)(Math.random()*80);
			int yend = (int)(Math.random()*25);
			g.setColor(interLine(1,255));
			g.drawLine(xstart, ystart, xend, yend);
		}
		HttpSession session = request.getSession(); // 保存到session
		session.setAttribute("verificationCode", sb.toString()); // 将验证码保存到session中
		ImageIO.write(bfi, "JPG", response.getOutputStream()); // 写到输出流
	}
	
	private static Color interLine(int Low, int High) {
		if(Low > 255) { Low = 255; }
		if(High > 255) { High = 255; }
		if(Low < 0) { Low = 0; }
		if(High < 0) { High = 0; }
		int interval = High - Low;
		int r = Low + (int)(Math.random()*interval);
		int g = Low + (int)(Math.random()*interval);
		int b = Low + (int)(Math.random()*interval);
		return new Color(r, g, b);
	}
}
