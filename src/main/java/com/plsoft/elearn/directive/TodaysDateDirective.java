package com.plsoft.elearn.directive;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * This is a custom Free Marker directive that will print today's date
 * in a format like so: Tuesday, September 7, 2011
 *
 * If the format attribute is passed in, this directive will use the format
 * pattern that was passed in instead of using its default format
 *
 * @author Ravikanth.Tangeti
 */
public class TodaysDateDirective implements TemplateDirectiveModel {

	private DateFormat df = new SimpleDateFormat("EEEE, MMMM d, yyyy"); //Tuesday, September 7, 2010

    public void execute(Environment env,
            Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body)
            throws TemplateException, IOException {

        if (loopVars.length != 0) {
                throw new TemplateModelException(
                    "This directive doesn't allow loop variables.");
        }

        // If there is non-empty nested content:
        if (body != null) {
        	throw new TemplateModelException("This directive does not support nested content.");
        } else {

        	DateFormat formatter = df;

        	if (params.get("format") != null) {
        		formatter = new SimpleDateFormat(((SimpleScalar)params.get("format")).getAsString());
        	}

        	Writer writer = env.getOut();
        	writer.write(formatter.format(new Date()));

        }
    }

}
