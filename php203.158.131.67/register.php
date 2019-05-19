<?php

	
if($_SERVER['REQUEST_METHOD'] =='POST'){

    include ('connect.php');

	$id_user = $_POST['id_user'];
    $password = $_POST['password'];
    $name = $_POST['name'];
    $surname = $_POST['surname'];
    $gender = $_POST['gender'];
    $email = $_POST['email'];
    $telphone = $_POST['telphone'];

   // $password = password_hash($password, PASSWORD_DEFAULT);

   $sql = "INSERT INTO user (Id_user ,Id_role , Password , Name , Surname , Gender , Email , Telephone , Create_by , Create_date , Update_by , Update_date) VALUES 
   ('$id_user' , '102' ,  '$password' , '$name' , '$surname' , '$gender' , '$email' ,'$telephone' , '$id_user' , now()  , '$id_user' , now() );";

  if (mysqli_query($conn ,$sql )) {
  	echo "Success";
  }
       // mysqli_close($conn);
   }

?>