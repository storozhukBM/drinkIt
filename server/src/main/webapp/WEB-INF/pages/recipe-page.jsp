<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <c:if test="${not empty successMessage}">
            <div class="alert alert-info">${successMessage}</div>
        </c:if>
        <form:form method="POST"
                   action="add-recipe.html" enctype="multipart/form-data" role="form">

            <div class="col-md-8">
                <div class="form-group">
                    <label>Name: </label>
                    <form:input path="name" type="text" class="k-textbox"/>
                    <form:errors cssStyle="color: red" path="name" />
                </div>

                <div class="form-group">
                    <label>Description:</label>
                    <form:textarea path="description" class="k-textbox"/>
                    <form:errors cssStyle="color: red" path="description" />
                </div>

                <div class="form-group">
                    <label>CocktailType:</label>
                    <form:select id="cocktailType" class="drop_down_menu" path="cocktailType"
                                 items="${cocktailTypes}" itemLabel="name" itemValue="id"/>
                    <form:errors cssStyle="color: red" path="cocktailType" />
                </div>

                <div class="form-group">
                    <label>Option:</label>
                    <form:select id="options" multiple="multiple" class="multiselect_menu" path="options"
                                 items="${options}" itemLabel="name" itemValue="id"/>
                    <form:errors cssStyle="color: red" path="options" />
                </div>

                <div class="form-group">
                    <label>Image:</label>
                    <input type="file" name="image" path="image">
                    <form:errors cssStyle="color: red" path="image" />
                </div>

                <div class="form-group">
                    <label>Thumbnail:</label>
                    <input type="file" name="thumbnail" path="thumbnail">
                    <form:errors cssStyle="color: red" path="thumbnail" />
                </div>
                <input type="submit" name="Save"/>
            </div>

            <div class="col-md-4">
                <h4 class="ra-well-title"><a href="#" id="add_ingredient">Add ingredient</a></h4>
                    <div id="resizeable">
                        <span id="to_clone">
                            <div class="form-group">
                                <label>Ingredient:</label>
                                <form:select class="form-control" id="ingredient" path="ingredients" name="ingredients[]"
                                             items="${ingredients}" itemLabel="name" itemValue="id"/>
                                <form:errors cssStyle="color: red" path="ingredients" />

                            </div>
                            <div class="form-group">
                                <label>Quantity:</label>
                                <form:input class="form-control" name="quantities[]"
                                            path="quantities" type="number" value="10" min="0" max="1000"/>
                                <form:errors cssStyle="color: red" path="quantities" />
                            </div>
                        </span>
                    </div>
            </div>
        </form:form>
    </div>

</div>

<script type="text/javascript">
    $(".drop_down_menu").kendoDropDownList();
    $(".multiselect_menu").kendoMultiSelect().data("kendoMultiSelect");
    $("#ingredient").removeProp("multiple");
    $(function() {
        var scntDiv = jQuery('#resizeable');
        var i = jQuery('#to_clone').size() + 1;
        var etalon = jQuery('#resizeable span');

        jQuery('#add_ingredient').on("click", function() {
            var newField = etalon.clone();
            jQuery('<a href="#" id="remScnt">Remove</a>').appendTo(newField);
            newField.appendTo(scntDiv);
            i++;
        });

        jQuery('#resizeable').on("click", '#remScnt', function() {
            if( i > 2 ) {
                $(this).parents('span').remove();
                i--;
            }
        });
    });
</script>

</body>
</html>