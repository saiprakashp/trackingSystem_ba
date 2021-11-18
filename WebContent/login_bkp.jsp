<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>ERICSSON LIVE WIRE</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="Content/css/lib/bootstrap.min.css" />   
        <link rel="stylesheet" type="text/css" href="Content/css/custom/Login.css"/>

    </head>
    <body>
<!--     <form action="authenticate" method="post" > -->

<form action="loginForm" method="post" >

        <div class="container-fluid" style="height: inherit;">
            <div class="row" style="height: inherit;">
            
                <div id="grad" class="col-lg-12" >
                    <div class="login-content">
                        <div class="login-block">
                            <p class="ico_login_style  "> </p>
                            <p  class="Title_style"><font >ERICSSON <br>LIVE WIRE</font></p>
                            <p class="colour_line"></p>
                            <p align="center" class="sign_style"><font >Registered Users Login</font></p>
                            <input name="user" id="user" class="credentials" type="text" value="" placeholder="signum" required/>
                            <input class="credentials" type="password" value="" placeholder="Password" id="password" name="password" required/>
                        
<!--                             <button onclick="">Sign In</button> -->
                          <input type="submit" value="Sign In" class="btn btn-primary" />
                        </div>
                    </div>
                </div>
            </div>
        </div>  
        </form>  
	<script src="js/jquery-3.1.1.min.js" type="text/javascript"></script>  
    <script src="js/jquery.toaster.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/custom/Login.js" type="text/javascript"></script>
      
    </body>
</html>
