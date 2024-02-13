<%@ page import="org.w3c.dom.Element" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>

    <style>
        .updateView{
            display: grid;
            place-items: center;
            margin: 0;
        }

        .search{
            display: grid;
            place-items: center;
            margin: 0;
        }

        h1{
            text-align: center;
            margin-top: 30px;
        }

        label{
            font-size: 20px;
            margin: 5px;
        }

        input{
            font-size: 15px;
            margin: 5px;
        }

        .navigation{
            position: absolute;
            right: 0px;
        }

        a{
            text-decoration: none;
            color: black;
            font-size: 20px;
            margin: 5px;
            padding: 5px;
        }

        a:hover{
            background-color: rgba(117, 106, 116, 0.8);
        }

        .labels{
            display: grid;
            place-items: start;
            margin-left: 175px;
        }

        .labels td{
            font-size: 20px;
            width: 230px;
        }

        td{
            font-size: 18px;
            width: 240px;
        }
    </style>
</head>
<body>

    <div class="navigation">
        <a href="index.jsp">form</a>
        <a href="xml-read">read</a>
        <a href="xml-updateview">update</a>
    </div>

        <h1>Update Details</h1>

    <div class="search">
        <form action="xml-updateview" method="get">
            <label for="searchName">Search:</label>
            <input type="text" id="searchName" name="searchName" placeholder="Search by Name"/>
            <input type="submit" value="Search" />
        </form>
    </div>

    <table class="labels">
        <tr>
            <td>Name</td>
            <td>Email</td>
            <td>Telephone</td>
            <td>Degree Name</td>
            <td>Identity Number</td>
        </tr>
    </table>
<%
    NodeList personlist = (NodeList) request.getAttribute("personlist");

    // Iterate over the NodeList and display each person's details
    for (int i = 0; i < personlist.getLength(); i++) {
        Element personElement = (Element) personlist.item(i);
        String id = personElement.getElementsByTagName("id").item(0).getTextContent();
        String name = personElement.getElementsByTagName("name").item(0).getTextContent();
        String email = personElement.getElementsByTagName("email").item(0).getTextContent();
        String phone = personElement.getElementsByTagName("phone").item(0).getTextContent();
        String degree = personElement.getElementsByTagName("degree").item(0).getTextContent();
        String idNum = personElement.getElementsByTagName("idNum").item(0).getTextContent();
%>

    <div class="updateView">
        <form action="xml-update" method="post">
            <table>
                <tr>
                    <td><input type="hidden" name="personId" value="<%= id %>"></td>
                    <td><input type="text" id="name" name="name" value="<%= name %>" required></td>
                    <td><input type="email" id="email" name="email" value="<%= email %>" required></td>
                    <td><input type="text" id="phone" name="phone" value="<%= phone %>" required></td>
                    <td><input type="text" id="degree" name="degree" value="<%= degree %>" required></td>
                    <td><input type="text" id="idNum" name="idNum" value="<%= idNum %>" required></td>
                    <td><input type="submit" value="Update"></td>
                </tr>
            </table>
        </form>
    </div>

<%
    }
%>

</body>
</html>
