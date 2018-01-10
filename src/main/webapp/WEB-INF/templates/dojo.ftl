<#-- 

This file contains templates for Dojo Widgets (aka Dijits). These templates should be used
instead of directly declaring the Dijits in the page since it allows us to have a single
point of change for configuring Dijits.

-->

<#--
Standard Dialog

Benefits of use:
1) namespace applied to jsId
2) single point of change

-->
<#macro dialog id title onHide="">

<div dojoType="dijit.Dialog" jsId="${.globals.namespace}.${id}" id="${id}" title="${title}" <#if onHide != "">onHide="${.globals.namespace}.${onHide}"</#if>>
    <#nested/>
</div>

</#macro>


<#-- 
Standard DateTextBox.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) trim whitespace by default
4) sets standard date pattern of MM/dd/yyyy
5) standard placeholder for display mm/dd/yyyy
6) standard maxLength for Dates
7) standard formatter applied as user types into the box
8) easy to set the minimum valid date to today's date by setting the 'todayOrLater' attribute to true

Example: <@dojo.dateTextBox name="theDate" />
-->
<#macro dateTextBox name required="false" style="width: 100px;" onChange="" displayedValue="" customAttributes="" todayOrLater=false>

<input dojoType="dijit.form.DateTextBox" constraints="{datePattern:'MM/dd/yyyy'<#if todayOrLater>,min:new Date('<@todaysDate format="MM/dd/yyyy"/>')</#if>}" placeHolder="mm/dd/yyyy" trim="true" 
style="${style}" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" maxLength="10" required="${required}"
onKeyUp="this.set('displayedValue', cci.format.date(this.get('displayedValue')));" 
<#if onChange != "">onChange="${.globals.namespace}.${onChange}"</#if> <#if displayedValue != "">displayedValue="${displayedValue}"</#if> ${customAttributes}/>

</#macro>


<#-- 
Standard Radio Button.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) sets standard for jsId and id - both will have the value appended to them in order to distinguish them

Example: <@dojo.radio name="myRadio" value="Y" />
-->
<#macro radio name value required="false" style="" onChange="" checked="false" customAttributes="">

<input dojoType="dijit.form.RadioButton" type="radio" jsId="${.globals.namespace}.${name}_${value}" id="${name}_${value}" name="${name}" value="${value}" 
    required="${required}" <#if style != ""> style="${style}"</#if><#if onChange != "">onChange="${.globals.namespace}.${onChange}"</#if> 
    <#if checked == "true">checked="true"</#if> ${customAttributes} />

</#macro>


<#-- 
Standard Check Box.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) submitted value is "true" by default instead of Dojo's default of "on"

Example: <@dojo.checkBox name="myCheck" />
-->
<#macro checkBox name value="true" required="false" style="" onChange="" checked="false" customAttributes="">

<input dojoType="dijit.form.CheckBox" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" value="${value}" <#if checked == "true">checked="true"</#if>
required="${required}" <#if style != ""> style="${style}"</#if><#if onChange != "">onChange="${.globals.namespace}.${onChange}"</#if> ${customAttributes} />

</#macro>


<#-- 
Standard Filtering Select Box.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) trim whitespace by default
4) standard placeholder for display "please select"
5) can be backed by HTML options or a Dojo data store.
6) if backed by a Dojo data store, will automatically prepend the namespace of the FilteringSelect to the store attribute

Example: <@dojo.select name="names">
                <option value="Adam">Adam</option>
                <option value="Anquan">Anquan</option>
         </@dojo.select>
         
Example 2: <@dojo.select name="myStoreSelect" store="suStore" />
-->
<#macro select name required="false" style="" onChange="" value="" store="" customAttributes="">

<#-- You must set value="" when you have HTML options and not a store in order to show the placeHolder -->
<select dojoType="dijit.form.FilteringSelect" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" 
placeHolder="please select" required="${required}" trim="true" value="${value}" ${customAttributes}
<#if store != ""> store="${.globals.namespace}.${store}"</#if>
<#if style != ""> style="${style}"</#if><#if onChange != ""> onChange="${.globals.namespace}.${onChange}"</#if>>
    <#nested/>
</select>

</#macro>


<#--
Standard drop-down select box for the type of search matching that should be done for a user's form field entry
-->
<#macro querySelect name style="width: 110px;">
    <@select name="${name}" value="1" style="${style}">
        <option value="1">Begins with</option>
        <option value="2">Contains</option>
        <option value="3">Equal to</option>
    </@select>

</#macro>


<#-- 
Standard text box without need for validation.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) better than a regular HTML text field because only Dijits will be affected by the functions of the containing form,
   such as form.reset()
4) forces setting maxLength, which is a good practice to prevent allowing more data than the database can handle
-->
<#macro textBox name maxLength customAttributes="" type="text">

<input dojoType="dijit.form.TextBox" type="${type}" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" maxLength="${maxLength}" ${customAttributes} />

</#macro>


<#-- 
Standard hidden text box.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) better than a regular HTML hidden field because only Dijits will be affected by the functions of the containing form,
   such as form.reset()
-->
<#macro hiddenTextBox name>

<input dojoType="dijit.form.TextBox" type="hidden" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" />

</#macro>


<#-- 
Standard generic Validation Text Box.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) trim whitespace by default
4) forces setting maxLength, which is a good practice to prevent allowing more data than the database can handle

Example:<@dojo.validationTextBox name="myText" maxLength="100" />
-->
<#macro validationTextBox name maxLength required="false" style="" onChange="" value="" validator="" invalidMessage="" customAttributes="">

<input dojoType="dijit.form.ValidationTextBox" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" maxLength='${maxLength}' 
required="${required}" trim="true" value="${value}" ${customAttributes} <#if validator != ""> validator="${validator}"</#if>
<#if invalidMessage != ""> invalidMessage="${invalidMessage}"</#if>
<#if style != ""> style="${style}"</#if><#if onChange != ""> onChange="${.globals.namespace}.${onChange}"</#if> />

</#macro>

<#-- 
Standard generic Number Text Box.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) trim whitespace by default
4) forces setting maxLength, which is a good practice to prevent allowing more data than the database can handle
5) if decimalPlaces attribute is used, standard formatter applied as user types into the box


Example:<@dojo.numberTextBox name="myText" maxLength="100" />
-->
<#macro numberTextBox name maxLength required="false" style="" onChange="" onBlur="" value="" constraints="" invalidMessage="" customAttributes="" decimalPlaces="">

<input dojoType="dijit.form.NumberTextBox" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" maxLength='${maxLength}'
required="${required}" trim="true" value="${value}" ${customAttributes} <#if constraints != ""> constraints="${constraints}"</#if>
<#if invalidMessage != ""> invalidMessage="${invalidMessage}"</#if> 
<#if decimalPlaces != ""> onKeyUp="this.set('displayedValue', cci.format.decimal( this.get('displayedValue'), ${decimalPlaces}))"</#if>
<#if style != ""> style="${style}"</#if><#if onChange != ""> onChange="${.globals.namespace}.${onChange}"</#if> 
onBlur="<#if decimalPlaces != "">this.set('value', cci.getFixedDecimals(this.get('value'),${decimalPlaces}));</#if><#if onBlur != "">${.globals.namespace}.${onBlur}</#if>" />

</#macro>


<#--
Standard Validation Text Box for email addresses.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) trim whitespace by default
4) sets standard validating regular expression for email addresses
5) standard error message for invalid email addresses
6) forces setting maxLength, which is a good practice to prevent allowing more data than the database can handle

Example: <@dojo.emailTextBox name="email" maxLength="150" />
-->
<#macro emailTextBox name maxLength required="false" style="" onChange="" value="" customAttributes="">

<input dojoType="dijit.form.ValidationTextBox" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" maxLength='${maxLength}' 
required="${required}" trim="true" value="${value}" regexpgen="dojox.validate.regexp.emailAddress" 
invalidMessage="Invalid Email Address." ${customAttributes} 
<#if style != "">style="${style}"</#if><#if onChange != "">onChange="${.globals.namespace}.${onChange}"</#if> />

</#macro>


<#--
Standard Validation Text Box for phone numbers.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) trim whitespace by default
4) sets standard validating regular expression for phone numbers
6) standard error message for invalid phone numbers
7) standard maxLength for phone numbers
8) standard placeholder for display (XXX) XXX-XXXX
9) standard formatter applied as user types into the box

Example: <@dojo.phoneTextBox name="phone" />
-->
<#macro phoneTextBox name required="false" style="" onChange="" value="" customAttributes="">

<input dojoType="dijit.form.ValidationTextBox" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" maxLength='14' 
required="${required}" trim="true" value="${value}" placeHolder="(XXX) XXX-XXXX" invalidMessage="Invalid Phone Number. (XXX) XXX-XXXX"
onKeyUp="this.set('value', cci.format.phone( this.get('value')))" regExp="^\([\d]{3}\) [\d]{3}\-[\d]{4}$" ${customAttributes}
<#if style != "">style="${style}"</#if><#if onChange != "">onChange="${.globals.namespace}.${onChange}"</#if> />

</#macro>


<#--
Standard Validation Text Box for submission unit ID.

If forQuery attribute is set to true, the validation rules are relaxed a bit to support a "begins with" query

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) trim whitespace by default
4) sets standard validating regular expression for submission unit ID
6) standard error message for invalid submission unit ID
7) standard maxLength for submission unit ID
9) standard formatter applied as user types into the box

Example: <@dojo.submissionUnitIdTextBox name="suId" />
-->
<#macro submissionUnitIdTextBox name required="false" style="" onChange="" value="" forQuery=false customAttributes="">

<#if forQuery>
    <#local regExp = "^[\\d]{0,4}(\\-)?([\\d]{0,3})?$">
<#else>
    <#local regExp = "^[\\d]{4}\\-[\\d]{3}$">
</#if>

<input dojoType="dijit.form.ValidationTextBox" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" maxLength='8' 
required="${required}" trim="true" value="${value}" invalidMessage="Invalid Submission Unit ID"
onKeyUp="this.set('value', cci.format.submissionUnit( this.get('value')))" regExp="${regExp}" ${customAttributes}
<#if style != "">style="${style}"</#if><#if onChange != "">onChange="${.globals.namespace}.${onChange}"</#if> />

</#macro>


<#--
Standard Validation Text Box for social security number.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) trim whitespace by default
4) sets standard validating regular expression for SSN
6) standard error message for invalid SSN
7) standard maxLength for SSN
8) standard placeholder for display XXX-XX-XXXX
9) standard formatter applied as user types into the box

Example: <@dojo.ssnTextBox name="ssn" />
-->
<#macro ssnTextBox name required="false" style="" onChange="" value="" customAttributes="">

<input dojoType="dijit.form.ValidationTextBox" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" maxLength='11' 
required="${required}" trim="true" value="${value}" placeHolder="XXX-XX-XXXX" invalidMessage="Invalid SSN"
onKeyUp="this.set('value', cci.format.ssn( this.get('value')))" regExp="^[\d]{3}\-[\d]{2}\-[\d]{4}$" ${customAttributes}
<#if style != "">style="${style}"</#if><#if onChange != "">onChange="${.globals.namespace}.${onChange}"</#if> />

</#macro>


<#--
Standard Validation Text Box for zip code.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) trim whitespace by default
4) sets standard validating regular expression for zip
6) standard error message for invalid zip
7) standard maxLength for zip
8) standard formatter applied as user types into the box

Example: <@dojo.zipTextBox name="zip" />
-->
<#macro zipTextBox name required="false" style="" onChange="" value="" customAttributes="">

<input dojoType="dijit.form.ValidationTextBox" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" maxLength='10' 
required="${required}" trim="true" value="${value}" invalidMessage="Invalid Zip Code."
onKeyUp="this.set('value', cci.format.zip( this.get('value')))" regExp="^\d{5}([\-]\d{4})?$" ${customAttributes}
<#if style != "">style="${style}"</#if><#if onChange != "">onChange="${.globals.namespace}.${onChange}"</#if> />

</#macro>


<#--
Standard Dojo StackContainer.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) standard for doLayout = false

-->
<#macro stackContainer id>

<div jsId="${.globals.namespace}.${id}" id="${id}" dojoType="dijit.layout.StackContainer" doLayout="false">

    <#nested/>
</div>

</#macro>



<#-- 
Standard Dojo TabContainer. 

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) standard for doLayout = false
4) prompts user if he tries to change the current tab but has unsaved form changes

-->
<#macro tabContainer id formChangeObserver=true customAttributes="" style="">

<div jsId="${.globals.namespace}.${id}" id="${id}" dojoType="dijit.layout.TabContainer" style="${style}" doLayout="false" ${customAttributes}>
<#if formChangeObserver>
    <script type="dojo/method">
        cci.formObserver.observeTabContainer(${.globals.namespace}.${id}, ${.globals.namespace});
    </script>
</#if>

    <#nested/>
</div>

</#macro>


<#-- 
Standard Dojo ContentPane. 

Benefits of use:
1) namespace applied to jsId
2) sets preventCache to true if the content is lazy loaded (href attribute is set) instead of inlined in the div
3) If the eval attribute is set to true, searches for divs inside its nested content that have a class of 'onLoadJs' and runs a JavaScript 
   eval on the innerHTML of the matching divs. This feature allows us to simulate onLoad events for pages that are lazy-loaded 
   (downloaded when a tab with an href is clicked).
4) Automatically fixes lazy loading bug for tabs with href set - Bug Description (Dojo version 1.5.0)- If you change the href of
   a ContentPane that is not currently showing, it is not supposed to actually make a new request to the server to reload the 
   content until the tab is shown. There appears to be a bug in Dojo that if a tab has been shown, but is no longer the currently 
   shown tab, changing its href will cause the tab to reload itself. If the tab reloads itself while hidden, Dijits like the grid 
   will not render properly if the user clicks to view the tab again. The fix for this bug is to set the 'open' property of the 
   ContentPane to false when the ContentPane tab is hidden. 

Example: <@dojo.contentPane id="cp4" href="gridFragment" title="Lazy Loaded Tab With Grid"/>

ContentPane Example Event Registration:
<div class="onLoadJs" style="display:none;">alert('content pane lazy-loaded page onLoad event'); </div>
-->
<#macro contentPane id href="" title="Title of Pane" onLoad="" onShow="" onHide="" style="" selected=false customAttributes="" eval=false>
<div jsId="${.globals.namespace}.${id}" id="${id}" dojoType="dijit.layout.ContentPane" title="${title}" ${customAttributes}
  selected="${selected?string}" <#if style != ""> style="${style}"</#if> onShow="this.set('open',true);<#if onShow != "">${.globals.namespace}.${onShow}</#if>"
  <#if href != ""> href="${href}" preventCache="true" onHide="this.set('open',false);<#if onHide != "">${.globals.namespace}.${onHide}"</#if>"
  onLoad="<#if eval>dojo.query('.onLoadJs', '${id}').forEach(function(item, idx, arr){eval(item.innerHTML);});</#if>${onLoad}"
  <#else>
    <#if onHide != ""> onHide="${.globals.namespace}.${onHide}"</#if>
  </#if>   
>
    <#nested/>
</div>
</#macro>


<#--  
Standard Dojo form. 

Benefits of use:
1) Automatically registers an on change listener for the form to prevent the user from losing unsaved changes when he tries
   to leave the form area. 
2) If the 'ajaxSubmit' parameter is set to true, this form will only submit through AJAX calls and will not cause a full page load
3) Supports calling a handler function when the user hits the Enter button. 
4) When defaultButton attribute is set to a dijit or a function that returns a dijit, the default button will receive the focus when the 
   user hits the Enter key. Setting the focus is important because it ensures that the onChange event for all form fields has fired before 
   the event handler for the Enter key fires.

If present, the handler function must be in the same JavaScript namespace as the form and the function must be 
named 'formName_onDefaultButtonClick' where formName is the name value passed to this macro. If the handler returns 
true, the form will be submitted (assuming the 'ajaxSubmit' parameter was not set to true). If it returns false, the form will not be submitted.
-->
<#macro form name defaultButton action="#" customAttributes="" ajaxSubmit=false onSubmit="" formObserver=true>

<#local defaultHandler = "${.globals.namespace}.${name}_onDefaultButtonClick">

<form dojoType="dijit.form.Form" method="POST" jsId="${.globals.namespace}.${name}" id="${name}" name="${name}" action="${action}" ${customAttributes}
      onSubmit="<#if onSubmit != "">${.globals.namespace}.${onSubmit}</#if><#if ajaxSubmit>;return false;</#if>">
        <script type="dojo/method">

            var thiz = this;

            <#if formObserver>
                dojo.connect(this, "validate", cci.formObserver, cci.formObserver.hidePageInfoNode);

                var formStarted = function() { 
                    cci.formObserver.registerFormChangeListener('${name}');
                }

                <#--
                register the form observer once the form and all its children have been
                created and added to the page
                -->
                dojo.connect(this, "startup", formStarted);
            </#if>

            var formKeyPress = function(evt) {

                <#--Don't intercept the Enter key when a button has the focus-->
                if (evt.keyCode == dojo.keys.ENTER && !dojo.hasClass(document.activeElement, 'dijitButtonContents')) {
                    dojo.stopEvent(evt);

                        var focusDijit = ${.globals.namespace}.${defaultButton};
                        if (dojo.isFunction(focusDijit)) {
                            focusDijit = ${.globals.namespace}.${defaultButton}();
                        }
                        cci.focusButton(focusDijit);

                    if ( !${defaultHandler} || ${defaultHandler}() ) {
                        thiz.submit();
                    }
                } 
            }

            dojo.connect(this, "onKeyPress", formKeyPress);
        </script>
   
   <#nested/>
        
</form>

</#macro>



<#-- 
Standard Button.

Benefits of use:
1) namespace applied to onClick
2) single point of change
3) supports "invisible" Boolean property

Example: <@dojo.button label="Add Contact" onClick="onAddContact();" />

-->
<#macro button label onClick id="" invisible=false style="" class="" customAttributes="">

<button dojoType="dijit.form.Button" onClick="cci.focusButton(this);${.globals.namespace}.${onClick}" <#if style != ""> style="${style}"</#if> ${customAttributes}
<#if id != ""> jsId="${.globals.namespace}.${id}" id="${id}" </#if><#if class != "" || invisible> class="${class} <#if invisible>invisible</#if>" </#if>
>${label}</button>

</#macro>


<#--
Standard Drag and Drop Source.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) automatically prevents dragging and dropping an item onto its original parent container
4) marks the form as changed for form observer when an item is dropped onto the source
5) deselects items on-drop
-->
<#macro dnd id disableSelfDrop=true style="">

<div dojoType="dojo.dnd.Source" jsId="${.globals.namespace}.${id}" id="${id}" class="container" style="border: 1px solid #BCBCBC;${style}">


<script type="dojo/method">

    dojo.connect(this, "onDrop", cci.formObserver, cci.formObserver.markFormAsChanged);
    dojo.connect(this, "onDrop", this, function(){ this.selectNone();});

    <#if disableSelfDrop>
        var thiz = this;
        var aop = dojox.lang.aspect;

        aop.advise(this, ["checkAcceptance"], {
            around: function(source, nodes){

                if (thiz == source) return false;
                return aop.proceed(source, nodes);                 
            }
        });
    </#if>
</script>


    <#nested/>
</div>

</#macro>


<#--
Standard Dojo data store.

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) standard typeMap for converting from JSON to Objects is configured
4) prevents browsers from caching the data returned from the url

-->
<#macro store id url fetchType="local" type="read" customAttributes="">

<#if fetchType == "remote">
    <#local dojoType = "dojox.data.QueryReadStore">
<#elseif type == "read">
    <#local dojoType = "dojo.data.ItemFileReadStore">
<#else>
    <#local dojoType = "dojo.data.ItemFileWriteStore">
</#if>

<span jsId="${.globals.namespace}.${id}" id="${id}" dojoType="${dojoType}" urlPreventCache="true"
      url="${url}" typeMap="cci.storeTypeMap" ${customAttributes}></span>

</#macro>


<#-- 
Standard HTML for Dojo grids. 

Benefits of use:
1) namespace applied to jsId
2) single point of change
3) auto-size 
4) pagination on-the-fly and only when needed 
5) standard default attribute values
6) first row selected automatically when the grid is loaded. Set selectFirstOnLoad=false to disable this feature
7) column shown as sorted when grid is first shown (if showSortingCol attribute is set)
8) easy way to register a handler that is executed when a grid row is selected. 
9) prompts user if he tries to change the currently selected row (by mouse or keyboard or sorting) but has unsaved form changes

Parameters:
    id                  - String    - required - the DOM id of the grid
    store               - String    - required - the JavaScript identifier of the store that contains the model for the grid
    fetchSize           - Integer   - optional - the number of records that are pulled from the store per-read. Defaults to 30
    rowsVisible         - Integer   - optional - the maximum number of rows to display at one time. Defaults to 10
    customAttributes    - String    - optional - any attributes you want to specify on the grid that aren't already
                                                 supplied by this macro
    registerOnLoad      - Boolean   - optional - sets up pagination and sizing when the grid loads up. Defaults to true. This 
                                                 should almost always be true.
    rowHeight           - Integer   - optional - this value tells the Dojo grid what the height of each row is. This value is dependent
                                                 on the font of the text that is displayed in the grid. This value should almost never
                                                 be changed. Defaults to 16
    selectFirstOnLoad   - Boolean   - optional - set to true if the first row of the grid should be selected when the grid is first
                                                 loaded or false if you do not want this behavior. Defaults to true.
    showSortingCol      - Integer   - optional - set to a column number (1-based) that you want to show as being sorted (the grid will 
                                                 display) an arrow by the column name. This value does not make the grid attempt to sort
                                                 the data, it just shows the sorting arrow. The reason is that we assume that the data
                                                 has already been sorted on the server side for the initial page load. Defaults to
                                                 0, which means that no column will be shown as sorted.
    selectionMode       - String    - optional - set whether the user can select multiple rows at a time, one row at a time, or none. Defaults to
                                                 "single." Valid values are:
                                                    none     - No row selection.
                                                    single   - Only single row selection.
                                                    multiple - Multiple explicit row selection. A single click selects a row a second single 
                                                               click deselects the row.
                                                    extended - Multiple row selection including ranges (default).
    onSelect            - String    - optional - the name of a function that should be executed when a row is selected in the grid. The function
                                                 must be in the same JavaScript namespace as the grid
    formChangeObserver  - Boolean   - optional - set this to false only when you want to disable the confirmation message that warns the user
                                                 about unsaved changes when he changes the selected grid row or sorts the grid 
    indirectSelection   - Boolean   - optional - show the grid show radio buttons or check boxes to show which row is selected. Defaults to true                                             
                                                    
                                                    
Example: 

<@dojo.grid id="contactGrid" store="suStore" rowsVisible=5 showSortingCol=4 onSelect="onContactSelect">
    <th field="firstName" width="22%" formatter="elearnGrid.format.noWrap">First Name</th>
    <th field="lastName" width="28%" formatter="elearnGrid.format.noWrap">Last Name</th>
    <th field="email" width="30%" formatter="elearnGrid.format.noWrap">Email</th>
    <th field="status" width="10%">Status</th>
    <th field="date" width="10%" formatter="elearnGrid.format.dateOnly">Date</th>
</@dojo.grid> 

<table jsId="${.globals.namespace}.${id}" id="${id}" dojoType="dojox.grid.EnhancedGrid" rowsPerPage="${fetchSize?string}" noDataMessage="${noDataMessage}" selectable="true"
   style="height: ${autoHeight}px;" selectionMode="${selectionMode}" plugins="{<#if indirectSelection>indirectSelection: {name:'&nbsp;',width:'40px'}, rowSelector: '0px', </#if>nestedSorting: true}"
  rowHeight="${rowHeight}" store="${.globals.namespace}.${store}" ${customAttributes}>
-->
<#macro grid id store fetchSize=30 rowsVisible=10 customAttributes="" registerOnLoad=true rowHeight=31 noDataMessage="No Data Available"
             selectFirstOnLoad=true showSortingCol=0 selectionMode="single" formChangeObserver=true onSelect="null" indirectSelection=false>

<#local autoHeight = ((rowsVisible+1)*(rowHeight+2)) + 4>
<#local registerGridOnLoad>
    elearnGrid.registerGridOnLoad({
        gridId:'${id}', 
        rowsVisible:${rowsVisible}, 
        selectFirstOnLoad:${selectFirstOnLoad?string}, 
        showSortingCol:${showSortingCol},
        <#if onSelect != "null">onSelect:${.globals.namespace}.${onSelect},</#if>        
        namespace:${.globals.namespace}
        });
</#local>

<table jsId="${.globals.namespace}.${id}" id="${id}" dojoType="dojox.grid.EnhancedGrid" rowsPerPage="${fetchSize?string}" noDataMessage="${noDataMessage}" selectable="true"
  selectionMode="${selectionMode}" plugins="{filter:true, <#if indirectSelection>indirectSelection: {name:'&nbsp;',width:'40px'}</#if>}"
  store="${.globals.namespace}.${store}" ${customAttributes} autoHeight="true">

    <script type="dojo/method">
        <#if formChangeObserver>
            cci.formObserver.observeGrid(${.globals.namespace}.${id}, ${.globals.namespace});
        </#if>
        ${registerGridOnLoad}
    </script>
    
     <thead>
       <tr>            
           <#nested/>
       </tr>
     </thead>
</table>
<@gridPaginationBar id=id rowsVisible=rowsVisible/>

</#macro>

<#-- Standard HTML for the pagination bar for Dojo grids. This should be called by grid macros, not by pages directly. -->
<#macro gridPaginationBar id rowsVisible>
<div class="elearnGridPagination" id="${id}Pagination" style="display:none;">
    <table cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td class="pageButton">
                <div class="first" onclick="elearnGrid.goToFirst('${id}', ${rowsVisible});" title="Go to first page"></div>
            </td>
            <td class="pageButton">
                <div class="prev" onclick="elearnGrid.goToPrev('${id}', ${rowsVisible});"  title="Go to previous page"></div>
            </td>
            <td>
                <div class="btnSeparator"></div>
            </td>
            <td class="pageButton">
                Page&nbsp;<span id="${id}gridPage">1</span>&nbsp;of&nbsp;<span id="${id}gridPageTotal">1</span>
            </td>
            <td>
                <div class="btnSeparator"></div>
            </td>
            <td class="pageButton">
                <div class="next" onclick="elearnGrid.goToNext('${id}', ${rowsVisible});"  title="Go to next page"></div>
            </td>
            <td class="pageButton">
                <div class="last" onclick="elearnGrid.goToLast('${id}', ${rowsVisible});"  title="Go to last page"></div>
            </td>
            <td>
                <div class="btnSeparator"></div>
            </td>
            <td class="pageButton">
                Displaying <span id="${id}start">0</span>&nbsp;to&nbsp;<span id="${id}end">0</span>&nbsp;of&nbsp;<span id="${id}RowCount">0</span>&nbsp;items
            </td>
        </tr>
    </table>
</div>
</#macro>
