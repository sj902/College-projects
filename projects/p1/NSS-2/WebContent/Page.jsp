<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.lang.Runtime"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="HelperClasses.Helper"%>
<%@ page import="java.util.concurrent.TimeUnit"%>
<%@ page import=" java.io.BufferedInputStream"%>
<%@ page import=" java.io.File"%>
<%@ page import=" java.io.FileInputStream"%>
<%@ page import=" java.io.InputStream"%>
<%@ page trimDirectiveWhitespaces="true"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<head>
<meta charset="UTF-8">
<title>Gradecard Downloading...</title>
<link rel="stylesheet" href="css/style.css">
</head>

<body>

	<div class="wrapper">
		<div class="container">

			<%
				String enrolmentNumber = request.getParameter("enrolmentNumber");

				String pincode = Helper.function(enrolmentNumber);
				String path = "/Users/shubhamjain/eclipse-workspace/NSS-2/Data/";
				if (pincode.equals("")) {
					out.println("<h1>User does not exist</h1><br/>Click <a"+
							" style = \"width: 100%;"
							+ "position: relative; z-index: 2;\"" 
							+ "href=" +  "\"index.html\">here</a> to go back.");
				} else {

					
					String inputPath = path + enrolmentNumber + ".pdf";
					String outputPath = path + enrolmentNumber + "_watermark.pdf";

					String watermarkCommand = "java -jar /Users/shubhamjain/NetBeansProjects/WatermarkHelper/dist/WatermarkHelper.jar"
							+ " " + inputPath + " " + outputPath + " " + enrolmentNumber+"2085-2693";

					try {
						Process p = Runtime.getRuntime().exec(watermarkCommand);
					} catch (Exception e) {
						out.println("An exception occurred: " + e.getMessage());
					}
					inputPath = outputPath;
					outputPath = path + enrolmentNumber + "_signed.pdf";

					
					
					String signCommand = "java -jar /Users/shubhamjain/eclipse-workspace/NSS-2/WebContent/PasswrodHelper.jar"
							+ " " + inputPath + " " + outputPath + " " +pincode+" "+ "pratham"+" " +
							"/Users/shubhamjain/eclipse-workspace/NSS-2/WebContent/certificate.p12";

					try {
						Process p = Runtime.getRuntime().exec(signCommand);
					} catch (Exception e) {
						out.println(e.getMessage());

						//out.println("<h1>Enter Pincode to open the file</h1>");

						System.out.println("AAAAAAAAAAAAAAAAAAAAn exception occurred: " + e.getMessage());
					}
					
					TimeUnit.SECONDS.sleep(2);
					out.println("<h1>Enter Pincode to open the file</h1>");
					String filename = outputPath;
					response.setContentType("application/octet-stream");
					String disHeader = "Attachment; Filename=\"Gradecard.pdf\"";
					response.setHeader("Content-Disposition", disHeader);
					File fileToDownload = new File(filename);

					InputStream in = null;
					ServletOutputStream outs = response.getOutputStream();
					out.clear(); // where out is a JspWriter 
					out = pageContext.pushBody();

					try {
						in = new BufferedInputStream(new FileInputStream(fileToDownload));
						int ch;
						while ((ch = in.read()) != -1) {
							outs.print((char) ch);
						}
					} finally {
						if (in != null)
							in.close(); // very important
					}
					out.clear(); // where out is a JspWriter 
					out = pageContext.pushBody();

					outs.flush();
					outs.close();
					in.close();

				}
			%>
		</div>

		<ul class="bg-bubbles">
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>


	<script src="js/index.js"></script>




</body>

</html>
