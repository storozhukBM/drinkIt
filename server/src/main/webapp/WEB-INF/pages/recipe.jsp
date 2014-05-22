<%--
  Created by IntelliJ IDEA.
  User: bstorozhuk
  Date: 22.05.14
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title></title>
    <link href="http://cdn.kendostatic.com/2014.1.416/styles/kendo.common-bootstrap.min.css" rel="stylesheet">
    <link href="http://cdn.kendostatic.com/2014.1.416/styles/kendo.flat.min.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://cdn.kendostatic.com/2013.3.1119/js/kendo.web.min.js"></script>
</head>
<body class="k-content">
<div id="main" class="container" style="width: 1000px">
    <div class="col"
    <selection class="well">
        <h2 class="ra-well-title">Recipe creation form</h2>
        <form:form method="POST" commandName="recipe"
                   action="add-recipe.html" enctype="multipart/form-data" role="form">
                <div class="form-group">
                    <label>Name: </label>
                    <form:input path="name" type="text" class="k-textbox"/>
                </div>

                <div class="form-group">
                    <label>Description:</label>
                    <form:textarea path="description" class="k-textbox"/>
                </div>

                <div class="form-group">
                    <label>CocktailType:</label>
                    <form:select id="cocktailType" class="drop_down_menu" path="cocktailType"
                                     items="${cocktailTypes}" itemLabel="name" itemValue="id"/>
                </div>
                                     items="${cocktailTypes}" itemLabel="name" itemValue="id"/>
                </div>

                <div class="form-group">
                    <label>Option:</label>
                    <form:select id="options" multiple="multiple" class="multiselect_menu" path="options"
                                     items="${options}" itemLabel="name" itemValue="id"/>
                </div>

                <div class="form-group">
                    <label>Image:</label>
                    <input type="file" name="image" path="image">
                </div>

                <div class="form-group">
                    <label>Thumbnail:</label>
                    <input type="file" name="thumbnail" path="thumbnail">

                </div>

                <input type="submit" name="Save"/>
        </form:form>
    </selection>
</div>

<script>
    $(".drop_down_menu").kendoDropDownList();
    $(".multiselect_menu").kendoMultiSelect().data("kendoMultiSelect");
</script>

</body>
</html>
