<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>

    <script src="http://d3js.org/d3.v3.min.js"> </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">

    <link rel="icon" type="image/png" href="public/adam.png">
    <style>
        .form-control, .form-control::after {
            border-radius: 15px !important;
        }

        .form-control::after {
            border: 1px solid #007bff;
            color: #007bff;
            background-color: white;
        }

        .btn {
            border-radius:100px;
        }
        .first-enter-hidden-div {
            font-size: 1rem;
            display: none;
        }

        .first-enter-primary-div {
            font-size: 24px;
            display: block;
        }

        .first-enter-secondary-div {
            font-size: 1rem;
            display: block;
        }

        .first-enter-modal-hidden-div {
            display: none;
        }

        .first-enter-modal-showed-div {
            display: inline-block;
            vertical-align: top;
        }

        .page-title {
            margin: 0px 0px 30px 0px;
            text-align: center;
        }

        .axis path, .axis line {
            fill: none;
            stroke: #333;
        }
        .axis .grid-line {
            stroke: #000;
            stroke-opacity: 0.2;
        }
        .axis text {
            font: 10px Verdana;
        }
        .dot {
            stroke: steelblue;
            fill: lightblue;
        }

        .overview-info {
            opacity: 0;
            background: white;
            position: absolute;
            width: 300px;
            padding: 2px;
            border: 1px #000 solid;
            border-radius: 12px;*/
         /*  pointer-events: none;	This line needs to be removed */

        }

        #goals-info, #transactions-info {
            padding-top: 3px;

        }

        #goals-info p, #transactions-info p {
            margin-bottom: -2px;
        }

        #goals-info i, #transactions-info i {
            vertical-align: middle;
        }

    </style>
    <title>Adam - finconsult</title>
</head>
<body>
<#include "navbar.ftl">
<div class="container mt-3">

    <#if isLogin>
        <#if user.getActiveEventsTitles()?seq_contains("First Enter")>
            <#include "firstEnter.ftl">
        </#if>
    </#if>
    <#nested>
</div>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->



</body>
</html>
</#macro>