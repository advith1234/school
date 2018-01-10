<h2>An unexpected error has occurred</h2>
<p>Please report this error to your system administrator or
appropriate technical support personnel. Thank you for your cooperation.
</p>
<%
    if (request.getServerName().equals("localhost")) {
%>
<h3>Error Message</h3>

<div style="width: 456px; overflow: auto;">${exception.message}</div>
<p />
<h3>Technical Details</h3>
<div style="width: 456px; overflow: auto;">${exceptionStack}</div>
<%
    }
%>