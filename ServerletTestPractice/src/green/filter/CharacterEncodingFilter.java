package green.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {

	FilterConfig config;
	
	public void destry() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding(config.getInitParameter("encoding")); //web.xml¿¡¼­ °¡Á®¿È
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
}
