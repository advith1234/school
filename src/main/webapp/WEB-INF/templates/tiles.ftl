<#-- 

This file contains all of the components/tiles/html fragments that are
used in the application. It contains no top-level page macros, only
fragment macros. See layout.ftl for top-level page macros.

-->


<#-- This is a shortcut for typing the full URL path to the application -->
<#macro fullPath>
${request.scheme}://${request.serverName}:${request.serverPort?c}${request.contextPath}
</#macro>


<#-- This is the image of PERF and TRF icons at the top of the screen -->
<#macro banner smallImages=true>

<#if smallImages>
    <img style="position:absolute;" src="<@fullPath/>/images/logo_perf_trf_small.jpg" alt="PERF/TRF logo" />
    <div style="height:54px;"></div> 
<#else>
    <div id="Header"><img style="display: inline; position: absolute; margin-left: 197px; margin-top: -1px;" src="<@fullPath/>/images/logo_trf_large.jpg" alt="TRF logo"/><img style="display: inline; position: absolute;" src="<@fullPath/>/images/logo_perf_large.jpg" alt="PERF logo" />
    </div>
    <div style="height: 81px">&nbsp;</div>
</#if>

</#macro>



<#-- 
This is the part of the header that shows date and page title with a grey background. 

By default, the submission unit and fund will be displayed in this header as well. For
a single page, you can prevent these values from being displayed by setting the 
headerSubmissionUnit and headerFund global boolean values to false.
-->
<#macro infoHeader forTabs=false borders=false>
  <#if borders>
    <@roundedCornerBorder bgGrey=true>
      <table width="100%">
        <tr>
          <td><@todaysDate/></td>
          <td align="right" nowrap="nowrap">Welcome <@s.property value='%{#session.user_name}'/> | <a href="#">Logout</a></td>
        </tr>
      </table>
     </@roundedCornerBorder>
  <#else>
    <div id="employeeInfo">
      <table width="100%">
        <tr>
          <td><@todaysDate/></td>
          <td align="right" nowrap="nowrap">Welcome <@s.property value='%{#session.user_name}'/> | <a href="#">Logout</a></td>
        </tr>
      </table>
    </div>
  </#if>
</#macro>

<#-- These are all of the Dojo require statements the application needs -->
<#macro dojoRequire>
<script type="text/javascript">
  dojo.require("dojo.parser");
  dojo.require("dojo.currency");
  dojo.require("dojo.cookie");
  dojo.require("dijit.form.ValidationTextBox");
  dojo.require("dijit.form.NumberTextBox");
  dojo.require("dijit.form.CurrencyTextBox");
  dojo.require("dijit.form.Form");
  dojo.require("dijit.form.Button");
  dojo.require("dijit.form.Select");
  dojo.require("dijit.form.SimpleTextarea");
  dojo.require("dojo.data.ItemFileReadStore");
  dojo.require("dojo.data.ItemFileWriteStore");
  dojo.require("dojox.data.QueryReadStore");
  dojo.require("dijit.form.DateTextBox");
  dojo.require("dijit.form.FilteringSelect");
  //dojo.require("dijit.layout.AccordionContainer");
  dojo.require("dijit.layout.StackContainer");
  dojo.require("dijit.layout.ContentPane");
  dojo.require("dijit.layout.TabContainer");
  dojo.require("dijit.Dialog");
  dojo.require("dijit.TooltipDialog");
  dojo.require("dijit.ProgressBar");
  //dojo.require("dijit.MenuBar");
  //dojo.require("dijit.PopupMenuBarItem");
  dojo.require("dijit.form.CheckBox");
  dojo.require("dojox.lang.aspect");
  dojo.require("dojox.grid.DataGrid");
  dojo.require("dojox.grid.EnhancedGrid");
  dojo.require("dojox.grid.enhanced.plugins.IndirectSelection");
  dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
  dojo.require("dojox.grid.enhanced.plugins.Filter");
  dojo.require("dojo.i18n");
  dojo.require("dojo.io.iframe");      
  dojo.require("dojox.validate");
  dojo.require("dojox.html.entities");
  dojo.require("dojo.dnd.Source");
</script>
</#macro>



<#-- This is everything we need to include Dojo JavaScript in the page -->
<#macro dojo>
<script type="text/javascript" src="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dojo/dojo.js" djConfig="parseOnLoad:true"></script>
<script type="text/javascript" src="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dojo/elearn.js"></script>
<@dojoRequire/>
</#macro>



<#-- These are all the style sheets we need in the application -->
<#macro styleSheets>
<link href="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dojo/resources/dojo.css" rel="stylesheet" type="text/css" />
<link href="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dijit/themes/claro/claro.css" rel="stylesheet" type="text/css" />
<link href="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dijit/themes/claro/document.css" rel="stylesheet" type="text/css" />
<link href="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dojox/grid/resources/claroGrid.css" rel="stylesheet" type="text/css" />
<link href="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dojox/grid/enhanced/resources/claro/EnhancedGrid.css" rel="stylesheet" type="text/css" />
<link href="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css" rel="stylesheet" type="text/css" />
<link href="<@fullPath/>/css/main.css" rel="stylesheet" type="text/css" />
</#macro>



<#-- This is everything we need to include elearn Javascript files into the application -->
<#macro elearnJavaScript>
<script type="text/javascript" src="<@fullPath/>/js/final-block.js"></script>
<script type="text/javascript" src="<@fullPath/>/js/cci.js"></script>
<script type="text/javascript" src="<@fullPath/>/js/elearnGrid.js"></script>
<script type="text/javascript" src="<@fullPath/>/js/asset.js"></script>
<script type="text/javascript" src="<@fullPath/>/js/user.js"></script>
<script type="text/javascript" src="<@fullPath/>/js/employee.js"></script>
<script type="text/javascript" src="<@fullPath/>/js/elearnscript.js"></script>
<script type="text/javascript">
    dojo.addOnLoad(cci.pageLoaded);
    if (${namespace}.onLoad) {
        dojo.addOnLoad(dojo.hitch(${namespace}, ${namespace}.onLoad));
    }
</script>
</#macro>



<#-- This is the JavaScript that must run when the page loads to collapse the left-side menu 

You must create a new accordion object before initialization. The parameter taken by accordion.slider is the variable 
name used for the object. The object.init function takes 5 parameters: the id of the accordion "ul", the header element 
tag, whether the panels should be expandable independently (optional), the index of the initially expanded section 
(optional) and the class for the active header (optional). 

-->
<#macro initializeLeftMenuJS>
<script type="text/javascript">

var topLevelExpand = -1;
var employerExpand = -1;
var adminExpand = -1;
var highlightMenuSelector = null;

<#switch namespace>
  <#case "employer.addcontact">
    topLevelExpand = 0;
    highlightMenuSelector = '#employerContactsMenu';
    
    <#if user_session.staffFlag == 'T'>
      employerExpand = 0;    
    </#if>
    <#break>

  <#case "employer.contacts">
    topLevelExpand = 0;
    highlightMenuSelector = '#employerContactsMenu';
    
    <#if user_session.staffFlag == 'T'>
      employerExpand = 0;
    </#if>
    <#break>
    
  <#case "admin.security">
    topLevelExpand = 3;
    highlightMenuSelector = '#adminSecurityMenu';
    <#break>
    
  <#case "funds.trf">
    topLevelExpand = 3;
    adminExpand = 1;
    highlightMenuSelector = '#adminFundsMenu';
    <#break>  
</#switch>

var topLevelAccordion=new TINY.accordion.slider("topLevelAccordion");
topLevelAccordion.init("topLevelAccordion","h3",true,topLevelExpand,"selected");

var nestedAdminAccordion=new TINY.accordion.slider("nestedAdminAccordion");
nestedAdminAccordion.init("nestedAdminAccordion","h3",true,adminExpand,"acc-selected");

if (highlightMenuSelector) dojo.query(highlightMenuSelector).style('color', 'FFFF99');

</script>
</#macro>



<#-- This is the HTML for the left-side menu -->
<#macro leftMenuHTML>
<#assign security=JspTaglibs["/WEB-INF/security.tld"]/>
<div id="SideNavMainContainer">
	<#-- <div id="SideNavMainTop">
		<img src="<@fullPath/>/images/sidenavmain_top.jpg" style="position: absolute;" width="170" height="11" alt="Main Side Nav Top Bar" />
	</div> -->
    <div id="SideNavMain">
    	<p class="nav_main tspace"><a class="acc-link" href="/whereis/elearn/employeeInitAction">Home</a></p>
    	<ul class="topLevelAccordion" id="topLevelAccordion">
    	  <li class="nav_main" style="margin-top:4px; padding-left: 8px;"><h3>Administration</h3>
				<div class="acc-section">
       				<div class="acc-content">
       					<ul class="nestedAccordion" id="nestedAdminAccordion" style="width: 150px">
							<li class="nav_main last_nav"><h3>Assets</h3>
	             	   			<div class="acc-section">
	                          		<div class="acc-content">
	             	   					<p class="nav_sub"><a class="acc-link-sub" href="/whereis/elearn/addAsset">Add Asset</a></p>
	             	   				</div>
	             	   			</div>
	             	   		</li>
	             	   		<li class="nav_main last_nav"><h3>Users</h3>
	             	   			<div class="acc-section">
	                          		<div class="acc-content">
	             	   					<p class="nav_sub"><a class="acc-link-sub" href="/whereis/elearn/addUser">Add User</a></p>
	             	   				</div>
	             	   			</div>
	             	   		</li>
                		</ul>
       				</div>
          		</div>
      		</li>
        </ul>
     </div>
    
    <#-- <img src="<@fullPath/>/images/sidenavmain_bottom.jpg" style="position: absolute;" width="170" height="11" alt="Main Side Nav Bottom Bar" />	 --> 	

</div> <!-- SideNavMainContainer -->

</#macro>



<#-- This is the HTML for the standard footer -->
<#macro footer footerId="FooterLeftMenu">
<div class="bodytext" id="${footerId}">
    <span class="bodytextsmall">&copy; 2011 CedarCrestone Inc. All Rights Reserved.</span>
 </div>
</#macro>


<#-- 
This is the HTML for wrapping content with a border with rounded corners. Set "forTabs" to true only when using
server side tabs. You can make the background grey by setting byGrey to true. 
-->
<#macro roundedCornerBorder forTabs=false bgGrey=false>

<@roundedCornerBorderTop forTabs=forTabs bgGrey=bgGrey/>
	<#nested/>
<@roundedCornerBorderBottom bgGrey=bgGrey/>

</#macro>


<#--Used by the 'roundedCornerBorder' macro. Puts the top rounded corner border into the page.--> 
<#macro roundedCornerBorderTop forTabs=false bgGrey=false>

<#if forTabs>
	<#local topLeft="top-left-tab" topRight="top-right-tab" inside="inside">	
<#elseif bgGrey>
	<#local topLeft="top-left-grey" topRight="top-right-grey" inside="inside-grey">
<#else>
	<#local topLeft="top-left" topRight="top-right" inside="inside">
</#if>

<div class="${topLeft}"></div><div class="${topRight}"></div><div class="${inside}">
</#macro>



<#--Used by the 'roundedCornerBorder' macro. Puts the bottom rounded corner border into the page. --> 
<#macro roundedCornerBorderBottom bgGrey=false>

<#if bgGrey>
	<#local bottomLeft="bottom-left-grey" bottomRight="bottom-right-grey">
<#else>
	<#local bottomLeft="bottom-left" bottomRight="bottom-right">
</#if>

</div><div class="${bottomLeft}"></div><div class="${bottomRight}"></div>
</#macro>


<#-- HTML for an inactive server side tab -->
<#macro inactiveTabHeader title onClick>
<div class="dijitTab" onclick="window.location.href='${onClick}';">
  <div class="dijitTabInnerDiv">
    <div class="dijitTabContent">
      <div>
		<img class="dijitIcon dijitTabButtonIcon"src="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dojo/resources/blank.gif">
	    <span class="tabLabel">${title}</span>
      </div>
    </div>
  </div>
</div>
</#macro>



<#-- HTML for an active server side tab -->
<#macro activeTabHeader title>
<div class="dijitTab dijitTabChecked dijitChecked dijitTabFocused dijitTabCheckedFocused dijitCheckedFocused dijitFocused">
  <div class="dijitTabInnerDiv">
    <div class="dijitTabContent">
      <div>
		<img class="dijitIcon dijitTabButtonIcon" src="${request.scheme}://${request.serverName}:${request.serverPort?c}/elearn-dojo-1-7-2-xdomain/dojo/resources/blank.gif">
		<span class="tabLabel" style="-moz-user-select: none;">${title}</span>
      </div>
    </div>
  </div>
</div>
</#macro>



<#-- HTML for a server side tab container. Server side tabs are tabs that refresh the page when clicked. -->
<#macro tabLinksContainer tabs width="710px;">
<div class="dijitTabListWrapper dijitTabContainerTopNone dijitAlignClient" style="height: 28px; width: ${width}">
  <div class="nowrapTabStrip dijitTabContainerTop-tabs dijitTabNoLayout">
	<#list tabs as tab>
		<#if tab.active>
			<@activeTabHeader title=tab.title/>
		<#else>
			<@inactiveTabHeader title=tab.title onClick=tab.onClick/>
		</#if>
	</#list>
  </div>
</div>
</#macro>



<#-- HTML for explanation of using the asterisk to denote required fields -->
<#macro requiredMessage>

<div class="infoMessage">Fields marked with <span class="required">*</span> are required.</div>

</#macro>



<#-- HTML for Drag & Drop text with big arrows -->
<#macro dndMessage>

<span class="bigText">&larr;</span> Drag & Drop <span class="bigText">&rarr;</span>

</#macro>



<#-- HTML for "Save Successful" message that appears to the right of the page title-->
<#macro pageTitleMessage>

<table width="100%" cellspacing="0" cellpadding="0">
   <tr>
       <td>
           <div class="headertext2">${pageTitle!"Missing Page Title!"}</div>
       </td>
       <td align="right" valign="top">
           <div id="pageTitleMessage" class="fadable fadeGreen" style="width:250px;">Save Successful</div>
       </td>
   </tr>
</table>

</#macro>