<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="T1" class="util.TypeUsageCDR"></jsp:useBean>
<%
String anjana[] = new String[10];
ArrayList<String> values = T1.newMain();
Iterator<String> it = values.iterator();
int i = 0;
while(it.hasNext()){
   anjana[i] = (it.next());
   //System.out.println(anjana[i]);
   i++;
}
session.setAttribute("subscriber", anjana[0]);
session.setAttribute("description", anjana[1]);
session.setAttribute("totalUnits", anjana[2]);
session.setAttribute("currentpricePlan", anjana[3]);
session.setAttribute("currentPrice", anjana[4]);
session.setAttribute("proposedPlan", anjana[5]);
session.setAttribute("ProposedBill", anjana[6]);
String redirectURL = "newBestPlan.jsp";
response.sendRedirect(redirectURL);

%>
</body>
</html>