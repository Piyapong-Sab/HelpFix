<?php
 
 if($_SERVER['REQUEST_METHOD']=='GET'){
    include('connect.php');
 $id = $_GET['id'];
 $sql = "select Pic_success from issue where Id_issue = '$id'";
 
 $r = mysqli_query($conn,$sql);
 
 $result = mysqli_fetch_array($r);
 
 header('content-type: image/jpeg');
 
 echo base64_decode($result['Pic_success']);
 
 mysqli_close($conn);
 
 }else{
 echo "Error";
 }

 ?>