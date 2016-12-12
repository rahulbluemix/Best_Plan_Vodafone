<%@page import="javax.swing.text.Document"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="util.TypeUsageCDR"%>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<style>
#image {
 	width: 30%;
	height: 100%;
	float: left;
}
#language {
 	width: 70%;
	height: 100%;
	float: left;
}
#header {
    width: 100%;
	height: 150px;
	border-width: 5px;
}
#section {
    width:1300px;
    clear:both;
    float:left;
    padding:2px;	 	 
}
#footer {
    color:Black;
    clear:both;
    text-align:center;
    padding:1px;	 	 
}
</style>
</head>	
<body style="background-color: White;">
<div id ="header">
	<div id ="image">
		<img src="images/vf-logomobile.png" alt="Best Plan Invoice" style = "height = 90px;">
	</div>
	<div id="language">
	<p style="padding-top: 30px;"></p>
	<center>
	<font color="Black" size="+2" face="Monotype Corsiva"><h3> <B>Best Plan On Invoice</B></h3></font>	
	</center>
	</div>
</div>
<div>
	<div id="section">
	<p style="padding: 2px;font-style: oblique;font-size: 15px;padding-top: initial;">
	A new section can be introduced on the invoice where a price plan will be offered to the customer & comparison will be drawn between the customers current plan & the proposed plan .Plan here is not just  limited to the price plan but the overall configuration or the products provisoned to the customer.
</p>
	</div>
	<div id="section">
	<fieldset>
			<legend>CDR File:</legend>
			<form name="uploadForm" action="UploadServlet.jsp" method="post" enctype="multipart/form-data">
			<input type="file" name="file1" size="50" />
			<br />
			<button id="buttonsubmit"> Submit </button>
	</form>
	</fieldset>
	</div>
	<div id="section">
	<fieldset>
			<legend>Subscriber Details:</legend>
			Subscriber Number:<br> 
			Frequent Call Type:<br>
			Total units Consumed:<br>
	</fieldset>
	</div>
	<div id="section">
	<fieldset>
			<legend>Current:</legend>
			Current Price Plan:<br> 
			Bill amount of current plan:<br>
	</fieldset>
	</div>
	<div id="section">
	<fieldset>
			<legend>Proposed Price Plan:</legend>
			Proposed Price Plan:<br>
			Bill amount of Proposed plan:<br>
	</fieldset>
	</div>
</div>
<div id="footer">
	<fieldset>
	<legend>Disclaimer:</legend>
	Copyright � Best Plan On Invoice
	</fieldset>
</div>
</div>
</body></html>