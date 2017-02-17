
package com.yy.springboot.sys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yy.springboot.sys.bean.EntryBean;
import com.yy.springboot.utils.UserUtil;
/**
 * 登录拦截器
 * @author janice
 *
 */
public class EntryInterceptor  extends HandlerInterceptorAdapter  {
	private static final Logger logger = LoggerFactory.getLogger(EntryInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,  Object obj, ModelAndView view)
			throws Exception {
		   
	}

	private void doPrint(HttpServletRequest request, HttpServletResponse response, String words, boolean toLogin) {
		try {
			PrintWriter out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
			builder.append("alert(\"" + words + "\");");
			if (toLogin) {
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
				builder.append("window.top.location.href=\"");
				builder.append(basePath);
				builder.append("\";");
			}
			builder.append("</script>");
			out.print(builder.toString());
			out.close();
		} catch (IOException e) {
			
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		logger.debug("do EntryInterceptor.preHandle ...");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 获取登录的信息
		EntryBean entryBean = UserUtil.getUserInSession(request);
		if (null == entryBean) {
			// 未登录或已过期
			doPrint(request, response, "当前页面过期，请重新登录！", true);
			return false;
		}

		return true;
	}

}
