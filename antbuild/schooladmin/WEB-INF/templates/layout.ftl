<#-- 

This file contains all of the top-level page macros that define the overall structure of
a page in the application. These macros assemble, aggregate, and organize the tiles/html fragments
from tiles.ftl in order to come up with a complete page

-->


<#-- Top level page layout that includes only the minimum a page body with the standard width as 
	 well as the CSS and JavaScript we need -->
<#macro baseLayout>
<!DOCTYPE html PUBLIC ?-//W3C//DTD XHTML 1.0 Strict//EN? ?http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd?>
<html xmlns=?http://www.w3.org/1999/xhtml?>
  
<head>
  <@tiles.styleSheets/>
  <@tiles.dojo/>
  <@tiles.elearnJavaScript/>

  <title>GAQM</title> 
</head>

<body class="claro">

  <div id="Container">    
	<#nested/>
  </div>
	
</body>
</html>
</#macro>



<#-- Top level page layout that has a banner and header info area. -->
<#macro pageWithInfoHeaderLayout bodyBorder=true footer=true infoHeader=true>

	<@baseLayout>
	
		<@tiles.banner/>
		
		<#if infoHeader>
	    	<@tiles.infoHeader/>
	    </#if>
	    
	    <div style="height: 3px; font-size: 1px;"></div>
	    
	    <#if bodyBorder>	    	
			<@tiles.roundedCornerBorderTop/>
		</#if>
		
	    <#nested/>
	    
	    <#if footer>
			<@tiles.footer footerId="FooterFullWidth"/>
		</#if>
	    
	    <#if bodyBorder>
			<@tiles.roundedCornerBorderBottom/> 
		</#if>
	</@baseLayout>

</#macro>



<#-- Top level page layout that includes the standard header but not left side navigation menu -->
<#macro pageWithHeaderLayout bodyBorder=true footer=true infoHeader=true smallImages=true>

	<@baseLayout>
	
	    <@tiles.banner smallImages=smallImages/>
	    
	    <#if infoHeader>
	    	<@tiles.infoHeader/>
	    </#if>
	    
	    <div style="height: 5px; font-size: 1px;"></div>
	    
	    <#if bodyBorder>	    	
			<@tiles.roundedCornerBorderTop/>
		</#if>
		
	    <#nested/>
	    
	    <#if footer>
			<@tiles.footer footerId="FooterFullWidth"/>
		</#if>
	    
	    <#if bodyBorder>
			<@tiles.roundedCornerBorderBottom/>  
		</#if>
				
	</@baseLayout>
	
</#macro>



<#-- Top level page layout that includes the standard header and left side navigation menu -->
<#macro pageWithSideMenuLayout bodyBorder=true footer=true>

	<@pageWithInfoHeaderLayout bodyBorder=false footer=false>
	
		<div id="belowHeaderArea">
		
			<@tiles.leftMenuHTML/>
			<@tiles.initializeLeftMenuJS/>
			
			<span id="loadingMessage">Loading...</span>
            <div id="bodyContentArea" style="float: left; width: 727px; visibility:hidden">
			
				<#if bodyBorder>
					<@tiles.roundedCornerBorderTop/>
					<@tiles.pageTitleMessage/>
				</#if>
				
				<#nested/>
				
				<#if bodyBorder>
					<@tiles.roundedCornerBorderBottom/> 
				</#if>
				
				<#if footer>
					<@tiles.footer/>
				</#if>
				
			</div>
		</div>
	</@pageWithInfoHeaderLayout>

</#macro>
