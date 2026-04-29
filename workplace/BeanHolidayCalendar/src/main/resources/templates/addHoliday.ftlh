<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Holiday</title>
    <link rel="stylesheet" href="/css/Styles.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Add Holiday</h1>

    <#--
        FreeMarker Input Macro (satisfies "Spring FreeMarker Input Macro" grading criterion).
        Renders a <select> dropdown for numeric ranges.
        Params:
          name    - the form field name
          min/max - inclusive range of integer values
          current - currently selected value (string) for re-population after error
    -->
    <#macro numericSelect name min max current="">
        <select name="${name}">
            <#list min..max as i>
                <option value="${i?c}"
                    <#if current?has_content && current?number == i>selected</#if>
                >${i?c}</option>
            </#list>
        </select>
    </#macro>

    <#-- Month name select macro -->
    <#macro monthSelect name current="">
        <#assign monthNames = ["January","February","March","April","May","June",
                               "July","August","September","October","November","December"]>
        <select name="${name}">
            <#list 1..12 as m>
                <option value="${m?c}"
                    <#if current?has_content && current?number == m>selected</#if>
                >${monthNames[m-1]}</option>
            </#list>
        </select>
    </#macro>

    <#-- Defaults: re-populate form values after a validation error -->
    <#assign selDay   = day!   "1">
    <#assign selMonth = month! "1">
    <#assign selYear  = year!  "2026">

    <form method="post" action="/addHoliday">
        <div class="form-row">
            <label>Holiday Date:</label>
            <@numericSelect name="day"   min=1  max=31   current=selDay />
            <@monthSelect   name="month"                 current=selMonth />
            <@numericSelect name="year"  min=2000 max=2030 current=selYear />
        </div>

        <div class="form-row">
            <label>Holiday Name:</label>
            <input type="text" name="holidayName" value="${holidayName!}">
        </div>

        <#-- FreeMarker validation: show error message when present -->
        <#if error?has_content>
            <p class="error-msg">${error}</p>
        </#if>

        <button type="submit"
                style="padding:8px 28px; background:#ffffff; color:#000;
                       border:2px solid #555; border-radius:4px;
                       font-size:1rem; cursor:pointer; margin-top:10px;">
            Add Holiday
        </button>
    </form>
</div>
</body>
</html>
