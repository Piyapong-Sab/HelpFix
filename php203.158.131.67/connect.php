<?php

    $conn = mysqli_connect('localhost', 'devhelpfix', 'Helpfix@2562', 'devhelpfix_helpfix') or die("Error Connect to Database");
    mysqli_query($conn ,"SET character_set_results=utf8");
    mysqli_query($conn ,"SET character_set_client='utf8'");
    mysqli_query($conn ,"SET character_set_connection='utf8'");
    mysqli_query($conn ,"collation_connection=utf8");
    mysqli_query($conn ,"collation_database=utf8");
    mysqli_query($conn ,"collation_server=utf8");

?>