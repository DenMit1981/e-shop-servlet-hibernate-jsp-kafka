<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>https://www.online-shop.com</title>
        <link rel="stylesheet" href="css/main.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
            integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    </head>
    <body class="centered">
        <h2>Register page</h2>
        <h3 class="has-error"><%= (String) session.getAttribute("registerErrors") %></h3>
        <form action="/register" method="post">
            <table>
                <tr>
                    <td>Login</td>
                    <td><input type="text" name="login" placeholder="Enter your name"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" placeholder="Enter your password"/></td>
                </tr>
            </table><br/>
            <input type="checkbox" name="isUserCheck" class="checkbox" id="checkbox" value="yes"/>
            <label for="checkbox">I agree with the terms of service</label>
                <br/><br/>
            <p><input type="submit" value="Sign In"/></p>
        </form>
    </body>
</html>