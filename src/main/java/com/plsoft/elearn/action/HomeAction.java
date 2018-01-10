package com.plsoft.elearn.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.xhtmlrenderer.extend.FontResolver;
import org.xml.sax.SAXException;

import com.lowagie.text.DocumentException;
import com.plsoft.elearn.entity.Worksheet;

@Namespace("/worksheets")
public class HomeAction extends ElearnBaseAction {
	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger( HomeAction.class);
	private static final long serialVersionUID = 1L;
	
	HttpServletRequest request;
	HttpServletResponse response;
	
	private SimpleDateFormat sdf = new SimpleDateFormat( "EEEE, MMMM dd, yyyy");

	private Map<String, Object> session;
	
	private Integer rightvalue;
	private Integer leftvalue;
	private Integer questionsno;
	
	@Autowired
	public HomeAction(){
		
	}

	@Action(value="home", results={@Result(name="success", location="/WEB-INF/content/index.ftl", type="freemarker")})
	public String home() {
		logger.debug( "home");
		session.put( "currentDate", sdf.format( new Date()));
		return SUCCESS;
	}
	
	@Action(value="close", results={@Result(name="success", location="/WEB-INF/content/close.ftl", type="freemarker")})
	public String close() {
		session.put( "currentDate", sdf.format( new Date()));
		return SUCCESS;
	}
	
	@Action(value="worksheets", results={@Result(name="success", location="/WEB-INF/content/worksheets.ftl", type="freemarker")})
	public String worksheets() {
		session.put( "currentDate", sdf.format( new Date()));
		return SUCCESS;
	}
	
	@SuppressWarnings("static-access")
	@Action(value="additions", results={@Result(name="success", location="/WEB-INF/content/calculations.ftl", type="freemarker")})
	public String additions() {
		System.out.println(rightvalue+"..."+leftvalue+"...."+questionsno);
		session.remove("additions");
		session.remove("divisions");
		session.remove("multiplications");
		session.remove("subtractions");
		session.remove("status");
		/**
		 * below commented code is useful for generating ramdom values for a singleside, 
		 * mean, right/left side values length should be random with this.
		 * 
		 * **/
		//if(lowervalue >0 && highervalue >0 && lowervalue < highervalue){
			/*ArrayList<Integer> alist = new ArrayList<Integer>();
			for(int r=lowervalue;r<=highervalue;r++){
				alist.add(r);
			}
			Random rmd = new Random();
			Collections.shuffle(alist,rmd);*/
		if(rightvalue >0 && leftvalue >0){
			RandomStringUtils r = new RandomStringUtils();
			HashSet<Worksheet> w = new HashSet<Worksheet>();
			//while(w.size() <= 10){
				//for(Integer ing:alist){
			
			//do{
			int n = 1;
			if(n != 0){
				for(int i=1;i<=n;i++){
					Long rv = Long.parseLong(r.randomNumeric(rightvalue));
					Long lv = Long.parseLong(r.randomNumeric(leftvalue));
					if(rv.toString().length() == rightvalue && lv.toString().length() == leftvalue && w.size()<questionsno){
						Worksheet wObj = new Worksheet();
						Long tv = rv+lv;
						wObj.setRvalue(rv.toString());
						wObj.setLvalue(lv.toString());
						wObj.setTotalvalue(tv.toString());
						w.add(wObj);
						
					}
					n++;
					if(w.size() == questionsno){
						n=0;
					}					
				}
			}
			//}while(w.size()<=questionsno);
				//}
			session.put("additions",w);
		}/*else{
			session.put("status", "Lower value should be lesser than higher value");
		}*/
		session.put( "currentDate", sdf.format( new Date()));
		return SUCCESS;
	}		
	
	/************
	 * Generating PDF	 *  
	 * ************/
	@SuppressWarnings("unchecked")
	@Action(value="generatepdf")
	public void generatepdf() throws MalformedURLException, DocumentException {
		request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		response.setContentType("application/pdf");
		DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		
		StringBuffer buf = new StringBuffer();
		buf.append("<html>");
		String destPath=request.getSession().getServletContext().getRealPath("/")+"css/";
		String inputFile = destPath+"pdfstyle.css";
		String url = new File (inputFile).toURI().toURL().toString();
		buf.append("<head><link rel='stylesheet' type='text/css' "+"href='"+url+"' media='print'/></head>");
		//additions
		if(session.containsKey("additions")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("additions");			
			if(s.size() != 0){
				buf.append("<body>" +
						"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Additions worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : _________________</b><b style='float:right;margin-right:250px;'>Roll No : ________________</b><b style='float:right;margin-top:-17px;'>Class : ________________</b><br/><hr/></p></div>"+
						"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
						"</div>");
						int x = 0;
						for(Worksheet wObj:s){
						buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
						"<span style='padding:0px;color:#595959;'> "+wObj.getRvalue()+"</span><br/>"+
						"<span style='padding:0px;color:#595959;'>+"+wObj.getLvalue()+"</span><br/>"+
						//"<span style='padding:3px;'>"+wObj.getLvalue()+"</span>"+
						"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
						"<br/></div>");	x++;	}
				buf.append("</body></html>");
			}
		}
		//subtractions
		if(session.containsKey("subtractions")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("subtractions");			
			if(s.size() != 0){
			buf.append("<body>" +
					"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Subtractions worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : _________________</b><b style='float:right;margin-right:250px;'>Roll No : ________________</b><b style='float:right;margin-top:-17px;'>Class : ________________</b><br/><hr/></p></div>"+
					"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
					"</div>");
					int x = 0;
					for(Worksheet wObj:s){
					buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
					"<span style='padding:0px;color:#595959;'>"+wObj.getRvalue()+"</span><br/>"+
					//"<span style='padding:3px;'> - </span>"+
					"<span style='padding:0px;color:#595959;'>-"+wObj.getLvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<br/></div>");		x++;			}
			buf.append("</body></html>");
			}
		}
		//multiplications
		if(session.containsKey("multiplications")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("multiplications");			
			if(s.size() != 0){
			buf.append("<body>" +
					"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Multiplications worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : _________________</b><b style='float:right;margin-right:250px;'>Roll No : ________________</b><b style='float:right;margin-top:-17px;'>Class : ________________</b><br/><hr/></p></div>"+
					"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
					"</div>");
					int x = 0;
					for(Worksheet wObj:s){
					buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
					"<span style='padding:0px;color:#595959;'>"+wObj.getRvalue()+"</span><br/>"+
					//"<span style='padding:3px;'> * </span>"+
					"<span style='padding:0px;color:#595959;'>x"+wObj.getLvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<br/></div>");		x++;			}
			buf.append("</body></html>");
			}
		}
		//divisions
		if(session.containsKey("divisions")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("divisions");			
			if(s.size() != 0){
			buf.append("<body>" +
					"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Divisions worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : _________________</b><b style='float:right;margin-right:250px;'>Roll No : ________________</b><b style='float:right;margin-top:-17px;'>Class : ________________</b><br/><hr/></p></div>"+
					"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
					"</div>");
					int x = 0;
					for(Worksheet wObj:s){
					buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
					"<span style='padding:0px;color:#595959;'>"+wObj.getRvalue()+"</span><br/>"+
					//"<span style='padding:3px;'> / </span>"+
					"<span style='padding:0px;color:#595959;'>/"+wObj.getLvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<br/></div>");		x++;			}
			buf.append("</body></html>");
			}
		}
		
		//All Mathematics
		if(session.containsKey("maths")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("maths");			
			if(s.size() != 0){
			buf.append("<body>" +
					"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Mathematics worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : _________________</b><b style='float:right;margin-right:250px;'>Roll No : ________________</b><b style='float:right;margin-top:-17px;'>Class : ________________</b><br/><hr/></p></div>"+
					"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
					"</div>");
					int x = 0;
					for(Worksheet wObj:s){
					buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
					"<span style='padding:0px;color:#595959;'>"+wObj.getRvalue()+"</span><br/>"+
					//"<span style='padding:3px;'> / </span>"+
					"<span style='padding:0px;color:#595959;'>"+wObj.getLvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<br/></div>");		x++;			}
			buf.append("</body></html>");
			}
		}
		
		System.out.println("before build");
		DocumentBuilder builder;

		OutputStream os=null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			byte[] byteArray = buf.toString().getBytes("ISO-8859-1"); 
			ByteArrayInputStream baos = new ByteArrayInputStream(byteArray); 
			org.w3c.dom.Document doc = builder.parse(baos);
			org.xhtmlrenderer.pdf.ITextRenderer renderer = new org.xhtmlrenderer.pdf.ITextRenderer();
			renderer.setDocument(doc, "http://localhost:8080/schooladmin/worksheets/home");
			FontResolver resolver = renderer.getFontResolver();
			renderer.getFontResolver().addFont(destPath+"ebrima-webfont.ttf",true);
			renderer.setDocument(doc, null);			
			renderer.layout();
			os = response.getOutputStream();
			renderer.createPDF(os);
			os.flush();
			os.close();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(null != os)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		session.put( "currentDate", sdf.format( new Date()));
	}
	
	@SuppressWarnings("unchecked")
	@Action(value="generatepdfwithans")
	public void generatepdfwithans() throws MalformedURLException, DocumentException {
		request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		response.setContentType("application/pdf");
		DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		
		StringBuffer buf = new StringBuffer();
		buf.append("<html>");
		String destPath=request.getSession().getServletContext().getRealPath("/")+"css/";
		String inputFile = destPath+"pdfstyle.css";
		String url = new File (inputFile).toURI().toURL().toString();
		buf.append("<head><link rel='stylesheet' type='text/css' "+"href='"+url+"' media='print'/></head>");
		if(session.containsKey("additions")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("additions");			
			if(s.size() != 0){
			buf.append("<body>" +
					"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Additions worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : Admin</b><br/><hr/></p></div>"+
					"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
					"</div>");
					int x = 0;
					for(Worksheet wObj:s){
					buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getRvalue()+"</span><br/>"+
					//"<span style='padding:3px;'> + </span>"+
					"<span  style='padding:0px;color:#595959;'>+"+wObj.getLvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getTotalvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<br/></div>");x++;
					}
			buf.append("</body></html>");
			}
		}
		//subtractions
		if(session.containsKey("subtractions")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("subtractions");			
			if(s.size() != 0){
			buf.append("<body>" +
					"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Subtractions worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : Admin</b><br/><hr/></p></div>"+
					"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
					"</div>");
					int x = 0;
					for(Worksheet wObj:s){
					buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getRvalue()+"</span><br/>"+
					//"<span style='padding:3px;'> - </span>"+
					"<span  style='padding:0px;color:#595959;'>-"+wObj.getLvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getTotalvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<br/></div>");	x++;				}
			buf.append("</body></html>");
			}
		}
		//multiplications
		if(session.containsKey("multiplications")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("multiplications");			
			if(s.size() != 0){
			buf.append("<body>" +		
					"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Multiplications worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : Admin</b><br/><hr/></p></div>"+
					"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
					"</div>");
					int x = 0;
					for(Worksheet wObj:s){
					buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getRvalue()+"</span><br/>"+
					//"<span style='padding:3px;'> * </span>"+
					"<span  style='padding:0px;color:#595959;'>x"+wObj.getLvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getTotalvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<br/></div>");		x++;			}
			buf.append("</body></html>");
			}
		}
		//divisions
		if(session.containsKey("divisions")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("divisions");			
			if(s.size() != 0){
			buf.append("<body>" +					
					"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Divisions worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : Admin</b><br/><hr/></p></div>"+
					"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
					"</div>");
					int x = 0;
					for(Worksheet wObj:s){
					buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getRvalue()+"</span><br/>"+
					//"<span style='padding:3px;'> / </span>"+
					"<span  style='padding:0px;color:#595959;'>/"+wObj.getLvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getTotalvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<br/></div>");
					x++;
					}
			buf.append("</body></html>");
			}
		}
		//All Maths
		if(session.containsKey("maths")){
			HashSet<Worksheet> s = (HashSet<Worksheet>) session.get("maths");			
			if(s.size() != 0){
			buf.append("<body>" +					
					"<div style='margin-top:10px;'><center><h2>Vaishnavi</h2> <h3 style='margin-top:-15px;'>(The Innovative Play School)</h3><h3><u>Mathematics worksheet</u></h3></center><br/><p style='margin-top:-20px;'><b style='float:left;'>Name : Admin</b><br/><hr/></p></div>"+
					"<div style='margin-top:20px;background:#fff;color:#595959;'><span></span>"+					
					"</div>");
					int x = 0;
					for(Worksheet wObj:s){
					buf.append("<div style='color:#595959;width:160px;height:110px;float:left;text-align: right;'><b style='margin-right:10px;'>"+(x+1)+") </b>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getRvalue()+"</span><br/>"+
					//"<span style='padding:3px;'> / </span>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getLvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<span  style='padding:0px;color:#595959;'>"+wObj.getTotalvalue()+"</span><br/>"+
					"<span style='padding:0px;margin-left:9px;color:#595959;'>--------------</span><br/>"+
					"<br/></div>");
					x++;
					}
			buf.append("</body></html>");
			}
		}
		System.out.println("before build");
		DocumentBuilder builder;

		OutputStream os=null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			byte[] byteArray = buf.toString().getBytes("ISO-8859-1"); 
			ByteArrayInputStream baos = new ByteArrayInputStream(byteArray); 
			org.w3c.dom.Document doc = builder.parse(baos);
			org.xhtmlrenderer.pdf.ITextRenderer renderer = new org.xhtmlrenderer.pdf.ITextRenderer();
			renderer.setDocument(doc, "http://localhost:8080/schooladmin/worksheets/home");
			FontResolver resolver = renderer.getFontResolver();
			renderer.getFontResolver().addFont(destPath+"ebrima-webfont.ttf",true);
			renderer.setDocument(doc, null);			
			renderer.layout();
			os = response.getOutputStream();
			renderer.createPDF(os);
			os.flush();
			os.close();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(null != os)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		session.put( "currentDate", sdf.format( new Date()));
	}
	
	@SuppressWarnings("static-access")
	@Action(value="subtractions", results={@Result(name="success", location="/WEB-INF/content/calculations.ftl", type="freemarker")})
	public String subtractions() {
		System.out.println(rightvalue+"..."+leftvalue);
		session.remove("subtractions");
		session.remove("divisions");
		session.remove("additions");
		session.remove("multiplications");
		session.remove("status");
		if(rightvalue >0 && leftvalue >0 && leftvalue < rightvalue ){
			/*ArrayList<Integer> alist = new ArrayList<Integer>();
			for(int r=lowervalue;r<=highervalue;r++){
				alist.add(r);
			}
			Random rmd = new Random();
			Collections.shuffle(alist,rmd);*/
			RandomStringUtils r = new RandomStringUtils();
			HashSet<Worksheet> w = new HashSet<Worksheet>();
			//while(w.size() <= 10){
			int n = 1;
			if(n != 0){
			for(int i=1;i<=n;i++){
					Long rv = Long.parseLong(r.randomNumeric(rightvalue));
					Long lv = Long.parseLong(r.randomNumeric(leftvalue));
					if(rv.toString().length() == rightvalue && lv.toString().length() == leftvalue  && rv > lv && w.size()<questionsno){
						Worksheet wObj = new Worksheet();
						Long tv = rv-lv;
						wObj.setRvalue(rv.toString());
						wObj.setLvalue(lv.toString());
						wObj.setTotalvalue(tv.toString());
						w.add(wObj);
					}
					n++;
					if(w.size() == questionsno){
						n=0;
					}
				}
			}
			
			/*RandomStringUtils r = new RandomStringUtils();
			HashSet<Worksheet> w = new HashSet<Worksheet>();
			int i=1;
			do{
				Worksheet wObj = new Worksheet();
				Long rv = Long.parseLong(r.randomNumeric(rightvalue));
				Long lv = Long.parseLong(r.randomNumeric(leftvalue));
				if(rv.toString().length() == rightvalue && lv.toString().length() == leftvalue && rv > lv){
					Long tv = rv-lv;
					wObj.setRvalue(rv.toString());
					wObj.setLvalue(lv.toString());
					wObj.setTotalvalue(tv.toString());
					w.add(wObj);
					i++;
				}
				
			}while(i <= 10);*/
			session.put("subtractions",w);
		}else{
			session.put("status", "Leftside value should be lesser than Rightside value");
		}
		session.put( "currentDate", sdf.format( new Date()));
		return SUCCESS;
	}
	
	@SuppressWarnings("static-access")
	@Action(value="multiplications", results={@Result(name="success", location="/WEB-INF/content/calculations.ftl", type="freemarker")})
	public String multiplications() {
		System.out.println(rightvalue+"..."+leftvalue);
		session.remove("multiplications");
		session.remove("divisions");
		session.remove("additions");
		session.remove("subtractions");
		session.remove("status");
		if(rightvalue >0 && leftvalue >0){
				RandomStringUtils r = new RandomStringUtils();
				HashSet<Worksheet> w = new HashSet<Worksheet>();
				int n = 1;
				if(n != 0){
				for(int i=1;i<=n;i++){
						Long rv = Long.parseLong(r.randomNumeric(rightvalue));
						Long lv = Long.parseLong(r.randomNumeric(leftvalue));
						if(rv.toString().length() == rightvalue && lv.toString().length() == leftvalue && w.size()<questionsno){
							Worksheet wObj = new Worksheet();
							Long tv = rv*lv;
							wObj.setRvalue(rv.toString());
							wObj.setLvalue(lv.toString());
							wObj.setTotalvalue(tv.toString());
							w.add(wObj);
						}
						n++;
						if(w.size() == questionsno){
							n=0;
						}
					}
					}
			session.put("multiplications",w);
		}	
		session.put( "currentDate", sdf.format( new Date()));
		return SUCCESS;
	}
	
	@SuppressWarnings("static-access")
	@Action(value="divisions", results={@Result(name="success", location="/WEB-INF/content/calculations.ftl", type="freemarker")})
	public String divisions() {
		System.out.println(rightvalue+"..."+leftvalue);
		session.remove("divisions");
		session.remove("additions");
		session.remove("multiplications");
		session.remove("subtractions");
		session.remove("status");
		if(rightvalue >0 && leftvalue >0 && rightvalue > leftvalue){
				RandomStringUtils r = new RandomStringUtils();
				HashSet<Worksheet> w = new HashSet<Worksheet>();
				int n = 1;
				if(n != 0){
				for(int i=1;i<=n;i++){
						Long rv = Long.parseLong(r.randomNumeric(rightvalue));
						Long lv = Long.parseLong(r.randomNumeric(leftvalue));
						if(rv.toString().length() == rightvalue && lv.toString().length() == leftvalue  && rv > lv && w.size()<questionsno){
							Worksheet wObj = new Worksheet();
							Double tv = (double) (Double.parseDouble(rv.toString())/Double.parseDouble(lv.toString()));
							wObj.setRvalue(rv.toString());
							wObj.setLvalue(lv.toString());
							wObj.setTotalvalue(new DecimalFormat("#.####").format(tv));
							w.add(wObj);
						}
						n++;
						if(w.size() == questionsno){
							n=0;
						}
					}
				}
			
			session.put("divisions",w);
		}else{
			session.put("status", "Leftside value should be lesser than Rightside value");
		}
		session.put( "currentDate", sdf.format( new Date()));
		return SUCCESS;
	}
	
	//Action for All(additions/ subtractions/ multiplications/ divisions)
	@SuppressWarnings("static-access")
	@Action(value="maths", results={@Result(name="success", location="/WEB-INF/content/calculations.ftl", type="freemarker")})
	public String maths() {
		System.out.println(rightvalue+"..."+leftvalue);
		session.remove("multiplications");
		session.remove("divisions");
		session.remove("additions");
		session.remove("subtractions");
		session.remove("maths");
		session.remove("status");
		if(rightvalue >0 && leftvalue >0 && rightvalue > leftvalue){
				RandomStringUtils r = new RandomStringUtils();
				HashSet<Worksheet> w = new HashSet<Worksheet>();
				//for addition
				int n = 1;
				if(n != 0){
				for(int i=1;i<=n;i++){
						Long rv = Long.parseLong(r.randomNumeric(rightvalue));
						Long lv = Long.parseLong(r.randomNumeric(leftvalue));
						if(rv.toString().length() == rightvalue && lv.toString().length() == leftvalue && w.size()<questionsno){
							Worksheet wObj = new Worksheet();							
							wObj.setRvalue(rv.toString());
							wObj.setLvalue("+  "+lv.toString());
							Long tv = rv+lv;
							wObj.setTotalvalue(tv.toString());
							w.add(wObj);
						}
						n++;
						if(w.size() == questionsno){
							n=0;
						}
					}
					}
				//subtractions
				int s = 1;
				if(s != 0){
				for(int i=1;i<=s;i++){
						Long rv = Long.parseLong(r.randomNumeric(rightvalue));
						Long lv = Long.parseLong(r.randomNumeric(leftvalue));
						if(rv.toString().length() == rightvalue && lv.toString().length() == leftvalue && w.size()< 2*questionsno){
							Worksheet wObj = new Worksheet();							
							wObj.setRvalue(rv.toString());
							wObj.setLvalue("-  "+lv.toString());
							Long tv = rv-lv;
							wObj.setTotalvalue(tv.toString());
							w.add(wObj);
						}
						s++;
						if(w.size()/2 == questionsno){
							s=0;
						}
					}
					}
				//multiplications
				int m = 1;
				if(m != 0){
				for(int i=1;i<=m;i++){
						Long rv = Long.parseLong(r.randomNumeric(rightvalue));
						Long lv = Long.parseLong(r.randomNumeric(leftvalue));
						if(rv.toString().length() == rightvalue && lv.toString().length() == leftvalue && w.size()< 3*questionsno){
							Worksheet wObj = new Worksheet();							
							wObj.setRvalue(rv.toString());
							wObj.setLvalue("x  "+lv.toString());
							Long tv = rv*lv;
							wObj.setTotalvalue(tv.toString());
							w.add(wObj);
						}
						m++;
						if(w.size()/3 == questionsno){
							m=0;
						}
					}
					}
				//divisions
				int d = 1;
				if(d != 0){
				for(int i=1;i<=d;i++){
						Long rv = Long.parseLong(r.randomNumeric(rightvalue));
						Long lv = Long.parseLong(r.randomNumeric(leftvalue));
						if(rv.toString().length() == rightvalue && lv.toString().length() == leftvalue && w.size()< 4*questionsno){
							Worksheet wObj = new Worksheet();							
							wObj.setRvalue(rv.toString());
							wObj.setLvalue("/  "+lv.toString());
							Double tv = (double) (Double.parseDouble(rv.toString())/Double.parseDouble(lv.toString()));
							wObj.setTotalvalue(new DecimalFormat("#.####").format(tv));
							w.add(wObj);
						}
						d++;
						if(w.size()/4 == questionsno){
							d=0;
						}
					}
					}
			session.put("maths",w);
		}else{
			session.put("status", "Leftside value should be lesser than Rightside value");
		}	
		session.put( "currentDate", sdf.format( new Date()));
		return SUCCESS;
	}

	public Integer getLeftvalue() {
		return leftvalue;
	}

	public void setLeftvalue(Integer leftvalue) {
		this.leftvalue = leftvalue;
	}

	public Integer getRightvalue() {
		return rightvalue;
	}

	public void setRightvalue(Integer rightvalue) {
		this.rightvalue = rightvalue;
	}

	/*public Map<String, Object> getSession() {
		return session;
	}*/

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Integer getQuestionsno() {
		return questionsno;
	}

	public void setQuestionsno(Integer questionsno) {
		this.questionsno = questionsno;
	}
	
	
	
}
