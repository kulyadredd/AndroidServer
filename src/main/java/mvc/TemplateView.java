package mvc;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class TemplateView implements View {
	
	private Map<String, Object> ctx;
	private String templateName;
	
	
public TemplateView(String templateName, Map<String, Object> ctx) {
		
		this.templateName = templateName;
		this.ctx = ctx;
		
		Velocity.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.setProperty("resource.loader", "class");
		Velocity.init();
	}

	@Override
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		StringWriter sw = null;
		try {

			VelocityContext vc = new VelocityContext();			
			for(Map.Entry<String, Object> entry : this.ctx.entrySet())
				vc.put(entry.getKey(), entry.getValue());
			
			Template t = Velocity.getTemplate(this.templateName, "utf-8");
			sw = new StringWriter();
			t.merge(vc, sw);
			response.getWriter().write(sw.toString());

		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			if (sw != null)
				sw.close();
		}
	}
}
