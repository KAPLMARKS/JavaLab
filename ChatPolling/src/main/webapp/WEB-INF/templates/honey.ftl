<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign In</title>
</head>
<style type="text/css">
    A {
        text-decoration: none;
    }
</style>
<body>
    <#if check??>
    ${check}
</#if>
<#list honey as honeys>
    <tr>
        <td>${honeys.id}.</td>
        <td>Name: ${honeys.name}.</td>
        <td>Description: ${honeys.description}</td>
        <td>Price: ${honeys.cost} RUB. <br></td>
    </tr>
    <button><a href="http://localhost:8080/honey/add"class="nubex">Add Honey</a></button>
</#list>
</body>