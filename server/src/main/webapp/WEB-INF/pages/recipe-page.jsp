<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel='stylesheet' href='http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/css/bootstrap-rtl.css' type="text/css">
    <link href="http://cdn.kendostatic.com/2014.1.416/styles/kendo.flat.min.css" rel="stylesheet">
    <link href="http://cdn.kendostatic.com/2014.1.416/styles/kendo.common-bootstrap.min.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://cdn.kendostatic.com/2013.3.1119/js/kendo.web.min.js"></script>
</head>
<body class="k-content">
<div class="container">
    <div class="page-header">
        <h1>Recipe
            <small>
            </small>
        </h1>
    </div>

    <div id="body">

        <h2 class="ra-well-title">Recipe creation form</h2>
        <form:form method="POST" commandName="recipeBuilder"
                   action="add-recipe.html" enctype="multipart/form-data" role="form">

            <div class="col-md-8">
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
            </div>

            <div class="col-md-4">
                <h4 class="ra-well-title"><button id="add_ingredient">Add ingredient</button></h4>
                    <div class="form-group">
                        <label>Ingredient:</label>
                        <form:select id="ingredient" class="drop_down_menu" path="ingredients" name="ingredients[]"
                                     items="${ingredients}" itemLabel="name" itemValue="id"/>
                    </div>
                    <div class="form-group">
                        <label>Quantity:</label>
                        <form:input class="numeric" name="quantities[]"
                                    path="quantities" type="number" value="10" min="0" max="1000"/>
                    </div>
            </div>
        </form:form>
    </div>

</div>

<script>
    $(".drop_down_menu").kendoDropDownList();
    $(".multiselect_menu").kendoMultiSelect().data("kendoMultiSelect");
    $(".numeric").kendoNumericTextBox({decimals: 1});
</script>

</body>
</html>