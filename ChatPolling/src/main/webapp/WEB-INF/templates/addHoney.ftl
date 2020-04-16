<#if check??>
    ${check}
</#if>
<form id="honeyForm" action="/honey/add" method="post">
    <input id="name" type="text" name="name" placeholder="Product name">
    <input id="description" type="text" name="description" placeholder="Description">
    <input id="cost" type="number" name="cost" placeholder="Cost">
    <input type="submit" name="addHoney" id="addHoney" class="form-submit" value="Add honey"/>
</form>